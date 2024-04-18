package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IEventTime;
import cs3500.nuplanner.provider.model.IReadOnlyCentralSystem;
import cs3500.nuplanner.provider.model.ISchedule;

public class ReadOnlyNUPlannerAdapter implements IReadOnlyCentralSystem {

  private final ReadOnlyPlanner adaptee;

  public ReadOnlyNUPlannerAdapter(ReadOnlyPlanner adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Observes the map of user ids and user schedules found in the central system.
   *
   * @return Returns map of user ids and user schedules.
   */
  @Override
  public Map<String, ISchedule> accessUserSchedules() {
    Map<String, ISchedule> userSchedules = new HashMap<>();
    for (IUser user : adaptee.getUsers()) {
      ISchedule newScheduleAdapted = new ScheduleAdapter(user.getSchedule());
      userSchedules.put(user.getName(), newScheduleAdapted);
    }
    return userSchedules;
  }

  /**
   * Observes the list of users in the planner.
   *
   * @returns Returns a list of users as strings.
   */
  @Override
  public List<String> accessUsers() {
    List<String> usersList = new ArrayList<>();
    for (IUser user : adaptee.getUsers()) {
      usersList.add(user.getName());
    }
    return usersList;
  }

  /**
   * Observes the events in a given user's schedule.
   *
   * @param userId String of a user's name.
   * @return Returns a list of events on the user's schedule.
   */
  @Override
  public List<IEvent> accessEvents(String userId) {
    List<model.IEvent> events = this.adaptee.retrieveUserEvents(getIUserFromUserName(userId));
    List<IEvent> accessedEvents = new ArrayList<>();
    for (model.IEvent currEvent : events) {
      IEvent newEventAdapted = new EventAdapter(currEvent);
      accessedEvents.add(newEventAdapted);
    }
    return accessedEvents;
  }

  private IUser getIUserFromUserName(String userName) {
    List<IUser> userArr = adaptee.getUsers();
    int idxOfUser = 0;
    for (int userIdx = 0; userIdx < userArr.size(); userIdx++) {
      if (userArr.get(userIdx).getName().equals(userName)) {
        idxOfUser = userIdx;
      }
    }
    return userArr.get(idxOfUser);
  }

  /**
   * Creates a list of strings defining each line in a Schedule XML File for
   * a given user.
   *
   * @param userId String of a user's name.
   * @return Returns a list of strings for a specific user's schedule.
   */
  @Override
  public List<String> toList(String userId) {
    return null;
  }

  /**
   * Checks if an event conflicts with the existing schedules for all of the
   * event's invited users.
   *
   * @param event New event being checked.
   * @return Returns true if the event conflicts; returns false if otherwise.
   */
  @Override
  public boolean eventConflict(IEvent event) {
    return false;
  }

  /**
   * Checks if there is an event at a certain time on a given user's schedule.
   *
   * @param userId String of a user's name
   * @param time   a time of an event
   * @return returns an instance of an event.
   * @throws IllegalArgumentException if no event exist during the given event time
   */
  @Override
  public IEvent findEvent(String userId, IEventTime time) {
    return null;
  }
}
