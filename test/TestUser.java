import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import model.Event;
import model.NUPlanner;
import model.Schedule;
import model.Time;
import model.User;

import static controller.UtilsXML.readXML;
import static model.User.interpretXML;
import static model.User.makeEvent;

/**
 * Class to test functionality of User class.
 */
public class TestUser {
  private Event morningLec;
  private Event morningSnack;
  private Event afternoonLec;
  private Event officeHours;
  private Event sleep;
  private Schedule luciaSchedule;
  private Schedule studentAnonSchedule;
  private User profLuciaUser;
  private User studentAnonUser;
  private User chatUser;

  @Before
  public void setUp() {

    Schedule emptySchedule = new Schedule(new ArrayList<>());

    this.profLuciaUser = new User("Prof Lucia", emptySchedule);
    this.studentAnonUser = new User("Student Anon", emptySchedule);
    this.chatUser = new User("Chat", emptySchedule);

    this.morningLec = new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 00, 01),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));


    Event newMorningLec = new Event("CS3500 Morning Lecture",
            new Time(Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 16, 00),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));


    // same time as the morning lecture
    Event morningLecSameTime = new Event("same time morning lecture",
            new Time(Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList(
                    "Student Anon")));

    // overlapping time as the morning lecture
    Event morningLecOverlapping = new Event("overlapping morning lecture ",
            new Time(Time.Day.TUESDAY, 8, 30),
            new Time(Time.Day.TUESDAY, 10, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // start time same as end time of morning lecture
    Event morningLecEndTime = new Event("same start time as end time",
            new Time(Time.Day.TUESDAY, 11, 30),
            new Time(Time.Day.TUESDAY, 12, 15),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // same time as the morning lecture
    morningLecSameTime = new Event("same time morning lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 11, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // overlapping time as the morning lecture
    morningLecOverlapping = new Event("overlapping morning lecture ",
            new Time( Time.Day.TUESDAY, 8, 30),
            new Time(Time.Day.TUESDAY, 10, 30),
            false,
            "Churchill Hall 101",
            new ArrayList<>(Arrays.asList("Prof. Lucia",
                    "Student Anon",
                    "Chat")));

    // start time same as end time of morning lecture
    morningLecEndTime = new Event("same start time as end time",
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

    this.luciaSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec, afternoonLec,
            sleep)));
    this.studentAnonSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec)));
    Schedule chatSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec, afternoonLec)));

    this.profLuciaUser = new User("Prof. Lucia", luciaSchedule);
    this.studentAnonUser = new User("Student Anon", studentAnonSchedule);
    this.chatUser = new User("Chat", chatSchedule);

    this.morningSnack = new Event("snack",
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
    users.add(this.profLuciaUser);
    users.add(this.studentAnonUser);
    users.add(this.chatUser);
    NUPlanner plannerSystem = new NUPlanner(users);
  }

  /**
   * Test observational methods in User class.
   */

  @Test
  public void testObservationalMethods() {
    // Test observational method for username
    Assert.assertEquals("Prof. Lucia", this.profLuciaUser.getName());
    Assert.assertEquals("Student Anon", this.studentAnonUser.getName());
    // test observational method for user schedule
    Assert.assertEquals(this.luciaSchedule, this.profLuciaUser.getSchedule());
    Assert.assertEquals(this.studentAnonSchedule, this.studentAnonUser.getSchedule());

  }

  @Test
  public void testUserToString() {
    // what the user schedule should look like
    String userLine = "User: Prof. Lucia";
    String sundayLine = "Sunday: \n";
    String mondayLine = "Monday: \n";
    String tuesdayLine = "Tuesday: \n" +
            "name: CS3500 Morning Lecture\n" +
            "time: Tuesday: 09:50->Tuesday: 00:01\n" +
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
            "Chat          \n";
    String wednesdayLine = "Wednesday: \n";
    String thursdayLine = "Thursday: \n";
    String fridayLine = "Friday: \n" +
            "name: Sleep\n" +
            "time: Friday: 18:00->Sunday: 12:00\n" +
            "location: Home\n" +
            "online: true\n" +
            "users: Prof. Lucia          \n";
    String saturdayLine = "Saturday: \n";

    // grabbing different parts of the schedule from the userToString method
    String sundaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Sunday"),
                    this.profLuciaUser.userToString().indexOf("Monday"));
    String mondaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Monday"),
                    this.profLuciaUser.userToString().indexOf("Tuesday"));
    String tuesdaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Tuesday"),
                    this.profLuciaUser.userToString().indexOf("Wednesday"));
    String wednesdaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Wednesday"),
                    this.profLuciaUser.userToString().indexOf("Thursday"));
    String thursdaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Thursday"),
                    this.profLuciaUser.userToString().indexOf("Friday"));
    String fridaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Friday"),
                    this.profLuciaUser.userToString().indexOf("Saturday"));
    String saturdaySubstring = this.profLuciaUser.userToString()
            .substring(this.profLuciaUser.userToString().indexOf("Saturday"));
    // check if each day lists the correct events
    Assert.assertEquals(sundayLine, sundaySubstring);
    Assert.assertEquals(mondayLine, mondaySubstring);
    Assert.assertEquals(tuesdayLine, tuesdaySubstring);
    Assert.assertEquals(wednesdayLine, wednesdaySubstring);
    Assert.assertEquals(thursdayLine, thursdaySubstring);
    Assert.assertEquals(fridayLine, fridaySubstring);
    Assert.assertEquals(saturdayLine, saturdaySubstring);
  }

  /**
   * Test that the user's schedule is correctly exported into an XML via
   * the userSchedToXML(String filePath) method AND that it can be
   * correctly interpreted via the interpretXML(String filePath) method.
   */

  @Test
  public void testSchedToXML() {
    // write a schedule to a file for Prof.Lucia
    this.profLuciaUser.userSchedToXML("src/controller/");
    // try grabbing the events
    Document xmlDoc1 = readXML("src/controller/Prof. Lucia_schedule.xml");
    List<Event> luciaEvents = interpretXML(xmlDoc1);
    // ensure the correct events are included in the right order and nothing else
    Assert.assertEquals(this.morningLec.eventToString(), luciaEvents.get(0).eventToString());
    Assert.assertEquals(this.afternoonLec.eventToString(), luciaEvents.get(1).eventToString());
    Assert.assertEquals(this.sleep.eventToString(), luciaEvents.get(2).eventToString());
    Assert.assertEquals(3, luciaEvents.size());

    // write a schedule to a file for Chat
    this.chatUser.userSchedToXML("src/controller/");
    Document xmlDoc2 = readXML("src/controller/Chat_schedule.xml");
    // try grabbing the events
    List<Event> chatEvents = interpretXML(xmlDoc2);
    // ensure the correct events are included in the right order and nothing else
    Assert.assertEquals(this.morningLec.eventToString(), chatEvents.get(0).eventToString());
    Assert.assertEquals(this.afternoonLec.eventToString(), chatEvents.get(1).eventToString());
    Assert.assertEquals(2, chatEvents.size());
  }


  /**
   * Test if an event can be correctly converted from a hashmap of strings.
   */

  @Test
  public void testMakeEvent() {
    // morning lecture
    HashMap<String, String[]> morningLecMap = new HashMap<>();
    morningLecMap.put("name", new String[]{"CS3500 Morning Lecture"});
    morningLecMap.put("time", new String[]{"Tuesday", "0950", "Tuesday", "0001"});
    morningLecMap.put("location", new String[]{"false", "Churchill Hall 101"});
    morningLecMap.put("users", new String[]{"Prof. Lucia", "Student Anon", "Chat"});

    Assert.assertEquals(this.morningLec, makeEvent(morningLecMap));

    // afternoon lecture
    HashMap<String, String[]> sleepMap = new HashMap<>();
    sleepMap.put("name", new String[]{"Sleep"});
    sleepMap.put("time", new String[]{"Friday", "1800", "Sunday", "1200"});
    sleepMap.put("location", new String[]{"true", "Home"});
    sleepMap.put("users", new String[]{"Prof. Lucia"});

    Assert.assertEquals(this.sleep, makeEvent(sleepMap));
  }

  /**
   * Test adding and removing an event to a user's schedule.
   */
  @Test
  public void testAddRemoveEvent() {
    // adding an event
    Assert.assertEquals(3, this.profLuciaUser.getSchedule().getEvents().size());
    this.profLuciaUser.addEventForUser(this.morningSnack);
    Assert.assertEquals(4, this.profLuciaUser.getSchedule().getEvents().size());
    this.profLuciaUser.addEventForUser(this.officeHours);
    Assert.assertEquals(5, this.profLuciaUser.getSchedule().getEvents().size());

    // removing an event
    this.profLuciaUser.removeEventForUser(this.officeHours);
    Assert.assertEquals(4, this.profLuciaUser.getSchedule().getEvents().size());
    this.profLuciaUser.removeEventForUser(this.morningSnack);
    Assert.assertEquals(3, this.profLuciaUser.getSchedule().getEvents().size());
  }

}
