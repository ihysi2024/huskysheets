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

  Set<User> getUsers();

  /**
   * return events in a user's schedule at a given time.
   * @param user the user to examine
   * @param givenTime the time to look at event within
   * @return a list of events. return at empty list if no events at that time
   * @throws IllegalArgumentException if user doesn't exist or doesn't have a schedule
   */
  List<Event> retrieveUserScheduleAtTime(User user, Time givenTime);

  /**
   * Remove an event from planner system for relevant users.
   * If host (first user in invitee list) is removing event, remove for all users.
   * If any other user, only remove event from their schedule.
   *
   * @param eventToRemove event to remove from planner system
   * @param userRemovingEvent user removing the event
   */
  void removeEventForRelevantUsers(Event eventToRemove, User userRemovingEvent);

  /**
   * Events can only be modified if all users can attend the event.
   * @param prevEvent event to be modified
   * @param newEvent what the previous event should be modified to
   * @throws IllegalArgumentException if user listed cannot attend the modified event
   **/
  void modifyEvent(Event prevEvent, Event newEvent);

  /**
   * Add events for the users listed in the event's invitee list.
   *
   * @param eventToAdd event to add to the relevant user schedule
   */
  void addEventForRelevantUsers(Event eventToAdd);


  // could take in a string (make a user with an empty schedule)
  // or a User (with a schedule)
  // gui is adding a user via xml
  void addUser(User userToAdd);


}

//QUESTIONS:
// adding a user: our users have a schedule
// Event vs IEvent in methods?
  // change all of these into IEvents
// LinkedHashSet issue