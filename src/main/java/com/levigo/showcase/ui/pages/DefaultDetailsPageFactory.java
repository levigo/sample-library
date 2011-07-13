package com.levigo.showcase.ui.pages;

import com.levigo.showcase.Category;
import com.levigo.showcase.SampleInstance;

public class DefaultDetailsPageFactory implements DetailsPageFactory {

  @Override
  public DetailsPage createCategoryPage(Category category) {
    return new CategoryDetailsPage(category);
  }

  @Override
  public DetailsPage createSamplePage(SampleInstance sample) {
    return new SampleDetailsPage(sample);
  }

}
