package com.levigo.jadice.showcase.annotations;

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.UIDocument;
import com.levigo.jadice.document.write.DefaultWriterControls;
import com.levigo.jadice.format.annotation.JadiceAnnotationWriter;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.ExecutableSample;
import com.levigo.jadice.showcase.SourceViewerFrame;
import com.levigo.jadice.showcase.util.XMLUtils;

public class AnnotationsSavingSample extends AbstractSample implements ExecutableSample {
  public AnnotationsSavingSample() {
    super("Annotations, save xml format", AnnotationsSavingSample.class);
  }

  @Override
  public void execute(BasicJadicePanel basicJadicePanel) {

    final UIDocument<Component> doc = basicJadicePanel.getPageView().getDocument();

    if (doc != null) {

      final JadiceAnnotationWriter w = new JadiceAnnotationWriter();
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final String formatted;

      try {
        w.write(doc, baos, new DefaultWriterControls());

        // format the xml code
        formatted = XMLUtils.format(CharStreams.toString(CharStreams.newReaderSupplier(
            ByteStreams.newInputStreamSupplier(baos.toByteArray()), Charset.defaultCharset())));
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }

      SourceViewerFrame.forString("Annotations.xml", formatted).setVisible(true);
    }
  }

}
