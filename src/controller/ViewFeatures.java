package controller;

import java.util.List;

import model.Time;

public interface ViewFeatures {

  void quit();

  // won't implement anything yet
  void scheduleEvent();

  void openEventView();
  void createEvent(String eventName, String startDay, String startTime,
                   String endDate, String endTime, String location, List<String> users);

  // list of strings or just a string of users? somewhere would need to separate by newline/comma


  void removeEvent();

  void quitEditingEvent();

  void addCalendar(String filePath);

  void saveCalendars();

  void selectUser(String userName);
}
