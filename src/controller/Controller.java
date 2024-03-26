package controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Schedule;
import model.Time;
import model.User;
import view.EventPanel;
import view.IEventView;
import view.IScheduleView;


/**
 * Controller to control the functions of the Simon Game.
 */
public class Controller implements ViewFeatures {
  private final PlannerSystem model;

  private IScheduleView scheduleView;

  private IEventView eventView;
   // private final IEventView view;


  /**
   * Creates an instance of a Simon game controller to control user input (mouse clicks).
   * @param model Simon model
   */
  public Controller(PlannerSystem model) {
    this.model = model;
  }

  public void setScheduleView(IScheduleView v) {
    scheduleView = v;
    scheduleView.addFeatures(this);
  }

  public void setEventView(IEventView v) {
    eventView = v;
    eventView.addFeatures(this);
  }

  public void goPlayGame() {

    this.scheduleView.addClickListener(this);
    this.scheduleView.display(true);

  }


  @Override
  public void quit() {

  }

  @Override
  public void scheduleEvent() {

  }


  public void closeScheduleView() {

    scheduleView.closeScheduleView(model);
  }
  @Override
  public void openEventView() {

    eventView.openEvent(model);
  }

  public void selectUserSchedule(String userName) {
    for (User user: model.getUsers()) {
      if (user.getName().equals(userName)) {
        scheduleView.displayUserSchedule(this.model, user);
      }
    }
  }

  public void closeEventView() {
    eventView.closeEvent();
  }

  public void setCurrentUser() {
    scheduleView.setCurrentUser(model);
  }

  public Event findEvent(Time timeOfEvent) {
    for (User user: this.model.getUsers()) {
      Event tempEvent = model.retrieveUserScheduleAtTime(user, timeOfEvent);
      if (tempEvent != null) {
        return tempEvent;
      }

    }
    return null;
  }

  public void populateEvent(Event event) {
    eventView.populateEventInPanel(event);
  }
  public void openScheduleView() {
    scheduleView.openScheduleView(model);
  }

  public void updatedEvent(Event oldEvent) {

    //model.modifyEvent(eventView.storeOpenedEvent(), newEvent);
  }

  public void modifyEvent(Event oldEvent, Event newEvent) {
    model.modifyEvent(oldEvent, newEvent);
  }


  public void removeEvent(Event eventToRemove) {
    System.out.println(scheduleView.getCurrentUser().getName());
    System.out.println("Remove event: ");
    System.out.println(scheduleView.getCurrentUser().getName());
    System.out.println("Remove event***: " + eventToRemove.eventToString());
    model.removeEventForRelevantUsers(eventToRemove, scheduleView.getCurrentUser());

    //eventView.removeEventFromSchedule(model, eventToRemove, scheduleView.getCurrentUser());
  }

  @Override
  public void createEvent() {

    eventView.createEvent(model);
  }

  public Event storeEvent() {
    return eventView.storeOpenedEvent();
  }

  /**
  @Override
  public void modifyEvent(String eventName, String startDay, String startTime,
                          String endDate, String endTime, String location, List<String> users) {

  }

   **/

  @Override
  public void quitEditingEvent() {

  }

  @Override
  public void addCalendar() {
   // System.out.println("file path selected: " + filePath);
    scheduleView.addCalendarInfo();

  }

  @Override
  public void saveCalendars() {

  }

  @Override
  public void selectUser(String userName) {

  }
}

/*
 QUESTIONS TO ASK:
 - methods in frame vs panel?
 - glitching with selecting user
 */
