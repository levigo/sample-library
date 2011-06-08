package com.levigo.jadice.showcase.tool;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.StartStopSample;

public class ToolSample extends AbstractSample implements StartStopSample {

  public ToolSample() {
    super("Tool, simple Marker", MarkerTool.class, ToolSample.class);
  }

  @Override
  public void start(BasicJadicePanel basicJadicePanel) {
    basicJadicePanel.getPageView().getToolManager().register(MarkerTool.class, true);
  }

  @Override
  public void stop(BasicJadicePanel basicJadicePanel) {
    basicJadicePanel.getPageView().getToolManager().deregister(MarkerTool.class);
  }

  @Override
  public boolean isStarted(BasicJadicePanel basicJadicePanel) {
    return basicJadicePanel.getPageView().getToolManager().getCompatibleTools(MarkerTool.class).size() > 0;
  }

}
