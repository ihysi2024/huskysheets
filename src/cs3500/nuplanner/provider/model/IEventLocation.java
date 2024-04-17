package cs3500.nuplanner.provider.model;

import java.util.List;

/**
 * Represents the location of an event. The online element represents whether
 * the event is also online. The place element represents the location of the event.
 */
public interface IEventLocation {

  /**
   * Observes whether the event is online or not.
   *
   * @return Returns true if the event is online, and false otherwise.
   */
  boolean accessOnline();

  /**
   * Observes the place of the event.
   *
   * @return Returns the place of the event as a string.
   */
  String accessPlace();

  /**
   * Returns the EventLocation in the form of a textual view as a string.
   *
   * @return Returns the string containing the textual view format.
   */
  String toString();

  /**
   * Adds the fields of EventLocation (online, location) to a list.
   *
   * @return returns the list of the EventLocation fields
   */
  List<String> toList();
}
