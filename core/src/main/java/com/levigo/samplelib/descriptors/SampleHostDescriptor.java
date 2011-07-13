package com.levigo.samplelib.descriptors;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

@XmlAccessorType(XmlAccessType.FIELD)
public class SampleHostDescriptor {
  @XmlElement
  protected String id;
  
  @XmlElementWrapper(name = "requires")
  @XmlElements({
    @XmlElement(name = "require-sample-host", type = RequireSampleHostDescriptor.class)
  })
  protected List<RequirementDescriptor> requirements;
  
  @XmlElementWrapper(name="provides")
  @XmlElement(name="provide")
  protected List<ProviderDescriptor> providers;
  
  public String getId() {
    return id;
  }
  public List<ProviderDescriptor> getProviders() {
    if(providers == null)
      providers = new ArrayList<ProviderDescriptor>();
    return providers;
  }
  
  public List<RequirementDescriptor> getRequirements() {
    if(requirements == null)
      requirements = new ArrayList<RequirementDescriptor>();
    return requirements;
  }
  
  public void setId(String id) {
    this.id = id;
  }
}
