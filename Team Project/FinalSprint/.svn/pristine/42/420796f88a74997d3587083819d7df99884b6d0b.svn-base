package events;

import agents.Customer;
import agents.Product;

/**
 * Event that models a customer taking a single product from the
 * shelves of the supermarket and putting it in its shopping basket.
 * <p>
 * This event is in charge of creating the product object with the
 * right stock ID that will be used to look up for the remaining
 * attributes on the database.
 * <p>
 * @author Luigi Pardey
 * @see Product
 */
public class AcquireProduct extends Event {
    /**
     * Reference to the customer acting on this event.
     */
    private Customer customer;
    /**
     * Reference to the product ID of the product being taken.
     */
    private int productID;
    /**
     * Creates an AcquireProduct event.
     * <p>
     * @param time the time at which this event will be executed.
     * @param c the customer performing this event.
     * @param pID the stock ID of the unique product to acquire.
     */
    public AcquireProduct(final int time, final Customer c, final int pID) {
        super(time);
        customer = c;
        productID = pID;
    }

    /* (non-Javadoc)
     * @see events.Event#execute()
     */
    @Override
    public final void execute() {
        // Create the product object and add it to the basket
        Product p = new Product(productID);
        customer.getBaggage().addToBasket(p);
    }
}
