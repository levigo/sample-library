package com.levigo.jadice.showcase.tool;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.levigo.jadice.showcase.tool2.ConditionalMarkerTool;
import com.levigo.jadice.swing.tool.RenderParameters;
import com.levigo.jadice.swing.tool.Tool;

public class MarkerTool extends Tool {

  private static final BufferedImage VALID = readImage("/com/levigo/jadice/showcase/tool/highlight.png");

  private static BufferedImage readImage(String resource) {
    try {
      return ImageIO.read(ConditionalMarkerTool.class.getResourceAsStream(resource));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void render(RenderParameters parameters, boolean isActive) {

    final Graphics2D g2d = parameters.getGraphics();
    final AffineTransform tx = g2d.getTransform();

    g2d.transform(parameters.getTransform());
    g2d.scale(50, 50);
    g2d.translate(50, 50);

    g2d.drawImage(VALID, null, 0, 0);

    g2d.setTransform(tx);
  }
}
