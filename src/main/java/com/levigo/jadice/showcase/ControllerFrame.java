package com.levigo.jadice.showcase;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.showcase.read.ReadAPISample;

import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerFrame extends JFrame {

  public static final class SourceButton extends JButton implements ActionListener {
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(
        ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/text-x-java.png"));
    private final Class<?>[] classes;

    public SourceButton(Class<?>... classes) {
      this.classes = classes;
      setBorder(new EmptyBorder(0, 0, 0, 0));
      setIcon(DEFAULT_ICON);

      addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      final SourceViewerFrame svf = new SourceViewerFrame(classes);
      svf.setSize(800, 600);
      svf.setVisible(true);
    }

  }

  public static final class ExecuteButton extends JButton implements ActionListener {
    private static final ImageIcon DEFAULT_ICON = new ImageIcon(
        ControllerFrame.class.getResource("/com/levigo/jadice/showcase/icons/next.png"));
    private final ExecutableSample sample;
    private final BasicJadicePanel basicJadicePanel;

    public ExecuteButton(BasicJadicePanel basicJadicePanel, ExecutableSample sample) {
      this.basicJadicePanel = basicJadicePanel;
      this.sample = sample;
      setBorder(new EmptyBorder(0, 0, 0, 0));
      setIcon(DEFAULT_ICON);
      addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      sample.execute(basicJadicePanel);
    }
  }


  private final Sample[] samples = new Sample[]{
    new ReadAPISample()
  };
  private final BasicJadicePanel basicJadicePanel;

  public ControllerFrame(BasicJadicePanel basicJadicePanel) {
    super("jadice\u00ae 5 Showcase Controller");
    setIconImage((Image) UIManager.get("Jadice.window.icon.image"));
    this.basicJadicePanel = basicJadicePanel;
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout(0, 0));

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(20, 20, 0, 20));
    getContentPane().add(panel);


    initSamples(panel);

    JSeparator separator = new JSeparator();
    GridBagConstraints gbc_separator = new GridBagConstraints();
    gbc_separator.insets = new Insets(0, 0, 5, 0);
    gbc_separator.gridx = 0;
    gbc_separator.gridwidth = 3;
    gbc_separator.weightx = 1.0;
    gbc_separator.fill = GridBagConstraints.HORIZONTAL;
    panel.add(separator, gbc_separator);

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

  }

  private void initSamples(JPanel panel) {

    for (Sample s : samples) {

      final JLabel lbl = new JLabel(s.name());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.WEST;
      gbc.gridx = 0;
      gbc.gridy = -1;

      panel.add(lbl, gbc);

      if (s instanceof ExecutableSample) {
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        panel.add(new ExecuteButton(basicJadicePanel, (ExecutableSample) s),gbc);
      }
      
      gbc = new GridBagConstraints();
      gbc.gridx = 2;
      panel.add(new SourceButton(s.getClasses()),gbc);
      
    }

  }

  private static final long serialVersionUID = 1L;


}
