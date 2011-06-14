package com.levigo.jadice.showcase.annotations2;

import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.AsyncReadConfigurer;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.format.annotation.JadiceAnnotationFormat;
import com.levigo.jadice.showcase.util.ClasspathInputProvider;

public class AnnotationsReadAPISample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public AnnotationsReadAPISample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }


  public void execute() {

    final AsyncReadConfigurer r = Read.asynchronously();

    r.newDocument() //
    .appendGroup() //
    /* G */.add(r.task(new ClasspathInputProvider("background.pdf")).mapDefaultLayer().toBackgroundLayer()) //
    /* G */.add(r.task(new ClasspathInputProvider("content.txt"))) //
    /* G */.add(r.task(new ClasspathInputProvider("watermark.png")).mapDefaultLayer().toFormLayer()) //
    /* G */.add(r.task(new ClasspathInputProvider("annotations.xml")).using(JadiceAnnotationFormat.class)); //


    basicJadicePanel.getPageView().setDocument(r.getFirstDocument());

    r.execute();
  }
}