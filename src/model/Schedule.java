package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Represents a schedule in the planner system.
 */
public class Schedule implements ISchedule {
  private final ArrayList<Event> events;


  public Schedule(ArrayList<Event> events) {
    this.events = Objects.requireNonNull(events);
  }

  /**
   * Adds a new event to this schedule. Event can only be added to the
   * schedule if it does not overlap with any other events in this schedule.
   *
   * @param event the event to be added
   * @throws IllegalArgumentException if event overlaps another event
   */

  public void addEvent(Event event) {
    int countOverlapping = 0;
    for (Event existingEvent: this.events) {
      if (existingEvent.overlappingEvents(event)) {
        countOverlapping++;
      }
    }
    if (countOverlapping != 0) {
      throw new IllegalArgumentException("Event coincides with another event");
    }
    else {
      this.events.add(event);
    }
  }

  /**
   * Remove event from this schedule. Only removes an event that exactly matches an event
   * already in the system.
   *
   * @param otherEvent the event to be removed
   * @throws IllegalArgumentException if event doesn't exist
   */
  public void removeEvent(Event otherEvent) {
    int scheduleSize = this.events.size();
    this.events.removeIf(thisEvent -> thisEvent.equals(otherEvent));
    if (this.events.size() == scheduleSize) {
      throw new IllegalArgumentException("Event not in schedule");
    }
  }

  /**
   * Observes the events present in a given schedule. Necessary to
   * allow the user to observe the events in their schedule.
   * @return the list of the schedule's events.
   */
  public List<Event> getEvents() {
    return this.events;
  }

  /**
   * Creating an association between day of the week and events occurring that day.
   *
   * @return a HashMap relating each day of the week to a list of events
   */
  public HashMap<Time.Day, List<Event>> dayToEventsMappping() {
    HashMap<Time.Day, List<Event>> dayToEvent = new LinkedHashMap<>();
    dayToEvent.put(Time.Day.SUNDAY, new ArrayList<>());
    dayToEvent.put(Time.Day.MONDAY, new ArrayList<>());
    dayToEvent.put(Time.Day.TUESDAY, new ArrayList<>());
    dayToEvent.put(Time.Day.WEDNESDAY, new ArrayList<>());
    dayToEvent.put(Time.Day.THURSDAY, new ArrayList<>());
    dayToEvent.put(Time.Day.FRIDAY, new ArrayList<>());
    dayToEvent.put(Time.Day.SATURDAY, new ArrayList<>());

    for (Event eventToSchedule: this.events) {
      dayToEvent.get(eventToSchedule.getStartTime().getDate()).add(eventToSchedule);
    }
    return dayToEvent;
  }

  /**
   * Convert the schedule to a string format for XML exportation.
   *
   * @return a string representing the schedule
   */
  public String scheduleToString() {
    StringBuilder scheduleStr = new StringBuilder();
    HashMap<Time.Day, List<Event>> eventsMap = this.dayToEventsMappping();
    for (Time.Day dayOfTheWeek: eventsMap.keySet()) {
      scheduleStr.append(dayOfTheWeek.getDayString() + ": " + "\n");
      for (Event eventsInMap: eventsMap.get(dayOfTheWeek)) {

        scheduleStr.append(eventsInMap.eventToString() + " ".repeat(10) + "\n");
      }
    }
    return scheduleStr.toString();
  }

  /**
   * Convert the schedule to proper XML format for exportation.
   *
   * @return a string compatible with XML formats
   */
  public String scheduleToXMLFormat() {
    StringBuilder scheduleXML = new StringBuilder();
    for (Event event: this.events) {
      scheduleXML.append(event.eventToXMLFormat() + "\n");
    }
    return scheduleXML.toString();
  }

  /**
   * Return a list of events occurring at a given time.
   * @param time the time to search for events occurring during
   * @return a list of events at the given time
   */
  public List<Event> eventsOccurring(Time time) {
    List<Event> occurringNow = new ArrayList<>();
    for (Event event: this.events) {
      if (event.getStartTime().compareTimes(time) <= 0
              && event.getEndTime().compareTimes(time) >= 0) {
        occurringNow.add(event);
      }
    }
    return occurringNow;
  }

}
