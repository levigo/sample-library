package com.levigo.jadice.showcase;

import com.levigo.jadice.demo.BasicJadicePanel;

public interface StartStopSample extends Sample {
  void start(BasicJadicePanel basicJadicePanel);

  void stop(BasicJadicePanel basicJadicePanel);

  boolean isStarted(BasicJadicePanel basicJadicePanel);
}
