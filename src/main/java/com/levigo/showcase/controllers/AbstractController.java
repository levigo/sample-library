package com.levigo.showcase.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.levigo.showcase.descriptors.ControllerDescriptor.InvocationThread;

public abstract class AbstractController {
  protected static Method getMethod(Object instance, final String methodName) {
    try {
      return instance.getClass().getMethod(methodName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to use method: " + methodName + " of instance " + instance
          + " (class: " + instance.getClass().getName() + ")", e);
    }
  }

  protected void doInvoke(Object instance, Method m) {
    // FIXME better error handling
    // FIXME we should respect the invocation thread!
    try {
      m.invoke(instance);
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private final InvocationThread invocationThread;

  protected AbstractController(InvocationThread invocationThread) {
    super();
    this.invocationThread = invocationThread != null ? invocationThread : InvocationThread.EDT;
  }


}
