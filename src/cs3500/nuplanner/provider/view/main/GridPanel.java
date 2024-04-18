package view.main.panel;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import controller.IFeatures;
import model.EventTime;
import model.IEvent;
import model.IEventTime;
import model.IReadOnlyCentralSystem;
import view.event.IEventFrame;

/**
 * Represents the Grid panel that adds the calendar grid with colored blocks
 * to represent events for a schedule to the main frame.
 *
 */
public class GridPanel extends JPanel implements IGridPanel {

  private IReadOnlyCentralSystem model;
  private IEventFrame eventFrame;
  private String currentUser;
  private boolean hostColorToggle;

  /**
   * Constructor for the GridPanel class that creates the calendar grid for the MainFrame.
   * @param model read-only model.
   * @param eventFrame EventFrame instance
   */
  public GridPanel(IReadOnlyCentralSystem model, IEventFrame eventFrame) {
    super();
    this.model = model;
    this.eventFrame = eventFrame;
    if (model.accessUsers().isEmpty()) {
      currentUser = null;
    } else {
      this.currentUser = model.accessUsers().get(0);
    }
    hostColorToggle = false;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D)g; //downpass - cast into a more specific type (subclass)

    this.repaint();
    //draw events on the board
    if (currentUser != null) {
      for (IEvent event : model.accessEvents(this.currentUser)) {
        addEvent(g2d, event);
      }
    }

    g2d.setColor(Color.BLACK);
    // vertical lines
    for (int i = 1; i < 7; i++) {
      g2d.drawLine(i * getWidth() / 7, 0, i * getWidth() / 7, getHeight());
    }

    // horizontal lines
    for (int i = 1; i < 24; i++) {
      if (i % 4 == 0) {
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(0, i * getHeight() / 24, getWidth(), i * getHeight() / 24);
      } else {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, i * getHeight() / 24, getWidth(), i * getHeight() / 24);
      }
    }
  }

  private void addEvent(Graphics2D g2d, IEvent event) {
    Map<DayOfWeek, Integer> dayOfWeekNumbers = dayOfNumbersMap();
    DayOfWeek startDay = event.accessStartDay();
    int startTime = event.accessStartTime().accessTimeAsInt();
    DayOfWeek endDay = event.accessEndDay();
    int endTime = event.accessEndTime().accessTimeAsInt();
    int dayDifference = Math.abs(dayOfWeekNumbers.get(startDay) - dayOfWeekNumbers.get(endDay));
    if (hostColorToggle && event.isHost(currentUser)) {
      g2d.setColor(Color.CYAN);
    } else {
      g2d.setColor(Color.RED);
    }
    if (startDay == endDay) {
      g2d.fillRect(dayOfWeekNumbers.get(startDay) * getWidth() / 7,
              startTime * getHeight() / 2400,
              getWidth() / 7, (endTime - startTime) * getHeight() / 2400);
    }
    else if (dayDifference == 1) {
      //start day rectangle
      g2d.fillRect(dayOfWeekNumbers.get(startDay) * getWidth() / 7,
              startTime * getHeight() / 2400,
              getWidth() / 7, (2359 - startTime) * getHeight() / 2400);
      //end day rectangle
      g2d.fillRect(dayOfWeekNumbers.get(endDay) * getWidth() / 7, 0,
              getWidth() / 7, endTime * getHeight() / 2400);
    }
    else if (dayDifference > 1) {
      //start day rectangle
      g2d.fillRect(dayOfWeekNumbers.get(startDay) * getWidth() / 7,
              startTime * getHeight() / 2400,
              getWidth() / 7, (2359 - startTime) * getHeight() / 2400);

      int curDay = dayOfWeekNumbers.get(startDay);
      for (int fillNum = 1; fillNum < dayDifference; fillNum++) {
        if (curDay + 1 > 6) {
          curDay = 0;
        } else {
          curDay++;
        }
        g2d.fillRect(curDay * getWidth() / 7, 0,
                getWidth() / 7, 2359);
      }

      //end day rectangle
      g2d.fillRect(dayOfWeekNumbers.get(endDay) * getWidth() / 7, 0,
              getWidth() / 7, endTime * getHeight() / 2400);
    }

  }

  private Map<DayOfWeek, Integer> dayOfNumbersMap() {
    HashMap<DayOfWeek, Integer> dayOfWeekNumbers = new HashMap<>();
    dayOfWeekNumbers.put(DayOfWeek.SUNDAY, 0);
    dayOfWeekNumbers.put(DayOfWeek.MONDAY, 1);
    dayOfWeekNumbers.put(DayOfWeek.TUESDAY, 2);
    dayOfWeekNumbers.put(DayOfWeek.WEDNESDAY, 3);
    dayOfWeekNumbers.put(DayOfWeek.THURSDAY, 4);
    dayOfWeekNumbers.put(DayOfWeek.FRIDAY, 5);
    dayOfWeekNumbers.put(DayOfWeek.SATURDAY, 6);
    return dayOfWeekNumbers;
  }


  //set largest x to 1440 (24*60)
  //get hours by x/60
  //get minutes by x%60
  //set largest y to 70
  //get day by y/10

  private Dimension getPreferredLogicalSize() {
    return new Dimension(70, 1440);
  }

  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    return ret;
  }

  @Override
  public void addGridPanelFeatures(IFeatures features) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Point2D logicalP = transformPhysicalToLogical().transform(e.getPoint(), null);
        System.out.println("coordinates: " + logicalP.getX() + ", " + logicalP.getY());

        IEventTime time = calculateTime(logicalP);
        System.out.println("Current time clicked: " + time.toString());

        if (currentUser != null) {
          if (model.findEvent(currentUser, time) != null) {
            eventFrame.autofill(model.findEvent(currentUser, time));
            eventFrame.setVisible(); //send to controller?!?
          }
        }
      }

      private IEventTime calculateTime(Point2D coordinate) {
        Map<DayOfWeek, Integer> dayOfWeekNumbers = dayOfNumbersMap();
        int day = (int)coordinate.getX() / 10;
        DayOfWeek currentDay = DayOfWeek.MONDAY;
        int hour = (int)coordinate.getY() / 60;
        int minute = (int)coordinate.getY() % 60;

        for (Entry<DayOfWeek, Integer> entry : dayOfWeekNumbers.entrySet()) {
          if (entry.getValue() == day) {
            currentDay = entry.getKey();
            break;
          }
        }
        return new EventTime(currentDay, hour, minute);
      }

      @Override
      public void mousePressed(MouseEvent e) {
        //not used
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //not used
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //not used
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //not used
      }
    });
  }

  @Override
  public void setCurrentUser(String user) {
    System.out.println("setCurrentUser has been called!");
    this.currentUser = user;
    System.out.println("current user in grid panel: " + currentUser);
  }

  @Override
  public void setHostColorToggle() {
    hostColorToggle = !hostColorToggle;
    System.out.println("The host color toggle is: " + hostColorToggle);
  }

}

