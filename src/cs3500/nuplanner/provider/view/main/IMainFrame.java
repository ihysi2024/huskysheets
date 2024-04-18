package cs3500.nuplanner.provider.view.main;


import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.view.event.IEventFrame;
import cs3500.nuplanner.provider.view.schedule.IScheduleFrame;

/**
 * Represents the main view that holds the calendar in order to view user schedules.
 */
public interface IMainFrame {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void setVisible();

  /**
   * adds features to the MainFrame view.
   * @param features features from the controller.
   */
  void addFeatures(IFeatures features);

  /**
   * Brings up an error dialog box containing the message:
   * "Error: One or more event fields are empty."
   */
  void emptyInputDialog();

  /**
   * Brings up an error dialog box containing the message:
   * "Error: One or more times are invalid. Formatting is as follows: XX:xx"
   */
  void invalidTimeDialog();

  /**
   * Brings up an error dialog box containing the given exception message.
   */
  void exceptionDialog(Exception ex);

  /**
   * Sets the reference to the current event frame open.
   * @param frame The current event frame that is opened.
   */
  void setEventFrame(IEventFrame frame);

  /**
   * Sets the reference to the current schedule frame open.
   * @param frame The current schedule frame that is opened.
   */
  void setScheduleFrame(IScheduleFrame frame);

  /**
   * Calls closeEventFrame for the current EventFrame object.
   */
  void closeEventFrame();

  /**
   * Calls closeScheduleFrame for the current ScheduleFrame object.
   */
  void closeScheduleFrame();
}
