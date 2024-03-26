package model;

import java.util.List;

/**
 * Represents an event in a planner system. Main operations of an event are formatting itself
 * as a String or an XML file to be used in other aspects of the planner system.
 */
public interface IEvent {

  /**
   * Getting the users invited to the event. The first user is the host of the event.
   *
   * @return list of strings representing the users of the system that are invited to event.
   */
  List<String> getUsers();

  /**
   * Getting the event's start time to be used in other event calculations.
   *
   * @return the event's start time
   */
  ITime getStartTime();

  /**
   * Getting the event's end time to be used in other event calculations.
   *
   * @return the event's end time
   */
  ITime getEndTime();


  /**
   * Calculates the duration of this event in minutes.
   *
   * @return event's duration in minutes
   */
  int eventDuration();

  /**
   * Compares two events to see if their times coincide with each other.
   * if the event's start time is the same or after the previous event's end time,
   * the event do not coincide.
   *
   * @param otherEvent event to compare
   * @return true if the two events coincide and false otherwise
   */
  boolean overlappingEvents(IEvent otherEvent);

  /**
   * Overriding default equals method to compare each field of this event with another event.
   *
   * @param other the event to compare this event to
   * @return true if the events are equal, false otherwise
   */
  boolean equals(Object other);

  /**
   * Equals method was overriden, so overriding hashCode as well using the event's fields.
   *
   * @return hashCode representation of this event.
   */
  @Override
  int hashCode();

  /**
   * Formats this event as a String to easily interpret this event's contents and aid in testing.
   *
   * @return user-friendly representation of this event
   */
  String eventToString();

  /**
   * Parses through each field of this event and adds the proper XML tags around each field.
   *
   * @return String represenation of event with proper XML tags.
   */
  String eventToXMLFormat();

  Boolean getOnline();

  String getEventName();

  String getLocation();


}
