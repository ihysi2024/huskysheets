package cs3500.nuplanner.provider.view.mainView;

/**
 * Represents the interface for the GridPanel that implements the grid in the event menu.
 */
public interface IGridPanel {

  /**
   * Set up the panel to handle clicks in this view.
   *
   */
  void addClickListener();

  void setCurrentUser(String user);
}
