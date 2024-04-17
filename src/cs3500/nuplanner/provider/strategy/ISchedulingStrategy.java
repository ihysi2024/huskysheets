package cs3500.nuplanner.provider.strategy;

import java.util.List;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IEventLocation;

/**
 * Represents the interface for the different type of strategies for scheduling.
 */
public interface ISchedulingStrategy {

  /**
   * Schedules an event based on a list of user IDs and the duration of the event.
   *
   * @param name The list of user IDs involved in the event.
   * @param location location of the event.
   * @param durationMinutes The duration of the event in minutes.
   * @param userIds list of users invited to the event.
   * @return An IEvent object representing the scheduled event,
   *        or null if scheduling is not possible.
   */
  IEvent scheduleEvent(String name, IEventLocation location,
                       int durationMinutes, List<String> userIds);

}
