package com.levigo.showcase.ui.pages;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.levigo.showcase.SampleInstance;
import com.levigo.showcase.controllers.AbstractController;
import com.levigo.showcase.controllers.ExecutionController;
import com.levigo.showcase.controllers.StartStopController;

public class SampleDetailsPage extends CaptionDetailsPage {
  private static final long serialVersionUID = 1L;

  public SampleDetailsPage(SampleInstance sample) {
    setHeader(sample.getDescriptor().getName());

    final JPanel p = new JPanel();
    p.setBackground(Color.WHITE);
    p.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridwidth = 3;
    gbc.weightx = 1.0;
//    gbc.weighty = 1.0;

    final JTextArea descriptionTextArea = new JTextArea();
    descriptionTextArea.setText(sample.getDescriptor().getDescription());
    descriptionTextArea.setEditable(false);
    p.add(descriptionTextArea, gbc);


    int gridy = 1;

    for (AbstractController controller : sample.getControllers()) {

      gbc = new GridBagConstraints();
      gbc.insets = new Insets(10, 10, 10, 0);
      gbc.gridy = gridy;
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.WEST;

      p.add(new JLabel(controller.getDescriptor().getName()), gbc);

      if (controller instanceof ExecutionController) {

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,0,10,10);
        gbc.gridy = gridy;
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        p.add(new RoundedButton(new ExecuteAction((ExecutionController) controller)), gbc);

      } else if (controller instanceof StartStopController) {

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridy = gridy;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final StartStopSampleStateManager stateManager = new StartStopSampleStateManager(
            (StartStopController) controller);

        p.add(new RoundedButton(stateManager.getStartAction()), gbc);
        gbc.insets = new Insets(10,5,10,10);
        gbc.gridx = 2;
        p.add(new RoundedButton(stateManager.getStopAction()), gbc);
      }

      gridy++;
    }

    final JScrollPane sp = new JScrollPane(p);
    sp.setBorder(null);
    setBody(sp);
  }
}
