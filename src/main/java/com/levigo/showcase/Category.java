package com.levigo.showcase;

import java.util.ArrayList;
import java.util.List;

import com.levigo.showcase.descriptors.CategoryDescriptor;
import com.levigo.showcase.descriptors.SampleDescriptor;

public class Category {

  public static Category create(CategoryDescriptor descriptor) {

    List<Category> categories = new ArrayList<Category>();
    // no locking needed as we're the only ones writing to that list
    for (CategoryDescriptor cd : descriptor.getChildren()) {
      categories.add(create(cd));
    }

    return new Category(descriptor.getId(), descriptor.getName(), descriptor.getDescription(), categories);
  }

  private final String id;
  private final String name;
  private final String description;

  private final List<Category> categories;
  // FIXME SampleInstance instead?
  protected final List<SampleDescriptor> samples;

  public Category(String id, String name, String description, List<Category> children) {
    super();
    this.id = id;
    this.name = name;
    this.description = description;
    this.categories = children;
    this.samples = new ArrayList<SampleDescriptor>();
  }

  public List<Category> getCategories() {
    return categories;
  }

  public String getDescription() {
    return description;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean install(SampleDescriptor sample) {
    if (sample.getCategories().contains(getId())) {
      samples.add(sample);
      return true;
    }
    // try our children

    for (Category c : categories) {
      if (c.install(sample))
        return true;
    }
    return false;
  }

  public List<SampleDescriptor> getSamples() {
    return samples;
  }
}
