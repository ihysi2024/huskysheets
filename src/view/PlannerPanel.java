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

    Dimension preferred = getPreferredLogicalSize();



    Time startTime = event.getStartTime();
    Time endTime = event.getEndTime();
   /// int rectWidth = (int) Math.round(preferred.width / 7.0);
   // int rectWidth = preferred.width - ((int) Math.round(preferred.width / 7.0));
   // int rectWidth = 14;
    int rectWidth = (int) Math.round(this.getWidth() / 7.0);
  //  int[] xy_coords = this.timeToPaintLoc(g, startTime);
    int[] xy_coords_test = this.timeToPaintLoc(g, startTime);

    int[] xy_coords = {xy_coords_test[0], 0};
    //int rectHeight = (int) Math.round(event.eventDuration() / 1440.0);
    int rectHeight = (int) Math.round(this.getWidth() / 6.0);
    System.out.println("rect width: " + rectWidth);
    System.out.println("rect height: " + rectHeight);

    System.out.println("curr width: " + this.getWidth());
    System.out.println("curr height: " + this.getHeight());

    g2d.fillOval(0, 0, 40, 40);

    g2d.fillRect(xy_coords[0], xy_coords[1], rectWidth, rectHeight);
  }

  // for painting the events
  // divide the view into 1440 sections (24 hours * 60 minutes), use that to determine
  // where an event should be drawn
  private int[] timeToPaintLoc(Graphics g, Time time) {
    Dimension preferred = getPreferredLogicalSize();
    int[] x_y_coords = new int[2];

    // width of the rectangle (for one day) will always be constant
    // width of the board / 7
    // (x, y) coordinate --> x coordinate will correspond to the day event is starting
    // y coordinate will correspond to the time
    // height of rectangle will correspond to duration

    System.out.print("preferred width: " + preferred.width);

    System.out.print("preferred height: " + preferred.height);

    int weekColXCoord = (int) Math.round((time.getDate().getDayIdx() / 7.0) * this.getWidth()); // int division will be an issue?
    //01:30 --> 90 minutes
    // (1440
  //  int weekColYCoord = (int) Math.round(time.minutesSinceMidnight() / 1440.0);
    int weekColYCoord = (int) Math.round(this.dayLoc(time) * this.getHeight());
   // int rectHeight = (int) Math.round(time.minutesSinceMidnight() / 1440.0);
    System.out.println("x coord: " + weekColXCoord);
    System.out.println("y coord: " + weekColYCoord);

    x_y_coords[0] = weekColXCoord;
    x_y_coords[1] = weekColYCoord;
    return x_y_coords;
  }

  // -1 to 1
  // need to split into 1440 sections and determine what coordinate to place
  private double minLoc(Time time) {
    int timePos = time.minutesSinceMidnight();
    // make an array
    double[] lineArr = getLineSpacings(60*24);
    return lineArr[timePos];
  }

  private double dayLoc(Time time) {
    int dayPos = time.getDate().getDayIdx();
    // make an array
    double[] lineArr = getLineSpacings(8);
    System.out.println("day coord: " + lineArr[dayPos]);
    return lineArr[dayPos];
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
