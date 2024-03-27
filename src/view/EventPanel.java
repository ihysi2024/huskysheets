package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.IUser;
import model.ReadOnlyPlanner;
import model.Time;
import static model.User.makeEvent;

/**
 * EventPanel represents the panel where a user can create/modify/remove an event.
 * Allows the user to input a name, location, start time, end time, and list of users.
 */
public class EventPanel extends JPanel implements IEventView {

  private final ReadOnlyPlanner model;

  private final List<ViewFeatures> featuresListeners;

  /**
   * LABEL FIELDS
   */
  private JLabel eventNameLabel;

  private JLabel locationLabel;

  private JLabel startTimeLabel;

  private JLabel endTimeLabel;

  private JLabel usersListLabel;

  private JLabel onlineLabel;
  private JLabel endDayLabel;
  private JLabel startDayLabel;

  /**
   * BUTTON FIELDS
   */
  private JComboBox onlineMenu;

  private JComboBox startDay;
  private JComboBox endDay;

  private JButton modifyEvent;
  private JButton removeEvent;
  private JButton saveEvent;


  /**
   * TEXT FIELDS
   */

  private JTextField eventName;

  private JTextField location;

  private JTextField startTime;

  private JTextField endTime;
  private JList<String> usersList;


  /**
   * Creates a panel that will house the view representation of the Simon game
   * with clicking capabilities.
   * @param model desired model to represent Simon game
   */

  public EventPanel(ReadOnlyPlanner model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();

    MouseListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    /**
     * Add input labels, buttons, and text fields for the user to
     * create/modify/remove an event.
     */

    eventNameLabel = new JLabel("Event Name:");
    this.add(eventNameLabel);

    eventName = new JTextField(5);
    this.add(eventName);

    onlineLabel = new JLabel("Online:");
    this.add(onlineLabel);

    onlineMenu = new JComboBox<>();

    onlineMenu.addItem("True");
    onlineMenu.addItem("False");

    this.add(onlineMenu);

    locationLabel = new JLabel("Location:");
    this.add(locationLabel);

    location = new JTextField(5);
    this.add(location);

    startDayLabel = new JLabel("Start Day:");
    this.add(startDayLabel);

    startDay = new JComboBox();

    for (Time.Day day: Time.Day.values()) {
      startDay.addItem(day);
    }

    this.add(startDay);

    startTimeLabel = new JLabel("Start Time:");
    this.add(startTimeLabel);

    startTime = new JTextField(5);
    this.add(startTime);

    endDayLabel = new JLabel("End Day:");
    this.add(endDayLabel);

    endDay = new JComboBox();

    for (Time.Day day: Time.Day.values()) {
      endDay.addItem(day);
    }

    this.add(endDay);

    endTimeLabel = new JLabel("End Time:");
    this.add(endTimeLabel);

    endTime = new JTextField(5);
    this.add(endTime);

    usersListLabel = new JLabel("User List:");
    this.add(usersListLabel);

    DefaultListModel<String> allUsers = new DefaultListModel<>();

    for (IUser user: model.getUsers()) {
      allUsers.addElement(user.getName());
    }
    usersList = new JList<>(allUsers);
    this.add(usersList);

    /**
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

  }

  /**
   * Set the event fields on the panel to the given event's fields.
   * Visualizes a user's entry for an event in the event panel text fields.
   * @param event event to visualize in the event panel.
   */
  public void populateEventContents(IEvent event) {
    eventName.setText(event.getEventName());
    startDay.setSelectedIndex(event.getStartTime().getDate().getDayIdx());
    startTime.setText(String.valueOf(event.getStartTime().getHours())
            + String.valueOf(event.getStartTime().getMinutes()));
    endDay.setSelectedIndex(event.getEndTime().getDate().getDayIdx());
    endTime.setText(String.valueOf(event.getEndTime().getHours())
            + String.valueOf(event.getEndTime().getMinutes()));
    location.setText(event.getLocation());
    if (event.getOnline()) {
      onlineMenu.setSelectedIndex(0);
    }
    else {
      onlineMenu.setSelectedIndex(1);
    }



    Set<IUser> setUsers = model.getUsers();

    for (IUser users : setUsers) {
   //   System.out.println("curr user in set: " + users.getName());
    }

    // Create String[] of size of setOfString
 //   String[] arrayOfUsersString = new String[setUsers.size()];

    IUser[] arrayOfUsers2 = setUsers.toArray(IUser[]::new);

    for (IUser users : arrayOfUsers2) {
    //  System.out.println("curr user in IUser[]: " + users.getName());
    }

    String[] arrayOfUsersString = new String[setUsers.size()];

    for (int i = 0; i < arrayOfUsersString.length; i++) {
      arrayOfUsersString[i] = arrayOfUsers2[i].getName();
    }

    // Copy elements from set to string array
    // using advanced for loop
    int index = 0;
    for (String userName : arrayOfUsersString) {
   //   arrayOfUsersString[index++] = user.getName();
      //arrayOfUsers.
    //  System.out.println("curr user added: " + userName);
    }


   // String [] testArray = {"a","b","c"};

   // String[] usersStringArr = event.getUsers().toArray(new String[0]);
  //  usersList.setListData(usersStringArr);

   // usersList.addSelectionInterval(0, usersList.getModel().getSize() - 1);

   // for (int i = 0; i < usersList.getModel().getSize(); i++) {
   //   usersList.setSelectedIndex(i);
    //  System.out.println("userrr: " + usersList.getModel().getElementAt(i));

   // }

   // usersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

  //  int idx = 0;
    System.out.println("event name: " + event.getEventName());
    usersList.clearSelection();
    for (IUser plannerUsers: model.getUsers()) {
   //   for (String eventUser: event.getUsers()) {
        for (int currIndex = 0; currIndex < event.getUsers().size(); currIndex++) {
          String currUserName = event.getUsers().get(currIndex);
        if (currUserName.equals(plannerUsers.getName())) {
          System.out.println("curr user in system: " + currUserName);
          System.out.println("curr user in event: " + plannerUsers.getName());
         // usersList.setSelectedIndex(idx);
          usersList.addSelectionInterval(currIndex, currIndex);
        }
      }
    //  idx++;
    }




  }

  /**
   * Get the user's input for the event name.
   * @return a String[] of the event name
   */
  public String[] getEventNameInput() {
    return new String[]{eventName.getText()};
  }

  /**
   * Get the user's input for the event time.
   * @return a String[] of the event time
   */

  public String[] getTimeInput() {
    return new String[]{startDay.getSelectedItem().toString(), startTime.getText(),
            endDay.getSelectedItem().toString(), endTime.getText()};
  }

  /**
   * Get the user's input for the event location.
   * @return a String[] of the location
   */

  public String[] getLocationInput() {
    return new String[]{onlineMenu.getSelectedItem().toString(), location.getText()};
  }

  /**
   * Get the user's input for the event list of users.
   * @return a String[] of the user list
   */

  public String[] getUsersInput() {
    return usersList.getSelectedValuesList().toArray(new String[0]);
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
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
   * @return Our preferred *logical* size.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(100, 100);
  }

  /**
   * Resets the panel to its originally empty fields. Useful for trying to create a new event
   * after an event has already been created.
   */
  public void resetPanel() {
    eventName.setText("");
    startTime.setText("");
    endTime.setText("");
    location.setText("");
  }

  /**
   * Open the event view for the user to see.
   * @param model observational planner interface.
   */
  public void openEvent() {
    // implemented by the IEventView interface for the EventView. Panel shouldn't
    // be implemented, entire view should be.
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
   * @param features functionality that the user has access to through the event view.
   */
  @Override
  public void addFeatures(ViewFeatures features) {
    saveEvent.addActionListener(evt -> features.createEvent());
    saveEvent.addActionListener(evt -> features.closeEventView());
    saveEvent.addActionListener(evt -> features.openScheduleView());

    try {
      removeEvent.addActionListener(evt -> features.removeEvent(makeEvent(features.storeEvent())));
    }
    catch (IllegalArgumentException ignored) {
      System.out.println("check inputs to remove event");
    }
  //  removeEvent.addActionListener(evt -> features.removeEvent(new Event("office hours",
   //         new Time(Time.Day.MONDAY, 12, 10),
   //         new Time(Time.Day.MONDAY, 15, 30),
   //         false,
   //         "Churchill Hall 101",
    //        List.of("Prof. Lucia", "Me"))));
    removeEvent.addActionListener(evt -> features.closeEventView());
    removeEvent.addActionListener(evt -> features.openScheduleView());

    modifyEvent.addActionListener(evt -> features.modifyEvent(makeEvent(features.storeEvent()), model));
    modifyEvent.addActionListener(evt -> features.closeEventView());
    modifyEvent.addActionListener(evt -> features.openScheduleView());
  }

  /**
   * Display the view.
   * @param show whether to the display or not
   */
  @Override
  public void display(boolean show) {

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
  public void createEvent() {
    HashMap<String, String[]> eventMap = this.storeOpenedEventMap();
    try {
      IEvent eventMade = makeEvent(eventMap);
      for (IUser user : model.getUsers()) {
        for (String userName : this.getUsersInput()) {
          if (userName.contains(user.getName())) {
            user.addEventForUser(eventMade);
            System.out.println("Create event: ");
            System.out.println(eventMade.eventToString());
          }
        }
      }
    }
    catch (NullPointerException | IllegalArgumentException ignored) {
      System.out.println("Could not create event: " +
              "Event info not fully entered, error in given values, " +
              "or event already exists at that time");
    }
  }

  /**
   * Modify an event with the user's new input to the event panel.
<<<<<<< HEAD
   * @param event represents the updated event
   * @param model observational planner system to use.
   */
  public void modifyEvent(IEvent event, ReadOnlyPlanner model) {
=======
   * @param eventMap represents the updated event
   */
  public void modifyEvent(HashMap<String, String[]> eventMap) {
    IEvent event = makeEvent(eventMap);
>>>>>>> 289f5df607135534ad10f636899cc4e866068cd9
    System.out.println("Modify event: ");
    System.out.println(event.eventToString());
  }

  /**
   * Mouse Events Listener to implement methods relevant to a user's mouse click.
   */
  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
      Point physicalP = e.getPoint();
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
    }
  }

}

