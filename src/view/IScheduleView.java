package view;

import java.awt.*;

import controller.Controller;
import controller.ViewFeatures;
import model.ReadOnlyPlanner;
import model.User;

public interface IScheduleView {
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);

  void openScheduleView(ReadOnlyPlanner model);

  void displayUserSchedule(ReadOnlyPlanner model, User userToShow);
  void closeScheduleView(ReadOnlyPlanner model);

  //void createEventView(ReadOnlyPlanner model);

  void addFeatures(ViewFeatures features);

  void addClickListener(ViewFeatures features);
}
