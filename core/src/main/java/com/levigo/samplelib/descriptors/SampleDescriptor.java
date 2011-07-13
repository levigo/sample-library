package com.levigo.samplelib.descriptors;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "sample")
@XmlAccessorType(XmlAccessType.FIELD)
public class SampleDescriptor {

  @XmlElement(required = true)
  protected String name;
  @XmlElement
  protected String description;

  @SuppressWarnings("rawtypes")
  @XmlElement(name = "class", required = true)
  protected Class clazz;

  @XmlElementWrapper(name = "requires")
  @XmlElements({
    @XmlElement(name = "require-sample-host", type = RequireSampleHostDescriptor.class)
  })
  protected List<RequirementDescriptor> requirements;

  @XmlElementWrapper(name="categories")
  @XmlElement(name="category")
  protected List<String> categories;
  
  @XmlElements({
      @XmlElement(name = "start-stop-controller", type = StartStopControllerDescriptor.class),
      @XmlElement(name = "execution-controller", type = ExecutionControllerDescriptor.class)
  })
  @XmlElementWrapper(name = "controllers")
  protected List<ControllerDescriptor> controllers;

  @SuppressWarnings("rawtypes")
  @XmlElement(name = "class")
  @XmlElementWrapper(name = "classes")
  protected List<Class> classes;

  @XmlElement(name = "resource")
  @XmlElementWrapper(name = "resources")
  protected List<ResourceDescriptor> resources;

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  @SuppressWarnings("rawtypes")
  public Class getSampleClass() {
    return clazz;
  }

  @SuppressWarnings("rawtypes")
  public void setSampleClass(Class clazz) {
    this.clazz = clazz;
  }

  @SuppressWarnings("rawtypes")
  public List<Class> getClasses() {
    if (classes == null)
      classes = new ArrayList<Class>();
    return classes;
  }


  public List<ControllerDescriptor> getControllers() {
    if (controllers == null)
      controllers = new ArrayList<ControllerDescriptor>();
    return controllers;
  }

  public List<ResourceDescriptor> getResources() {
    if (resources == null)
      resources = new ArrayList<ResourceDescriptor>();
    return resources;
  }
  
  public List<RequirementDescriptor> getRequirements() {
    if(requirements == null)
      requirements = new ArrayList<RequirementDescriptor>();
    return requirements;
  }
  
  public List<String> getCategories() {
    if(categories == null)
      categories = new ArrayList<String>();
    return categories;
  }
}
