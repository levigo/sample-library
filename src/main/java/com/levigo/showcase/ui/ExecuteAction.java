package com.levigo.showcase.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.levigo.showcase.controllers.ExecutionController;

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
