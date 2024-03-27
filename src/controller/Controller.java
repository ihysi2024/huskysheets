package controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Schedule;
import model.Time;
import model.User;
import view.EventPanel;
import view.IEventView;
import view.IScheduleView;


/**
 * Controller to control the functions of the Simon Game.
 */
public class Controller implements ViewFeatures {
  private final PlannerSystem model;

  private IScheduleView scheduleView;

  private IEventView eventView;

  /**
   * Creates an instance of a Calendar Controller that responds to user input via mouse clicks
   * or button presses.
   * @param model model of calendar implementations reflected by controller
   */

  public Controller(PlannerSystem model) {
    this.model = model;
  }

  /**
   * Initialize the view of the Schedule.
   * @param v the view to visualize.
   */

  public void setScheduleView(IScheduleView v) {
    scheduleView = v;
    scheduleView.addFeatures(this);
  }

  /**
   * Initialize the view of the event.
   * @param v view to visualize
   */

  public void setEventView(IEventView v) {
    eventView = v;
    eventView.addFeatures(this);
  }

  /**
   * Listen to user input and visualize the game.
   */
  public void goPlayGame() {

    this.scheduleView.addClickListener(this);
    this.scheduleView.display(true);

  }

  /**
   * Delegate to the view of the schedule to close the view.
   */
  public void closeScheduleView() {

    scheduleView.closeScheduleView();

  }

  /**
   * Delegate to the view of the event to open the view.
   */

  @Override
  public void openEventView() {

    eventView.openEvent();

  }

  /**
   * Delegate to the view of the schedule to display the schedule of the user
   * with the same given name.
   * @param userName name to cross-reference with set of users in the system.
   */

  public void selectUserSchedule(String userName) {
    for (IUser user: model.getUsers()) {
      if (user.getName().equals(userName)) {
        scheduleView.displayUserSchedule(user);
      }
    }
  }

  /**
   * Delegate to the view of the event to close the event view.
   */

  public void closeEventView() {
    eventView.closeEvent();
  }

  /**
   * Delegate to the view of the schedule to set who the current user is.
   */
  public void setCurrentUser() {
    scheduleView.setCurrentUser();
  }

  /**
   * Determine the event occurring at the given time. Useful for user mouse events in the
   * schedule view for interacting with the correct existing event.
   * @param timeOfEvent time of event the controller is searching for
   * @return an event at the given time.
   */

  public IEvent findEvent(ITime timeOfEvent) {
    for (IUser user: this.model.getUsers()) {
      IEvent tempEvent = model.retrieveUserScheduleAtTime(user, timeOfEvent);
      if (tempEvent != null) {
        return tempEvent;
      }

    }
    return null;
  }

  /**
   * Delegates to the view of the event to create empty fields in the panel.
   */

  public void resetPanelView() {
    eventView.resetPanel();
  }

  /**
   * Delegates to the view fo the even to populate the fields in the panel
   * with the event information given.
   * @param event event that should be contained in the panel.
   */

  public void populateEvent(IEvent event) {
    eventView.populateEventContents(event);
  }

  /**
   * Delegate to the view of the schedule to open the view.
   */
  public void openScheduleView() {
    scheduleView.openScheduleView(model);
  }

  /**
   * Delegate to the view of the event to take in a current event as a map of its contents
   * and modify it.
   * @param eventMap a String of content tags to a String[] of content values
   */

  public void modifyEvent(HashMap<String, String[]> eventMap) {
    eventView.modifyEvent(eventMap);
  }


  /**
   * Delegates to the model to remove the given event from the relevant schedules
   * depending on the current user.
   * @param eventToRemove event that the model should remove.
   */

  public void removeEvent(IEvent eventToRemove) {
    System.out.println(scheduleView.getCurrentUser().getName());
    System.out.println("Remove event: ");
    System.out.println(scheduleView.getCurrentUser().getName());
    System.out.println("Remove event***: " + eventToRemove.eventToString());
    model.removeEventForRelevantUsers(eventToRemove, scheduleView.getCurrentUser());

  }

  /**
   * Delegate to the view of the event and create a new event.
   */
  @Override
  public void createEvent() {
    eventView.createEvent();
  }

  /**
   * Delegate to the view of the event and store the opened event's information.
   * @return a Hashmap of String content tags to String[] content values.
   */
  public HashMap<String, String[]> storeEvent() {
    return eventView.storeOpenedEventMap();
  }


  /**
   * Delegate to the view of the schedule to add the calendar info to the planner system.
   */
  @Override
  public void addCalendar() {
    scheduleView.addCalendarInfo();
  }

  /**
   * Delegate to the view of the schedule to save the calendar info to the planner system.
   */
  @Override
  public void saveCalendars() {
    scheduleView.saveCalendarInfo();
  }

}
