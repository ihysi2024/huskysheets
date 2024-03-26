package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
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

  private User currentUser;
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

    this.panel = new PlannerPanel(model);
    this.menuPanel = new JPanel();
    this.panel.setLayout(new BorderLayout());


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
    this.add(panel);
    panel.add(menuPanel, BorderLayout.SOUTH);
    this.setVisible(true);

    // add panel to bottom after schedule
    this.pack();
  }

  public void setCurrentUser(ReadOnlyPlanner model) {
    for (User user: model.getUsers()) {
      if (user.getName().equals(selectUserButton.getSelectedItem().toString())) {
        this.currentUser = user;
      }
    }
  }
  public void closeScheduleView(ReadOnlyPlanner model) {
    this.setVisible(false);
    //newEvent.createEvent(model);
    // remove the create button
    //createEventButton.setVisible(false);
    // make the event panel visible
    //this.panel.setVisible(false);

  }





  public void addFeatures(ViewFeatures features) {
    createEventButton.addActionListener(evt -> features.closeScheduleView());
    createEventButton.addActionListener(evt -> features.openEventView());
    selectUserButton.addActionListener(evt ->
            features.selectUserSchedule(selectUserButton.getSelectedItem().toString()));
    selectUserButton.addActionListener(evt -> features.setCurrentUser());

    // handle when a user has clicked on an event
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
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
    System.out.println(currentUser.getName());
    //String userName = selectUserButton.getSelectedItem().toString();
    for (User user: model.getUsers()) {
      if (user.getName().equals(currentUser.getName())) {
        System.out.println(user.getSchedule().getEvents().size());
        this.displayUserSchedule(model, user);
      }
    }
    this.setVisible(true);
  }


  @Override
  public void displayUserSchedule(ReadOnlyPlanner model, User userToShow) {

    this.panel.resetPanel();
    for (Event event: userToShow.getSchedule().getEvents()) {
      System.out.println("Event");
      //Graphics g = new Graphics();
      this.panel.paintEvent(panel.getGraphics(), event);
    }
    menuPanel.revalidate();
    menuPanel.repaint();
    panel.add(menuPanel, BorderLayout.SOUTH);
    this.add(panel);

  }

}
