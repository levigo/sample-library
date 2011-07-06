package com.levigo.showcase.descriptors;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryDescriptor {
  @XmlElement(required =true)
  private String id;
  @XmlElement(required =true)
  private String name;
  @XmlElement
  private String description;
  @XmlElement(name = "category")
  private List<CategoryDescriptor> children;

  public String getDescription() {
    return description;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<CategoryDescriptor> getChildren() {
    if (children == null)
      children = new ArrayList<CategoryDescriptor>();
    return children;
  }
}
