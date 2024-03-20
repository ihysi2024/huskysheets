**OVERVIEW**

1. This program is used to develop the model behind a planner system.
2. The planner system contains a list of users, each with a unique name and schedule.
A user's schedule is defined as a list of events, where an event must have a name,
a start time, end time, location (online and physical) and a list of other users invited.
3. The planner system is defined as having a set of loaded users, where their
schedules stand as XML files. Any user can add, remove, or modify an event in the uploaded XML file.
4. Once a user has been loaded in the system, they can also export an XML file of their schedule.

**TO USE THE CODE**

1. A user can export a schedule to XML without the suffix ".xml"

`public void userSchedToXML(String filePathToSave) {
writeToFile(filePathToSave + this.name + "_schedule.xml", this.name, this.schedule.scheduleToXMLFormat());
}`

2. A user can interpret their own schedule stored to a filepath location

`public static List<Event> interpretXML(String filePath) {
// create an empty list of events represented as hashmaps
List<HashMap<String, String[]>> listEventsMap = new ArrayList<>();
// create an empty list of events represented as events
List<Event> listEvents = new ArrayList<>();
// open the XML as a document
Document xmlDoc = readXML(filePath);
// grab the schedule node and a list of its children nodes representing events
Node scheduleNode = xmlDoc.getElementsByTagName("schedule").item(0);
NodeList nodeList = scheduleNode.getChildNodes();`

3. A user can add, modify, or remove an event in their schedule through the planner system

`  public void addEventForUser(Event event) {
  this.schedule.addEvent(event);
  }`


`  public void removeEventForUser(Event event) {
  this.schedule.removeEvent(event);
  }`

`public void modifyEvent(Event prevEvent, Event newEvent) {
User host = null;
for (User user: this.users) {
if (user.getName() == prevEvent.getUsers().get(0)) {
host = new User(user.getName(), user.getSchedule());
}
}
// only allow modification if the old event and updated event
// still have the same host
if (newEvent.getUsers().contains(prevEvent.getUsers().get(0))) {
try {
// remove the previous event from the user's schedule
removeEventForRelevantUsers(prevEvent, host);
// add the new event to the user's schedule
addEventForRelevantUsers(newEvent);
}
    catch (IllegalArgumentException e) {
    // if an exception is thrown, leave the list of events alone to avoid
    // causing conflict in any user's schedule
    }
}
}`

**KEY COMPONENTS**
1. A Planner System 

   a. A planner system has a list of users, where no two users can have the same 
    name and exist in the system.

   b. A planner system allows a user to add, modify, or remove an event from
    all relevant users' schedules.

   c. A planner system is assumed to have all users **already** loaded with their corresponding 
    schedules as XML files. 

2. A User has a name and a schedule. A user can add, remove or modify their schedule through the 
    planner system

3. A Schedule is a list of events occurring throughout the day every day of the week. 

    a. In a schedule, no two events can be made at the same time for a given user. Only one
    event can occur at any given time. A schedule can also be added to, removed from, and modified 
    with the direction of the user through the planner system. 

4. An Event has a name, a start time, an end time, a location and a list of users. 

   a. A location can be both physical and remote, hence the online and "string" location fields. 

   b. An event **must** have at least **one** user. The first user in all invitee lists
   to an event is the host. All other users in the invitee list are invitees.

   c. A host and an invitee can add an event to all other invitees' calendars given that 
   no coinciding events exist on any other user's calendar. 

   d. A host can remove an event from all other invitee's schedules, but an invitee can only 
    remove an event from their own schedule. 

   e. Any user can modify an event but the host must remain invariant in the updated event

   f. When an event is modified, it is modified for all users. If an event cannot be updated 
   due to scheduling conflicts, user schedules are remained unchanged only for those who cannot
    attend the updated event. 

5. A Time represents the time at which an event can occur.

   a. Time is represented using the seven days of the week as well
   as time in military standard time. 

6. A ScheduleTextView represents the output of the calendar in a String format. 
    The model can write the view of the schedule to an appendable that allows the user
    to see it. 

7. A UtilsXML allows the planner system to write to an XML file and read an XML file. 

**SOURCE ORGANIZATION**

1. The main components of the planner system model can be found under src/model. This
contains the interfaces for event, schedule, time, user, and planner system, as well their
class implementations Event, NUPlanner, Schedule, Time, and User. 
2. The visual components of the planner system can be found in src/view. This contains the
interface for ScheduleView and its class implementation ScheduleTextView. 
3. The XML tools needed to read and write a file are found in src/controller in UtilsXML. 
4. Tests for the model have been written in code/test where Event, NUPlannerSystem, Schedule, 
ScheduleTextView, Time, and User have been tested in individual test files. 

