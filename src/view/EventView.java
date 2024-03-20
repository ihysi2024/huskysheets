package view;

import javax.swing.*;

import controller.ViewFeatures;
import model.ReadOnlyPlanner;

/**
 * Frame for the event pop-out window, where users will be able to see the functionality
 * related to creating new events, modifying existing events, and removing events.
 */
public class EventView extends JFrame implements IEventView {

  private final EventPanel panel;

  /**
   * Creates a view of the Simon game.
   *
   * @param model desired model to represent Simon game
   */
  public EventView(ReadOnlyPlanner model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new EventPanel(model);
    this.add(panel);
    this.pack();
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }
}
