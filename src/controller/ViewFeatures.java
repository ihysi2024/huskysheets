package controller;

import java.util.HashMap;

import model.IEvent;
import model.ITime;

/**
 * Represents the features that the user is allowed to use to interact
 * with the calendar system.
 */

public interface ViewFeatures {

  /**
   * Delegate to the view of the schedule to close the view.
   */

  void closePlannerView();

  void closeScheduleView();

  /**
   * Delegate to the view of the schedule to open the view.
   */

  void openPlannerView();

  void openScheduleView();
  /**
   * Delegate to the view of the event to open the view.
   */

  void openEventView();


  /**
   * Delegate to the view of the event and create a new event.
   */
  void createEvent();

  /**
   * Delegate to the view of the event and store the opened event's information.
   * @return a Hashmap of String content tags to String[] content values.
   */

  HashMap<String, String[]> storeEvent();

  /**
   * Delegate to the view of the event to take in a current event as a map of its contents
   * and modify it.
   * @param event a String of content tags to a String[] of content values
   */
  void modifyEvent(IEvent event);


  /**
   * Delegates to the view fo the even to populate the fields in the panel
   * with the event information given.
   * @param event event that should be contained in the panel.
   */

  void populateEvent(IEvent event);

  /**
   * Delegate to the view of the schedule to display the schedule of the user
   * with the same given name.
   * @param userName name to cross-reference with set of users in the system.
   */

  void selectUserSchedule(String userName);

  /**
   * Delegate to the view of the event to close the event view.
   */

  void closeEventView();

  /**
   * Delegate to the view of the schedule to set who the current user is.
   */

  void setCurrentUser();

  /**
   * Determine the event occurring at the given time. Useful for user mouse events in the
   * schedule view for interacting with the correct existing event.
   * @param timeOfEvent time of event the controller is searching for
   * @return an event at the given time.
   */

  IEvent findEvent(ITime timeOfEvent);

  /**
   * Delegates to the model to remove the given event from the relevant schedules
   * depending on the current user.
   * @param eventToRemove event that the model should remove.
   */

  void removeEvent(IEvent eventToRemove);

  /**
   * Delegates to the view of the event to create empty fields in the panel.
   *
   * @param host host of the event
   */

  void resetPanelView(String host);

  /**
   * Delegate to the view of the schedule to add the calendar info to the planner system.
   */

  void addCalendar();

  /**
   * Delegate to the view of the schedule to save the calendar info to the planner system.
   */

  void saveCalendars();

  void scheduleEvent();

  void scheduleEventInPlanner();

}
