package view;

import controller.ViewFeatures;

/**
 * Frame for the event window.
 */
public interface IEventView {
  void addFeatureListener(ViewFeatures features);

  void display(boolean show);
}
