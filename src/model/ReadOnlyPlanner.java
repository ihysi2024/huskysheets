package model;

import java.util.List;
import java.util.Set;

public interface ReadOnlyPlanner {

  /**
   * return event in a user's schedule at a given time.
   *
   * @param user      the user to examine
   * @param givenTime the time to look at event within
   * @return an event. return null if no events at that time
   * @throws IllegalArgumentException if user doesn't exist or doesn't have a schedule
   */
  IEvent retrieveUserScheduleAtTime(IUser user, ITime givenTime);

  /**
   * User list
   */

  Set<IUser> getUsers();

  /**
   * Retrieves the events in this user's schedule.
   *
   * @param user desired user for whom to retrieve the schedule
   * @return a list of this user's events
   */
  List<IEvent> retrieveUserEvents(IUser user);

}
