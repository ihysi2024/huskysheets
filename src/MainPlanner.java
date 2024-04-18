import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.Controller;
import controller.FeaturesAdapter;
import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.model.ICentralSystem;
import cs3500.nuplanner.provider.view.event.EventFrame;
import cs3500.nuplanner.provider.view.event.EventFrameAdapter;
import cs3500.nuplanner.provider.view.event.IEventFrame;
import cs3500.nuplanner.provider.view.main.GridPanel;
import cs3500.nuplanner.provider.view.main.IGridPanel;
import cs3500.nuplanner.provider.view.main.IMainFrame;
import cs3500.nuplanner.provider.view.main.MainFrame;
import cs3500.nuplanner.provider.view.main.MainFrameAdapter;
import cs3500.nuplanner.provider.view.schedule.IScheduleFrame;
import cs3500.nuplanner.provider.view.schedule.ScheduleFrame;
import cs3500.nuplanner.provider.view.schedule.ScheduleFrameAdapter;
import model.Event;
import model.IEvent;
import model.ISchedule;
import model.NUPlanner;
import model.NUPlannerAdapter;
import model.PlannerSystem;
import model.ReadOnlyNUPlannerAdapter;
import model.Schedule;
import model.Time;
import model.User;
import view.EventView;
import view.IEventView;
import strategies.IScheduleStrategy;
import view.IScheduleTextView;
import view.IPlannerView;
import view.IScheduleView;
import view.ScheduleTextView;
import view.PlannerView;
import view.ScheduleView;
import strategies.scheduleAnyTime;
import strategies.scheduleWorkHours;

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
    Controller controller;
    IPlannerView plannerView;
    IEventView eView;
    IScheduleView schedView;

    IMainFrame providerMainView;
    IGridPanel providerGridView;
    IEventFrame providerEventView;
    IScheduleFrame providerSchedView;


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

    PlannerSystem model = new NUPlanner("None"); // Feel free to customize this as desired
    model.addUser(new User("Prof. Lucia",
            new Schedule(new ArrayList<>(List.of(morningSnack, officeHours, sleep)))));
    model.addUser(new User("Me", new Schedule(new ArrayList<>(List.of(officeHours)))));


    // using provider view + my model adapters. using my controller
    // my controller needs to set the planner view, eventview, and scheduleview - provider has equivalents of these?

    // my controller takes in a model: needs to be the adapted version of provider model (Read Only NU Planner Adapter)
    if (args.length > 1 && args[1].equals("providerView")) {
      ICentralSystem providerModel;
      if (args[0].equals("anytime")) {
        IScheduleStrategy anyTime = new scheduleAnyTime();
      //  providerModel = new NUPlannerAdapter(model);
        controller = new Controller(model, anyTime);
      } else if (args[0].equals("workhours")) {
        IScheduleStrategy workHours = new scheduleWorkHours();
        controller = new Controller(model, workHours);

      } else {
        throw new IllegalArgumentException("Not a valid scheduling strategy");
      }
      providerModel = new NUPlannerAdapter(model);
      providerEventView = new EventFrame(providerModel);
      providerMainView = new MainFrame(providerModel);
      providerGridView = new GridPanel(providerModel, providerEventView);
      providerSchedView = new ScheduleFrame(providerModel);


      IEventView eViewAdapted = new EventFrameAdapter(providerEventView);
      IPlannerView plannerViewAdapted = new MainFrameAdapter(providerMainView, providerGridView);
      IScheduleView schedViewAdapted = new ScheduleFrameAdapter(providerSchedView);

      controller.setPlannerView(plannerViewAdapted);
      controller.setEventView(eViewAdapted);
      controller.setScheduleView(schedViewAdapted);
      IFeatures featuresAdapted = new FeaturesAdapter(controller);

      featuresAdapted.launch(providerModel);
    //  plannerView = new PlannerView(model);
     // eView = new EventFrameAdapter(providerModel);
    //  schedView = new ScheduleView(model);
     // controller.goLaunchPlanner();

    }

    else {
      if (args[0].equals("anytime")) {
        IScheduleStrategy anyTime = new scheduleAnyTime();
        controller = new Controller(model, anyTime);
      } else if (args[0].equals("workhours")) {
        IScheduleStrategy workHours = new scheduleWorkHours();
        controller = new Controller(model, workHours);
      } else {
        throw new IllegalArgumentException("Not a valid scheduling strategy");
      }
      plannerView = new PlannerView(model);
      eView = new EventView(model);
      schedView = new ScheduleView(model);

      controller.setPlannerView(plannerView);
      controller.setEventView(eView);
      controller.setScheduleView(schedView);
      controller.goLaunchPlanner();
    }


  }



}
