package com.levigo.showcase;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.levigo.showcase.SampleHostInstance.SampleHostModule;
import com.levigo.showcase.descriptors.CategoryDescriptor;
import com.levigo.showcase.descriptors.LevigoSampleSystem;
import com.levigo.showcase.descriptors.LevigoSamples;
import com.levigo.showcase.descriptors.RequireSampleHostDescriptor;
import com.levigo.showcase.descriptors.RequirementDescriptor;
import com.levigo.showcase.descriptors.SampleDescriptor;
import com.levigo.showcase.descriptors.SampleHostDescriptor;

public class SampleManager {

  // FIXME should that really be static?`
  private static final Injector ROOT_INJECTOR = Guice.createInjector(new AbstractModule() {
    @Override
    protected void configure() {
      bind(ShowcaseContext.class).toInstance(new ShowcaseContext());
    }
  });

  private Category categories;

  private List<SampleHostInstance> hostInstances;

  public SampleManager() {
    super();

    // load the category tree
    // LevigoSampleHosts.read(is)

    final List<Category> allCategories = new ArrayList<Category>();
    hostInstances = new ArrayList<SampleHostInstance>();
    load("META-INF/levigo-sample-system.xml", new Processor<Void, InputStream>() {

      @Override
      public Void process(InputStream in) throws Exception {
        final LevigoSampleSystem system = LevigoSampleSystem.read(in);

        // FIXME category collision
        for (CategoryDescriptor desc : system.getCategories()) {
          allCategories.add(Category.create(desc));
        }

        for (final SampleHostDescriptor shd : system.getSampleHosts()) {

          hostInstances.add(new SampleHostInstance(shd, new SampleHostModule(shd)));

        }

        return null;
      }
    });

    categories = new RootCategory(allCategories);

    for (SampleDescriptor descriptor : getSamples()) {
      categories.install(descriptor);
    }

  }

  @SuppressWarnings({
      "unchecked", "rawtypes"
  })
  public Object createSampleInstance(SampleDescriptor sd) {
    final Class clazz = sd.getSampleClass();

    List<AbstractModule> modules = new ArrayList<AbstractModule>(2);

    for (RequirementDescriptor rd : sd.getRequirements()) {
      if (rd instanceof RequireSampleHostDescriptor) {
        for (SampleHostInstance shi : hostInstances) {
          if (shi.getDescriptor().getId().equals(((RequireSampleHostDescriptor) rd).getName()))
            modules.add(shi.getSampleHostModule());
        }
      }
    }

    final Injector injector = ROOT_INJECTOR.createChildInjector(modules);
    System.err.println(injector);
    return injector.getInstance(clazz);
  }

  private final class RootCategory extends Category {
    private RootCategory(List<Category> children) {
      super("ROOT", "Samples", "", children);
    }

    public boolean install(SampleDescriptor sample) {
      if (!super.install(sample)) {
        // the root category is some kind of "Fallback"
        samples.add(sample);
      }
      return true;
    }
  }

  interface Processor<R, P> {
    R process(P in) throws Exception;
  }

  public List<SampleDescriptor> getSamples() {
    final List<SampleDescriptor> res = load("META-INF/levigo-samples.xml",
        new Processor<List<SampleDescriptor>, InputStream>() {
          final List<SampleDescriptor> sds = new ArrayList<SampleDescriptor>();

          @Override
          public List<SampleDescriptor> process(InputStream in) throws Exception {
            LevigoSamples samples = LevigoSamples.read(in);
            sds.addAll(samples.getSamples());
            return sds;
          }
        });
    if (res != null)
      return res;
    return Collections.emptyList();
  }

  private <R> R load(final String resourceName, final Processor<R, InputStream> processor) {
    Enumeration<URL> resources;
    try {
      resources = getClass().getClassLoader().getResources(resourceName);
    } catch (IOException e1) {
      e1.printStackTrace();
      return null;
    }

    R res = null;
    while (resources.hasMoreElements()) {
      URL url = resources.nextElement();

      try {
        final InputStream is = url.openStream();
        res = processor.process(is);

      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return res;
  }

  public Category getCategories() {
    return categories;
  }
}
