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
 * one queue for each of the checkouts. Therefore, the customer
 * will choose to join the shortest queue for paying at the till
 * that follows it.
 * <p>
 * @author Luigi Pardey
 * @see Simulation
 */
public class MultiQueue extends Simulation {
    /**
     * Constructs a simulation that models multiple queues.
     * <p>
     * @param c number of customers to model.
     * @param t number of tills to model.
     */
    public MultiQueue(final int c, final int t) {
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
        PolicyArena policy = new PolicyArena();
        // Allocate spaces for as many checkouts as given
        QueueArena [] queues = new QueueArena[getNumTills()];
        CheckoutArena [] checkouts = new CheckoutArena[getNumTills()];
        EndArena end = new EndArena();
        // Checkouts routine
        for (int i = 0; i < getNumTills(); i++) {
            // Create the checkout
            CheckoutArena checkout = new CheckoutArena();
            QueueArena queue = new QueueArena();
            // Link policy to the queue
            policy.setLinkedArena(queue);
            // Link the queue to the checkout
            queue.setLinkedArena(checkout);
            // Link the checkout to the end
            checkout.setLinkedArena(end);
            // Store in the respective arrays
            queues[i] = queue;
            checkouts[i] = checkout;
        }
        // Set the linked arenas
        start.setLinkedArena(floor);
        floor.setLinkedArena(policy);
        // Add the arenas
        getArenas().add(start);
        getArenas().add(floor);
        getArenas().add(policy);
        for (int i = 0; i < getNumTills(); i++) {
            getArenas().add(queues[i]);
            getArenas().add(checkouts[i]);
        }
        getArenas().add(end);
        // Set the simulation type
        SimType type = SimType.MULTI;
        for (Arena a : getArenas()) {
            a.setSimType(type);
        }
    }
}
