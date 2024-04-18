package cs3500.nuplanner.provider.view.event;

import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.controller.IFeatures;

/**
 * Interface for the EventFrame that represents the view to enter event values.
 */
public interface IEventFrame {

  /**
   * Make the view visible to start the game session.
   */
  void setVisible();

  /**
   * Autofills an EventFrame with information from an IEvent.
   *
   * @param event IEvent, containing the event information.
   */
  void autofill(IEvent event);


  /**
   * Closes the current eventFrame.
   */
  void closeEventFrame();

  /**
   * adds features from the controller to be called in the EventFrame.
   * @param features IFeatures from the controller.
   */
  void addFeatures(IFeatures features);
}
