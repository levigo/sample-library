package com.levigo.jadice.showcase.read;


import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.AsyncReadConfigurer;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.showcase.util.ClasspathInputProvider;

public class ReadAPISample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public ReadAPISample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }


  public void execute() {

    final AsyncReadConfigurer r = Read.asynchronously();

    r.newDocument() //
    .append(new ClasspathInputProvider("background.pdf")) //
    .append(new ClasspathInputProvider("content.txt")) //
    .append(new ClasspathInputProvider("watermark.png"));

    basicJadicePanel.getPageView().setDocument(r.getFirstDocument());

    r.execute();
  }
}
