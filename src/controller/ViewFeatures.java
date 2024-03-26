package controller;

import java.awt.*;
import java.util.List;

import model.Event;
import model.ReadOnlyPlanner;
import model.Time;

public interface ViewFeatures {

  void quit();

  // won't implement anything yet
  void scheduleEvent();

  void closeScheduleView();

  void openScheduleView();

  void openEventView();
  void createEvent();

  void updatedEvent(Event updatedEvent);

  Event storeEvent();

  void modifyEvent(Event oldEvent, Event newEvent);

  void populateEvent(Event event);

  void selectUserSchedule(String userName);

  // list of strings or just a string of users? somewhere would need to separate by newline/comma

  void closeEventView();

  void setCurrentUser();

  Event findEvent(Time timeOfEvent);
  void removeEvent(Event eventToRemove);

  void quitEditingEvent();

  void addCalendar();

  void saveCalendars();

  void selectUser(String userName);
}
