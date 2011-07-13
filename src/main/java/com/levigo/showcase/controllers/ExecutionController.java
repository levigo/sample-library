package com.levigo.showcase.controllers;

import java.lang.reflect.Method;

import com.levigo.showcase.descriptors.ExecutionControllerDescriptor;

public class ExecutionController extends AbstractController {

  private final Object instance;

  private final Method m;


  public static ExecutionController create(Object instance, ExecutionControllerDescriptor descriptor) {
    Method m = getMethod(instance, descriptor.getMethod());
    return new ExecutionController(instance, m, descriptor);

  }

  public ExecutionController(Object instance, Method m, ExecutionControllerDescriptor descriptor) {
    super(descriptor);
    this.instance = instance;
    this.m = m;
  }

  @Override
  public ExecutionControllerDescriptor getDescriptor() {
    return (ExecutionControllerDescriptor) super.getDescriptor();
  }

  public void execute() {
    doInvoke(instance, m);
  }
}
