package arenas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import simulation.Simulation;
import agents.Customer;
import agents.Thief;
import database.Database;
import events.AcquireProduct;
import events.CustomerMove;
import events.Event;
import events.StealProduct;

/**
 * Arena that models the supermarket floor, where customers find items.
 * <p>
 * This Arena links to the database to get information about the products
 * that the Customer will get. Customers will spend a set amount of time
 * in this Arena depending on their profile.
 * <p>
 * @author Luigi Pardey
 *
 */
public class FloorArena extends Arena {

    /* (non-Javadoc)
     * @see arenas.Arena#initialise()
     */
    @Override
    protected final void initialise() {
        restock();
    }
    /* (non-Javadoc)
     * @see arenas.Arena#setCustomer(agents.Customer)
     */
    @Override
    public final void setCustomer(final Customer c) {
        super.setCustomer(c);
        c.setShopping(true);
    }
    /**
     * Adds products to the shelves, replacing the ones that have been taken.
     * <p>
     * This routine is executed when the arena is initialised, so the items
     * that have been taken are replaced. This serves to provide the same
     * amount of items on each day of simulation.
     */
    private void restock() {
        Database database = Database.getDatabase();
        try {
            System.out.println("Supermarket is restocking...");
            System.out.println("(This may take a while)");
            // SQL to get the list of products
            String query = "SELECT * FROM products";
            ResultSet product = database.getData(query);
            while (product.next()) {
                // Useful variables
                int prodID = product.getInt("product_id");
                int max = product.getInt("max");
                int current = 0;
                // SQL statement for the stock count
                String sql = "SELECT count(*) "
                        + "FROM stock "
                        + "WHERE product_id = " + prodID;
                ResultSet count = database.getData(sql);
                // If there is an output row then we use it for the
                // amount of items of this type in stock
                if (count.next()) {
                    current = count.getInt("count");
                }
                // SQL statement for inserting data
                String values = "DEFAULT,"
                        + "'" + prodID + "',"
                        + "'" + Simulation.getDay() + "'";
                String insert = "INSERT INTO stock VALUES (" + values + ")";
                // Insert as many as needed to fill up the stock
                for (int i = current; i < max; i++) {
                    database.updateData(insert);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    /* (non-Javadoc)
     * @see arenas.Arena#update(int)
     */
    @Override
    public final void update(final int simTime) {
        //if (getCustomerList().size() == 0) return;
        for (Customer c : getCustomerList()) {
            c.update(simTime);
            // Check if customer is ready to move
            if (c.isReady() && !c.isMoving()) {
                // Moving logic: move in the next time-step
                // because customer is ready to move.
                int nextTime = simTime + 1;
                // This will always have only one linked arena.
                Arena destination = getLinkedArenas().get(0);
                Event ev = new CustomerMove(nextTime, c, destination, this);
                ev.schedule();
            } else if (c.isShopping() && !c.isMoving()) {
                // Raise an event for acquiring products from the product list.
                if (c.isThief()) {
                    emptyShoppingList(
                            ((Thief) c).getThiefBaggage().getShoppingList(),
                            simTime, c, true);
                }
                emptyShoppingList(
                        c.getBaggage().getShoppingList(), simTime, c, false);
                c.setShopping(false);
            }
        }
    }
    /**
     * Empties the customers shopping list.
     * <p>
     * @param shoppingList The customers shopping list.
     * @param simTime The current simulation time.
     * @param c The Customer.
     * @param shouldSteal Whether or not we should take the items from the
     * thieves shopping list, or the ordinary list.
     */
    private void emptyShoppingList(
            final List<Integer> shoppingList,
            final int simTime, final Customer c, final boolean shouldSteal) {
        int evTime = simTime;
        Database db = Database.getDatabase();
        List<Integer> unavailable = new ArrayList<Integer>();
        while (!shoppingList.isEmpty()) {
            int productID = shoppingList.remove(0);
            String query = "SELECT product_id, stock_id "
                    + "FROM stock "
                    + "WHERE product_id = " + productID;
            ResultSet rs = db.getData(query);
            try {
                if (rs.next()) {
                    int prodID = rs.getInt("product_id");
                    int stockID = rs.getInt("stock_id");
                    Event ev;
                    if (shouldSteal) {
                        ev = new StealProduct(evTime++, c, prodID);
                    } else {
                        ev = new AcquireProduct(evTime++, c, prodID);
                    }
                    ev.schedule();
                    String remove = "DELETE FROM stock "
                                  + "WHERE stock_id = " + stockID;
                    Database.getDatabase().updateData(remove);
                } else {
                    unavailable.add(productID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        shoppingList.addAll(unavailable);
    }
}
