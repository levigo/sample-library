package com.levigo.jadice.showcase.tool3;

import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.swing.tool.ToolManager;

public class SimpleHelloWorldToolSample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public SimpleHelloWorldToolSample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }

  public void start() {

    final ToolManager tm = basicJadicePanel.getPageView().getToolManager();

    tm.register(SimpleHelloWorldTool.class, true);

  }

  public void stop() {
    final ToolManager tm = basicJadicePanel.getPageView().getToolManager();

    tm.deregister(SimpleHelloWorldTool.class);

  }

}
