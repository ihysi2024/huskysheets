package strategies;

import java.util.List;
import java.util.Map;

import model.IEvent;
import model.ITime;
import model.IUser;
import model.Time;

/**
 * Represents a way of automatically creating a strategy depending on the user's command line arguments.
 */
public interface IScheduleStrategy {


  /**
   * Provides the times to schedule the given event at.
   * @param user the user who is attempting to schedule the event
   * @param duration the duration of the event in the schedule
   * @return a list of times representing the start and end times of the event
   */
  List<ITime> scheduleEvent(IUser user, int duration);

  /**
   * Order all the events in the user's schedule from earliest to latest.
   * @param user user whose schedule should be sorted.
   * @return a list of sorted events.
   */
  public List<IEvent> getOrderedEvents(IUser user);

  /**
   * Provides the correct time to schedule an event at.
   * @param duration duration of events
   * @param timeBetweenEvents mapping of free time between scheduled events
   * @return a list of times representing the start and end times of the event
   */
  List<ITime> getStartandEndTimes(int duration,
                                                Map<ITime, Integer> timeBetweenEvents);

  /**
   * Calculate the duration of free time between events.
   * @param thisNumDays this event's number of days
   * @param thatNumDays that event's number of days
   * @param thisNumHours this event's number of hours
   * @param thatNumHours that event's number of hours
   * @param thisNumMin this event's number of minutes
   * @param thatNumMin that event's number of minutes
   * @param durationMin the base duration to add this amount of free time to
   * @return a total duration for that part of the schedule that is free.
   */
  int calculateDuration(int thisNumDays, int thatNumDays, int thisNumHours,
                                       int thatNumHours, int thisNumMin, int thatNumMin,
                                       int durationMin);

  /**
   * Determine the right start and end time for an event of that duration
   * @param duration amount of time the event lasts
   * @param timeBetweenEvents mapping of when events end and how much free time there is after each one.
   * @param startTime when the week time constraints start
   * @param hours number of hours to add to any event's end time
   * @param newHours number of final hours to add to this event's end time
   * @param date the number of days to add to any event's end time
   * @param newDay number of final days to add to any event's end time
   * @param newMin final number of minutes to add to any event's end time
   * @param finalTimes list of times to schedule this event at
   */
  void findTimeForEvent(int duration, Map<ITime, Integer> timeBetweenEvents, ITime startTime, int hours,
                   int newHours, int date, Time.Day newDay, int newMin, List<ITime> finalTimes);
}
