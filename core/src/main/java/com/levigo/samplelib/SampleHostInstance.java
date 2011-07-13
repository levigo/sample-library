package com.levigo.samplelib;

import java.util.logging.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.levigo.samplelib.descriptors.ProviderDescriptor;
import com.levigo.samplelib.descriptors.SampleHostDescriptor;

public class SampleHostInstance {
  private static final Logger LOG = Logger.getLogger(SampleHostDescriptor.class.getName());

  public static final class SampleHostModule extends AbstractModule {
    private final SampleHostDescriptor shd;

    public SampleHostModule(SampleHostDescriptor shd) {
      this.shd = shd;
    }

    // we have to suppress warnings here as we're configuring using reflection without
    // concrete knowledge about the types that we are using.
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    @Override
    protected void configure() {
      LOG.info("Configuring sample host '" + shd.getId() + '\'');
      // FIXME error detection and prevention
      for (ProviderDescriptor pd : shd.getProviders()) {
        LOG.info("\tClass '" + pd.getProvidedClass() + "' provided using '" + pd.getProvider()+"'");
        final AnnotatedBindingBuilder b = bind(pd.getProvidedClass());
        b.toProvider(pd.getProvider());
      }
    }
  }

  private final SampleHostDescriptor descriptor;
  private final SampleHostModule sampleHostModule;


  public SampleHostInstance(SampleHostDescriptor descriptor, SampleHostModule sampleHostModule) {
    this.descriptor = descriptor;
    this.sampleHostModule = sampleHostModule;
  }

  public SampleHostDescriptor getDescriptor() {
    return descriptor;
  }

  public SampleHostModule getSampleHostModule() {
    return sampleHostModule;
  }
}
