package com.levigo.samplelib;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jsyntaxpane.DefaultSyntaxKit;

import com.levigo.samplelib.ui.ControllerFrame;

public class Main {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      // we don't care.
    }

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        
        // initialize the syntax editor kit
        DefaultSyntaxKit.initKit();
        
        // create and open the showcase controller frame
        ControllerFrame cf = new ControllerFrame();
        cf.pack();
        cf.setVisible(true);

      }
    });
  }
}
