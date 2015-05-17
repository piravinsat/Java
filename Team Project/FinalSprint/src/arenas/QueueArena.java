package arenas;

import events.CustomerMove;
import events.Event;

import agents.Customer;

import simulation.SimType;

/**
 * Simulates a supermarket Queue.
 * <p>
 * This depends on the type of simulation modelled. A Single-queue
 * simulation will only have one instance of this Arena, that will
 * link to a Policy, which chooses the next free checkout. Whereas
 * a Multi-queue simulation will have a Policy arena linking to many
 * instances of Queues (one per till).
 * <p>
 * @author Lestat
 *
 */
public class QueueArena extends Arena {
    /**
     * Whether the customer at the head has chosen its destination.
     */
    private boolean assigned;
    /* (non-Javadoc)
     * @see arenas.Arena#initialise()
     */
    @Override
    protected final void initialise() {
        assigned = false;
    }
    @Override
    public final void removeCustomer(final Customer c) {
        assigned = false;
        super.removeCustomer(c);
    }
    /* (non-Javadoc)
     * @see arenas.Arena#update(int)
     */
    @Override
    public final void update(final int simTime) {
        // Execute only if there is customers here
        if (getCustomerList().size() > 0) {
            // The customer that's deciding:
            Customer customer = getCustomerList().get(0);
            int time = simTime + 1;
            // The next step depends on the simulation type
            if (getSimType() == SimType.SINGLE) {
                /* Single Queue logic:
                 * The next arena is a Checkout
                 * The destination is the next free Checkout */

                // Policy Arena is the only arena linked to this Arena so it
                // has index 0
                PolicyArena policy = (PolicyArena) getLinkedArenas().get(0);

                // Act if there is no customers currently deciding.
                if (policy.isEmpty()) {
                    // Schedule the move for this customer

                    Event ev = new CustomerMove(time, customer, policy, this);
                    ev.schedule();
                    customer.updateQueuing(time);
                    // Customer is now in moving state
                    customer.setMoving(true);

                    // Policy is locked by the customer moving there
                    policy.setLock();
                }
                // Do nothing if the policy is locked.
            } else {
                /* Multiple Queue logic:
                 * The next arena is a Queue
                 * The destination is the shortest queue.*/

                // This is the checkout that follows the queue
                CheckoutArena till = (CheckoutArena) getLinkedArenas().get(0);

                if (till.isEmpty() && !assigned) {
                    // Schedule the move for this customer
                    Event ev = new CustomerMove(time, customer, till, this);
                    ev.schedule();
                    customer.updateQueuing(time);
                    // Customer is now in moving state
                    customer.setMoving(true);

                    assigned = true;
                }
            }
        }
    }
}
