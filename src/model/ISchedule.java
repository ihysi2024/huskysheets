package model;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a user's schedule in the planner system. Main functionality is to add and remove
 * events from a user's schedule.
 */
public interface ISchedule {

  void addEvent(Event event);

  /**
   * Remove event from this schedule. Only removes an event that exactly matches an event
   * already in the system.
   *
   * @param otherEvent the event to be removed
   * @throws IllegalArgumentException if event doesn't exist
   */
  void removeEvent(Event otherEvent);

  /**
   * Observes the events present in a given schedule. Necessary to
   * allow the user to observe the events in their schedule.
   * @return the list of the schedule's events.
   */
  List<Event> getEvents();

  /**
   * Creating an association between day of the week and events occurring that day.
   *
   * @return a HashMap relating each day of the week to a list of events
   */
  HashMap<Time.Day, List<Event>> dayToEventsMappping();

  /**
   * Convert the schedule to a string format for XML exportation.
   *
   * @return a string representing the schedule
   */
  String scheduleToString();

  /**
   * Convert the schedule to proper XML format for exportation.
   *
   * @return a string compatible with XML formats
   */
  String scheduleToXMLFormat();

  /**
   * Return a list of events occurring at a given time.
   * @param time the time to search for events occurring during
   * @return a list of events at the given time
   */
  List<Event> eventsOccurring(Time time);
}
