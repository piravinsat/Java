package arenas;

import events.CustomerLeaves;
import events.Event;
import agents.Customer;

/**
 * The last of the arenas that represents the end of the supermarket.
 * <p>
 * All customers get here after traversing across the supermarket.
 * If necessary, the Arena calls the destructor on each of the customer
 * objects, or just collects the information for display.
 * <p>
 * @author Luigi Pardey
 *
 */
public class EndArena extends Arena {

    /* (non-Javadoc)
     * @see arenas.Arena#initialise()
     */
    @Override
    protected void initialise() {
        // Nothing to do here yet
    }
    /* (non-Javadoc)
     * @see arenas.Arena#update(int)
     */
    @Override
    public final void update(final int simTime) {
        for (Customer c : getCustomerList()) {
            // Check if customer is ready to move
            if (c.isReady() && !c.isMoving()) {
                Event leave = new CustomerLeaves(simTime, c);
                leave.schedule();
            }
        }
    }
}
