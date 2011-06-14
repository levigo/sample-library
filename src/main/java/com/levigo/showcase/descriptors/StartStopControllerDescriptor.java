package com.levigo.showcase.descriptors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class StartStopControllerDescriptor extends ControllerDescriptor {



  @XmlAttribute(name = "start-method", required = true)
  protected String startMethod;
  @XmlAttribute(name = "stop-method", required = true)
  protected String stopMethod;

  public String getStartMethod() {
    return startMethod;
  }

  public String getStopMethod() {
    return stopMethod;
  }

  public void setStartMethod(String startMethod) {
    this.startMethod = startMethod;
  }

  public void setStopMethod(String stopMethod) {
    this.stopMethod = stopMethod;
  }

}
