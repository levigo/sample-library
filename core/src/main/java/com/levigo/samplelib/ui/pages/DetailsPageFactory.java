package com.levigo.samplelib.ui.pages;

import com.levigo.samplelib.Category;
import com.levigo.samplelib.SampleInstance;

public interface DetailsPageFactory {
  
  DetailsPage createCategoryPage(Category category);
  DetailsPage createSamplePage(SampleInstance sample);
}
