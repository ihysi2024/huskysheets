package controller;

import java.util.HashMap;
import model.IEvent;
import model.ITime;
import model.PlannerSystem;
import view.IEventView;
import view.IScheduleTextView;
import view.IScheduleView;

/**
 * Controller to control the functions of the Simon Game.
 */
public class Controller implements ViewFeatures {

  private IScheduleView scheduleView;

  private IEventView eventView;

  private IScheduleTextView scheduleTextView;

  /**
   * Creates an instance of a Calendar Controller that responds to user input via mouse clicks
   * or button presses.
   * @param model model of calendar implementations reflected by controller
   */
  public Controller(PlannerSystem model) {
    // creates a controller using given model
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

    scheduleView.displayUserSchedule(userName);

    /*
    for (IUser user: model.getUsers()) {
      if (user.getName().equals(userName)) {
        scheduleView.displayUserSchedule(user);
      }
    }
     */

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

  /**
   * Delegate to the view of the schedule to open the view.
   */
  public void openScheduleView() {
    scheduleView.openScheduleView();
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
              scheduleView.getCurrentUser().getSchedule().eventOccurring(startTime);
      System.out.println("user event at time: "
              + scheduleTextView.eventToString(userEventAtStartTime));
      System.out.println("event to remove: "
              + scheduleTextView.eventToString(eventToRemove));
      if (eventToRemove.equals(userEventAtStartTime)) {
        System.out.println("Remove event: ");
        System.out.println("User from which event is being removed: "
                + scheduleView.getCurrentUser().getName());
        System.out.println(scheduleTextView.eventToString(eventToRemove));
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
