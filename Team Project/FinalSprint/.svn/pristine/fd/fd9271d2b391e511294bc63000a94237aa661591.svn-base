package testsuite;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import database.Database;

import agents.Customer;
import arenas.FloorArena;
import arenas.SourceArena;

import events.CustomerArrives;
import events.CustomerLeaves;
import events.CustomerMove;
import events.Event;

/**
 * This class contains a set of JUnit tests for the most important methods
 * of the Event class and its subclasses, namely CustomerArrives,
 * CustomerLeaves, CustomerMove, AcquireProduct and StealProduct.
 * <p>
 * @author Luigi Pardey
 * @see Event
 * @see CustomerArrives
 * @see CustomerLeaves
 * @see CustomerMove
 */
public class TestEvents {

    /**
     * Event object being tested.
     * <p>
     * Models a Customer entering the Supermarket through the first Arena.
     * The first Arena in a supermarket is a SourceArena.
     */
    private CustomerArrives arrives;
    /**
     * Event object being tested.
     * <p>
     * Models a Customer leaving the Supermarket through the last Arena.
     * The last arena in a supermarket is an EndArena.
     */
    private CustomerLeaves leaves;
    /**
     * Event object being tested.
     * <p>
     * Models a Customer traversing between two Arenas in the supermarket
     * that are linked.
     */
    private CustomerMove moves;
    /**
     * The Customer object that will perform the events.
     */
    private Customer fred;
    /**
     * Source Arena of a supermarket.
     */
    private SourceArena source;
    /**
     * Floor Arena of a supermarket.
     */
    private FloorArena floor;

    /**
     * Initialises the objects being tested and other objects that help with
     * testing.
     * <p>
     * This method forces to create the different event objects, that have to
     * be extensions of a superclass Event. Since the superclass Event is
     * abstract, it will be tested via its subclasses.
     * <p>
     * Also, constructors for each individual subclass must be provided, as per
     * the specification.
     * <p>
     * @throws Exception exception if the objects can't be initialised.
     */
    @Before
    public final void setUp() throws Exception {
        new Database("Supermarket");
        int simTime = 0;
        source = new SourceArena();
        floor = new FloorArena();
        fred = new Customer(0, "Fred", "Doe");
        arrives = new CustomerArrives(simTime++, fred, source);
        leaves = new CustomerLeaves(simTime++, fred);
        moves = new CustomerMove(simTime++, fred, floor, source);
        @SuppressWarnings("unused")
        Event test;
        // Type casting to ensure they are subclasses of Event.
        test = (Event) arrives;
        test = (Event) leaves;
        test = (Event) moves;
    }

    /**
     * Test One: test the event scheduler.
     * <p>
     * The Event class should have a queue of events so that events that
     * must be executed first are towards the beginning of the queue.
     * <p>
     * This test forces to add a TreeSet object as the queue since it's the
     * Set object with the best structure to hold such Events. Also, the
     * method <code>schedule()</code> needs to be added to the abstract
     * class, so that events scheduled get added to the queue.
     */
    @Test
    public final void testSchedule() {
        arrives.schedule();
        leaves.schedule();
        moves.schedule();
        assertEquals("Test 1: arrives", arrives, Event.QUEUE.pollFirst());
        assertEquals("Test 1: leaves", leaves, Event.QUEUE.pollFirst());
        assertEquals("Test 1: moves", moves, Event.QUEUE.pollFirst());
    }
    /**
     * Test Two: test the event scheduler, with unordered methods.
     * <p>
     * The queue of events must order them according to the planned execution
     * time of each event, those with lower times appearing first. For this,
     * each Event object must implement the interface <code>Comparable</code>.
     */
    @Test
    public final void testUnorderedSchedule() {
        moves.schedule();
        leaves.schedule();
        // Compare the events
        int compare = moves.compareTo(leaves);
        // Since "moves" has a higher value for scheduled time, it will be
        // considered larger, and compareTo returns a positive integer
        final int expected = 1;
        assertEquals("Test 2: compare", expected, compare);
        assertEquals("Test 2: leaves", leaves, Event.QUEUE.pollFirst());
        assertEquals("Test 2: moves", moves, Event.QUEUE.pollFirst());
    }
}
