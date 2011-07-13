package com.levigo.showcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.levigo.showcase.controllers.AbstractController;
import com.levigo.showcase.controllers.ExecutionController;
import com.levigo.showcase.controllers.StartStopController;
import com.levigo.showcase.descriptors.ControllerDescriptor;
import com.levigo.showcase.descriptors.ExecutionControllerDescriptor;
import com.levigo.showcase.descriptors.SampleDescriptor;
import com.levigo.showcase.descriptors.StartStopControllerDescriptor;

public class SampleInstance {
  private final SampleManager manager;
  private final SampleDescriptor descriptor;
  private final List<AbstractController> controllers;

  public SampleInstance(SampleManager manager, SampleDescriptor descriptor) {
    super();
    this.manager = manager;
    this.descriptor = descriptor;

    // FIXME that should be done later and with better error handling

    final Object si = manager.createSampleInstance(descriptor);

    final List<AbstractController> temp = new ArrayList<AbstractController>(descriptor.getControllers().size());

    for (ControllerDescriptor desc : descriptor.getControllers()) {
      if (desc instanceof ExecutionControllerDescriptor) {

        temp.add(ExecutionController.create(si, (ExecutionControllerDescriptor) desc));

      } else if (desc instanceof StartStopControllerDescriptor) {

        temp.add(StartStopController.create(si, (StartStopControllerDescriptor) desc));
      }
    }

    this.controllers = Collections.unmodifiableList(temp);
  }

  public SampleDescriptor getDescriptor() {
    return descriptor;
  }

  public SampleManager getManager() {
    return manager;
  }

  public List<AbstractController> getControllers() {
    return controllers;
  }

}
