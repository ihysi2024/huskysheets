package view.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controller.IFeatures;
import model.IReadOnlyCentralSystem;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;

/**
 * represents the JPanel for the fields in the ScheduleFrame: name, location, duration,
 * a user list, and the schedule event button on the bottom.
 */
public class SchedulePanel extends JPanel implements ISchedulePanel {

  private IReadOnlyCentralSystem model;
  private JPanel namePanel;
  private JPanel locationPanel;
  private JPanel durationPanel;
  private JPanel usersPanel;
  private JPanel buttonPanel;
  private final int frameWidth;
  private JTextField nameInput;
  private JComboBox<String> locationOnline;
  private JTextField locationInput;
  private JTextField durationInput;
  private JList<String> userList;
  private JButton scheduleEventButton;

  /**
   * Constructor that sets up the SchedulePanel with a model and the Main frame width.
   * @param model read-only model
   * @param frameWidth main frame width.
   */
  public SchedulePanel(IReadOnlyCentralSystem model, int frameWidth) {
    this.model = model;
    this.frameWidth = frameWidth;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    initializeNamePanel();
    initializeLocationPanel();
    initializeDurationPanel();
    initializeUsersPanel();
    initializeButtonPanel();
    this.add(namePanel);
    this.add(locationPanel);
    this.add(durationPanel);
    this.add(usersPanel);
    this.add(buttonPanel);
  }

  /**
   * for mock testing.
   */
  public SchedulePanel() {
    super();
    frameWidth = 0;
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

  private void initializeDurationPanel() {
    durationPanel = new JPanel();
    durationPanel.setLayout(new FlowLayout((FlowLayout.LEFT)));
    JLabel durationLabel = new JLabel("Duration in minutes:");
    durationPanel.add(durationLabel);
    durationPanel.add(Box.createHorizontalStrut(10));

    durationInput = new JTextField();
    durationInput.setPreferredSize(new Dimension(frameWidth - 225, 25));
    durationPanel.add(durationInput);
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

  private void initializeButtonPanel() {
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    scheduleEventButton = new JButton("Schedule event");

    scheduleEventButton.setPreferredSize(new Dimension(frameWidth / 3, 25));

    buttonPanel.add(scheduleEventButton);
  }

  @Override
  public void addButtonPanelFeatures(IFeatures features) {

    scheduleEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Event scheduled.");
        features.scheduleEvent(nameInput.getText(),
                Objects.equals(locationOnline.getSelectedItem(), "is online"),
                locationInput.getText(),
                durationInput.getText(),
                userList.getSelectedValuesList());

      }
    });
  }
}
