package view;

import controller.ViewFeatures;
import model.Event;
import model.IEvent;
import model.IUser;
import model.ReadOnlyPlanner;
import model.User;

/**
 * Frame for the event window.
 */
public interface IEventView {
  void addFeatures(ViewFeatures features);

  void display(boolean show);

  void openEvent(ReadOnlyPlanner model);

  void closeEvent();
  void populateEventInPanel(IEvent event);
  void removeEventFromSchedule(ReadOnlyPlanner model, IEvent eventToRemove, IUser userRemoving);

  IEvent storeOpenedEvent();

  void createEvent(ReadOnlyPlanner model);
}
