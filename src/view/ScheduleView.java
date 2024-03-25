package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.*;

import controller.Controller;
import controller.ViewFeatures;
import model.Event;
import model.ReadOnlyPlanner;
import model.User;

import static model.User.makeEvent;

public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;
  private JButton createEventButton;

  //private JButton saveEventButton;

  /**
   * Creates a view of the Simon game.
   * @param model desired model to represent Simon game
   */
  public ScheduleView(ReadOnlyPlanner model) {
    //saveEventButton = new JButton("Create Event");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    System.out.println("SCHEDULEVIEW CONSTRUCTOR");
    this.panel = new PlannerPanel(model);
    this.add(panel);

    //panel.getPreferredSize();
    createEventButton = new JButton("Create Event");
    createEventButton.setActionCommand("Create Event");

    panel.add(createEventButton);
    this.setVisible(true);

    this.pack();
  }

  public void closeScheduleView(ReadOnlyPlanner model) {
    this.setVisible(false);
    //newEvent.createEvent(model);
    // remove the create button
    //createEventButton.setVisible(false);
    // make the event panel visible
    //this.panel.setVisible(false);
    System.out.println("Event created");

  }


  public void addFeatures(ViewFeatures features) {
    createEventButton.addActionListener(evt -> features.closeScheduleView());
    createEventButton.addActionListener(evt -> features.openEventView());
    // handle when a user has clicked on an event
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // open the event panel for the event they clicked on

      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });
  }

    //saveEventButton.addActionListener(evt -> features.createEvent());


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
    this.setVisible(true);
  }

}
