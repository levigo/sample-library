package com.levigo.showcase.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import com.levigo.showcase.controllers.StartStopController;

final class StartStopSampleStateManager {

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
      setBorderPainted(false);
      setRolloverEnabled(true);
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
      setBorderPainted(false);
      setRolloverEnabled(true);
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

  protected final StartStopSampleStateManager.StartButton startButton;
  protected final StartStopSampleStateManager.StopButton stopButton;
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