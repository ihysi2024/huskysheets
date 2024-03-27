package view;

import java.awt.*;

import controller.Controller;
import controller.ViewFeatures;
import model.IEvent;
import model.ITime;
import model.IUser;
import model.ReadOnlyPlanner;
import model.User;

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
