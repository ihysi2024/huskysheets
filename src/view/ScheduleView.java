package view;

import java.util.List;

import javax.swing.JFrame;

import controller.ViewFeatures;

import model.ReadOnlyPlanner;

/**
 * Represents the view of the event panel that allows the user to automatically
 */
public class ScheduleView extends JFrame implements IScheduleView {
  private final SchedulePanel panel;

  /**
   * Creates a view of the Schedule view.
   *
   * @param model desired model to represent the Schedule view
   */
  public ScheduleView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new SchedulePanel(model);
    this.add(panel);
    this.setVisible(false);
    this.pack();
  }

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */

  @Override
  public void addFeatures(ViewFeatures features) {
    panel.addFeatures(features);
  }

  /**
   * Get the user's input for the event name.
   * @return a String of the event name
   */

  @Override
  public String getEventNameInput() {
    return panel.getEventNameInput();
  }

  /**
   * Get the user's input for the event location.
   * @return a String of the location
   */

  @Override
  public String getLocationInput() {
    return panel.getLocationInput();
  }

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the location
   */

  @Override
  public List<String> getUsersInput() {
    return panel.getUsersInput();
  }

  /**
   * Observes the user's input for whether the event is online or not
   * @return whether the event is online
   */

  public boolean getOnline() {
    return panel.getOnline();
  }

  /**
   * Opens up the current user's schedule.
   */

  @Override
  public void openScheduleView() {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }

  /**
   * Observes how long the event is.
   * @return the length of the event
   */

  public int getDuration() {
    return panel.getDuration();
  }

  /**
   * Empties the fields in the panel for the user to enter their own inputs.
   * @param host the host of the event.
   */

  public void resetSchedulePanel(String host) {
    panel.resetSchedulePanel(host);
  }

  /**
   * Closes the current schedule view.
   */

  public void closeScheduleView() {
    this.setVisible(false);
  }

  /**
   * Display errors that may arise should the user provide invalid inputs to the panel.
   */

  public void displayError() {
    panel.displayError();

  }
}


