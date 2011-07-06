package com.levigo.showcase.descriptors;

import javax.inject.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderDescriptor {
  @SuppressWarnings("rawtypes")
  @XmlAttribute(name = "with")
  protected Class<? extends Provider> provider;

  @XmlAttribute(name = "class")
  protected Class<?> providedClass;

  @SuppressWarnings("rawtypes")
  public Class<? extends Provider> getProvider() {
    return provider;
  }

  @SuppressWarnings("rawtypes")
  public void setProvider(Class<? extends Provider> providerClass) {
    this.provider = providerClass;
  }

  public Class<?> getProvidedClass() {
    return providedClass;
  }

  public void setProvidedClass(Class<?> providedClass) {
    this.providedClass = providedClass;
  }

}
