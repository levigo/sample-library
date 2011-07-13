package com.levigo.showcase.ui.pages;

import com.levigo.showcase.Category;
import com.levigo.showcase.SampleInstance;

public interface DetailsPageFactory {
  
  DetailsPage createCategoryPage(Category category);
  DetailsPage createSamplePage(SampleInstance sample);
}
