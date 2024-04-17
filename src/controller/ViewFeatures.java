package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.IEvent;
import model.ITime;

/**
 * Represents the features that the user is allowed to use to interact
 * with the calendar system.
 */

public interface ViewFeatures {

  /**
   * Delegate to the view of the planner to close the view.
   */

  void closePlannerView();

  /**
   * Delegate to the view of the schedule to stop visualizing the schedule event panel.
   */
  void closeScheduleView();

  /**
   * Delegate to the view of the schedule to open the view.
   */

  void openPlannerView();

  /**
   * Visualize the panel that allows users to automatically schedule an event.
   */

  void openScheduleView();

  /**
   * Delegate to the view of the event to open the view.
   */
  void openEventView(String host);

  /**
   * Delegate to the view of the event to open the view.
   */

  void openBlankEventView(String host);

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
   * Modify an event with the user's new input to the event panel.
   * @param oldEvent represents the updated event
   * @param newEvent represents the updated event
   */
  void modifyEvent(IEvent oldEvent, IEvent newEvent);


  /**
   * Delegates to the view for the event to populate the fields in the panel
   * with the event information given.
   * @param event event that should be contained in the panel.
   */

  void populateEvent(IEvent event);

  /**
   * Delegate to the view of the planner to display the schedule of the user
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
   * Delegates to the view of the scheduled event to create empty fields in the panel.
   * @param host of the event.
   */

  void resetSchedulePanelView(String host);

  /**
   * Delegates to the view of the event to create empty fields in the panel.
   */

  void resetPanelView();

  /**
   * Delegate to the view of the schedule to add the calendar info to the planner system.
   */

  void addCalendar();

  /**
   * Delegate to the view of the schedule to save the calendar info to the planner system.
   */

  void saveCalendars();

  /**
   * Allow the user to schedule an event at the earliest possible time within the constraints
   * detailed in the command line arguments.
   */

  void scheduleEventInPlanner();

  /**
   * Delegates to the Schedule View to displays the errors in a dialog box if the user
   * has tried to schedule an event with invalid inputs.
   */

  void displayScheduleErrors();

  /**
   * Display errors that arise when user provides invalid inputs when creating an event.
   */

  void displayEventCreateErrors();

  /**
   * Display errors that arise when user provides invalid inputs when removing an event.
   * @param eventToRemove event the user is trying to remove.
   */

  void displayEventRemoveErrors(Map<String, String[]> eventToRemove);

  /**
   * Display errors that arise when user provides invalid inputs when modifying an event.
   */

  void displayEventModifyErrors();
}
