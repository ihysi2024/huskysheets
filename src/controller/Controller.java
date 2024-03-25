package controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Schedule;
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
    System.out.println("Close schedule");
    scheduleView.closeScheduleView(model);
  }
  @Override
  public void openEventView() {
    System.out.println("Open event");
    eventView.openEvent(model);
  }

  public void selectUserSchedule(String userName) {
    for (User user: model.getUsers()) {
      if (user.getName().equals(userName)) {
        scheduleView.displayUserSchedule(this.model, user);
      }
    }
  }

  public void openScheduleView() {
    scheduleView.openScheduleView(model);
  }

  public void modifyEvent(Event newEvent) {
    model.modifyEvent(eventView.storeOpenedEvent(), newEvent);
  }

  public void removeEvent() {
    eventView.removeEventFromSchedule(model);
  }

  @Override
  public void createEvent() {
    System.out.println("got to controller for event");
    eventView.createEvent(model);
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
  public void addCalendar(String filePath) {

  }

  @Override
  public void saveCalendars() {

  }

  @Override
  public void selectUser(String userName) {

  }
}