package cs3500.nuplanner.provider.view.schedule;

import java.util.List;

import cs3500.nuplanner.provider.controller.IFeatures;

import cs3500.nuplanner.provider.view.schedule.SchedulePanel;
import cs3500.nuplanner.provider.view.schedule.ISchedulePanel;

/**
 *  Mock for testing SchedulePanel's use of controller features.
 */
public class SchedulePanelMock extends SchedulePanel implements ISchedulePanel {

  private IFeatures features;
  private StringBuilder log;

  /**
   * constructor to create a new StringBuilder log.
   */
  public SchedulePanelMock() {
    super();
    this.log = new StringBuilder();
  }

  @Override
  public void addButtonPanelFeatures(IFeatures features) {
    this.features = features;
  }

  /**
   * simulates the button press of Schedule Event in the schedule frame.
   */
  public void simulateScheduleButtonClicked(String name, boolean isOnline, String place,
                                            String durationMinutes, List<String> userIds) {
    features.scheduleEvent(name, isOnline, place, durationMinutes, userIds);
    log.append("Schedule event button clicked.\n");
  }

  /**
   * gets the log as a string.
   * @return returns a string.
   */
  public String getLog() {
    return log.toString();
  }
}
