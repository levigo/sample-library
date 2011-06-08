package com.levigo.jadice.showcase;

/**
 * <pre>
 * Copyright (c) 2011, levigo holding gmbh.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  * Neither the name of levigo holding gmbh nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * </pre>
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jsyntaxpane.DefaultSyntaxKit;

import com.levigo.jadice.appbase.FileOpener;
import com.levigo.jadice.appbase.status.SnapInFactory;
import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.demo.SpacePanBehaviour;
import com.levigo.jadice.demo.UpdateWindowTitleContextListener;
import com.levigo.jadice.demo.annotation.AnnotationMenuContributor;
import com.levigo.jadice.demo.gestures.MouseGestureTool;
import com.levigo.jadice.document.ProductInformation;
import com.levigo.jadice.document.Selection;
import com.levigo.jadice.document.config.JadicePropertiesConfiguration;
import com.levigo.jadice.search.TextSelectionTool;
import com.levigo.jadice.swing.BasicViewer;
import com.levigo.jadice.swing.JadiceProductInformationSplash;
import com.levigo.jadice.swing.annotation.AnnotationTool;
import com.levigo.jadice.swing.dnd.ExportHandler;
import com.levigo.jadice.swing.dnd.RasterizedSelectionExportHandler;
import com.levigo.jadice.swing.dnd.SelectedTextExportHandler;
import com.levigo.jadice.swing.menu.ActionFactoryMenuContributor;
import com.levigo.jadice.swing.menu.MenuContributor;
import com.levigo.jadice.swing.pageview.AreaSelectionTool;
import com.levigo.jadice.swing.pageview.GalleryNavigationTool;
import com.levigo.jadice.swing.pageview.KeyboardNavigationTool;
import com.levigo.jadice.swing.pageview.MouseWheelTool;
import com.levigo.jadice.swing.pageview.MouseWheelZoomTool;
import com.levigo.jadice.swing.pageview.PanTool;
import com.levigo.jadice.swing.status.StatusBar;
import com.levigo.jadice.swing.thumbnailview.LightboxThumbnailTool;
import com.levigo.jadice.swing.tool.DefaultToolActivationPolicy;
import com.levigo.jadice.swing.tool.PopupMenuTool;
import com.levigo.jadice.swing.tool.ToolManager;
import com.levigo.jadice.swing.util.DebugActionCreationFailureLogger;
import com.levigo.util.swing.action.Context;
import com.levigo.util.swing.action.DefaultActionFactory;
import com.levigo.util.swing.action.DefaultCommandFactory;
import com.levigo.util.swing.action.DefaultMenuComponentFactory;

/**
 * <code>JadicePanel</code> is a demonstration how easy a viewer can be embedded into developers
 * environment.
 * 
 * @author <a href="mailto:c.koehler@levigo.de">Carolin Koehler </a>
 */
public class ShowcaseJadicePanel {
  private static final String JADICE_NAME = "levigo \u00ae  -  " + "jadice \u00ae viewer "
      + ProductInformation.getMajorVersion() + "." + ProductInformation.getMinorVersion();
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  private static void addMenuContributions(final PopupMenuTool popupMenuTool) {

    // the action factory with all swing viewer actions.
    final DefaultActionFactory viewerSwingActionFactory = //
    DefaultActionFactory.getInstance("/com/levigo/jadice/swing/resources/actions.properties");

    final List<MenuContributor> fixedContributors = popupMenuTool.getFixedContributors();

    // Group annotation (only visible during annotation editing)
    ActionFactoryMenuContributor mc = new AnnotationMenuContributor(viewerSwingActionFactory, "AnnoCTXAnnoDelete");
    mc.setGroup("annotation");
    fixedContributors.add(mc);

    // Group prodInfo
    mc = new ActionFactoryMenuContributor(viewerSwingActionFactory, "ProductInfoSplash");
    mc.setGroup("prodInfo");
    fixedContributors.add(mc);

    // Group pageexploring
    mc = new ActionFactoryMenuContributor(viewerSwingActionFactory, "FirstPage", "PreviousPage", "NextPage", "LastPage");
    mc.setGroup("pageexploring");
    fixedContributors.add(mc);

    // Group zoom
    mc = new ActionFactoryMenuContributor(viewerSwingActionFactory, "ZoomIn", "ZoomOut");
    mc.setGroup("zoom");
    fixedContributors.add(mc);

    // Group rotate
    mc = new ActionFactoryMenuContributor(viewerSwingActionFactory, "PageSpin270", "PageSpin90");
    mc.setGroup("rotate");
    fixedContributors.add(mc);
  }

  private static void configureAreaSelectionTool(final AreaSelectionTool areaSelectionTool, Context context) {
    final DefaultActionFactory viewerSwingActionFactory = //
    DefaultActionFactory.getInstance("/com/levigo/jadice/swing/resources/actions.properties");

    areaSelectionTool.getMenuContributors().add(new ActionFactoryMenuContributor(viewerSwingActionFactory, //
        "ZoomToSelection", //
        "CopyRasterizedSelection", //
        "CopySelectedText"));

    final List<ExportHandler<Selection>> exportHandlers = areaSelectionTool.getExportHandlers().getHandlers();

    exportHandlers.add(new RasterizedSelectionExportHandler());
    exportHandlers.add(new SelectedTextExportHandler());
  }

  private static void configureTextSelectionTool(final TextSelectionTool textSelectionTool, Context context) {
    final DefaultActionFactory viewerSwingActionFactory = //
    DefaultActionFactory.getInstance("/com/levigo/jadice/swing/resources/actions.properties");

    textSelectionTool.getMenuContributors().add(new ActionFactoryMenuContributor(viewerSwingActionFactory, //
        "CopySelectedText"));

    final List<ExportHandler<Selection>> exportHandlers = textSelectionTool.getExportHandlers().getHandlers();

    exportHandlers.add(new SelectedTextExportHandler());
  }

  /**
   * Initialize Jadice Panel GUI
   */
  public static void initialiseGUI(java.lang.String[] args) {

    DefaultSyntaxKit.initKit();

    // for debugging purposes propagate action and command creation errors/messages to logging
    // system
    DefaultMenuComponentFactory.addMenuComponentCreationListener(DebugActionCreationFailureLogger.getInstance());
    DefaultActionFactory.addActionCreationListener(DebugActionCreationFailureLogger.getInstance());
    DefaultCommandFactory.addCommandCreationListener(DebugActionCreationFailureLogger.getInstance());

    // Insert code to start the application here.
    final BasicJadicePanel mainViewerPanel = new BasicJadicePanel();
    final StatusBar snapInStatusBar = mainViewerPanel.getSnapInStatusBar();
    // the following 2 lines add the developer snap-ins in the status bar
    snapInStatusBar.add(SnapInFactory.createTaskServiceMonitorSnapIn());
    snapInStatusBar.add(SnapInFactory.createMemoryMonitorSnapIn());

    // Uncomment this line if the toggling sorter frame should be used instead
    // of the sorter toolbar
    mainViewerPanel.addSorterToolBar(BorderLayout.NORTH);
    // Uncomment this line if the info bar should not be added
    // mainViewerPanel.addRollingMessagePanel(BorderLayout.NORTH);

    // Uncomment this line if the viewer should not support file drop support
    final BasicViewer viewer = mainViewerPanel.getViewer();
    viewer.setDropTarget(new DropTarget(viewer, new FileOpener(viewer.getPageView())));

    final JFrame mainFrame = new JFrame(JADICE_NAME);
    mainFrame.setIconImage((Image) UIManager.get("Jadice.window.icon.image"));

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setContentPane(mainViewerPanel);
    final JMenuBar mBar = mainViewerPanel.getJadiceMenuBar();
    if (mBar != null)
      mainFrame.setJMenuBar(mBar);

    mainViewerPanel.getContext().addContextChangeListener(new UpdateWindowTitleContextListener(mainFrame, JADICE_NAME));

    final ToolManager tm = mainViewerPanel.getPageView().getToolManager();
    tm.register(MouseGestureTool.class, true);
    tm.register(TextSelectionTool.class, true);
    tm.register(AreaSelectionTool.class, true);
    tm.register(LightboxThumbnailTool.class, true);
    tm.register(GalleryNavigationTool.class, true);
    tm.register(MouseWheelZoomTool.class, true);
    tm.register(MouseWheelTool.class, true);
    tm.register(AnnotationTool.class, true);
    tm.register(PopupMenuTool.class, true);
    tm.register(KeyboardNavigationTool.class, true);
    tm.register(PanTool.class, true);
    tm.activate(PanTool.class);

    // add additional commands to the context Menu.
    addMenuContributions(tm.getTool(PopupMenuTool.class));

    // add actions to area- and text selection tool
    configureAreaSelectionTool(tm.getTool(AreaSelectionTool.class), mainViewerPanel.getContext());
    configureTextSelectionTool(tm.getTool(TextSelectionTool.class), mainViewerPanel.getContext());

    // enable the space pan behavior. This enabled the user to press and hold space to pan around.
    // Releasing will change the active tool to the tool that has been active before.
    final SpacePanBehaviour spacePanBehaviour = new SpacePanBehaviour();
    spacePanBehaviour.setDecoratedToolActivationPolicy(new DefaultToolActivationPolicy());
    spacePanBehaviour.install(mainViewerPanel.getPageView());

    mainFrame.pack();
    mainFrame.setVisible(true);

    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // make frame square, unless screen too small.
    mainFrame.setSize(Math.min(mainFrame.getSize().width, screenSize.width - 100),
        Math.min(mainFrame.getSize().width, screenSize.height - 100));
    mainFrame.validate();

    // center the window on the screen
    final Dimension size = mainFrame.getSize();
    mainFrame.setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);

    new JadiceProductInformationSplash(mainFrame, true);


    // create and open the showcase controller frame

    ControllerFrame cf = new ControllerFrame(mainViewerPanel);
    cf.pack();
    cf.setVisible(true);
  }

  /**
   * Starts the application.
   * 
   * @param args an array of command-line arguments
   */
  public static void main(final java.lang.String[] args) {

    // respect local system L&F
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (final Exception e) {
      // don't care
    }

    // Use default Jadice.properties from class path
    JadicePropertiesConfiguration.configure();

    // init gui just on edt!
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        initialiseGUI(args);
      }
    });
  }

}