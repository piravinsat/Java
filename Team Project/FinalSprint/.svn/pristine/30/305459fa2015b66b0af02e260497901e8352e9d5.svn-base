package arenas;

import java.util.ArrayList;

import simulation.SimType;
import agents.Customer;


/**
 * This is the abstract class for an Arena.
 * <p>
 * Arenas are abstractions of the areas in a supermarket. Each Arena has its
 * own functions and properties, and can hold customers who can take actions
 * accordingly.
 * <p>
 * Arenas link to other arenas where an Agent may traverse to. The correct
 * way to move an Agent from one place to another is by raising an Event that
 * references the current Arena, the destination Arena and the acting Agent.
 * <p>
 * @author Luigi Pardey
 * @see Agent
 * @see Customer
 * @see Event
 */
public abstract class Arena {
    /**
     * Incremental counter for new arena IDs.
     */
    private static long arenaIDCounter = 0;
    /**
     * Unique number identifier of the arena.
     */
    private long arenaID;
    /**
     * Simplification: present in the arena.
     */
    private int customerCounter;
    /**
     * List of customers present in the arena.
     */
    private ArrayList<Customer> customers;
    /**
     * List of arenas where an Agent can arrive from the current one.
     */
    private ArrayList<Arena> linkedArenas;
    /**
     * Type of this simulation.
     */
    private SimType sType;
    /**
     * Constructs an Arena object.
     * <p>
     * <code>Arena</code>s model specific areas in a <code>Simulation</code>
     * where <code>Agent</code>s may enter and perform certain actions.
     * <p>
     * An Arena needs to be instantiated in the simulation that is modelling
     * it, and it must be of any of the available types.
     * <p>
     * The constructor sets important instance fields in the abstract class.
     */
    protected Arena() {
        arenaID = arenaIDCounter++;
        customers = new ArrayList<Customer>();
        linkedArenas = new ArrayList<Arena>();
        initialise();
    }
    /**
     * Initialises all the instance fields of the current arena.
     */
    protected abstract void initialise();
    /**
     * Returns the number used to uniquely identify an arena.
     * <p>
     * Identifiers are set during the creation of the arena and follow
     * an incremental pattern. The ID of an arena does not represent any
     * relationship with neighbouring arenas, nor does it represent
     * priorities. It only determines which arenas were created first.
     * <p>
     * @return the unique number identifier for this arena.
     */
    public final long getArenaID() {
        return arenaID;
    }
    /**
     * Returns the number of customers that are present in the arena.
     * <p>
     * @return the number of customers.
     */
    public final int getCustomerCount() {
        return customerCounter;
    }
    /**
     * Returns the list of customers that are currently in this arena.
     * <p>
     * Customers stay in the arena for as long as they need, depending
     * on the type of arena they are in. In arenas where they collect
     * baggage, they will stay longer than in transitional arenas.
     * <p>
     * @return the list of customers present in this arena.
     */
    public final ArrayList<Customer> getCustomerList() {
        return customers;
    }
    /**
     * Returns the list of Arenas that are linked to the current
     * one.
     * <p>
     * Arenas linked to this arena are those where an agent may move to
     * when they are in the current arena. Normally, the list contains
     * only one arena, since the arenas that link to many others need
     * a logical routine to decide which one is the appropriate destination
     * when an Agent is ready to move.
     * <p>
     * @return the list of arenas linked to the current one.
     */
    public final ArrayList<Arena> getLinkedArenas() {
        return linkedArenas;
    }
    /**
     * Returns the type of simulation model that is being used.
     * <p>
     * @return the type of simulation.
     */
    public final SimType getSimType() {
        return sType;
    }
    /**
     * Returns the instance of the Customer that it's in the current
     * Arena.
     * <p>
     * @param c the customer to remove from the arena.
     */
    public void removeCustomer(final Customer c) {
        // Get the index of the customer
        int i = customers.indexOf(c);
        // If it exists, i >= 0
        if (i != -1) {
            // Remove it from the list
            customers.remove(i);
        }
    }
    /**
     * Sets the count of customers back to zero.
     */
    public final void reset() {
        this.initialise();
        this.customers.clear();
        this.customerCounter = 0;
    }
    /**
     * Adds a customer into the list of customers in this arena.
     * <p>
     * Arenas can be populated by <code>Customer</code>s who perform certain
     * actions according to the <code>Arena</code> they are in. Customers
     * are stored inside an indexed List ordered according to their arrival.
     * <p>
     * @param c the customer to add to the list.
     */
    public void setCustomer(final Customer c) {
        customers.add(c);
        customerCounter += 1;
        c.setMoving(false);
    }
    /**
     * Links an arena to the current one.
     * <p>
     * Arenas may be linked together so an entity can traverse between
     * them. However, it is an unidirectional relationship. The arena that
     * is being linked does not need to link to the arena linking it.
     * <p>
     * @param a the arena to be linked
     */
    public final void setLinkedArena(final Arena a) {
        linkedArenas.add(a);
    }
    /**
     * Sets the model type of the simulation.
     * <p>
     * The simulation model may be either single-queue or multi-queue, as
     * given by the parameter.
     * <p>
     * @param st the type of simulation.
     */
    public final void setSimType(final SimType st) {
        sType = st;
    }

    /**
     * Actions to follow when the arena is updated every time-step of
     * the simulation.
     * <p>
     * Arenas should not directly change the simulation system. Instead,
     * if a change of state is required, an event could be scheduled.
     * <p>
     * @param simTime current simulation time-step.
     */
    public abstract void update(int simTime);

    /**
     * Prints a report of the arena's counters, etc.
     */
    public final void report() {
        String s1 = "Arena: " + this.getClass().getSimpleName();
        String s2 = "Customer count: " + customers.size();
        String s3 = "Customer passed: " + customerCounter;
        System.out.printf("%-20s %-20s %-20s\n", s1, s2, s3);
    }
}
