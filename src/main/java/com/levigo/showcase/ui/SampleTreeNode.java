package com.levigo.showcase.ui;

import com.levigo.showcase.descriptors.SampleDescriptor;

public class SampleTreeNode {

  private final SampleDescriptor sampleDescriptor;

  public SampleTreeNode(SampleDescriptor sampleDescriptor) {
    this.sampleDescriptor = sampleDescriptor;
  }

  public String toString() {
    return sampleDescriptor.getName();
  }
}
