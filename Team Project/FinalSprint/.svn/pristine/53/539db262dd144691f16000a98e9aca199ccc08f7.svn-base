package arenas;

import simulation.Simulation;

import agents.Customer;
import agents.Product;
import database.Database;
import events.CustomerMove;
import events.Event;

/**
 * This class models an Arena that contains the checkout till.
 * <p>
 * In an arena like this, a Customer is served by a member of staff to
 * exchange money for the products in the basket.
 * <p>
 * The time a member of staff will take to serve a specific customer is
 * proportional to the amount of items in the basket.
 * <p>
 * @author Luigi Pardey
 *
 */
public class CheckoutArena extends Arena {
    /**
     * Multiplier to convert between a floating-point waiting time
     * to a integer waiting time.
     */
    private static final int MULTIPLIER = 100;
    /**
     * Whether the checkout is serving a customer.
     */
    private boolean busy;
    /**
     * Whether there's a customer scheduled to leave this till.
     */
    private boolean scheduled;
    /**
     * Whether to use the Pareto distribution instead of exponential.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private boolean pareto;
    /**
     * Alpha value for the Pareto distribution.
     */
    private int alpha;
    /**
     * Time that takes to serve the customer.
     */
    private int time;
    /* (non-Javadoc)
     * @see arenas.Arena#initialise()
     */
    @Override
    protected final void initialise() {
        busy = false;
        scheduled = false;
    }

    /**
     * @return if the till is busy;
     */
    public final boolean isEmpty() {
        return !busy;
    }

    /* (non-Javadoc)
     * @see arenas.Arena#setCustomer(agents.Customer)
     */
    @Override
    public final void removeCustomer(final Customer c) {
        // Overriding the method to update the flags accordingly
        busy = false;
        scheduled = false;
        super.removeCustomer(c);
    }
    /* (non-Javadoc)
     * @see arenas.Arena#setCustomer(agents.Customer)
     */
    @Override
    public final void setCustomer(final Customer c) {
        // Overriding the method to update the flags accordingly
        if (!busy) {
            busy = true;
            super.setCustomer(c);
        }
    }
    /**
     * Tells this Arena to use a Pareto distribution for generating random
     * service times, instead of an exponential distribution.
     * <p>
     * @param a alpha value for Pareto distribution.
     */
    @Deprecated
    public final void setPareto(final int a) {
        pareto = true;
        alpha = a;
    }
    /* (non-Javadoc)
     * @see arenas.Arena#update(int)
     */
    @Override
    public final void update(final int simTime) {
        // Whether the till is empty, it has no customers.
        boolean empty = (getCustomerList().size() == 0);
        // No customers arriving, and no customers being served
        if (!scheduled && !empty) {
            Database database = Database.getDatabase();
            // Get the customer being served
            Customer c = getCustomerList().get(0);
            // Write the acquisition, iterating through the elements.
            for (Product p : c.getBaggage().getBasket()) {
                String values = "DEFAULT,"
                        + "'" + c.getCustomerID() + "',"
                        + "'" + p.getProductID() + "',"
                        + "'" + Simulation.getDay() + "',"
                        + "'" + simTime + "',"
                        + "'" + p.getPrice() + "', "
                        + "'" + (p.getPrice() - p.getCost()) + "'";
                String write = "INSERT INTO purchases VALUES (" + values + ")";
                database.updateData(write);
            }
            // Time proportional to number of items.
            time = simTime + (MULTIPLIER * c.getBaggage().getItems());
            // Next arena is always End arena
            Arena next = getLinkedArenas().get(0);
            // Schedule move to the End arena.
            Event ev = new CustomerMove(time, c, next, this);
            ev.schedule();
            c.updateQueuing(time);
            // Set the flag back
            scheduled = true;
        }
    }

    /**
     * Routine to generate a random service time with exponential
     * probability.
     * <p>
     * @return the service time.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private double exponentialServiceTime() {
        double u = Math.random();
        return -Math.log(u);
    }
    /**
     * Routine to generate a random service time with Pareto distribution.
     * <p>
     * @return the service time.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private double paretoServiceTime() {
        double u = Math.random();
        return (alpha - 1) * (Math.exp(-Math.log(u) / alpha) - 1);
    }
}
