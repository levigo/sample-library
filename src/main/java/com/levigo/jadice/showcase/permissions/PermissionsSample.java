package com.levigo.jadice.showcase.permissions;

import java.awt.Component;

import javax.inject.Inject;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.UIDocument;
import com.levigo.jadice.document.auth.DocumentPermission;

public class PermissionsSample {

  private final BasicJadicePanel basicJadicePanel;

  @Inject
  public PermissionsSample(BasicJadicePanel basicJadicePanel) {
    super();
    this.basicJadicePanel = basicJadicePanel;
  }

  public void start() {
    final UIDocument<Component> doc = basicJadicePanel.getPageView().getDocument();

    if (doc != null) {
      doc.getPermissions().getPermissions().add(DocumentPermission.deny.reorderPages);
      doc.getPermissions().getPermissions().add(DocumentPermission.GRANT_ALL);
    }
  }

  public void stop() {
    final UIDocument<Component> doc = basicJadicePanel.getPageView().getDocument();

    if (doc != null) {
      doc.getPermissions().getPermissions().clear();
    }
  }

}
