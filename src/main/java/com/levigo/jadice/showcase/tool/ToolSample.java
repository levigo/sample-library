package com.levigo.jadice.showcase.tool;

import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;

public class ToolSample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public ToolSample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }

  public void start() {
    basicJadicePanel.getPageView().getToolManager().register(MarkerTool.class, true);
  }

  public void stop() {
    basicJadicePanel.getPageView().getToolManager().deregister(MarkerTool.class);
  }

}
