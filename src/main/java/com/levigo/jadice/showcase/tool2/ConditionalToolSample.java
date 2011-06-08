package com.levigo.jadice.showcase.tool2;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.StartStopSample;

public class ConditionalToolSample extends AbstractSample implements StartStopSample {

  public ConditionalToolSample() {
    super("Tool, conditional Marker", ConditionalMarkerTool.class, ConditionalToolSample.class);
  }

  @Override
  public void start(BasicJadicePanel basicJadicePanel) {
    basicJadicePanel.getPageView().getToolManager().register(ConditionalMarkerTool.class, true);
  }

  @Override
  public void stop(BasicJadicePanel basicJadicePanel) {
    basicJadicePanel.getPageView().getToolManager().deregister(ConditionalMarkerTool.class);
  }

  @Override
  public boolean isStarted(BasicJadicePanel basicJadicePanel) {
    return basicJadicePanel.getPageView().getToolManager().getCompatibleTools(ConditionalMarkerTool.class).size() > 0;
  }

}
