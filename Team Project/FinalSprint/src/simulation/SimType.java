package simulation;

/**
 * Defines the different classifications for simulations.
 * <p>
 * @author Luigi Pardey
 *
 */
public enum SimType {
    /**
     * All customers form a single queue.
     * <p>
     * This type of simulation uses one large queue of Customers before
     * the checkouts. The Customer on the head of the queue then acts
     * according to the Policy, and moves to the next available till.
     */
    SINGLE,
    /**
     * Each till has its own queue.
     * <p>
     * This type of simulation uses one queue for each till. A Customer
     * will join the shortest queue as per the Policy, and wait to be
     * served. The Customer on the head of the queue moves to the
     * checkout only if the checkout is free.
     */
    MULTI
}
