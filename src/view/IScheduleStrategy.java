package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ITime;
import model.IUser;
import model.Time;

public interface IScheduleStrategy {

  List<ITime> scheduleEvent(IUser user, int duration);

  public static List<ITime> getStartandEndTimes(int duration, HashMap<ITime, Integer> timeBetweenEvents) {
    ITime autStartTime = null;
    ITime autEndTime = null;

    int date = 0;
    int hours = 0;
    Time.Day newDay = null;

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

    // each section of free time in the user's schedule
    for (ITime startTime: timeBetweenEvents.keySet()) {
      // if the chunk of free time is equal to or more than the desired
      // duration for the proposed event
      if (timeBetweenEvents.get(startTime) <= duration) {
        for (Time.Day day: Time.Day.values()) {
          if (day.getDayIdx() == startTime.getDate().getDayIdx() + date) {
            newDay = day;
          }
        }
        // make a new start time for the event that is the start time of the earliest
        // available section
        autStartTime = new Time(startTime.getDate(), startTime.getHours(), startTime.getMinutes());
        // make the end time the start time with the duration in compatible time format added
        autEndTime = new Time(newDay,
                startTime.getHours() + hours,
                startTime.getMinutes() + minutes);
      }
    }

    List<ITime> finalTimes = new ArrayList<>();
    finalTimes.add(autStartTime);
    finalTimes.add(autEndTime);
    return finalTimes;
  }

  public static int calculateDuration(int thisNumDays, int thatNumDays, int thisNumHours,
                                       int thatNumHours, int thisNumMin, int thatNumMin,
                                       int durationMin) {
    // translate duration in days -> minutes, hours -> minutes, and minutes -> minutes
    durationMin += (thatNumDays - thisNumDays) * 24 * 60;
    durationMin += (thatNumHours - thisNumHours) * 60;
    durationMin += (thatNumMin - thisNumMin);
    return durationMin;
  }
}
