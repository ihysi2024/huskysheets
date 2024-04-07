package controller;


import java.awt.*;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import model.IEvent;
import model.ITime;
import model.IUser;

import model.PlannerSystem;
import view.IEventView;
import view.IScheduleStrategy;
import view.IScheduleTextView;
import view.IPlannerView;
import view.IScheduleView;


/**
 * Controller to control the functions of the Simon Game.
 */
public class Controller implements ViewFeatures {

  private IScheduleStrategy strategy;
  private IPlannerView plannerView;

  private IEventView eventView;
  private IScheduleView scheduleView;

  private IScheduleTextView scheduleTextView;

  private final PlannerSystem model;

  /**
   * Creates an instance of a Calendar Controller that responds to user input via mouse clicks
   * or button presses.
   * @param model model of calendar implementations reflected by controller
   */
  public Controller(PlannerSystem model, IScheduleStrategy strategy) {
    this.strategy = strategy;
    // creates a controller using given model
    this.model = model;
  }

  /**
   * Initialize the view of the Schedule.
   * @param v the view to visualize.
   */
  public void setPlannerView(IPlannerView v) {
    plannerView = v;
    plannerView.addFeatures(this);
  }

  /**
   * Initialize the view of the event.
   * @param v view to visualize
   */
  public void setEventView(IEventView v) {
    eventView = v;
    eventView.addFeatures(this);
  }

  public void setScheduleView(IScheduleView v) {
    scheduleView = v;
    scheduleView.addFeatures(this);
  }

  public void scheduleEvent() {
    // delegate to the text view
  }

  @Override
  public void scheduleEventInPlanner() {
    IUser user = plannerView.getCurrentUser();
    List<ITime> times = this.strategy.scheduleEvent(user, scheduleView.getDuration());
    scheduleView.addScheduleAtTime(user, times.get(0), times.get(1));
  }

  public void setTextView(IScheduleTextView v) {
    scheduleTextView = v;
  }

  /**
   * Listen to user input and visualize the game.
   */
  public void goLaunchPlanner() {
    this.plannerView.addClickListener(this);
    this.plannerView.display(true);
  }

  @Override
  public void closePlannerView() {
    plannerView.closePlannerView();
  }

  /**
   * Delegate to the view of the schedule to close the view.
   */
  public void closeScheduleView() {
    System.out.println("here");
    scheduleView.closeScheduleView();
  }

  /**
   * Delegate to the view of the event to open the view.
   */
  @Override
  public void openEventView() {
   // System.out.println("opening an event");
    eventView.openEvent();
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
    plannerView.setCurrentUser();
  }

  /**
   * Determine the event occurring at the given time. Useful for user mouse events in the
   * schedule view for interacting with the correct existing event.
   * @param timeOfEvent time of event the controller is searching for
   * @return an event at the given time.
   */
  public IEvent findEvent(ITime timeOfEvent) {
    return plannerView.findEventAtTime(timeOfEvent);
  }

  /**
   * Delegates to the view of the event to create empty fields in the panel.
   *
   * @param host of the event
   */
  public void resetPanelView(String host) {
    eventView.resetPanel(host);
  }

  /**
   * Delegates to the view fo the even to populate the fields in the panel
   * with the event information given.
   * @param event event that should be contained in the panel.
   */
  public void populateEvent(IEvent event) {
    eventView.populateEventContents(event);
  }

  @Override
  public void selectUserSchedule(String userName) {
    plannerView.displayUserSchedule(userName);
  }

  /**
   * Delegate to the view of the schedule to open the view.
   */
  public void openPlannerView() {
    plannerView.openPlannerView();
  }


  /**
   * Delegate to the view of the event to take in a current event as a map of its contents
   * and modify it.
   * @param event a String of content tags to a String[] of content values
   */
  public void modifyEvent(IEvent event) {
    try {
      eventView.modifyEvent(event);
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
              plannerView.getCurrentUser().getSchedule().eventOccurring(startTime);
      if (eventToRemove.equals(userEventAtStartTime)) {
        model.removeEventForRelevantUsers(eventToRemove, this.plannerView.getCurrentUser());
        this.openPlannerView();
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
    this.openPlannerView();
  }

  public void openScheduleView() {
    scheduleView.openScheduleView();
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
   // scheduleView.addCalendarInfo();
    String filePath = plannerView.addCalendarInfo();
    if (!filePath.isEmpty()) {
      model.importScheduleFromXML(filePath);
    //  model.getUsers();
    //  for (IUser user : model.getUsers()) {
    //    System.out.println("curr user: " + user.getName());
    //  }
     // scheduleView.getCurrentUser();
    //  model.exportScheduleAsXML(filePath);
    }
    int numUsers = model.getUsers().size();
    String newUserName = model.getUsers().get(numUsers - 1).getName();
    plannerView.addUserToDropdown(newUserName);
    //   scheduleView.add(newUserName);
    this.openPlannerView();
  }

  /**
   * Delegate to the view of the schedule to save the calendar info to the planner system.
   */
  @Override
  public void saveCalendars() {
    String filePath = plannerView.saveCalendarInfo();
    if (!filePath.isEmpty()) {
      model.exportScheduleAsXML(filePath);
    }
    this.openPlannerView();
  }

}
