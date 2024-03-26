package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Planner system that contains a set of users and their corresponding schedules.
 */
public class NUPlanner implements PlannerSystem {

  /**
   * Planner system that contains a list of users and their corresponding schedules.
   * Assumptions to be made is that all users that will ever be invited to an event
   * are already in the system and that every user has exactly one schedule. Duplicate
   * users cannot be added to the system by the inclusion of a set of users
   * instead of a list.
   */
  private final Set<User> users;

  /**
   * Initialize a planner system with a given set of users.
   *
   * @param users non-duplicate user list in the system
   */
  public NUPlanner(Set<User> users) {
    this.users = new LinkedHashSet<>(users);
  }

  /**
   * Initialize a planner system with an empty list of users.
   */
  public NUPlanner() {
    this.users = new LinkedHashSet<>();
  }

  /**
   * Observe the users in the system.
   * @return a set of users
   */

  public Set<User> getUsers() {
    return this.users;
  }

  /**
   * Retrieves the events in this user's schedule.
   *
   * @param user desired user for whom to retrieve the schedule
   * @return a list of this user's events
   */
  @Override
  public List<Event> retrieveUserEvents(User user) {
    return user.getSchedule().getEvents();
  }

  /**
   * Write each user's schedule in the system to an XML file and store it.
   * @param filePathToSave where to save the XML file
   */
  public void exportScheduleAsXML(String filePathToSave) {
    for (User user: this.users) {
      user.userSchedToXML(filePathToSave);
    }
  }

  /**
   * return event in a user's schedule at a given time.
   *
   * @param user      the user to examine
   * @param givenTime the time to look at event within
   * @return an event. return null if no events at that time
   * @throws IllegalArgumentException if user doesn't exist or doesn't have a schedule
   */
  @Override
  public Event retrieveUserScheduleAtTime(User user, Time givenTime) {
    return user.getSchedule().eventOccurring(givenTime);
  }

  /**
   * Add events for the users listed in the event's invitee list.
   *
   * @param eventToAdd event to add to the relevant user schedule
   */
  public void addEventForRelevantUsers(Event eventToAdd) {
    for (User currUser : this.users) {
      if (eventToAdd.getUsers().contains(currUser.getName())) {
        try {
          // add event to current user's schedule
          currUser.addEventForUser(eventToAdd);
        } catch (IllegalArgumentException e) {
          // event is not added because it overlaps
        }
      }
    }
  }

  /**
   * Adding a user to the planner system. A user has a schedule.
   *
   * @param userToAdd user to add to Planner
   */
  @Override
  public void addUser(User userToAdd) {
    this.users.add(userToAdd);
  }

  /**
   * Events can only be modified if all users can attend the event.
   * @param prevEvent event to be modified
   * @param newEvent what the previous event should be modified to
   * @throws IllegalArgumentException if user listed cannot attend the modified event
   **/
  public void modifyEvent(Event prevEvent, Event newEvent) {
    User host = null;
    for (User user: this.users) {
      if (user.getName().equals(prevEvent.getUsers().get(0))) {
        host = new User(user.getName(), user.getSchedule());
      }
    }

    // only allow modification if the old event and updated event
    // still have the same host
    if (newEvent.getUsers().contains(prevEvent.getUsers().get(0))) {
      try {
        // remove the previous event from the user's schedule
        removeEventForRelevantUsers(prevEvent, host);
        // add the new event to the user's schedule
        addEventForRelevantUsers(newEvent);

      }
      catch (IllegalArgumentException e) {
        // if an exception is thrown, leave the list of events alone to avoid
        // causing conflict in any user's schedule
      }
    }
  }

  /**
   * Remove an event from planner system for relevant users.
   * If host (first user in invitee list) is removing event, remove for all users.
   * If any other user, only remove event from their schedule.
   *
   * @param eventToRemove event to remove from planner system
   * @param userRemovingEvent user removing the event
   */
  public void removeEventForRelevantUsers(Event eventToRemove, User userRemovingEvent) {
    Iterator<User> iterUsers = this.users.iterator();

    if (userRemovingEvent.getName().equals(eventToRemove.getUsers().get(0))) {
      while (iterUsers.hasNext()) {
        User currUser = iterUsers.next();
        if (eventToRemove.getUsers().contains(currUser.getName())) {
          try {
            // remove the event from the current user's schedule
            currUser.removeEventForUser(eventToRemove);
          }
          // given event is not in schedule
          catch (IllegalArgumentException e) {
            // ignoring exception because event will not be removed for this user
          }
        }
      }
    }
    // just an invitee trying to remove an event
    else {
      userRemovingEvent.removeEventForUser(eventToRemove);
    }
  }


}
