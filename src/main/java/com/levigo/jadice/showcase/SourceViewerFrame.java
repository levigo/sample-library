package com.levigo.jadice.showcase;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

public class SourceViewerFrame extends JFrame {

  public SourceViewerFrame(Class<?>... classes) {

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    getContentPane().setLayout(new BorderLayout());

    final JTabbedPane tabbedPane = new JTabbedPane();
    getContentPane().add(tabbedPane);


    for (Class<?> c : classes) {

      final JEditorPane editorPane = new JEditorPane();
      tabbedPane.addTab(c.getSimpleName(), new JScrollPane(editorPane));
      
      editorPane.setEditable(false);
      editorPane.setContentType("text/java");
      editorPane.setText(resolveClassSource(c));

    }

  }

  private String resolveClassSource(final Class<?> c) {
    try {
      return CharStreams.toString(CharStreams.newReaderSupplier(new InputSupplier<InputStream>() {

        @Override
        public InputStream getInput() throws IOException {
          final String filename = '/' + c.getName().replace('.', '/') + ".java";
          return SourceViewerFrame.class.getResourceAsStream(filename);
        }
      }, Charset.defaultCharset()));
    } catch (IOException e) {
      e.printStackTrace();
      return "FAILED TO LOAD SOURCE CODE FOR: " + c.getName();
    }
  }
}
