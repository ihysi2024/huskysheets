package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import model.IEvent;
import model.ISchedule;
import model.ITime;
import model.IUser;
import model.PlannerSystem;
import model.Time;
import model.User;

/**
 * Represents a textual view of the planner system.
 * Generate a text view of all users (and their schedules) in the planner system to an appendable.
 */
public class ScheduleTextView implements IScheduleTextView {

  private PlannerSystem plannerSystem;
  private Appendable appendable;

  /**
   * Produce a view for this planner system for all users' schedules to be seen.
   * @param plannerSystem planner system to be viewed
   * @param appendable output view to show the planner system text view on
   */

  public ScheduleTextView(PlannerSystem plannerSystem, Appendable appendable) {
    this.plannerSystem = plannerSystem;
    this.appendable = appendable;
  }

  /**
   * Generate a string text view of each user's schedule.
   * @return a string of every user's schedule
   */
  public String plannerSystemString() {
    StringBuilder planner = new StringBuilder();
    for (IUser user: this.plannerSystem.getUsers()) {
      planner.append(this.userToString(user) + "\n");
    }
    return planner.toString();
  }

  /**
   * Show the user's schedule in a text format.
   * @return String representing the user's schedule
   */

  private String userToString(IUser user) {
    return "User: " + user.getName() + "\n" + this.scheduleToString(user.getSchedule());
  }

  /**
   * Convert the schedule to a string format for XML exportation.
   *
   * @return a string representing the schedule
   */
  private String scheduleToString(ISchedule schedule) {
    StringBuilder scheduleStr = new StringBuilder();
    HashMap<Time.Day, List<IEvent>> eventsMap = schedule.dayToEventsMappping();
    for (Time.Day dayOfTheWeek: eventsMap.keySet()) {
      scheduleStr.append(dayOfTheWeek.getDayString() + ": " + "\n");
      for (IEvent eventsInMap: eventsMap.get(dayOfTheWeek)) {

        scheduleStr.append(eventToString(eventsInMap) + " ".repeat(10) + "\n");
      }
    }
    return scheduleStr.toString();
  }

  /**
   * Formats this event as a String to easily interpret this event's contents and aid in testing.
   *
   * @return user-friendly representation of this event
   */
  private String eventToString(IEvent event) {
    return "name: " + event.getEventName() + "\n"
            + "time: " + this.timeToString(event.getStartTime()) + "->"
            + this.timeToString(event.getEndTime()) + "\n"
            + "location: " + event.getLocation() + "\n"
            + "online: " + event.getOnline() + "\n"
            + "users: " + String.join("\n", event.getUsers());
  }
  /**
   * Creates a String representation of this time in format "DATE: HH:MM".
   *
   * @return String rep of time in readable format
   */
  private String timeToString(ITime time) {
    String hoursStr = "" + time.getHours();
    String minutesStr = "" + time.getMinutes();
    if (time.getHours() < 10) {
      hoursStr = "0" + hoursStr;
    }
    if (time.getMinutes() < 10) {
      minutesStr = "0" + minutesStr;
    }
    return time.getDate().getDayString() + ": " + hoursStr + ":" + minutesStr;
  }

  /**
   * Appends the text view to an output that can be read.
   * @throws IOException if the planner system text view cannot be properly generated
   */
  public void renderPlanner() throws IOException {
    this.appendable.append(this.plannerSystemString());
  }

}
