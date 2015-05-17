package agents;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

/**
 * Class that describes a product to be purchased by a customer.
 * @author Lewis Chun
 */
public class Product {

    /**
     * This product's commercial name.
     */
    private String name;
    /**
     * This product's price.
     * <p>
     * The amount of money that the store will take from the Customer
     * as payment for this product.
     */
    private double price;
    /**
     * The cost of the product.
     * <p>
     * The amount of money that the store had to pay to acquire this
     * product.
     */
    private double cost;
    /**
     * Number Identifier of this product.
     * <p>
     * Determines which type of product this is.
     */
    private int productID;
    /**
     * Creates a new Product object, based on its product ID.
     * <p>
     * This constructor will look up on the database for the remaining
     * details of this product in order to instantiate the fields.
     * <p>
     * @param pID the ID of the type of product
     */
    public Product(final int pID) {
        productID = pID;
        try {
            Database db = Database.getDatabase();
            String query = "SELECT * "
                         + "FROM products "
                         + "WHERE product_id = " + productID;
            ResultSet rs = db.getData(query);
            while (rs.next()) {
                name = rs.getString("product_name");
                price = rs.getDouble("price");
                cost = rs.getDouble("cost");
            }
        } catch (SQLException e) {
            return;
        }
    }

    /**
     * Returns the number that identifies this type of product.
     * <p>
     * Products of similar types will have the same values for productID, as
     * this value serves to identify the properties of the products that belong
     * under this category.
     * <p>
     * @return The ID of this type of product.
     */
    public final int getProductID() {
        return productID;
    }

    /**
     * Returns the commercial name of this product.
     * <p>
     * @return The name of the product
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     * <p>
     * Price is the amount of money that the customer has to pay in order to
     * acquire this product.
     * <p>
     * @return Gets the price of this product.
     */
    public final double getPrice() {
        return price;
    }

    /**
     * Returns the cost of the product.
     * <p>
     * Cost is the amount of money that the store had to pay in order to acquire
     * this product.
     * <p>
     * @return Get the cost of this product.
     */
    public final double getCost() {
        return cost;
    }
}
