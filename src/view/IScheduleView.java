package view;

import controller.Controller;
import controller.ViewFeatures;
import model.ReadOnlyPlanner;

public interface IScheduleView {
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);

  void openScheduleView(ReadOnlyPlanner model);
  void closeScheduleView(ReadOnlyPlanner model);

  //void createEventView(ReadOnlyPlanner model);

  void addFeatures(ViewFeatures features);
}
