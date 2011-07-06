package com.levigo.showcase.ui;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.levigo.showcase.SampleManager;

public class SampleTreeModel implements TreeModel {

  private final CategoryTreeNode rootNode;

  public SampleTreeModel(SampleManager manager) {
    rootNode = new CategoryTreeNode(manager.getCategories());
  }

  @Override
  public Object getRoot() {
    return rootNode;
  }

  @Override
  public Object getChild(Object parent, int index) {
    if (parent instanceof CategoryTreeNode) {
      return ((CategoryTreeNode) parent).getChildren().get(index);
    }
    return null;
  }

  @Override
  public int getChildCount(Object parent) {
    if (parent instanceof CategoryTreeNode) {
      return ((CategoryTreeNode) parent).getChildren().size();
    }
    return 0;
  }

  @Override
  public boolean isLeaf(Object node) {
    return !(node instanceof CategoryTreeNode);
  }

  @Override
  public void valueForPathChanged(TreePath path, Object newValue) {

  }

  @Override
  public int getIndexOfChild(Object parent, Object child) {
    if (parent instanceof CategoryTreeNode) {
      return ((CategoryTreeNode) parent).getChildren().indexOf(child);
    }
    return -1;
  }

  @Override
  public void addTreeModelListener(TreeModelListener l) {

  }

  @Override
  public void removeTreeModelListener(TreeModelListener l) {

  }

}
