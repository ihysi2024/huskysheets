package view;

import java.awt.Dimension;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.BoxLayout;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.ITime;
import model.IUser;

import model.ReadOnlyPlanner;


public class SchedulePanel extends JPanel implements IScheduleView {

  private final ReadOnlyPlanner model;

  /**
   * BUTTON FIELDS.
   */
  private final JComboBox<String> onlineMenu;
  private final JButton scheduleEvent;
  private IScheduleTextView textV;

  /**
   * TEXT FIELDS.
   */
  private final JTextField eventName;

  private final JTextField location;

  private final JTextField duration;
  private final JLabel durationLabel;
  private final JList<String> usersList;

  /**
   * Creates a panel that will house the input labels, buttons, and text fields for the user to
   * * create/modify/remove an event.
   *
   * @param model desired model to represent Simon game
   */
  public SchedulePanel(ReadOnlyPlanner model) {
    this.model = Objects.requireNonNull(model);

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    JLabel eventNameLabel = new JLabel("Event Name:");
    this.add(eventNameLabel);

    eventName = new JTextField(5);
    this.add(eventName);

    JLabel onlineLabel = new JLabel("Online:");
    this.add(onlineLabel);

    onlineMenu = new JComboBox<>();
    onlineMenu.addItem("True");
    onlineMenu.addItem("False");

    this.add(onlineMenu);

    JLabel locationLabel = new JLabel("Location:");
    this.add(locationLabel);

    location = new JTextField(5);
    this.add(location);

    durationLabel = new JLabel("Duration in minutes:");
    this.add(durationLabel);

    duration = new JTextField(5);
    this.add(duration);

    JLabel usersListLabel = new JLabel("User List:");
    this.add(usersListLabel);

    DefaultListModel<String> allUsers = new DefaultListModel<>();

    for (IUser user : model.getUsers()) {
      allUsers.addElement(user.getName());
    }
    usersList = new JList<>(allUsers);
    this.add(usersList);

    /*
     * Add buttons to extend the ability for a user to create, modify or remove
     * an event.
     */
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    scheduleEvent = new JButton("Schedule Event");
    scheduleEvent.setVisible(true);

    buttonPanel.add(scheduleEvent);
    buttonPanel.setVisible(true);
    this.add(buttonPanel);

    setVisible(true);

    // NOTE: PlannerSystem model is only integrated for code reuse purposes.
    // HW8 requires that create event, modify event, and remove event print
    // the event's information to the console, which is a method implemented
    // in the IScheduleTextView interface and ScheduleTextView class.

  }
  @Override
  public void addFeatures(ViewFeatures features) {
    // if user presses schedule event button
    scheduleEvent.addActionListener(evt -> features.scheduleEventInPlanner());
    scheduleEvent.addActionListener(evt -> features.closeScheduleView());

  }

  @Override
  public String getEventNameInput() {
    return this.eventName.getText();
  }

  @Override
  public String getLocationInput() {
    return this.location.getText();
  }

  public boolean getOnline() {
    return (this.onlineMenu.getSelectedItem().equals("True"));
  }
  public List<String> getUsersInput() {
    return usersList.getSelectedValuesList();
  }

  public int getDuration() {
    return Integer.parseInt(duration.getText());
  }

  @Override
  public void openScheduleView() {
    //System.out.println("HERE");
    //this.setVisible(true);
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   *
   * @return Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(350, 350);
  }

  /**
   * Conceptually, we can choose a different coordinate system
   * and pretend that our panel is 40x40 "cells" big. You can choose
   * any dimension you want here, including the same as your physical
   * size (in which case each logical pixel will be the same size as a physical
   * pixel, but perhaps your calculations to position things might be trickier)
   *
   * @return Our preferred *logical* size.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(100, 100);
  }

  public void addScheduleAtTime(IUser user, ITime startTime, ITime endTime) {
    IEvent eventToAdd = new Event(this.getEventNameInput(),
            startTime, endTime,
            this.getOnline(),
            this.getLocationInput(),
            this.getUsersInput());

    user.addEventForUser(eventToAdd);
    // make an event with the information given by the user and
    // the times provided by the controller
  }

  public void closeScheduleView() {
    this.setVisible(false);
  }

}
