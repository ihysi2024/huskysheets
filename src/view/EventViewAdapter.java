package view;

import java.util.Objects;

import controller.FeaturesAdapter;
import controller.ViewFeatures;
import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.view.eventFrame.IEventFrame;
import model.EventAdapter;

import static model.EventAdapter.convertToProviderEventType;

public class EventViewAdapter implements IEventFrame {

  private final view.IEventView adaptee;

  public EventViewAdapter(view.IEventView adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  private IEventView getEventView() {
    return this.adaptee;
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void setVisible() {
    //this.adaptee.setVisible(true);
    IEventFrame viewAdapted = new EventViewAdapter(this.getEventView()); // is this circular??
    viewAdapted.setVisible();
  }

  /**
   * Autofills an EventFrame with information from an IEvent.
   *
   * @param event IEvent, containing the event information.
   */
  @Override
  public void autofill(IEvent event) {
    model.IEvent newEventAdapted = convertToProviderEventType(event);
    this.adaptee.populateEventContents(newEventAdapted);
  }

  /**
   * Closes the current eventFrame.
   */
  @Override
  public void closeEventFrame() {
    this.adaptee.closeEvent();
  }

  /**
   * adds features from the controller to be called in the EventFrame.
   *
   * @param features IFeatures from the controller.
   */
  @Override
  public void addFeatures(IFeatures features) {
    //IFeatures featuresAdapted = new FeaturesAdapter(features);
   // ViewFeatures featuresAdapted;
  //  this.getEventView();
 //   IEventFrame viewAdapted = new EventViewAdapter(this.getEventView()); // is this circular??
   // viewAdapted.addFeatures(features);
    this.adaptee.addFeatures(features);

  }
}
