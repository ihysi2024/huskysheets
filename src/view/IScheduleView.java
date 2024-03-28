package view;

import controller.ViewFeatures;
import model.IEvent;
import model.ITime;
import model.IUser;

/**
 * Represents the interface for a schedule view to be implemented through a panel
 * and frame.
 */
public interface IScheduleView {

  void display(boolean show);

  void openScheduleView();

  void setCurrentUser();

  IUser getCurrentUser();

  void displayUserSchedule(IUser userToShow);

  void closeScheduleView();

  void addFeatures(ViewFeatures features);

  void addClickListener(ViewFeatures features);

  void addCalendarInfo();

  void saveCalendarInfo();

  IEvent findEventAtTime(ITime timeOfEvent);

}
