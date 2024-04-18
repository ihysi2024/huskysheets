package model;

import java.util.List;
import java.util.Objects;

import cs3500.nuplanner.provider.model.IEventLocation;

public class EventLocationAdapter implements IEventLocation {

  private final model.IEvent adaptee;

  public EventLocationAdapter(model.IEvent adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Observes whether the event is online or not.
   *
   * @return Returns true if the event is online, and false otherwise.
   */
  @Override
  public boolean accessOnline() {
    return this.adaptee.getOnline();
  }

  /**
   * Observes the place of the event.
   *
   * @return Returns the place of the event as a string.
   */
  @Override
  public String accessPlace() {
    return this.adaptee.getLocation();
  }

  /**
   * Adds the fields of EventLocation (online, location) to a list.
   *
   * @return returns the list of the EventLocation fields
   */
  @Override
  public List<String> toList() {
    return null;
  }
}
