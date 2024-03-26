import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Event;
import model.IEvent;
import model.NUPlanner;
import model.PlannerSystem;
import model.Schedule;
import model.Time;
import model.User;
import view.EventView;
import view.IEventView;
import view.IScheduleTextView;
import view.IScheduleView;
import view.ScheduleView;

public class MainPlanner {
  /**
   * Main method to play an instance of Simon Game.
   * @param args arguments to start an instance of Simon game
   */
  public static void main(String[] args) {
    PlannerSystem model = new NUPlanner(); // Feel free to customize this as desired
    Event morningSnack = new Event("snack",
            new Time(Time.Day.TUESDAY, 9, 30),
            new Time(Time.Day.TUESDAY, 11, 45),
            false,
            "Churchill Hall 101",
            List.of("Prof. Lucia"));

    Event officeHours = new Event("office hours",
            new Time(Time.Day.MONDAY, 12, 01),
            new Time(Time.Day.MONDAY, 15, 30),
            false,
            "Churchill Hall 101",
            List.of("Prof. Lucia"));

    model.addUser(new User("Prof. Lucia",
            new Schedule(new ArrayList<>(List.of(morningSnack, officeHours)))));
    model.addUser(new User("Me", new Schedule(new ArrayList<>())));

    //  IScheduleView view = new ScheduleView(model);
    IScheduleView schedView = new ScheduleView(model); // (michelle) currently working on event view so changed this here + in controller.
    IEventView eView = new EventView(model);
    Controller controller = new Controller(model);

    controller.setScheduleView(schedView);
    controller.setEventView(eView);
    controller.goPlayGame();
  }

}
