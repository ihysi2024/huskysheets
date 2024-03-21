package view;

import controller.ViewFeatures;

/**
 * Frame for the event window.
 */
public interface IEventView {
  void addFeatures(ViewFeatures features);

  void display(boolean show);
}
