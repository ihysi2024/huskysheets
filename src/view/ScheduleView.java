package view;

import javax.swing.*;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.ITime;

import model.IUser;
import model.ReadOnlyPlanner;
public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;

  /**
   * Creates a view of the Simon game.
   * @param model desired model to represent Simon game
   */
  public ScheduleView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.panel = new PlannerPanel(model);
    this.add(panel);
    this.setVisible(true);
    this.pack();
  }

  public void setCurrentUser() {
    panel.setCurrentUser();
  }

  public void closeScheduleView() {
    panel.closeScheduleView();
  }

  public IUser getCurrentUser() {
    return panel.getCurrentUser();
  }


  public void addFeatures(ViewFeatures features) {
    panel.addFeatures(features);
  }

  @Override
  public void addClickListener(ViewFeatures features) {
    panel.addClickListener(features);
  }

  @Override
  public void addCalendarInfo() {
    panel.addCalendarInfo();
  }

  @Override
  public void saveCalendarInfo() {
    panel.saveCalendarInfo();

  }

  @Override
  public IEvent findEventAtTime(ITime timeOfEvent) {
    return this.panel.findEventAtTime(timeOfEvent);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void openScheduleView() {
    panel.openScheduleView();
  }


  @Override
  public void displayUserSchedule(IUser userToShow) {
    panel.displayUserSchedule(userToShow);
  }

}
