package cs3500.nuplanner.provider.model;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Represents an Event in a user's schedule. Every event has a name, start time,
 * end time, location, and list of users that are invited to the event.
 */
public interface IEvent {

  /**
   * Checks if given event overlaps current event.
   *
   * @param other The new event given to be compared.
   * @return Returns true if the two events are overlapping, and false otherwise.
   */
  boolean isOverlapping(IEvent other);

  /**
   * Checks if current event overlaps the start and end times of another event.
   *
   * @param otherStartTime The other event's start time.
   * @param otherEndTime The other event's end time.
   * @return Returns true if the current event's start time and end times don't overlap with
   *        the given start time and end time.
   */
  boolean isOverlapping(IEventTime otherStartTime, IEventTime otherEndTime);

  /**
   * Checks if the given user is the host of the event
   * (is first in the list of users for the event).
   *
   * @param user The given user to be checked.
   * @return Returns true if given user is host; returns false otherwise.
   */
  boolean isHost(String user);

  /**
   * Observes the event name.
   *
   * @return Returns the name of the event.
   */
  String accessName();

  /**
   * Observes the starting day of the event.
   *
   * @return Returns the event start day.
   */
  DayOfWeek accessStartDay();

  /**
   * Observes the ending day of the event.
   *
   * @return Returns the event end day.
   */
  DayOfWeek accessEndDay();

  /**
   * Observes the event start time.
   *
   * @return Returns the start time of the event.
   */
  IEventTime accessStartTime();

  /**
   * Observes the event end time.
   *
   * @return Returns the end time of the event.
   */
  IEventTime accessEndTime();

  /**
   * Observes the event location.
   *
   * @return Returns the location of the event.
   */
  IEventLocation accessLocation();

  /**
   * Observes the list of users attending the event.
   *
   * @return Returns the event list of users.
   */
  List<String> accessUsers();

  /**
   * Returns the Event in the form of a textual view as a string.
   *
   * @return Returns the string containing the textual view format.
   */
  String toString();

  /**
   * Creates a list of strings defining each line in an event XML file.
   *
   * @return Returns the list of strings defining an event.
   */
  List<String> toList();
}


