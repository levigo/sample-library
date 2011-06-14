package com.levigo.showcase.controllers;

import java.lang.reflect.Method;

import com.levigo.showcase.descriptors.ControllerDescriptor.InvocationThread;
import com.levigo.showcase.descriptors.ExecutionControllerDescriptor;

public class ExecutionController extends AbstractController {

  private final Object instance;

  private final Method m;


  public static ExecutionController create(Object instance, ExecutionControllerDescriptor descriptor) {
    Method m = getMethod(instance, descriptor.getMethod());
    return new ExecutionController(instance, m, descriptor.getInvocationThread());

  }

  public ExecutionController(Object instance, Method m, InvocationThread invocationThread) {
    super(invocationThread);
    this.instance = instance;
    this.m = m;
  }


  public void execute() {
    doInvoke(instance, m);
  }
}
