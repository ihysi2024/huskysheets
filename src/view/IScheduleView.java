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
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);

  void openScheduleView(ReadOnlyPlanner model);

  void setCurrentUser(ReadOnlyPlanner model);

  IUser getCurrentUser();

  void displayUserSchedule(ReadOnlyPlanner model, IUser userToShow);
  void closeScheduleView(ReadOnlyPlanner model);

  void addFeatures(ViewFeatures features);

  void addClickListener(ViewFeatures features);

  void addCalendarInfo();

  void saveCalendarInfo();
  IEvent findEventAtTime(ITime timeOfEvent);

}
