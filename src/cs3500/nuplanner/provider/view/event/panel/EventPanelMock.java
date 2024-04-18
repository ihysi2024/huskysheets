package view.event.panel;

import java.time.DayOfWeek;
import java.util.List;

import controller.IFeatures;
import model.IEvent;
import view.event.panel.IEventPanel;

/**
 * Mock for testing the EventPanel that uses the controller features.
 */
public class EventPanelMock extends EventPanel implements IEventPanel {

  private IFeatures features;
  private StringBuilder log;

  /**
   * constructor to set the log to a new StringBuilder.
   */
  public EventPanelMock() {
    super();
    this.log = new StringBuilder();
  }


  @Override
  public void addBottomPanelFeatures(IFeatures features) {
    this.features = features;
  }

  /**
   * simulates the press of the AddEvent button.
   */
  public void simulateAddEventButtonClicked(String name, boolean isOnline, String location,
                                            DayOfWeek startDay, String startTime,
                                            DayOfWeek endDay, String endTime, List<String> users) {
    features.addEvent(name, isOnline, location, startDay, startTime, endDay, endTime, users);
  }

  /**
   * simulates the press of the ModifyEvent button.
   */
  public void simulateModifyEventButtonClicked(
          IEvent originalEvent, String name, boolean isOnline, String location,
          DayOfWeek startDay, String startTime, DayOfWeek endDay,
          String endTime, List<String> users) {
    features.modifyEvent(originalEvent, name, isOnline, location, startDay, startTime,
            endDay, endTime, users);
    log.append("Modify event button clicked.\n");
  }

  /**
   * simulates the press of the RemoveEvent button.
   */
  public void simulateRemoveEventButtonClicked(String name,
                                               boolean isOnline, String location,
                                               DayOfWeek startDay, String startTime,
                                               DayOfWeek endDay, String endTime,
                                               List<String> users) {
    features.removeEvent(name, isOnline, location, startDay, startTime, endDay, endTime, users);
    log.append("Remove event button clicked.\n");
  }

  /**
   * gets the log as a string.
   * @return returns a string.
   */
  public String getLog() {
    return log.toString();
  }
}
