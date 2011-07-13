package com.levigo.samplelib;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.levigo.samplelib.SampleHostInstance.SampleHostModule;
import com.levigo.samplelib.api.SampleLibraryContext;
import com.levigo.samplelib.descriptors.CategoryDescriptor;
import com.levigo.samplelib.descriptors.LevigoSampleSystem;
import com.levigo.samplelib.descriptors.LevigoSamples;
import com.levigo.samplelib.descriptors.RequireSampleHostDescriptor;
import com.levigo.samplelib.descriptors.RequirementDescriptor;
import com.levigo.samplelib.descriptors.SampleDescriptor;
import com.levigo.samplelib.descriptors.SampleHostDescriptor;
import com.levigo.samplelib.util.TreeWalker;

public class SampleManager {

  private static final Logger LOG = Logger.getLogger(SampleManager.class.getName());

  private static final class CategoryDescriptorDebugTreeWalker extends TreeWalker<CategoryDescriptor> {
    private final Logger log;

    public CategoryDescriptorDebugTreeWalker(Logger log) {
      super();
      this.log = log;
    }

    @Override
    protected Iterable<CategoryDescriptor> loadChildren(CategoryDescriptor parent, TraversalContext context) {
      return parent.getChildren() != null ? parent.getChildren() : TreeWalker.<CategoryDescriptor> empty();
    }

    @Override
    protected void process(CategoryDescriptor obj, TraversalContext context) {
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < context.getDepth(); i++)
        b.append("  ");
      b.append(obj.getId());
      
      log.fine(b.toString());
    }
  }

  // FIXME should that really be static?`
  private static final Injector ROOT_INJECTOR = Guice.createInjector(new AbstractModule() {
    @Override
    protected void configure() {
      bind(SampleLibraryContext.class).toInstance(new SampleLibraryContext());
    }
  });

  private Category categories;

  private List<SampleHostInstance> hostInstances;

  public SampleManager() {
    super();

    // load the category tree
    // LevigoSampleHosts.read(is)

    hostInstances = new ArrayList<SampleHostInstance>();
    load("META-INF/levigo-sample-system.xml", new Processor<Void, InputStream>() {

      @Override
      public Void process(InputStream in) throws Exception {
        final LevigoSampleSystem system = LevigoSampleSystem.read(in);

        for (final SampleHostDescriptor shd : system.getSampleHosts()) {

          hostInstances.add(new SampleHostInstance(shd, new SampleHostModule(shd)));

        }

        return null;
      }
    });

    final List<Category> allCategories = new ArrayList<Category>();
    List<SampleInstance> tmp = load("META-INF/levigo-samples.xml", new Processor<List<SampleInstance>, InputStream>() {
      final List<SampleInstance> sds = new ArrayList<SampleInstance>();

      @Override
      public List<SampleInstance> process(InputStream in) throws Exception {
        LevigoSamples samples = LevigoSamples.read(in);


        // FIXME category collision
        for (CategoryDescriptor desc : samples.getCategories()) {
          if (LOG.isLoggable(Level.FINE))
            new CategoryDescriptorDebugTreeWalker(LOG).walk(desc);
          allCategories.add(Category.create(desc));
        }

        for (SampleDescriptor desc : samples.getSamples()) {
          sds.add(new SampleInstance(SampleManager.this, desc));
        }
        return sds;
      }
    });

    if (tmp == null)
      tmp = new ArrayList<SampleInstance>();

    samples = tmp;

    categories = new RootCategory(allCategories);

    for (SampleInstance si : samples) {
      categories.install(si);
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

    public boolean install(SampleInstance sample) {
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

  private final List<SampleInstance> samples;

  public List<SampleInstance> getSamples() {
    return samples;
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
        // FIXME better exception handling!
        e.printStackTrace();
      }
    }

    return res;
  }

  public Category getCategories() {
    return categories;
  }
}
