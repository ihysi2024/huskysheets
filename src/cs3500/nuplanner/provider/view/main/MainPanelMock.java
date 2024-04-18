package cs3500.nuplanner.provider.view.main;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import cs3500.nuplanner.provider.controller.IFeatures;
import cs3500.nuplanner.provider.view.main.IMainPanel;
import cs3500.nuplanner.provider.view.main.MainPanel;

/**
 * Mock for testing the MainPanel that uses the controller features.
 */
public class MainPanelMock extends MainPanel implements IMainPanel {

  private IFeatures features;
  private StringBuilder log;

  /**
   * constructor that sets the StringBuilder log.
   */
  public MainPanelMock() {
    super();
    this.log = new StringBuilder();
  }

  @Override
  public void addFilePanelFeatures(IFeatures features) {
    this.features = features;
  }

  @Override
  public void addBottomPanelFeatures(IFeatures features) {
    this.features = features;
  }

  /**
   * simulates the press of the addCalendar button.
   */
  public void simulateAddCalendarButtonClicked(String filepath)
          throws IOException, ParserConfigurationException, SAXException {
    features.loadCalendarXML(filepath);
    log.append("Add calendar button clicked.\n");
  }

  /**
   * simulates the press of the saveCalendars button.
   */
  public void simulateSaveCalendarsButtonClicked(String filepath) {
    features.saveCalendarsXML(filepath);
    log.append("Save calendars button clicked.\n");
  }



  /**
   * gets the log as a string.
   * @return returns a string.
   */
  public String getLog() {
    return log.toString();
  }
}
