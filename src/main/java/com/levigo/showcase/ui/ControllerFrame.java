package com.levigo.showcase.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.showcase.SampleManager;
import com.levigo.showcase.controllers.ExecutionController;
import com.levigo.showcase.controllers.StartStopController;
import com.levigo.showcase.descriptors.ControllerDescriptor;
import com.levigo.showcase.descriptors.ExecutionControllerDescriptor;
import com.levigo.showcase.descriptors.SampleDescriptor;
import com.levigo.showcase.descriptors.StartStopControllerDescriptor;

public class ControllerFrame extends JFrame {

  public static final class ExecuteButton extends JButton implements ActionListener {
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(
        ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/next.png"));
    private static final long serialVersionUID = 1L;
    private final ExecutionController controller;

    public ExecuteButton(ExecutionController controller) {
      this.controller = controller;
      setBorder(new EmptyBorder(0, 0, 0, 0));
      setIcon(DEFAULT_ICON);
      addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      controller.execute();
    }
  }

  public static final class SourceButton extends JButton implements ActionListener {
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(
        ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/text-x-java.png"));
    private static final long serialVersionUID = 1L;
    private final Class<?>[] classes;

    public SourceButton(Class<?>... classes) {
      this.classes = classes;
      setBorder(new EmptyBorder(0, 0, 0, 0));
      setIcon(DEFAULT_ICON);

      addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      final SourceViewerFrame svf = SourceViewerFrame.forClasses(classes);
      svf.setVisible(true);
    }

  }


  private static final class StartStopSampleStateManager {

    private static final class StartButton extends JButton implements ActionListener {
      private static final ImageIcon DEFAULT_ICON = new ImageIcon(
          ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/next.png"));
      /**
       * 
       */
      private static final long serialVersionUID = 1L;
      private final StartStopSampleStateManager stateManager;

      public StartButton(StartStopSampleStateManager stateManager) {
        this.stateManager = stateManager;
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setIcon(DEFAULT_ICON);
        addActionListener(this);
        updateState();
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        stateManager.start();
      }

      protected void updateState() {
        setEnabled(!stateManager.isStarted());
      }
    }

    private static final class StopButton extends JButton implements ActionListener {
      private static final ImageIcon DEFAULT_ICON = new ImageIcon(
          ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/stop-sign.png"));
      /**
       * 
       */
      private static final long serialVersionUID = 1L;
      private final StartStopSampleStateManager stateManager;

      public StopButton(StartStopSampleStateManager stateManager) {
        this.stateManager = stateManager;
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setIcon(DEFAULT_ICON);
        addActionListener(this);
        updateState();
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        stateManager.stop();
      }

      protected void updateState() {
        setEnabled(stateManager.isStarted());
      }
    }

    protected final StartButton startButton;
    protected final StopButton stopButton;
    private final StartStopController controller;
    // FIXME that should be part of the StartStopController
    private volatile boolean started;

    public StartStopSampleStateManager(StartStopController controller) {
      super();
      this.controller = controller;
      startButton = new StartButton(this);
      stopButton = new StopButton(this);
    }

    public JButton getStartButton() {
      return startButton;
    }

    public JButton getStopButton() {
      return stopButton;
    }

    public boolean isStarted() {
      return started;
    }

    public void start() {
      controller.start();
      started = true;
      updateStates();
    }

    public void stop() {
      controller.stop();
      started = false;
      updateStates();
    }


    private void updateStates() {
      if (startButton != null)
        startButton.updateState();
      if (stopButton != null)
        stopButton.updateState();
    }

  }

  private static final long serialVersionUID = 1L;

  private final BasicJadicePanel basicJadicePanel;

  private final SampleManager manager;

  public ControllerFrame(BasicJadicePanel basicJadicePanel) {
    super("jadice\u00ae 5 Showcase Controller");
    setIconImage((Image) UIManager.get("Jadice.window.icon.image"));
    this.basicJadicePanel = basicJadicePanel;
    manager = new SampleManager(basicJadicePanel);

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout(0, 0));

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(20, 20, 0, 20));
    getContentPane().add(panel);


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

    SourceButton button = new SourceButton(ShowcaseJadicePanel.class, ControllerFrame.class, SourceViewerFrame.class);
    GridBagConstraints gbc_button = new GridBagConstraints();
    gbc_button.gridx = 2;
    panel.add(button, gbc_button);

    separator(panel);

    final JPanel layerPanel = new JPanel();
    new JScrollPane(layerPanel);
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
