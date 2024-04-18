package cs3500.nuplanner.provider.model;

import java.util.List;

/**
 * Represents a single user's schedule in the NUPlanner.
 * Handles the addition and removal of Events.
 */
public interface ISchedule {

  /**
   * Adds an event to the schedule.
   *
   * @param event The event to be added to the schedule.
   */
  void addEvent(IEvent event);

  /**
   * Removes an event from the schedule.
   * If the user the schedule belongs to is the host, the event will be removed from
   * all schedules in the system. If the user is not the host, the event will only be
   * removed from their schedule.
   *
   * @param event The event to be removed from the schedule.
   */
  void removeEvent(IEvent event);

  /**
   * Modifies an event in the schedule.
   *
   * @param originalEvent The original event to be modified.
   * @param modifiedEvent The modified event to be substituted for the original event.
   */
  void modifyEvent(IEvent originalEvent, IEvent modifiedEvent);

  /**
   * Observes the list of events in a user's schedule.
   *
   * @return Returns a user's list of events.
   */
  List<IEvent> accessEvents();

  /**
   * Returns the Schedule in the form of a textual view as a string.
   *
   * @return Returns the string containing the textual view format.
   */
  String toString();

  /**
   * Creates a list of strings defining each line in a Schedule XML file.
   *
   * @return Returns the list of strings defining a Schedule.
   */
  List<String> toList();

  /**
   * Updates user schedule. If any events in the new schedule are overlapping with
   * the existing schedule, the operation will be suspended.
   *
   * @param newSchedule Schedule of new events to be added to the existing schedule.
   * @throws IllegalArgumentException if new schedule events are overlapping.
   */
  void updateSchedule(ISchedule newSchedule);
}
