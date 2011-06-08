package com.levigo.jadice.showcase.tool2;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.levigo.jadice.document.Page;
import com.levigo.jadice.swing.tool.MouseEditEvent;
import com.levigo.jadice.swing.tool.RenderParameters;
import com.levigo.jadice.swing.tool.Tool;

public class ConditionalMarkerTool extends Tool {

  private static final BufferedImage INVALID = readImage("/com/levigo/jadice/showcase/tool/error.png");

  private static final String KEY = "valid";

  private static final BufferedImage VALID = readImage("/com/levigo/jadice/showcase/tool/check.png");

  private static BufferedImage readImage(String resource) {
    try {
      return ImageIO.read(ConditionalMarkerTool.class.getResourceAsStream(resource));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void handleMouseClicked(MouseEditEvent e, boolean isActive) {
    if (!e.hasPageContext())
      return;

    final Point2D p = e.getConstrainedDocumentPoint();

    if (new Rectangle2D.Double(2500, 11400, 6400, 6400).contains(p)) {
      System.err.println("MATCH:::");
      final Map<String, Object> props = e.getPage().getProperties();
      if (props.containsKey(KEY))
        props.remove(KEY);
      else
        props.put(KEY, Boolean.TRUE);
      e.consume();
      getManager().getViewComponent().repaint();
    }
  }

  @Override
  protected void render(RenderParameters parameters, boolean isActive) {

    final Page p = parameters.getPage();
    final Graphics2D g2d = parameters.getGraphics();
    final AffineTransform tx = g2d.getTransform();

    g2d.transform(parameters.getTransform());
    g2d.scale(50, 50);
    g2d.translate(50, 228);

    if (p.getProperties().get(KEY) != null)
      g2d.drawImage(VALID, null, 0, 0);
    else
      g2d.drawImage(INVALID, null, 0, 0);

    g2d.setTransform(tx);
  }
}
