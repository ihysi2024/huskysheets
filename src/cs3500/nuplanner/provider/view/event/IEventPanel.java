package cs3500.nuplanner.provider.view.event;

import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.model.IEvent;

/**
 * The IEventPanel interface defines the necessary methods for interacting with
 * an event's detail panel. This includes setting up features for name, location,
 * start time, end time, user selection, and actions for modifying or removing an event,
 * as well as autoFill.
 */
public interface IEventPanel {

  /**
   * Sets up features related to the event's bottom panel, including actions
   * for the modify and remove event buttons.
   * @param features features from the controller.
   */
  void addBottomPanelFeatures(IFeatures features);

  /**
   * Autofills the event detail fields based on the provided event.
   * This is used to pre-populate the frame when editing an existing event.
   *
   * @param event The event to populate the frame.
   */
  void autoFill(IEvent event);
}
