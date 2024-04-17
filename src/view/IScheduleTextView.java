package view;

import java.io.IOException;

import model.IEvent;
import model.ISchedule;
import model.ITime;
import model.IUser;

/**
 * Represents the view of the planner system.
 */
public interface IScheduleTextView {


  String timeToString(ITime time);

  String scheduleToString(ISchedule schedule);

  String userToString(IUser user);

  String eventToString(IEvent event);
  /**
   * Generate a string text view of each user's schedule.
   * @return a string of every user's schedule
   * @throws IOException if stringbuilder can't append correctly
   */

  String plannerSystemString() throws IOException;

  /**
   * Appends the text view to an output that can be read.
   * @throws IOException if the planner system text view cannot be properly generated
   */
  void renderPlanner() throws IOException;

}
