package com.levigo.jadice.showcase;

import com.levigo.jadice.demo.BasicJadicePanel;

public interface StartStopSample extends Sample {
  boolean isStarted(BasicJadicePanel basicJadicePanel);

  void start(BasicJadicePanel basicJadicePanel);

  void stop(BasicJadicePanel basicJadicePanel);
}
