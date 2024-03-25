import controller.Controller;
import model.IEvent;
import model.NUPlanner;
import model.PlannerSystem;
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
  //  IScheduleView view = new ScheduleView(model);
    IScheduleView schedView = new ScheduleView(model); // (michelle) currently working on event view so changed this here + in controller.
    IEventView eView = new EventView(model);
    Controller controller = new Controller(model);
    controller.setScheduleView(schedView);
    controller.setEventView(eView);
    controller.goPlayGame();
  }

}
