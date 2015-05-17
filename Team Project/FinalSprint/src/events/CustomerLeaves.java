package events;

import agents.Customer;

/**
 * Models the departure of a customer into the Supermarket.
 * <p>
 * This event is in charge of deleting the customer object.
 * <p>
 * @author Luigi Pardey
 * @see Customer
 * @see SourceArena
 */
public class CustomerLeaves extends Event {
    /**
     * The customer that acts in this Event.
     */
    private Customer customer;
    /**
     * Creates a CustomerLeaves event.
     * <p>
     * @param time the time at which this event will be executed.
     * @param c the customer that acts in this event.
     */
    public CustomerLeaves(final int time, final Customer c) {
        super(time);
        customer = c;
        c.setMoving(true);
    }

    /* (non-Javadoc)
     * @see events.Event#execute()
     */
    @Override
    public final void execute() {
        customer.getBaggage().setTimeFinished(getTime());
        customer.getBaggage().updateAverages();
    }

}
