package arenas;

import java.util.Random;

import agents.Customer;

import events.CustomerMove;
import events.Event;

/**
 * A model of the place where customers arrive when they enter the
 * Supermarket.
 * <p>
 * To simplify the model, this arena only generates customers with
 * a random probability P. While random variables are still not
 * implemented, this arena will only be a transitional arena between
 * the beginning of the simulation and the supermarket Floor.
 * <p>
 * @author Luigi Pardey
 * @see Customer
 */
public class SourceArena extends Arena {
    /**
     * Random number generator.
     */
    private Random rng;
    /**
     * Probability that a customer will be generated in a specific
     * time-step t.
     */
    private static final double P = 0.5f;
    /* (non-Javadoc)
     * @see arenas.Arena#initialise()
     */
    @Override
    protected final void initialise() {
        rng = new Random();
    }
    /* (non-Javadoc)
     * @see arenas.Arena#update(int)
     */
    @Override
    public final void update(final int simTime) {
        for (Customer c : getCustomerList()) {
            // For each customer, update its state
            c.update(simTime);
            // Check if customer is ready to move
            if (c.isReady() && !c.isMoving()) {
                final double x = rng.nextDouble();
                // Probability condition
                if (x > P) {
                    // Move in the next time-step as Customer is ready.
                    int nextTime = simTime + 1;
                    // Arena to move to is the Floor arena, alone in the list.
                    Arena destination = getLinkedArenas().get(0);
                    // Schedule the event
                    Event ev = new CustomerMove(nextTime, c, destination, this);
                    ev.schedule();
                }
            }
        }
    }


}
