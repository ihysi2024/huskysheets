package view;

import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import controller.ViewFeatures;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.ReadOnlyPlanner;
import model.Time;

public class ScheduleView extends JFrame implements IScheduleView {
  private final SchedulePanel panel;

  /**
   * Creates a view of the Simon game.
   *
   * @param model desired model to represent Simon game
   */
  public ScheduleView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new SchedulePanel(model);
    this.add(panel);
    this.setVisible(false);
    this.pack();
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    panel.addFeatures(features);
  }

  @Override
  public String getEventNameInput() {
    return panel.getEventNameInput();
  }

  @Override
  public String getLocationInput() {
    return panel.getLocationInput();
  }

  @Override
  public List<String> getUsersInput() {
    return panel.getUsersInput();
  }

  public boolean getOnline() {
    return panel.getOnline();
  }


  @Override
  public void openScheduleView() {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }

  public int getDuration() {
    return panel.getDuration();
  }

  public void addScheduleAtTime(IUser user, ITime startTime, ITime endTime) {
    panel.addScheduleAtTime(user, startTime, endTime);
    // make an event with the information given by the user and
    // the times provided by the controller
  }

  public void closeScheduleView() {
    this.setVisible(false);
  }
}


