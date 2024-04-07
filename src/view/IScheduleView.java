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

  void addScheduleAtTime(IUser user, ITime startTime, ITime endTime);

  int getDuration();

}
