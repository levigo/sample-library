package com.levigo.jadice.showcase.permissions;

import java.awt.Component;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.UIDocument;
import com.levigo.jadice.document.auth.DocumentPermission;
import com.levigo.jadice.showcase.AbstractSample;
import com.levigo.jadice.showcase.StartStopSample;

public class PermissionsSample extends AbstractSample implements StartStopSample {

  public PermissionsSample() {
    super("Permissions, lock reordering", PermissionsSample.class);
  }

  private boolean started;

  @Override
  public void start(BasicJadicePanel basicJadicePanel) {
    final UIDocument<Component> doc = basicJadicePanel.getPageView().getDocument();

    if (doc != null) {
      doc.getPermissions().getPermissions().add(DocumentPermission.deny.reorderPages);
      doc.getPermissions().getPermissions().add(DocumentPermission.GRANT_ALL);
      started = true;
    }
  }

  @Override
  public void stop(BasicJadicePanel basicJadicePanel) {
    final UIDocument<Component> doc = basicJadicePanel.getPageView().getDocument();

    if (doc != null) {
      doc.getPermissions().getPermissions().clear();
      started = false;
    }
  }

  @Override
  public boolean isStarted(BasicJadicePanel basicJadicePanel) {
    return started;
  }

}
