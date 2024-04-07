import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import model.Event;
import model.NUPlanner;
import model.PlannerSystem;
import model.Schedule;
import model.Time;
import model.User;
import view.IScheduleTextView;
import view.ScheduleTextView;

/**
 * Class to test the functionality of the Event class.
 */
public class TestEvent {

  private Event morningLec;
  private Event morningLecOverlapping;
  private Event morningLecSameTime;
  private Event morningLecEndTime;
  private Event afternoonLec;
  private Event afternoonLecOverlapping;
  private Event afternoonLecEndAfter;

  private IScheduleTextView textV;

  @Before
  public void setUp() {
    PlannerSystem modelForTextView = new NUPlanner(new ArrayList<>());
    this.textV = new ScheduleTextView(modelForTextView, new StringBuilder());

    Schedule emptySchedule = new Schedule(new ArrayList<>());

    User profLuciaUser = new User("Prof Lucia", emptySchedule);
    User studentAnonUser = new User("Student Anon", emptySchedule);
    User chatUser = new User("Chat", emptySchedule);

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

    // afternoon lecture
    this.afternoonLec = new Event("CS3500 Afternoon Lecture",
            new Time(Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 15, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat")));

    this.afternoonLecOverlapping = new Event("CS3500 Afternoon Lecture",
            new Time(Time.Day.TUESDAY, 14, 30),
            new Time(Time.Day.TUESDAY, 15, 10),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat")));

    this.afternoonLecEndAfter = new Event("CS3500 Afternoon Lecture",
            new Time(Time.Day.TUESDAY, 14, 30),
            new Time(Time.Day.TUESDAY, 15, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Chat")));


    // sleep
    Event sleep = new Event("Sleep",
            new Time(Time.Day.FRIDAY, 18, 0),
            new Time(Time.Day.SUNDAY, 12, 0),
            true,
            "Home",
            new ArrayList<>(Arrays.asList("Prof. Lucia")));

  }

  /**
   * Test constructor of an event to ensure correct exceptions thrown for invalid events.
   */
  @Test
  public void testConstructor() {
    // exactly a week-long event or event has no duration
    Assert.assertThrows(IllegalArgumentException.class, () -> new Event("Meeting",
            new Time(Time.Day.TUESDAY, 13, 00),
            new Time(Time.Day.TUESDAY, 13, 00),
            true, "LA",
            List.of("Prof. Lucia")));


    // end time is null
    Assert.assertThrows(IllegalArgumentException.class, () -> new Event("Meeting",
            new Time(Time.Day.TUESDAY, 13, 00),
            null,
            true, "LA",
            List.of("Prof. Lucia")));

    // start time is null
    Assert.assertThrows(IllegalArgumentException.class, () -> new Event("Meeting",
            null,
            new Time(Time.Day.TUESDAY, 13, 00),
            true, "LA",
            List.of("Prof. Lucia")));

    // there are no invitees/hosts
    Assert.assertThrows(IllegalArgumentException.class, () -> new Event("Meeting",
            new Time(Time.Day.TUESDAY, 13, 00),
            new Time(Time.Day.TUESDAY, 11, 00),
            true, "LA",
            List.of()));

    // null list of invitees/hosts
    Assert.assertThrows(IllegalArgumentException.class, () -> new Event("Meeting",
            new Time(Time.Day.TUESDAY, 13, 00),
            new Time(Time.Day.TUESDAY, 11, 00),
            true, "LA",
            null));

    // the event has no name
    Assert.assertThrows(IllegalArgumentException.class, () -> new Event(null,
            new Time( Time.Day.TUESDAY, 18, 00),
            new Time(Time.Day.TUESDAY, 16, 00),
            true, "LA",
            List.of("Prof. Lucia")));
  }

  /**
   * Test the observational methods used in events.
   */

  @Test
  public void testObservationalMethods() {
    // observe the start time of the event
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.TUESDAY, 9, 50)),
            textV.timeToString(this.morningLec.getStartTime()));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.TUESDAY, 13, 35)),
            textV.timeToString(this.afternoonLec.getStartTime()));
    // observe the end time of the event
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.TUESDAY, 11, 30)),
            textV.timeToString(this.morningLec.getEndTime()));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.TUESDAY, 15, 15)),
            textV.timeToString(this.afternoonLec.getEndTime()));
    // observe the users of the event
    Assert.assertEquals(List.of("Prof. Lucia", "Student Anon", "Chat"), this.morningLec.getUsers());
    Assert.assertEquals(List.of("Prof. Lucia", "Chat"), this.afternoonLec.getUsers());
  }

  /**
   * Test whether overlappingEvents(Event otherEvent) correctly determines whether
   * a given event overlaps with this event.
   */

  @Test
  public void testOverlappingEvents() {
    // an event overlaps with itself
    Assert.assertTrue(this.morningLec.overlappingEvents(this.morningLec));
    // an event overlaps with another event at the same time
    Assert.assertTrue(this.morningLec.overlappingEvents(this.morningLecSameTime));
    // an event overlaps with an event that starts before but ends during
    Assert.assertTrue(this.morningLec.overlappingEvents(this.morningLecOverlapping));
    // an event overlaps with an event that starts after this event starts
    // and ends before this event ends
    Assert.assertTrue(this.afternoonLec.overlappingEvents(this.afternoonLecOverlapping));
    // an event overlaps with an event that starts after this event starts
    // and end after this event ends
    Assert.assertTrue(this.afternoonLec.overlappingEvents(this.afternoonLecEndAfter));
    // an event does not overlap with an event that starts when the first event ends
    Assert.assertFalse(this.morningLec.overlappingEvents(this.morningLecEndTime));
    // an event does not overlap with an event that starts after the first event ends
    Assert.assertFalse(this.morningLec.overlappingEvents(this.afternoonLec));
  }

  /**
   * Test that an event can be correctly converted to a string for text view.
   */
  @Test
  public void testEventToString() {
    String morningLec = "name: CS3500 Morning Lecture\n"
            + "time: Tuesday: 09:50->Tuesday: 11:30\n"
            + "location: Churchill Hall 101\n"
            + "online: false\n"
            + "users: Prof. Lucia\n"
            + "Student Anon\n"
            + "Chat";
    Assert.assertEquals(morningLec, textV.eventToString(this.morningLec));

    String afternoonLec  = "name: CS3500 Afternoon Lecture\n"
            + "time: Tuesday: 13:35->Tuesday: 15:15\n"
            + "location: Churchill Hall 101\n"
            + "online: false\n"
            + "users: Prof. Lucia\n"
            + "Chat";

    Assert.assertEquals(afternoonLec , textV.eventToString(this.afternoonLec));

  }

  /**
   * Test equals method for Event.
   */

  @Test
  public void testEquals() {
    Event morningLecSame = new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    Assert.assertEquals(morningLecSame, this.morningLec);
    Assert.assertNotEquals(this.morningLec, this.afternoonLec);
  }


  /**
   * Test that an events fields can be correctly tagged for XML exports.
   */
  @Test
  public void testEventToXML() {
    String indents5 = " ".repeat(5);
    String indents10 = " ".repeat(10);
    String morningLecXML = "<event>\n"
            + indents5 + "<name>CS3500 Morning Lecture</name>\n"
            + indents5 + "<time>\n"
            + indents10 + "<start-day>TUESDAY</start-day>\n"
            + indents10 + "<start>0950</start>\n"
            + indents10 + "<end-day>TUESDAY</end-day>\n"
            + indents10 + "<end>1130</end>\n"
            + indents5 + "</time>\n"
            + indents5 + "<location>\n"
            + indents10 + "<online>false</online>\n"
            + indents10 + "<place>Churchill Hall 101</place>\n"
            + indents5 + "</location>\n"
            + indents5 + "<users>\n"
            + indents10 + "<uid>Prof. Lucia</uid>\n"
            + indents10 + "<uid>Student Anon</uid>\n"
            + indents10 + "<uid>Chat</uid>\n"
            + indents5 + "</users>\n"
            + "</event>";
    Assert.assertEquals(morningLecXML, this.morningLec.eventToXMLFormat());
    String afternoonLecXML = "<event>\n"
            + indents5 + "<name>CS3500 Afternoon Lecture</name>\n"
            + indents5 + "<time>\n"
            + indents10 + "<start-day>TUESDAY</start-day>\n"
            + indents10 + "<start>1335</start>\n"
            + indents10 + "<end-day>TUESDAY</end-day>\n"
            + indents10 + "<end>1515</end>\n"
            + indents5 + "</time>\n"
            + indents5 + "<location>\n"
            + indents10 + "<online>false</online>\n"
            + indents10 + "<place>Churchill Hall 101</place>\n"
            + indents5 + "</location>\n"
            + indents5 + "<users>\n"
            + indents10 + "<uid>Prof. Lucia</uid>\n"
            + indents10 + "<uid>Chat</uid>\n"
            + indents5 + "</users>\n"
            + "</event>";
    Assert.assertEquals(afternoonLecXML, this.afternoonLec.eventToXMLFormat());
  }
}
