package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static controller.UtilsXML.writeToFile;

/**
 * Represents a user of the planner system. A user has a schedule that they can interact with.
 */
public class User implements IUser {
  private final String name;

  private final ISchedule schedule;

  /**
   * A user of the planner system has the ability to add, modify, and remove events
   * as well as upload or export a schedule as an XML. A user has a distinct name and
   * has access to exactly one schedule.
   * @param name a string representing the user name
   * @param schedule the user's schedule in the planner system
   */
  public User(String name, ISchedule schedule) {
    this.name = Objects.requireNonNull(name);
    this.schedule = schedule;
  }


  /**
   * Observes the name of the user. This is necessary to compare a list of users names.
   * invited to an Event and the list of users in the Planner System.
   * @return a string representing the current user's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Observes the schedule of the user. This is necessary for the addition or removal
   * of an event from the schedules of all relevant invitees to the event.
   * @return a Schedule as a list of events that is specific to the current user
   */

  public ISchedule getSchedule() {
    return this.schedule;
  }



  /**
   * Writes the user's schedule to an XML file that is unique to them and a given filepath.
   * @param filePathToSave path to save the XML written by the user
   */
  public void userSchedToXML(String filePathToSave) {
    writeToFile(filePathToSave + "\\" + this.name + "_schedule.xml", this.name,
            this.schedule.scheduleToXMLFormat());
    System.out.println("got here");
  }

  /**
   * Allow the user to examine the contents of an XML file located a specific path
   * and use the XML to generate a list of events compatible with the planner system
   * corresponding to the user.
   * @param xmlDoc path where XML is located
   * @return a list of events
   */

  // should take in a Document instead - if not a bit of coupling but okay
  public static IUser interpretXML(Document xmlDoc) {
    // create an empty list of events represented as hashmaps
    List<HashMap<String, String[]>> listEventsMap = new ArrayList<>();
    // create an empty list of events represented as events
    List<IEvent> listEvents = new ArrayList<>();

    // grab the schedule node and a list of its children nodes representing events
    Node scheduleNode = xmlDoc.getElementsByTagName("schedule").item(0);
    NodeList nodeList = scheduleNode.getChildNodes();

    Element elemSched = (Element) scheduleNode;
    String userName = elemSched.getAttribute("id");

    // go through each event in the schedule
    for (int item = 0; item < nodeList.getLength(); item++) {
      Node current = nodeList.item(item);
      if (current.getNodeType() == Node.ELEMENT_NODE) {
        // generate an element to represent the current event
        NodeList elemChildren = getNodeList((Element) current);
        // generate an empty hashmap to represent a *single* event
        // this hashmap will map each event attribute name to its corresponding
        // set of values where they will be stored as a list of strings
        // i.e. "Time" -> ["Tuesday", "0950", "Tuesday", "1230"]
        HashMap<String, String[]> eventMap = new HashMap<>(); // one per event

        // go through each event attribute
        for (int childIdx = 0; childIdx < elemChildren.getLength(); childIdx++) {
          Node childNode = elemChildren.item(childIdx);
          if (childNode.getNodeType() == Node.ELEMENT_NODE) {
            // generate an element from that event attribute
            Element child = (Element) childNode;
            String tagName = child.getTagName().trim();
            // generate a list of string values from each tagged attribute
            // remove any excess spaces
            makeEventMap(child, eventMap, tagName);
          }
        }
        // add the fully formed event as a hashmap to this list
        listEventsMap.add(eventMap);
      }
    }
    // convert each hashmap of an event into an actual event
    for (HashMap<String, String[]> eventMapping : listEventsMap) {
      listEvents.add(makeEvent(eventMapping));
    }
    ArrayList<IEvent> events = new ArrayList<>(listEvents);
    return new User(userName, new Schedule(events));
  }

  /**
   * Gets the children nodes for an event.
   * @param current current Element node
   * @return NodeList representing all the children nodes of the event.
   */
  private static NodeList getNodeList(Element current) {
    Element elem = current;

    // make temporary variables to store the event information
    String tempEventName = "";
    String tempStartDay = "";
    String tempStartTime = "";
    String tempEndDay = "";
    String tempEndTime = "";
    String online = "";
    String location = "";
    List<String> userList = new ArrayList<>();

    // grab the children nodes of an event i.e. time, location, etc.
    return elem.getChildNodes();
  }


  /**
   * Adds keys and their corresponding values to a hashmap for events.
   * @param child current Element node
   * @param eventMap current list of values for that Event's inner node
   * @param tagName the relevant tag name
   */
  private static void makeEventMap(Element child, HashMap<String, String[]> eventMap,
                                   String tagName) {
    String[] tagNameChildren = child.getTextContent().trim().split("\n");
    for (int idx = 0; idx < tagNameChildren.length; idx++) {
      tagNameChildren[idx] = tagNameChildren[idx].trim();
    }
    // add the attribute to the event map
    eventMap.put(tagName, tagNameChildren);
  }

  /**
   * Convert a hashmap of an event's attribute name to a list of string values
   * into an event compatible with the planner system, i.e. as an instance of Event.
   * @param eventToMake HashMap of attribute name->list of values to convert to events.
   * @return the event corresponding to the HashMap
   */
  public static IEvent makeEvent(Map<String, String[]> eventToMake) {
    try {
      // generate temporary values for the event
      String tempEventName = "";
      Time tempStartTime = null;
      Time tempEndTime = null;
      boolean online = true;
      String location = "";
      List<String> userList = new ArrayList<>();

      // parse through each event attribute in the map
      // the following array indexing is made with the assumption that
      // 1. a name has only one element - indexed at 0
      // 2. a time has 4 elements (start day, start time, end day, end time) - indexed 0 - 3
      // 3. a location has 2 elements (online, location) - indexed from 0-1
      // 4. users has n elements that can be added to the temp users list variable
      for (String key : eventToMake.keySet()) {
        if (key.equals("name")) {
          tempEventName = eventToMake.get(key)[0];
        }

        if (key.equals("time")) {
          System.out.println(eventToMake.get(key)[1]);
          tempStartTime = Time.stringToTime(eventToMake.get(key)[0], eventToMake.get(key)[1]);
          tempEndTime = Time.stringToTime(eventToMake.get(key)[2], eventToMake.get(key)[3]);
        }
        if (key.equals("location")) {
          online = (eventToMake.get(key)[0].toLowerCase().equals("true"));
          location = eventToMake.get(key)[1];
        }
        if (key.equals("users")) {
          for (String userName : eventToMake.get(key)) {
            userList.add(userName);
          }

          //  userList.addAll(Arrays.asList(eventToMake.get(key)));
        }

      }
      return new Event(tempEventName, tempStartTime, tempEndTime, online, location, userList);
    }
    // input these variables to a new event and return it
    catch (IllegalArgumentException e) {
      System.out.println("wasn't able to make event" + e.getMessage());
      return null;
    }
    //return null;
  }

  /**
   * Adds an event to a user's schedule.
   * @param event event to add
   */
  public void addEventForUser(IEvent event) {
    try {
      this.schedule.addEvent(event);
    }
    catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("cannot add this event");
    }
  }

  /**
   * Removes an event from a user's schedule.
   * @param event to remove
   */
  public void removeEventForUser(IEvent event) {
    this.schedule.removeEvent(event);
  }

}
