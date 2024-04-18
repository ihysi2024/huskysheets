package view;

import java.util.List;

import controller.ViewFeatures;

/**
 * Represents the view that allows an event to be scheduled as early as possible given
 * user time constraints.
 */
public interface IScheduleView {

  /**
   * Opens up the current user's schedule.
   */
  void openScheduleView();

  /**
   * Closes the current schedule view.
   */
  void closeScheduleView();

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */

  void addFeatures(ViewFeatures features);


  /**
   * Get the user's input for the event name.
   * @return a String of the event name
   */

  String getEventNameInput();

  /**
   * Get the user's input for the event location.
   * @return a String of the location
   */
  String getLocationInput();

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the location
   */
  List<String> getUsersInput();

  /**
   * Observes the user's input for whether the event is online or not
   * @return whether the event is online
   */
  boolean getOnline();

  /**
   * Observes how long the event is.
   * @return the length of the event
   */
  int getDuration();

  /**
   * Empties the fields in the panel for the user to enter their own inputs.
   * @param host the host of the event.
   */
  void resetSchedulePanel(String host);

  /**
   * Display errors that may arise should the user provide invalid inputs to the panel.
   */

  void displayError();
}
