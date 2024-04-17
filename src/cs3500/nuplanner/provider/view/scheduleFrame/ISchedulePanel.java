package cs3500.nuplanner.provider.view.scheduleFrame;

import cs3500.nuplanner.provider.controller.IFeatures;

/**
 * interface for the SchedulePanel that handles scheduling events given a strategy.
 */
public interface ISchedulePanel {

  /**
   * Sets up features related to the scheduling frame button panel--
   * specifically the schedule event button.
   *
   * @param features features from the controller.
   */
  void addButtonPanelFeatures(IFeatures features);
}
