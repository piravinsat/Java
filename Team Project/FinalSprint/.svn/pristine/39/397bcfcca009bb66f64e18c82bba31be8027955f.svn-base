package testsuite;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import agents.Agent;
import agents.Baggage;
import agents.Customer;

/**
 * This class contains a set of JUnit tests for the major methods in the
 * class Customer and its dependent subclasses, as well as in the superclass
 * Agent.
 * <p>
 * @author Luigi Pardey
 * @see Agent
 * @see Baggage
 * @see Customer
 */
public class TestCustomer {
    /**
     * Customer object under test.
     */
    private Customer fred;
    /**
     * Initialises all the objects under test.
     * <p>
     * This test forces the creation of the Customer class, along with a
     * constructor that takes in an integer, corresponding to the time of
     * creation.
     * <p>
     * Every Customer object is an extension of the Agent class, so by
     * setting the type to Agent, this forces to create the abstract Agent
     * class.
     * <p>
     * @throws Exception exception if the customer can't be initialised.
     */
    @Before
    public final void setUp() throws Exception {
        fred = new Customer(0, "Fred", "Doe");
        @SuppressWarnings("unused")
        Agent castFred = (Agent) fred;
    }

    /**
     * Test One: test automatically generated customer ID.
     * <p>
     * This test forces to add a method that returns a customer's unique
     * identification number, and a static counter in the Customer class
     * that will increment by one each time a new customer is created, used
     * to assign unique and sequential customer ID numbers.
     * <p>
     * Later, when the Database is implemented, the customer ID will be given
     * by the parameter used to create it.
     */
    @Test
    public final void testCustomerID() {
        Customer first = fred;
        Customer second = new Customer(1, "Jane", "Doe");
        assertEquals("Customer ID: 0 ", 0, first.getCustomerID());
        assertEquals("Customer ID: 1 ", 1, second.getCustomerID());
    }
    /**
     * Test Two: test the update method.
     * <p>
     * Each Customer object should have an update method that will change
     * the ready state of the customer depending on which activity is the
     * customer performing. Since activities are determined by events, the
     * state of the customer is given by a series of boolean variables.
     * <p>
     * The variable that determines if a customer has already changed its
     * state must be returned by the method <code>isReady()</code>, which
     * will be true after updating the customer.
     * <p>
     * The method <code>isReady()</code> tells whether a Customer is ready
     * to move to the next Arena.
     */
    @Test
    public final void testUpdate() {
        fred.update(0);
        assertTrue("Fred is ready", fred.isReady());
    }
    /**
     * Test Three: test the update method, adding a case for when the customer
     * is shopping for products.
     * <p>
     * When a Customer is located in the FloorArena, they will take Products
     * as given in their shopping list (in Baggage). While the customers are
     * "shopping" (given by the event AcquireProduct) they aren't ready to move
     * but as soon as the products are taken, the customer is ready to move.
     */
    @Test
    public final void testUpdateTwo() {
        fred.setShopping(true);
        fred.update(0);
        assertFalse("Fred is not ready", fred.isReady());
    }
    /**
     * Test Four: tests the customer's baggage.
     * <p>
     * Baggage is a class that contain a series of waiting times. Each Customer
     * creates and updates his own Baggage accordingly. This test ensures the
     * class Baggage exists and that it contains all it needs.
     * <p>
     * Other classes may need to store different information in the Customer's
     * baggage, so not all the possible and existing variables are tested here.
     * <p>
     * When the Database is implemented, starting times will be set manually
     * given through the appropriate setter methods.
     */
    @Test
    public final void testBaggage() {
        // Test for fred, our default test subject
        Baggage bag = fred.getBaggage();
        assertEquals("Created: time 0", 0, bag.getTimeStarted());
    }
}
