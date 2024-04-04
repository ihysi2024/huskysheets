package view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
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

  private IScheduleTextView textV;


  /**
   * TEXT FIELDS.
   */
  private final JTextField eventName;

  private final JTextField location;

  private final JTextField startTime;

  private final JTextField endTime;
  private JList<String> usersList;

  /**
   * Creates a panel that will house the input labels, buttons, and text fields for the user to
   * * create/modify/remove an event.
   *
   * @param model desired model to represent Simon game
   */
  public EventPanel(ReadOnlyPlanner model) {
    this.model = Objects.requireNonNull(model);
    List<ViewFeatures> featuresListeners = new ArrayList<>();

    MouseListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
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
      System.out.println("curr user adding to user list: " + user.getName());
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
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   *
   * @param event event to visualize in the event panel.
   */
  public void populateEventContents(IEvent event) {
    eventName.setText(event.getEventName());
    startDay.setSelectedIndex(event.getStartTime().getDate().getDayIdx());
    endDay.setSelectedIndex(event.getEndTime().getDate().getDayIdx());

    int minValue = event.getStartTime().getMinutes();
    String minString = "";
    if (minValue < 10) {
      minString = "0" + minValue;
    }
    else {
      minString = String.valueOf(minValue);
    }

    int hourValue = event.getStartTime().getHours();
    String hourString = "";
    if (hourValue < 10) {
      hourString = "0" + hourValue;
    }
    else {
      hourString = String.valueOf(hourValue);
    }

    int minValue2 = event.getEndTime().getMinutes();
    String minString2 = "";
    if (minValue < 10) {
      minString2 = "0" + minValue2;
    }
    else {
      minString2 = String.valueOf(minValue2);
    }

    int hourValue2 = event.getEndTime().getHours();
    String hourString2 = "";
    if (hourValue2 < 10) {
      hourString2 = "0" + hourValue2;
    }
    else {
      hourString2 = String.valueOf(hourValue2);
    }
    /*
    startTime.setText(event.getStartTime().getHours()
            + String.valueOf(event.getStartTime().getMinutes()));
    endTime.setText(event.getEndTime().getHours()
            + String.valueOf(event.getEndTime().getMinutes()));
     */
    startTime.setText(hourString + minString);
    endTime.setText(hourString2 + minString2);


    location.setText(event.getLocation());

    if (event.getOnline()) {
      onlineMenu.setSelectedIndex(0);
    } else {
      onlineMenu.setSelectedIndex(1);
    }

    this.updateUserList();
    usersList.clearSelection();

    for (int plannerUserIdx = 0; plannerUserIdx < model.getUsers().size(); plannerUserIdx++) {
      String currPlannerUserName = model.getUsers().get(plannerUserIdx).getName();
      if (event.getUsers().contains(currPlannerUserName)) {
        usersList.addSelectionInterval(plannerUserIdx, plannerUserIdx);
      }
    }
  }

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
  //  System.out.println("start time 2: " + startTime.getText());
   // System.out.println("end time 2: " + endTime.getText());

    return new String[]{startDay.getSelectedItem().toString(), startTime.getText(),
            endDay.getSelectedItem().toString(), endTime.getText()};
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
   * after an event has already been created.
   *
   * @param host host of the event
   */
  public void resetPanel(String host) {
    eventName.setText("");
    startTime.setText("");
    endTime.setText("");
    location.setText("");

    this.updateUserList();
    usersList.clearSelection();
    // always selecting this schedule's user as an invitee to event
    for (int index = 0; index < usersList.getModel().getSize(); index++) {
      if (usersList.getModel().getElementAt(index).equals(host)) {
        usersList.addSelectionInterval(index, index);
      }
    }
  }

  /**
   * Open the event view for the user to see.
   */
  public void openEvent() {
    // implemented by the IEventView interface for the EventView. Panel shouldn't
    // be implemented, entire view should be.
  }

  /**
   * Updates list of users in event view.
   */
  @Override
  public void updateUserList() {
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
    saveEvent.addActionListener(evt -> features.closeEventView());
    saveEvent.addActionListener(evt -> features.openScheduleView());

    removeEvent.addActionListener(evt -> features.removeEvent(makeEvent(features.storeEvent())));
    removeEvent.addActionListener(evt -> features.closeEventView());
    removeEvent.addActionListener(evt -> features.openScheduleView());

    modifyEvent.addActionListener(evt -> features.modifyEvent(makeEvent(features.storeEvent())));
    modifyEvent.addActionListener(evt -> features.closeEventView());
    modifyEvent.addActionListener(evt -> features.openScheduleView());
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

  public HashMap<String, String[]> storeOpenedEventMap() {
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
    PlannerSystem modelForTextView = new NUPlanner(model.getUsers());
    IScheduleTextView textV = new ScheduleTextView(modelForTextView, new StringBuilder());
    HashMap<String, String[]> eventMap = this.storeOpenedEventMap();
    try {
      IEvent eventMade = makeEvent(eventMap);
      System.out.println("Create event: ");
      System.out.println(textV.eventToString(eventMade));
      return eventMade;

    } catch (NullPointerException | IllegalArgumentException ignored) {
      System.out.println("Could not create event: "
              + "Event info not fully entered, error in given values, "
              + "or event already exists at that time");
    }
    return null;
  }

  /**
   * Modify an event with the user's new input to the event panel.
   *
   * @param event represents the updated event
   */
  public void modifyEvent(IEvent event) {
    PlannerSystem modelForTextView = new NUPlanner(model.getUsers());
    IScheduleTextView textV = new ScheduleTextView(modelForTextView, new StringBuilder());
    System.out.println("Modify event: ");
    System.out.println(textV.eventToString(event));
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

