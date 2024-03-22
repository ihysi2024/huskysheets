package model;

import java.util.List;

public interface ReadOnlyPlanner {

  /**
   * return events in a user's schedule at a given time.
   * @param user the user to examine
   * @param givenTime the time to look at event within
   * @return a list of events. return at empty list if no events at that time
   * @throws IllegalArgumentException if user doesn't exist or doesn't have a schedule
   */
  List<Event> retrieveUserScheduleAtTime(User user, Time givenTime);

}
