package controller;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import model.Event;
import model.IEvent;
import model.ITime;
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

  void updatedEvent(IEvent updatedEvent);

  HashMap<String, String[]> storeEvent();

  void modifyEvent(IEvent event, ReadOnlyPlanner model);

  void populateEvent(IEvent event);

  void selectUserSchedule(String userName);

  // list of strings or just a string of users? somewhere would need to separate by newline/comma

  void closeEventView();

  void setCurrentUser();

  IEvent findEvent(ITime timeOfEvent);
  void removeEvent(IEvent eventToRemove);

  void resetPanelView();

  void quitEditingEvent();

  void addCalendar();

  void saveCalendars();

  void selectUser(String userName);

 // void addingDummyMethod();
}
