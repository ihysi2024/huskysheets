package view;

import controller.ViewFeatures;
import model.Event;
import model.ReadOnlyPlanner;

/**
 * Frame for the event window.
 */
public interface IEventView {
  void addFeatures(ViewFeatures features);

  void display(boolean show);

  void openEvent(ReadOnlyPlanner model);

  void closeEvent();
  void populateEventInPanel(Event event);
  void removeEventFromSchedule(ReadOnlyPlanner model);

  Event storeOpenedEvent();

  void createEvent(ReadOnlyPlanner model);
}
