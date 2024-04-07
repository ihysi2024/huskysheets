import java.util.List;

import controller.ViewFeatures;
import model.ITime;
import model.IUser;
import model.PlannerSystem;
import view.IScheduleTextView;
import view.IScheduleView;
import view.ScheduleTextView;

public class MockScheduleView implements IScheduleView {
  private IScheduleTextView view;
  private StringBuilder out;
  private PlannerSystem model;

  public MockScheduleView(StringBuilder out, PlannerSystem model) {
    this.out = out;
    this.model = model;
    this.view = new ScheduleTextView(model, out);
  }

  @Override
  public void openScheduleView() {
    out.delete(0, out.length());
    out.append("Opening schedule panel view");
  }

  @Override
  public void closeScheduleView() {
    out.delete(0, out.length());
    out.append("Closing schedule panel view");
  }

  @Override
  public void addFeatures(ViewFeatures features) {

  }

  @Override
  public String getEventNameInput() {
    return null;
  }

  @Override
  public String getLocationInput() {
    return null;
  }

  @Override
  public List<String> getUsersInput() {
    return null;
  }

  @Override
  public boolean getOnline() {
    return false;
  }

  @Override
  public void addScheduleAtTime(IUser user, ITime startTime, ITime endTime) {
    out.delete(0, out.length());
    out.append("Adding the scheduled event to this schedule: " + view.userToString(user));
    out.append("\n");
    out.append("And from these times: "
            + view.timeToString(startTime)
            + " - " + view.timeToString(endTime));
  }

  @Override
  public int getDuration() {
    return 90;
  }
}
