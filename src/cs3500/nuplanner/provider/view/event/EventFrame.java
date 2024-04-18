package cs3500.nuplanner.provider.view.event;

import javax.swing.JFrame;

import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IReadOnlyCentralSystem;
import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.view.event.EventPanelMock;

/**
 * Represents the event frame view that manipulates event name, location, time, and
 * the list of users.
 */
public class EventFrame extends JFrame implements IEventFrame {

  private EventPanel eventPanel;
  private int frameWidth = 600;
  private int frameHeight = 400;

  /**
   * Constructor for EventFrame that takes in a ReadOnly model.
   * @param model ReadOnly model.
   */
  public EventFrame(IReadOnlyCentralSystem model) {
    this.setSize(frameWidth, frameHeight);
    eventPanel = new EventPanel(model, frameWidth);
    this.setResizable(true);
    this.add(eventPanel);
  }

  /**
   * Constructor for EventFrame that takes in a ReadOnly model and an IEvent event to be
   * passed to the eventPanel.
   * @param model ReadOnly model.
   * @param event IEvent event to be passed in during the new EventPanel creation.
   */
  public EventFrame(IReadOnlyCentralSystem model, IEvent event) {
    this.setSize(frameWidth, frameHeight);
    eventPanel = new EventPanel(model, frameWidth, event);
    this.add(eventPanel);
  }

  /**
   * for mock testing.
   * @param model read-only model.
   * @param eventPanel mock eventPanel.
   */
  public EventFrame(IReadOnlyCentralSystem model, EventPanelMock eventPanel) {
    this.eventPanel = eventPanel;
    this.add(eventPanel);
  }

  @Override
  public void setVisible() {
    setVisible(true);
  }


  @Override
  public void autofill(IEvent event) {
    eventPanel.autoFill(event);
  }

  @Override
  public void closeEventFrame() {
    System.out.println("Disposing of EventFrame...");
    this.eventPanel.setVisible(false);
    this.dispose();
  }

  @Override
  public void addFeatures(IFeatures features) {
    eventPanel.addBottomPanelFeatures(features);


  }

}
