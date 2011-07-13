package com.levigo.samplelib.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import com.levigo.samplelib.controllers.StartStopController;
import com.levigo.samplelib.ui.ControllerFrame;

final class StartStopSampleStateManager {

  private static final class StartAction extends AbstractAction {
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(
        ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/next.png"));
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final StartStopSampleStateManager stateManager;

    public StartAction(StartStopSampleStateManager stateManager) {
      super("Start");
      this.stateManager = stateManager;
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

  private static final class StopAction extends AbstractAction {
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(
        ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/stop-sign.png"));
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final StartStopSampleStateManager stateManager;

    public StopAction(StartStopSampleStateManager stateManager) {
      super("Stop");
      this.stateManager = stateManager;
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

  protected final StartStopSampleStateManager.StartAction startAction;
  protected final StartStopSampleStateManager.StopAction stopAction;
  private final StartStopController controller;
  // FIXME that should be part of the StartStopController
  private volatile boolean started;

  public StartStopSampleStateManager(StartStopController controller) {
    super();
    this.controller = controller;
    startAction = new StartAction(this);
    stopAction = new StopAction(this);
  }

  public Action getStartAction() {
    return startAction;
  }

  public Action getStopAction() {
    return stopAction;
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
    if (startAction != null)
      startAction.updateState();
    if (stopAction != null)
      stopAction.updateState();
  }

}