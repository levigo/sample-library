package com.levigo.jadice.showcase.tool3;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;

import com.levigo.jadice.swing.tool.MouseEditEvent;
import com.levigo.jadice.swing.tool.RenderParameters;
import com.levigo.jadice.swing.tool.Tool;

public class SimpleHelloWorldTool extends Tool {

  @Override
  protected void handleMouseClicked(MouseEditEvent e, boolean isActive) {
    if (!e.hasPageContext())
      return;

    if (new Rectangle2D.Double(800, 2200, 6000, 1000).contains(e.getDocumentPoint()))
      JOptionPane.showMessageDialog(getManager().getViewComponent(), "Click: " + e.getDocumentPoint());
  }

  @Override
  protected void render(RenderParameters parameters, boolean isActive) {

    final Graphics2D g2d = parameters.getGraphics();

    g2d.transform(parameters.getTransform());

    final Font f = new Font("Arial", Font.PLAIN, 1000);
    final GlyphVector glv = f.createGlyphVector(new FontRenderContext(null, true, true), "Hello World!");

    g2d.drawGlyphVector(glv, 1000, 3000);

  }
}
