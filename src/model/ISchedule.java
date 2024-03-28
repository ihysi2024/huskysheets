package model;

import java.util.HashMap;
import java.util.List;

/**
 * Represents a user's schedule in the planner system. Main functionality is to add and remove
 * events from a user's schedule.
 */
public interface ISchedule {

  void addEvent(IEvent event);

  /**
   * Remove event from this schedule. Only removes an event that exactly matches an event
   * already in the system.
   *
   * @param otherEvent the event to be removed
   * @throws IllegalArgumentException if event doesn't exist
   */
  void removeEvent(IEvent otherEvent);

  /**
   * Observes the events present in a given schedule. Necessary to
   * allow the user to observe the events in their schedule.
   * @return the list of the schedule's events.
   */
  List<IEvent> getEvents();

  /**
   * Creating an association between day of the week and events occurring that day.
   *
   * @return a HashMap relating each day of the week to a list of events
   */
  HashMap<Time.Day, List<IEvent>> dayToEventsMappping();


  /**
   * Convert the schedule to proper XML format for exportation.
   *
   * @return a string compatible with XML formats
   */
  String scheduleToXMLFormat();

  /**
   * Return the event occurring at a given time. Schedule can only have one event at any given time
   * @param time the time to search for events occurring during
   * @return the event at the given time. returns null if no event is occurring
   */
  IEvent eventOccurring(ITime time);
}
