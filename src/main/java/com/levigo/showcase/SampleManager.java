package com.levigo.showcase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.showcase.descriptors.LevigoSamples;
import com.levigo.showcase.descriptors.SampleDescriptor;

public class SampleManager {

  private final Module jadiceDocumentplatformHostModule;

  public SampleManager(final BasicJadicePanel basicJadicePanel) {
    super();

    jadiceDocumentplatformHostModule = new AbstractModule() {

      @Override
      protected void configure() {
        bind(BasicJadicePanel.class).toInstance(basicJadicePanel);
      }
    };
  }

  @SuppressWarnings({
      "unchecked", "rawtypes"
  })
  public Object createSampleInstance(SampleDescriptor sd) {
    final Class clazz = sd.getSampleClass();
    // FIXME respect the sample host!
    return Guice.createInjector(jadiceDocumentplatformHostModule).getInstance(clazz);
  }

  public List<SampleDescriptor> getSamples() {
    Enumeration<URL> resources;
    try {
      resources = getClass().getClassLoader().getResources("META-INF/levigo-samples.xml");
    } catch (IOException e1) {
      e1.printStackTrace();
      return Collections.emptyList();
    }

    List<SampleDescriptor> sds = new ArrayList<SampleDescriptor>();

    while (resources.hasMoreElements()) {
      URL url = resources.nextElement();

      try {
        LevigoSamples samples = LevigoSamples.read(url.openStream());
        sds.addAll(samples.getSamples());
      } catch (JAXBException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return sds;
  }

}
