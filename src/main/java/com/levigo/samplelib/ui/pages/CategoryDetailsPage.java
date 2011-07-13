package com.levigo.samplelib.ui.pages;

import javax.swing.JTextPane;

import com.levigo.samplelib.Category;

public class CategoryDetailsPage extends CaptionDetailsPage {

  private static final long serialVersionUID = 1L;

  public CategoryDetailsPage(Category category) {

    setHeader(category.getName());

    JTextPane descriptionTextPane = new JTextPane();
    descriptionTextPane.setEditable(false);
    descriptionTextPane.setContentType("text/html");
    descriptionTextPane.setText("<html>" + category.getDescription() + "</html>");

    setBody(descriptionTextPane);

  }

}
