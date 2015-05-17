package testsuite;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import database.Database;

import simulation.SimType;

import agents.Customer;
import arenas.Arena;
import arenas.CheckoutArena;
import arenas.EndArena;
import arenas.FloorArena;
import arenas.PolicyArena;
import arenas.QueueArena;
import arenas.SourceArena;

/**
 * This class contains a set of JUnit tests for the major methods in the
 * class Arena and its dependent subclasses, corresponding to the different
 * arenas in the supermarket simulation.
 * <p>
 * @author Luigi Pardey
 * @see Arena
 * @see CheckoutArena
 * @see EndArena
 * @see FloorArena
 * @see PolicyArena
 * @see QueueArena
 * @see SourceArena
 */
public class TestArenas {
    /**
     * Customer to use in the arenas.
     */
    private Customer fred = new Customer(0, "Fred", "Doe");
    /**
     * Second customer, to be used when necessary.
     */
    private Customer bob = new Customer(0, "Bob", "Doe");
    /**
     * Test object: first arena in the supermarket.
     */
    private SourceArena source;
    /**
     * Test object: Arena where Customers take products and add them to their
     * baskets.
     */
    private FloorArena floor;
    /**
     * Test object: Arena where customers wait to be served at the checkout.
     */
    private QueueArena queue;
    /**
     * Test object: Arena where a single customer decides which checkout to
     * use.
     */
    private PolicyArena policy;
    /**
     * Test object: Arena where the customer buys the products it collected.
     */
    private CheckoutArena till;
    /**
     * Test object: Arena where all customers arrive after paying.
     */
    private EndArena end;
    /**
     * Initialises all the objects being tested.
     * <p>
     * The objects being tested will be Arenas but since Arena is an abstract
     * class, they will have the type of one of the subclasses of Arena.
     * <p>
     * This test forces to create all those classes, which need to be
     * extensions of the class Arena.
     * <p>
     * @throws Exception exception if the object can't be initialised.
     */
    @Before
    public final void setUp() throws Exception {
        new Database("Supermarket");
        source = new SourceArena();
        floor = new FloorArena();
        queue = new QueueArena();
        policy = new PolicyArena();
        till = new CheckoutArena();
        end = new EndArena();
        @SuppressWarnings("unused")
        Arena dummy;
        dummy = (Arena) source;
        dummy = (Arena) floor;
        dummy = (Arena) policy;
        dummy = (Arena) queue;
        dummy = (Arena) till;
        dummy = (Arena) end;
    }

    /**
     * Test One: Link arenas.
     * <p>
     * This test requires each Arena class to have a list that will hold
     * references to the Arenas that it will link to.
     * <p>
     * Also, Arenas are expected to return the list of arenas that are linked
     * to it via a getter method.
     */
    @Test
    public final void testLinks() {
        source.setLinkedArena(floor);
        floor.setLinkedArena(queue);
        queue.setLinkedArena(policy);
        policy.setLinkedArena(till);
        till.setLinkedArena(end);
        // Source links to Floor
        assertEquals("Test 1: S link to F", floor,
                source.getLinkedArenas().get(0));
        // Floor links to Queue
        assertEquals("Test 1: F link to Q", queue,
                floor.getLinkedArenas().get(0));
        // Queue links to Policy
        assertEquals("Test 1: Q link to P", policy,
                queue.getLinkedArenas().get(0));
        // Policy links to Chceckout
        assertEquals("Test 1: P link to C", till,
                policy.getLinkedArenas().get(0));
        // Checkout links to End
        assertEquals("Test 1: C link to E", end,
                till.getLinkedArenas().get(0));
    }
    /**
     * Test Two: add customers to arenas.
     * <p>
     * Arenas are expected to have a reference to the customers that are
     * in the arena at a particular moment in the simulation.
     * <p>
     * This test forces to implement a list of customers to hold the references
     * and relevant methods for adding and removing a customer. Also, the list
     * of customers must be accessible via a getter method.
     */
    @Test
    public final void testCustomers() {
        // Type cast to ensure the methods belong to Arena
        Arena srcArena = (Arena) source;
        // Add the customers
        srcArena.setCustomer(fred);
        srcArena.setCustomer(bob);
        assertEquals("Test 2: Fred", fred, srcArena.getCustomerList().get(0));
        assertEquals("Test 2: Bob", bob, srcArena.getCustomerList().get(1));
        // Remove the customers
        srcArena.removeCustomer(fred);
        srcArena.removeCustomer(bob);
        assertEquals("Test 2: empty", 0, srcArena.getCustomerList().size());
    }
    /**
     * Test Three: test the update method in Source arenas.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testSourceUpdate() {
        int simTime = 0;
        // Create the customer, and update its state so it's ready
        fred.update(simTime);

        /* Source Arena */
        source.setLinkedArena(floor);
        source.setCustomer(fred);
        // WHILE loop because the moving uses random probabilities
        while (!fred.isMoving()) {
            source.update(simTime++);
        }
        assertTrue("Test 3: Fred is moving", fred.isMoving());
        source.removeCustomer(fred);
    }
    /**
     * Test Four: test the update method in Floor arenas.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testFloorUpdate() {
        int simTime = 0;
        fred.update(simTime);
        fred.setShopping(false);
        /* Floor Arena */
        floor.setLinkedArena(queue);
        floor.setCustomer(fred);
        floor.update(simTime);
        assertTrue("Test 4: Fred is moving", fred.isMoving());
        floor.removeCustomer(fred);
    }
    /**
     * Test Five: test the update method in Queue arenas, when the simulation
     * models a single queue.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testSingleQueueUpdate() {
        int simTime = 0;
        fred.update(simTime);
        /* Queue Arena */
        queue.setLinkedArena(policy);
        queue.setCustomer(fred);
        queue.setSimType(SimType.SINGLE);
        // Policy is empty, so the customer can move there
        queue.update(simTime);
        assertTrue("Test 5: Fred is moving", fred.isMoving());
        queue.removeCustomer(fred);

        bob.update(simTime);
        policy.setCustomer(bob);
        queue.setCustomer(fred);
        // Policy is not empty so Fred can't move there
        queue.update(simTime);
        assertFalse("Test 5: Fred is not moving", fred.isMoving());
    }
    /**
     * Test Six: test the update method in Policy arenas, when the simulation
     * models a single queue.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testSinglePolicyUpdate() {
        int simTime = 0;
        fred.update(simTime);
        /* Policy Arena */
        policy.setLinkedArena(till);
        policy.setCustomer(fred);
        policy.setSimType(SimType.SINGLE);
        // Policy for single queues: choose the next available till
        policy.update(simTime);
        assertTrue("Test 6: Fred is moving", fred.isMoving());
        policy.removeCustomer(fred);

        till.setCustomer(bob);
        policy.setCustomer(fred);
        policy.update(simTime);
        assertFalse("Test 6: Fred is not moving", fred.isMoving());
    }
    /**
     * Test Seven: test the update method in Checkout arenas.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testCheckoutUpdate() {
        int simTime = 0;
        fred.update(simTime);
        /* Checkout Arena */
        till.setLinkedArena(end);
        till.setCustomer(fred);
        till.update(simTime);
        assertTrue("Test 7: Fred is moving", fred.isMoving());
        till.removeCustomer(fred);
    }
    /**
     * Test Eight: test the update method in Queue arenas, when the simulation
     * models multiple queues.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testMultiQueueUpdate() {
        // TODO Multiple Queue Arena update
    }
    /**
     * Test Nine: test the update method in Policy arenas, when the simulation
     * models multiple queues.
     * <p>
     * Each Arena subclass is expected to extend the abstract
     * <code>update()</code> method from the Arena superclass, which will
     * update the state of the Arena and raise events to execute specific
     * actions, which may be moving a customer to the next Arena, or allow
     * the customer to pick up products from the shelves, among others.
     */
    @Test
    public final void testMultiPolicyUpdate() {
        // TODO Multiple Policy Arena update
    }
}
