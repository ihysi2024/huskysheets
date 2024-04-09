package view;

import controller.ViewFeatures;
import model.IEvent;
import model.ITime;
import model.IUser;

/**
 * Represents the interface for a planner view to be implemented through a panel
 * and frame.
 */
public interface IPlannerView {


  /**
   * Shows or hides the frame as specified.
   *
   * @param show true if frame should be shown, false otherwise
   */
  void display(boolean show);

  /**
   * Opens up the current user's schedule.
   */
  void openPlannerView();

  /**
   * Sets the current user to what is selected in the appropriate button in the schedule view.
   */
  void setCurrentUser();

  /**
   * Retrieves the currently selected user.
   *
   * @return the currently selected user
   */
  IUser getCurrentUser();

  /**
   * Adds a user to the drop-down menu.
   * @param userName user's name to add
   */
  void addUserToDropdown(String userName);

  /**
   * Displays the desired user's schedule.
   *
   * @param userToShow desired user schedule to show
   */
  void displayUserSchedule(String userToShow);

  /**
   * Closes the current schedule view.
   */
  void closePlannerView();

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */
  void addFeatures(ViewFeatures features);

  /**
   * Handles the clicks in schedule panel. Specifically handles clicking on an event in the
   * schedule and opening up the corresponding view.
   *
   * @param features features available
   */
  void addClickListener(ViewFeatures features);

  /**
   * Allowing user to select an .xml file that contains the desired calendar.
   * Automatically starts in current directory.
   */
  String addCalendarInfo();

  /**
   * Allowing user to select a folder where they will export the user schedules.
   * Automatically starts in current directory.
   */
  String saveCalendarInfo();

  /**
   * Finds the event that is occurring at the specified time. If two events start and end at the
   * same time, returns the earlier event.
   *
   * @param timeOfEvent desired time to search at
   * @return Event occuring at that time, null otherwise
   */
  IEvent findEventAtTime(ITime timeOfEvent);
}
