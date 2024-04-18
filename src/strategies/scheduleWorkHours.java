package strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.IEvent;
import model.ITime;
import model.IUser;
import model.Time;


/**
 * This variation of the schedule strategy allows the user to schedule an event as early
 * in the week as possible from 9 AM on Monday to 5 PM on Friday.
 */

public class scheduleWorkHours implements IScheduleStrategy {

  /**
   * Provides the times to schedule the given event at.
   * @param user the user who is attempting to schedule the event
   * @param duration the duration of the event in the schedule
   * @return a list of times representing the start and end times of the event
   */

  public List<ITime> scheduleEvent(IUser user, int duration) {
    // order all the event in a user's schedule that are during the work week
    List<IEvent> orderedEvents = getOrderedEvents(user);


    // maps the start at which "free time" exists between events, and how long
    // this free time lasts. Starts at 9 am on Monday.
    LinkedHashMap<ITime, Integer> timeBetweenEvents = new LinkedHashMap<>();
    ITime startWorkWeek = new Time(Time.Day.MONDAY, 9, 0);
    int durationMin = 0;

    // calculate the duration from 9 am on Monday morning to the earliest event
    // in the user's schedule
    durationMin = calculateDuration(startWorkWeek.getDate().getDayIdx(),
            orderedEvents.get(0).getStartTime().getDate().getDayIdx(),
            startWorkWeek.getHours(),
            orderedEvents.get(0).getStartTime().getHours(),
            startWorkWeek.getMinutes(),
            orderedEvents.get(0).getStartTime().getMinutes(),
            durationMin);

    timeBetweenEvents.put(startWorkWeek, durationMin);

    // for each event in the list, calculate the duration of free time between them
    for (int eventIdx = 0; eventIdx < orderedEvents.size() - 1; eventIdx++) {
      durationMin = 0;

      durationMin = calculateDuration(orderedEvents.get(eventIdx).getEndTime().getDate().getDayIdx(),
              orderedEvents.get(eventIdx + 1).getStartTime().getDate().getDayIdx(),
              orderedEvents.get(eventIdx).getEndTime().getHours(),
              orderedEvents.get(eventIdx + 1).getStartTime().getHours(),
              orderedEvents.get(eventIdx).getEndTime().getMinutes(),
              orderedEvents.get(eventIdx + 1).getStartTime().getMinutes(),
              durationMin);

      timeBetweenEvents.put(orderedEvents.get(eventIdx).getEndTime(), durationMin);

    }

    // find the earliest start time from that list of durations that can host the proposed event
    List<ITime> finalTimes = getStartandEndTimes(duration, timeBetweenEvents);

    return finalTimes;
  }

  /**
   * Provides the correct time to schedule an event at.
   * @param duration duration of events
   * @param timeBetweenEvents mapping of free time between scheduled events
   * @return a list of times representing the start and end times of the event
   */

  public List<ITime> getStartandEndTimes(int duration, Map<ITime, Integer> timeBetweenEvents) {
    ITime autStartTime = null;
    ITime autEndTime = null;
    int date = 0;
    int hours = 0;

    // translate a duration to a time
    // i.e. 90 minutes is 0 days 1 hour and 30 minutes
    int minutes = duration % 60;
    if (((duration - duration % 60) / 60) > 23) {
      date = (((duration - duration % 60) / 60) -
              (((duration - duration % 60) / 60) % 24)) / 24;
      hours = ((duration - duration % 60) / 60) % 24;
    }
    else {
      hours = (duration - duration % 60) / 60;
    }

    List<ITime> finalTimes = new ArrayList<>();
    // each section of free time in the user's schedule
    for (ITime startTime: timeBetweenEvents.keySet()) {
      if (finalTimes.isEmpty()) {
        Time.Day newDay = null;
        int newHours = 0;
        int newMin = 0;
        if (startTime.getMinutes() + minutes >= 60) {
          newMin = (startTime.getMinutes() + minutes) - 60;
          newHours++;
        }
        else {
          newMin = startTime.getMinutes() + minutes;
        }
        // if the chunk of free time is equal to or more than the desired
        // duration for the proposed event
        this.findTimeForEvent(duration, timeBetweenEvents, startTime, hours, newHours, date, newDay, newMin, finalTimes);
      }
    }
    return finalTimes;
  }

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

  public void findTimeForEvent(int duration, Map<ITime, Integer> timeBetweenEvents,
                               ITime startTime, int hours, int newHours, int date, Time.Day newDay,
                               int newMin, List<ITime> finalTimes) {
    ITime autStartTime;
    ITime autEndTime;
    if (timeBetweenEvents.get(startTime) >= duration) {

      for (Time.Day day : Time.Day.values()) {
        // in case number of hours getting added to start time pushes it to the next day
        if (startTime.getHours() + hours + newHours >= 24) {
          if (day.getDayIdx() == startTime.getDate().getDayIdx() + date) {
            newHours = 24 - (startTime.getHours() + hours + newHours);
            newDay = day;
          }
        } else {
          if (day.getDayIdx() == startTime.getDate().getDayIdx() + date) {
            newDay = day;
            newHours = newHours + startTime.getHours() + hours;
          }
        }
      }

      // make a new start time for the event that is the start time of the earliest
      // available section
      autStartTime = new Time(startTime.getDate(), startTime.getHours(), startTime.getMinutes());
      // make the end time the start time with the duration in compatible time format added
      autEndTime = new Time(newDay,
              newHours,
              newMin);

      finalTimes.add(autStartTime);
      finalTimes.add(autEndTime);
    }
  }

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

  public int calculateDuration(int thisNumDays, int thatNumDays, int thisNumHours,
                                       int thatNumHours, int thisNumMin, int thatNumMin,
                                       int durationMin) {
    // translate duration in days -> minutes, hours -> minutes, and minutes -> minutes
    durationMin += (thatNumDays - thisNumDays) * 24 * 60;
    durationMin += (thatNumHours - thisNumHours) * 60;
    durationMin += (thatNumMin - thisNumMin);
    return durationMin;
  }

  /**
   * Order all the events in the user's schedule from earliest to latest.
   * @param user user whose schedule should be sorted.
   * @return a list of sorted events.
   */

  public List<IEvent> getOrderedEvents(IUser user) {
    List<IEvent> withinWorkHours = new ArrayList<>();
    for (IEvent event : user.getSchedule().getEvents()) {
      if ((event.getStartTime().getDate().getDayIdx() != 0)
              && (event.getStartTime().getDate().getDayIdx() != 7)
              && (event.getStartTime().getHours() >= 9)
              && (event.getStartTime().getHours() <= 17)) {
        withinWorkHours.add(event);
      }
    }

    List<IEvent> orderedEvents = new ArrayList<>();
    orderedEvents.addAll(withinWorkHours);
    Collections.sort(orderedEvents, new Comparator<IEvent>() {
      public int compare(IEvent e1, IEvent e2) {
        return e1.getStartTime().compareTimes(e2.getStartTime());
      }
    });
    return orderedEvents;
  }
}

