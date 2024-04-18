package cs3500.nuplanner.provider.view.event;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;

import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.model.IEventLocation;
import cs3500.nuplanner.provider.model.IReadOnlyCentralSystem;
import cs3500.nuplanner.provider.model.IEvent;


import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * represents the JPanels for all the fields in an event for the EventFrame: name, location,
 * start time, end time, users, and the buttons on the bottom.
 */
public class EventPanel extends JPanel implements IEventPanel {

  private IReadOnlyCentralSystem model;
  private JPanel namePanel;
  private JPanel locationPanel;
  private JPanel startTimePanel;
  private JPanel endTimePanel;
  private JPanel usersPanel;
  private JPanel bottomPanel;
  private JOptionPane errorPanel;
  private int frameWidth;

  private JTextField nameInput;
  private JComboBox<String> locationOnline;
  private JTextField locationInput;
  private JComboBox<String> startingDayBox;
  private JTextField startingTimeBox;
  private JComboBox<String> endingDayBox;
  private JTextField endingTimeBox;
  private JList<String> userList;
  private JButton createEventButton;
  private JButton modifyEventButton;
  private JButton removeEventButton;
  private IEvent originalEvent;

  /**
   * constructor that creates the event panel with all the event field panels.
   *
   * @param model ReadOnly model.
   * @param frameWidth width of the EventFrame.
   */
  public EventPanel(IReadOnlyCentralSystem model, int frameWidth) {
    this.model = model;
    this.frameWidth = frameWidth;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    initializeNamePanel();
    initializeLocationPanel();
    initializeStartTimePanel();
    initializeEndTimePanel();
    initializeUsersPanel();
    initializeBottomPanel();
    this.add(namePanel);
    this.add(locationPanel);
    this.add(startTimePanel);
    this.add(endTimePanel);
    this.add(usersPanel);
    this.add(bottomPanel);
  }

  /**
   * Constructor for when modifying an event-- so it has an original event stored.
   * @param model read only model.
   * @param frameWidth width of the frame.
   * @param originalEvent the original event.
   */
  public EventPanel(IReadOnlyCentralSystem model, int frameWidth, IEvent originalEvent) {
    this.model = model;
    this.frameWidth = frameWidth;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    initializeNamePanel();
    initializeLocationPanel();
    initializeStartTimePanel();
    initializeEndTimePanel();
    initializeUsersPanel();
    initializeBottomPanel();
    this.add(namePanel);
    this.add(locationPanel);
    this.add(startTimePanel);
    this.add(endTimePanel);
    this.add(usersPanel);
    this.add(bottomPanel);
    this.originalEvent = originalEvent;
  }

  /**
   * For mock testing.
   */
  public EventPanel() {
    super();
    this.frameWidth = 0;
  }

  private void initializeNamePanel() {
    namePanel = new JPanel();
    namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel nameLabel = new JLabel("Event name: ");
    namePanel.add(nameLabel);
    nameInput = new JTextField();
    nameInput.setPreferredSize(new Dimension(frameWidth - 110, 25));
    namePanel.add(nameInput);
  }

  private void initializeLocationPanel() {
    locationPanel = new JPanel();
    locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel locationLabel = new JLabel("Location:");
    locationPanel.add(locationLabel);
    locationPanel.add(Box.createHorizontalStrut(10));

    String[] onlineHuh = {"is online", "is in person"};
    locationOnline = new JComboBox<>(onlineHuh);
    locationPanel.add(locationOnline);

    locationPanel.add(Box.createHorizontalStrut(10));

    locationInput = new JTextField();
    locationInput.setPreferredSize(new Dimension(frameWidth - 225, 30));
    locationPanel.add(locationInput);
  }

  private void initializeStartTimePanel() {
    startTimePanel = new JPanel();
    startTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel startingDayLabel = new JLabel("Starting Day:");
    startTimePanel.add(startingDayLabel);

    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday",
                     "Friday", "Saturday", "Sunday"};
    startingDayBox = new JComboBox<>(days);
    startingDayBox.setPreferredSize(new Dimension(frameWidth / 6, 30));
    startTimePanel.add(startingDayBox);

    JLabel startingTimeLabel = new JLabel("Starting time:");
    startTimePanel.add(startingTimeLabel);

    startingTimeBox = new JTextField();
    startingTimeBox.setPreferredSize(new Dimension(frameWidth / 2, 30));
    startTimePanel.add(startingTimeBox);
  }

  private void initializeEndTimePanel() {
    endTimePanel = new JPanel();
    endTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel endingDayLabel = new JLabel("ending Day:");
    endTimePanel.add(endingDayLabel);

    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday",
                     "Friday", "Saturday", "Sunday"};
    endingDayBox = new JComboBox<>(days);
    endingDayBox.setPreferredSize(new Dimension(frameWidth / 6, 30));
    endTimePanel.add(endingDayBox);

    JLabel endingTimeLabel = new JLabel("ending time:");
    endTimePanel.add(endingTimeLabel);

    endingTimeBox = new JTextField();
    endingTimeBox.setPreferredSize(new Dimension(frameWidth / 2, 30));
    endTimePanel.add(endingTimeBox);
  }

  private void initializeUsersPanel() {
    usersPanel = new JPanel();
    usersPanel.setLayout(new BorderLayout());
    JLabel availUsers = new JLabel("Available users:");
    usersPanel.add(availUsers, BorderLayout.PAGE_START);

    userList = new JList<>(model.accessUsers().toArray(new String[0]));
    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    usersPanel.add(userList, BorderLayout.CENTER);

    usersPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
  }

  private void initializeBottomPanel() {
    bottomPanel = new JPanel();
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    createEventButton = new JButton("Create event");
    modifyEventButton = new JButton("Modify event");
    removeEventButton = new JButton("Remove event");

    createEventButton.setPreferredSize(new Dimension(frameWidth / 4, 25));
    modifyEventButton.setPreferredSize(new Dimension(frameWidth / 4, 25));
    removeEventButton.setPreferredSize(new Dimension(frameWidth / 4, 25));

    bottomPanel.add(createEventButton);
    bottomPanel.add(modifyEventButton);
    bottomPanel.add(removeEventButton);

  }

  @Override
  public void addBottomPanelFeatures(IFeatures features) {
    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.addEvent(
                nameInput.getText(),
                Objects.equals(locationOnline.getSelectedItem(), "is online"),
                locationInput.getText(),
                DayOfWeek.valueOf(
                        startingDayBox.getSelectedItem().toString().toUpperCase()),
                startingTimeBox.getText(),
                DayOfWeek.valueOf(
                        endingDayBox.getSelectedItem().toString().toUpperCase()),
                endingTimeBox.getText(),
                userList.getSelectedValuesList());
      }
    });

    modifyEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.modifyEvent(originalEvent,
                nameInput.getText(),
                Objects.equals(locationOnline.getSelectedItem(), "is online"),
                locationInput.getText(),
                DayOfWeek.valueOf(
                        startingDayBox.getSelectedItem().toString().toUpperCase()),
                startingTimeBox.getText(),
                DayOfWeek.valueOf(
                        endingDayBox.getSelectedItem().toString().toUpperCase()),
                endingTimeBox.getText(),
                userList.getSelectedValuesList());
      }
    });
    removeEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.removeEvent(
                nameInput.getText(),
                Objects.equals(locationOnline.getSelectedItem(), "is online"),
                locationInput.getText(),
                DayOfWeek.valueOf(
                        startingDayBox.getSelectedItem().toString().toUpperCase()),
                startingTimeBox.getText(),
                DayOfWeek.valueOf(
                        endingDayBox.getSelectedItem().toString().toUpperCase()),
                endingTimeBox.getText(),
                userList.getSelectedValuesList());
      }
    });
  }

  @Override
  public void autoFill(IEvent event) {
    //name
    nameInput.setText(event.accessName());
    IEventLocation location = event.accessLocation();
    //location
    boolean isOnline = location.accessOnline();
    if (isOnline) {
      locationOnline.setSelectedItem("is online");
    } else {
      locationOnline.setSelectedItem("is in person");
    }
    locationInput.setText(location.accessPlace());
    //start time
    String startingDay = event.accessStartDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    String startingTime = event.accessStartTime().timeToString();
    startingDayBox.setSelectedItem(startingDay);
    startingTimeBox.setText(startingTime);
    //end time
    String endingDay = event.accessEndDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    String endingTime = event.accessEndTime().timeToString();
    endingDayBox.setSelectedItem(endingDay);
    endingTimeBox.setText(endingTime);
    //users
    List<Integer> indices = new ArrayList<>();
    for (int user = 0; user < model.accessUsers().size(); user++) {
      if (event.accessUsers().contains(model.accessUsers().get(user))) {
        indices.add(user);
      }
    }
    userList.setSelectedIndices(indices.stream().mapToInt(i -> i).toArray());
  }
}
