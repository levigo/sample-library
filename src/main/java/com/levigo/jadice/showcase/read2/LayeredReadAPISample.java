package com.levigo.jadice.showcase.read2;


import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.AsyncReadConfigurer;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.showcase.util.ClasspathInputProvider;

public class LayeredReadAPISample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public LayeredReadAPISample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }


  public void execute() {

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
