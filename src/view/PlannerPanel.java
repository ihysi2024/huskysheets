package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ViewFeatures;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.ReadOnlyPlanner;
import model.Schedule;
import model.Time;
import model.User;

import static model.Time.indexToTime;

/**
 * Represents the panel of the planner system that will be displayed on the view.
 */

public class PlannerPanel extends JPanel implements IPlannerView {

  private final ReadOnlyPlanner model;
  private IUser currentUser;

  private final JButton scheduleEventButton;

  private final JButton createEventButton;

  private final JPanel menuPanel;
  protected JComboBox<String> selectUserButton;

  protected final JMenuBar menuBar;
  protected final JMenu fileSelectMenu;

  protected final JMenuItem addCalendar;
  protected final JMenuItem saveCalendar;


  /**
   * Creates a panel that will house the view representation of the Simon game
   * with clicking capabilities.
   *
   * @param model desired model to represent Simon game
   */
  public PlannerPanel(ReadOnlyPlanner model) {
    this.model = Objects.requireNonNull(model);
    MouseListener listener = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // not implemented because not needed for this program
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // not implemented because not needed for this program
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // not implemented because not needed for this program
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // not implemented because not needed for this program
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // not implemented because not needed for this program
      }
    };
    this.addMouseListener(listener);

    this.setLayout(new BorderLayout());

    menuBar = new JMenuBar();
    fileSelectMenu = new JMenu("File");
    addCalendar = new JMenuItem("Add calendar");
    saveCalendar = new JMenuItem("Save calendars");
    fileSelectMenu.add(addCalendar);
    fileSelectMenu.add(saveCalendar);
    menuBar.add(fileSelectMenu);
    this.add(menuBar, BorderLayout.NORTH);

    createEventButton = new JButton("Create Event");
    createEventButton.setActionCommand("Create Event");
    scheduleEventButton = new JButton("Schedule Event");
    scheduleEventButton.setActionCommand("Schedule Event");
    this.selectUserButton = new JComboBox<String>();
    selectUserButton.addItem("None");
    for (IUser user : model.getUsers()) {
      selectUserButton.addItem(user.getName());
    }
    selectUserButton.setActionCommand("Select User");

    this.menuPanel = new JPanel();
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
    menuPanel.add(selectUserButton);
    menuPanel.add(createEventButton);
    menuPanel.add(scheduleEventButton);
    this.add(menuPanel, BorderLayout.SOUTH);
    //this.setVisible(true);
  }


  /**
   * Sets the current user to what is selected in the appropriate button in the schedule view.
   */
  public void setCurrentUser() {
    this.currentUser = null;
    for (IUser user : model.getUsers()) {
      if (user.getName().equals(
              Objects.requireNonNull(selectUserButton.getSelectedItem()).toString())) {
        this.currentUser = user;
      }
    }
  }

  /**
   * Reset/repaint the panel to display the user's schedule.
   */
  public void resetPanel() {
    this.paintComponent(getGraphics());
  }

  /**
   * Observational method to retrieve the user whose schedule is being displayed.
   *
   * @return the user being interacted with.
   */

  public IUser getCurrentUser() {
    return Objects.requireNonNullElseGet(this.currentUser, () ->
            new User("None", new Schedule(new ArrayList<>())));
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 500x500 pixels.
   *
   * @return Our preferred *physical* size.
   */

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }

  public void addUserToDropdown(String userName) {
    selectUserButton.addItem(userName);
  }

  /**
   * Conceptually, we can choose a different coordinate system
   * and pretend that our panel is 100x100 "cells" big. You can choose
   * any dimension you want here, including the same as your physical
   * size (in which case each logical pixel will be the same size as a physical
   * pixel, but perhaps your calculations to position things might be trickier)
   *
   * @return Our preferred *logical* size.
   */

  private Dimension getPreferredLogicalSize() {
    return new Dimension(100, 100);
  }

  @Override
  public void display(boolean show) {
    // only the view should be displayed with this method.
  }

  /**
   * Opens up the current user's schedule.
   */

  public void openPlannerView() {
    try {
      for (IUser user : model.getUsers()) {
        if (user.getName().equals(currentUser.getName())) {
          this.displayUserSchedule(user.getName());
        }
      }
    } catch (NullPointerException ignored) {
      this.paintGridLines(this.getGraphics());
      System.out.println("No user selected");
    }
  }

  /**
   * Displays the desired user's schedule.
   *
   * @param userToShow desired user schedule to show
   */
  @Override
  public void displayUserSchedule(String userToShow) {
    if (userToShow.equals("None")) {
      this.removeAll();
      this.revalidate();
      this.updateUI();
      this.paintGridLines(this.getGraphics());
    } else {
      this.resetPanel();
    }

    menuPanel.revalidate();
    menuPanel.repaint();
    menuBar.revalidate();
    menuBar.repaint();
    this.add(menuPanel, BorderLayout.SOUTH);
    this.add(menuBar, BorderLayout.NORTH);

  }

  /**
   * Closes the current schedule view.
   */
  @Override
  public void closePlannerView() {
    this.setVisible(false);
  }

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   **/

  public void addFeatures(ViewFeatures features) {

    selectUserButton.addActionListener(evt ->
            features.selectUserSchedule(
                    Objects.requireNonNull(selectUserButton.getSelectedItem()).toString()));
    selectUserButton.addActionListener(evt -> features.setCurrentUser());

    createEventButton.addActionListener(evt ->
            features.resetEventPanelView(this.getCurrentUser().getName()));
    createEventButton.addActionListener(evt -> features.openEventView());

    addCalendar.addActionListener(evt -> features.addCalendar());
    saveCalendar.addActionListener(evt -> features.saveCalendars());

    scheduleEventButton.addActionListener(evt ->
            features.resetSchedulePanelView(this.getCurrentUser().getName()));
    scheduleEventButton.addActionListener(evt -> features.openScheduleView());


  }

  /**
   * Paints given event onto panel. If event goes in to following week, ends painting at
   * Saturday @23:59.
   *
   * @param g     Graphics to use
   * @param event IEvent to be painted
   */
  public void paintEvent(Graphics g, IEvent event) {
    Color color = new Color(255, 100, 200, 100);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(color);

    ITime startTime = event.getStartTime();
    ITime endTime = event.getEndTime();

    int dayWidth = (int) Math.round(this.getWidth() / 7.0); // constant, width of one day

    int[] eventStartCoords = this.timeToPaintLoc(startTime);
    int[] eventEndCoords = this.timeToPaintLoc(endTime);

    int rectHeight = eventEndCoords[1] - eventStartCoords[1];  // length of one-day event

    if (eventStartCoords[0] == eventEndCoords[0]) { // event starts + ends on same day
      g2d.fillRect(eventStartCoords[0], eventStartCoords[1], dayWidth, rectHeight);
    } else {
      // event goes to next week, changing end time to Sunday @23:59
      if (eventEndCoords[0] < eventStartCoords[0]) {
        endTime = new Time(Time.Day.SATURDAY, 23, 59);
        int[] sunday2359 = this.timeToPaintLoc(endTime);
        eventEndCoords[0] = sunday2359[0];
        eventEndCoords[1] = sunday2359[1];
      }

      int endOfFirstDay =
              (int) Math.round(this.minLoc(new Time(Time.Day.SATURDAY, 23, 59))
                      * this.getHeight()); // day doesn't matter, only time
      int rectHeightFirstDay = endOfFirstDay - eventStartCoords[1];
      g2d.fillRect(eventStartCoords[0], eventStartCoords[1], dayWidth, rectHeightFirstDay);

      int endDayIndex = endTime.getDate().getDayIdx();

      this.paintFullDay(g, color, dayWidth, startTime, endTime);

      // draw the last day. starts at 00:00, ends at actual end of the event
      int[] endDayCoords = this.timeToPaintLoc(indexToTime(endDayIndex, 0));

      g2d.fillRect(endDayCoords[0], endDayCoords[1], dayWidth, eventEndCoords[1]);
    }
  }

  /**
   * Paints an entire day on the schedule starting from 00:00 to 23:59.
   *
   * @param g        Graphics
   * @param color    color of event to be painted
   * @param dayWidth width of day, constant
   * @param startDay start day to be painted
   * @param endDay   end day to be painted
   */
  public void paintFullDay(Graphics g, Color color, int dayWidth, ITime startDay, ITime endDay) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(color);
    int startDayIndex = startDay.getDate().getDayIdx();
    int endDayIndex = endDay.getDate().getDayIdx();
    // drawing each full day
    for (int indexFullDay = startDayIndex + 1; indexFullDay < endDayIndex; indexFullDay++) {
      // full day starts at time 00:00
      int[] currDayCoords = this.timeToPaintLoc(indexToTime(indexFullDay, 0));

      g2d.fillRect(currDayCoords[0], currDayCoords[1], dayWidth, this.getHeight());
    }

  }

  /**
   * Calculates the top left coordinate of the given time. The x coordinate corresponds
   * to the day, the y coordinate corresponds to the time to minute granularity.
   * Changes based on the size of the board.
   *
   * @param time given time
   * @return 2-value int array containing top left coordinate of given time
   */
  private int[] timeToPaintLoc(ITime time) {
    int[] x_y_coords = new int[2];

    int weekColXCoord = (int) Math.round((time.getDate().getDayIdx() / 7.0) * this.getWidth());
    int weekColYCoord = (int) Math.round(this.minLoc(time) * this.getHeight());

    x_y_coords[0] = weekColXCoord;
    x_y_coords[1] = weekColYCoord;
    return x_y_coords;
  }

  /**
   * Calculates the time position from [0, 1) that the given time falls, with 0 representing
   * midnight.
   *
   * @param time Time to use for calculations
   * @return the decimal value representing the time's position
   */
  private double minLoc(ITime time) {
    int timePos = time.minutesSinceMidnight();
    return timePos / (60.0 * 24.0);
  }

  /**
   * Paint the schedule frame background grid and the events of the corresponding
   * schedule on top.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    this.paintGridLines(g);

    // painting all events on to schedule
    if (this.getCurrentUser() != null) {
      for (IEvent event : model.retrieveUserEvents(this.getCurrentUser())) {
        this.paintEvent(g, event);
      }
    }

  }

  protected void paintGridLines(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    // painting schedule grid lines
    this.paintLines(g2d, Color.GRAY, 8, 1, true);
    this.paintLines(g2d, Color.GRAY, 25, 1, false);
    this.paintLines(g2d, Color.BLACK, 7, 2, false);
  }

  /**
   * Determines what Time on the calendar system corresponds to the given mouse click.
   *
   * @param e MouseEvent that occurred
   * @return Time corresponding to the given click location
   */
  public ITime timeAtClick(MouseEvent e) {
    int dayIndex = e.getX() / (this.getWidth() / 7);
    int totMinutes = (int) Math.round(e.getY() / (this.getHeight() / (60.0 * 24)));
    return indexToTime(dayIndex, totMinutes);
  }

  /**
   * Handles the clicks in schedule panel. Specifically handles clicking on an event in the
   * schedule and opening up the corresponding view.
   *
   * @param features features available
   */
  public void addClickListener(ViewFeatures features) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {

        // might not even need try catch
        try {
          PlannerPanel panel = PlannerPanel.this;
          ITime timeOfEvent = panel.timeAtClick(e);
        //  System.out.println("time: " + timeOfEvent.getDate() + timeOfEvent.getHours() + timeOfEvent.getMinutes());
          IEvent eventClicked = features.findEvent(timeOfEvent);
          if(eventClicked != null) {
            features.openEventView();
            System.out.println(eventClicked.getEventName());
            features.populateEvent(eventClicked);
          }

        } catch (NullPointerException ignored) {
         // System.out.println("got here no event");
          // click where no event is present, ignoring
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // this is not used
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // this is not used
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // this is not used
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // this is not used
      }
    });
  }

  /**
   * Allowing user to select an .xml file that contains the desired calendar.
   * Automatically starts in current directory.
   * @return String of the selected file path
   */
  public String addCalendarInfo() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "XML files", "xml");
    File workingDirectory = new File(System.getProperty("user.dir"));
    chooser.setCurrentDirectory(workingDirectory);
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(addCalendar);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      System.out.println("Selected file path: "
              + chooser.getSelectedFile().getName());
      return chooser.getSelectedFile().getName();
      //displayUserSchedule(String userToShow);
    }
    return "";
  }

  /**
   * Allowing user to select a folder where they will export the user schedules.
   * Automatically starts in current directory.
   * @return String of the selected file path
   */
  @Override
  public String saveCalendarInfo() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    File workingDirectory = new File(System.getProperty("user.dir"));
    chooser.setCurrentDirectory(workingDirectory);
    int returnVal = chooser.showOpenDialog(saveCalendar);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      System.out.println("Selected folder for saving each xml: "
              + chooser.getCurrentDirectory() + "\\" + chooser.getSelectedFile().getName());
      return chooser.getCurrentDirectory() + "\\" + chooser.getSelectedFile().getName();
    }
    return "";
  }

  /**
   * Finds the event that is occurring at the specified time. If two events start and end at the
   * same time, returns the earlier event.
   *
   * @param timeOfEvent desired time to search at
   * @return Event occurring at that time, null otherwise
   */

  public IEvent findEventAtTime(ITime timeOfEvent) {
    return model.retrieveUserScheduleAtTime(this.currentUser, timeOfEvent);
  }

  /**
   * Paints desired number of equally spaced lines. Number of lines will include lines drawn on
   * the left and right sides of the bounds.
   * Can specify the color, width, and orientation of the lines.
   *
   * @param g           instance of Graphics
   * @param color       desired color for lines
   * @param numLines    number of equally spaced lines desired. must be >= 2 to work
   * @param strokeWidth desired stroke width for lines
   * @param vert        true if vertical lines desired, false if horizontal
   */
  private void paintLines(Graphics g, Color color, int numLines, int strokeWidth, boolean vert) {
    try {
      Dimension preferred = getPreferredLogicalSize();
      Graphics2D g2d = (Graphics2D) g.create();
      g2d.setColor(color);
      g2d.setStroke(new BasicStroke(strokeWidth));

      double[] lineSpacings = getLineSpacings(numLines); // get spacings between each line

      for (int index = 0; index < numLines; index++) {
        if (vert) {
          g2d.drawLine((int) Math.round(lineSpacings[index] * preferred.width),
                  -1 * preferred.height,
                  (int) Math.round(lineSpacings[index] * preferred.width),
                  preferred.height);
        } else {
          g2d.drawLine(-1 * preferred.width,
                  (int) Math.round(lineSpacings[index] * preferred.height),
                  preferred.width,
                  (int) Math.round(lineSpacings[index] * preferred.height));
        }
      }
    } catch (IllegalArgumentException ex) {
      // number of lines must be >= 2
    }
  }

  /**
   * Creates an array of given length consisting of equally spaced values from -1 to 1.
   *
   * @param numLines number of lines desired in array. must be > 1
   * @return array of equally spaced values from -1 to 1
   * @throws IllegalArgumentException if the # of lines is <= 1
   */
  private double[] getLineSpacings(int numLines) {
    if (numLines <= 1) {
      throw new IllegalArgumentException("num lines must be > 1");
    }
    double[] d = new double[numLines];
    for (int i = 0; i < numLines; i++) {
      d[i] = -1 + (double) (2 * i) / (numLines - 1);
    }
    return d;
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
}
