package controller;

import java.util.List;

import model.PlannerSystem;
import view.IEventView;
import view.IScheduleView;


/**
 * Controller to control the functions of the Simon Game.
 */
public class Controller implements ViewFeatures {
  private final PlannerSystem model;

 // private final IScheduleView view;
   private final IEventView view;


  /**
   * Creates an instance of a Simon game controller to control user input (mouse clicks).
   * @param model Simon model
   * @param view Simon view
   */
  public Controller(PlannerSystem model, IEventView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatureListener(this);
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
  public void createEvent(String eventName, String startDay, String startTime,
                          String endDate, String endTime, String location, List<String> users) {

  }

  @Override
  public void modifyEvent(String eventName, String startDay, String startTime,
                          String endDate, String endTime, String location, List<String> users) {

  }

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