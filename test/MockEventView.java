
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.NUPlanner;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Time;
import view.EventView;
import view.IEventView;
import view.IScheduleTextView;
import view.ScheduleTextView;

public class MockEventView implements IEventView {

  private IScheduleTextView view;
  private StringBuilder out;

  public MockEventView(StringBuilder out) {
    this.out = out;
    PlannerSystem model = new NUPlanner();
    this.view = new ScheduleTextView(model, out);
  }

  /**
   * Allow the user to interact with the calendar through the features present
   * in the event view.
   * @param features functionality that the user has access to through the event view.
   */
  public void addFeatures(ViewFeatures features) {

  }

  /**
   * Close the event view so it stops being visible.
   */
  public void closeEvent() {
    out.delete(0, out.length());
    out.append("Closing an event");
  }

  /**
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   * @param event event to visualize in the event panel.
   */
  public void populateEventContents(IEvent event) {
    out.delete(0, out.length());
    out.append("Populating the view with the following event fields: \n");
    out.append(view.eventToString(event));
  }

  /**
   * Get the user's input for the event name.
   * @return a String[] of the event name
   */
  public String[] getEventNameInput() {
    return null;
  }

  /**
   * Get the user's input for the event time.
   * @return a String[] of the event time
   */
  public String[] getTimeInput() {
    return null;
  }

  /**
   * Get the user's input for the event location.
   * @return a String[] of the location
   */
  public String[] getLocationInput() {
    return null;
  }

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the location
   */
  public String[] getUsersInput() {
    return null;
  }

  /**
   * Modify an event with the user's new input to the event panel.
   * @param event represents the updated event
   */
  public void modifyEvent(IEvent event) {
    out.delete(0, out.length());
    out.append("Modifying the event with the following event fields: \n");
    out.append(view.eventToString(event));
  }

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   *
   * @param host host of the event
   */
  public void resetPanel(String host) {
    out.delete(0, out.length());
    out.append("Resetting the panel with host: " + host);
  }

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   */
  public void openEvent() {
    out.append("Opening an event");
  }

  @Override
  public void displayCreateError() {
    out.delete(0, out.length());
    out.append("Error in creating an event");
  }

  /**
   * Store the current event's inputs as a map of String -> String[]
   * Useful for modifying an event with the current panel inputs.
   * @return a map of strings to string[]
   */
  public HashMap<String, String[]> storeOpenedEventMap() {
    out.delete(0, out.length());
    out.append("Storing an opened event");
    return null;
  }

  /**
   * Store the user's input as an event that is added to their schedule.
   * @return the event that was created, null otherwise
   */
  public IEvent createEvent() {
    out.delete(0, out.length());
    out.append("Creating an event");
    return new Event("movie",
            new Time(Time.Day.FRIDAY, 21, 15),
            new Time(Time.Day.FRIDAY, 23, 30),
            true,
            "home",
            List.of("Student Anon"));
  }

  /**
   * Updates list of users in event view.
   */
  public void updateUserList() {

  }

  @Override
  public void displayRemoveError(Map<String, String[]> eventToRemove) {
    out.delete(0, out.length());
    out.append("Error in removing an event");
  }

}
