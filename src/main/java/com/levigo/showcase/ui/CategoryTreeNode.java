package com.levigo.showcase.ui;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.levigo.showcase.Category;
import com.levigo.showcase.descriptors.SampleDescriptor;

public class CategoryTreeNode {
  private final Category category;


  public CategoryTreeNode(Category category) {
    super();
    this.category = category;
  }

  private List<Object> children;

  public List<Object> getChildren() {
    if (children == null) {
      List<Object> result = Lists.newArrayList();
      result.addAll(Lists.transform(category.getCategories(), new Function<Category, Object>() {
        @Override
        public Object apply(Category input) {
          return new CategoryTreeNode(input);
        }
      }));
      result.addAll(Lists.transform(category.getSamples(), new Function<SampleDescriptor, Object>() {
        @Override
        public Object apply(SampleDescriptor input) {
          return new SampleTreeNode(input);
        }
      }));
      children = result;
    }
    return children;
  }

  @Override
  public String toString() {
    return category.getName();
  }
}
