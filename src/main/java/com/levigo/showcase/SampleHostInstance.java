package com.levigo.showcase;

import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.levigo.showcase.descriptors.ProviderDescriptor;
import com.levigo.showcase.descriptors.SampleHostDescriptor;

public class SampleHostInstance {

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
      for (ProviderDescriptor pd : shd.getProviders()) {

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
