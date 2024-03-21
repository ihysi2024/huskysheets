package view;

import javax.swing.*;

import controller.ViewFeatures;
import model.ReadOnlyPlanner;

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
    this.pack();
  }

  public void openEventView(ReadOnlyPlanner model) {
    EventPanel newEvent = new EventPanel(model);
    System.out.println("Event created");
    newEvent.setVisible(true);
  }



  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

}
