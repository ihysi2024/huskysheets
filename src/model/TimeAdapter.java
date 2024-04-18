package model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.nuplanner.provider.model.IEventTime;

import static model.Time.indexToTime;

public class TimeAdapter implements IEventTime {
  private final ITime adaptee;

  public TimeAdapter(ITime adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  public static model.ITime convertToProviderTimeType(IEventTime time) {
    int newDayVal = time.accessDay().getValue();
    return indexToTime(newDayVal, time.accessTimeAsInt());
  }

  /**
   * Checks if the current event time is before the given event time.
   *
   * @param time Other event time to be compared.
   * @return Returns true if the current event time is before the given event time;
   * returns false otherwise.
   */
  @Override
  public boolean isBefore(IEventTime time) {
    ITime newTime = convertToProviderTimeType(time);
    return this.adaptee.compareTimes(newTime) < 0;
  }

  /**
   * Checks if the current event time is after the given event time.
   *
   * @param time Other event time to be compared.
   * @return Returns true if the current event time is after the given event time;
   * returns false otherwise.
   */
  @Override
  public boolean isAfter(IEventTime time) {
    ITime newTime = convertToProviderTimeType(time);
    return this.adaptee.compareTimes(newTime) > 0;
  }

  /**
   * Observes the day of the week in the EventTime object.
   *
   * @return Returns the day of the week (DayOfWeek type).
   */
  @Override
  public DayOfWeek accessDay() {
    int dayOfWeek = this.adaptee.getDate().getDayIdx();
    if (dayOfWeek == 0) {
      return DayOfWeek.SUNDAY;
    }
    if (dayOfWeek == 1) {
      return DayOfWeek.MONDAY;
    }
    if (dayOfWeek == 2) {
      return DayOfWeek.TUESDAY;
    }
    if (dayOfWeek == 3) {
      return DayOfWeek.WEDNESDAY;
    }
    if (dayOfWeek == 4) {
      return DayOfWeek.THURSDAY;
    }
    if (dayOfWeek == 5) {
      return DayOfWeek.FRIDAY;
    }
    if (dayOfWeek == 6) {
      return DayOfWeek.SATURDAY;
    }
    return null;
  }

  /**
   * Observes the hour and minute of the day as an integer object.
   *
   * @return Returns an integer value representing the time.
   */
  @Override
  public int accessTimeAsInt() {
    return this.accessHour() * 60 + this.accessMinute(); // not sure what this is
  }

  /**
   * Observes the hour of the day in EventTime object.
   *
   * @return Returns the hour of the day.
   */
  @Override
  public int accessHour() {
    return this.adaptee.getHours();
  }

  /**
   * Observes the minute of the hour in the EventTime object.
   *
   * @return Returns the minute of the hour.
   */
  @Override
  public int accessMinute() {
    return this.adaptee.getMinutes();
  }

  /**
   * Returns the time of the EventTime in the form of a string.
   *
   * @return Returns the string containing the time in the format "%02d:%02d".
   */
  @Override
  public String timeToString() {
    int mins = this.adaptee.getMinutes();
    int hrs = this.adaptee.getMinutes();
    String minsStr = "" + mins;
    String hrsStr = "" + hrs;
    if (mins < 10) {
      minsStr = "0" + mins;
    }

    if (hrs < 10) {
      hrsStr = "0" + hrs;
    }
    return hrsStr + ":" + minsStr;
  }

  /**
   * Creates a list of strings defining each line in an EventTime XML node.
   *
   * @return Returns the list of strings defining an EventTime.
   */
  @Override
  public List<String> toList() {
    return new ArrayList<>(List.of(this.adaptee.getDate().getDayString(),
            String.valueOf(this.adaptee.getHours()),
            String.valueOf(this.adaptee.getMinutes())));
  }
}
