package com.levigo.samplelib.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class TreeWalker<T> {

  /**
   * Generates an empty {@link Iterable}.
   * @param <T>
   * @return
   */
  public static <T> Iterable<T> empty() {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new Iterator<T>() {
          @Override
          public boolean hasNext() {
            return false;
          }

          @Override
          public T next() {
            throw new NoSuchElementException();
          }

          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  public enum TraversalOrder {
    /**
     * Parents will be processed before traversing the children
     */
    PARENT_FIRST,
    /**
     * children will be processed before the parent
     */
    CHILD_FIRST
  }

  public static final class TraversalContext {
    /* default */int depth;

    public int getDepth() {
      return depth;
    }
  }

  /**
   * Load an {@link Iterable} representing all children, or an empty {@link Iterable} using {@link #empty()}.
   * @param parent the parent object for which the children shall be loaded.
   * @return an {@link Iterable} representing all children, or an empty {@link Iterable} using {@link #empty()} 
   */
  protected abstract Iterable<T> loadChildren(T parent, TraversalContext context);

  protected abstract void process(T obj, TraversalContext context);

  public void walk(T obj) {
    walk(obj, null);
  }

  public void walk(T obj, TraversalOrder order) {
    doWalk(obj, order, new TraversalContext());
  }

  private void doWalk(T obj, TraversalOrder order, TraversalContext context) {
    if (obj == null) {
      throw new IllegalArgumentException("obj must not be null");
    }
    if (order == null)
      order = TraversalOrder.PARENT_FIRST;

    if (context == null)
      context = new TraversalContext();

    if (order == TraversalOrder.PARENT_FIRST)
      process(obj, context);


    context.depth++;
    for (T child : loadChildren(obj, context))
      doWalk(child, order, context);
    context.depth--;

    if (order == TraversalOrder.CHILD_FIRST)
      process(obj, context);
  }
}
