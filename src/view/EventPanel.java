package view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import java.util.HashMap;

import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.event.MouseInputAdapter;

import controller.ViewFeatures;
import model.IEvent;
import model.IUser;
import model.NUPlanner;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Time;

import static model.User.makeEvent;

/**
 * EventPanel represents the panel where a user can create/modify/remove an event.
 * Allows the user to input a name, location, start time, end time, and list of users.
 */
public class EventPanel extends JPanel implements IEventView {

  private final ReadOnlyPlanner model;

  /**
   * BUTTON FIELDS.
   */
  private final JComboBox<String> onlineMenu;

  private final JComboBox<Time.Day> startDay;
  private final JComboBox<Time.Day> endDay;

  private final JButton modifyEvent;
  private final JButton removeEvent;
  private final JButton saveEvent;
  private String currHost;

  private IEvent originalEvent;

  /**
   * TEXT FIELDS.
   */
  private final JTextField eventName;

  private final JTextField location;

  private final JTextField startTime;

  private final JTextField endTime;
  private final JList<String> usersList;

  /**
   * Creates a panel that will house the input labels, buttons, and text fields for the user to
   * * create/modify/remove an event.
   *
   * @param model desired model to represent Simon game
   */
  public EventPanel(ReadOnlyPlanner model) {
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

    JLabel startDayLabel = new JLabel("Start Day:");
    this.add(startDayLabel);

    startDay = new JComboBox<>();

    for (Time.Day day : Time.Day.values()) {
      startDay.addItem(day);
    }

    this.add(startDay);

    JLabel startTimeLabel = new JLabel("Start Time:");
    this.add(startTimeLabel);

    startTime = new JTextField(5);
    this.add(startTime);

    JLabel endDayLabel = new JLabel("End Day:");
    this.add(endDayLabel);

    endDay = new JComboBox();

    for (Time.Day day : Time.Day.values()) {
      endDay.addItem(day);
    }

    this.add(endDay);

    JLabel endTimeLabel = new JLabel("End Time:");
    this.add(endTimeLabel);

    endTime = new JTextField(5);
    this.add(endTime);

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
    modifyEvent = new JButton("Modify Event");
    modifyEvent.setVisible(true);
    removeEvent = new JButton("Remove Event");
    removeEvent.setVisible(true);
    saveEvent = new JButton("Create Event");
    saveEvent.setVisible(true);
    buttonPanel.add(saveEvent);
    buttonPanel.add(modifyEvent);
    buttonPanel.add(removeEvent);
    buttonPanel.setVisible(true);

    this.add(buttonPanel);

    setVisible(true);

    // NOTE: PlannerSystem model is only integrated for code reuse purposes.
    // HW8 requires that create event, modify event, and remove event print
    // the event's information to the console, which is a method implemented
    // in the IScheduleTextView interface and ScheduleTextView class.

  }

  /**
   * Get current user
   */
  private String getCurrUser() {
    return model.getHost();
   // return new PlannerView(model).getCurrentUser().getName();
  }

  /**
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   *
   * @param event event to visualize in the event panel.
   */
  public void populateEventContents(IEvent event) {
    eventName.setText(event.getEventName());
    startDay.setSelectedIndex(event.getStartTime().getDate().getDayIdx());
    startTime.setText(event.getStartTime().getHours()
            + String.valueOf(event.getStartTime().getMinutes()));
    endDay.setSelectedIndex(event.getEndTime().getDate().getDayIdx());
    endTime.setText(event.getEndTime().getHours()
            + String.valueOf(event.getEndTime().getMinutes()));
    location.setText(event.getLocation());

    System.out.println("Event online status stored: " + event.getOnline());
    if (event.getOnline()) {
      onlineMenu.setSelectedIndex(0);
    } else {
      onlineMenu.setSelectedIndex(1);
    }

    // highlighting attendees of event
    usersList.clearSelection();
    for (int plannerUserIdx = 0; plannerUserIdx < usersList.getModel().getSize(); plannerUserIdx++) {
      String currPlannerUserName = usersList.getModel().getElementAt(plannerUserIdx);
      if (event.getUsers().contains(currPlannerUserName)) {
        usersList.addSelectionInterval(plannerUserIdx, plannerUserIdx);
      }
    }
    this.originalEvent = event;

  }

  /**
   * Get current user.
   */
  // public

  /**
   * Get the user's input for the event name.
   *
   * @return a String[] of the event name
   */
  public String[] getEventNameInput() {
    return new String[]{eventName.getText()};
  }

  /**
   * Get the user's input for the event time.
   *
   * @return a String[] of the event time
   */
  public String[] getTimeInput() {
    return new String[]{startDay.getSelectedItem().toString(), this.startTime.getText(),
            endDay.getSelectedItem().toString(), this.endTime.getText()};
  }

  /**
   * Get the user's input for the event location.
   *
   * @return a String[] of the location
   */
  public String[] getLocationInput() {
    return new String[]{onlineMenu.getSelectedItem().toString(), location.getText()};
  }

  /**
   * Get the user's input for the event list of users.
   * Makes sure that the host of the event (the current user) is at the front of the user list.
   *
   * @return a String[] of the user list
   */
  public String[] getUsersInput() {
    return usersList.getSelectedValuesList().toArray(new String[0]);
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   *
   * @return Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(350, 400);
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
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created
   */
  public void resetPanel() {
    eventName.setText("");
    startTime.setText("");
    endTime.setText("");
    location.setText("");

   // this.updateUserList();
    usersList.clearSelection();
    // always selecting this schedule's user as an invitee to event
    for (int index = 0; index < usersList.getModel().getSize(); index++) {
      if (usersList.getModel().getElementAt(index).equals(this.currHost)) {
        usersList.addSelectionInterval(index, index);
      }
    }
  }

  /**
   * Open the event view for the user to see.
   */
  public void openEvent(String host) {
    this.updateUserListNoHostChange();
   // this.currHost = host;
   // this.updateUserList();

    // this.updateUserList();
  }

  /**
   * Open the event view for the user to see.
   */
  public void openBlankEvent(String host) {
    this.currHost = host;
    this.updateUserList();

    // this.updateUserList();
  }



  /**
   * Updates list of users in event view, putting this user as the first element in the list.
   * Used because host is always the first
   */
  @Override
  public void updateUserList() {
    DefaultListModel<String> newUsers = new DefaultListModel<>();
    for (IUser user : model.getUsers()) {
      newUsers.addElement(user.getName());
    }
    if (this.currHost != null) {
      newUsers.removeElement(this.currHost);
      newUsers.add(0, this.currHost);
    }
    usersList.setModel(newUsers);

  }

  /**
   * Updates list of users in event view, putting this user as the first element in the list.
   * Used because host is always the first
   */
  public void updateUserListNoHostChange() {
    DefaultListModel<String> newUsers = new DefaultListModel<>();
    for (IUser user : model.getUsers()) {
      newUsers.addElement(user.getName());
    }
    usersList.setModel(newUsers);

  }

  /**
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in center, width and height
   * our logical size).
   *
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(1, -1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }

  /**
   * Allow the user to interact with the calendar through the features present
   * in the event view.
   *
   * @param features functionality that the user has access to through the event view.
   */
  @Override
  public void addFeatures(ViewFeatures features) {
    saveEvent.addActionListener(evt -> features.createEvent());
    saveEvent.addActionListener(evt -> features.displayEventCreateErrors());
    saveEvent.addActionListener(evt -> features.closeEventView());
    saveEvent.addActionListener(evt -> features.openPlannerView());

    //removeEvent.addActionListener(evt -> System.out.println(makeEvent(features.storeEvent())));
    removeEvent.addActionListener(evt -> features.removeEvent(makeEvent(features.storeEvent())));
    removeEvent.addActionListener(evt -> features.displayEventRemoveErrors(features.storeEvent()));
    removeEvent.addActionListener(evt -> features.closeEventView());
    removeEvent.addActionListener(evt -> features.openPlannerView());

    modifyEvent.addActionListener(evt -> features.modifyEvent(this.originalEvent, this.getCurrEvent()));
    modifyEvent.addActionListener(evt -> features.removeEvent(this.getCurrEvent()));
    modifyEvent.addActionListener(evt -> features.displayEventCreateErrors());
    modifyEvent.addActionListener(evt -> features.displayEventModifyErrors());
    modifyEvent.addActionListener(evt -> features.closeEventView());
    modifyEvent.addActionListener(evt -> features.openPlannerView());
  }

  /**
   * Gets current event
   */
  public IEvent getCurrEvent() {
    return makeEvent(this.storeOpenedEventMap());
  }

  /**
   * Close the event view so it stops being visible.
   */

  @Override
  public void closeEvent() {
    this.setVisible(false);
  }

  /**
   * Store the current event's inputs as a map of String -> String[]
   * Useful for modifying an event with the current panel inputs.
   *
   * @return a map of strings to string[]
   */

  public Map<String, String[]> storeOpenedEventMap() {
    HashMap<String, String[]> eventMap = new HashMap<>();
    eventMap.put("name", this.getEventNameInput());
    eventMap.put("time", this.getTimeInput());
    eventMap.put("location", this.getLocationInput());
    eventMap.put("users", this.getUsersInput());
    return eventMap;
  }

  /**
   * Store the user's input as an event that is added to their schedule.
   */
  public IEvent createEvent() {
    IEvent eventMade = null;
    PlannerSystem modelForTextView = new NUPlanner(model.getUsers());
    IScheduleTextView tView = new ScheduleTextView(modelForTextView,
            new StringBuilder());

    Map<String, String[]> eventMap = this.storeOpenedEventMap();
    try {
      eventMade = makeEvent(eventMap);
      return eventMade;
    }
    catch (IllegalArgumentException e) {

    }
    if (eventMade != null) {
      System.out.println("Create event: ");
      tView.eventToString(eventMade);
    }
    return null;
  }


  public void displayCreateError() {
    int counter = 0;
    StringBuilder sb = new StringBuilder("");
    if (this.eventName.getText().isEmpty()) {
      sb.append("No name given to the event\n");
    }
    if (this.onlineMenu.getSelectedItem() == null) {
      sb = sb.append("Online menu not selected\n");
    }
    sb = timeErrors(sb);
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

  private StringBuilder timeErrors(StringBuilder sb) {
    if (this.startDay.getSelectedItem() == null || this.endDay.getSelectedItem() == null) {
      sb = sb.append("No start day or end day selected\n");
    }
    if (this.startTime == null || this.startTime.getText().isEmpty()) {
      sb = sb.append("No start time provided\n");
    }
    if (this.startTime.getText().length() != 4) {
      sb = sb.append("Must provide start time in the form HHMM\n");
    }
    if (this.endTime == null || this.endTime.getText().isEmpty()) {
      sb = sb.append("No end time provided\n");
    }
    if (this.endTime.getText().length() != 4) {
      sb = sb.append("Must provide end time in the form HHMM\n");
    }
    try {
      int start = Integer.parseInt(this.startTime.getText());
      int end = Integer.parseInt(this.endTime.getText());
      if (start <= 0) {
        sb = sb.append("Invalid start time provided. Must be positive integer\n");
      }
      if (end <= 0) {
        sb = sb.append("Invalid end time provided. Must be positive integer\n");
      }
    }
    catch (IllegalArgumentException e) {
      sb = sb.append("Invalid times provided. Must be positive integer\n");
    }
    return sb;
  }

  public void displayRemoveError(Map<String, String[]> eventToRemove) {
    StringBuilder sb = new StringBuilder("");
    for (String key: eventToRemove.keySet()) {
      if (eventToRemove.get(key).length == 0) {
        sb.append(key + " cannot be empty \n");
      }
      if (key == "time") {
        if (eventToRemove.get(key).length != 4) {
          sb.append("Missing time field input\n");
        }
        if (eventToRemove.get(key)[1] == null) {
          sb.append("Start time cannot be null\n");
        }
        if (eventToRemove.get(key)[3] == null) {
          sb.append("End time cannot be null\n");
        }
        if (eventToRemove.get(key)[1].length() != 4) {
          sb.append("Start time must be in format HHMM\n");
        }
        if (eventToRemove.get(key)[3].length() != 4) {
          sb.append("End time must be in format HHMM\n");
        }
      }
    }
    if (sb.length() != 0) {
      JOptionPane.showMessageDialog(this, sb,
              "Errors in scheduling the event: \n", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Displays the error that arises when a user tries to modify an event.
   * @param host the user of the event
   */

  public void displayModifyError(IUser host) {

    if (!this.usersList.getSelectedValuesList().contains(host.getName())) {
      JOptionPane.showMessageDialog(this,
              "Cannot remove host when modifying event",
              "Errors in modifying the event: \n", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Mouse Events Listener to implement methods relevant to a user's mouse click.
   */
  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      // not implemented because not needed for this program
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // not implemented because not needed for this program
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      // not implemented because not needed for this program
    }
  }

}

