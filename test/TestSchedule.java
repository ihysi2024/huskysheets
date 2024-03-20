import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import model.Event;
import model.NUPlanner;
import model.Schedule;
import model.Time;
import model.User;

/**
 * Class to test functionality of Schedule class.
 */
public class TestSchedule {
  private Event newMorningLec;
  private Event morningLec;
  private Event morningLecOverlapping;
  private Event morningLecSameTime;
  private Event morningLecEndTime;

  private Event afternoonLec;
  private Event officeHours;

  private Event sleep;

  private Schedule emptySchedule;

  @Before
  public void setUp() {

    this.emptySchedule = new Schedule(new ArrayList<>());

    User profLuciaUser = new User("Prof Lucia", emptySchedule);
    User studentAnonUser = new User("Student Anon", emptySchedule);
    User chatUser = new User("Chat", emptySchedule);

    this.newMorningLec = new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 16, 00),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    this.morningLec = new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // same time as the morning lecture
    this.morningLecSameTime = new Event("same time morning lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // overlapping time as the morning lecture
    this.morningLecOverlapping = new Event("overlapping morning lecture ",
            new Time( Time.Day.TUESDAY, 8, 30),
            new Time(Time.Day.TUESDAY, 10, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // start time same as end time of morning lecture
    this.morningLecEndTime = new Event("same start time as end time",
            new Time( Time.Day.TUESDAY, 11, 30),
            new Time(Time.Day.TUESDAY, 12, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    this.afternoonLec = new Event("CS3500 Afternoon Lecture",
            new Time(Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 15, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat")));

    this.sleep = new Event("Sleep",
            new Time(Time.Day.FRIDAY, 18, 0),
            new Time(Time.Day.SUNDAY, 12, 0),
            true,
            "Home",
            new ArrayList<>(Arrays.asList("Prof. Lucia")));

    Schedule luciaSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec, afternoonLec,
            sleep)));
    Schedule studentAnonSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec)));
    Schedule chatSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec, afternoonLec)));

    profLuciaUser = new User("Prof. Lucia", luciaSchedule);
    studentAnonUser = new User("Student Anon", studentAnonSchedule);
    chatUser = new User("Chat", chatSchedule);

    Event morningSnack = new Event("snack",
            new Time(Time.Day.TUESDAY, 11, 30),
            new Time(Time.Day.TUESDAY, 11, 45),
            false,
            "Churchill Hall 101",
            List.of("Student Anon"));

    this.officeHours = new Event("office hours",
            new Time(Time.Day.MONDAY, 12, 01),
            new Time(Time.Day.MONDAY, 12, 30),
            false,
            "Churchill Hall 101",
            List.of("Student Anon",
                    "Prof. Lucia"));

    LinkedHashSet<User> users = new LinkedHashSet<>();
    users.add(profLuciaUser);
    users.add(studentAnonUser);
    users.add(chatUser);
    NUPlanner plannerSystem = new NUPlanner(users);
  }

  /**
   * Test observational methods - getEvents.
   */
  @Test
  public void testGetEvents() {
    Assert.assertEquals(List.of(), emptySchedule.getEvents());
    emptySchedule.addEvent(this.morningLec);
    Assert.assertEquals(List.of(this.morningLec), emptySchedule.getEvents());
  }

  /**
   * Test that an event can be correctly added to a schedule.
   */
  @Test
  public void testAddEvent() {
    // starting schedule - no events yet
    Assert.assertEquals(0, emptySchedule.getEvents().size());
    // add a valid event
    emptySchedule.addEvent(this.morningLec);
    Assert.assertEquals(1, emptySchedule.getEvents().size());

    // adding class at the same time or overlapping time
    Assert.assertThrows(IllegalArgumentException.class, () ->
            emptySchedule.addEvent(this.morningLecSameTime));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            emptySchedule.addEvent(this.morningLecOverlapping));

    // adding class that starts when previous class ends
    emptySchedule.addEvent(this.morningLecEndTime);
    Assert.assertEquals(2, emptySchedule.getEvents().size());
    emptySchedule.addEvent(this.afternoonLec);
    Assert.assertEquals(3, emptySchedule.getEvents().size());
  }

  /**
   * Test removing an event.
   */
  @Test
  public void testRemoveEvent() {
    emptySchedule.addEvent(this.morningLec);
    emptySchedule.addEvent(this.afternoonLec);
    emptySchedule.addEvent(this.sleep);
    emptySchedule.addEvent(this.officeHours);
    Assert.assertEquals(4, emptySchedule.getEvents().size());

    // try removing an event
    emptySchedule.removeEvent(this.morningLec);

    Assert.assertEquals(3, emptySchedule.getEvents().size());
    Assert.assertFalse(emptySchedule.getEvents().contains(this.morningLec));

    // try removing another event
    emptySchedule.removeEvent(this.sleep);

    Assert.assertEquals(2, emptySchedule.getEvents().size());
    Assert.assertFalse(emptySchedule.getEvents().contains(this.sleep));

    // removing an event that doesn't exit
    Assert.assertThrows(IllegalArgumentException.class, () ->
            emptySchedule.removeEvent(this.newMorningLec));

  }

  /**
   * Test that events can be accurately mapped to the days of the week.
   */

  @Test
  public void testdayToEventsMapping() {
    emptySchedule.addEvent(this.morningLec);
    emptySchedule.addEvent(this.afternoonLec);
    emptySchedule.addEvent(this.sleep);
    emptySchedule.addEvent(this.officeHours);

    Assert.assertEquals(List.of(),
            emptySchedule.dayToEventsMappping().get(Time.Day.SUNDAY));
    Assert.assertEquals(List.of(this.officeHours),
            emptySchedule.dayToEventsMappping().get(Time.Day.MONDAY));
    Assert.assertEquals(List.of(this.morningLec, this.afternoonLec),
            emptySchedule.dayToEventsMappping().get(Time.Day.TUESDAY));
    Assert.assertEquals(List.of(), emptySchedule.dayToEventsMappping().get(Time.Day.WEDNESDAY));
    Assert.assertEquals(List.of(), emptySchedule.dayToEventsMappping().get(Time.Day.THURSDAY));
    Assert.assertEquals(List.of(this.sleep),
            emptySchedule.dayToEventsMappping().get(Time.Day.FRIDAY));
    Assert.assertEquals(List.of(), emptySchedule.dayToEventsMappping().get(Time.Day.SATURDAY));
  }

  /**
   * Test that a schedule can be correctly converted to a string.
   */
  @Test
  public void testScheduleToString() {
    String luciaSched = "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "name: CS3500 Morning Lecture\n" +
            "time: Tuesday: 09:50->Tuesday: 11:30\n" +
            "location: Churchill Hall 101\n" +
            "online: false\n" +
            "users: Prof. Lucia\n" +
            "Student Anon\n" +
            "Chat          \n" +
            "name: CS3500 Afternoon Lecture\n" +
            "time: Tuesday: 13:35->Tuesday: 15:15\n" +
            "location: Churchill Hall 101\n" +
            "online: false\n" +
            "users: Prof. Lucia\n" +
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "name: Sleep\n" +
            "time: Friday: 18:00->Sunday: 12:00\n" +
            "location: Home\n" +
            "online: true\n" +
            "users: Prof. Lucia          \n" +
            "Saturday: \n";
    emptySchedule.addEvent(this.morningLec);
    emptySchedule.addEvent(this.afternoonLec);
    emptySchedule.addEvent(this.sleep);

    Assert.assertEquals(luciaSched, emptySchedule.scheduleToString());
  }

  /**
   * Test that a schedule can be correctly converted to its XML format.
   */
  @Test
  public void testScheduleToXML() {
    String luciaSchedXML = "<event>\n" +
            "     <name>CS3500 Morning Lecture</name>\n" +
            "     <time>\n" +
            "          <start-day>TUESDAY</start-day>\n" +
            "          <start>0950</start>\n" +
            "          <end-day>TUESDAY</end-day>\n" +
            "          <end>1130</end>\n" +
            "     </time>\n" +
            "     <location>\n" +
            "          <online>false</online>\n" +
            "          <place>Churchill Hall 101</place>\n" +
            "     </location>\n" +
            "     <users>\n" +
            "          <uid>Prof. Lucia</uid>\n" +
            "          <uid>Student Anon</uid>\n" +
            "          <uid>Chat</uid>\n" +
            "     </users>\n" +
            "</event>\n" +
            "<event>\n" +
            "     <name>CS3500 Afternoon Lecture</name>\n" +
            "     <time>\n" +
            "          <start-day>TUESDAY</start-day>\n" +
            "          <start>1335</start>\n" +
            "          <end-day>TUESDAY</end-day>\n" +
            "          <end>1515</end>\n" +
            "     </time>\n" +
            "     <location>\n" +
            "          <online>false</online>\n" +
            "          <place>Churchill Hall 101</place>\n" +
            "     </location>\n" +
            "     <users>\n" +
            "          <uid>Prof. Lucia</uid>\n" +
            "          <uid>Chat</uid>\n" +
            "     </users>\n" +
            "</event>\n" +
            "<event>\n" +
            "     <name>Sleep</name>\n" +
            "     <time>\n" +
            "          <start-day>FRIDAY</start-day>\n" +
            "          <start>1800</start>\n" +
            "          <end-day>SUNDAY</end-day>\n" +
            "          <end>1200</end>\n" +
            "     </time>\n" +
            "     <location>\n" +
            "          <online>true</online>\n" +
            "          <place>Home</place>\n" +
            "     </location>\n" +
            "     <users>\n" +
            "          <uid>Prof. Lucia</uid>\n" +
            "     </users>\n" +
            "</event>\n";

    emptySchedule.addEvent(this.morningLec);
    emptySchedule.addEvent(this.afternoonLec);
    emptySchedule.addEvent(this.sleep);

    Assert.assertEquals(luciaSchedXML, emptySchedule.scheduleToXMLFormat());
  }

  /**
   * Tests that the list of events occurring at a given time are correctly identified.
   */
  @Test
  public void testEventsOccurring() {
    emptySchedule.addEvent(this.morningLec);
    emptySchedule.addEvent(this.morningLecEndTime);
    emptySchedule.addEvent(this.afternoonLec);
    emptySchedule.addEvent(this.sleep);

    // event starting at the given time
    Assert.assertEquals(List.of(this.morningLec),
            emptySchedule.eventsOccurring(
                    new Time(Time.Day.TUESDAY, 9, 50)));

    // event starting before the given time but ending after
    Assert.assertEquals(List.of(this.morningLec),
            emptySchedule.eventsOccurring(
                    new Time(Time.Day.TUESDAY, 10, 15)));

    // no event occurring at the time
    Assert.assertEquals(List.of(),
            emptySchedule.eventsOccurring(
                    new Time(Time.Day.WEDNESDAY, 14, 15)));

    // an event starting at that time and ending at that time
    Assert.assertEquals(List.of(this.morningLec, this.morningLecEndTime),
            emptySchedule.eventsOccurring(
                    new Time(Time.Day.TUESDAY, 11, 30)));

  }
}
