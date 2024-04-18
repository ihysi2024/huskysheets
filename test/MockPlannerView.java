import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.NUPlanner;
import model.PlannerSystem;
import model.Schedule;
import model.Time;
import model.User;
import view.IPlannerView;
import view.IScheduleTextView;
import view.PlannerView;
import view.ScheduleTextView;

/**
 * Represents the mock planner for a planner view to be implemented through a panel
 * and frame.
 */
public class MockPlannerView implements IPlannerView {

  private IScheduleTextView view;
  private StringBuilder out;
  private PlannerSystem model;

  /**
   * Represents a mock planner to show the controller is delegating correctly.
   * @param out string builder with messages
   * @param model model used by the planner system
   */

  public MockPlannerView(StringBuilder out, PlannerSystem model) {
    this.out = out;
    this.model = model;
    this.view = new ScheduleTextView(model, out);
  }

  /**
   * Shows or hides the frame as specified.
   *
   * @param show true if frame should be shown, false otherwise
   */
  public void display(boolean show) {
    out.delete(0, out.length());
    out.append("Displaying the planner view");
  }

  /**
   * Opens up the current user's schedule.
   */

  public void openPlannerView() {
    out.delete(0, out.length());
    out.append("Opening planner view");
  }

  /**
   * Sets the current user to what is selected in the appropriate button in the schedule view.
   */
  public String setCurrentUser() {
    out.append("Setting the current user");
    out.delete(0, out.length());
    return "";
  }

  /**
   * Retrieves the currently selected user.
   *
   * @return the currently selected user
   */

  public IUser getCurrentUser() {
    out.delete(0, out.length());
    out.append("Getting the current user");
    return new User("Prof. Lucia", new Schedule(new ArrayList<>(Arrays.asList(new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat"))),
            new Event("CS3500 Afternoon Lecture",
                    new Time(Time.Day.TUESDAY, 13, 35),
                    new Time(Time.Day.TUESDAY, 15, 15),
                    false,
                    "Churchill Hall 101",
                    new ArrayList<>(Arrays.asList("Prof. Lucia",
                            "Chat"))),
            new Event("Sleep",
                    new Time(Time.Day.FRIDAY, 18, 0),
                    new Time(Time.Day.SUNDAY, 12, 0),
                    true,
                    "Home",
                    new ArrayList<>(Arrays.asList("Prof. Lucia")))))));
  }

  /**
   * Adds a user to the drop-down menu.
   * @param userName user's name to add
   */

  public void addUserToDropdown(String userName) {
    out.delete(0, out.length());
    out.append("Add this user: \n");
    for (IUser user: model.getUsers()) {
      if (user.getName().equals(userName)) {
        out.append(view.userToString(user));
      }
    }
  }

  /**
   * Displays the desired user's schedule.
   * @param userToShow desired user schedule to show
   */

  public void displayUserSchedule(String userToShow) {
    out.delete(0, out.length());
    out.append("Displaying the schedule for \n");
    for (IUser user: model.getUsers()) {
      if (user.getName().equals(userToShow)) {
        out.append(view.userToString(user));
      }
    }
  }

  /**
   * Closes the current schedule view.
   */

  public void closePlannerView() {
    out.delete(0, out.length());
    out.append("Closing the planner view");
  }

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   * @param features available features
   */

  public void addFeatures(ViewFeatures features) {

  }

  /**
   * Handles the clicks in schedule panel. Specifically handles clicking on an event in the
   * schedule and opening up the corresponding view.
   * @param features features available
   */

  public void addClickListener(ViewFeatures features) {
    out.delete(0, out.length());
    out.append("Click listener");
  }

  /**
   * Allowing user to select an .xml file that contains the desired calendar.
   * Automatically starts in current directory.
   */

  public String addCalendarInfo() {
    out.delete(0, out.length());
    out.append("Adding calendar info from a filepath");
    return "";
  }

  /**
   * Allowing user to select a folder where they will export the user schedules.
   * Automatically starts in current directory.
   */

  public String saveCalendarInfo() {
    out.delete(0, out.length());
    out.append("Save calendar info to a filepath");
    return "";
  }

  /**
   * Finds the event that is occurring at the specified time. If two events start and end at the
   * same time, returns the earlier event.
   * @param timeOfEvent desired time to search at
   * @return Event occuring at that time, null otherwise
   */

  public IEvent findEventAtTime(ITime timeOfEvent) {
    out.delete(0, out.length());
    out.append("Finding event at time: " + view.timeToString(timeOfEvent));
    return new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));
  }
}
