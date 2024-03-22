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



  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    // Draw your calibration pattern here
    Rectangle bounds = this.getBounds();

    // g2d.translate(0, bounds.height);
    // g2d.scale(1, -1);

    Dimension preferred = getPreferredLogicalSize();

    g2d.transform(transformLogicalToPhysical());

    //ORIGINALLY:
    // x & y should be (0, 0) --> coordinates start in top left
    // RED line will start in upper left corner
    // BLUE line will start in upper right corner
    g2d.setColor(Color.RED);
    //NOW:
    // RED line starts in bottom left
    // BLUE line starts in bottom right
    g2d.drawLine(-1 * preferred.width, -1 * preferred.height,
            preferred.width, preferred.height);

    //g2d.drawLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
    g2d.setColor(Color.BLUE);
    g2d.drawLine(preferred.width, -1 * preferred.height,
            -1 * preferred.width, preferred.height);

    this.paintVerticalLines(g2d, 3);

/*
 g2d.drawLine(this.getWidth() / 3, 0,
            this.getWidth()/3, this.getHeight());
    g2d.drawLine(2 * this.getWidth() / 3, 0,
            2 * this.getWidth() /3, this.getHeight());
    g2d.drawLine(0, this.getHeight() / 3,
            this.getWidth(), this.getHeight() / 3);
    g2d.drawLine(0, 2 * this.getHeight() / 3,
            this.getWidth(), 2 * this.getHeight() / 3);

 */

  }

  private void paintVerticalLines(Graphics g, int numLines) {
    Dimension preferred = getPreferredLogicalSize();
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.BLACK);
    System.out.println("width: " + preferred.width);
    System.out.println("height: " + preferred.height);

    double[] lineSpacings = getLineSpacings(numLines);

    for (int index = 0; index < numLines; index++) {
      int x_coord = (index * preferred.width) / numLines;
      System.out.println("x coord: " +x_coord);
      g2d.drawLine((int) ((lineSpacings[index] * preferred.width) / numLines), -1 * preferred.height,
              (int) ((lineSpacings[index] * preferred.width) / numLines), preferred.height);

     // g2d.drawLine((i * this.getWidth()) / numLines, this.getHeight(),
    //          (i * this.getWidth()) / numLines, 0);
    }
  }

  private double[] getLineSpacings(int numLines) {
    double[] d = new double[numLines];
    for (int i = 0; i < numLines; i++){
      //d[i] = min + i * (max - min) / (points - 1);
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
