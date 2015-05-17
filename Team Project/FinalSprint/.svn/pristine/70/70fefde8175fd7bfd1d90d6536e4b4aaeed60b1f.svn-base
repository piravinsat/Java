package events;

import agents.Customer;
import arenas.Arena;

/**
 * Event to move a customer from an arena A to an arena B.
 * <p>
 * This Event is in charge of passing the reference of the
 * moving customer from one arena to another, removing the
 * reference from the source arena, and adding it to the
 * destination arena.
 * <p>
 * @author Luigi Pardey
 * @author Piravin Satkunarajah
 *
 */
public class CustomerMove extends Event {
    /**
     * Arena that holds the customer that will move.
     */
    private Arena referencer;
    /**
     * Arena this event will send the customer to.
     */
    private Arena arena;
    /**
     * The customer moving between arenas.
     */
    private Customer customer;
    /**
     * Creates a CustomerMove event.
     * <p>
     * @param time the time at which this event will be executed.
     * @param c the customer performing this event.
     * @param act the arena this event acts on.
     * @param ref the arena this event was called from.
     */
    public CustomerMove(final int time,
            final Customer c,
            final Arena act,
            final Arena ref) {
        super(time);
        // Set the destination arena
        arena = act;
        // Set the source arena
        referencer = ref;
        // Set the reference of the customer
        customer = c;
        // Set the customer state to moving.
        c.setMoving(true);
    }

    /* (non-Javadoc)
     * @see events.Event#execute()
     */
    @Override
    public final void execute() {
        // Remove the customer from the source arena
        referencer.removeCustomer(customer);
        // Place it into the destination arena
        arena.setCustomer(customer);
        // Update customer moving state
        customer.setMoving(false);
        // Update customer's baggage.
        customer.updateBaggage(getTime());
    }
    /**
     * Debug-friendly report of the event.
     */
    public final void report() {
        System.out.println("Customer " + customer.getCustomerID()
                + "\n\tLeaves: " + referencer.getClass().getSimpleName()
                + "\n\tEnters: " + arena.getClass().getSimpleName());
    }
}
