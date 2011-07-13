package com.levigo.samplelib.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.levigo.samplelib.controllers.ExecutionController;
import com.levigo.samplelib.ui.ControllerFrame;

public final class ExecuteAction extends AbstractAction {
  private static final ImageIcon DEFAULT_ICON = new ImageIcon(
      ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/next.png"));
  private static final long serialVersionUID = 1L;
  private final ExecutionController controller;

  public ExecuteAction(ExecutionController controller) {
    super("Run");
    this.controller = controller;
    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    controller.execute();
  }
}
