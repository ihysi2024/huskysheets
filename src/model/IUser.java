package model;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a user of the planner system. A user has a schedule that they can interact with.
 */
public interface IUser {

  /**
   * Observes the name of the user. This is necessary to compare a list of users names.
   * invited to an Event and the list of users in the Planner System.
   * @return a string representing the current user's name
   */
  String getName();

  /**
   * Observes the schedule of the user. This is necessary for the addition or removal
   * of an event from the schedules of all relevant invitees to the event.
   * @return a Schedule as a list of events that is specific to the current user
   */
  Schedule getSchedule();

  /**
   * Show the user's schedule in a text format.
   * @return String representing the user's schedule
   */
  String userToString();

  /**
   * Writes the user's schedule to an XML file that is unique to them and a given filepath.
   * @param filePathToSave path to save the XML written by the user
   */
  void userSchedToXML(String filePathToSave);

  /**
   * Allow the user to examine the contents of an XML file located a specific path
   * and use the XML to generate a list of events compatible with the planner system
   * corresponding to the user.
   *
   * @param filePath path where XML is located
   * @return a list of events
   */
  static List<Event> interpretXML(String filePath) {
    return null;
  }

  /**
   * Convert a hashmap of an event's attribute name to a list of string values
   * into an event compatible with the planner system, i.e. as an instance of Event.
   * @param eventToMake HashMap of attribute name->list of values to convert to events.
   * @return the event corresponding to the HashMap
   */
  static Event makeEvent(HashMap<String, String[]> eventToMake) {
    return null;
  }

  /**
   * Adds an event to a user's schedule.
   * @param event event to add
   */
  void addEventForUser(Event event);

  /**
   * Removes an event from a user's schedule.
   * @param event to remove
   */
  void removeEventForUser(Event event);
}
