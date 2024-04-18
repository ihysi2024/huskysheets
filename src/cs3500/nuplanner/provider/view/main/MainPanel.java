package view.main.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IReadOnlyCentralSystem;
import view.event.IEventFrame;
import controller.IFeatures;
import view.schedule.IScheduleFrame;

/**
 * Main panel that represents the JPanels of the MainFrame: FilePanel, GridPanel,
 * and BottomPanel.
 */
public class MainPanel extends JPanel implements IMainPanel {

  private IReadOnlyCentralSystem model;
  private JPanel filePanel;
  private GridPanel gridPanel;
  private JPanel bottomPanel;
  private JMenuItem addCalendars;
  private JMenuItem saveCalendars;
  private String currentUser;
  private JComboBox<String> userDropdown;
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JButton hostColorToggleButton;

  /**
   * Constructor to set up the MainPanel with a model and frames.
   * @param model read-only model.
   * @param eventFrame Event Frame instance.
   * @param scheduleFrame Schedule Frame instance.
   */
  public MainPanel(IReadOnlyCentralSystem model, IEventFrame eventFrame,
                   IScheduleFrame scheduleFrame) {
    super();
    this.model = model;
    gridPanel = new GridPanel(model, eventFrame);
    this.setLayout(new BorderLayout());
    initializeFilePanel();

    initializeBottomPanel();
    this.add(filePanel, BorderLayout.NORTH);
    this.add(gridPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);

    if (model.accessUsers().isEmpty()) {
      this.currentUser = null;
    } else {
      this.currentUser = model.accessUsers().get(0);
    }
  }

  /**
   * empty constructor for mock testing.
   */
  public MainPanel() {
    super();
  }

  private void initializeFilePanel() {
    this.filePanel = new JPanel();
    this.filePanel.setLayout(new BorderLayout());
    this.filePanel.setBorder(BorderFactory.createLineBorder(Color.black));
    JMenuBar menuBar = new JMenuBar();

    // Set up the File menu
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    // Set up the menu items with action commands
    addCalendars = new JMenuItem("Add Calendar");
    addCalendars.setActionCommand("Add Calendar");
    fileMenu.add(addCalendars);

    saveCalendars = new JMenuItem("Save Calendars");
    saveCalendars.setActionCommand("Save Calendars");
    fileMenu.add(saveCalendars);

    this.filePanel.add(menuBar, BorderLayout.LINE_START);
  }

  private void initializeBottomPanel() {
    this.bottomPanel = new JPanel();
    this.bottomPanel.setLayout(new GridBagLayout());
    userDropdown = new JComboBox<>(model.accessUsers().toArray(new String[0]));

    createEventButton = new JButton("Create Event");
    scheduleEventButton = new JButton("Schedule Event");
    hostColorToggleButton = new JButton("Toggle Host Color");

    GridBagConstraints gbConstraints = new GridBagConstraints();
    gbConstraints.fill = GridBagConstraints.HORIZONTAL;
    gbConstraints.weightx = 1;

    this.bottomPanel.add(userDropdown,gbConstraints);
    this.bottomPanel.add(createEventButton,gbConstraints);
    this.bottomPanel.add(scheduleEventButton,gbConstraints);
    this.bottomPanel.add(hostColorToggleButton,gbConstraints);
  }

  @Override
  public void addFilePanelFeatures(IFeatures features) {
    addCalendars.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select XML Calendar File");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(
                new FileNameExtensionFilter("XML Files", "xml"));
        int option = fileChooser.showOpenDialog(MainPanel.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          File selectedPath = fileChooser.getSelectedFile();
          try {
            features.loadCalendarXML(selectedPath.getAbsolutePath());
            userDropdown.removeAllItems();
            for (String user : MainPanel.this.model.accessUsers()) {
              userDropdown.addItem(user);
              userDropdown.setSelectedIndex(0);
            }

          } catch (Exception ex) {
            JOptionPane.showMessageDialog(MainPanel.this,
                    "Failed to load XML: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    saveCalendars.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Directory to Save Calendars");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showSaveDialog(MainPanel.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedDirectory = fileChooser.getSelectedFile();
          try {
            features.saveCalendarsXML(selectedDirectory.getAbsolutePath());
            System.out.println("Calendars saved to " + selectedDirectory.getAbsolutePath());
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(MainPanel.this,
                    "Failed to save XML: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });
  }

  @Override
  public void addBottomPanelFeatures(IFeatures features) {

    userDropdown.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        currentUser = e.getItem().toString();
        gridPanel.setCurrentUser(e.getItem().toString());
        System.out.println("current user: " + e.getItem().toString());
      }

    });

    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.openNewEventFrame();
      }
    });


    scheduleEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        features.openNewScheduleFrame();
      }
    });

    hostColorToggleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        gridPanel.setHostColorToggle();
      }
    });
  }

  public void addGridPanelFeatures(IFeatures features) {
    gridPanel.addGridPanelFeatures(features);
  }
}
