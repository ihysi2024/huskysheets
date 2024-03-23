package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.ReadOnlyPlanner;
import model.Time;

import static java.lang.Math.floor;
import static model.Time.stringToTime;

public class PlannerPanel extends JPanel implements IScheduleView{

  /**
   * Our view will need to display a model, so it needs to get the current sequence from the model.
   */
  private final ReadOnlyPlanner model;
  /**
   * We'll allow an arbitrary number of listeners for our events...even if
   * we happen right now to only expect a single listener.
   */
  private final List<ViewFeatures> featuresListeners;

  private JButton scheduleEventButton;

  private JButton createEventButton;

  private JButton selectUserButton; // I don't think this will actually be a JButton, some other type?

  /**
   * Creates a panel that will house the view representation of the Simon game
   * with clicking capabilities.
   * @param model desired model to represent Simon game
   */
  public PlannerPanel(ReadOnlyPlanner model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);

    //createEventButton = new JButton("Create Event");
    //createEventButton.setActionCommand("Create Event");
    //this.add(createEventButton);
    System.out.println("Got to constructor");


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


  @Override
  public void addFeatureListener(ViewFeatures features) {

  }

  @Override
  public void display(boolean show) {

  }

  @Override
  public void openEventView(ReadOnlyPlanner model) {

  }

  public void addFeatures(ViewFeatures features) {
    System.out.println("got to add features in planner panel");
    createEventButton.addActionListener(evt -> features.openEventView());
    this.addMouseListener(new MouseEventsListener() {
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        features.openEventView();
      }
    });
  }

  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  private void paintEvent(Graphics g, IEvent event) {
    Color color = new Color(255, 100, 200, 100);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(color);

    Time startTime = event.getStartTime();
    Time endTime = event.getEndTime();

    int rectWidth = (int) Math.round(this.getWidth() / 7.0); // constant, width of one day

    int[] eventStartCoords = this.timeToPaintLoc(startTime);
    int[] eventEndCoords = this.timeToPaintLoc(endTime);

    int rectHeight = eventEndCoords[1] - eventStartCoords[1];

    g2d.fillOval(0, 0, 40, 40);


    if (eventStartCoords[0] == eventEndCoords[0]) { // event starts + ends on same day
      g2d.fillRect(eventStartCoords[0], eventStartCoords[1], rectWidth, rectHeight);
    }
    else {
      if (eventEndCoords[0] < eventStartCoords[0]) { // event goes to next week
        endTime = new Time(Time.Day.SATURDAY, 23, 59);
        int[] sunday2359 = this.timeToPaintLoc(endTime);
        eventEndCoords[0] = sunday2359[0]; // setting end day to Saturday
        eventEndCoords[1] = sunday2359[1]; // setting end time to 23:59 PM
      }

      int endOfFirstDay =
              (int) Math.round(this.minLoc(new Time(Time.Day.SATURDAY, 23, 59))
                      * this.getHeight());
      int rectHeightFirstDay = endOfFirstDay - eventStartCoords[1];
      g2d.fillRect(eventStartCoords[0], eventStartCoords[1], rectWidth, rectHeightFirstDay);

      int startDayIndex = startTime.getDate().getDayIdx();
      int endDayIndex = endTime.getDate().getDayIdx();

      for (int indexFullDay = startDayIndex + 1; indexFullDay < endDayIndex; indexFullDay++) {
        int[] currDayCoords = this.timeToPaintLoc(startTime.indexToTime(indexFullDay));
        g2d.fillRect(currDayCoords[0], currDayCoords[1], rectWidth, this.getHeight());
      }

      // draw the last day. starts at 00:00, ends at actual end of the event
      int startOfLastDay =
              (int) Math.round(this.minLoc(new Time(Time.Day.SATURDAY, 0, 0))
                      * this.getHeight());

      int[] endDayCoords = this.timeToPaintLoc(endTime.indexToTime(endDayIndex));

      g2d.fillRect(endDayCoords[0], endDayCoords[1], rectWidth, eventEndCoords[1]);
    }

    // get the start + end coordinates
    // make sure that you're only looking at the current week (don't roll over to next one)
  }

  /**
   * Calculates the top left coordinate of the given time. The x coordinate corresponds
   * to the day, the y coordinate corresponds to the time to minute granularity.
   * Changes based on the size of the board.
   *
   * @param time given time
   * @return 2-value int array containing top left coordinate of given time
   */
  private int[] timeToPaintLoc(Time time) {
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
  private double minLoc(Time time) {
    int timePos = time.minutesSinceMidnight();
    return timePos / (60.0*24.0);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    this.paintLines(g2d, Color.GRAY, 8, 1, true);
    this.paintLines(g2d, Color.GRAY, 25, 1, false);
    this.paintLines(g2d, Color.BLACK, 7, 2, false);

    this.paintEvent(g, new Event("CS3500 Morning Lecture",
            new Time(Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat"))));

    this.paintEvent(g, new Event("CS3500 Afternoon Lecture",
            new Time(Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 15, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat"))));

    this.paintEvent(g, new Event("Sleep",
            new Time(Time.Day.WEDNESDAY, 18, 35),
            new Time(Time.Day.MONDAY, 15, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat"))));

    this.paintEvent(g, new Event("Retreat",
            new Time(Time.Day.SUNDAY, 0, 5),
            new Time(Time.Day.TUESDAY, 1, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat"))));

    /*
        Dimension preferred = getPreferredLogicalSize();
            Rectangle bounds = this.getBounds();

    g2d.setColor(Color.RED);
    g2d.drawLine(-1 * preferred.width, -1 * preferred.height,
            preferred.width, preferred.height);

    //g2d.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    g2d.setColor(Color.BLUE);
    g2d.drawLine(preferred.width, -1 * preferred.height,
            -1 * preferred.width, preferred.height);
     */
  }

  /**
   * Paints desired number of equally spaced lines. Number of lines will include lines drawn on
   * the left and right sides of the bounds.
   * Can specify the color, width, and orientation of the lines.
   * @param g instance of Graphics
   * @param color desired color for lines
   * @param numLines number of equally spaced lines desired. must be >= 2 to work
   * @param strokeWidth desired stroke width for lines
   * @param vert true if vertical lines desired, false if horizontal
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
        }
        else {
          g2d.drawLine(-1 * preferred.width,
                  (int) Math.round(lineSpacings[index] * preferred.height),
                  preferred.width,
                  (int) Math.round(lineSpacings[index] * preferred.height));
        }
      }
    }
    catch (IllegalArgumentException ex) {
     // throw new IllegalArgumentException("number of lines must be >= 2");
    }

  }

  /**
   * Creates an array of given length consisting of equally spaced values from -1 to 1.
   * @param numLines number of lines desired in array. must be > 1
   * @return array of equally spaced values from -1 to 1
   * @throws IllegalArgumentException if the # of lines is <= 1
   */
  private double[] getLineSpacings(int numLines) {
    if (numLines <= 1) {
      throw new IllegalArgumentException("num lines must be > 1");
    }
    double[] d = new double[numLines];
    for (int i = 0; i < numLines; i++){
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
