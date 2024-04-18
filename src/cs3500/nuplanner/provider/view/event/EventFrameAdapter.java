package cs3500.nuplanner.provider.view.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import controller.ViewFeatures;
import model.IEvent;
import model.IUser;
import view.IEventView;

public class EventFrameAdapter implements IEventView {

  private final IEventFrame adaptee;

  public EventFrameAdapter(IEventFrame adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Allow the user to interact with the calendar through the features present
   * in the event view.
   *
   * @param features functionality that the user has access to through the event view.
   */
  @Override
  public void addFeatures(ViewFeatures features) {

  }

  /**
   * Close the event view so it stops being visible.
   */
  @Override
  public void closeEvent() {

  }

  /**
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   *
   * @param event event to visualize in the event panel.
   */
  @Override
  public void populateEventContents(IEvent event) {

  }

  /**
   * Get the user's input for the event name.
   *
   * @return a String[] of the event name
   */
  @Override
  public String[] getEventNameInput() {
    return new String[0];
  }

  /**
   * Get the user's input for the event time.
   *
   * @return a String[] of the event time
   */
  @Override
  public String[] getTimeInput() {
    return new String[0];
  }

  /**
   * Get the user's input for the event location.
   *
   * @return a String[] of the location
   */
  @Override
  public String[] getLocationInput() {
    return new String[0];
  }

  /**
   * Get the user's input for the event list of users.
   *
   * @return a String[] of the location
   */
  @Override
  public String[] getUsersInput() {
    return new String[0];
  }

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   */
  @Override
  public void resetPanel() {

  }

  /**
   * Opens an event panel to further populate its fields.
   *
   * @param host host of this event
   */
  @Override
  public void openEvent(String host) {

  }

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   *
   * @param host host of this event
   */
  @Override
  public void openBlankEvent(String host) {

  }

  @Override
  public void displayCreateError() {

  }

  /**
   * Store the current event's inputs as a map of String -> String[]
   * Useful for modifying an event with the current panel inputs.
   *
   * @return a map of strings to string[]
   */
  @Override
  public HashMap<String, String[]> storeOpenedEventMap() {
    return null;
  }

  /**
   * Store the user's input as an event that is added to their schedule.
   *
   * @return the event that was created, null otherwise
   */
  @Override
  public IEvent createEvent() {
    return null;
  }

  /**
   * Updates list of users in event view.
   */
  @Override
  public void updateUserList() {

  }

  /**
   * Displays the error that arises when a user tries to remove an event.
   *
   * @param eventToRemove event to remove in a map.
   */
  @Override
  public void displayRemoveError(Map<String, String[]> eventToRemove) {

  }

  /**
   * Displays the error that arises when a user tries to modify an event.
   *
   * @param host the user of the event
   */
  @Override
  public void displayModifyError(IUser host) {

  }
}
