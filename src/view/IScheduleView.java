package view;

import controller.Controller;
import controller.ViewFeatures;
import model.ReadOnlyPlanner;

public interface IScheduleView {
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);

  void openEventView(ReadOnlyPlanner model);

  void addFeatures(ViewFeatures features);
}
