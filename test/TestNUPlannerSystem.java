import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import model.Event;
import model.IEvent;
import model.IUser;
import model.NUPlanner;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Schedule;
import model.Time;
import model.User;
import view.IScheduleTextView;
import view.ScheduleTextView;

import static controller.UtilsXML.readXML;
import static model.User.interpretXML;

/**
 * Class to test functionality of NUPlannerSystem.
 */
public class TestNUPlannerSystem {
  private IEvent morningLec;
  private IEvent morningSnack;
  private IEvent afternoonLec;
  private IEvent officeHours;
  private IEvent sleep;
  private IUser profLuciaUser;
  private IUser studentAnonUser;
  private IUser chatUser;
  private PlannerSystem plannerSystem;
  private IScheduleTextView textV;
  private ReadOnlyPlanner model;


  @Before
  public void setUp() {

    PlannerSystem modelForTextView = new NUPlanner(model.getUsers());
    this.textV = new ScheduleTextView(modelForTextView, new StringBuilder());

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
    Schedule luciaSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec, afternoonLec,
            sleep)));
    Schedule studentAnonSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec)));
    Schedule chatSchedule = new Schedule(new ArrayList<>(Arrays.asList(morningLec, afternoonLec)));
    this.profLuciaUser = new User("Prof. Lucia", luciaSchedule);
    this.studentAnonUser = new User("Student Anon", studentAnonSchedule);
    this.chatUser = new User("Chat", chatSchedule);
    this.morningSnack = new Event("snack",
            new Time(Time.Day.TUESDAY, 8, 30),
            new Time(Time.Day.TUESDAY, 8, 45),
            false,
            "Churchill Hall 101",
            List.of("Student Anon"));
    this.officeHours = new Event("office hours",
            new Time(Time.Day.THURSDAY, 15, 15),
            new Time(Time.Day.THURSDAY, 16, 30),
            false,
            "Churchill Hall 101",
            List.of("Student Anon",
                    "Prof. Lucia"));
    Event movie = new Event("movie",
            new Time(Time.Day.FRIDAY, 21, 15),
            new Time(Time.Day.FRIDAY, 23, 30),
            true,
            "home",
            List.of("Student Anon"));
    Set<IUser> users = new LinkedHashSet<>();
    users.add(this.profLuciaUser);
    users.add(this.studentAnonUser);
    users.add(this.chatUser);
    this.plannerSystem = new NUPlanner(users);
  }

  /**
   * Test the observational methods - getUsers().
   */
  @Test
  public void testObservationalMethods() {
    LinkedHashSet<IUser> usersCompare = new LinkedHashSet<>();
    usersCompare.add(this.profLuciaUser);
    usersCompare.add(this.studentAnonUser);
    usersCompare.add(this.chatUser);
    Assert.assertEquals(3, this.plannerSystem.getUsers().size());
    Assert.assertEquals(usersCompare, this.plannerSystem.getUsers());
  }

  /**
   * Test that a schedule can be correctly exported as an XML.
   */
  @Test
  public void testExportScheduleAsXML() {
    plannerSystem.exportScheduleAsXML("src/controller/");
    Document xmlDoc1 = readXML("src/controller/Prof. Lucia_schedule.xml");
    List<IEvent> luciaEvents = interpretXML(xmlDoc1);
    // ensure the correct events are included in the right order and nothing else
    Assert.assertEquals(textV.eventToString(this.morningLec),
            textV.eventToString(luciaEvents.get(0)));
    Assert.assertEquals(textV.eventToString(this.afternoonLec),
            textV.eventToString(luciaEvents.get(1)));
    Assert.assertEquals(textV.eventToString(this.sleep),
            textV.eventToString(luciaEvents.get(2)));
    Assert.assertEquals(3, luciaEvents.size());
    Document xmlDoc2 = readXML("src/controller/Chat_schedule.xml");
    List<IEvent> chatEvents = interpretXML(xmlDoc2);
    // ensure the correct events are included in the right order and nothing else
    Assert.assertEquals(textV.eventToString(this.morningLec),
            textV.eventToString(chatEvents.get(0)));
    Assert.assertEquals(textV.eventToString(this.afternoonLec),
            textV.eventToString(chatEvents.get(1)));
    Assert.assertEquals(2, chatEvents.size());
    Document xmlDoc3 = readXML("src/controller/Student Anon_schedule.xml");
    List<IEvent> studentAnonEvents =
            interpretXML(xmlDoc3);
    // ensure the correct events are included in the right order and nothing else
    Assert.assertEquals(textV.eventToString(this.morningLec),
            textV.eventToString(chatEvents.get(0)));
    Assert.assertEquals(textV.eventToString(this.afternoonLec),
            textV.eventToString(chatEvents.get(1)));
    Assert.assertEquals(2, chatEvents.size());
  }

  /**
   * Test that a user can retrieve a set of events occurring at a certain time correctly.
   */
  @Test
  public void testRetrieveUserScheduleAtTime() {
    // event starting at the given time
    Assert.assertEquals(List.of(this.afternoonLec),
            this.plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.TUESDAY, 13, 35)));
    // event starting before the given time but ending after
    Assert.assertEquals(List.of(this.afternoonLec),
            this.plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.TUESDAY, 14, 15)));
    // no event occurring at the time
    Assert.assertEquals(List.of(),
            this.plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.THURSDAY, 14, 15)));
    plannerSystem.addEventForRelevantUsers(this.officeHours);
    // an event starting at that time and ending at that time
    Assert.assertEquals(List.of(this.afternoonLec),
            this.plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.TUESDAY, 15, 15)));
  }

  @Test
  public void testAddEvent() {
    Event nap = new Event("nap",
            new Time(Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 14, 30 ),
            false,
            "Churchill Hall 101",
            List.of("Prof. Lucia", "Chat"));
    plannerSystem.addEventForRelevantUsers(this.officeHours);
    Assert.assertTrue(this.profLuciaUser.getSchedule().getEvents().contains(this.officeHours));
    Assert.assertTrue(this.studentAnonUser.getSchedule().getEvents().contains(this.officeHours));
    Assert.assertFalse(this.chatUser.getSchedule().getEvents().contains(this.officeHours));
    // add another event
    plannerSystem.addEventForRelevantUsers(this.morningSnack);
    Assert.assertFalse(this.profLuciaUser.getSchedule().getEvents().contains(this.morningSnack));
    Assert.assertTrue(this.studentAnonUser.getSchedule().getEvents().contains(this.morningSnack));
    Assert.assertFalse(this.chatUser.getSchedule().getEvents().contains(this.morningSnack));
    // add an event that coincides with an existing event
    plannerSystem.addEventForRelevantUsers(nap);
    Assert.assertFalse(this.profLuciaUser.getSchedule().getEvents().contains(nap));
    Assert.assertFalse(this.chatUser.getSchedule().getEvents().contains(nap));
    Assert.assertTrue(this.profLuciaUser.getSchedule().getEvents().contains(this.afternoonLec));
    Assert.assertTrue(this.chatUser.getSchedule().getEvents().contains(this.afternoonLec));
  }

  /**
   * Test to determine if a user can modify events correctly
   * through the planner system.
   */
  @Test
  public void testModifyEvent() {
    Event newOfficeHours = new Event("office hours",
            new Time(Time.Day.WEDNESDAY, 15, 15),
            new Time(Time.Day.WEDNESDAY, 16, 30),
            false,
            "Churchill Hall 101",
            List.of("Student Anon",
                    "Prof. Lucia"));
    Event newMorningLec = new Event("morning lecture",
            new Time(Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 10, 50),
            false,
            "Churchill Hall 101",
            List.of("Prof. Lucia", "Chat"));
    Event tryAgainMorning = new Event("another morning lecture",
            new Time(Time.Day.TUESDAY, 10, 00),
            new Time(Time.Day.TUESDAY, 10, 50),
            false,
            "Churchill Hall 101",
            List.of("Prof. Lucia", "Chat"));
    Event nap = new Event("nap",
            new Time(Time.Day.TUESDAY, 13, 35),
            new Time(Time.Day.TUESDAY, 14, 30 ),
            false,
            "Churchill Hall 101",
            List.of("Prof. Lucia", "Chat"));
    Event run = new Event("go for run",
            new Time(Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 10, 50),
            false,
            "centennial",
            List.of("Chat"));
    // change the time of a current event where it doesn't coincide with any other events
    plannerSystem.modifyEvent(this.officeHours, newOfficeHours);
    Assert.assertEquals(List.of(newOfficeHours),
            plannerSystem.retrieveUserScheduleAtTime(studentAnonUser,
                    new Time(Time.Day.WEDNESDAY, 15, 15)));
    Assert.assertEquals(List.of(newOfficeHours),
            plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.WEDNESDAY, 15, 15)));
    // change users invited to the event, name and time of event
    plannerSystem.modifyEvent(this.morningLec, newMorningLec);
    Assert.assertEquals(List.of(),
            plannerSystem.retrieveUserScheduleAtTime(studentAnonUser,
                    new Time(Time.Day.TUESDAY, 9, 50)));

    Assert.assertEquals(List.of(newMorningLec),
            plannerSystem.retrieveUserScheduleAtTime(chatUser,
                    new Time(Time.Day.TUESDAY, 9, 50)));
    Assert.assertEquals(List.of(newMorningLec),
            plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.TUESDAY, 9, 50)));
    // users have removed the old event and have the new event
    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(this.officeHours));
    Assert.assertFalse(studentAnonUser.getSchedule().getEvents().contains(this.officeHours));
    Assert.assertFalse(studentAnonUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(chatUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(this.morningLec));
    // Try to modify the same event that we just changed - doesn't exist, throws an exception
    plannerSystem.modifyEvent(this.morningLec, tryAgainMorning);
    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(chatUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(tryAgainMorning));
    Assert.assertFalse(chatUser.getSchedule().getEvents().contains(tryAgainMorning));

    // try to modify an event with another event occurring at the same time as an existing event
    plannerSystem.modifyEvent(this.morningLec, nap);
    Assert.assertEquals(List.of(this.afternoonLec),
            plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.TUESDAY, 13, 35)));
    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(nap));
    Assert.assertFalse(chatUser.getSchedule().getEvents().contains(nap));
    Assert.assertTrue(profLuciaUser.getSchedule().getEvents().contains(this.afternoonLec));
    Assert.assertTrue(chatUser.getSchedule().getEvents().contains(this.afternoonLec));
    // try to modify an event with an event that has a different host
    // events should remain unchanged
    plannerSystem.modifyEvent(newMorningLec, run);

    Assert.assertEquals(List.of(newMorningLec),
            plannerSystem.retrieveUserScheduleAtTime(profLuciaUser,
                    new Time(Time.Day.TUESDAY, 9, 50)));
    Assert.assertEquals(List.of(newMorningLec),
            plannerSystem.retrieveUserScheduleAtTime(chatUser,
                    new Time(Time.Day.TUESDAY, 9, 50)));

    Assert.assertFalse(profLuciaUser.getSchedule().getEvents().contains(run));
    Assert.assertFalse(chatUser.getSchedule().getEvents().contains(run));
    Assert.assertTrue(profLuciaUser.getSchedule().getEvents().contains(newMorningLec));
    Assert.assertTrue(chatUser.getSchedule().getEvents().contains(newMorningLec));

  }

  /**
   * Test that a user can correctly remove an event through the planner system.
   */
  @Test
  public void testRemoveEvent() {
    // host removing an event - removed for everyone
    plannerSystem.removeEventForRelevantUsers(this.morningLec, profLuciaUser);
    Assert.assertFalse(this.profLuciaUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(this.studentAnonUser.getSchedule().getEvents().contains(this.morningLec));
    Assert.assertFalse(this.chatUser.getSchedule().getEvents().contains(this.morningLec));
    plannerSystem.removeEventForRelevantUsers(this.afternoonLec, profLuciaUser);
    Assert.assertFalse(this.profLuciaUser.getSchedule().getEvents().contains(this.afternoonLec));
    Assert.assertFalse(this.studentAnonUser.getSchedule().getEvents().contains(this.afternoonLec));
    Assert.assertFalse(this.chatUser.getSchedule().getEvents().contains(this.afternoonLec));
    plannerSystem.addEventForRelevantUsers(this.afternoonLec);
    // invitee removing an event - only removed for them
    plannerSystem.removeEventForRelevantUsers(this.afternoonLec, this.chatUser);
    Assert.assertTrue(this.profLuciaUser.getSchedule().getEvents().contains(this.afternoonLec));
    Assert.assertFalse(this.studentAnonUser.getSchedule().getEvents().contains(this.afternoonLec));
    Assert.assertFalse(this.chatUser.getSchedule().getEvents().contains(this.afternoonLec));
    // trying to remove an event that doesn't exist yet
    Assert.assertThrows(IllegalArgumentException.class, () ->
            plannerSystem.removeEventForRelevantUsers(this.officeHours, profLuciaUser));

  }
}