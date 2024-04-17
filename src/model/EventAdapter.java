package model;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IEventLocation;
import cs3500.nuplanner.provider.model.IEventTime;

import static model.Time.indexToTime;

public class EventAdapter implements IEvent {

  private final model.IEvent adaptee;


  public EventAdapter(model.IEvent adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Convert customer's event into provider event type
   */
  public static model.IEvent convertToProviderEventType(IEvent event) {
    Time startTime = indexToTime(event.accessStartDay().getValue(), event.accessStartTime().accessTimeAsInt());
    Time endTime = indexToTime(event.accessEndDay().getValue(), event.accessEndTime().accessTimeAsInt());
    String eventName = event.accessName();
    boolean online = event.accessLocation().accessOnline();
    String location = event.accessLocation().accessPlace();
    List<String> usersList = event.accessUsers();
    return new Event(eventName, startTime, endTime, online, location, usersList);
  }


  /**
   * Checks if given event overlaps current event.
   *
   * @param other The new event given to be compared.
   * @return Returns true if the two events are overlapping, and false otherwise.
   */
  @Override
  public boolean isOverlapping(IEvent other) {
    return this.adaptee.overlappingEvents(convertToProviderEventType(other));
  }

  /**
   * Checks if current event overlaps the start and end times of another event.
   *
   * @param otherStartTime The other event's start time.
   * @param otherEndTime   The other event's end time.
   * @return Returns true if the current event's start time and end times don't overlap with
   * the given start time and end time.
   */
  @Override
  public boolean isOverlapping(IEventTime otherStartTime, IEventTime otherEndTime) {
    //IEvent newEvent = new Event()
    return false;
  }

  /**
   * Checks if the given user is the host of the event
   * (is first in the list of users for the event).
   *
   * @param user The given user to be checked.
   * @return Returns true if given user is host; returns false otherwise.
   */
  @Override
  public boolean isHost(String user) {
    return this.adaptee.getUsers().get(0).equals(user);
  }

  /**
   * Observes the event name.
   *
   * @return Returns the name of the event.
   */
  @Override
  public String accessName() {
    return this.adaptee.getEventName();
  }

  /**
   * Observes the starting day of the event.
   *
   * @return Returns the event start day.
   */
  @Override
  public DayOfWeek accessStartDay() {
    // return this.adaptee.getStartTime().getDate().getDayIdx();
    return null;
  }

  /**
   * Observes the ending day of the event.
   *
   * @return Returns the event end day.
   */
  @Override
  public DayOfWeek accessEndDay() {
    return null;
  }

  /**
   * Observes the event start time.
   *
   * @return Returns the start time of the event.
   */
  @Override
  public IEventTime accessStartTime() {
    return null;
  }

  /**
   * Observes the event end time.
   *
   * @return Returns the end time of the event.
   */
  @Override
  public IEventTime accessEndTime() {
    return null;
  }

  /**
   * Observes the event location.
   *
   * @return Returns the location of the event.
   */
  @Override
  public IEventLocation accessLocation() {
    return null;
  }

  /**
   * Observes the list of users attending the event.
   *
   * @return Returns the event list of users.
   */
  @Override
  public List<String> accessUsers() {
    return this.adaptee.getUsers();
  }

  /**
   * Creates a list of strings defining each line in an event XML file.
   *
   * @return Returns the list of strings defining an event.
   */
  @Override
  public List<String> toList() {
    return null;
  }
}
