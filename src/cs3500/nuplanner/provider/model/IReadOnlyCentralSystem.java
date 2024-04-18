package cs3500.nuplanner.provider.model;

import java.util.List;
import java.util.Map;

/**
 * Represents the READ-ONLY central management system for the NUPlanner,
 * handling observations methods and excluding the mutator methods
 * to ensure schedule consistency.
 */
public interface IReadOnlyCentralSystem {

  /**
   * Observes the map of user ids and user schedules found in the central system.
   *
   * @return Returns map of user ids and user schedules.
   */
  Map<String, ISchedule> accessUserSchedules();

  /**
   * Observes the list of users in the planner.
   *
   * @returns Returns a list of users as strings.
   */
  List<String> accessUsers();

  /**
   * Observes the events in a given user's schedule.
   *
   * @param userId String of a user's name.
   * @return Returns a list of events on the user's schedule.
   */
  List<IEvent> accessEvents(String userId);

  /**
   * Creates a string defining a textual view of the CentralSystem.
   *
   * @return Returns a string that contains all user's schedules.
   */
  String toString();

  /**
   * Creates a list of strings defining each line in a Schedule XML File for
   * a given user.
   * @param userId String of a user's name.
   * @return Returns a list of strings for a specific user's schedule.
   */
  List<String> toList(String userId);

  /**
   * Checks if an event conflicts with the existing schedules for all of the
   * event's invited users.
   *
   * @param event New event being checked.
   * @return Returns true if the event conflicts; returns false if otherwise.
   */
  boolean eventConflict(IEvent event);

  /**
   * Checks if there is an event at a certain time on a given user's schedule.
   * @param userId String of a user's name
   * @param time a time of an event
   * @return returns an instance of an event.
   * @throws IllegalArgumentException if no event exist during the given event time
   */
  IEvent findEvent(String userId, IEventTime time);

}
