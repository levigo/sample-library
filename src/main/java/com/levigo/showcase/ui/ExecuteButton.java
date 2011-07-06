package com.levigo.showcase.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import com.levigo.showcase.controllers.ExecutionController;

public final class ExecuteButton extends JButton implements ActionListener {
  private static final ImageIcon DEFAULT_ICON = new ImageIcon(
      ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/next.png"));
  private static final long serialVersionUID = 1L;
  private final ExecutionController controller;

  public ExecuteButton(ExecutionController controller) {
    this.controller = controller;
    setBorder(new EmptyBorder(0, 0, 0, 0));
    setBorderPainted(false);
    setRolloverEnabled(true);
    setIcon(DEFAULT_ICON);
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    controller.execute();
  }
}
