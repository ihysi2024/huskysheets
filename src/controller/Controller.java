package controller;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;

import model.PlannerSystem;
import view.IEventView;
import strategies.IScheduleStrategy;
import view.IScheduleTextView;
import view.IPlannerView;
import view.IScheduleView;

/**
 * Controller to delegate client-based decisions to the event view, planner view, and
 * scheduling event view. The controller also takes in a scheduling strategy
 * that is one of "anytime" or "workhours" to signify which time constraints
 * the controller can allow the client to schedule events automatically to.
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
   * @param strategy way in which a calendar can automatically schedule an event.
   */
  public Controller(PlannerSystem model, IScheduleStrategy strategy) {
    this.strategy = strategy;
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

  /**
   * Initializes the view of scheduling an event.
   * @param v view to visualize
   */
  public void setScheduleView(IScheduleView v) {
    scheduleView = v;
    scheduleView.addFeatures(this);
  }

  /**
   * Initializes the textual view of the planner system.
   * @param v view to visualize.
   */
  public void setTextView(IScheduleTextView v) {
    scheduleTextView = v;
  }


  /**
   * Allow the user to schedule an event at the earliest possible time within the constraints
   * detailed in the command line arguments.
   */
  @Override
  public void scheduleEventInPlanner() {
    IUser user = plannerView.getCurrentUser();
    try {
      List<ITime> times = this.strategy.scheduleEvent(user, scheduleView.getDuration());
      if (times == null || times.isEmpty()) {
        JOptionPane.showMessageDialog((Component) eventView,
                "Unable to find enough time in schedule for this event",
                "ERROR", JOptionPane.ERROR_MESSAGE);
      }
      IEvent eventToAdd = new Event(scheduleView.getEventNameInput(),
              times.get(0), times.get(1),
              scheduleView.getOnline(),
              scheduleView.getLocationInput(),
              scheduleView.getUsersInput());

      scheduleTextView.eventToString(eventToAdd);
      model.addEventForRelevantUsers(eventToAdd);
      this.openPlannerView();
    }
    catch (IllegalArgumentException | IndexOutOfBoundsException ignored) {

    }
  }

  /**
   * Delegates to the Schedule View to displays the errors in a dialog box if the user
   * has tried to schedule an event with invalid inputs.
   */
  @Override
  public void displayScheduleErrors() {
    scheduleView.displayError();
  }

  /**
   * Listen to user input and visualize the game.
   */
  public void goLaunchPlanner() {
    this.plannerView.addClickListener(this);
    this.plannerView.display(true);
  }

  /**
   * Delegate to the planner view to stop visualizing the planner.
   */
  @Override
  public void closePlannerView() {
    plannerView.closePlannerView();
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
  public void openEventView(String host) {
    eventView.openEvent(host);
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
   */

  public void resetPanelView() {
    eventView.resetPanel();
  }


  /**
   * Delegates to the view of the scheduled event to create empty fields in the panel.
   * @param host of the event.
   */
  public void resetSchedulePanelView(String host) {
    scheduleView.resetSchedulePanel(host);
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
   * Delegate to the view of the planner to display the schedule of the user
   * with the same given name.
   * @param userName name to cross-reference with set of users in the system.
   */
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
   * @param oldEvent original event
   * @param newEvent updated event
   */

  public void modifyEvent(IEvent oldEvent, IEvent newEvent) {
    try {
      model.modifyEvent(oldEvent, newEvent);
      plannerView.displayUserSchedule(plannerView.getCurrentUser().getName());
    }
    catch (IllegalArgumentException | NullPointerException ignored) {
      JOptionPane.showMessageDialog((Component) eventView,
              "Error in modifying event: given event not part of system",
              "ERROR", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Delegates to the model to remove the given event from the relevant schedules
   * depending on the current user.
   * @param eventToRemove event that the model should remove.
   */
  public void removeEvent(IEvent eventToRemove) {

    try {
        model.removeEventForRelevantUsers(eventToRemove, this.plannerView.getCurrentUser());
    }
    catch (NullPointerException | IllegalArgumentException ignored) {

    }


    /*
        System.out.println("tryin to remove event: " + eventToRemove.getEventName());
    try {
      ITime startTime = eventToRemove.getStartTime();
      IEvent userEventAtStartTime =
              plannerView.getCurrentUser().getSchedule().eventOccurring(startTime);
      if ((eventToRemove.getStartTime().compareTimes(userEventAtStartTime.getStartTime()) == 0)
        && (eventToRemove.getEndTime().compareTimes(userEventAtStartTime.getEndTime()) == 0)) {
        model.removeEventForRelevantUsers(eventToRemove, this.plannerView.getCurrentUser());
        this.openPlannerView();
      }
      else {
        JOptionPane.showMessageDialog((Component) eventView,
                "Event to be removed is not part of schedule",
                "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
      catch (NullPointerException | IllegalArgumentException ignored) {
      System.out.println("Error in removing event2: Given event not part of system, check inputs");

    }
     */

  }

  /**
   * Delegate to the view of the event and create a new event.
   */

  // is casting okay here, and should this throw an error if event overlaps with any other schedule?? assumption that we're making
  @Override
  public void createEvent() {
    IEvent event = eventView.createEvent();
    if (event != null) {
      try {
        model.addEventForRelevantUsers(event);
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog((Component) plannerView,
                "Event conflicts with 1+ user schedules",
                "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
    this.openPlannerView();
  }

  /**
   * Visualize the panel that allows users to automatically schedule an event.
   */

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
    String filePath = plannerView.addCalendarInfo();
    if (!filePath.isEmpty()) {
      model.importScheduleFromXML(filePath);
    }
    int numUsers = model.getUsers().size();
    String newUserName = model.getUsers().get(numUsers - 1).getName();
   // model.r
    plannerView.addUserToDropdown(newUserName);
    this.openScheduleView();
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

  /**
   * Display errors that arise when user provides invalid inputs when creating an event.
   */
  public void displayEventCreateErrors() {
    eventView.displayCreateError();
  }

  /**
   * Display errors that arise when user provides invalid inputs when removing an event.
   * @param eventToRemove event the user is trying to remove.
   */
  public void displayEventRemoveErrors(Map<String, String[]> eventToRemove) {
    eventView.displayRemoveError(eventToRemove);
  }

  /**
   * Display errors that arise when user provides invalid inputs when modifying an event.
   */

  public void displayEventModifyErrors() {
    eventView.displayModifyError(plannerView.getCurrentUser());
  }

}
