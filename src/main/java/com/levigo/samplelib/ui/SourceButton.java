package com.levigo.samplelib.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public final class SourceButton extends JButton implements ActionListener {
  private static final ImageIcon DEFAULT_ICON = new ImageIcon(
      ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/text-x-java.png"));
  private static final long serialVersionUID = 1L;
  private final Class<?>[] classes;

  public SourceButton(Class<?>... classes) {
    this.classes = classes;
    setBorder(new EmptyBorder(0, 0, 0, 0));
    setIcon(DEFAULT_ICON);
    setBorderPainted(false);
    setRolloverEnabled(true);

    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    final SourceViewerFrame svf = SourceViewerFrame.forClasses(classes);
    svf.setVisible(true);
  }

}