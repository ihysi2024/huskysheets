package cs3500.nuplanner.provider.model;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Represents the time of an event by day of the week, hour, and minute.
 */
public interface IEventTime {

  /**
   * Checks if the current event time is before the given event time.
   *
   * @param time Other event time to be compared.
   * @return Returns true if the current event time is before the given event time;
   *        returns false otherwise.
   */
  boolean isBefore(IEventTime time);

  /**
   * Checks if the current event time is after the given event time.
   *
   * @param time Other event time to be compared.
   * @return Returns true if the current event time is after the given event time;
   *        returns false otherwise.
   */
  boolean isAfter(IEventTime time);

  /**
   * Observes the day of the week in the EventTime object.
   *
   * @return Returns the day of the week (DayOfWeek type).
   */
  DayOfWeek accessDay();

  /**
   * Observes the hour and minute of the day as an integer object.
   *
   * @return Returns an integer value representing the time.
   */
  int accessTimeAsInt();

  /**
   * Observes the hour of the day in EventTime object.
   * @return Returns the hour of the day.
   */
  int accessHour();

  /**
   * Observes the minute of the hour in the EventTime object.
   *
   * @return Returns the minute of the hour.
   */
  int accessMinute();

  /**
   * Returns the EventTime in the form of a textual view as a string.
   *
   * @return Returns the string containing the textual view format.
   */
  String toString();

  /**
   * Returns the time of the EventTime in the form of a string.
   * @return Returns the string containing the time in the format "%02d:%02d".
   */
  String timeToString();

  /**
   * Creates a list of strings defining each line in an EventTime XML node.
   *
   * @return Returns the list of strings defining an EventTime.
   */
  List<String> toList();
}
