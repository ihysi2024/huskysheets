package view.main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.IFeatures;
import model.CentralSystemMock;
import model.ICentralSystem;
import model.IReadOnlyCentralSystem;
import view.event.EventFrame;
import view.event.IEventFrame;
import view.main.panel.MainPanel;
import view.main.panel.MainPanelMock;
import view.event.panel.EventPanelMock;
import view.schedule.IScheduleFrame;
import view.schedule.ScheduleFrame;
import view.schedule.SchedulePanelMock;


/**
 * Represents the MainFrame class that creates the main schedule view.
 */
public class MainFrame extends JFrame implements IMainFrame {

  private IReadOnlyCentralSystem model;
  private IEventFrame eventFrame;
  private IScheduleFrame scheduleFrame;
  private MainPanel mainPanel;

  /**.
   * Constructor for MainFrame that takes in a ReadOnlyCentralSystem
   * @param model a ReadOnlyCentralSystem model.
   */
  public MainFrame(ICentralSystem model) {
    super();
    setSize(800, 800);

    this.model = model;
    eventFrame = new EventFrame(model);
    scheduleFrame = new ScheduleFrame(model);
    mainPanel = new MainPanel(model, eventFrame, scheduleFrame);
    this.add(mainPanel);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Constructor for testing with a mock MainPanel.
   * @param model read-only model.
   * @param mainPanel mock MainPanel instance.
   */
  public MainFrame(CentralSystemMock model, MainPanelMock mainPanel,
                   EventPanelMock eventPanel, SchedulePanelMock schedulePanel) {
    super();
    this.model = model;
    this.mainPanel = mainPanel;
    this.add(mainPanel);
    scheduleFrame = new ScheduleFrame(model, schedulePanel);
    eventFrame = new EventFrame(model, eventPanel);
  }


  /**
   * constructor for testing with an EventPanelMock.
   * @param model Read-only model
   * @param eventPanel mock Event Panel instance.
   */
  public MainFrame(ICentralSystem model, EventPanelMock eventPanel) {
    super();
    this.model = model;
    scheduleFrame = new ScheduleFrame(model);
    eventFrame = new EventFrame(model, eventPanel);
    this.mainPanel = new MainPanel(model, eventFrame, scheduleFrame);
  }

  /**
   * constructor for testing with a SchedulePanelMock.
   * @param model read-only model instance.
   * @param schedulePanel mock SchedulePanel instance
   */
  public MainFrame(ICentralSystem model, SchedulePanelMock schedulePanel) {
    super();
    this.model = model;
    scheduleFrame = new ScheduleFrame(model, schedulePanel);
    eventFrame = new EventFrame(model);
    this.mainPanel = new MainPanel(model, eventFrame, scheduleFrame);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setVisible() {
    setVisible(true);
  }

  @Override
  public void addFeatures(IFeatures features) {
    mainPanel.addFilePanelFeatures(features);
    mainPanel.addGridPanelFeatures(features);
    mainPanel.addBottomPanelFeatures(features);

    //for mock testing
    scheduleFrame.addFeatures(features);
    eventFrame.addFeatures(features);
  }

  @Override
  public void emptyInputDialog() {
    JOptionPane.showMessageDialog(MainFrame.this,
            "One or more of the input fields is empty.",
            "Error: ", JOptionPane.ERROR_MESSAGE);
    System.out.println("Asking for empty input dialog");
  }

  @Override
  public void invalidTimeDialog() {
    JOptionPane.showMessageDialog(MainFrame.this,
            "One or more times are invalid. Formatting is as follows: XX:xx",
            "Error: ", JOptionPane.ERROR_MESSAGE);
    System.out.println("Asking for invalid time dialog.");
  }

  @Override
  public void exceptionDialog(Exception ex) {
    JOptionPane.showMessageDialog(MainFrame.this,
            "Failed to create/modify/remove event: " + ex.getMessage(),
            "Error: ", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setEventFrame(IEventFrame frame) {
    this.eventFrame = frame;
  }

  @Override
  public void setScheduleFrame(IScheduleFrame frame) {
    this.scheduleFrame = frame;
  }

  @Override
  public void closeEventFrame() {
    eventFrame.closeEventFrame();
  }

  @Override
  public void closeScheduleFrame() {
    scheduleFrame.closeScheduleFrame();
  }
}


