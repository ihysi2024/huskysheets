package model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Represents an event in a planner system. Main operations of an event are formatting itself
 * as a String or an XML file to be used in other aspects of the planner system.
 *
 * <p> The first user in the list of users is always the host of the event. The users' names
 * are assumed to be unique. </p>
 *
 * <p> If the end time is 'before' the start time of the event, it is assumed that this means that
 * the event starts this week but ends in the following week. </p>
 */
public class Event implements IEvent {
  private final String eventName;
  private final ITime startTime;
  private final ITime endTime;
  private final boolean online;
  private final String location;
  private final List<String> users;
  // INVARIANT: users list must have at least one user in it (the host of event)

  /**
   * Creating an event to be added to the planner system.
   *
   * @param eventName name of this event
   * @param startTime event's start time
   * @param endTime   event's end time
   * @param online    specifies whether an event has an online option (true == yes online)
   * @param location  physical location of an event
   * @param users     users invited to the event. first user in list is the host of the event
   */
  public Event(String eventName, ITime startTime, ITime endTime, boolean online, String location,
               List<String> users) {
    if ((users == null) || (users.isEmpty())) {
      throw new IllegalArgumentException("Null list of invitees not allowed");
    }
    if ((eventName == null) || (eventName.isEmpty())) {
      throw new IllegalArgumentException("Null event name not allowed");
    }
    if (startTime == null) {
      throw new IllegalArgumentException("Start times cannot be null");
    }
    if (endTime == null) {
      throw new IllegalArgumentException("End time cannot be null");
    }
    if (startTime.compareTimes(endTime) == 0) {
      throw new IllegalArgumentException("Start and end times must be different.");
    }
    this.eventName = eventName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.online = online;
    this.location = location;
    this.users = users;
  }

  /**
   * Getting the users invited to the event. The first user is the host of the event.
   *
   * @return list of strings representing the users of the system that are invited to event.
   */
  public List<String> getUsers() {
    return this.users;
  }

  /**
   * Getting the event's start time to be used in other event calculations.
   *
   * @return the event's start time
   */
  public ITime getStartTime() {
    return this.startTime;
  }

  /**
   * Getting the event's end time to be used in other event calculations.
   *
   * @return the event's end time
   */
  public ITime getEndTime() {
    return this.endTime;
  }


  /**
   * Calculates the duration of this event in minutes.
   *
   * @return event's duration in minutes
   */
  public int eventDuration() {
    return this.startTime.minutesSinceMidnight() - this.endTime.minutesSinceMidnight();
  }

  /**
   * Compares two events to see if their times coincide with each other.
   * if the event's start time is the same or after the previous event's end time,
   * the event do not coincide.
   *
   * @param otherEvent event to compare
   * @return true if the two events coincide and false otherwise
   */
  public boolean overlappingEvents(IEvent otherEvent) {

    // do the events occur at the same time? if so, they overlap
    if ((this.startTime.compareTimes(otherEvent.getEndTime()) == 0)
            || (this.endTime.compareTimes(otherEvent.getStartTime()) == 0)) {
      return false;
    }
    // do the events start and end times overlap? if so, they overlap
    else {
      return (this.startTime.compareTimes(otherEvent.getEndTime()) <= 0)
            && (this.endTime.compareTimes(otherEvent.getStartTime()) >= 0);
    }
  }


  /**
   * Overriding default equals method to compare each field of this event with another event.
   *
   * @param other the event to compare this event to
   * @return true if the events are equal, false otherwise
   */
  public boolean equals(Object other) {
    if (!(other instanceof Event)) {
      return false;
    }
    Event that = (Event) other;
    return this.eventName.equals(that.eventName)
            && this.online == that.online
            && (this.endTime.compareTimes(that.endTime) == 0)
            && (this.startTime.compareTimes(that.startTime) == 0)
            && this.location.equals(that.location)
           // && this.users.equals(that.users);
            && new HashSet<>(this.users).containsAll(that.users)
            && new HashSet<>(that.users).containsAll(this.users);
  }

  /**
   * Equals method was overriden, so overriding hashCode as well using the event's fields.
   *
   * @return hashCode representation of this event.
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(this.eventName) + Objects.hashCode(this.endTime)
            + Objects.hashCode(this.startTime) + Objects.hashCode(this.online)
            + Objects.hashCode(this.location) + Objects.hashCode(this.users);
  }

  /**
   * Formats this event as a String to easily interpret this event's contents and aid in testing.
   *
   * @return user-friendly representation of this event
   */
  public String eventToString() {
    return "name: " + eventName + "\n"
            + "time: " + startTime.timeToString() + "->"
            + endTime.timeToString() + "\n"
            + "location: " + location + "\n"
            + "online: " + online + "\n"
            + "users: " + String.join("\n", users);
  }

  /**
   * Parses through each field of this event and adds the proper XML tags around each field.
   *
   * @return String represenation of event with proper XML tags.
   */
  public String eventToXMLFormat() {
    String indent_5 = " ".repeat(5);
    String indent_10 = " ".repeat(10);
    StringBuilder eventXML = new StringBuilder();
    eventXML.append("<event>" + "\n");
    eventXML.append(indent_5 + "<name>" + this.eventName + "</name>" + "\n");
    eventXML.append(indent_5 + "<time>\n");
    timeToXMLFormat(eventXML, 10); // formatting start and end times
    eventXML.append(indent_5 + "</time>" + "\n");
    eventXML.append(indent_5 + "<location>" + "\n");
    eventXML.append(indent_10 + "<online>" + this.online + "</online>" + "\n");
    eventXML.append(indent_10 + "<place>" + this.location + "</place>" + "\n");
    eventXML.append(indent_5 + "</location>" + "\n");
    eventXML.append(indent_5 + "<users>" + "\n");
    usersToXMLFormat(eventXML, 10); // formatting list of users
    eventXML.append(indent_5 + "</users>\n");
    eventXML.append("</event>");

    return eventXML.toString();
  }

  /**
   * Helper method to format the event's start + end times with XML tags.
   *
   * @param eventXML   xml document currently being parsed that time should be appended to
   * @param numIndents # of indents desired for this block of xml text.
   */
  private void timeToXMLFormat(StringBuilder eventXML, int numIndents) {
    String indents = " ".repeat(numIndents);
    String startHoursStr = "" + this.startTime.getHours();
    String startMinutesStr = "" + this.startTime.getMinutes();
    String endHoursStr = "" + this.endTime.getHours();
    String endMinutesStr = "" + this.endTime.getMinutes();
    if (this.startTime.getHours() < 10) {
      startHoursStr = "0" + startHoursStr;
    }
    if (this.startTime.getMinutes() < 10) {
      startMinutesStr = "0" + startMinutesStr;
    }
    if (this.endTime.getHours() < 10) {
      endHoursStr = "0" + endHoursStr;
    }
    if (this.endTime.getMinutes() < 10) {
      endMinutesStr = "0" + endMinutesStr;
    }

    eventXML.append(indents + "<start-day>" + this.startTime.getDate() + "</start-day>" + "\n");
    eventXML.append(indents + "<start>" + startHoursStr +
            startMinutesStr + "</start>" + "\n");
    eventXML.append(indents + "<end-day>" + this.endTime.getDate() + "</end-day>" + "\n");
    eventXML.append(indents + "<end>" + endHoursStr
            + endMinutesStr + "</end>" + "\n");
  }

  /**
   * Helper method to format the event's users with XML tags.
   *
   * @param eventXML   xml document currently being parsed that the user list should be appended to
   * @param numIndents # of indents desired for this block of xml text.
   */
  private void usersToXMLFormat(StringBuilder eventXML, int numIndents) {
    String indents = " ".repeat(numIndents);
    for (String invitee : this.users) {
      eventXML.append(indents + "<uid>" + invitee + "</uid>" + "\n");
    }
  }

  public String getEventName() {
    return this.eventName;
  }

  public String getLocation() {
    return this.location;
  }

  public Boolean getOnline() {
    return this.online;
  }
}
