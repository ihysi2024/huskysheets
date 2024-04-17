package cs3500.nuplanner.provider.model;

import java.util.List;
import org.w3c.dom.Document;

/**
 * Represents the central management system for the NUPlanner,
 * handling user and event management operations to ensure schedule consistency.
 * All implementations of the ICentralSystem interface must have a map that contains key-value
 * pairs for user ids (String type) and user schedules (Schedule type).
 */
public interface ICentralSystem extends IReadOnlyCentralSystem {

  /**
   * Adds a new user to the NUPlanner system along with their schedule.
   * If the user already exists, no action is taken.
   *
   * @param userSchedule The schedule of the user to be added.
   * @param userId       The unique identifier of the user.
   * @throws IllegalArgumentException if the userId already exists.
   */
  void addUser(ISchedule userSchedule, String userId);

  /**
   * Adds a new user to the NUPlanner system using a given XML schedule.
   * If the user already exists, the schedule is updated.
   *
   * @param xmlDoc The XML Document object containing a single schedule.
   */
  void addUser(Document xmlDoc);

  /**
   * Removes a user from the NUPlanner system using their unique identifier.
   * If the specified userId does not exist in the system, an IllegalArgumentException is thrown.
   *
   * @param userId The unique identifier of the user to be removed.
   * @throws IllegalArgumentException if the userId does not exist in the system.
   * @throws IllegalArgumentException if the userId is empty.
   */
  void removeUser(String userId);

  /**
   * Creates a new event, adding it to the schedule of the event creator
   * and all invited participants. If the userId does not exist,
   * an IllegalArgumentException is thrown.
   *
   * @param userId         The unique identifier of the user creating the event.
   * @param event          The event to be created.
   * @param invitedUserIds List of unique identifiers for users invited to the event.
   * @throws IllegalArgumentException if the userId does not exist in the system
   *                                  or if any of the invitedUserIds do not exist.
   * @throws IllegalArgumentException if the userId is empty.
   */
  void createEvent(String userId, IEvent event, List<String> invitedUserIds);

  /**
   * Modifies the details of an existing event within the NUPlanner system.
   * This requires both the original event to be modified and the new details
   * of the event. If the userId does not exist or the original event cannot be found,
   * an IllegalArgumentException is thrown.
   *
   * @param userId        The unique identifier of the user requesting the event modification.
   * @param originalEvent The original event before modification.
   * @param modifiedEvent The event with its new details.
   * @throws IllegalArgumentException if the userId does not exist in the system
   *                                  or the original event cannot be identified.
   */
  void modifyEvent(String userId, IEvent originalEvent, IEvent modifiedEvent);

  /**
   * Removes an event from the NUPlanner system.
   * This involves removing the event from all affected user schedules.
   * If the userId does not exist or the event cannot be found, an
   * IllegalArgumentException is thrown.
   *
   * @param userId The unique identifier of the user requesting the event removal.
   * @param event  The event to be removed.
   * @throws IllegalArgumentException if the userId does not exist in the system
   *                                  or the event cannot be identified.
   */
  void removeEvent(String userId, IEvent event);

  /**
   * checks if a given time slot is available for scheduling an event for a list of
   * users.
   * @param startTime start time of the time slot.
   * @param endTime end time of the time slot.
   * @param users List of users that would be part of a potential event.
   * @return
   */
  boolean isTimeSlotAvailable(IEventTime startTime,
                              IEventTime endTime, List<String> users);
}

