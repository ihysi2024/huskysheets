package view;

import controller.ViewFeatures;
import model.ReadOnlyPlanner;

public interface IScheduleView {
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);

  void openEventView(ReadOnlyPlanner model);
}
