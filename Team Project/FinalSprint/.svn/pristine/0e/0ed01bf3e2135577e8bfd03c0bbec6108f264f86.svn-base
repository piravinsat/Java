package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents the object that interacts with the Database Management System
 * via the JDBC driver.
 * <p>
 * Objects of this class are able to connect to a certain database - specified
 * in the constructor when the object is created - and then queries may be
 * executed on that database.
 * <p>
 * @author Luigi Pardey
 * @see Connection
 * @see ResultSet
 * @see Statement
 */
public class Database {
    /**
     * Instance of the latest connection to the database.
     */
    private static Database database;
    /**
     * The connection object for the database.
     */
    private Connection connection;
    /**
     * The statement object for the database.
     */
    private Statement statement;
    /**
     * Creates a new Database object.
     * <p>
     * When an object of this type is instantiated, it connects to a specific
     * database, given by the string parameter <code>dbName</code>
     * @param dbName the name of the database to connect to.
     */
    public Database(final String dbName) {
        /* Changed the port number. The DB only runs on teaching, 
         * so the IP stays localhost (127.0.0.1), right?
         * Perhaps it will be necessary to get those as parameters.
         */
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:30187/" + dbName);
            database = this;
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Error: Couldn't connect to database");
        }
    }
    /**
     * Returns the object that contains the information about the connection
     * to the database.
     * <p>
     * This object is used for referring to which database will be acted upon,
     * as seen in the documentation.
     * <p>
     * @return the connection to the database.
     * @see Connection
     */
    public final Connection getConnection() {
        return connection;
    }
    /**
     * Returns the set of rows that result from a database query.
     * <p>
     * Once a database is queried using a <code>Statement</code>, if the query
     * produces results, these will be ordered into a table according to the
     * order the database provides them. In SELECT queries, the columns of the
     * table correspond to the keys that were specified in the query.
     * <p>
     * @param query the SQL query to execute.
     * @return the set of results from the query.
     * @see ResultSet
     * @see Statement.executeQuery
     */
    public final ResultSet getData(final String query) {
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            /* TODO find a way to get around the error, if necessary.
             * ________________________
             * that should give us the error message in format:
             * SQLState: errorcode
             * Error Code: errorcode
             * Message: 'DB command' cannot be performed on
             * 'DB.table' reason.
             * ----------------------------
             * Taken from: http://
             * docs.oracle.com/javase/tutorial/jdbc/basics/sqlexception.html
             * Totally change it back if it messes something up or if it
             * is more complicated than needed.
             */

            e.printStackTrace(System.err);
            System.err.println("SQLState: " 
                    + ((SQLException) e).getSQLState());

            System.err.println("Error Code: " 
                    + ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            Throwable t = e.getCause();
            while (t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
            return null;
        }
    }

    /**
     * Performs an update operation on the database.
     * <p>
     * Update operations are SQL operations such as INSERT, UPDATE or DELETE.
     * These commands don't return any data from the database as an output, but
     * there is always the risk of a SQLException if the query is improper or
     * the connection to the database can't be established.
     * <p>
     * @param query the SQL update statement.
     * @return (1) the number of rows returned by the statement, (2) 0 if the
     * statement produces no rows, or (3) -1 if there's an error.
     * @see Statement.executeUpdate
     */
    public final int updateData(final String query) {
        try {
            statement = connection.createStatement();
            int code = statement.executeUpdate(query);
            return code;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * Returns the reference to the last Database object created.
     * <p>
     * This method is useful for objects which need to execute queries on the
     * database but can't or don't need to hold the reference as a field.
     * <p>
     * @return the reference to the last database.
     */
    public static final Database getDatabase() {
        return database;
    }
}
