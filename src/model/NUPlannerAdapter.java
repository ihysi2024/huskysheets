package model;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import cs3500.nuplanner.provider.model.ICentralSystem;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IEventTime;
import cs3500.nuplanner.provider.model.ISchedule;

import static model.Time.indexToTime;
import static model.TimeAdapter.convertToProviderTimeType;


public class NUPlannerAdapter implements ICentralSystem {
  private final PlannerSystem adaptee;

  public NUPlannerAdapter(PlannerSystem adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Adds a new user to the NUPlanner system along with their schedule.
   * If the user already exists, no action is taken.
   *
   * @param userSchedule The schedule of the user to be added.
   * @param userId       The unique identifier of the user.
   * @throws IllegalArgumentException if the userId already exists.
   */
  @Override
  public void addUser(ISchedule userSchedule, String userId) {
    model.ISchedule newSchedule = new Schedule(new ArrayList<>());
    for (IEvent eventInSched : userSchedule.accessEvents()) {
      newSchedule.addEvent(convertToProviderEventType(eventInSched));
    }
    IUser newUser = new User(userId, newSchedule);
    this.adaptee.addUser(newUser);
  }


  /**
   * Adds a new user to the NUPlanner system using a given XML schedule.
   * If the user already exists, the schedule is updated.
   *
   * @param xmlDoc The XML Document object containing a single schedule.
   */
  @Override
  public void addUser(Document xmlDoc) {
    this.adaptee.importScheduleFromXML(xmlDoc.getBaseURI());
  }

  /**
   * Removes a user from the NUPlanner system using their unique identifier.
   * If the specified userId does not exist in the system, an IllegalArgumentException is thrown.
   *
   * @param userId The unique identifier of the user to be removed.
   * @throws IllegalArgumentException if the userId does not exist in the system.
   * @throws IllegalArgumentException if the userId is empty.
   */
  @Override
  public void removeUser(String userId) {
    // don't have this functionality in our system??
  }


  /**
   * Creates a new event, adding it to the schedule of the event creator
   * and all invited participants. If the userId does not exist,
   * an IllegalArgumentException is thrown.
   *
   * @param userId         The unique identifier of the user creating the event.
   * @param event          The event to be created.
   * @param invitedUserIds List of unique identifiers for users invited to the event.
   * @throws IllegalArgumentException if the userId does not exist in the system
   *                                  or if any of the invitedUserIds do not exist.
   * @throws IllegalArgumentException if the userId is empty.
   */
  @Override
  public void createEvent(String userId, IEvent event, List<String> invitedUserIds) {
    this.adaptee.addEventForRelevantUsers(convertToProviderEventType(event));
  }


  /**
   * Modifies the details of an existing event within the NUPlanner system.
   * This requires both the original event to be modified and the new details
   * of the event. If the userId does not exist or the original event cannot be found,
   * an IllegalArgumentException is thrown.
   *
   * @param userId        The unique identifier of the user requesting the event modification.
   * @param originalEvent The original event before modification.
   * @param modifiedEvent The event with its new details.
   * @throws IllegalArgumentException if the userId does not exist in the system
   *                                  or the original event cannot be identified.
   */
  @Override
  public void modifyEvent(String userId, IEvent originalEvent, IEvent modifiedEvent) {
    adaptee.modifyEvent(convertToProviderEventType(originalEvent),
            convertToProviderEventType(modifiedEvent));
  }

  /**
   * Removes an event from the NUPlanner system.
   * This involves removing the event from all affected user schedules.
   * If the userId does not exist or the event cannot be found, an
   * IllegalArgumentException is thrown.
   *
   * @param userId The unique identifier of the user requesting the event removal.
   * @param event  The event to be removed.
   * @throws IllegalArgumentException if the userId does not exist in the system
   *                                  or the event cannot be identified.
   */
  @Override
  public void removeEvent(String userId, IEvent event) {
    IUser userToRemoveEventFor = getIUserFromUserName(userId);
    adaptee.removeEventForRelevantUsers(convertToProviderEventType(event), userToRemoveEventFor);
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
   * Convert customer's event into provider event type
   */
  private model.IEvent convertToProviderEventType(IEvent event) {
    Time startTime = indexToTime(event.accessStartDay().getValue(), event.accessStartTime().accessTimeAsInt());
    Time endTime = indexToTime(event.accessEndDay().getValue(), event.accessEndTime().accessTimeAsInt());
    String eventName = event.accessName();
    boolean online = event.accessLocation().accessOnline();
    String location = event.accessLocation().accessPlace();
    List<String> usersList = event.accessUsers();

    return new Event(eventName, startTime, endTime, online, location, usersList);
  }

  /**
   * checks if a given time slot is available for scheduling an event for a list of
   * users.
   *
   * @param startTime start time of the time slot.
   * @param endTime   end time of the time slot.
   * @param users     List of users that would be part of a potential event.
   * @return
   */
  @Override
  public boolean isTimeSlotAvailable(IEventTime startTime, IEventTime endTime, List<String> users) {
    return false;
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
    ITime timeOfEvent = convertToProviderTimeType(time);
    model.IEvent eventOccurring = this.adaptee.getUsers().get(0).getSchedule().eventOccurring(timeOfEvent);
    if (eventOccurring == null) {
      throw new IllegalArgumentException("no event at this time");
    }
    else {
      return new EventAdapter(eventOccurring);
    }
  }
}


