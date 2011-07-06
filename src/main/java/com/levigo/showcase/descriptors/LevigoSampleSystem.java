package com.levigo.showcase.descriptors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

@XmlRootElement(name = "levigo-sample-system")
@XmlAccessorType(XmlAccessType.FIELD)
public class LevigoSampleSystem {
  @XmlElementWrapper(name = "categories")
  @XmlElement(name = "category", type = CategoryDescriptor.class)
  protected List<CategoryDescriptor> categories;
  
  @XmlElementWrapper(name="sample-hosts")
  @XmlElement(name="sample-host")
  protected List<SampleHostDescriptor> sampleHosts;

  public List<CategoryDescriptor> getCategories() {
    if (categories == null)
      categories = new ArrayList<CategoryDescriptor>();
    return categories;
  }

  public List<SampleHostDescriptor> getSampleHosts() {
    if (sampleHosts == null)
      sampleHosts = new ArrayList<SampleHostDescriptor>();
    return sampleHosts;
  }

  public static LevigoSampleSystem read(InputStream is) throws JAXBException {
    // FIXME this is not failsafe!
    final JAXBContext ctx = JAXBContext.newInstance(LevigoSampleSystem.class);

    final Unmarshaller u = ctx.createUnmarshaller();
    final JAXBElement<LevigoSampleSystem> res = u.unmarshal(new StreamSource(is), LevigoSampleSystem.class);
    return res.getValue();
  }

}
