package com.levigo.samplelib.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingUtilities;

import com.levigo.samplelib.descriptors.ControllerDescriptor;
import com.levigo.samplelib.descriptors.ControllerDescriptor.InvocationThread;

public abstract class AbstractController {
  protected static final class MethodInvocationRunnable implements Runnable {
    private final Object instance;
    private final Method m;

    protected MethodInvocationRunnable(Object instance, Method m) {
      this.instance = instance;
      this.m = m;
    }

    public void run() {
      // FIXME better error handling
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
  }

  protected static Method getMethod(Object instance, final String methodName) {
    try {
      return instance.getClass().getMethod(methodName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to use method: " + methodName + " of instance " + instance
          + " (class: " + instance.getClass().getName() + ")", e);
    }
  }

  protected void doInvoke(final Object instance, final Method m) {

    final Runnable r = new MethodInvocationRunnable(instance, m);

    if (getInvocationThread() == InvocationThread.EDT) {
      if (SwingUtilities.isEventDispatchThread()) {
        r.run();
      } else {
        SwingUtilities.invokeLater(r);
      }
    }

  }

  protected AbstractController(ControllerDescriptor descriptor) {
    super();
    this.descriptor = descriptor;
  }

  private final ControllerDescriptor descriptor;

  protected InvocationThread getInvocationThread() {
    return descriptor.getInvocationThread() != null ? descriptor.getInvocationThread() : InvocationThread.EDT;
  }

  public ControllerDescriptor getDescriptor() {
    return descriptor;
  }
}
