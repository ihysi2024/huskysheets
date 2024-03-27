package view;

import java.util.HashMap;
import javax.swing.*;
import controller.ViewFeatures;

import model.IEvent;

import model.ReadOnlyPlanner;

/**
 * Frame for the event pop-out window, where users will be able to see the functionality
 * related to creating new events, modifying existing events, and removing events.
 */

public class EventView extends JFrame implements IEventView {
  private final EventPanel panel;

  /**
   * Creates a view of the Simon game.
   *
   * @param model desired model to represent Simon game
   */
  public EventView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new EventPanel(model);
    this.add(panel);
    this.setVisible(false);
    this.pack();
  }

  /**
   * Store the user's input as an event that is added to their schedule.
   * Delegate to the panel.
   */
  public void createEvent() {
    panel.createEvent();
  }

  /**
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   * Delegate to the panel.
   * @param event event to visualize in the event panel.
   */
  public void populateEventContents(IEvent event) {
    this.panel.populateEventContents(event);
  }

  /**
   * Get the user's input for the event name.
   * Delegate to the panel.
   * @return a String[] of the event name
   */
  @Override
  public String[] getEventNameInput() {
    return panel.getEventNameInput();
  }

  /**
   * Get the user's input for the event time.
   * Delegate to the panel.
   * @return a String[] of the event time
   */
  @Override
  public String[] getTimeInput() {
    return panel.getTimeInput();
  }

  /**
   * Get the user's input for the event location.
   * Delegate to the panel.
   * @return a String[] of the location
   */
  @Override
  public String[] getLocationInput() {
    return panel.getLocationInput();
  }

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * Delegate to the panel.
   * after an event has already been created.
   */
  public void resetPanel() {
    panel.resetPanel();
  }

  /**
   * Get the user's input for the event list of users.
   * Delegate to the panel.
   * @return a String[] of the location
   */
  @Override
  public String[] getUsersInput() {
    return panel.getUsersInput();
  }

  /**
   * Store the current event's inputs as a map of String -> String[]
   * Useful for modifying an event with the current panel inputs.
   * Delegate to the panel.
   *
   * @return a map of strings to string[]
   */
  @Override
  public HashMap<String, String[]> storeOpenedEventMap() {
    return panel.storeOpenedEventMap();
  }

  /**
   * Modify an event with the user's new input to the event panel.
   * Delegate to the panel.
   *
   * @param event represents the updated event
   */
  public void modifyEvent(IEvent event) {
    panel.modifyEvent(event);
  }

  /**
   * Allow the user to interact with the calendar through the features present
   * in the event view.
   * Delegate to the panel.
   *
   * @param features functionality that the user has access to through the event view.
   */
  @Override
  public void addFeatures(ViewFeatures features) {
    panel.addFeatures(features);
  }

  /**
   * Close the event view so it stops being visible.
   */
  public void closeEvent() {
    this.setVisible(false);
  }

  /**
   * Open the event view for the user to see.
   * Delegate to the panel.
   */
  public void openEvent() {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }
}
