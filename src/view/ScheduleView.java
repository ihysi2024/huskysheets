package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.ReadOnlyPlanner;
import model.Time;
import model.User;

import static model.User.makeEvent;

public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;

  /**
   * Creates a view of the Simon game.
   * @param model desired model to represent Simon game
   */
  public ScheduleView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.panel = new PlannerPanel(model);
    this.add(panel);
    this.setVisible(true);
    this.pack();
  }

  public void setCurrentUser(ReadOnlyPlanner model) {
    panel.setCurrentUser(model);
  }
  public void closeScheduleView(ReadOnlyPlanner model) {
    panel.closeScheduleView(model);
  }

  public IUser getCurrentUser() {
    return panel.getCurrentUser();
  }


  public void addFeatures(ViewFeatures features) {
    panel.addFeatures(features);
  }

  @Override
  public void addClickListener(ViewFeatures features) {
    panel.addClickListener(features);
  }

  @Override
  public void addCalendarInfo() {
    panel.addCalendarInfo();
  }

  @Override
  public void saveCalendarInfo() {
    panel.saveCalendarInfo();

  }

  @Override
  public IEvent findEventAtTime(ITime timeOfEvent) {
    return this.panel.findEventAtTime(timeOfEvent);
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void openScheduleView(ReadOnlyPlanner model) {
    panel.openScheduleView(model);
  }


  @Override
  public void displayUserSchedule(ReadOnlyPlanner model, IUser userToShow) {
    panel.displayUserSchedule(model, userToShow);
  }

}
