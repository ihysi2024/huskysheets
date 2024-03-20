package view;

import java.io.IOException;

/**
 * Represents the view of the planner system.
 */
public interface IScheduleTextView {

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
