package view;

import java.util.HashMap;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.IUser;
import model.ReadOnlyPlanner;
import model.User;

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
   * Display the view.
   * @param show whether to the display or not
   */

  void display(boolean show);

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
   * @param eventMap represents the updated event
   * @param model observational planner system to use.
   */

  void modifyEvent(HashMap<String, String[]> eventMap);

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   */

  void resetPanel();

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
   */

  void createEvent();
}
