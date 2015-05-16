package TicketBooking;

/**
 * @author Piravin Satkunarajah
 */
import java.sql.*;
/*
 * test a database installation by creating and querying the tables from the
 * database
 */
public class TestDB {

    /**
     * Creates a new instance of TestDB
     */

    public static void main (String[] args) throws Exception {
        try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt = conn.createStatement();

            //Select SQL Statement
            ResultSet result = stmt.executeQuery("SELECT * from STANDARDINFO");
            result.next();

            System.out.println(result.getString("TICKET_TYPE"));
            result.close();
            conn.close();
        }
        catch (Exception ex)
        {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
    }

}
