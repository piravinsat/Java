package TicketBooking;

/**
 *@author Piravin Satkuanarajah
 */

/**
 * Include libraries
 */

import java.sql.*;
import java.io.*;
import java.util.*;

public class DatabaseConnect
{
  /**
   * Variables used
   */

  private static String url;
  private static String username;
  private static String password;

  /**
   * Define link to name database
   * @throws ClassNotFoundException
   */

  public static void init() throws ClassNotFoundException
  {
   url = "jdbc:derby://localhost:1527/ticket_booking";
   username = "nbuser";
   password = "nbuser";

   /**
    * Links to the driver for Java DB (JDBC)
    */

   Class.forName("org.apache.derby.jdbc.ClientDriver");
  }

  public static Connection getConnection() throws SQLException
  {
      /**
       * Link to database
       */
   return DriverManager.getConnection(url, username, password);
  }
}
