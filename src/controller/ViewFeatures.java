package controller;

import java.awt.*;
import java.util.List;

import model.Event;
import model.Time;

public interface ViewFeatures {

  void quit();

  // won't implement anything yet
  void scheduleEvent();

  void closeScheduleView();

  void openScheduleView();

  void openEventView();
  void createEvent();

  void modifyEvent(Event updatedEvent);

  void selectUserSchedule(String userName);

  // list of strings or just a string of users? somewhere would need to separate by newline/comma


  void removeEvent();

  void quitEditingEvent();

  void addCalendar(String filePath);

  void saveCalendars();

  void selectUser(String userName);
}
