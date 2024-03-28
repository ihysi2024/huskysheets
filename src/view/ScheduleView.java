package view;

import java.util.ArrayList;

import javax.swing.JFrame;

import controller.ViewFeatures;
import model.IEvent;
import model.ITime;

import model.IUser;
import model.ReadOnlyPlanner;
import model.Schedule;
import model.User;

/**
<<<<<<< HEAD
 * Represents the view of the planner system through the GUI. Creates a frame for a schedule.
 * This frame will be used to draw the planner system within it,
 * including the schedules and the buttons related to its functionality.
 */
public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;

  /**
   * Creates a view of the planner system view.
   * @param model desired model to represent a planner system
   */
  public ScheduleView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new PlannerPanel(model);
    this.add(panel);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Sets the current user to what is selected in the appropriate button in the schedule view.
   */
  public void setCurrentUser() {
    panel.setCurrentUser();
  }

  /**
   * Closes the current schedule view.
   */
  public void closeScheduleView() {
    panel.closeScheduleView();
  }

  /**
   * Observational method to retrieve the user whose schedule is being displayed.
   * @return the user being interacted with.
   */
  public IUser getCurrentUser() {
    return panel.getCurrentUser();
  }

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */
  public void addFeatures(ViewFeatures features) {
    panel.addFeatures(features);
  }

  /**
   * Handles the clicks in schedule panel. Specifically handles clicking on an event in the
   * schedule and opening up the corresponding view.
   *
   * @param features features available
   */
  @Override
  public void addClickListener(ViewFeatures features) {
    panel.addClickListener(features);
  }

  /**
   * Allowing user to select an .xml file that contains the desired calendar.
   * Automatically starts in current directory.
   */
  @Override
  public void addCalendarInfo() {
    panel.addCalendarInfo();
  }

  /**
   * Allowing user to select a folder where they will export the user schedules.
   * Automatically starts in current directory.
   */
  @Override
  public void saveCalendarInfo() {
    panel.saveCalendarInfo();

  }

  /**
   * Finds the event that is occurring at the specified time. If two events start and end at the
   * same time, returns the earlier event.
   *
   * @param timeOfEvent desired time to search at
   * @return Event occuring at that time, null otherwise
   */
  @Override
  public IEvent findEventAtTime(ITime timeOfEvent) {
    return panel.findEventAtTime(timeOfEvent);
  }

  /**
   * Shows or hides the frame as specified.
   *
   * @param show true if frame should be shown, false otherwise
   */
  @Override
  public void display(boolean show) {
    this.setVisible(true);
  }

  /**
   * Opens up the current user's schedule.
   */
  @Override
  public void openScheduleView() {
    panel.openScheduleView();
  }

  /**
   * Displays the desired user's schedule.
   *
   * @param userToShow desired user schedule to show
   */
  @Override
  public void displayUserSchedule(String userToShow) {
    panel.displayUserSchedule(userToShow);
  }

}
