package events;

import java.util.TreeSet;

/**
 * Class that contains the common interface of the simulation's events.
 * <p>
 * @author Luigi Pardey
 *
 */
public abstract class Event implements Comparable<Event> {
    /**
     * A list of Events ordered by the time at which they are scheduled
     * for execution.
     * <p>
     * Events that are scheduled will be stored here and ordered by the
     * time variable that was given as a parameter when the event was
     * constructed.
     * <p>
     * The queue is set as a class variable so it can be accessed from the
     * Simulation class to fetch the events.
     */
    public static TreeSet<Event> QUEUE = new TreeSet<Event>();
    /**
     * Class variable, an incremental number used for the event ID.
     */
    private static long eventIDCounter = 0;
    /**
     * Unique number identifier for this event.
     */
    private long eventID;
    /**
     * Time-step at which this event is scheduled.
     */
    private int eventTime;
    /**
     * Creates a new Event object, to be executed at the specified time.
     * <p>
     * Events are not executed upon creation. Instead, they must be added
     * to a queue, and the simulation will execute the first event in the
     * queue, which will be the current event at the matching time-step.
     * <p>
     * @param time the time at which the event will be executed.
     */
    protected Event(final int time) {
        eventTime = time;
        eventID = eventIDCounter++;
    }
    /**
     * Returns the time at which this event is meant to be executed.
     * <p>
     * @return the time-step.
     */
    public final int getTime() { return eventTime; }
    /**
     * Returns the unique number identifier for this event.
     * <p>
     * @return the unique number ID.
     */
    public final long getID() { return eventID; }
    /**
     * Schedules this event by adding it to the event queue.
     * <p>
     * Events must be scheduled in order to be executed. The events that
     * are in the queue are ordered by their execution time, so the ones
     * scheduled for a sooner time are executed first.
     */
    public final void schedule() {
        QUEUE.add(this);
    }
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(final Event e) {
        int otherTime = e.getTime();
        if (eventTime >= otherTime) {
            return 1;
        } else {
            return -1;
        }
    }
    /**
     * Performs a set of actions specific to this event.
     * <p>
     * When the simulation reaches the time-step that corresponds to
     * this event, and if the event has been scheduled, the event will
     * be called for execution.
     */
    public abstract void execute();
}
