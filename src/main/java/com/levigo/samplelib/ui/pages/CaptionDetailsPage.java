package com.levigo.samplelib.ui.pages;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public abstract class CaptionDetailsPage extends DetailsPage {

  private static final long serialVersionUID = 1L;
  private JLabel headerLabel;
  private JComponent body;

  public CaptionDetailsPage() {
    super.setLayout(new GridBagLayout());
    headerLabel = new JLabel();
    headerLabel.getInsets().set(10, 10, 10, 10);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    headerLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0d;
    gbc.gridx = 0;
    gbc.gridy = 0;
    super.addImpl(headerLabel, gbc, -1);
  }

  protected void setHeader(String text) {
    headerLabel.setText(text != null ? text : "");
  }

  protected String getHeader() {
    return headerLabel.getText();
  }

  @Override
  protected void addImpl(Component comp, Object constraints, int index) {
    throw new UnsupportedOperationException("new contents may only be added using setBody(JComponent)");
  }

  protected void setBody(JComponent body) {

    if (this.body != null) {
      remove(this.body);
    }
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0d;
    gbc.weighty = 1.0d;
    super.addImpl(body, gbc, -1);
    this.body = body;

    invalidate();
    revalidate();
  }
}
