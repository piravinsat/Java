package agents;

import java.util.Random;

/**
 * The model of one customer in a supermarket.
 * <p>
 * Customers are one type of Agent objects, which follow the same
 * parameters. A Customer may traverse between arenas, acquire baggage,
 * decide on which queue to join, and leave the supermarket.
 * <p>
 * @author Luigi Pardey
 * @author Piravin Satkunarajah
 *
 */

public class Customer extends Agent {
    /**
     * Unique Number Identifier for a customer.
     */
    private long customerID;
    /**
     * First Name.
     * <p>
     * This value is taken from the database and assigned to this customer.
     */
    private String firstName;
    /**
     * Last name.
     * <p>
     * This value is taken from the database and assigned to this customer.
     */
    private String lastName;
    /**
     * Whether the customer is ready to move.
     */
    private boolean ready;
    /**
     * Whether the customer has been already scheduled to move to a different
     * Arena, as given by a CustomerMove event.
     */
    private boolean moving;
    /**
     * Whether the customer is looking for products on the floor.
     */
    private boolean shopping;
    /**
     * This customer's Baggage, or history of waiting times.
     */
    private Baggage baggage;
    /**
     * Value used to detect thieves.
     * <p>
     * Reflects the number of times the customer has been in the supermarket
     * when a theft was detected.
     */
    private int suspicionFactor = 0;
    /**
     * Creates a Customer object.
     * <p>
     * @param id the customer ID as found in the database.
     * @param fname the customer's first name, from the database.
     * @param lname the customer's last name, from the database.
     */
    public Customer(final int id, final String fname, final String lname) {
        super(AgentType.CUSTOMER);
        baggage = new Baggage();
        this.customerID = id;
        this.firstName = fname;
        this.lastName = lname;
        ready = false;
        setShoppingList();
    }
    /**
     * Returns the object that contains the history of waiting times for this
     * Customer.
     * <p>
     * @return the baggage of this customer.
     */
    public final Baggage getBaggage() {
        return baggage;
    }
    /**
     * Returns the number that uniquely identifies a Customer.
     * <p>
     * This differs from the Agent ID in that only Customers get a
     * CustomerID number for themselves, while Agent ID also englobes
     * staff members.
     * <p>
     * @return the customer's ID.
     */
    public final long getCustomerID() {
        return customerID;
    }
    /**
     * Returns this customer's first name.
     * <p>
     * @return the customer's first name.
     */
    public final String getFirstName() {
        return firstName;
    }
    /**
     * Returns this customer's last name.
     * <p>
     * @return the customer's last name.
     */
    public final String getLastName() {
        return lastName;
    }
    /**
     * Returns the level of suspicion of this customer.
     * <p>
     * @return the level of suspicion.
     */
    public final int getSuspicion() {
        return suspicionFactor;
    }
    /**
     * Increments this customer's suspicion factor by one.
     */
    public final void incrementSuspicion() {
        suspicionFactor++;
    }
    /**
     * Returns whether the customer is ready to move to the next Arena.
     * <p>
     * Once this Customer has updated its state accordingly, it will set this
     * variable to true, meaning that there are no more actions to take in
     * the current Arena, and a CustomerMove event can be scheduled on this
     * customer.
     * <p>
     * @return true if the customer is ready to move.
     */
    public final boolean isReady() {
        return ready;
    }
    /**
     * Returns whether a CustomerMove event has been scheduled for the
     * current customer.
     * <p>
     * Once a CustomerMove event is raised and scheduled for execution, the
     * state of this Customer will change to tell it is in a moving state:
     * traversing from one Arena to another.
     * <p>
     * @return true if the customer is in a moving state.
     */
    public final boolean isMoving() {
        return moving;
    }
    /**
     * Returns whether this customer is looking for products in FloorArena.
     * <p>
     * Once a Customer arrives to the supermarket's floor, it will search for
     * the products on its shopping list until the list is empty (no more
     * products to look for).
     * <p>
     * @return true if the customer's shopping list is not empty.
     */
    public final boolean isShopping() {
        return shopping;
    }
    /**
     * @return False for customers, true for thieves.
     */
    public boolean isThief() {
        return false;
    }
    /**
     * Resets the volatile variables of this customer.
     */
    public void reset() {
        ready = false;
        moving = false;
        shopping = false;
        baggage.reset();
        setShoppingList();
    }
    /**
     * Sets whether the customer is in a moving state.
     * <p>
     * This method assigns true or false to the variable that determines
     * whether a CustomerMove event has been raised and scheduled for this
     * Customer.
     * <p>
     * @param m whether the moving has been scheduled.
     */
    public final void setMoving(final boolean m) {
        moving = m;
    }
    /**
     * Sets whether the customer is looking for products on the shelves.
     * <p>
     * This method assigns true or false to the variable that determines
     * whether a Customer has entered the FloorArena and is raising
     * AcquireProduct events until its shopping list is empty.
     * <p>
     * @param s whether the customer is looking for products.
     */
    public final void setShopping(final boolean s) {
        shopping = s;
    }
    /**
     * Routine to fill up the shopping list according to the customer's
     * data on the database.
     * <p>
     * The shopping list is located on the customer's baggage.
     */
    protected final void setShoppingList() {
        //Clears the old shopping list
        baggage.getShoppingList().clear();
        Random rnd = new Random();
        final int nProducts = 20;
        //Randomly gives a number of items on the list from 1 to 20
        int itemsOnList = rnd.nextInt(nProducts) + 1;

        //Randomly selects an item and places it in shopping list
        for (int i = 0; i < itemsOnList; i++) {
            int item = rnd.nextInt(nProducts) + 1;
            baggage.getShoppingList().add(item);
        }
    }
    /* (non-Javadoc)
     * @see simulation.Agent#update(int)
     */
    @Override
    public final void update(final int simTime) {
        // Acquire Baggage
        // determine if it is ready to move
        ready = true;
        if (shopping) { ready = false; }
    }
    /**
     * Updates the waiting times in this customer's baggage accordingly.
     * <p>
     * @param time the time of the customer's move action.
     */
    public final void updateBaggage(final int time) {
        baggage.setTimeShopping(time - baggage.getTimeStarted());
        baggage.setTimeInArena(time - baggage.getEnteredArena());
        baggage.setEnteredArena(time);
    }
    /**
     * Updates the customer's queueing time, relative to a time step.
     * <p>
     * @param time the time of the customer's move action.
     */
    public final void updateQueuing(final int time) {
        baggage.addTimeQueueing(time - baggage.getEnteredArena());
    }
}
