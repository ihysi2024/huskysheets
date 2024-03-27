package controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;
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
    for (IUser user: model.getUsers()) {
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

  public IEvent findEvent(ITime timeOfEvent) {
    for (IUser user: this.model.getUsers()) {
      IEvent tempEvent = model.retrieveUserScheduleAtTime(user, timeOfEvent);
      if (tempEvent != null) {
        return tempEvent;
      }

    }
    return null;
  }

  public void resetPanelView() {
    eventView.resetPanel();
  }

  public void populateEvent(IEvent event) {
    eventView.populateEventContents(event);
  }
  public void openScheduleView() {
    scheduleView.openScheduleView(model);
  }

  public void updatedEvent(IEvent oldEvent) {

  }

  public void modifyEvent(HashMap<String, String[]> eventMap, ReadOnlyPlanner model) {
    eventView.modifyEvent(eventMap, model);
  }


  public void removeEvent(IEvent eventToRemove) {
    System.out.println(scheduleView.getCurrentUser().getName());
    System.out.println("Remove event: ");
    System.out.println(scheduleView.getCurrentUser().getName());
    System.out.println("Remove event***: " + eventToRemove.eventToString());
    model.removeEventForRelevantUsers(eventToRemove, scheduleView.getCurrentUser());

  }

  @Override
  public void createEvent() {

    eventView.createEvent(model);
  }

  public HashMap<String, String[]> storeEvent() {
    return eventView.storeOpenedEventMap();
  }

  @Override
  public void quitEditingEvent() {

  }

  @Override
  public void addCalendar() {
    scheduleView.addCalendarInfo();
  }

  @Override
  public void saveCalendars() {
    scheduleView.saveCalendarInfo();
  }

  @Override
  public void selectUser(String userName) {

  }
}
