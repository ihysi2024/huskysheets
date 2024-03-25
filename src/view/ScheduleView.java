package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.*;

import controller.Controller;
import controller.ViewFeatures;
import model.Event;
import model.ReadOnlyPlanner;
import model.Time;
import model.User;

import static model.User.makeEvent;

public class ScheduleView extends JFrame implements IScheduleView {

  private final PlannerPanel panel;

  private final JPanel menuPanel;
  protected JButton createEventButton;
  protected JButton scheduleEventButton;
  protected JComboBox selectUserButton;

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
    this.menuPanel = new JPanel();
    this.panel.setLayout(new BorderLayout());
    this.add(panel);

    //panel.getPreferredSize();
    createEventButton = new JButton("Create Event");
    createEventButton.setActionCommand("Create Event");
    scheduleEventButton = new JButton("Schedule Event");
    scheduleEventButton.setActionCommand("Schedule Event")
    ;
    this.selectUserButton = new JComboBox();
    for (User user: model.getUsers()) {
      selectUserButton.addItem(user.getName());
    }
    selectUserButton.setActionCommand("Select User");
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
    menuPanel.add(selectUserButton);
    menuPanel.add(createEventButton);
    menuPanel.add(scheduleEventButton);
    panel.add(menuPanel, BorderLayout.SOUTH);
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
    selectUserButton.addActionListener(evt -> features.selectUserSchedule(selectUserButton.getSelectedItem().toString()));
    // handle when a user has clicked on an event
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("ADD FEATURES IN VIEW");
        Time timeOfEvent = panel.timeAtClick(e);
        try {
          Event eventClicked = features.findEvent(timeOfEvent);
          features.openEventView();
        }
        catch (NullPointerException ignored) {
        }

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

  @Override
  public void addClickListener(ViewFeatures features) {
    panel.addClickListener(features);
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
    String userName = selectUserButton.getSelectedItem().toString();
    for (User user: model.getUsers()) {
      if (user.getName().equals(userName)) {
        System.out.println(user.getName());
        this.displayUserSchedule(model, user);
      }
    }
    this.setVisible(true);
  }


  @Override
  public void displayUserSchedule(ReadOnlyPlanner model, User userToShow) {
    this.panel.resetPanel();
    this.add(panel);
    for (Event event: userToShow.getSchedule().getEvents()) {
      this.panel.paintEvent(getGraphics(), event);
    }
    menuPanel.revalidate();
    menuPanel.repaint();
    panel.add(menuPanel, BorderLayout.SOUTH);
    this.add(panel);

  }

}
