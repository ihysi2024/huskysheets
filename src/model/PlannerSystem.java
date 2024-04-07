package model;

import java.util.List;
import java.util.Set;

/**
 * Main interface for the planner system. Used to interact with the users' schedules.
 */
public interface PlannerSystem extends ReadOnlyPlanner {

  /**
   * Export schedule as an XML file.
   */
  void exportScheduleAsXML(String filePath);

  List<IUser> getUsers();

  /**
   * Adds a user to the planner system with the schedule provided in the file path's XML
   *
   * @param filePath file path to read XML schedule from
   */
  void importScheduleFromXML(String filePath);

  /**
   * Remove an event from planner system for relevant users.
   * If host (first user in invitee list) is removing event, remove for all users.
   * If any other user, only remove event from their schedule.
   *
   * @param eventToRemove event to remove from planner system
   * @param userRemovingEvent user removing the event
   */
  void removeEventForRelevantUsers(IEvent eventToRemove, IUser userRemovingEvent);

  /**
   * Remove a user from the event list for every other person in planner system
   * that is also invited to the given event.
   *
   * @param event event to updated in planner system
   * @param userToRemove user being removed from the event
   */
  void removeUserFromEventList(IEvent event, IUser userToRemove);

  /**
   * Events can only be modified if all users can attend the event.
   * @param prevEvent event to be modified
   * @param newEvent what the previous event should be modified to
   * @throws IllegalArgumentException if user listed cannot attend the modified event
   **/
  void modifyEvent(IEvent prevEvent, IEvent newEvent);

  /**
   * Add events for the users listed in the event's invitee list.
   *
   * @param eventToAdd event to add to the relevant user schedule
   */
  void addEventForRelevantUsers(IEvent eventToAdd);


  // could take in a string (make a user with an empty schedule)
  // or a User (with a schedule)
  // gui is adding a user via xml
  void addUser(IUser userToAdd);
}