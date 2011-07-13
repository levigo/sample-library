package com.levigo.showcase.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.levigo.showcase.SampleManager;
import com.levigo.showcase.ui.pages.DefaultDetailsPageFactory;
import com.levigo.showcase.ui.pages.DetailsPage;
import com.levigo.showcase.ui.pages.DetailsPageFactory;

public class ControllerFrame extends JFrame {

  private final class ExitAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private ExitAction(String name) {
      super(name);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
      putValue(MNEMONIC_KEY, KeyEvent.VK_X);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }

  private static final long serialVersionUID = 1L;


  private final SampleManager manager;
  private final JPanel contentPanel;
  private final DetailsPageFactory detailsPageFactory;

  public ControllerFrame() {
    super("Example Library");
    manager = new SampleManager();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    detailsPageFactory = new DefaultDetailsPageFactory();


    setJMenuBar(createMenuBar());

    getContentPane().setLayout(new BorderLayout());

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(20, 20, 0, 20));

//    Kernel kernel = new Kernel(3, 3, new float[]{ //
//            1f / 9f, 1f / 9f, 1f / 9f, //
//            1f / 9f, 1f / 9f, 1f / 9f, //
//            1f / 9f, 1f / 9f, 1f / 9f
//        //
//        });
//    LockableUI ui = new LockableUI(//
//        new BufferedImageOpEffect(new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)), //
//        new BufferedImageOpEffect(new ConvolveOp(kernel)) //
//    );
//
//    final JXLayer<JComponent> l = new JXLayer<JComponent>(panel, ui);
//    getContentPane().add(l);
    getContentPane().add(panel);

    initSamplesTree(panel);

    contentPanel = initContent(panel);

    separator(panel);

    JLabel lblControllerSource = new JLabel("Showcase Controller Source Code");
    GridBagConstraints gbc_lblControllerSource = new GridBagConstraints();
    gbc_lblControllerSource.insets = new Insets(0, 0, 0, 5);
    gbc_lblControllerSource.anchor = GridBagConstraints.WEST;
    gbc_lblControllerSource.weightx = 1.0;
    gbc_lblControllerSource.gridy = -1;
    gbc_lblControllerSource.gridx = 0;
    panel.add(lblControllerSource, gbc_lblControllerSource);

    SourceButton button = new SourceButton(ControllerFrame.class, SourceViewerFrame.class);
    GridBagConstraints gbc_button = new GridBagConstraints();
    gbc_button.gridx = 2;
    panel.add(button, gbc_button);
  }

  protected JMenuBar createMenuBar() {
    final JMenuBar mBar = new JMenuBar();

    // File menu
    final JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('f');
    fileMenu.add(new JMenuItem(new ExitAction("Exit")));

    mBar.add(fileMenu);

    return mBar;
  }

  private JPanel initContent(JPanel panel) {
    JPanel content = new JPanel();
    content.setBackground(Color.WHITE);
    content.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
    content.setPreferredSize(new Dimension(400, 400));
    final GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridwidth = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.insets = new Insets(0, 20, 0, 0);
    gbc.fill = GridBagConstraints.BOTH;
    panel.add(content, gbc);
    return content;
  }

  private void initSamplesTree(JPanel panel) {
    final JTree sampleTree = new JTree(new SampleTreeModel(manager));
    sampleTree.setRootVisible(false);
    sampleTree.setShowsRootHandles(true);

    sampleTree.addTreeSelectionListener(new TreeSelectionListener() {

      @Override
      public void valueChanged(TreeSelectionEvent e) {
        final TreePath p = e.getPath();
        if (p != null) {
          final Object o = p.getLastPathComponent();
          DetailsPage page = null;
          if (o instanceof CategoryTreeNode) {
            page = detailsPageFactory.createCategoryPage(((CategoryTreeNode) o).getCategory());
          } else if (o instanceof SampleTreeNode) {
            page = detailsPageFactory.createSamplePage(((SampleTreeNode) o).getSample());
          }
          if (page != null) {
            setDetailsPage(page);
          }
        }
      }
    });

    final GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridwidth = 3;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;

    final JScrollPane scrollPane = new JScrollPane(sampleTree);
    scrollPane.setPreferredSize(new Dimension(200, 400));
    panel.add(scrollPane, gbc);
  }

  protected void setDetailsPage(DetailsPage page) {
    contentPanel.removeAll();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.add(page, BorderLayout.CENTER);
    contentPanel.revalidate();
  }

  private void separator(JPanel panel) {
    JSeparator separator = new JSeparator();
    GridBagConstraints gbc_separator = new GridBagConstraints();
    gbc_separator.insets = new Insets(0, 0, 5, 0);
    gbc_separator.gridx = 0;
    gbc_separator.gridwidth = 3;
    gbc_separator.weightx = 1.0;
    gbc_separator.fill = GridBagConstraints.HORIZONTAL;
    panel.add(separator, gbc_separator);
  }

  //  private void initSamples(JPanel panel) {
  //
  //    List<SampleDescriptor> sds = manager.getSamples();
  //
  //    for (SampleDescriptor sd : sds) {
  //
  //      final JLabel lbl = new JLabel(sd.getName());
  //      GridBagConstraints gbc = new GridBagConstraints();
  //      gbc.weightx = 1.0;
  //      gbc.anchor = GridBagConstraints.WEST;
  //      gbc.gridx = 0;
  //      gbc.gridy = -1;
  //
  //      panel.add(lbl, gbc);
  //
  //      JPanel buttonPanel = new JPanel();
  //
  //      final Object si = manager.createSampleInstance(sd);
  //
  //      // for now we're respecting only the first controller
  //      if (sd.getControllers().size() == 1) {
  //        final ControllerDescriptor c = sd.getControllers().get(0);
  //
  //        if (c instanceof ExecutionControllerDescriptor) {
  //
  //          final ExecutionController controller = ExecutionController.create(si, (ExecutionControllerDescriptor) c);
  //
  //          buttonPanel.add(new ExecuteButton(controller));
  //
  //        } else if (c instanceof StartStopControllerDescriptor) {
  //
  //          final StartStopController controller = StartStopController.create(si, (StartStopControllerDescriptor) c);
  //          final StartStopSampleStateManager stateManager = new StartStopSampleStateManager(controller);
  //          buttonPanel.add(stateManager.getStartButton());
  //          buttonPanel.add(stateManager.getStopButton());
  //        }
  //
  //      }
  //
  //
  //      gbc = new GridBagConstraints();
  //      gbc.gridx = 1;
  //      gbc.anchor = GridBagConstraints.EAST;
  //      panel.add(buttonPanel, gbc);
  //
  //      gbc = new GridBagConstraints();
  //      gbc.gridx = 2;
  //      panel.add(new SourceButton(sd.getClasses().toArray(new Class[sd.getClasses().size()])), gbc);
  //
  //    }
  //
  //  }


}
