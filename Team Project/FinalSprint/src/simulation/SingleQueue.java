package simulation;

import java.util.ArrayList;

import arenas.Arena;
import arenas.CheckoutArena;
import arenas.EndArena;
import arenas.FloorArena;
import arenas.PolicyArena;
import arenas.QueueArena;
import arenas.SourceArena;


/**
 * Models the simulation of a supermarket with different arenas.
 * <p>
 * This particular simulation will model the supermarket as having
 * one single queue for all the checkouts. Therefore, the customer
 * located at the head of the queue will choose the next available
 * till to pay for its items.
 * <p>
 * @author Luigi Pardey
 *
 */
public class SingleQueue extends Simulation {
    /**
     * Constructs a Simulation object.
     *
     * @param c number of customers to model.
     * @param t number of tills to open.
     */
    public SingleQueue(final int c, final int t) {
        super(c, t);
    }
    /* (non-Javadoc)
     * @see simulation.Simulation#loadArenas()
     */
    @Override
    protected final void loadArenas() {
        setArenas(new ArrayList<Arena>());
        // Create the arenas
        SourceArena start = new SourceArena();
        FloorArena floor = new FloorArena();
        QueueArena queue = new QueueArena();
        PolicyArena policy = new PolicyArena();
        // Allocate spaces for as many checkouts as given
        CheckoutArena [] checkouts = new CheckoutArena[getNumTills()];
        EndArena end = new EndArena();
        // Checkouts routine
        for (int i = 0; i < getNumTills(); i++) {
            // Create the checkout
            CheckoutArena checkout = new CheckoutArena();
            // Link policy to the checkout
            policy.setLinkedArena(checkout);
            // Link the checkout to the end
            checkout.setLinkedArena(end);
            // Store it in the checkouts array
            checkouts[i] = checkout;
        }
        // Set the linked arenas
        start.setLinkedArena(floor);
        floor.setLinkedArena(queue);
        queue.setLinkedArena(policy);
        // Add the arenas
        getArenas().add(start);
        getArenas().add(floor);
        getArenas().add(queue);
        getArenas().add(policy);
        for (Arena a : checkouts) {
            getArenas().add(a);
        }
        getArenas().add(end);
        // Set the simulation type
        SimType type = SimType.SINGLE;
        for (Arena a : getArenas()) {
            a.setSimType(type);
        }
    }
}
