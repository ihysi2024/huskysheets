package model;

/**
 * Represents a time, which includes a day of the week and the time up to minute granularity.
 * The day of the week is relative to this current week and is not associated with a specific
 * day of a month/year. Some of the main operations are comparing two times and converting
 * between Time and String (and vice versa).
 */
public interface ITime {
  /**
   * Getting this Time's hours.
   * @return this Time's hours.
   */
  int getHours();

  /**
   * Getting this Time's minutes.
   * @return this Time's minutes.
   */
  int getMinutes();

  /**
   * Getting this Time's day of the week.
   * @return this Time's day of the week.
   */
  Time.Day getDate();

  /**
   * compare two times and determine what their relation is to each other.
   * @param refTime other time to compare to
   * @return 0 if they are the same time
   *        -1 if this time comes before that time
   *         1 if this time comes after that time
   */
  int compareTimes(ITime refTime);


  /**
   * Calculates the # of minutes since midnight that have passed prior to event beginning.
   * If event starts at midnight, returns 0.
   *
   * @return # of minutes since midnight until beginning of event
   */
  int minutesSinceMidnight();

}