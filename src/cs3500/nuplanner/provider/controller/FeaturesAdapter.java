package cs3500.nuplanner.provider.controller;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

import controller.ViewFeatures;
import cs3500.nuplanner.provider.model.ICentralSystem;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IEventTime;

public class FeaturesAdapter implements IFeatures {

  private final ViewFeatures adaptee;

  public FeaturesAdapter(ViewFeatures adaptee) {
    this.adaptee = Objects.requireNonNull(adaptee);
  }

  /**
   * Adds an event to the Central System.
   *
   * @param name      Name of the event.
   * @param isOnline  Flag indicating if the event is online or not.
   * @param place     Location of the event.
   * @param startDay  Start day of the event.
   * @param startTime Start time of the event.
   * @param endDay    End day of the event.
   * @param endTime   End time of the event.
   * @param users     List of users attending the event.
   */
  @Override
  public void addEvent(String name, boolean isOnline, String place, DayOfWeek startDay, String startTime, DayOfWeek endDay, String endTime, List<String> users) {

  }

  /**
   * Modifies an event in the Central System.
   *
   * @param originalEvent Original event before modification.
   * @param name          (Modified) name of the event.
   * @param isOnline      (Modified) flag indicating if the event is online or not.
   * @param place         (Modified) location of the event.
   * @param startDay      (Modified) start day of the event.
   * @param startTime     (Modified) start time of the event.
   * @param endDay        (Modified) end day of the event.
   * @param endTime       (Modified) end time of the event.
   * @param users         (Modified) list of users attending the event.
   */
  @Override
  public void modifyEvent(IEvent originalEvent, String name, boolean isOnline, String place, DayOfWeek startDay, String startTime, DayOfWeek endDay, String endTime, List<String> users) {

  }

  /**
   * Removes an event from the Central System.
   *
   * @param name      Name of the event.
   * @param isOnline  Flag indicating if the event is online or not.
   * @param place     Location of the event.
   * @param startDay  Start day of the event.
   * @param startTime Start time of the event.
   * @param endDay    End day of the event.
   * @param endTime   End time of the event.
   * @param users     List of users attending the event.
   */
  @Override
  public void removeEvent(String name, boolean isOnline, String place, DayOfWeek startDay, String startTime, DayOfWeek endDay, String endTime, List<String> users) {

  }

  /**
   * Opens a new event frame when an existing event is clicked on in the calendar and autofills
   * the fields with the event at the given time for the given user.
   *
   * @param currentUser The user the current schedule belongs to.
   * @param time        The time the expected event is at.
   */
  @Override
  public void openExistingEventFrame(String currentUser, IEventTime time) {

  }

  /**
   * Opens a new event frame when the Create Event button is clicked.
   */
  @Override
  public void openNewEventFrame() {

  }

  /**
   * Opens a new schedule frame when the Schedule Event button is clicked.
   */
  @Override
  public void openNewScheduleFrame() {

  }

  /**
   * Sets the CentralSystem model in the controller.
   *
   * @param model ICentralSystem model instance.
   * @throws NullPointerException if the given instance of the model is null.
   */
  @Override
  public void launch(ICentralSystem model) {

  }

  /**
   * Given the file path, save the list of user's schedules as XML.
   *
   * @param filePath the String path of where schedules are to be stored
   */
  @Override
  public void saveCalendarsXML(String filePath) {

  }

  /**
   * Takes a file path and converts it into a document to then be read by the model.
   *
   * @param filePath the String path of where a schedule is stored as an XML document.
   * @throws IOException                  IOException thrown if error.
   * @throws SAXException                 SAXException thrown if error.
   * @throws ParserConfigurationException ParserConfigurationException thrown in error.
   */
  @Override
  public void loadCalendarXML(String filePath) throws IOException, SAXException, ParserConfigurationException {

  }

  /**
   * Finds an available time to schedule an
   * event with the chosen strategy, either workhours or anytime,
   * given an event duration.
   *
   * @param name            String name of an event.
   * @param isOnline        Flag indicating if the event is online or not.
   * @param place           Location of the event.
   * @param durationMinutes Duration of an event.
   * @param userIds         list of users invited to event.
   */
  @Override
  public void scheduleEvent(String name, boolean isOnline, String place, String durationMinutes, List<String> userIds) {

  }
}
