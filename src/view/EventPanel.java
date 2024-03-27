package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import controller.ViewFeatures;
import model.IEvent;
import model.IUser;
import model.ReadOnlyPlanner;
import model.Time;

public class EventPanel extends JPanel {

  /**
   * Our view will need to display a model, so it needs to get the current sequence from the model.
   */
  private final ReadOnlyPlanner model;
  /**
   * We'll allow an arbitrary number of listeners for our events...even if
   * we happen right now to only expect a single listener.
   */
  private final List<ViewFeatures> featuresListeners;

  private JButton createEvent;
  // private JButton modifyEvent;
  // private JButton removeEvent;
  // private JButton saveEvent;

  private JLabel eventNameLabel;

  private JLabel locationLabel;

  private JLabel startTimeLabel;

  private JLabel endTimeLabel;

  private JLabel usersListLabel;

  private JLabel onlineLabel;

  private JComboBox onlineMenu;

  private JTextField eventName;

  private JTextField location;

  private JTextField startTime;
  private JComboBox startDay;

  private JTextField endTime;
  private JComboBox endDay;

  private JLabel endDayLabel;
  private JLabel startDayLabel;
  private JList<String> usersList;

  IEvent event;

  // need:
  // buttons for selecting online/not online
  // buttons for start + end day


  /**
   * Creates a panel that will house the view representation of the Simon game
   * with clicking capabilities.
   * @param model desired model to represent Simon game
   */
  public EventPanel(ReadOnlyPlanner model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);

    // we will need to figure out what layout(s) to use
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    //this.setLayout(new SpringLayout());

    // adding a bunch of labels + buttons
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

    //  pack();
    setVisible(true);

    /**
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    modifyEvent = new JButton("Modify Event");
    removeEvent = new JButton("Remove Event");
    saveEvent = new JButton("Create Event");
    buttonPanel.add(saveEvent);
    buttonPanel.add(modifyEvent);
    buttonPanel.add(removeEvent);
    this.add(buttonPanel);

     **/


    //System.out.println(this.event.getStartTime().timeToString());
  }

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
    int idx = 0;
    for (IUser plannerUsers: model.getUsers()) {
      for (String eventUser: event.getUsers()) {
        if (eventUser.equals(plannerUsers.getName())) {
          usersList.setSelectedIndex(idx);
        }
      }
      idx++;
    }
   //for (String user: event.getUsers()) {
    //  usersList.setSelectedIndex(usersList.getSelectedIndex());
    //}


  }

  /**
  public boolean fieldValuesEmpty() {
    String eventNameStringInput = eventName.getText();
    String endDayStringInput = endDay.getSelectedItem().toString();
    String startDayStringInput = startDay.getSelectedItem().toString();
    String endTimeStringInput = endTime.getText();
    String startTimeStringInput = startTime.getText();
    String onlineStringInput = onlineMenu.getSelectedItem().toString();
    String locationStringInput = location.getText();
    List<String> usersStringInput = new ArrayList<>();
    for (String user: usersList.getSelectedValuesList()) {
      usersStringInput.add(user);
    }
    System.out.println("endTimeStringInput: " + endTimeStringInput);
    return (eventNameStringInput.isEmpty()
            && endTimeStringInput.isEmpty()
            && startTimeStringInput.isEmpty()
            && locationStringInput.isEmpty()
            && usersStringInput.isEmpty());
  }
  public void saveValuesToNewEventOne() {
    System.out.println("empty or nah" + saveValuesToNewEvent());
    List<String> users = new ArrayList<>();
    while (eventName.getText().isEmpty() && users.isEmpty()
            && location.getText().isEmpty() && endTime.getText().isEmpty()
            && startTime.getText().isEmpty()) {
      Time finalEndTime = Time.stringToTime(endDay.getSelectedItem().toString(), endTime.getText());
      Time finalStartTime = Time.stringToTime(startDay.getSelectedItem().toString(), startTime.getText());
      String finalEventName = eventName.getText();
      Boolean online = onlineMenu.getSelectedItem().toString().toLowerCase().equals("true");
      String finalLocation = location.getText();
      for (String user: usersList.getSelectedValuesList()) {
        users.add(user);
      }
      this.event = new Event(finalEventName, finalStartTime, finalEndTime, online,
              finalLocation,
              users);
    }
  }

**/
  public String[] getEventNameInput() {
    System.out.println("event name: " + eventName.getText());
    return new String[]{eventName.getText()};
  }

  public String[] getTimeInput() {
    System.out.println("Start day: " + startDay.getSelectedItem().toString());
    System.out.println("Start time: " + startTime.getText());
    System.out.println("End day: " + endDay.getSelectedItem().toString());
    System.out.println("End time: " + endTime.getText());


    return new String[]{startDay.getSelectedItem().toString(), startTime.getText(),
            endDay.getSelectedItem().toString(), endTime.getText()};
  }

  public String[] getLocationInput() {
    System.out.println("online/not" + onlineMenu.getSelectedItem().toString());
    System.out.println("location" + location.getText());

    return new String[]{onlineMenu.getSelectedItem().toString(), location.getText()};
  }

  public String[] getUsersInput() {
    System.out.println("users list" + usersList.getSelectedValuesList().toString());
    return new String[]{usersList.getSelectedValuesList().toString()};
  }

  /**
  public void addFeatures(ViewFeatures features) {
    saveEvent.addActionListener(evt -> features.createEvent());
  }
   **/

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


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    // Draw your calibration pattern here
    Rectangle bounds = this.getBounds();

    Dimension preferred = getPreferredLogicalSize();

  //  g2d.transform(transformLogicalToPhysical());

    //ORIGINALLY:
    // x & y should be (0, 0) --> coordinates start in top left
    // RED line will start in upper left corner
    // BLUE line will start in upper right corner
    g2d.setColor(Color.YELLOW);
    //NOW:
    // RED line starts in bottom left
    // BLUE line starts in bottom right
    g2d.drawLine(-1 * preferred.width, -1 * preferred.height,
            preferred.width, preferred.height);

    //g2d.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    g2d.setColor(Color.GREEN);
    g2d.drawLine(preferred.width, -1 * preferred.height,
            -1 * preferred.width, preferred.height);

  }


  /**
   * Computes the transformation that converts board coordinates
   * (with (0,0) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels).
   *
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(0.5 * getWidth() / preferred.getWidth(),
            0.5 * getHeight() / preferred.getHeight());
    ret.scale(1, -1);
    return ret;
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

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
      // This point is measured in actual physical pixels
      Point physicalP = e.getPoint();
      // For us to figure out which circle it belongs to, we need to transform it
      // into logical coordinates
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
      // TODO: Figure out whether this location is inside a circle, and if so, which one
    }
  }

}

