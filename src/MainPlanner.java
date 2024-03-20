import controller.Controller;
import model.NUPlanner;
import model.PlannerSystem;
import view.IScheduleView;
import view.ScheduleView;

public class MainPlanner {
  /**
   * Main method to play an instance of Simon Game.
   * @param args arguments to start an instance of Simon game
   */
  public static void main(String[] args) {
    PlannerSystem model = new NUPlanner(); // Feel free to customize this as desired
    IScheduleView view = new ScheduleView(model);
    Controller controller = new Controller(model, view);
    controller.goPlayGame();
  }

}
