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
import model.ReadOnlyPlanner;

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
  private JButton modifyEvent;
  private JButton removeEvent;

  private JLabel eventNameLabel;

  private JLabel locationLabel;

  private JLabel startTimeLabel;

  private JLabel endTimeLabel;

  private JLabel usersListLabel;


  private JTextField eventName;

  private JTextField location;

  private JTextField startTime;

  private JTextField endTime;

  private JList<String> usersList;

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
    this.setLayout(new FlowLayout());
    //this.setLayout(new SpringLayout());


    // adding a bunch of labels + buttons
    eventNameLabel = new JLabel("Event Name:");
    this.add(eventNameLabel);

    eventName = new JTextField(10);
    this.add(eventName);

    locationLabel = new JLabel("Location:");
    this.add(locationLabel);

    location = new JTextField(10);
    this.add(location);

    startTimeLabel = new JLabel("Start Time:");
    this.add(startTimeLabel);

    startTime = new JTextField(10);
    this.add(startTime);

    endTimeLabel = new JLabel("End Time:");
    this.add(endTimeLabel);

    endTime = new JTextField(10);
    this.add(endTime);

    usersListLabel = new JLabel("User List:");
    this.add(usersListLabel);

    usersList = new JList<>();
    this.add(usersList);


  //  pack();
    setVisible(true);

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
    return new Dimension(40, 40);
  }

  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    // Draw your calibration pattern here
    Rectangle bounds = this.getBounds();

    Dimension preferred = getPreferredLogicalSize();

    g2d.transform(transformLogicalToPhysical());

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

