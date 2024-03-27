import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import model.Event;
import model.IEvent;
import model.ISchedule;
import model.IUser;
import model.NUPlanner;
import model.PlannerSystem;
import model.Schedule;
import model.Time;
import model.User;
import view.ScheduleTextView;
import view.IScheduleTextView;


/**
 * Class to test functionality of TextView.
 */
public class TestTextView {
  private IEvent morningLec;
  private IEvent afternoonLec;
  private IEvent sleep;
  private ISchedule emptySchedule;
  private PlannerSystem plannerSystem;
  private IScheduleTextView view;


  @Before
  public void setUp() {

    this.emptySchedule = new Schedule(new ArrayList<>());

    this.morningLec = new Event("CS3500 Morning Lecture",
            new Time( Time.Day.TUESDAY, 9, 50),
            new Time(Time.Day.TUESDAY, 00, 01),
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

    User profLuciaUser = new User("Prof. Lucia", luciaSchedule);
    User studentAnonUser = new User("Student Anon", studentAnonSchedule);
    User chatUser = new User("Chat", chatSchedule);

    Set<IUser> users = new LinkedHashSet<>();
    users.add(profLuciaUser);
    users.add(studentAnonUser);
    users.add(chatUser);
    this.plannerSystem = new NUPlanner(users);
    this.view = new ScheduleTextView(this.plannerSystem, new StringBuilder());
  }

  /**
   * Tests if the view can correctly convert the planner system to a text view.
   * @throws IOException if the schedule cannot be converted correctly
   */
  @Test
  public void testPlannerSystemToString() throws IOException {
    emptySchedule.addEvent(this.morningLec);
    emptySchedule.addEvent(this.afternoonLec);
    emptySchedule.addEvent(this.sleep);

    String planner = "User: Prof. Lucia\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
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
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "name: Sleep\n" +
            "time: Friday: 18:00->Sunday: 12:00\n" +
            "location: Home\n" +
            "online: true\n" +
            "users: Prof. Lucia          \n" +
            "Saturday: \n" +
            "\n" +
            "User: Student Anon\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "name: CS3500 Morning Lecture\n" +
            "time: Tuesday: 09:50->Tuesday: 00:01\n" +
            "location: Churchill Hall 101\n" +
            "online: false\n" +
            "users: Prof. Lucia\n" +
            "Student Anon\n" +
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "\n" +
            "User: Chat\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
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
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "\n";
    Assert.assertEquals(planner, view.plannerSystemString());
  }

  /**
   * Test that the view can correctly output the model's text.
   * @throws IOException if the output cannot be rendered
   */
  @Test
  public void testRender() throws IOException {
    Appendable ap = new StringBuilder();
    IScheduleTextView view2 = new ScheduleTextView(plannerSystem, ap);
    view2.renderPlanner();
    StringBuilder rendered = new StringBuilder("User: Prof. Lucia\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
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
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "name: Sleep\n" +
            "time: Friday: 18:00->Sunday: 12:00\n" +
            "location: Home\n" +
            "online: true\n" +
            "users: Prof. Lucia          \n" +
            "Saturday: \n\n" +
            "User: Chat\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
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
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n\n" +
            "User: Student Anon\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "name: CS3500 Morning Lecture\n" +
            "time: Tuesday: 09:50->Tuesday: 00:01\n" +
            "location: Churchill Hall 101\n" +
            "online: false\n" +
            "users: Prof. Lucia\n" +
            "Student Anon\n" +
            "Chat          \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "\n");

    Assert.assertEquals(rendered.toString(), ap.toString());
    //Assert.assertEquals("test", new StringBuilder("test").toString());
  }

}