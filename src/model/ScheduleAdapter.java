package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.ISchedule;

import static model.EventAdapter.convertToProviderEventType;

public class ScheduleAdapter implements ISchedule {

  private final model.ISchedule adaptee;

  public ScheduleAdapter(model.ISchedule adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Adds an event to the schedule.
   *
   * @param event The event to be added to the schedule.
   */
  @Override
  public void addEvent(IEvent event) {
    model.IEvent eventToAdd = convertToProviderEventType(event);
    this.adaptee.addEvent(eventToAdd);
  }

  /**
   * Removes an event from the schedule.
   * If the user the schedule belongs to is the host, the event will be removed from
   * all schedules in the system. If the user is not the host, the event will only be
   * removed from their schedule.
   *
   * @param event The event to be removed from the schedule.
   */
  @Override
  public void removeEvent(IEvent event) {
    model.IEvent eventToRemove = convertToProviderEventType(event);
    this.adaptee.removeEvent(eventToRemove);
  }

  /**
   * Modifies an event in the schedule.
   *
   * @param originalEvent The original event to be modified.
   * @param modifiedEvent The modified event to be substituted for the original event.
   */
  @Override
  public void modifyEvent(IEvent originalEvent, IEvent modifiedEvent) {
    // our planner system is what modifies the event...
    model.IEvent origEvent = convertToProviderEventType(originalEvent);
    model.IEvent modEvent = convertToProviderEventType(modifiedEvent);
    for (model.IEvent eventInSched : this.adaptee.getEvents()) {
      if (eventInSched.equals(origEvent)) {
       // this.eventInSched.modifyEvent(origEvent, modEvent);

      }
    }

  }

  /**
   * Observes the list of events in a user's schedule.
   *
   * @return Returns a user's list of events.
   */
  @Override
  public List<IEvent> accessEvents() {
    List<model.IEvent> customerEvents = this.adaptee.getEvents();
    List<IEvent> providerEvents = new ArrayList<>();
    for (model.IEvent currEvent : customerEvents) {
      EventAdapter newEventAdapted = new EventAdapter(currEvent);
      providerEvents.add(newEventAdapted);
    }
    return providerEvents;
  }

  /**
   * Creates a list of strings defining each line in a Schedule XML file.
   *
   * @return Returns the list of strings defining a Schedule.
   */
  @Override
  public List<String> toList() {
    return null;
  }

  /**
   * Updates user schedule. If any events in the new schedule are overlapping with
   * the existing schedule, the operation will be suspended.
   *
   * @param newSchedule Schedule of new events to be added to the existing schedule.
   * @throws IllegalArgumentException if new schedule events are overlapping.
   */
  @Override
  public void updateSchedule(ISchedule newSchedule) {
    for (IEvent currEvent : newSchedule.accessEvents()) {
      model.IEvent newEvent = convertToProviderEventType(currEvent);
      this.adaptee.addEvent(newEvent);
    }
  }
}
