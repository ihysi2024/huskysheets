package view;

import java.util.HashMap;

import controller.ViewFeatures;
import model.IEvent;

/**
 * Frame for the event window.
 */
public interface IEventView {

  /**
   * Allow the user to interact with the calendar through the features present
   * in the event view.
   * @param features functionality that the user has access to through the event view.
   */
  void addFeatures(ViewFeatures features);

  /**
   * Close the event view so it stops being visible.
   */
  void closeEvent();

  /**
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   * @param event event to visualize in the event panel.
   */
  void populateEventContents(IEvent event);

  /**
   * Get the user's input for the event name.
   * @return a String[] of the event name
   */
  String[] getEventNameInput();

  /**
   * Get the user's input for the event time.
   * @return a String[] of the event time
   */
  String[] getTimeInput();

  /**
   * Get the user's input for the event location.
   * @return a String[] of the location
   */
  String[] getLocationInput();

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the location
   */
  String[] getUsersInput();

  /**
   * Modify an event with the user's new input to the event panel.
   * @param event represents the updated event
   */
  void modifyEvent(IEvent event);

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   *
   * @param host host of the event
   */
  void resetPanel(String host);

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   */
  void openEvent();

  /**
   * Store the current event's inputs as a map of String -> String[]
   * Useful for modifying an event with the current panel inputs.
   * @return a map of strings to string[]
   */
  HashMap<String, String[]> storeOpenedEventMap();

  /**
   * Store the user's input as an event that is added to their schedule.
   * @return the event that was created, null otherwise
   */
  IEvent createEvent();

  /**
   * Updates list of users in event view.
   */
  void updateUserList();
}
