package cs3500.nuplanner.provider.view.mainView;

import cs3500.nuplanner.provider.controller.IFeatures;

/**
 * Represents the JPanels for all the parts in the frame: GridPanel,
 * FilePanel, and bottomPanel.
 */
public interface IMainPanel {

  /**
   * Add features so that the controller can manipulate the filePanel.
   */
  void addFilePanelFeatures(IFeatures features);

  /**
   * Add features so that the controller can manipulate the GridPanel.
   */
  void addGridPanelFeatures(IFeatures features);

  /**
   * Add features so that the controller can manipulate the BottomPanel.
   */
  void addBottomPanelFeatures(IFeatures features);
}
