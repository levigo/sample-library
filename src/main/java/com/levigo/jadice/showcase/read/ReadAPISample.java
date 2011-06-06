package com.levigo.jadice.showcase.read;


import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.AsyncReadConfigurer;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.ExecutableSample;

public class ReadAPISample extends AbstractSample implements ExecutableSample {


  public ReadAPISample() {
    super("Read API, multiple Layers", ReadAPISample.class, ClasspathInputProvider.class);
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
