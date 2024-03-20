package view;

import java.io.IOException;

import model.PlannerSystem;
import model.User;

/**
 * Represents a textual view of the planner system.
 * Generate a text view of all users (and their schedules) in the planner system to an appendable.
 */
public class ScheduleTextView implements IScheduleTextView {

  PlannerSystem plannerSystem;
  Appendable appendable;

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
    for (User user: this.plannerSystem.getUsers()) {
      planner.append(user.userToString() + "\n");
    }
    return planner.toString();
  }

  /**
   * Appends the text view to an output that can be read.
   * @throws IOException if the planner system text view cannot be properly generated
   */
  public void renderPlanner() throws IOException {
    this.appendable.append(this.plannerSystemString());
  }

}
