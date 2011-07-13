package com.levigo.showcase.controllers;

import java.lang.reflect.Method;

import com.levigo.showcase.descriptors.StartStopControllerDescriptor;

public class StartStopController extends AbstractController {

  public static StartStopController create(Object instance, StartStopControllerDescriptor descriptor) {

    Method startMethod = getMethod(instance, descriptor.getStartMethod());
    Method stopMethod = getMethod(instance, descriptor.getStopMethod());

    return new StartStopController(instance, startMethod, stopMethod, descriptor);
  }

  private final Object instance;
  private final Method startMethod;
  private final Method stopMethod;

  public StartStopController(Object instance, Method startMethod, Method stopMethod, StartStopControllerDescriptor descriptor) {
    super(descriptor);
    this.instance = instance;
    this.startMethod = startMethod;
    this.stopMethod = stopMethod;
  }

  
  public void start() {
    doInvoke(instance, startMethod);
  }
  public void stop() {
    doInvoke(instance, stopMethod);
  }
  
  @Override
  public StartStopControllerDescriptor getDescriptor() {
    return (StartStopControllerDescriptor) super.getDescriptor();
  }

}
