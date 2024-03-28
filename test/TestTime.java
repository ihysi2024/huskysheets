import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.NUPlanner;
import model.PlannerSystem;
import model.ReadOnlyPlanner;
import model.Time;
import view.IScheduleTextView;
import view.ScheduleTextView;

/**
 * Class to test functionality of Time class.
 */
public class TestTime {
  private Time satOnePM;
  private Time tues10AM;
  private Time tues10PM;
  private Time thursNoon;
  private Time wedFiveThirtyPM;
  private Time wedFiveThirtyOnePM;
  private Time satOnePMTwo;
  private Time sunThreePM;
  private Time monFiveAM;
  private IScheduleTextView textV;
  private ReadOnlyPlanner model;

  @Before
  public void setUp() {
    PlannerSystem modelForTextView = new NUPlanner(model.getUsers());
    this.textV = new ScheduleTextView(modelForTextView, new StringBuilder());

    this.satOnePM = new Time(Time.Day.SATURDAY, 13, 0);
    this.tues10AM = new Time(Time.Day.TUESDAY, 10, 0);
    this.tues10PM = new Time(Time.Day.TUESDAY, 22, 0);
    this.thursNoon = new Time(Time.Day.THURSDAY, 12, 0);
    this.wedFiveThirtyPM = new Time(Time.Day.WEDNESDAY, 17, 30);
    this.wedFiveThirtyOnePM = new Time(Time.Day.WEDNESDAY, 17, 31);
    this.satOnePMTwo = new Time(Time.Day.SATURDAY, 13, 0);
    this.sunThreePM = new Time(Time.Day.SUNDAY, 15, 0);
    this.monFiveAM = new Time(Time.Day.MONDAY, 5, 0);
  }

  @Test
  public void testCompareTimes() {
    Assert.assertEquals(0, satOnePM.compareTimes(satOnePM));
    Assert.assertEquals(0, satOnePMTwo.compareTimes(satOnePM));
    Assert.assertEquals(-1, tues10AM.compareTimes(tues10PM));
    Assert.assertEquals(-1, tues10AM.compareTimes(thursNoon));
    Assert.assertEquals(1, wedFiveThirtyOnePM.compareTimes(wedFiveThirtyPM));
    Assert.assertEquals(1, monFiveAM.compareTimes(sunThreePM));
  }

  @Test
  public void testStringToTime() {
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.TUESDAY, 9, 50)),
            textV.timeToString(Time.stringToTime("Tuesday", "0950")));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.SUNDAY, 11, 49)),
            textV.timeToString(Time.stringToTime("Sunday", "1149")));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.FRIDAY, 0, 8)),
            textV.timeToString(Time.stringToTime("FRIDAY", "0008")));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.WEDNESDAY, 12, 48)),
            textV.timeToString(Time.stringToTime("wednesday", "1248")));
  }

  @Test
  public void testTimeToString() {
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.TUESDAY, 9, 50)),
            textV.timeToString(Time.stringToTime("tuesday", "0950")));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.MONDAY, 1, 12)),
            textV.timeToString(Time.stringToTime("MONDAY", "0112")));
    Assert.assertEquals(textV.timeToString(new Time(Time.Day.THURSDAY, 23, 59)),
           textV.timeToString( Time.stringToTime("thursday", "2359")));

    Assert.assertEquals("Monday: 05:00", textV.timeToString(this.monFiveAM));
    Assert.assertEquals("Saturday: 13:00", textV.timeToString(this.satOnePMTwo));
    Assert.assertEquals("Thursday: 12:00", textV.timeToString(this.thursNoon));
  }

  @Test
  public void testObservationalMethods() {
    Assert.assertEquals(13, this.satOnePM.getHours());
    Assert.assertEquals(0, this.satOnePM.getMinutes());
    Assert.assertEquals(Time.Day.SATURDAY, this.satOnePM.getDate());
  }

  @Test
  public void testTimeConstructor() {
    Assert.assertThrows(IllegalArgumentException.class,() ->
            new Time(Time.Day.SUNDAY, 25, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new Time(Time.Day.SUNDAY, 23, 70));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new Time(Time.Day.SUNDAY, -23, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new Time(Time.Day.SUNDAY, 23, -10));
  }
}
