package view.schedule;

import javax.swing.JFrame;

import controller.IFeatures;
import model.IReadOnlyCentralSystem;

/**
 * Represents the ScheduleFrame that manipulates the name, location, duration and list
 * of users for scheduling an event.
 */
public class ScheduleFrame extends JFrame implements IScheduleFrame {
  private SchedulePanel schedulePanel;
  private int frameWidth = 600;
  private int frameHeight = 400;

  /**
   * constructor that sets up the ScheduleFrame with a model.
   * @param model read-only model instance.
   */
  public ScheduleFrame(IReadOnlyCentralSystem model) {
    this.setSize(frameWidth, frameHeight);
    schedulePanel = new SchedulePanel(model, frameWidth);
    this.add(schedulePanel);
  }

  /**
   * for mock testing.
   * @param model read-only model instance.
   * @param schedulePanel SchedulePanelMock instance to test.
   */
  public ScheduleFrame(IReadOnlyCentralSystem model, SchedulePanelMock schedulePanel) {
    this.schedulePanel = schedulePanel;
    this.add(schedulePanel);
  }

  @Override
  public void setVisible() {
    setVisible(true);
  }

  @Override
  public void closeScheduleFrame() {
    this.dispose();
  }

  @Override
  public void addFeatures(IFeatures features) {
    schedulePanel.addButtonPanelFeatures(features);
  }
}
