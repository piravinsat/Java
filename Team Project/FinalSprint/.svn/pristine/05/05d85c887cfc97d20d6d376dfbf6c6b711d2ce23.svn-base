package events;

import agents.Customer;
import arenas.Arena;

/**
 * Models the arrival of a customer into the Supermarket.
 * <p>
 * This event is in charge of creating the customer object
 * and assigning the reference to the first arena.
 * <p>
 * @author Luigi Pardey
 * @see Customer
 * @see SourceArena
 */
public class CustomerArrives extends Event {
    /**
     * Arena this event links to.
     */
    private Arena arena;
    /**
     * The Customer performing this event.
     */
    private Customer customer;
    /**
     * Creates a new CustomerArrives event.
     * <p>
     * This event brings a customer to the supermarket and places it in the
     * specified arena.
     * <p>
     * @param time the time at which this event will be executed.
     * @param c the customer arriving the supermarket.
     * @param act the arena this event acts on.
     */
    public CustomerArrives(final int time, final Customer c, final Arena act) {
        super(time);
        // Generate the customer
        customer = c;
        c.getBaggage().setTimeStarted(time);
        arena = act;
    }
    /* (non-Javadoc)
     * @see events.Event#execute()
     */
    @Override
    public final void execute() {
        // Set the reference on the first arena.
        arena.setCustomer(customer);
    }
    /**
     * Debug-friendly report of the event.
     */
    public final void report() {
        // Debug output
        System.out.println("(" + super.getTime() + ") "
                + "Customer " + customer.getCustomerID()
                + "\n\tArrives: " + arena.getClass().getSimpleName());
    }
}
