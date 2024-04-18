package view.schedule;

import controller.IFeatures;

/**
 * Interface for the schedule frame that allows user to schedule an event.
 */
public interface IScheduleFrame {

  /**
   * Make the view visible to start the game session.
   */
  void setVisible();

  /**
   * disposes of the current schedule frame.
   */
  void closeScheduleFrame();

  /**
   * adds features from the controller to be called in the ScheduleFrame.
   * @param features IFeatures from the controller.
   */
  void addFeatures(IFeatures features);
}
