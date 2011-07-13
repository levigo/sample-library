package com.levigo.showcase.ui.pages;

import javax.swing.JTextPane;

import com.levigo.showcase.Category;

public class CategoryDetailsPage extends CaptionDetailsPage {

  private static final long serialVersionUID = 1L;

  public CategoryDetailsPage(Category category) {

    setHeader(category.getName());

    JTextPane descriptionTextPane = new JTextPane();
    descriptionTextPane.setEditable(false);
    descriptionTextPane.setText(category.getDescription().trim());

    setBody(descriptionTextPane);

  }

}