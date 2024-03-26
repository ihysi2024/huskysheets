package view;

import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

import controller.ViewFeatures;
import model.Event;
import model.ReadOnlyPlanner;
import model.User;

import static model.User.makeEvent;

/**
 * Frame for the event pop-out window, where users will be able to see the functionality
 * related to creating new events, modifying existing events, and removing events.
 */
public class EventView extends JFrame implements IEventView {

  private final EventPanel panel;

  private JButton modifyEvent;
  private JButton removeEvent;
  private JButton saveEvent;

  /**
   * Creates a view of the Simon game.
   *
   * @param model desired model to represent Simon game
   */
  public EventView(ReadOnlyPlanner model) {

    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.panel = new EventPanel(model);
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
    panel.add(buttonPanel);
    this.add(panel);
    this.setVisible(false);
    this.pack();
  }

  public void createEvent(ReadOnlyPlanner model) {
    HashMap<String, String[]> eventMap = new HashMap<>();
    eventMap.put("name", this.panel.getEventNameInput());
    eventMap.put("time", this.panel.getTimeInput());
    eventMap.put("location", this.panel.getLocationInput());
    eventMap.put("users", this.panel.getUsersInput());
    try {
      Event eventMade = makeEvent(eventMap);
      for (User user : model.getUsers()) {
        for (String userName: Arrays.stream(this.panel.getUsersInput()).toList()) {
          if (userName.contains(user.getName())) {
            user.addEventForUser(eventMade);
            System.out.println("Create event: ");
            System.out.println(eventMade.eventToString());
           }
        }
      }

    }
    catch (IllegalArgumentException ignored) {
      System.out.println("CREATE EVENT" + ignored);
    }
  }

  public void openEvent(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }


  public void populateEventInPanel(Event event) {
    this.panel.populateEventContents(event);

  }


  @Override
  public void removeEventFromSchedule(ReadOnlyPlanner model, Event eventToRemove, User userRemoving) {

  }

  public Event storeOpenedEvent() {
    HashMap<String, String[]> eventMap = new HashMap<>();
    eventMap.put("name", this.panel.getEventNameInput());
    eventMap.put("time", this.panel.getTimeInput());
    eventMap.put("location", this.panel.getLocationInput());
    eventMap.put("users", this.panel.getUsersInput());
    Event eventMade = makeEvent(eventMap);
    return eventMade;
    /**
    try {
      Event eventMade = makeEvent(eventMap);
      return eventMade;

    }
    catch (IllegalArgumentException ignored) {
      return null;
    }
     **/
  }


  @Override
  public void addFeatures(ViewFeatures features) {
    saveEvent.addActionListener(evt -> features.createEvent());
    saveEvent.addActionListener(evt -> features.closeEventView());
    saveEvent.addActionListener(evt -> features.openScheduleView());


    removeEvent.addActionListener(evt -> features.removeEvent(features.storeEvent()));

    removeEvent.addActionListener(evt -> features.closeEventView());
    removeEvent.addActionListener(evt -> features.openScheduleView());

    //saveEvent.addActionListener(evt -> features.displayUserSchedule());
    try {
      Event oldEvent = features.storeEvent();
      modifyEvent.addActionListener(evt -> features.modifyEvent(oldEvent, this.storeOpenedEvent()));
    }
    catch (NullPointerException ignored) {

    }
    //Event oldEvent = features.storeEvent();
    //System.out.println(oldEvent.getEventName());
  }

  public void closeEvent() {
    this.setVisible(false);
  }

  @Override
  public void display(boolean show) {

  }

  /**
  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }
  **/

}
