package cs3500.nuplanner.provider.view.schedule;

import java.util.List;
import java.util.Objects;

import controller.FeaturesAdapter;
import controller.ViewFeatures;
import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.view.event.IEventFrame;
import view.IScheduleView;

public class ScheduleFrameAdapter implements IScheduleView {

  private final IScheduleFrame adaptee;

  public ScheduleFrameAdapter(IScheduleFrame adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Opens up the current user's schedule.
   */
  @Override
  public void openScheduleView() {
    this.adaptee.setVisible();

  }

  /**
   * Closes the current schedule view.
   */
  @Override
  public void closeScheduleView() {
    this.adaptee.closeScheduleFrame();

  }

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */
  @Override
  public void addFeatures(ViewFeatures features) {
    IFeatures adaptedFeatures = new FeaturesAdapter(features);
    this.adaptee.addFeatures(adaptedFeatures);
  }

  /**
   * Get the user's input for the event name.
   *
   * @return a String of the event name
   */
  @Override
  public String getEventNameInput() {
    return null;
  }

  /**
   * Get the user's input for the event location.
   *
   * @return a String of the location
   */
  @Override
  public String getLocationInput() {
    return null;
  }

  /**
   * Get the user's input for the event list of users.
   *
   * @return a String[] of the location
   */
  @Override
  public List<String> getUsersInput() {
    return null;
  }

  /**
   * Observes the user's input for whether the event is online or not
   *
   * @return whether the event is online
   */
  @Override
  public boolean getOnline() {
    return false;
  }

  /**
   * Observes how long the event is.
   *
   * @return the length of the event
   */
  @Override
  public int getDuration() {
    return 0;
  }

  /**
   * Empties the fields in the panel for the user to enter their own inputs.
   *
   * @param host the host of the event.
   */
  @Override
  public void resetSchedulePanel(String host) {

  }

  /**
   * Display errors that may arise should the user provide invalid inputs to the panel.
   */
  @Override
  public void displayError() {

  }
}
