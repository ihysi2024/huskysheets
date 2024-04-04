import java.util.ArrayList;
import java.util.Arrays;
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
import view.ScheduleTextView;
import view.PlannerView;

/**
 * Represents the class that allows the user to run the calendar system end to end
 * through the graphical user interface.
 */
public class MainPlanner {
  /**
   * Main method to play an instance of Simon Game.
   * @param args arguments to start an instance of Simon game
   */
  public static void main(String[] args) {
    PlannerSystem model = new NUPlanner(); // Feel free to customize this as desired
    IEvent morningSnack = new Event("snack",
            new Time(Time.Day.TUESDAY, 10, 30),
            new Time(Time.Day.TUESDAY, 11, 45),
            false,
            "Churchill Hall 101",
            Arrays.asList("Prof. Lucia"));

    IEvent officeHours = new Event("office hours",
            new Time(Time.Day.MONDAY, 12, 10),
            new Time(Time.Day.MONDAY, 15, 30),
            false,
            "Churchill Hall 101",
            Arrays.asList("Prof. Lucia", "Me"));

    IEvent sleep = new Event("sleep",
            new Time(Time.Day.FRIDAY, 12, 10),
            new Time(Time.Day.SUNDAY, 15, 30),
            false,
            "home",
            Arrays.asList("Prof. Lucia"));

    model.addUser(new User("Prof. Lucia",
            new Schedule(new ArrayList<>(List.of(morningSnack, officeHours, sleep)))));
    model.addUser(new User("Me", new Schedule(new ArrayList<>(List.of(officeHours)))));

    IScheduleView schedView = new PlannerView(model);
    IEventView eView = new EventView(model);
    IScheduleTextView tView = new ScheduleTextView(model, new StringBuilder());
    Controller controller = new Controller(model);

    controller.setScheduleView(schedView);
    controller.setEventView(eView);
    controller.setTextView(tView);
    controller.goLaunchPlanner();
  }

}
