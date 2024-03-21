package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import controller.ViewFeatures;
import model.ReadOnlyPlanner;

public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;
  private JButton createEventButton;
  private JButton dummyButton;

  /**
   * Creates a view of the Simon game.
   * @param model desired model to represent Simon game
   */
  public ScheduleView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.panel = new PlannerPanel(model);
    this.add(panel);

    //panel.getPreferredSize();
    createEventButton = new JButton("Create Event");
    createEventButton.setActionCommand("Create Event");

    panel.add(createEventButton);
    this.setVisible(true);

    this.pack();
  }

  public void openEventView(ReadOnlyPlanner model) {
    EventPanel newEvent = new EventPanel(model);
    this.add(newEvent);
    createEventButton.setVisible(false);
    this.panel.setVisible(false);
    this.setVisible(true);
    System.out.println("Event created");
  }

  public void addFeatures(ViewFeatures features) {
    createEventButton.addActionListener(evt -> features.openEventView());

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
