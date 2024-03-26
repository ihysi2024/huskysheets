package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.ViewFeatures;
import model.Event;
import model.ReadOnlyPlanner;
import model.Time;
import model.User;

import static model.User.makeEvent;

public class ScheduleView extends JFrame implements IScheduleView {

  protected User currentUser;
  private final PlannerPanel panel;

  private final JPanel menuPanel;
  protected JButton createEventButton;
  protected JButton scheduleEventButton;
  protected JComboBox selectUserButton;

  protected final JMenuBar menuBar;
  protected final JMenu fileSelectMenu;

  protected final JMenuItem addCalendar;
  protected final JMenuItem saveCalendar;


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


    menuBar = new JMenuBar();

    fileSelectMenu = new JMenu("File");
    addCalendar = new JMenuItem("Add calendar");
    saveCalendar = new JMenuItem("Save calendar");
    fileSelectMenu.add(addCalendar);
    fileSelectMenu.add(saveCalendar);

    menuBar.add(fileSelectMenu);

    panel.add(menuBar, BorderLayout.NORTH);

    //panel.getPreferredSize();
    createEventButton = new JButton("Create Event");
    createEventButton.setActionCommand("Create Event");
    scheduleEventButton = new JButton("Schedule Event");
    scheduleEventButton.setActionCommand("Schedule Event");
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

  public User getCurrentUser() {
    return this.currentUser;
  }


  public void addFeatures(ViewFeatures features) {
    //createEventButton.addActionListener(evt -> features.closeScheduleView());
    createEventButton.addActionListener(evt -> features.openEventView());
    selectUserButton.addActionListener(evt -> features.selectUserSchedule(selectUserButton.getSelectedItem().toString()));
    selectUserButton.addActionListener(evt -> features.setCurrentUser());
    addCalendar.addActionListener(evt -> features.addCalendar());
    saveCalendar.addActionListener(evt -> features.saveCalendars());


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
  public void addCalendarInfo() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "XML files", "xml");
    File workingDirectory = new File(System.getProperty("user.dir"));
    chooser.setCurrentDirectory(workingDirectory);
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(addCalendar);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      System.out.println("Selected file path for uploading xml: " +
              chooser.getSelectedFile().getName());
    }

  }

  @Override
  public void saveCalendarInfo() {
    JFileChooser chooser = new JFileChooser();
  //  FileNameExtensionFilter filter = new FileNameExtensionFilter(
  //          "XML files", "xml");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    File workingDirectory = new File(System.getProperty("user.dir"));
    chooser.setCurrentDirectory(workingDirectory);
//    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(saveCalendar);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      System.out.println("Selected folder for saving each xml: " +
              chooser.getCurrentDirectory() +  "\\" + chooser.getSelectedFile().getName());
    }
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

    String userName = selectUserButton.getSelectedItem().toString();
    for (User user: model.getUsers()) {
      if (user.getName().equals(currentUser.getName())) {
        this.displayUserSchedule(model, user);
      }
    }
    this.setVisible(true);
  }


  @Override
  public void displayUserSchedule(ReadOnlyPlanner model, User userToShow) {

    this.panel.resetPanel();

   // for (Event event: userToShow.getSchedule().getEvents()) {
   //   this.panel.paintEvent(panel.getGraphics(), event);
   // }
    menuPanel.revalidate();
    menuPanel.repaint();
    menuBar.revalidate();
    menuBar.repaint();
    panel.add(menuPanel, BorderLayout.SOUTH);
    panel.add(menuBar, BorderLayout.NORTH);
    this.add(panel);

  }

}
