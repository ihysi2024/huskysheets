package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.Time;

public class scheduleAnyTime implements IScheduleStrategy{

  public List<ITime> scheduleEvent(IUser user, int duration) {
    // order all the event in a user's schedule that are during the work week
    List<IEvent> orderedEvents = getOrderedEvents(user);

    // maps the start at which "free time" exists between events, and how long
    // this free time lasts. Starts at 9 am on Monday.
    LinkedHashMap<ITime, Integer> timeBetweenEvents = new LinkedHashMap<>();
    int durationMin = 0;

    if (orderedEvents.get(orderedEvents.size() - 1).getEndTime().compareTimes(orderedEvents.get(0).getStartTime()) < 0) {
      durationMin = calculateDuration(orderedEvents.get(orderedEvents.size() - 1).getEndTime().getDate().getDayIdx(),
              orderedEvents.get(0).getStartTime().getDate().getDayIdx(),
              orderedEvents.get(orderedEvents.size() - 1).getEndTime().getHours(),
              orderedEvents.get(0).getStartTime().getHours(),
              orderedEvents.get(orderedEvents.size() - 1).getEndTime().getMinutes(),
              orderedEvents.get(0).getStartTime().getMinutes(),
              durationMin);
    }

    timeBetweenEvents.put(orderedEvents.get(orderedEvents.size() - 1).getEndTime(), durationMin);

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

  private static List<ITime> getStartandEndTimes(int duration, HashMap<ITime, Integer> timeBetweenEvents) {
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
      while (finalTimes.isEmpty()) {
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
    }
    return finalTimes;
  }

  private static int calculateDuration(int thisNumDays, int thatNumDays, int thisNumHours,
                                       int thatNumHours, int thisNumMin, int thatNumMin,
                                       int durationMin) {
    // translate duration in days -> minutes, hours -> minutes, and minutes -> minutes
    durationMin += (thatNumDays - thisNumDays) * 24 * 60;
    durationMin += (thatNumHours - thisNumHours) * 60;
    durationMin += (thatNumMin - thisNumMin);
    return durationMin;
  }

  private static List<IEvent> getOrderedEvents(IUser user) {
    List<IEvent> orderedEvents = new ArrayList<>();
    // grab the first event for comparison purposes
    orderedEvents.add(user.getSchedule().getEvents().get(0));

    // add all events in this user's schedule that exist during work hours
    // order them from earliest in the week to latest
    for (IEvent event: user.getSchedule().getEvents()) {
      if ((orderedEvents.get(orderedEvents.size() - 1).getStartTime().compareTimes(event.getStartTime())) == 0) {
        // don't allow it to be added again since event is already in the list
      } else if ((orderedEvents.get(orderedEvents.size() - 1).getStartTime().compareTimes(event.getStartTime())) > 0) {
        // if the event is earlier than the last event in the list, remove the last event from
        // the list, add this event to the list, and then add back in the one you just removed
        IEvent tempEvent = new Event(orderedEvents.get(orderedEvents.size() - 1).getEventName(),
                orderedEvents.get(orderedEvents.size() - 1).getStartTime(),
                orderedEvents.get(orderedEvents.size() - 1).getEndTime(),
                orderedEvents.get(orderedEvents.size() - 1).getOnline(),
                orderedEvents.get(orderedEvents.size() - 1).getLocation(),
                orderedEvents.get(orderedEvents.size() - 1).getUsers());
        orderedEvents.remove(orderedEvents.size() - 1);
        orderedEvents.add(event);
        orderedEvents.add(tempEvent);
      } else {
        // this event already comes after the one already in the list,
        // you can just add it normally
        orderedEvents.add(event);
      }
    }
    return orderedEvents;
  }

}
