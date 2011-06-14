package com.levigo.showcase.descriptors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ControllerDescriptor {
  @XmlEnum
  public static enum InvocationThread {
    EDT
  }

  @XmlAttribute(name = "invocation-thread")
  protected InvocationThread invocationThread;

  public void setInvocationThread(InvocationThread invocationThread) {
    this.invocationThread = invocationThread;
  }

  public InvocationThread getInvocationThread() {
    return invocationThread;
  }

  @XmlElement(required = true)
  protected String name;
  @XmlElement
  protected String description;


  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

}
