package view;

import java.util.HashMap;
import java.util.List;

import controller.ViewFeatures;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.Time;

public interface IScheduleView {
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
   * Get the user's input for the event name.
   * @return a String[] of the event name
   */
  String getEventNameInput();

  /**
   * Get the user's input for the event location.
   * @return a String[] of the location
   */
  String getLocationInput();

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the location
   */
  List<String> getUsersInput();

  boolean getOnline();

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

  void openScheduleView();

  void addScheduleAtTime(IUser user, ITime startTime, ITime endTime);

  int getDuration();

  void closeScheduleView();
}
