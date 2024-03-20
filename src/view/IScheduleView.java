package view;

import controller.ViewFeatures;

public interface IScheduleView {
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);
}
