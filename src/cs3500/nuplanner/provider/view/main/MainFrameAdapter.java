package cs3500.nuplanner.provider.view.main;

import java.util.Objects;

import controller.FeaturesAdapter;
import controller.ViewFeatures;
import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.view.event.IEventFrame;
import model.IEvent;
import model.ITime;
import model.IUser;
import view.IPlannerView;

public class MainFrameAdapter implements IPlannerView {


  private final IMainFrame adapteeMain;
  private final IGridPanel adapteeGrid;

  public MainFrameAdapter(IMainFrame adapteeMain, IGridPanel adapteeGrid) {
    this.adapteeMain = Objects.requireNonNull(adapteeMain);
    this.adapteeGrid = Objects.requireNonNull(adapteeGrid);
  }

  /**
   * Shows or hides the frame as specified.
   *
   * @param show true if frame should be shown, false otherwise
   */
  @Override
  public void display(boolean show) {
    this.adapteeMain.setVisible();
 //   this.adapteeGrid.

  }

  /**
   * Opens up the current user's schedule.
   */
  @Override
  public void openPlannerView() {
    //this.adapteeMain.
    this.adapteeMain.setVisible();

  }

  /**
   * Sets the current user to what is selected in the appropriate button in the schedule view.
   */
  @Override
  public String setCurrentUser() {
    return "";

  }

  /**
   * Retrieves the currently selected user.
   *
   * @return the currently selected user
   */
  @Override
  public IUser getCurrentUser() {
    return null;
  }

  /**
   * Adds a user to the drop-down menu.
   *
   * @param userName user's name to add
   */
  @Override
  public void addUserToDropdown(String userName) {

  }

  /**
   * Displays the desired user's schedule.
   *
   * @param userToShow desired user schedule to show
   */
  @Override
  public void displayUserSchedule(String userToShow) {
    //this.adapteeGrid.setCurrentUser(userToShow);
  }

  /**
   * Closes the current schedule view.
   */
  @Override
  public void closePlannerView() {
    this.adapteeMain.closeScheduleFrame();
  }

  /**
   * Adds feature listeners available on this panel, including the button clicks for
   * creating and scheduling events, adding/saving calendars, and selecting a user.
   *
   * @param features available features
   */
  @Override
  public void addFeatures(ViewFeatures features) {
    IFeatures adaptedFeatures = new FeaturesAdapter(features);
    this.adapteeMain.addFeatures(adaptedFeatures);
    this.adapteeGrid.addGridPanelFeatures(adaptedFeatures);
  }

  /**
   * Handles the clicks in schedule panel. Specifically handles clicking on an event in the
   * schedule and opening up the corresponding view.
   *
   * @param features features available
   */
  @Override
  public void addClickListener(ViewFeatures features) {
   // this.adapteeMain.
  }

  /**
   * Allowing user to select an .xml file that contains the desired calendar.
   * Automatically starts in current directory.
   */
  @Override
  public String addCalendarInfo() {
    return null;
  }

  /**
   * Allowing user to select a folder where they will export the user schedules.
   * Automatically starts in current directory.
   */
  @Override
  public String saveCalendarInfo() {
    return null;
  }

  /**
   * Finds the event that is occurring at the specified time. If two events start and end at the
   * same time, returns the earlier event.
   *
   * @param timeOfEvent desired time to search at
   * @return Event occuring at that time, null otherwise
   */
  @Override
  public IEvent findEventAtTime(ITime timeOfEvent) {
    return null;

  }
}
