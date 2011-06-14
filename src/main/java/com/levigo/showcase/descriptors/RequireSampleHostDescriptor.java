package com.levigo.showcase.descriptors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RequireSampleHostDescriptor extends RequirementDescriptor {

  public RequireSampleHostDescriptor() {
  }


  public RequireSampleHostDescriptor(String name) {
    super();
    this.name = name;
  }

  @XmlAttribute
  protected String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
