package controller;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import model.IEvent;
import model.ITime;
import model.IUser;
import model.NUPlanner;
import model.PlannerSystem;
import view.IEventView;
import view.IScheduleTextView;
import view.IScheduleView;
import view.ScheduleTextView;

import static model.User.makeEvent;

/**
 * Controller to control the functions of the Simon Game.
 */
public class Controller implements ViewFeatures {

  private IScheduleView scheduleView;

  private IEventView eventView;

  private IScheduleTextView scheduleTextView;

  private final PlannerSystem model;

  /**
   * Creates an instance of a Calendar Controller that responds to user input via mouse clicks
   * or button presses.
   * @param model model of calendar implementations reflected by controller
   */
  public Controller(PlannerSystem model) {
    // creates a controller using given model
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

  public void setTextView(IScheduleTextView v) {
    scheduleTextView = v;
  }

  /**
   * Listen to user input and visualize the game.
   */
  public void goLaunchPlanner() {
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
   *
   * @param host host of this event
   */
  @Override
  public void openEventView(String host) {
    eventView.openEvent(host);
  }

  /**
   * Delegate to the view of the schedule to display the schedule of the user
   * with the same given name.
   * @param userName name to cross-reference with set of users in the system.
   */
  public void selectUserSchedule(String userName) {
    scheduleView.displayUserSchedule(userName);
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
    return scheduleView.findEventAtTime(timeOfEvent);
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
    scheduleView.openScheduleView();
  }

  /**
   * Delegate to the view of the event to take in a current event as a map of its contents
   * and modify it.
   * @param oldEvent original event
   * @param newEvent updated event
   */
  public void modifyEvent(IEvent oldEvent, IEvent newEvent) {
    try {
      model.modifyEvent(oldEvent, newEvent);
      scheduleView.displayUserSchedule(scheduleView.getCurrentUser().getName());
    }
    catch (IllegalArgumentException | NullPointerException exc) {
      throw new IllegalArgumentException("Error in modifying event: " +
              "given event not part of system.");
    }
  }

  /**
   * Delegates to the model to remove the given event from the relevant schedules
   * depending on the current user.
   * @param eventToRemove event that the model should remove.
   */
  public void removeEvent(IEvent eventToRemove) {
    try {
      ITime startTime = eventToRemove.getStartTime();
      IEvent userEventAtStartTime =
              scheduleView.getCurrentUser().getSchedule().eventOccurring(startTime);
      if (eventToRemove.equals(userEventAtStartTime)) {
        model.removeEventForRelevantUsers(eventToRemove, this.scheduleView.getCurrentUser());
        this.openScheduleView();
      }
      else {
        System.out.println("Error in removing event: Given event not part of system, check inputs");
      }
    }
      catch (NullPointerException | IllegalArgumentException ignored) {
      System.out.println("Error in removing event: Given event not part of system, check inputs");

    }
  }

  /**
   * Delegate to the view of the event and create a new event.
   */
  // is casting okay here, and should this throw an error if event overlaps with any other schedule?? assumption that we're making
  @Override
  public void createEvent() {
    IEvent event = eventView.createEvent();
    try {
      model.addEventForRelevantUsers(event);
    }
    catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog((Component) eventView, "Event conflicts with 1+ user schedules",
              "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    this.openScheduleView();
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
    String filePath = scheduleView.addCalendarInfo();
    if (!filePath.isEmpty()) {
      model.importScheduleFromXML(filePath);
    }
    int numUsers = model.getUsers().size();
    String newUserName = model.getUsers().get(numUsers - 1).getName();
    scheduleView.addUserToDropdown(newUserName);
    this.openScheduleView();
  }

  /**
   * Delegate to the view of the schedule to save the calendar info to the planner system.
   */
  @Override
  public void saveCalendars() {
    String filePath = scheduleView.saveCalendarInfo();
    if (!filePath.isEmpty()) {
      model.exportScheduleAsXML(filePath);
    }
    this.openScheduleView();
  }

}
