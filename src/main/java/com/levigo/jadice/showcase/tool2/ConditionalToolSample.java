package com.levigo.jadice.showcase.tool2;

import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;

public class ConditionalToolSample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public ConditionalToolSample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }

  public void start() {
    basicJadicePanel.getPageView().getToolManager().register(ConditionalMarkerTool.class, true);
  }

  public void stop() {
    basicJadicePanel.getPageView().getToolManager().deregister(ConditionalMarkerTool.class);
  }

}
