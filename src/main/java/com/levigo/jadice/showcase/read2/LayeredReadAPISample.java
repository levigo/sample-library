package com.levigo.jadice.showcase.read2;


import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.AsyncReadConfigurer;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.ExecutableSample;
import com.levigo.jadice.showcase.util.ClasspathInputProvider;

public class LayeredReadAPISample extends AbstractSample implements ExecutableSample {


  public LayeredReadAPISample() {
    super("Read API, multiple Layers", LayeredReadAPISample.class, ClasspathInputProvider.class);
  }

  @Override
  public void execute(BasicJadicePanel basicJadicePanel) {

    final AsyncReadConfigurer r = Read.asynchronously();

    r.newDocument() //
    .appendGroup() //
    /* G */.add(r.task(new ClasspathInputProvider("background.pdf")).mapDefaultLayer().toBackgroundLayer()) //
    /* G */.add(r.task(new ClasspathInputProvider("content.txt"))) //
    /* G */.add(r.task(new ClasspathInputProvider("watermark.png")).mapDefaultLayer().toFormLayer());

    basicJadicePanel.getPageView().setDocument(r.getFirstDocument());

    r.execute();
  }
}
