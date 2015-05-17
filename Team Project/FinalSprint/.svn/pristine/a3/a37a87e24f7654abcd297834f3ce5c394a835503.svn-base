package events;

import agents.Customer;
import agents.Product;
import agents.Thief;

/**
 * @author Group 3
 */
public class StealProduct extends Event {

    /**
     * Customer that does the looting.
     */
    private Thief thief;
    /**
     * Loot to be stolen.
     */
    private int productID;
    /**
     * Steals a product.
     * @param time The current simulation time.
     * @param c The customer that is doing the stealing.
     * @param pID The unique product ID of the product being stolen.
     */
    public StealProduct(final int time, final Customer c, final int pID) {
        super(time);
        thief = (Thief) c;
        productID = pID;
    }
    /**
     * Execute the event.
     */
    @Override
    public final void execute() {
        // Steal an item
        Product p = new Product(productID);
        thief.stealItem(p);
    }

}
