package view;

import javax.swing.*;

import controller.ViewFeatures;

import model.IUser;
import model.ReadOnlyPlanner;
public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;




  /**
   * Creates a view of the Simon game.
   * @param model desired model to represent Simon game
   */
  public ScheduleView(ReadOnlyPlanner model) {
    //saveEventButton = new JButton("Create Event");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.panel = new PlannerPanel(model);

    this.add(panel);
    this.setVisible(true);
    this.pack();
  }

  public void setCurrentUser() {
    panel.setCurrentUser();
  }
  public void closeScheduleView() {
    panel.closeScheduleView();
    //newEvent.createEvent(model);
    // remove the create button
    //createEventButton.setVisible(false);
    // make the event panel visible
    //this.panel.setVisible(false);

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
  public void openScheduleView() {
    panel.openScheduleView();
  }


  @Override
  public void displayUserSchedule(IUser userToShow) {
    panel.displayUserSchedule(userToShow);
  }

}
