package com.levigo.samplelib.ui;

import com.levigo.samplelib.SampleInstance;

public class SampleTreeNode {

  private final SampleInstance sample;

  public SampleTreeNode(SampleInstance sampleDescriptor) {
    this.sample = sampleDescriptor;
  }

  public String toString() {
    return sample.getDescriptor().getName();
  }

  public SampleInstance getSample() {
    return sample;
  }
}
