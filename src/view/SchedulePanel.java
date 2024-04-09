package view;
import java.awt.Dimension;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import controller.ViewFeatures;

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
   * @param model desired model to represent calendar system
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

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */

  @Override
  public void addFeatures(ViewFeatures features) {
    // if user presses schedule event button
    scheduleEvent.addActionListener(evt -> features.scheduleEventInPlanner());
    scheduleEvent.addActionListener(evt -> features.displayScheduleErrors());
    scheduleEvent.addActionListener(evt -> features.closeScheduleView());

  }

  /**
   * Get the user's input for the event name.
   * @return a String of the event name
   */

  @Override
  public String getEventNameInput() {
    return this.eventName.getText();
  }

  /**
   * Get the user's input for the event location.
   * @return a String of the location
   */

  @Override
  public String getLocationInput() {
    return this.location.getText();
  }

  /**
   * Observes the user's input for whether the event is online or not
   * @return whether the event is online
   */

  public boolean getOnline() {
    return (this.onlineMenu.getSelectedItem().toString().equals("True"));
  }

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the location
   */

  public List<String> getUsersInput() {
    return usersList.getSelectedValuesList();
  }

  /**
   * Observes how long the event is.
   * @return the length of the event
   */

  public int getDuration() {
    return Integer.parseInt(duration.getText());
  }

  /**
   * Empties the fields in the panel for the user to enter their own inputs.
   * @param host the host of the event.
   */

  public void resetSchedulePanel(String host) {
    this.eventName.setText("");
    this.location.setText("");
    this.duration.setText("");
    usersList.clearSelection();
    // always selecting this schedule's user as an invitee to event
    for (int index = 0; index < usersList.getModel().getSize(); index++) {
      if (usersList.getModel().getElementAt(index).equals(host)) {
        usersList.addSelectionInterval(index, index);
      }
    }
  }

  /**
   * Opens up the current user's schedule.
   */

  @Override
  public void openScheduleView() {
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

  /**
   * Closes the current schedule view.
   */

  public void closeScheduleView() {
    this.setVisible(false);
  }

  /**
   * Display errors that may arise should the user provide invalid inputs to the panel.
   */

  public void displayError() {
    int counter = 0;
    StringBuilder sb = new StringBuilder("");
    if (this.eventName.getText().isEmpty()) {
      sb.append("No name given to the event\n");
    }
    if (this.onlineMenu.getSelectedItem() == null) {
      sb = sb.append("Online menu not selected\n");
    }
    if (this.duration == null || this.duration.getText().isEmpty()) {
      sb = sb.append("No duration provided\n");
    }
    try {
      int dur = Integer.parseInt(this.duration.getText());
      if (dur <= 0) {
        sb = sb.append("Invalid duration provided. Must be positive integer\n");
      }
    }
    catch (IllegalArgumentException e) {
      sb = sb.append("Invalid duration provided. Must be positive integer\n");
    }
    if (this.usersList.getSelectedValuesList() == null ||
            this.usersList.getSelectedValuesList().isEmpty()) {
      sb = sb.append("No users selected\n");
    }
    if (this.location.getText() == null || this.location.getText().isEmpty()) {
      sb = sb.append("No location provided\n");
    }
    if (sb.length() != 0) {
      JOptionPane.showMessageDialog(this, sb,
              "Errors in scheduling the event: \n", JOptionPane.ERROR_MESSAGE);
    }
  }

}
