package view.main.panel;

import controller.IFeatures;

/**
 * Represents the interface for the GridPanel that implements the grid in the event menu.
 */
public interface IGridPanel {

  /**
   * Set up the panel to handle clicks in this view.
   *
   * @param features features from the controller.
   */
  void addGridPanelFeatures(IFeatures features);

  /**
   * Sets the current user when the schedule is changed.
   * @param user New user that has become the current user.
   */
  void setCurrentUser(String user);

  /**
   * Sets the host color toggle to the opposite of what it was before (if host color is on,
   * toggles it off, and if host color is off, toggles it on).
   */
  void setHostColorToggle();
}
