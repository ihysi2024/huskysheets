package controller;

import java.util.ArrayList;
import java.util.List;

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

  private IScheduleView view;
   // private final IEventView view;


  /**
   * Creates an instance of a Simon game controller to control user input (mouse clicks).
   * @param model Simon model
   */
  public Controller(PlannerSystem model) {
    this.model = model;
    this.model.addUser(new User("Prof. Lucia", new Schedule(new ArrayList<>())));
    this.model.addUser(new User("Me", new Schedule(new ArrayList<>())));
  }

  public void setView(IScheduleView v) {
    view = v;
    view.addFeatures(this);
  }

  public void goPlayGame() {
    this.view.display(true);
  }


  @Override
  public void quit() {

  }

  @Override
  public void scheduleEvent() {

  }

  @Override
  public void openEventView() {
    System.out.println("Got to controller");
    view.openEventView(model);
  }


  @Override
  public void createEvent(String eventName, String startDay, String startTime,
                          String endDate, String endTime, String location, List<String> users) {
  }

  /**
  @Override
  public void modifyEvent(String eventName, String startDay, String startTime,
                          String endDate, String endTime, String location, List<String> users) {

  }

   **/
  @Override
  public void removeEvent() {

  }

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