package com.levigo.showcase.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.tree.TreePath;

import com.levigo.showcase.Category;
import com.levigo.showcase.SampleManager;
import com.levigo.showcase.controllers.ExecutionController;
import com.levigo.showcase.controllers.StartStopController;
import com.levigo.showcase.descriptors.ControllerDescriptor;
import com.levigo.showcase.descriptors.ExecutionControllerDescriptor;
import com.levigo.showcase.descriptors.SampleDescriptor;
import com.levigo.showcase.descriptors.StartStopControllerDescriptor;

public class ControllerFrame extends JFrame {

  private static final long serialVersionUID = 1L;


  private final SampleManager manager;

  public ControllerFrame() {
    super("jadice\u00ae 5 Showcase Controller");
    setIconImage((Image) UIManager.get("Jadice.window.icon.image"));
    manager = new SampleManager();

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout(0, 0));

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(20, 20, 0, 20));
    getContentPane().add(panel);

    initSamplesTree(panel);

    initSamples(panel);

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

    separator(panel);

    final JPanel layerPanel = new JPanel();
    new JScrollPane(layerPanel);
  }

  private void initSamplesTree(JPanel panel) {
    final JTree sampleTree = new JTree(new SampleTreeModel(manager));
    final GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridwidth = 3;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;

    final JScrollPane scrollPane = new JScrollPane(sampleTree);
    scrollPane.setPreferredSize(new Dimension(200, 400));
    panel.add(scrollPane, gbc);


    sampleTree.setRootVisible(true);
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

  private void initSamples(JPanel panel) {

    List<SampleDescriptor> sds = manager.getSamples();

    for (SampleDescriptor sd : sds) {

      final JLabel lbl = new JLabel(sd.getName());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.WEST;
      gbc.gridx = 0;
      gbc.gridy = -1;

      panel.add(lbl, gbc);

      JPanel buttonPanel = new JPanel();

      final Object si = manager.createSampleInstance(sd);

      // for now we're respecting only the first controller
      if (sd.getControllers().size() == 1) {
        final ControllerDescriptor c = sd.getControllers().get(0);

        if (c instanceof ExecutionControllerDescriptor) {

          final ExecutionController controller = ExecutionController.create(si, (ExecutionControllerDescriptor) c);

          buttonPanel.add(new ExecuteButton(controller));

        } else if (c instanceof StartStopControllerDescriptor) {

          final StartStopController controller = StartStopController.create(si, (StartStopControllerDescriptor) c);
          final StartStopSampleStateManager stateManager = new StartStopSampleStateManager(controller);
          buttonPanel.add(stateManager.getStartButton());
          buttonPanel.add(stateManager.getStopButton());
        }

      }


      gbc = new GridBagConstraints();
      gbc.gridx = 1;
      gbc.anchor = GridBagConstraints.EAST;
      panel.add(buttonPanel, gbc);

      gbc = new GridBagConstraints();
      gbc.gridx = 2;
      panel.add(new SourceButton(sd.getClasses().toArray(new Class[sd.getClasses().size()])), gbc);

    }

  }


}
