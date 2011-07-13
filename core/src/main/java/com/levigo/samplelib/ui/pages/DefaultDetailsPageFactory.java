package com.levigo.samplelib.ui.pages;

import com.levigo.samplelib.Category;
import com.levigo.samplelib.SampleInstance;

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
