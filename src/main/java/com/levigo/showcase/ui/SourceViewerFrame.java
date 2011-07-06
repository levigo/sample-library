package com.levigo.showcase.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import jsyntaxpane.DefaultSyntaxKit;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

public class SourceViewerFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final class ByteArraySourceInputSupplier implements NamedInputSupplier<String> {
    private final byte[] data;
    private final String name;

    private ByteArraySourceInputSupplier(byte[] data, String name) {
      this.data = data;
      this.name = name;
    }

    @Override
    public String getInput() throws IOException {
      return CharStreams.toString(CharStreams.newReaderSupplier(ByteStreams.newInputStreamSupplier(data),
          Charset.defaultCharset()));
    }

    @Override
    public String getName() {
      return name;
    }
  }

  public static interface NamedInputSupplier<T> extends InputSupplier<T> {
    String getName();
  }

  private static class StringInputSupplier implements NamedInputSupplier<String> {
    private final String input;
    private final String name;

    public StringInputSupplier(String name, String input) {
      super();
      this.name = name;
      this.input = input;
    }

    @Override
    public String getInput() throws IOException {
      return input;
    }

    @Override
    public String getName() {
      return name;
    }


  }

  @SuppressWarnings("unchecked")
  public static SourceViewerFrame forBinary(final String name, final byte[] data) {
    return new SourceViewerFrame(new ByteArraySourceInputSupplier(data, name));
  }

  @SuppressWarnings("unchecked")
  public static SourceViewerFrame forClasses(Class<?>... classes) {
    final NamedInputSupplier<String>[] inputs = new NamedInputSupplier[classes.length];
    for (int i = 0; i < inputs.length; i++) {
      inputs[i] = resolveClassSource(classes[i]);
    }

    return new SourceViewerFrame(inputs);
  }

  @SuppressWarnings("unchecked")
  public static SourceViewerFrame forString(final String name, final String data) {
    return new SourceViewerFrame(new StringInputSupplier(name, data));
  }

  private static NamedInputSupplier<String> resolveClassSource(final Class<?> c) {

    return new NamedInputSupplier<String>() {
      @Override
      public String getInput() throws IOException {
        return CharStreams.toString(CharStreams.newReaderSupplier(new InputSupplier<InputStream>() {

          @Override
          public InputStream getInput() throws IOException {
            final String filename = '/' + c.getName().replace('.', '/') + ".java";
            return SourceViewerFrame.class.getResourceAsStream(filename);
          }
        }, Charset.defaultCharset()));
      }

      @Override
      public String getName() {
        return c.getSimpleName();
      }
    };
  }

  public SourceViewerFrame(NamedInputSupplier<String>... input) {

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(800, 600);

    getContentPane().setLayout(new BorderLayout());

    final JTabbedPane tabbedPane = new JTabbedPane();
    getContentPane().add(tabbedPane);


    for (NamedInputSupplier<String> in : input) {

      final JEditorPane editorPane = new JEditorPane();
      tabbedPane.addTab(in.getName(), new JScrollPane(editorPane));

      editorPane.setEditable(false);
      if (in.getName().endsWith(".xml"))
        editorPane.setContentType("text/xml");
      else
        editorPane.setContentType("text/java");

      try {
        editorPane.setText(in.getInput());
      } catch (IOException e) {
        e.printStackTrace();
        editorPane.setText("FAILED TO LOAD SOURCE CODE");
      }

    }

  }
}
