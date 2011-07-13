package com.levigo.showcase.ui;

import com.levigo.showcase.SampleInstance;

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
