package com.levigo.jadice.showcase.read;


import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.AsyncReadConfigurer;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.ExecutableSample;
import com.levigo.jadice.showcase.util.ClasspathInputProvider;

public class ReadAPISample extends AbstractSample implements ExecutableSample {


  public ReadAPISample() {
    super("Read API, single Layer", ReadAPISample.class, ClasspathInputProvider.class);
  }

  @Override
  public void execute(BasicJadicePanel basicJadicePanel) {

    final AsyncReadConfigurer r = Read.asynchronously();

    r.newDocument() //
    .append(new ClasspathInputProvider("background.pdf")) //
    .append(new ClasspathInputProvider("content.txt")) //
    .append(new ClasspathInputProvider("watermark.png"));

    basicJadicePanel.getPageView().setDocument(r.getFirstDocument());

    r.execute();
  }
}