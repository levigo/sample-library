package com.levigo.showcase.ui;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.levigo.showcase.Category;
import com.levigo.showcase.SampleInstance;

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
      result.addAll(Lists.transform(category.getSamples(), new Function<SampleInstance, Object>() {
        @Override
        public Object apply(SampleInstance input) {
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

  public Category getCategory() {
    return category;
  }
}
