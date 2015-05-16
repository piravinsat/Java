/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChooseFilm.java
 *
 * Created on 06-Dec-2010, 10:18:10
 */

package TicketBooking;

import java.util.Date;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.io.*;
import java.awt.*;
import java.awt.print.*;
import javax.swing.*;


public class Booking extends javax.swing.JFrame {

    //Various date and time formats needed
    Date aDate = new java.util.Date();
    SimpleDateFormat formatter = new SimpleDateFormat("EEEEEEEEE, dd MMM yyyy");
    SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter4 = new SimpleDateFormat("hh:mm");
    SimpleDateFormat formatter5 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter6 = new SimpleDateFormat("EEEEEEEEE");
    String dt = formatter.format(aDate);
    String d = formatter3.format(aDate);
    String t = formatter2.format(aDate);
    String day = formatter6.format(aDate);

    //Creating a model for listFilms and listTimes
    DefaultListModel filmList = new DefaultListModel();
    DefaultListModel timeList = new DefaultListModel();

    //Putting the current date into the 'c' variable
    String c;

    //The quantities picked from the combo boxes into variables
    int StnAduQuantity;
    int StnSenQuantity;
    int StnStuQuantity;
    int StnTeeQuantity;
    int StnChiQuantity;
    int PreAduQuantity;
    int PreSenQuantity;
    int PreStuQuantity;
    int PreTeeQuantity;
    int PreChiQuantity;

    //Total number of seats for each of the five screen in the cinema
    int Scr1TotalSeats = 244;
    int Scr2TotalSeats = 126;
    int Scr3TotalSeats = 169;
    int Scr4TotalSeats = 221;
    int Scr5TotalSeats = 230;

    //Variables for listTicketPrices and tickets
    String priceType;
    String screeningType;
    int familyTicketsNumTotal;
    Double familyTotal = 0.00;

    //Total price of tickets without any discounts
    Double acPrice = 0.00;

    //Super Saver Prices from SQL
    Double StnAdultPriceDub1;
    Double StnSeniorPriceDub1;
    Double StnStudentPriceDub1;
    Double StnTeenPriceDub1;
    Double StnChildPriceDub1;
    Double PreAdultPriceDub1;
    Double PreSeniorPriceDub1;
    Double PreStudentPriceDub1;
    Double PreTeenPriceDub1;
    Double PreChildPriceDub1;

    //Peak Prices from SQL
    Double StnAdultPriceDub2;
    Double StnSeniorPriceDub2;
    Double StnStudentPriceDub2;
    Double StnTeenPriceDub2;
    Double StnChildPriceDub2;
    Double PreAdultPriceDub2;
    Double PreSeniorPriceDub2;
    Double PreStudentPriceDub2;
    Double PreTeenPriceDub2;
    Double PreChildPriceDub2;

    //Quantity x price variables
    Double StnAdultTotalDub;
    Double StnSeniorTotalDub;
    Double StnStudentTotalDub;
    Double StnTeenTotalDub;
    Double StnChildTotalDub;
    Double PreAdultTotalDub;
    Double PreSeniorTotalDub;
    Double PreStudentTotalDub;
    Double PreTeenTotalDub;
    Double PreChildTotalDub;

    //Used for various procedures
    Double totalDub;
    Double discount = 0.00;
    int screenNum;
    int quantity;

    //Used to calculate family ticket prices
    int StnAduQNotFam;
    int StnChiQNotFam;
    int PreAduQNotFam;
    int PreChiQNotFam;
    int StnAduFamily = StnAduQuantity - StnAduQNotFam;
    int StnChiFamily = StnChiQuantity - StnChiQNotFam;
    int PreAduFamily = PreAduQuantity - PreAduQNotFam;
    int PreChiFamily = PreChiQuantity - PreChiQNotFam;

    //Membership points earned
    double totalDub1 = 0.00;
    int getPoints = 0;
    String points = Integer.toString(getPoints);

    //Variables to recieve SQL data from MEMBERSHIP
    long cardNumber = 0;
    int totalPoints = 0;
    int memberID = 0;

    //Used to create ticket booking record and information on the tickets
    String booking_ID;
    int bookingID = 0;
    String vat_Number;
    String screening_ID;
    int totalSeats;
    String ticket_ID;
    String seat_number;

    //To check if either variables has a number more than one
    int stnQuantity;
    int preQuantity;

    //pointSpent is needed so points don't get given by points are spent on the
    //ticket
    boolean pointsSpent = true;

    //Used to send ticket information to the printer
    FileWriter writer;
    PrintWriter out;

    //Check if certain condition has been meet
    boolean filmExist = false;
    boolean memberInput = false;

    //Where the ticket information goes to
    static JTextArea textarea = new JTextArea(10,40);
    static JFrame   window = new JFrame("Print");

    /** Creates new form ChooseFilm */
    public Booking()  {
        initComponents();
        lblDate.setText(dt);
        listStandardPrices();
        listPremierPrices();
        list3DPrices();
        list3DPremierPrices();
        getFirstLastTimes();
        generateFilmList();
   }

   //Puts data from SQL table STANDARDINFO into a table on the interface
   public void listStandardPrices() {
    try{
          //connect to the database
          DatabaseConnect.init();
          Connection conn = DatabaseConnect.getConnection();

          //enable SQL statements to be created and run
          Statement stmt = conn.createStatement();

          //retrieve records from database table
          String query = "SELECT * from STANDARDINFO";
          ResultSet result = stmt.executeQuery(query);

          //retireve the table model - so that rows can be added
          DefaultTableModel model = (DefaultTableModel)tblStandard.getModel();

          //retrieve the column model from the table so columns can be formatted
          TableColumnModel m = tblStandard.getColumnModel();
           
          //each column is formatted in currency
          m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
          m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());

          //move to first record
          if(result != null)
          {
            //loop through all the records retrieved
              while(result.next())
              {
               String ticketType= result.getString("TICKET_TYPE");
               double superSaver= result.getDouble("SUPER_SAVER");
               double peak= result.getDouble("PEAK");

               //This adds a new row to the table. The array
               //Object contains the content of each column
               model.addRow(new Object[]{ticketType, superSaver, peak});
              }
         stmt.close();
         conn.close();
         }
       }
       catch (Exception ex)
       {
         System.err.print("SQLException:");
         System.err.println(ex.getMessage());
       }  
   }

   //Puts data from SQL table PREMIERINFO into a table on the interface
   public void listPremierPrices() {
     try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           //retrieve records from database table
           String query = "SELECT * from PREMIERINFO";
           ResultSet result = stmt.executeQuery(query);

           //retireve the table model - so that rows can be added
           DefaultTableModel model = (DefaultTableModel)tblPremier.getModel();

           //retrieve the column model from the table so columns can be formatted
           TableColumnModel m = tblPremier.getColumnModel();

           //each column is formatted in currency
           m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
           m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());

           //move to first record
           if(result != null)
           {
             //loop through all the records retrieved
               while(result.next())
               {
                String ticketType= result.getString("TICKET_TYPE");
                double superSaver= result.getDouble("SUPER_SAVER");
                double peak= result.getDouble("PEAK");

                //This adds a new row to the table. The array
                //Object contains the content of each column
                model.addRow(new Object[]{ticketType, superSaver, peak});
               }
          stmt.close();
          conn.close();
          }
        }
       catch (Exception ex)
       {
           System.err.print("SQLException:");
           System.err.println(ex.getMessage());
       }
   }

   //Puts data from SQL table THREEDINFO into a table on the interface
   public void list3DPrices() {
 try{
       //connect to the database
       DatabaseConnect.init();
       Connection conn = DatabaseConnect.getConnection();

       //enable SQL statements to be created and run
       Statement stmt = conn.createStatement();

       //retrieve records from database table
       String query = "SELECT * from THREEDINFO";
       ResultSet result = stmt.executeQuery(query);

       //retireve the table model - so that rows can be added
       DefaultTableModel model = (DefaultTableModel)tbl3DFilms.getModel();

       //retrieve the column model from the table so columns can be formatted
       TableColumnModel m = tbl3DFilms.getColumnModel();

       //each column is formatted in currency
       m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
       m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());

       //move to first record
       if(result != null)
       {
         //loop through all the records retrieved
           while(result.next())
           {
            String ticketType= result.getString("TICKET_TYPE");
            double superSaver= result.getDouble("SUPER_SAVER");
            double peak= result.getDouble("PEAK");

            //This adds a new row to the table. The array
            //Object contains the content of each column
            model.addRow(new Object[]{ticketType, superSaver, peak});
           }
      stmt.close();
      conn.close();
      }
    }
   catch (Exception ex)
   {
       System.err.print("SQLException:");
       System.err.println(ex.getMessage());
   }
  }

   //Puts data from SQL table THREEDPREMIERINFO into a table on the interface
   public void list3DPremierPrices() {
   try{
       //connect to the database
       DatabaseConnect.init();
       Connection conn = DatabaseConnect.getConnection();

       //enable SQL statements to be created and run
       Statement stmt = conn.createStatement();

       //retrieve records from database table
       String query = "SELECT * from THREEDPREMIERINFO";
       ResultSet result = stmt.executeQuery(query);

       //retireve the table model - so that rows can be added
       DefaultTableModel model = (DefaultTableModel)tblPremier3D.getModel();

       //retrieve the column model from the table so columns can be formatted
       TableColumnModel m = tblPremier3D.getColumnModel();

       //each column is formatted in currency
       m.getColumn(1).setCellRenderer(NumberRenderer.getCurrencyRenderer());
       m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());

       //move to first record
       if(result != null)
       {
         //loop through all the records retrieved
           while(result.next())
           {
            String ticketType= result.getString("TICKET_TYPE");
            double superSaver= result.getDouble("SUPER_SAVER");
            double peak= result.getDouble("PEAK");

            //This adds a new row to the table. The array
            //Object contains the content of each column
            model.addRow(new Object[]{ticketType, superSaver, peak});
           }
      stmt.close();
      conn.close();
      }
    }
   catch (Exception ex)
   {
       System.err.print("SQLException:");
       System.err.println(ex.getMessage());
   }
   }

   //Gets the time from first and last film today from SCREENINGS table
   //and puts into lblTime1 and lblTime2
   public void getFirstLastTimes()
   {
    //Gets the date today and puts into c
    Date currentDay = new java.util.Date();
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    c = formatter1.format(currentDay);

    try{
    //connect to the database
    DatabaseConnect.init();
    Connection conn = DatabaseConnect.getConnection();

    //enable SQL statements to be created and run
    Statement stmt= conn.createStatement();

    //create and execute query
    String query = "SELECT STIME FROM SCREENINGS WHERE SDATE = '"+ c + "' ORDER BY STIME";
    ResultSet result = stmt.executeQuery(query);

    //get the time for the first film of the day
    if (result.next())
    {
        lblTime1.setText(result.getTime("STIME").toString());
    }

    //get the time for the last film of the day by the looping through the result
    //till it gets to the last result
    while (result != null)
    {
     lblTime2.setText(result.getTime("STIME").toString());
     result.next();
    }

    stmt.close();
    conn.close();
    }
     catch (Exception ex)
      {
       System.err.print("SQLException: ");
       System.err.println(ex.getMessage());
      }
   }

   //When the search button on the welcome screen is pressed, this procedure
   //searchs the FILMS table using the data inputted from txtSearchFilm
   public void searchFilms()
   {
    try{
        //connect to the database
        DatabaseConnect.init();
        Connection conn = DatabaseConnect.getConnection();

        //enable SQL statements to be created and run
        Statement stmt= conn.createStatement();

        //retrieve login fields from interface
        String film = txtSearchFilm.getText();

        //create and execute query

        String query = "SELECT * from FILMS where FILM_NAME = '"+ film +"'";
        ResultSet result = stmt.executeQuery(query);

       //retireve the table model - so that rows can be added
       DefaultTableModel model = (DefaultTableModel)tblSearchedFilms.getModel();

       //move to first record
       if(result != null)
       {
         //loop through all the records retrieved
           while(result.next())
           {
            filmExist = true;
            String filmName= result.getString("FILM_NAME");
            String BBFC = result.getString("BBFC_CERTIFICATE");
            String director = result.getString("DIRECTOR");
            String runTime= result.getString("RUN_TIME");
            String genre = result.getString("GENRE");
            String filmLanguage = result.getString("FILM_LANGUAGE");

            //This adds a new row to the table. The array
            //Object contains the content of each column
            model.addRow(new Object[]{filmName, BBFC, director, runTime, genre, filmLanguage});
           }

        stmt.close();
        conn.close();
      }
    }
    catch (Exception ex)
    {
     System.err.print("SQLException: ");
     System.err.println(ex.getMessage());
    }

   if(!filmExist)
    {
       lblError.setText("Film does not exist");
    }
   }

   //Retrieves the film names from FILMS table and puts them into listFilms
   //but only if the there are showings in the SCREENINGS table linked to the
   //film are 'Y'
   public void generateFilmList()
   {
       try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //create and execute query

            String query = ("SELECT FILM_NAME FROM FILMS WHERE SHOWING = 'Y'");
            ResultSet result = stmt.executeQuery(query);

            //move to first record
           if(result != null)
           {
             //loop through all the records retrieved and adds the films to the
             //list
               while(result.next())
               {
                String filmName= result.getString("FILM_NAME");
                filmList.addElement(filmName);
               }
            stmt.close();
            conn.close();       
           }
       }
        catch (Exception ex)
        {
         System.err.print("SQLException: ");
         System.err.println(ex.getMessage());
        }
   }

   //Retrieves the dates and times from all screenings in the SCREENINGS
   //table which are linked the film picked earlier from listFilms into
   //listTimes but only if it is on or after today's date
   public void generateTimesList(String filmName)
   {
   String film = filmName;

   try{
        //connect to the database
        DatabaseConnect.init();
        Connection conn = DatabaseConnect.getConnection();

        //enable SQL statements to be created and run
        Statement stmt= conn.createStatement();

        //create and execute query
        String query = ("SELECT SDATE, STIME FROM FILMS, SCREENINGS WHERE FILM_NAME = '" + film + "' AND SCREENINGS.FILM_ID = FILMS.FILM_ID AND SDATE >= '"+ c +"' AND SCREENING_STATUS = 'Y' AND CINEMA_ID = 1 ORDER BY SDATE,STIME");
        ResultSet result = stmt.executeQuery(query);

        //move to first record
       if(result != null)
       {
         //loop through all the records retrieved
           while(result.next())
           {
            String date= result.getString("SDATE");
            String time= result.getString("STIME");
            String dateTime = date + " " + time;
            timeList.addElement(dateTime);
           }
            stmt.close();
            conn.close();
       }
   }
    catch (Exception ex)
    {
        System.err.print("SQLException: ");
               System.err.println(ex.getMessage());
    }
   }

   //Retrieves the film information from the FILMS table and puts them on labels
   //and shows the BBFC certificate depending on the BBFC field in the record
   public void filmInformation(String filmName) {
   try{
        //connect to the database
        DatabaseConnect.init();
        Connection conn = DatabaseConnect.getConnection();

        //enable SQL statements to be created and run
        Statement stmt= conn.createStatement();

        //retrieve login fields from interface
        String film = filmName;

        //create and execute query

        String query = "SELECT * from FILMS where FILM_NAME = '"+ film +"'";
        ResultSet result = stmt.executeQuery(query);

        //move to first record
        if(result != null && result.next())
          {
           String title = result.getString("FILM_NAME");
           String BBFC = result.getString("BBFC_CERTIFICATE");
           String director = result.getString("DIRECTOR");
           String runTime= result.getString("RUN_TIME");
           String genre = result.getString("GENRE");
           String filmLanguage = result.getString("FILM_LANGUAGE");
           String country = result.getString("ORIGIN");
           String distributor = result.getString("DISTRIBUTOR");
           String plot = result.getString("SYNOPSIS");
           String bbfcInformation = result.getString("BBFC_INFO");

           //Sets the right BBFC certificate info depending the BBFC variable
           if (BBFC.equals("PG"))
           {
            lblBBFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_PG.png")));
           }

           if (BBFC.equals("12A"))
           {
             lblBBFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_12A.png")));
           }

           if (BBFC.equals("U"))
           {
             lblBBFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_U.png")));
           }

           if (BBFC.equals("15"))
           {
             lblBBFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_15.png")));
           }

           if (BBFC.equals("18"))
           {
             lblBBFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_18.png")));
           }

           //Puts from the all the data from the variables onto labels
           lblFilmName.setText(title);
           lblGenre.setText(genre);
           lblRunTime.setText(runTime);
           lblDirector.setText(director);
           lblCountry.setText(country);
           lblLanguage.setText(filmLanguage);
           lblDistributor.setText(distributor);
           txtPlot.setText(plot);
           txtBBFCinfo.setText(bbfcInformation);

           //Stops the text pane from being editable
           txtPlot.setEditable(false);
           txtBBFCinfo.setEditable(false);

           stmt.close();
           conn.close();
      }
   }
    catch (Exception ex)
    {
        System.err.print("SQLException: ");
               System.err.println(ex.getMessage());
    }

  }

   //Retrieves the screening dates and times from the films picked earlier from
   //listFilms but only for cinemas that isn't Marble Arch and puts into a table
   public void cinemaTimes(String filmName)
   {
     try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //create and execute query
            String query = "SELECT * from SCREENINGS, CINEMAS, FILMS where FILM_NAME = '"+ filmName +"' AND SCREENINGS.CINEMA_ID = CINEMAS.CINEMA_ID AND SCREENINGS.CINEMA_ID != 1 ";
            ResultSet result = stmt.executeQuery(query);

           //retireve the table model - so that rows can be added
           DefaultTableModel model = (DefaultTableModel)tblTimes.getModel();

           //move to first record
           if(result != null)
           {
             //loop through all the records retrieved
               while(result.next())
               {
                String cinema= result.getString("CINEMA_NAME");
                String date = result.getString("SDATE");
                String time = result.getString("STIME");
                

                //This adds a new row to the table. The array
                //Object contains the content of each column
                model.addRow(new Object[]{cinema, date, time});
               }

            stmt.close();
            conn.close();
        }}
        catch (Exception ex)
        {
            System.err.print("SQLException: ");
                   System.err.println(ex.getMessage());
        }
    }

   //Retrieves the film information from the FILMS table and puts them on labels
   //and shows the BBFC certificate depending on the BBFC field in the record
   //
   //It also restrict what types of ticket can be picked from the BBFC field
   //in the film's record
   public void filmInfoforSeating(String fDate, String fTime, String filmName)
   {
   try{
        //connect to the database
        DatabaseConnect.init();
        Connection conn = DatabaseConnect.getConnection();

        //enable SQL statements to be created and run
        Statement stmt= conn.createStatement();

        //retrieve login fields from interface
        String film = filmName;

        //create and execute query

        String query = "SELECT * from FILMS, SCREENINGS where FILM_NAME = '"+ film +"' AND SDATE = '"+ fDate +"' AND STIME = '"+ fTime +"' AND SCREENINGS.FILM_ID = FILMS.FILM_ID ";
        ResultSet result = stmt.executeQuery(query);

        //move to first record
        if(result != null && result.next())
          {
           String title = result.getString("FILM_NAME");
           String BBFC = result.getString("BBFC_CERTIFICATE");
           String sDate = result.getString("SDATE");
           String sTime = result.getString("STIME");
           String screenNumInfo = result.getString("SCREEN_NUMBER");

           if (BBFC.equals("PG"))
           {
            //Gets from the picture from the hard drive and puts into the label
            lblBBFC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_PG.png")));
           }

           if (BBFC.equals("12A"))
           {
             lblBBFC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_12A.png")));
           }

           if (BBFC.equals("U"))
           {
             lblBBFC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_U.png")));
           }

           if (BBFC.equals("15"))
           {
             lblBBFC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_15.png")));
             //Certain options are hidden so they can't be picked like
             //you can't pick a child ticket for a 15 film
             lblStnChild.setVisible(false);
             lblStnChildPrice.setVisible(false);
             comStnChild.setVisible(false);
             lblPreChild.setVisible(false);
             lblPreChildPrice.setVisible(false);
             comPreChild.setVisible(false);
           }

           if (BBFC.equals("18"))
           {
             lblBBFC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_18.png")));
             lblStnChild.setVisible(false);
             lblStnChildPrice.setVisible(false);
             comStnChild.setVisible(false);
             lblStnTeen.setVisible(false);
             lblStnTeenPrice.setVisible(false);
             comStnTeen.setVisible(false);
             lblPreChild.setVisible(false);
             lblPreChildPrice.setVisible(false);
             comPreChild.setVisible(false);
             lblPreTeen.setVisible(false);
             lblPreTeenPrice.setVisible(false);
             comPreTeen.setVisible(false);
           }

            //Puts data from FILMS into variables into labels
            lblFilmTitle.setText(title);
            lblDate3.setText(sDate);
            lblTime3.setText(sTime);
            lblScreenNum.setText(screenNumInfo);

            stmt.close();
            conn.close();
      }
   }
    catch (Exception ex)
    {
        System.err.print("SQLException: ");
               System.err.println(ex.getMessage());
    }
   }

   //Shows the tickets prices depending on the specific film and time picked
   public void listTicketPrices(String fDate, String fTime, String filmName)
   {
   try{
        //connect to the database
        DatabaseConnect.init();
        Connection conn = DatabaseConnect.getConnection();

        //enable SQL statements to be created and run
        Statement stmt= conn.createStatement();

        String film = filmName;

        String query22 = "SELECT SCREENING_TYPE from FILMS, SCREENINGS where FILM_NAME = '"+ film +"' AND STIME = '"+ fTime + "' AND SDATE = '"+ fDate +"' AND SCREENINGS.FILM_ID = FILMS.FILM_ID";
        ResultSet result22 = stmt.executeQuery(query22);

        //move to first record
        if(result22 != null && result22.next())
        {
         screeningType = result22.getString("SCREENING_TYPE");

        //create and execute query
        String query = "SELECT PRICE_TYPE from FILMS, SCREENINGS where FILM_NAME = '"+ film +"' AND STIME = '"+ fTime + "' AND SDATE = '"+ fDate +"' AND SCREENINGS.FILM_ID = FILMS.FILM_ID ";
        ResultSet result = stmt.executeQuery(query);

        //move to first record
        if(result != null && result.next())
         {
          priceType = result.getString("PRICE_TYPE");

        //Compare the priceType variable to the word Super Saver
        if (priceType.equals("Super Saver"))
        {
          //SQL statement to look for the Super Saver Adult price
          String query2 = "SELECT SUPER_SAVER from STANDARDINFO WHERE TICKET_TYPE = 'Adult' ";
          ResultSet result2 = stmt.executeQuery(query2);

          //Get the result from the query and puts it into a variable
          if(result2.next())
          {
          String StnAdultPrice1 = result2.getString("SUPER_SAVER");

           //Converts String into Double data type
           try
           {
              StnAdultPriceDub1 = Double.parseDouble(StnAdultPrice1);
           }
           catch (Exception e){
           }

           //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnAdultPrice1 = (cf.format(StnAdultPriceDub1));
           lblStnAdultPrice.setText(StnAdultPrice1);
          }

          //SQL statement to look for the Senior Super Saver Price
          String query3 = "SELECT SUPER_SAVER from STANDARDINFO WHERE TICKET_TYPE = 'Senior' ";
          ResultSet result3 = stmt.executeQuery(query3);

          //Get the result from the query and puts it into a variable
          if(result3.next())
          {
           String StnSeniorPrice1 = result3.getString("SUPER_SAVER");

           //Converts String into Double data type
           try
           {
              StnSeniorPriceDub1 = Double.parseDouble(StnSeniorPrice1);
           }
           catch (Exception e){
           }

           //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnSeniorPrice1 = (cf.format(StnSeniorPriceDub1));

           lblStnSeniorPrice.setText(StnSeniorPrice1);
          }

          String query4 = "SELECT SUPER_SAVER from STANDARDINFO WHERE TICKET_TYPE = 'Student' ";
          ResultSet result4 = stmt.executeQuery(query4);

          if(result4.next())
          {
           String StnStudentPrice1 = result4.getString("SUPER_SAVER");

           //Converts String into Double data type
           try
           {
              StnStudentPriceDub1 = Double.parseDouble(StnStudentPrice1);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnStudentPrice1 = (cf.format(StnStudentPriceDub1));

           lblStnStudentPrice.setText(StnStudentPrice1);
          }

          String query5 = "SELECT SUPER_SAVER from STANDARDINFO WHERE TICKET_TYPE = 'Teen' ";
          ResultSet result5 = stmt.executeQuery(query5);

          if(result5.next())
          {
              String StnTeenPrice1 = result5.getString("SUPER_SAVER");

           //Converts String into Double data type
           try
           {
              StnTeenPriceDub1 = Double.parseDouble(StnTeenPrice1);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnTeenPrice1 = (cf.format(StnTeenPriceDub1));

              lblStnTeenPrice.setText(StnTeenPrice1);
          }

          String query6 = "SELECT SUPER_SAVER from STANDARDINFO WHERE TICKET_TYPE = 'Child' ";
          ResultSet result6 = stmt.executeQuery(query6);

          if(result6.next())
           {
            String StnChildPrice1 = result6.getString("SUPER_SAVER");

            //Converts String into Double data type
            try
            {
              StnChildPriceDub1 = Double.parseDouble(StnChildPrice1);
            }
            catch (Exception e){
            }

            //Changes the format from standard to the currency format
            java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
            StnChildPrice1 = (cf.format(StnChildPriceDub1));

            lblStnChildPrice.setText(StnChildPrice1);
           }

          String query7 = "SELECT SUPER_SAVER from PREMIERINFO WHERE TICKET_TYPE = 'Adult' ";
          ResultSet result7 = stmt.executeQuery(query7);

          if(result7.next())
           {
            String PreAdultPrice1 = result7.getString("SUPER_SAVER");

            //Converts String into Double data type
            try
            {
              PreAdultPriceDub1 = Double.parseDouble(PreAdultPrice1);
            }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreAdultPrice1 = (cf.format(PreAdultPriceDub1));

          lblPreAdultPrice.setText(PreAdultPrice1);
          }

          String query8 = "SELECT SUPER_SAVER from PREMIERINFO WHERE TICKET_TYPE = 'Senior' ";
          ResultSet result8 = stmt.executeQuery(query8);

          if(result8.next())
          {
          String PreSeniorPrice1 = result8.getString("SUPER_SAVER");

           //Converts String into Double data type
           try{
              PreSeniorPriceDub1 = Double.parseDouble(PreSeniorPrice1);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreSeniorPrice1 = (cf.format(PreSeniorPriceDub1));

          lblPreSeniorPrice.setText(PreSeniorPrice1);
          }

          String query9 = "SELECT SUPER_SAVER from PREMIERINFO WHERE TICKET_TYPE = 'Student' ";
          ResultSet result9 = stmt.executeQuery(query9);

          if(result9.next())
          {
          String PreStudentPrice1 = result9.getString("SUPER_SAVER");

           //Converts String into Double data type
           try{
              PreStudentPriceDub1 = Double.parseDouble(PreStudentPrice1);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreStudentPrice1 = (cf.format(PreStudentPriceDub1));

           lblPreStudentPrice.setText(PreStudentPrice1);
          }


          String query10 = "SELECT SUPER_SAVER from PREMIERINFO WHERE TICKET_TYPE = 'Teen' ";
          ResultSet result10 = stmt.executeQuery(query10);

          if(result10.next())
          {
          String PreTeenPrice1 = result10.getString("SUPER_SAVER");

           //Converts String into Double data type
           try{
              PreTeenPriceDub1 = Double.parseDouble(PreTeenPrice1);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreTeenPrice1 = (cf.format(PreTeenPriceDub1));

          lblPreTeenPrice.setText(PreTeenPrice1);
          }

          String query11 = "SELECT SUPER_SAVER from PREMIERINFO WHERE TICKET_TYPE = 'Child' ";
          ResultSet result11 = stmt.executeQuery(query11);

          if(result11.next())
          {
          String PreChildPrice1 = result11.getString("SUPER_SAVER");

           //Converts String into Double data type
           try{
              PreChildPriceDub1 = Double.parseDouble(PreChildPrice1);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreChildPrice1 = (cf.format(PreChildPriceDub1));

          lblPreChildPrice.setText(PreChildPrice1);
          }

        }

        //Peak prices
        if (priceType.equals("Peak"))
        {
          String query12 = "SELECT PEAK from STANDARDINFO WHERE TICKET_TYPE = 'Adult' ";
          ResultSet result12 = stmt.executeQuery(query12);

          if(result12.next())
           {
            String StnAdultPrice2 = result12.getString("PEAK");

           //Converts String into Double data type
           try
           {
              StnAdultPriceDub2 = Double.parseDouble(StnAdultPrice2);
           }
           catch (Exception e){
           }

           //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnAdultPrice2 = (cf.format(StnAdultPriceDub2));

            lblStnAdultPrice.setText(StnAdultPrice2);
           }

          String query13 = "SELECT PEAK from STANDARDINFO WHERE TICKET_TYPE = 'Senior' ";
          ResultSet result13 = stmt.executeQuery(query13);

          if(result13.next())
           {
           String StnSeniorPrice2 = result13.getString("PEAK");

           //Converts String into Double data type
           try
           {
              StnSeniorPriceDub2 = Double.parseDouble(StnSeniorPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnSeniorPrice2 = (cf.format(StnSeniorPriceDub2));

          lblStnSeniorPrice.setText(StnSeniorPrice2);
          }

          String query14 = "SELECT PEAK from STANDARDINFO WHERE TICKET_TYPE = 'Student' ";
          ResultSet result14 = stmt.executeQuery(query14);

          if(result14.next())
          {
           String StnStudentPrice2 = result14.getString("PEAK");

           //Converts String into Double data type
           try
           {
              StnStudentPriceDub2 = Double.parseDouble(StnStudentPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnStudentPrice2 = (cf.format(StnStudentPriceDub2));

           lblStnStudentPrice.setText(StnStudentPrice2);
          }

          String query15 = "SELECT PEAK from STANDARDINFO WHERE TICKET_TYPE = 'Teen' ";
          ResultSet result15 = stmt.executeQuery(query15);

          if(result15.next())
          {
              String StnTeenPrice2 = result15.getString("PEAK");

           //Converts String into Double data type
           try
           {
              StnTeenPriceDub2 = Double.parseDouble(StnTeenPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           StnTeenPrice2 = (cf.format(StnTeenPriceDub2));

              lblStnTeenPrice.setText(StnTeenPrice2);
          }

          String query16 = "SELECT PEAK from STANDARDINFO WHERE TICKET_TYPE = 'Child' ";
          ResultSet result16 = stmt.executeQuery(query16);

          if(result16.next())
          {
          String StnChildPrice2 = result16.getString("PEAK");

          //Converts String into Double data type
            try
            {
              StnChildPriceDub2 = Double.parseDouble(StnChildPrice2);
            }
            catch (Exception e){
            }

            //Changes the format from standard to the currency format
            java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
            StnChildPrice2 = (cf.format(StnChildPriceDub2));

          lblStnChildPrice.setText(StnChildPrice2);
          }

          String query17 = "SELECT PEAK from PREMIERINFO WHERE TICKET_TYPE = 'Adult' ";
          ResultSet result17 = stmt.executeQuery(query17);

          if(result17.next())
          {
          String PreAdultPrice2 = result17.getString("PEAK");

           //Converts String into Double data type
            try
            {
              PreAdultPriceDub2 = Double.parseDouble(PreAdultPrice2);
            }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreAdultPrice2 = (cf.format(PreAdultPriceDub2));

          lblPreAdultPrice.setText(PreAdultPrice2);
          }

          String query18 = "SELECT PEAK from PREMIERINFO WHERE TICKET_TYPE = 'Senior' ";
          ResultSet result18 = stmt.executeQuery(query18);

          if(result18.next())
          {
          String PreSeniorPrice2 = result18.getString("PEAK");

           //Converts String into Double data type
           try{
              PreSeniorPriceDub2 = Double.parseDouble(PreSeniorPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreSeniorPrice2 = (cf.format(PreSeniorPriceDub2));

          lblPreSeniorPrice.setText(PreSeniorPrice2);
          }

          String query19 = "SELECT PEAK from PREMIERINFO WHERE TICKET_TYPE = 'Student' ";
          ResultSet result19 = stmt.executeQuery(query19);

          if(result19.next())
          {
          String PreStudentPrice2 = result19.getString("PEAK");

          //Converts String into Double data type
           try{
              PreStudentPriceDub2 = Double.parseDouble(PreStudentPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreStudentPrice2 = (cf.format(PreStudentPriceDub2));

          lblPreStudentPrice.setText(PreStudentPrice2);
          }

          String query20 = "SELECT PEAK from PREMIERINFO WHERE TICKET_TYPE = 'Teen' ";
          ResultSet result20 = stmt.executeQuery(query20);

          if(result20.next())
          {
          String PreTeenPrice2 = result20.getString("PEAK");

           //Converts String into Double data type
           try{
              PreTeenPriceDub2 = Double.parseDouble(PreTeenPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
          java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
          PreTeenPrice2 = (cf.format(PreTeenPriceDub2));

          lblPreTeenPrice.setText(PreTeenPrice2);
          }

          String query21 = "SELECT PEAK from PREMIERINFO WHERE TICKET_TYPE = 'Child' ";
          ResultSet result21 = stmt.executeQuery(query21);

          if(result21.next())
          {
          String PreChildPrice2 = result21.getString("PEAK");

           //Converts String into Double data type
           try{
              PreChildPriceDub2 = Double.parseDouble(PreChildPrice2);
           }
           catch (Exception e){
           }

          //Changes the format from standard to the currency format
           java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
           PreChildPrice2 = (cf.format(PreChildPriceDub2));


          lblPreChildPrice.setText(PreChildPrice2);
          }

         }
        }
      }
      stmt.close();
      conn.close();
      }

    catch (Exception ex)
    {
      System.err.print("SQLException: ");
      System.err.println(ex.getMessage());
    }
   }

   //Calculates the quantity picked, discounts, if seats are already filled
   //additional costs and the total cost of tickets
   public void tickets() {
     
     quantity = StnAduQuantity + StnSenQuantity + StnStuQuantity + StnTeeQuantity + StnChiQuantity + PreAduQuantity + PreSenQuantity + PreStuQuantity + PreTeeQuantity + PreChiQuantity;
     stnQuantity = StnAduQuantity + StnSenQuantity + StnStuQuantity + StnTeeQuantity + StnChiQuantity;
     preQuantity = PreAduQuantity + PreSenQuantity + PreStuQuantity + PreTeeQuantity + PreChiQuantity;

     screenNum = Integer.parseInt(lblScreenNum.getText().toString());

     Double family = 0.00;
     Double family3D = 6.40;
     Double addCosts = 0.00;
     Double subPreDiscount = 0.00;

     //A while loop for not allowing tickets to sold 9 or more or nothing
     if ((quantity >= 9 || quantity == 0))
     {
         lblTicketMessage.setText("You selected too much or no tickets. Please only try to book 9 or less tickets.");

     }

     //A while loop for not allowing both standard and premier tickets to be tickets
     if ((stnQuantity != 0 && preQuantity != 0))
     {
       lblTicketMessage.setText("You can't select both Standard and Premier Tickets");
     }

     //Put all the values selected from the combo boxes onto labels
     lblStnAdultNumTix.setText(comStnAdult.getSelectedItem().toString());
     lblStnSenNumTix.setText(comStnSenior.getSelectedItem().toString());
     lblStnStuNumTix.setText(comStnStudent.getSelectedItem().toString());
     lblStnTeeNumTix.setText(comStnTeen.getSelectedItem().toString());
     lblStnChiNumTix.setText(comStnChild.getSelectedItem().toString());
     lblpreAduNumTix.setText(comPreAdult.getSelectedItem().toString());
     lblpreSenNumTix.setText(comPreSenior.getSelectedItem().toString());
     lblPreStuNumTix.setText(comPreStudent.getSelectedItem().toString());
     lblpreTeeNumTix.setText(comPreTeen.getSelectedItem().toString());
     lblpreChiNumTix.setText(comPreChild.getSelectedItem().toString());

     //Times the quantity and price of tickets purchased
     if(priceType.equals("Super Saver"))
     {
       StnAdultTotalDub = StnAdultPriceDub1*StnAduQuantity;
       StnSeniorTotalDub = StnSeniorPriceDub1*StnSenQuantity;
       StnStudentTotalDub = StnStudentPriceDub1*StnStuQuantity;
       StnTeenTotalDub = StnTeenPriceDub1*StnTeeQuantity;
       StnChildTotalDub = StnChildPriceDub1*StnChiQuantity;
       PreAdultTotalDub = PreAdultPriceDub1*PreAduQuantity;
       PreSeniorTotalDub = PreSeniorPriceDub1*PreSenQuantity;
       PreStudentTotalDub = PreStudentPriceDub1*PreStuQuantity;
       PreTeenTotalDub = PreTeenPriceDub1*PreTeeQuantity;
       PreChildTotalDub = PreChildPriceDub1*PreChiQuantity;

       //Adds up the total of above variables
       totalDub = StnAdultTotalDub + StnSeniorTotalDub + StnStudentTotalDub
       + StnTeenTotalDub + StnChildTotalDub + PreAdultTotalDub
       + PreSeniorTotalDub + PreStudentTotalDub + PreTeenTotalDub
       + PreChildTotalDub;
     }

     //Times the quantity and price of tickets purchased
     if(priceType.equals("Peak"))
     {
       StnAdultTotalDub = StnAdultPriceDub2*StnAduQuantity;
       StnSeniorTotalDub = StnSeniorPriceDub2*StnSenQuantity;
       StnStudentTotalDub = StnStudentPriceDub2*StnStuQuantity;
       StnTeenTotalDub = StnTeenPriceDub2*StnTeeQuantity;
       StnChildTotalDub = StnChildPriceDub2*StnChiQuantity;
       PreAdultTotalDub = PreAdultPriceDub2*PreAduQuantity;
       PreSeniorTotalDub = PreSeniorPriceDub2*PreSenQuantity;
       PreStudentTotalDub = PreStudentPriceDub2*PreStuQuantity;
       PreTeenTotalDub = PreTeenPriceDub2*PreTeeQuantity;
       PreChildTotalDub = PreChildPriceDub2*PreChiQuantity;

       //Adds up total of above
       totalDub = StnAdultTotalDub + StnSeniorTotalDub + StnStudentTotalDub
       + StnTeenTotalDub + StnChildTotalDub + PreAdultTotalDub
       + PreSeniorTotalDub + PreStudentTotalDub + PreTeenTotalDub
       + PreChildTotalDub;
     }

        //Changes the format from standard to the currency format
        java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
        String StnAdultTotal = (cf.format(StnAdultTotalDub));
        String StnSeniorTotal = (cf.format(StnSeniorTotalDub));
        String StnStudentTotal = (cf.format(StnStudentTotalDub));
        String StnTeenTotal = (cf.format(StnTeenTotalDub));
        String StnChildTotal = (cf.format(StnChildTotalDub));

        String PreAdultTotal = (cf.format(PreAdultTotalDub));
        String PreSeniorTotal = (cf.format(PreSeniorTotalDub));
        String PreStudentTotal = (cf.format(PreStudentTotalDub));
        String PreTeenTotal = (cf.format(PreTeenTotalDub));
        String PreChildTotal = (cf.format(PreChildTotalDub));

       //Puts totals into labels
       lblStnAduTotal.setText(StnAdultTotal);
       lblStnSenTotal.setText(StnSeniorTotal);
       lblStnStuTotal.setText(StnStudentTotal);
       lblStnTeeTotal.setText(StnTeenTotal);
       lblStnChiTotal.setText(StnChildTotal);
       lblPreAduTotal.setText(PreAdultTotal);
       lblPreSenTotal.setText(PreSeniorTotal);
       lblPreStuTotal.setText(PreStudentTotal);
       lblPreTeeTotal.setText(PreTeenTotal);
       lblPreChiTotal.setText(PreChildTotal);

       //Put the inital total into the subTotal label
       String showSubTotal = (cf.format(totalDub));
       lblSubTotal.setText(showSubTotal);

       //Family Tickets calculations
       if(priceType.equals("Super Saver"))
        {
         //Cost of a family ticket
         //(Should be changed to retrieve price from SQL table)
         family = 32.00;
        }
        else
        {
         //Cost of a family ticket
         //(Should be changed to retrieve price from SQL table)
         family = 38.00;
        }

       //If quantity is less than or equal 8
       if (quantity <= 8)
       {
           if(StnAduQuantity == 2 && StnChiQuantity == 6)
           {
             //The number of family tickets that can replace the normal tickets
             //selected
             familyTicketsNumTotal = 2;
             //The price of a family ticket times the number of family tickets
             familyTotal = family*familyTicketsNumTotal;
             //The price of tickets normally without family discount
             acPrice = StnAdultPriceDub1*2 + StnChildPriceDub1*6;
             //Discount made from changing tickets
             subPreDiscount = familyTotal - acPrice;
           }

           if(StnAduQuantity == 4 && StnChiQuantity == 4)
           {
               familyTicketsNumTotal = 2;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*4 + StnChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
           }

           if(StnAduQuantity == 3 && StnChiQuantity ==5)
           {
               familyTicketsNumTotal = 2;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*3 + StnChildPriceDub1*5;
               subPreDiscount = familyTotal - acPrice;
           }
           
           if(StnAduQuantity == 6 && StnChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*6 + StnChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;

               //NotFam means leftover tickets which cannot be replaced with
               //family ticket
               StnAduQNotFam = 4;
           }

           if(StnAduQuantity == 5 && StnChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*5 + StnChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               StnAduQNotFam = 3;
               StnChiQNotFam = 1;
           }

           if(StnAduQuantity == 5 && StnChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*5 + StnChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               StnAduQNotFam = 3;
           }

           if(StnAduQuantity == 3 && StnChiQuantity == 4)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*3 + StnChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 2;
               StnAduQNotFam = 1;
           }

           if(StnAduQuantity == 3 && StnChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*3 + StnChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               StnAduQNotFam = 1;
               StnChiQNotFam = 1;
           }

           if(StnAduQuantity == 3 && StnChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*3 + StnChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               StnAduQNotFam = 1;
           }

           if(StnAduQuantity == 4 && StnChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*4 + StnChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               StnAduQNotFam = 1;
               StnChiQNotFam = 1;
           }

           if(StnAduQuantity == 4 && StnChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*4 + StnChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               StnAduQNotFam = 2;
           }

           if(StnAduQuantity == 2 && StnChiQuantity == 5)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*2 + StnChildPriceDub1*5;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 3;
           }

            if(StnAduQuantity == 2 && StnChiQuantity ==4)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*2 + StnChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 2;
           }

             if(StnAduQuantity == 2 && StnChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*2 + StnChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 1;
           }

            if(StnAduQuantity == 1 && StnChiQuantity ==7)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*1 + StnChildPriceDub1*7;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 4;
           }

            if(StnAduQuantity == 1 && StnChiQuantity ==6)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*1 + StnChildPriceDub1*6;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 3;
           }

            if(StnAduQuantity == 1 && StnChiQuantity ==5)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*1 + StnChildPriceDub1*5;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 2;
           }

            if(StnAduQuantity == 1 && StnChiQuantity ==4)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = StnAdultPriceDub1*1 + StnChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
               StnChiQNotFam = 1;
           }

           if(PreAduQuantity == 2 && PreChiQuantity == 6)
           {
               familyTicketsNumTotal = 2;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*2 + PreChildPriceDub1*6;
               subPreDiscount = familyTotal - acPrice;
         }

           if(PreAduQuantity == 4 && PreChiQuantity == 4)
           {
               familyTicketsNumTotal = 2;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*4 + PreChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
           }

           if(PreAduQuantity == 3 && PreChiQuantity ==5)
           {
               familyTicketsNumTotal = 2;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*3 + PreChildPriceDub1*5;
               subPreDiscount = familyTotal - acPrice;
           }

           if(PreAduQuantity == 6 && PreChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*6 + PreChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 4;
           }

           if(PreAduQuantity == 5 && PreChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*5 + PreChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 3;
               PreChiQNotFam = 1;
           }

           if(PreAduQuantity == 5 && PreChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*5 + PreChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 3;
           }

           if(PreAduQuantity == 3 && PreChiQuantity == 4)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*3 + PreChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 2;
               PreAduQNotFam = 1;
           }

           if(PreAduQuantity == 3 && PreChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*3 + PreChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 1;
               PreChiQNotFam = 1;
           }

           if(PreAduQuantity == 3 && PreChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*3 + PreChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 1;
           }

           if(PreAduQuantity == 4 && PreChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*4 + PreChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 1;
               PreChiQNotFam = 1;
           }

           if(PreAduQuantity == 4 && PreChiQuantity ==2)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*4 + PreChildPriceDub1*2;
               subPreDiscount = familyTotal - acPrice;
               PreAduQNotFam = 2;
           }

           if(PreAduQuantity == 2 && PreChiQuantity == 5)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*2 + PreChildPriceDub1*5;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 3;
           }

            if(PreAduQuantity == 2 && PreChiQuantity ==4)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*2 + PreChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 2;
           }

             if(PreAduQuantity == 2 && PreChiQuantity ==3)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*2 + PreChildPriceDub1*3;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 1;
           }

            if(PreAduQuantity == 1 && PreChiQuantity ==7)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*1 + PreChildPriceDub1*7;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 4;
           }

            if(PreAduQuantity == 1 && PreChiQuantity ==6)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*1 + PreChildPriceDub1*6;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 3;
           }

            if(PreAduQuantity == 1 && PreChiQuantity ==5)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*1 + PreChildPriceDub1*5;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 2;
           }

            if(PreAduQuantity == 1 && PreChiQuantity ==4)
           {
               familyTicketsNumTotal = 1;
               familyTotal = family*familyTicketsNumTotal;
               acPrice = PreAdultPriceDub1*1 + PreChildPriceDub1*4;
               subPreDiscount = familyTotal - acPrice;
               PreChiQNotFam = 1;
           }
           //If quantity is more than or equal to 4
           if(quantity >= 4)
           {
               if(StnAduQuantity == 1 && StnChiQuantity == 3)
               {
                   familyTicketsNumTotal = 1;
                   familyTotal = family*familyTicketsNumTotal;
                   subPreDiscount = familyTotal - totalDub;
               }

               if(StnAduQuantity == 2 && StnChiQuantity == 2)
               {
                   familyTicketsNumTotal = 1;
                   familyTotal = family*familyTicketsNumTotal;
                   subPreDiscount = familyTotal - totalDub;
               }

               if(PreAduQuantity == 1 && PreChiQuantity == 3)
               {
                   familyTicketsNumTotal = 1;
                   familyTotal = family*familyTicketsNumTotal;
                   subPreDiscount = familyTotal - totalDub;
               }

               if(PreAduQuantity == 2 && PreChiQuantity == 2)
               {
                   familyTicketsNumTotal = 1;
                   familyTotal = family*familyTicketsNumTotal;
                   subPreDiscount = familyTotal - totalDub;
               }
           }
       }

       //If no family ticket can be allocated then standard quantity is put
       //into NotFam group of variables
       if(familyTicketsNumTotal == 0)
       {
           StnAduQNotFam = StnAduQuantity;
           StnChiQNotFam = StnChiQuantity;
           PreAduQNotFam = PreAduQuantity;
           PreChiQNotFam = PreChiQuantity;
       }

       discount = subPreDiscount;

       //If screening is a 3D film then additional costs are added
       if (screeningType.equals("3D"))
       {
        familyTotal = familyTotal + family3D*familyTicketsNumTotal;
        addCosts = family3D*familyTicketsNumTotal;
       }

       if(screeningType.equals("3D"))
       {
        addCosts = addCosts + 2.00*StnAduQNotFam + 1.60*StnSenQuantity + 1.60*StnStuQuantity + 1.60*StnTeeQuantity + 1.60*StnChiQNotFam + 2.00*PreAduQNotFam + 2.00*PreSenQuantity + 1.60*PreStuQuantity + 1.60*PreTeeQuantity + 1.60*PreChiQNotFam;
        
        //Convert double to string
        String showAddCosts = Double.toString(addCosts);
        lblAddCosts.setText(showAddCosts);
       }

       String showQuantity = Integer.toString(quantity);
       lblTotalNumTix.setText(showQuantity);

       //Adding up total price of tickets with discount and additional costs
       totalDub = totalDub + discount;
       totalDub = totalDub + addCosts;

       //Convert price into points for addPoints procedure
       totalDub1 = totalDub;
       long myLong = Math.round(totalDub1);
       System.out.println(myLong);
       getPoints = (int)myLong*10;
       points = Integer.toString(getPoints);

       //Show costs and discount on the interface
       String showTotal = (cf.format(totalDub));
       lblTotalPrice.setText(showTotal);
       String showAddCostsFull = (cf.format(addCosts));
       lblAddCosts.setText(showAddCostsFull);
       String showDiscountsFull = (cf.format(discount));
       lblDiscount.setText(showDiscountsFull);
   }

   //Creates a booking record and many ticket records depending on the quantity
   public void createTicketBookingRecord(String fDate, String fTime, String filmName)
   {
       //Variable used for loops
       int af;
       
       //Get the booking id and VAT number
          try
          {
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //retrieve filmName variable and put into film
            String film = filmName;

            //create and execute query
            String query = "SELECT * from BOOKINGS ORDER BY BOOKING_ID";
            ResultSet result = stmt.executeQuery(query);

           //loop through until goes to last record
            if (result != null)
           {
                while (result.next()){
                booking_ID = result.getString("BOOKING_ID");
                vat_Number = result.getString("VAT_NUMBER");
            }
          }

         //create and execute new query
         String query2 = "SELECT SCREENING_ID from FILMS, SCREENINGS where FILM_NAME = '"+ film +"' AND STIME = '"+ fTime + "' AND SDATE = '"+ fDate +"' AND SCREENINGS.FILM_ID = FILMS.FILM_ID ";
         result = stmt.executeQuery(query2);

        //find the screening ID
        if(result != null && result.next())
        {
            screening_ID = result.getString("SCREENING_ID");
        }

       //Convert values from an string to an integer and put these into new variables
       bookingID = Integer.parseInt(booking_ID);
       int vatNumber = Integer.parseInt(vat_Number);

       //Create a new booking record
       bookingID = bookingID + 1;
       booking_ID = Integer.toString(bookingID);
       int cashierNumber = 1;
       memberID = 1;
       Double total = totalDub;

       String printDate = d;
       String printTime = t;

       vatNumber = vatNumber + 1;
       int quantitySQL = quantity;
       char orangeWednesday = 'N';
       char cancelled = 'N';
       int screeningID = Integer.parseInt(screening_ID);

       //execute insertion to bookings
       stmt.execute("INSERT INTO BOOKINGS (BOOKING_ID, CASHIER_NUMBER, MEMBER_ID, TOTAL, PRINT_DATE, PRINT_TIME, VAT_NUMBER, QUANTITY, ORANGE_WEDNESDAYS, CANCELLED, SCREENING_ID, PRINTED) VALUES "
      + "(" + bookingID + ","+ cashierNumber + ","+ memberID +","+ total +",{d '"+ printDate + "'},{t '"+ printTime +"'},"+ vatNumber +","+ quantitySQL +",'"+ orangeWednesday +"','"+ cancelled +"',"+ screeningID +",'N')");

       //To check seats have already been filled along the screening status
       if(screenNum == 1)
       {           
        totalSeats = Scr1TotalSeats;
       }
       if(screenNum == 2)
       {
         totalSeats = Scr2TotalSeats;
       }
       if(screenNum ==3)
       {
         totalSeats = Scr3TotalSeats;
       }
       if(screenNum ==4)
       {
         totalSeats = Scr4TotalSeats;
       }
       if(screenNum ==5)
       {
         totalSeats = Scr5TotalSeats;
       }

       //Check from tickets how many tickets have booked
       int seatsTaken = 0;

       String query3 = "SELECT * from TICKETS, BOOKINGS WHERE TICKETS.BOOKING_ID = BOOKINGS.BOOKING_ID AND BOOKINGS.SCREENING_ID = "+ screeningID +" ORDER BY SEAT_NUMBER";
       result = stmt.executeQuery(query3);

            if (result != null)
           {
                while (result.next()) {
                seatsTaken++;
                result.next();
                }
          }

        if (seatsTaken == totalSeats)
        {
         lblMessage.setText("All tickets sold out");
        }

       //Retrieves the values from Tickets so to increment by one.
       String query4 = "SELECT TICKET_ID from TICKETS ORDER BY TICKET_ID";
       result = stmt.executeQuery(query4);

           //move to first record
            if (result != null)
           {
            while(result.next())
            {
            ticket_ID = result.getString("TICKET_ID");
            }
           }

       //Gets the last seat number of the screening
       String query5 = "SELECT SEAT_NUMBER from TICKETS, BOOKINGS, SCREENINGS WHERE BOOKINGS.SCREENING_ID = SCREENINGS.SCREENING_ID ORDER BY SEAT_NUMBER";
       result = stmt.executeQuery(query5);

          if (result != null)
           {
            while(result.next())
            {
            seat_number = result.getString("SEAT_NUMBER");
            }
           }

       int ticketID = Integer.parseInt(ticket_ID);
       int seatNumber = Integer.parseInt(seat_number);

       //Loops here for Inserting each ticket record
       //One big loop using the quantity
       //If statement if tickets are standard or premier
       //Then loops inside for each type and quantity

       char rowLetter = 'A';

            for (af = StnAduFamily; af>0; af--)
            {
              String ticketTypeID = "FAMILY";
              ticketID++;
              seatNumber++;
              stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
              +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

            for(int cf = StnChiFamily; cf>0; cf--)
            {
             String ticketTypeID = "FAMILY";
             ticketID++;
             seatNumber++;
             stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
             +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

            for(int a = StnAduQNotFam; a>0; a--)
            {
             String ticketTypeID = "ADULT";
             ticketID++;
             seatNumber++;
             stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
             +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

            for(int ch = StnChiQNotFam; ch>0; ch--)
            {
             String ticketTypeID = "CHILD";
             ticketID++;
             seatNumber++;
             stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
             +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

            for(int se = StnSenQuantity; se>0; se--)
            {
             String ticketTypeID = "SENIOR";
             ticketID++;
             seatNumber++;
             stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
             +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

            for(int st = StnStuQuantity; st>0; st--)
            {
             String ticketTypeID = "STUDENT";
             ticketID++;
             seatNumber++;
             stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
             +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

            for(int te = StnTeeQuantity; te>0; te--)
            {
             String ticketTypeID = "TEEN";
             ticketID++;
             seatNumber++;
             stmt.execute("INSERT INTO TICKETS (TICKET_ID, BOOKING_ID, ROW_LETTER, SEAT_NUMBER, TICKET_TYPE_ID) VALUES"
             +"("+ ticketID +","+ bookingID +",'"+ rowLetter +"',"+ seatNumber +",'"+ ticketTypeID +"')");
            }

               if (seatsTaken == totalSeats)
               {
                 String query6 = ("UPDATE SCREENINGS SET = 'N' WHERE SCREENING_STATUS  "+ screeningID +"");
                 lblMessage.setText("All tickets sold out");
               }
                     
            stmt.close();
            conn.close();
       }
        catch (Exception ex)
        {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
       
   }

   //Puts film and screening information from earlier labels into the confirm
   //screen and also hides the orange wednesday panel if it's not a Wednesday
   public void confirmation()
   {
       lblConfirmFilmName.setText(lblFilmName.getText());
       lblConfirmBBFC.setIcon(lblBBFC3.getIcon());
       lblConfirmDate.setText(lblDate3.getText());
       lblConfirmTime.setText(lblTime3.getText());
       lblConfirmScrNum.setText(lblScreenNum.getText());
       lblPoints.setText(points);

       if(day.equals("Wednesday"))
       {
           panOW.setVisible(false);
       }        
   }

   //Retrieves the membership record from MEMBERSHIP table using the card num.
   //Put the total points field into a label
   public void getTotalPoints()
   {
          try
          {
            String cardNumberString = txtCardNumber.getText();         
            cardNumber = Long.parseLong(cardNumberString);

            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //create and execute query

            String query = "SELECT * FROM MEMBERSHIP WHERE CARD_NUMBER = "+ cardNumber + "";
            ResultSet result = stmt.executeQuery(query);

            //get the time for the first film of the day
            if (result != null && result.next())
            {
               memberID = result.getInt("MEMBER_ID");
               totalPoints = result.getInt("TOTAL_POINTS");
               lblTotalPoints.setText(Integer.toString(totalPoints));
               memberInput = true;
            }
            else
            {
               lblConfirmMessage.setText("Member not found");
            }

            stmt.close();
            conn.close();
        }
        catch (Exception ex)
        {
          System.err.print("SQLException: ");
          System.err.println(ex.getMessage());
        }
   }

   //If users wish points can be spent to reduce the price of the tickets
   //Updates the record with the new total number of points
   public void spendPoints()
   {
        int freeTicketsQ = 0;
        boolean ifDone = false;

        String spendPointsString = txtSpendPoints.getText();
        int spendPoints = Integer.parseInt(spendPointsString);

        //Reduces the total points from how much is being spent
        int newTotalPoints = totalPoints - spendPoints;

        if(screeningType.equals("Standard"))
        {
         if(spendPoints >= 1000)
        {
            //Determines how much free tickets can be recieved
            freeTicketsQ = spendPoints/1000;

            //Loops until freeTicketsQ is less than one
            while(freeTicketsQ > 0)
            {
                ifDone = false;

            //Protity goes more expensive tickets to the cheapest
            if (PreAduQuantity >= 1 && ifDone == false)
            {
                //Reduces the total price by the price of the ticket
                totalDub = totalDub - PreAdultPriceDub1;
                //Reduces the total of adult tickets by the price of the ticket
                PreAdultTotalDub = PreAdultTotalDub - PreAdultPriceDub1;
                //Decrements the freeTicketsQ variable
                freeTicketsQ--;
                ifDone = true;
            }

            if (PreSenQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreSeniorPriceDub1;
                PreSeniorTotalDub = PreSeniorTotalDub - PreSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (PreStuQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreStudentPriceDub1;
                PreStudentTotalDub = PreStudentTotalDub - PreSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (PreTeeQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreTeenPriceDub1;
                PreTeenTotalDub = PreTeenTotalDub - PreTeenPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
            if(PreChiQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreChildPriceDub1;
                PreChildTotalDub = PreChildTotalDub - PreChildPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
          }
         }

        if(spendPoints >= 800)
        {
            freeTicketsQ = spendPoints/800;

            while(freeTicketsQ > 0)
            {
                ifDone = false;

            if (StnAduQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnAdultPriceDub1;
                StnAdultTotalDub = StnAdultTotalDub - StnAdultPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (StnSenQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnSeniorPriceDub1;
                StnSeniorTotalDub = StnSeniorTotalDub - StnSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (StnStuQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnStudentPriceDub1;
                StnStudentTotalDub = StnStudentTotalDub - StnSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (StnTeeQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnTeenPriceDub1;
                StnTeenTotalDub = StnTeenTotalDub - StnTeenPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
            if(StnChiQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnChildPriceDub1;
                StnChildTotalDub = StnChildTotalDub - StnChildPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
          }
         }
        }

        //If it's a 3D film
            if(screeningType.equals("3D"))
            {
            if(spendPoints >= 1300)
            {
            freeTicketsQ = spendPoints/1300;

            while(freeTicketsQ > 0)
            {
                ifDone = false;

            if (PreAduQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreAdultPriceDub1;
                PreAdultTotalDub = PreAdultTotalDub - PreAdultPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (PreSenQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreSeniorPriceDub1;
                PreSeniorTotalDub = PreSeniorTotalDub - PreSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (PreStuQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreStudentPriceDub1;
                PreStudentTotalDub = PreStudentTotalDub - PreSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (PreTeeQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreTeenPriceDub1;
                PreTeenTotalDub = PreTeenTotalDub - PreTeenPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
            if(PreChiQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - PreChildPriceDub1;
                PreChildTotalDub = PreChildTotalDub - PreChildPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
          }
         }

        if(spendPoints >= 1100)
        {
            freeTicketsQ = spendPoints/1100;

            while(freeTicketsQ > 0)
            {
                ifDone = false;

            if (StnAduQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnAdultPriceDub1;
                StnAdultTotalDub = StnAdultTotalDub - StnAdultPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (StnSenQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnSeniorPriceDub1;
                StnSeniorTotalDub = StnSeniorTotalDub - StnSeniorPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (StnStuQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnStudentPriceDub1;
                StnStudentTotalDub = StnStudentTotalDub - StnStudentPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }

            if (StnTeeQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnTeenPriceDub1;
                StnTeenTotalDub = StnTeenTotalDub - StnTeenPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
            if(StnChiQuantity >= 1 && ifDone == false)
            {
                totalDub = totalDub - StnChildPriceDub1;
                StnChildTotalDub = StnChildTotalDub - StnChildPriceDub1;
                freeTicketsQ--;
                ifDone = true;
            }
          }
         }
        }

        try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            stmt.execute("UPDATE MEMBERSHIP SET TOTAL_POINTS = "+ newTotalPoints +" WHERE MEMBER_ID = "+ memberID +" ");

            stmt.close();
            conn.close();
          }
        catch (Exception ex)
        {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
   }

   //If spendPoints procedure hasn't be called then points are added to total
   //points and the membership record is updated
   public void addPoints()
   {
       int newTotalPoints = totalPoints + getPoints;

       if ((pointsSpent = false)&&(memberInput = true))
       {
        try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

        stmt.execute("UPDATE MEMBERSHIP SET TOTAL_POINTS = "+ newTotalPoints +" WHERE MEMBER_ID = "+ memberID +" ");

            stmt.close();
            conn.close();
        }
        catch (Exception ex)
        {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
        }
       }
   }

   //Subtracts the price of one ticket from the price of another ticket
   //to make the new total
   public void orangeWednesday()
   {
       boolean done = false;
       if(quantity == 2)
       {
          if(StnAduQuantity >=1 && done == false)
          {
            totalDub = totalDub - StnAdultPriceDub1;
            StnAdultTotalDub = StnAdultTotalDub - StnAdultPriceDub1;
            done = true;
          }
          if(StnStuQuantity >=1 && done == false)
          {
              totalDub = totalDub - StnStudentPriceDub1;
              StnStudentTotalDub = StnStudentTotalDub - StnStudentPriceDub1;
              done = true;
          }
          if(StnSenQuantity >=1 && done == false)
          {
              totalDub = totalDub - StnSeniorPriceDub1;
              StnSeniorTotalDub = StnSeniorTotalDub - StnSeniorPriceDub1;
              done = true;
          }
          if(StnTeeQuantity >=1 && done == false)
          {
              totalDub = totalDub - StnTeenPriceDub1;
              StnTeenTotalDub = StnTeenTotalDub - StnTeenPriceDub1;
              done = true;
          }
          if(StnChiQuantity >=1 && done == false)
          {
              totalDub = totalDub - StnChildPriceDub1;
              StnChildTotalDub = StnChildTotalDub - StnChildPriceDub1;
              done = true;
          }
          if(PreAduQuantity >=1 && done == false)
          {
            totalDub = totalDub - PreAdultPriceDub1;
            PreAdultTotalDub = PreAdultTotalDub - PreAdultPriceDub1;
            done = true;
          }
          if(PreStuQuantity >=1 && done == false)
          {
              totalDub = totalDub - PreStudentPriceDub1;
              PreStudentTotalDub = PreStudentTotalDub - PreStudentPriceDub1;
              done = true;
          }
          if(PreSenQuantity >=1 && done == false)
          {
              totalDub = totalDub - PreSeniorPriceDub1;
              PreSeniorTotalDub = PreSeniorTotalDub - PreSeniorPriceDub1;
              done = true;
          }
          if(PreTeeQuantity >=1 && done == false)
          {
              totalDub = totalDub - PreTeenPriceDub1;
              PreTeenTotalDub = PreTeenTotalDub - PreTeenPriceDub1;
              done = true;
          }
          if(PreChiQuantity >=1 && done == false)
          {
              totalDub = totalDub - PreChildPriceDub1;
              PreChildTotalDub = PreChildTotalDub - PreChildPriceDub1;
              done = true;
          }
          lblOWmessage.setText("Discount Applied");
       }
       else
       {
           lblOWmessage.setText("Quantity has to be two.");
       }
   }

   //Shows the new total and prices after going
   //through orangeWednesday and spendPoints procedures
   public void showPricesConfirm()
   {
       java.text.NumberFormat cf = NumberFormat.getCurrencyInstance();
       
       String AdultTotal;
       String SeniorTotal;
       String StudentTotal;
       String TeenTotal;
       String ChildTotal;

       if(stnQuantity != 0){
           lblAdultNumTixCon.setText(lblStnAdultNumTix.getText());
           lblSenNumTixCon.setText(lblStnSenNumTix.getText());
           lblStuNumTixCon.setText(lblStnStuNumTix.getText());
           lblTeeNumTixCon.setText(lblStnTeeNumTix.getText());
           lblChiNumTixCon.setText(lblStnChiNumTix.getText());

           //Converts the string format into currency
           AdultTotal = (cf.format(StnAdultTotalDub));
           SeniorTotal = (cf.format(StnSeniorTotalDub));
           StudentTotal = (cf.format(StnStudentTotalDub));
           TeenTotal = (cf.format(StnTeenTotalDub));
           ChildTotal = (cf.format(StnChildTotalDub));

           lblTeeTotalCon.setText(StnTeenTotalDub.toString());
           lblChiTotalCon.setText(StnChildTotalDub.toString());
       }
       else
       {
           lblAdultNumTixCon.setText(lblpreAduNumTix.getText());
           lblSenNumTixCon.setText(lblpreSenNumTix.getText());
           lblStuNumTixCon.setText(lblPreStuNumTix.getText());
           lblTeeNumTixCon.setText(lblpreTeeNumTix.getText());
           lblChiNumTixCon.setText(lblpreChiNumTix.getText());

           //Converts the string into the currency format
           AdultTotal = (cf.format(PreAdultTotalDub));
           SeniorTotal = (cf.format(PreSeniorTotalDub));
           StudentTotal = (cf.format(PreStudentTotalDub));
           TeenTotal = (cf.format(PreTeenTotalDub));
           ChildTotal = (cf.format(PreChildTotalDub));
       }

           //New totals are shown on the confirmation screen
           lblAduTotalCon.setText(AdultTotal);
           lblSenTotalCon.setText(SeniorTotal);
           lblStuTotalCon.setText(StudentTotal);
           lblTeeTotalCon.setText(TeenTotal);
           lblChiTotalCon.setText(ChildTotal);

           lblTotalNumTixCon.setText(Integer.toString(quantity));
           String total = (cf.format(totalDub));
           lblTotalPriceCon.setText(total);     
   }

   //Retrieves the ticket records made earlier in createTicketBookingRecord
   //as well as film and screening information which is used by printTickets
   //procedure called here
   public void getTickets()
   {
       String filmName = "";
       String rating = "";
       String totalPrice = "";
       String sdate = "";
       String stime = "";
       String type = "";
       String ticketID = "";

       try {
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();
           
           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           stmt.execute("UPDATE BOOKINGS SET PRINTED = 'Y' WHERE BOOKING_ID = "+ bookingID +"");
           
           //create and execute new query
           String query = "SELECT * from FILMS, SCREENINGS, BOOKINGS WHERE BOOKING_ID = "+ bookingID +" AND BOOKINGS.SCREENING_ID = SCREENINGS.SCREENING_ID AND FILMS.FILM_ID = SCREENINGS.FILM_ID ";
           ResultSet result = stmt.executeQuery(query);

           //find the screening ID
           if(result != null && result.next())
           {
            filmName = result.getString("SHORT_NAME");
            rating = result.getString("BBFC_CERTIFICATE");
            totalPrice = result.getString("TOTAL");
            sdate = result.getString("SDATE");
            stime = result.getString("STIME");
           }

           String query2 = "SELECT TICKET_ID from TICKETS WHERE BOOKING_ID = "+bookingID +"";
           result = stmt.executeQuery(query2);

           if(result != null && result.next())
           {
             ticketID = result.getString("TICKET_ID");
           }

           int tixID = Integer.parseInt(ticketID);

           //Keeps on calling printTickets to print off each tickets until q is
           //zero and increments ticketID.
           for(int q = quantity; q>0; q--)
           {
              printTickets(filmName, rating, totalPrice, sdate, stime,type,tixID);
              tixID++;
           }
          }
          catch (Exception ex)
          {
           System.err.print("SQLException:");
           System.err.println(ex.getMessage());
          }
        }

   //Sends ticket information to the printer so the ticket will be printed
   public void printTickets(String filmName, String rating, String totalPrice, String sdate, String stime, String type, int tixID)
   {
    try{

        //connect to the database
       DatabaseConnect.init();
       Connection conn = DatabaseConnect.getConnection();

       //enable SQL statements to be created and run
       Statement stmt = conn.createStatement();


       final Container cp = window.getContentPane();
       JButton buttonPJ = new JButton("Print Tickets");
       LayoutManager lm = new FlowLayout(FlowLayout.CENTER, 20,20);

       String query = "SELECT * from TICKETS WHERE TICKET_ID = "+ tixID +" ";
       ResultSet result = stmt.executeQuery(query);

    //find the screening ID
    while(result != null && result.next())
    {
     type = result.getString("TICKET_TYPE_ID");
    }

    // fill in text to display (and later print)
    textarea.append("           ODEON       \n");
    textarea.append(" FANATICAL ABOUT FILM\n");
    textarea.append("\n");
    textarea.append("         Marble Arch      ");
    textarea.append("\n");
    textarea.append("         SCREEN: " + screenNum + "\n");
    textarea.append("\n");
    textarea.append(" " + filmName + "\n");
    textarea.append("     RATING: " + rating + "\n");
    textarea.append("        Standard\n");
    textarea.append( sdate  +" "+ stime + "\n");
    textarea.append("     ROW         " + "SEAT\n");
    textarea.append("     --           " + "--\n");
    textarea.append(type + " " + totalPrice + "\n");
    textarea.append("\n");
    textarea.append("Served by: " + "1  Set of " + quantity + " tickets \n");
    textarea.append("Booking number: " + booking_ID);
    textarea.setEditable(false);

     // set up layout and fill in the ticket data
     cp.setLayout(lm);
     cp.add(new JScrollPane(textarea));
     cp.add(buttonPJ);

     try {
         boolean complete = textarea.print();

         if (complete)
         {
          /* show a success message  */
         }
         else
         {
          /*show a message indicating that printing was cancelled */
         }
      } 
      catch (PrinterException pe)
      {
        /* Printing failed, report to the user */
      }
     }
      catch (Exception ex)
       {
        System.err.print("SQLException:");
        System.err.println(ex.getMessage());
       }
   }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panBooking = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        lblOdeon = new javax.swing.JLabel();
        pantabbedBooking = new javax.swing.JTabbedPane();
        panFilms = new javax.swing.JPanel();
        lblShowingTitle = new javax.swing.JLabel();
        lblWelcomeScr = new javax.swing.JLabel();
        panWelcome = new javax.swing.JPanel();
        tabPrices = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStandard = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl3DFilms = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPremier = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPremier3D = new javax.swing.JTable();
        lblChildInfo = new javax.swing.JLabel();
        lblTeenInfo = new javax.swing.JLabel();
        lblStudentInfo = new javax.swing.JLabel();
        lblSeniorInfo = new javax.swing.JLabel();
        lblAdultInfo = new javax.swing.JLabel();
        lblFamilyInfo = new javax.swing.JLabel();
        lblPrices = new javax.swing.JLabel();
        lblSuperSaver = new javax.swing.JLabel();
        lblPeak = new javax.swing.JLabel();
        panBBFC = new javax.swing.JPanel();
        lblBBFCtitle = new javax.swing.JLabel();
        lblUinfo = new javax.swing.JLabel();
        lblPGinfo = new javax.swing.JLabel();
        lbl12Ainfo = new javax.swing.JLabel();
        lbl12Ainfo2 = new javax.swing.JLabel();
        lbl12Ainfo3 = new javax.swing.JLabel();
        lbl15info = new javax.swing.JLabel();
        lbl18info = new javax.swing.JLabel();
        lblFLtitle = new javax.swing.JLabel();
        lblFirst = new javax.swing.JLabel();
        lblTime1 = new javax.swing.JLabel();
        lblTime2 = new javax.swing.JLabel();
        lblLast = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panSearch = new javax.swing.JPanel();
        lblSearchTitle = new javax.swing.JLabel();
        scrSearchedFilms = new javax.swing.JScrollPane();
        tblSearchedFilms = new javax.swing.JTable();
        txtSearchFilm = new javax.swing.JTextField();
        btnFilmSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFilms = new javax.swing.JList();
        lblError = new javax.swing.JLabel();
        panScreenings = new javax.swing.JPanel();
        lblTimes = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listTimes = new javax.swing.JList();
        panCinemaTimes = new javax.swing.JPanel();
        lblTimesHeading = new javax.swing.JLabel();
        scrTimes = new javax.swing.JScrollPane();
        tblTimes = new javax.swing.JTable();
        panFilmInfo = new javax.swing.JPanel();
        lblFilmName = new javax.swing.JLabel();
        lblGenreHeading = new javax.swing.JLabel();
        lblRunTimeHeading = new javax.swing.JLabel();
        lblDirectorHeading = new javax.swing.JLabel();
        lblLanguageHeading = new javax.swing.JLabel();
        lblCountryHeading = new javax.swing.JLabel();
        lblDistributorHeading = new javax.swing.JLabel();
        lblPlotHeading = new javax.swing.JLabel();
        lblBBFCinfoHeading = new javax.swing.JLabel();
        lblGenre = new javax.swing.JLabel();
        lblRunTime = new javax.swing.JLabel();
        lblBBFC = new javax.swing.JLabel();
        lblDirector = new javax.swing.JLabel();
        lblCountry = new javax.swing.JLabel();
        lblLanguage = new javax.swing.JLabel();
        lblDistributor = new javax.swing.JLabel();
        scrPlot = new javax.swing.JScrollPane();
        txtPlot = new javax.swing.JTextPane();
        scrBBFCinfo = new javax.swing.JScrollPane();
        txtBBFCinfo = new javax.swing.JTextPane();
        panSeats = new javax.swing.JPanel();
        panTickets = new javax.swing.JPanel();
        lblTicketsHeading = new javax.swing.JLabel();
        lblStandard = new javax.swing.JLabel();
        lblPremier = new javax.swing.JLabel();
        lblStnAdult = new javax.swing.JLabel();
        lblPreAdult = new javax.swing.JLabel();
        lblStnSenior = new javax.swing.JLabel();
        lblStnStudent = new javax.swing.JLabel();
        lblStnTeen = new javax.swing.JLabel();
        lblStnChild = new javax.swing.JLabel();
        lblPreSenior = new javax.swing.JLabel();
        lblPreStudent = new javax.swing.JLabel();
        lblPreTeen = new javax.swing.JLabel();
        lblPreChild = new javax.swing.JLabel();
        lblStnAdultPrice = new javax.swing.JLabel();
        lblStnSeniorPrice = new javax.swing.JLabel();
        lblStnStudentPrice = new javax.swing.JLabel();
        lblStnTeenPrice = new javax.swing.JLabel();
        lblStnChildPrice = new javax.swing.JLabel();
        lblPreAdultPrice = new javax.swing.JLabel();
        lblPreSeniorPrice = new javax.swing.JLabel();
        lblPreStudentPrice = new javax.swing.JLabel();
        lblPreTeenPrice = new javax.swing.JLabel();
        lblPreChildPrice = new javax.swing.JLabel();
        comStnAdult = new javax.swing.JComboBox();
        comStnSenior = new javax.swing.JComboBox();
        comStnStudent = new javax.swing.JComboBox();
        comStnTeen = new javax.swing.JComboBox();
        comStnChild = new javax.swing.JComboBox();
        comPreAdult = new javax.swing.JComboBox();
        comPreSenior = new javax.swing.JComboBox();
        comPreStudent = new javax.swing.JComboBox();
        comPreTeen = new javax.swing.JComboBox();
        comPreChild = new javax.swing.JComboBox();
        btnSelect = new javax.swing.JButton();
        lblTicketMessage = new javax.swing.JLabel();
        panTicketPrice = new javax.swing.JPanel();
        lblSeatingMap = new javax.swing.JLabel();
        panMap = new javax.swing.JPanel();
        lblStnAdultName = new javax.swing.JLabel();
        lblStnSeniorName = new javax.swing.JLabel();
        lblStnStudentName = new javax.swing.JLabel();
        lblStnTeenName = new javax.swing.JLabel();
        lblStnChildName = new javax.swing.JLabel();
        lblPreAdultName = new javax.swing.JLabel();
        lblPreSeniorName = new javax.swing.JLabel();
        lblPreStudentName = new javax.swing.JLabel();
        lblPreTeenName = new javax.swing.JLabel();
        lblPreChildName = new javax.swing.JLabel();
        lblX1 = new javax.swing.JLabel();
        lblX2 = new javax.swing.JLabel();
        lblX3 = new javax.swing.JLabel();
        lblX4 = new javax.swing.JLabel();
        lblX5 = new javax.swing.JLabel();
        lblX6 = new javax.swing.JLabel();
        lblX7 = new javax.swing.JLabel();
        lblX8 = new javax.swing.JLabel();
        lblX9 = new javax.swing.JLabel();
        lblX10 = new javax.swing.JLabel();
        lblStnAdultNumTix = new javax.swing.JLabel();
        lblStnSenNumTix = new javax.swing.JLabel();
        lblStnStuNumTix = new javax.swing.JLabel();
        lblStnTeeNumTix = new javax.swing.JLabel();
        lblStnChiNumTix = new javax.swing.JLabel();
        lblpreAduNumTix = new javax.swing.JLabel();
        lblpreSenNumTix = new javax.swing.JLabel();
        lblPreStuNumTix = new javax.swing.JLabel();
        lblpreTeeNumTix = new javax.swing.JLabel();
        lblpreChiNumTix = new javax.swing.JLabel();
        lblStnAduTotal = new javax.swing.JLabel();
        lblStnSenTotal = new javax.swing.JLabel();
        lblStnStuTotal = new javax.swing.JLabel();
        lblStnTeeTotal = new javax.swing.JLabel();
        lblStnChiTotal = new javax.swing.JLabel();
        lblPreAduTotal = new javax.swing.JLabel();
        lblPreSenTotal = new javax.swing.JLabel();
        lblPreStuTotal = new javax.swing.JLabel();
        lblPreTeeTotal = new javax.swing.JLabel();
        lblPreChiTotal = new javax.swing.JLabel();
        lblTotalName = new javax.swing.JLabel();
        lblX11 = new javax.swing.JLabel();
        lblTotalNumTix = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        lbl3Dinfo1 = new javax.swing.JLabel();
        lbl3Dinfo2 = new javax.swing.JLabel();
        lbl3Dinfo3 = new javax.swing.JLabel();
        lbl3Dinfo4 = new javax.swing.JLabel();
        lblFamInfo = new javax.swing.JLabel();
        lblDiscountName = new javax.swing.JLabel();
        lblDiscount = new javax.swing.JLabel();
        lblAddCostsName = new javax.swing.JLabel();
        lblAddCosts = new javax.swing.JLabel();
        lblSubTotalName = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnGoConfirmation = new javax.swing.JButton();
        panFilmInfo3 = new javax.swing.JPanel();
        lblFilmTitle = new javax.swing.JLabel();
        lblBBFC3 = new javax.swing.JLabel();
        lblDate3 = new javax.swing.JLabel();
        lblTime3 = new javax.swing.JLabel();
        lblScreen = new javax.swing.JLabel();
        lblScreenNum = new javax.swing.JLabel();
        panConfirm = new javax.swing.JPanel();
        lblCStitle = new javax.swing.JLabel();
        btnAdvanceBook = new javax.swing.JButton();
        btnPrintOut = new javax.swing.JButton();
        panConfirmInfo = new javax.swing.JPanel();
        lblConfirmFilmName = new javax.swing.JLabel();
        lblConfirmBBFC = new javax.swing.JLabel();
        lblConfirmScreen = new javax.swing.JLabel();
        lblConfirmScrNum = new javax.swing.JLabel();
        lblConfirmDate = new javax.swing.JLabel();
        lblConfirmTime = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblStnAdultName1 = new javax.swing.JLabel();
        lblAdultNumTixCon = new javax.swing.JLabel();
        lblX12 = new javax.swing.JLabel();
        lblAduTotalCon = new javax.swing.JLabel();
        lblStnSeniorName1 = new javax.swing.JLabel();
        lblStnStudentName1 = new javax.swing.JLabel();
        lblStnTeenName1 = new javax.swing.JLabel();
        lblStnChildName1 = new javax.swing.JLabel();
        lblX13 = new javax.swing.JLabel();
        lblX14 = new javax.swing.JLabel();
        lblX15 = new javax.swing.JLabel();
        lblX16 = new javax.swing.JLabel();
        lblSenNumTixCon = new javax.swing.JLabel();
        lblStuNumTixCon = new javax.swing.JLabel();
        lblTeeNumTixCon = new javax.swing.JLabel();
        lblChiNumTixCon = new javax.swing.JLabel();
        lblChiTotalCon = new javax.swing.JLabel();
        lblTeeTotalCon = new javax.swing.JLabel();
        lblStuTotalCon = new javax.swing.JLabel();
        lblSenTotalCon = new javax.swing.JLabel();
        lblTotalNumTixCon = new javax.swing.JLabel();
        lblX17 = new javax.swing.JLabel();
        lblTotalPriceCon = new javax.swing.JLabel();
        lblConfirmMessage = new javax.swing.JLabel();
        btnDoneBooking = new javax.swing.JButton();
        panPremierClub = new javax.swing.JPanel();
        lblPremierClubTitle = new javax.swing.JLabel();
        lblSentence1 = new javax.swing.JLabel();
        lblPoints = new javax.swing.JLabel();
        lblEnterNum = new javax.swing.JLabel();
        txtCardNumber = new javax.swing.JTextField();
        lblTotalPointsTitle = new javax.swing.JLabel();
        lblTotalPoints = new javax.swing.JLabel();
        lblSentence2 = new javax.swing.JLabel();
        txtSpendPoints = new javax.swing.JTextField();
        lblStnInfo = new javax.swing.JLabel();
        lblPreInfo = new javax.swing.JLabel();
        lbl3DFilms = new javax.swing.JLabel();
        btnCardNumber = new javax.swing.JButton();
        btnSpendPoints = new javax.swing.JButton();
        panOW = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        lblOWmessage = new javax.swing.JLabel();
        lblMarbleArch = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        btnPickUp = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panBooking.setBackground(new java.awt.Color(0, 153, 204));
        panBooking.setForeground(new java.awt.Color(255, 255, 255));

        panBar.setBackground(new java.awt.Color(0, 153, 204));

        javax.swing.GroupLayout panBarLayout = new javax.swing.GroupLayout(panBar);
        panBar.setLayout(panBarLayout);
        panBarLayout.setHorizontalGroup(
            panBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(611, 611, 611)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panBarLayout.setVerticalGroup(
            panBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBarLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblOdeon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo_odeon.png"))); // NOI18N

        pantabbedBooking.setBackground(new java.awt.Color(51, 153, 255));

        panFilms.setBackground(new java.awt.Color(0, 102, 204));

        lblShowingTitle.setBackground(new java.awt.Color(0, 0, 0));
        lblShowingTitle.setFont(new java.awt.Font("Impact", 0, 18));
        lblShowingTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblShowingTitle.setText("FILMS SHOWN THIS WEEK");

        lblWelcomeScr.setBackground(new java.awt.Color(153, 153, 153));
        lblWelcomeScr.setFont(new java.awt.Font("Impact", 0, 24));
        lblWelcomeScr.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcomeScr.setText("WELCOME SCREEN");

        panWelcome.setBackground(new java.awt.Color(0, 102, 204));
        panWelcome.setForeground(new java.awt.Color(255, 255, 255));

        tblStandard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ticket Type", "Super Saver", "Peak"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblStandard);

        tabPrices.addTab("Standard", jScrollPane3);

        tbl3DFilms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ticket Type", "Super Saver", "Peak"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbl3DFilms);

        tabPrices.addTab("3D Films", jScrollPane4);

        tblPremier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ticket Type", "Super Saver", "Peak"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblPremier);

        tabPrices.addTab("Premier", jScrollPane5);

        tblPremier3D.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ticket Type", "Super Saver", "Peak"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblPremier3D);

        tabPrices.addTab("3D + Premier", jScrollPane6);

        lblChildInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblChildInfo.setText("Child - 12 years and under");

        lblTeenInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblTeenInfo.setText("Teen - Ages 13-17");

        lblStudentInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblStudentInfo.setText("Student - Valid student card required");

        lblSeniorInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblSeniorInfo.setText("Senior - Ages 60+");

        lblAdultInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblAdultInfo.setText("Adult - Ages 18+");

        lblFamilyInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblFamilyInfo.setText("Family - 1 Adult, 3 Children or 2 Adults, 2 Children");

        lblPrices.setFont(new java.awt.Font("Gill Sans MT", 0, 14));
        lblPrices.setForeground(new java.awt.Color(255, 255, 255));
        lblPrices.setText("Prices");

        lblSuperSaver.setForeground(new java.awt.Color(102, 255, 102));
        lblSuperSaver.setText("Super Saver - Mon-Thurs (before 5pm)");

        lblPeak.setForeground(new java.awt.Color(51, 204, 0));
        lblPeak.setText("Peak - Fri (after 5pm), Sat & Sun (all day) inc. Bank Holidays");

        panBBFC.setBackground(new java.awt.Color(0, 102, 204));

        lblBBFCtitle.setFont(new java.awt.Font("Gill Sans MT", 0, 14));
        lblBBFCtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblBBFCtitle.setText("BBFC Certificates");

        lblUinfo.setForeground(new java.awt.Color(255, 255, 255));
        lblUinfo.setText("Universal - Suitable for persons aged 4 and over");

        lblPGinfo.setForeground(new java.awt.Color(255, 255, 255));
        lblPGinfo.setText("Parental Guidance - General Viewing");

        lbl12Ainfo.setForeground(new java.awt.Color(255, 255, 255));
        lbl12Ainfo.setText("12A with Parental Guidance -  ");

        lbl12Ainfo2.setForeground(new java.awt.Color(255, 255, 255));
        lbl12Ainfo2.setText("Unsuitable for children under 12 but can watch the film when accompained by");

        lbl12Ainfo3.setForeground(new java.awt.Color(255, 255, 255));
        lbl12Ainfo3.setText("someone over 18");

        lbl15info.setForeground(new java.awt.Color(255, 255, 255));
        lbl15info.setText("Suitable ONLY for persons of 15 years and older");

        lbl18info.setForeground(new java.awt.Color(255, 255, 255));
        lbl18info.setText("Suitable ONLY for persons of 18 years and older");

        lblFLtitle.setFont(new java.awt.Font("Gill Sans MT", 0, 14));
        lblFLtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblFLtitle.setText("First and last film times");

        lblFirst.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblFirst.setForeground(new java.awt.Color(255, 255, 255));
        lblFirst.setText("First:");

        lblTime1.setForeground(new java.awt.Color(255, 255, 255));

        lblTime2.setForeground(new java.awt.Color(255, 255, 255));

        lblLast.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblLast.setForeground(new java.awt.Color(255, 255, 255));
        lblLast.setText("Last:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_U.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_PG.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_12A.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_15.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BBFC_18.png"))); // NOI18N

        javax.swing.GroupLayout panBBFCLayout = new javax.swing.GroupLayout(panBBFC);
        panBBFC.setLayout(panBBFCLayout);
        panBBFCLayout.setHorizontalGroup(
            panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBBFCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl12Ainfo2)
                    .addGroup(panBBFCLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUinfo))
                    .addComponent(lbl12Ainfo3)
                    .addGroup(panBBFCLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl15info))
                    .addGroup(panBBFCLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl18info))
                    .addComponent(lblBBFCtitle)
                    .addComponent(lblFLtitle)
                    .addGroup(panBBFCLayout.createSequentialGroup()
                        .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPGinfo)
                            .addComponent(lbl12Ainfo)))
                    .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panBBFCLayout.createSequentialGroup()
                            .addComponent(lblLast)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblTime2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panBBFCLayout.createSequentialGroup()
                            .addComponent(lblFirst)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblTime1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        panBBFCLayout.setVerticalGroup(
            panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBBFCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBBFCtitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblUinfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(lblPGinfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(lbl12Ainfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl12Ainfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl12Ainfo3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl15info)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl18info)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFLtitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFirst)
                    .addComponent(lblTime1, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(panBBFCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLast)
                    .addComponent(lblTime2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        panSearch.setBackground(new java.awt.Color(0, 51, 204));

        lblSearchTitle.setFont(new java.awt.Font("Gill Sans MT", 0, 14));
        lblSearchTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblSearchTitle.setText("Search Results");

        tblSearchedFilms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Film Name", "BBFC Certificate", "Director", "Run Time", "Genre", "Film Language"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSearchedFilms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSearchedFilmsMousePressed(evt);
            }
        });
        scrSearchedFilms.setViewportView(tblSearchedFilms);

        javax.swing.GroupLayout panSearchLayout = new javax.swing.GroupLayout(panSearch);
        panSearch.setLayout(panSearchLayout);
        panSearchLayout.setHorizontalGroup(
            panSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrSearchedFilms, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
                    .addComponent(lblSearchTitle))
                .addContainerGap())
        );
        panSearchLayout.setVerticalGroup(
            panSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSearchTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrSearchedFilms, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panWelcomeLayout = new javax.swing.GroupLayout(panWelcome);
        panWelcome.setLayout(panWelcomeLayout);
        panWelcomeLayout.setHorizontalGroup(
            panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panWelcomeLayout.createSequentialGroup()
                        .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tabPrices, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panWelcomeLayout.createSequentialGroup()
                                    .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTeenInfo)
                                        .addComponent(lblStudentInfo)
                                        .addComponent(lblChildInfo))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblFamilyInfo)
                                        .addComponent(lblAdultInfo)
                                        .addComponent(lblSeniorInfo))))
                            .addComponent(lblPrices)
                            .addComponent(lblSuperSaver)
                            .addComponent(lblPeak))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        panWelcomeLayout.setVerticalGroup(
            panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panWelcomeLayout.createSequentialGroup()
                .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panWelcomeLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblPrices)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabPrices, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panWelcomeLayout.createSequentialGroup()
                                .addComponent(lblChildInfo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTeenInfo))
                            .addGroup(panWelcomeLayout.createSequentialGroup()
                                .addComponent(lblFamilyInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAdultInfo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStudentInfo)
                            .addComponent(lblSeniorInfo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSuperSaver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPeak))
                    .addGroup(panWelcomeLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(panBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtSearchFilm.setText("Type a film name here...");
        txtSearchFilm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchFilmActionPerformed(evt);
            }
        });
        txtSearchFilm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFilmFocusLost(evt);
            }
        });

        btnFilmSearch.setText("Search");
        btnFilmSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilmSearchActionPerformed(evt);
            }
        });

        listFilms.setModel(filmList);
        listFilms.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listFilms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listFilmsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listFilmsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(listFilms);

        lblError.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panFilmsLayout = new javax.swing.GroupLayout(panFilms);
        panFilms.setLayout(panFilmsLayout);
        panFilmsLayout.setHorizontalGroup(
            panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilmsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblWelcomeScr)
                    .addComponent(lblShowingTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFilmsLayout.createSequentialGroup()
                        .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(txtSearchFilm, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFilmSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149))
                    .addGroup(panFilmsLayout.createSequentialGroup()
                        .addComponent(panWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panFilmsLayout.setVerticalGroup(
            panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFilmsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panFilmsLayout.createSequentialGroup()
                        .addGroup(panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSearchFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFilmSearch)
                                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblWelcomeScr, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblShowingTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pantabbedBooking.addTab("Choose Films", panFilms);

        panScreenings.setBackground(new java.awt.Color(0, 102, 204));

        lblTimes.setFont(new java.awt.Font("Impact", 0, 36));
        lblTimes.setForeground(new java.awt.Color(255, 255, 255));
        lblTimes.setText("FILM TIMES");

        listTimes.setModel(timeList);
        listTimes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listTimes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listTimesMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(listTimes);

        panCinemaTimes.setBackground(new java.awt.Color(0, 51, 204));
        panCinemaTimes.setForeground(new java.awt.Color(0, 51, 204));

        lblTimesHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblTimesHeading.setText("Screenings in other cinemas");

        tblTimes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cinema", "Date", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrTimes.setViewportView(tblTimes);

        javax.swing.GroupLayout panCinemaTimesLayout = new javax.swing.GroupLayout(panCinemaTimes);
        panCinemaTimes.setLayout(panCinemaTimesLayout);
        panCinemaTimesLayout.setHorizontalGroup(
            panCinemaTimesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCinemaTimesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panCinemaTimesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrTimes, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                    .addComponent(lblTimesHeading))
                .addContainerGap())
        );
        panCinemaTimesLayout.setVerticalGroup(
            panCinemaTimesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCinemaTimesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTimesHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrTimes, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );

        panFilmInfo.setBackground(new java.awt.Color(0, 102, 204));

        lblFilmName.setFont(new java.awt.Font("Impact", 0, 24));
        lblFilmName.setForeground(new java.awt.Color(255, 255, 255));
        lblFilmName.setText("Film Name");

        lblGenreHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblGenreHeading.setText("Genre:");

        lblRunTimeHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblRunTimeHeading.setText("Film running length:");

        lblDirectorHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblDirectorHeading.setText("Director by:");

        lblLanguageHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblLanguageHeading.setText("Language:");

        lblCountryHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblCountryHeading.setText("Country:");

        lblDistributorHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblDistributorHeading.setText("Distributor:");

        lblPlotHeading.setFont(new java.awt.Font("Impact", 0, 14));
        lblPlotHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblPlotHeading.setText("Plot");

        lblBBFCinfoHeading.setFont(new java.awt.Font("Impact", 0, 14));
        lblBBFCinfoHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblBBFCinfoHeading.setText("BBFC Information");

        lblGenre.setForeground(new java.awt.Color(255, 255, 255));

        lblRunTime.setForeground(new java.awt.Color(255, 255, 255));

        lblDirector.setForeground(new java.awt.Color(255, 255, 255));

        lblCountry.setForeground(new java.awt.Color(255, 255, 255));

        lblLanguage.setForeground(new java.awt.Color(255, 255, 255));

        lblDistributor.setForeground(new java.awt.Color(255, 255, 255));

        scrPlot.setBackground(new java.awt.Color(0, 102, 204));

        txtPlot.setBackground(new java.awt.Color(0, 102, 204));
        txtPlot.setForeground(new java.awt.Color(255, 255, 255));
        scrPlot.setViewportView(txtPlot);

        scrBBFCinfo.setBackground(new java.awt.Color(0, 102, 204));

        txtBBFCinfo.setBackground(new java.awt.Color(0, 102, 204));
        txtBBFCinfo.setForeground(new java.awt.Color(255, 255, 255));
        scrBBFCinfo.setViewportView(txtBBFCinfo);

        javax.swing.GroupLayout panFilmInfoLayout = new javax.swing.GroupLayout(panFilmInfo);
        panFilmInfo.setLayout(panFilmInfoLayout);
        panFilmInfoLayout.setHorizontalGroup(
            panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilmInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                        .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addComponent(lblGenreHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addComponent(lblDirectorHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addComponent(lblLanguageHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addComponent(lblDistributorHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDistributor, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                                        .addComponent(lblCountryHeading)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCountry, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                                        .addComponent(lblRunTimeHeading)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblRunTime, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(361, Short.MAX_VALUE))))
                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                        .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPlotHeading)
                            .addComponent(lblBBFCinfoHeading)
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addComponent(lblFilmName, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(457, Short.MAX_VALUE))
                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                        .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scrPlot, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrBBFCinfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        panFilmInfoLayout.setVerticalGroup(
            panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilmInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFilmName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGenreHeading)
                    .addComponent(lblRunTimeHeading)
                    .addComponent(lblGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRunTime, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                        .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDirectorHeading)
                                .addComponent(lblCountryHeading))
                            .addComponent(lblCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFilmInfoLayout.createSequentialGroup()
                                .addGroup(panFilmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblLanguageHeading)
                                    .addComponent(lblDistributorHeading))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPlotHeading))
                            .addComponent(lblDistributor, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panFilmInfoLayout.createSequentialGroup()
                        .addComponent(lblDirector, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrPlot, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBBFCinfoHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrBBFCinfo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panScreeningsLayout = new javax.swing.GroupLayout(panScreenings);
        panScreenings.setLayout(panScreeningsLayout);
        panScreeningsLayout.setHorizontalGroup(
            panScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panScreeningsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panScreeningsLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panScreeningsLayout.createSequentialGroup()
                                .addComponent(panFilmInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(155, 155, 155))
                            .addGroup(panScreeningsLayout.createSequentialGroup()
                                .addComponent(panCinemaTimes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(panScreeningsLayout.createSequentialGroup()
                        .addComponent(lblTimes)
                        .addContainerGap(1021, Short.MAX_VALUE))))
        );
        panScreeningsLayout.setVerticalGroup(
            panScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panScreeningsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTimes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panScreeningsLayout.createSequentialGroup()
                        .addComponent(panFilmInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panCinemaTimes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pantabbedBooking.addTab("Choose Screenings", panScreenings);

        panSeats.setBackground(new java.awt.Color(0, 102, 204));

        panTickets.setBackground(new java.awt.Color(0, 51, 204));

        lblTicketsHeading.setFont(new java.awt.Font("Impact", 0, 36));
        lblTicketsHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblTicketsHeading.setText("TICKETS");

        lblStandard.setBackground(new java.awt.Color(0, 0, 0));
        lblStandard.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblStandard.setForeground(new java.awt.Color(255, 255, 255));
        lblStandard.setText("Standard");

        lblPremier.setBackground(new java.awt.Color(0, 0, 0));
        lblPremier.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblPremier.setForeground(new java.awt.Color(255, 255, 255));
        lblPremier.setText("Premier");

        lblStnAdult.setForeground(new java.awt.Color(255, 255, 255));
        lblStnAdult.setText("Adult");

        lblPreAdult.setForeground(new java.awt.Color(255, 255, 255));
        lblPreAdult.setText("Adult");

        lblStnSenior.setForeground(new java.awt.Color(255, 255, 255));
        lblStnSenior.setText("Senior");

        lblStnStudent.setForeground(new java.awt.Color(255, 255, 255));
        lblStnStudent.setText("Student");

        lblStnTeen.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTeen.setText("Teen (13-17)");

        lblStnChild.setForeground(new java.awt.Color(255, 255, 255));
        lblStnChild.setText("Child");

        lblPreSenior.setForeground(new java.awt.Color(255, 255, 255));
        lblPreSenior.setText("Senior");

        lblPreStudent.setForeground(new java.awt.Color(255, 255, 255));
        lblPreStudent.setText("Student");

        lblPreTeen.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTeen.setText("Teen (13-17)");

        lblPreChild.setForeground(new java.awt.Color(255, 255, 255));
        lblPreChild.setText("Child");

        lblStnAdultPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblStnAdultPrice.setText("[Price Here]");

        lblStnSeniorPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblStnSeniorPrice.setText("[Price Here]");

        lblStnStudentPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblStnStudentPrice.setText("[Price Here]");

        lblStnTeenPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTeenPrice.setText("[Price Here]");

        lblStnChildPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblStnChildPrice.setText("[Price Here]");

        lblPreAdultPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPreAdultPrice.setText("[Price Here]");

        lblPreSeniorPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPreSeniorPrice.setText("[Price Here]");

        lblPreStudentPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPreStudentPrice.setText("[Price Here]");

        lblPreTeenPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTeenPrice.setText("[Price Here]");

        lblPreChildPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPreChildPrice.setText("[Price Here]");

        comStnAdult.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comStnAdult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comStnAdultActionPerformed(evt);
            }
        });

        comStnSenior.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comStnSenior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comStnSeniorActionPerformed(evt);
            }
        });

        comStnStudent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comStnStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comStnStudentActionPerformed(evt);
            }
        });

        comStnTeen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comStnTeen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comStnTeenActionPerformed(evt);
            }
        });

        comStnChild.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comStnChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comStnChildActionPerformed(evt);
            }
        });

        comPreAdult.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comPreAdult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPreAdultActionPerformed(evt);
            }
        });

        comPreSenior.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comPreSenior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPreSeniorActionPerformed(evt);
            }
        });

        comPreStudent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comPreStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPreStudentActionPerformed(evt);
            }
        });

        comPreTeen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comPreTeen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPreTeenActionPerformed(evt);
            }
        });

        comPreChild.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        comPreChild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comPreChildActionPerformed(evt);
            }
        });

        btnSelect.setText("Confirm Selection");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        lblTicketMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblTicketMessage.setText("Message");

        javax.swing.GroupLayout panTicketsLayout = new javax.swing.GroupLayout(panTickets);
        panTickets.setLayout(panTicketsLayout);
        panTicketsLayout.setHorizontalGroup(
            panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTicketsLayout.createSequentialGroup()
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panTicketsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTicketsHeading)
                            .addComponent(lblStandard)
                            .addGroup(panTicketsLayout.createSequentialGroup()
                                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPremier)
                                    .addGroup(panTicketsLayout.createSequentialGroup()
                                        .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPreAdult)
                                            .addComponent(lblPreSenior)
                                            .addComponent(lblPreStudent)
                                            .addComponent(lblPreTeen)
                                            .addComponent(lblPreChild))
                                        .addGap(70, 70, 70)
                                        .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblPreTeenPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPreStudentPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPreSeniorPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPreAdultPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPreChildPrice, javax.swing.GroupLayout.Alignment.LEADING))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(comPreAdult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comPreSenior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comPreStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comPreTeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comPreChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panTicketsLayout.createSequentialGroup()
                                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblStnSenior)
                                    .addComponent(lblStnStudent)
                                    .addComponent(lblStnTeen)
                                    .addComponent(lblStnChild)
                                    .addComponent(lblStnAdult))
                                .addGap(70, 70, 70)
                                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panTicketsLayout.createSequentialGroup()
                                        .addComponent(lblStnAdultPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(98, 98, 98)
                                        .addComponent(comStnAdult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panTicketsLayout.createSequentialGroup()
                                        .addComponent(lblStnChildPrice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                        .addComponent(comStnChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panTicketsLayout.createSequentialGroup()
                                        .addComponent(lblStnTeenPrice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                        .addComponent(comStnTeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panTicketsLayout.createSequentialGroup()
                                        .addComponent(lblStnStudentPrice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                        .addComponent(comStnStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panTicketsLayout.createSequentialGroup()
                                        .addComponent(lblStnSeniorPrice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                        .addComponent(comStnSenior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(panTicketsLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(btnSelect))
                    .addGroup(panTicketsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTicketMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panTicketsLayout.setVerticalGroup(
            panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTicketsLayout.createSequentialGroup()
                .addComponent(lblTicketsHeading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStandard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStnAdult)
                    .addComponent(comStnAdult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStnAdultPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStnSenior)
                    .addComponent(lblStnSeniorPrice)
                    .addComponent(comStnSenior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStnStudent)
                    .addComponent(lblStnStudentPrice)
                    .addComponent(comStnStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStnTeen)
                    .addComponent(lblStnTeenPrice)
                    .addComponent(comStnTeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStnChild)
                    .addComponent(lblStnChildPrice)
                    .addComponent(comStnChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(lblPremier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPreAdult)
                    .addComponent(lblPreAdultPrice)
                    .addComponent(comPreAdult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPreSenior)
                    .addComponent(lblPreSeniorPrice)
                    .addComponent(comPreSenior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPreStudent)
                    .addComponent(lblPreStudentPrice)
                    .addComponent(comPreStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPreTeen)
                    .addComponent(lblPreTeenPrice)
                    .addComponent(comPreTeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPreChild)
                    .addComponent(lblPreChildPrice)
                    .addComponent(comPreChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(btnSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTicketMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );

        panTicketPrice.setBackground(new java.awt.Color(0, 51, 204));

        lblSeatingMap.setFont(new java.awt.Font("Impact", 0, 36));
        lblSeatingMap.setForeground(new java.awt.Color(255, 255, 255));
        lblSeatingMap.setText("PRICE OF TICKETS");

        panMap.setBackground(new java.awt.Color(0, 51, 204));

        lblStnAdultName.setForeground(new java.awt.Color(255, 255, 255));
        lblStnAdultName.setText("Standard Adult");

        lblStnSeniorName.setForeground(new java.awt.Color(255, 255, 255));
        lblStnSeniorName.setText("Standard Senior");

        lblStnStudentName.setForeground(new java.awt.Color(255, 255, 255));
        lblStnStudentName.setText("Standard Student");

        lblStnTeenName.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTeenName.setText("Standard Teen");

        lblStnChildName.setForeground(new java.awt.Color(255, 255, 255));
        lblStnChildName.setText("Standard Child");

        lblPreAdultName.setForeground(new java.awt.Color(255, 255, 255));
        lblPreAdultName.setText("Premier Adult");

        lblPreSeniorName.setForeground(new java.awt.Color(255, 255, 255));
        lblPreSeniorName.setText("Premier Senior");

        lblPreStudentName.setForeground(new java.awt.Color(255, 255, 255));
        lblPreStudentName.setText("Premier Student");

        lblPreTeenName.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTeenName.setText("Premier Teen");

        lblPreChildName.setForeground(new java.awt.Color(255, 255, 255));
        lblPreChildName.setText("Premier Child");

        lblX1.setForeground(new java.awt.Color(255, 255, 255));
        lblX1.setText("x");

        lblX2.setForeground(new java.awt.Color(255, 255, 255));
        lblX2.setText("x");

        lblX3.setForeground(new java.awt.Color(255, 255, 255));
        lblX3.setText("x");

        lblX4.setForeground(new java.awt.Color(255, 255, 255));
        lblX4.setText("x");

        lblX5.setForeground(new java.awt.Color(255, 255, 255));
        lblX5.setText("x");

        lblX6.setForeground(new java.awt.Color(255, 255, 255));
        lblX6.setText("x");

        lblX7.setForeground(new java.awt.Color(255, 255, 255));
        lblX7.setText("x");

        lblX8.setForeground(new java.awt.Color(255, 255, 255));
        lblX8.setText("x");

        lblX9.setForeground(new java.awt.Color(255, 255, 255));
        lblX9.setText("x");

        lblX10.setForeground(new java.awt.Color(255, 255, 255));
        lblX10.setText("x");

        lblStnAdultNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblStnAdultNumTix.setText("Num. of Tickets");

        lblStnSenNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblStnSenNumTix.setText("Num. of Tickets");

        lblStnStuNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblStnStuNumTix.setText("Num. of Tickets");

        lblStnTeeNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTeeNumTix.setText("Num. of Tickets");

        lblStnChiNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblStnChiNumTix.setText("Num. of Tickets");

        lblpreAduNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblpreAduNumTix.setText("Num. of Tickets");

        lblpreSenNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblpreSenNumTix.setText("Num. of Tickets");

        lblPreStuNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblPreStuNumTix.setText("Num. of Tickets");

        lblpreTeeNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblpreTeeNumTix.setText("Num. of Tickets");

        lblpreChiNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblpreChiNumTix.setText("Num. of Tickets");

        lblStnAduTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblStnAduTotal.setText("Price of Tickets");

        lblStnSenTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblStnSenTotal.setText("Price of Tickets");

        lblStnStuTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblStnStuTotal.setText("Price of Tickets");

        lblStnTeeTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTeeTotal.setText("Price of Tickets");

        lblStnChiTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblStnChiTotal.setText("Price of Tickets");

        lblPreAduTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblPreAduTotal.setText("Price of Tickets");

        lblPreSenTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblPreSenTotal.setText("Price of Tickets");

        lblPreStuTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblPreStuTotal.setText("Price of Tickets");

        lblPreTeeTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTeeTotal.setText("Price of Tickets");

        lblPreChiTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblPreChiTotal.setText("Price of Tickets");

        lblTotalName.setFont(new java.awt.Font("Tahoma", 0, 24));
        lblTotalName.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalName.setText("Total ");

        lblX11.setForeground(new java.awt.Color(255, 255, 255));
        lblX11.setText("x");

        lblTotalNumTix.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalNumTix.setText("Num. of Tickets");

        lblTotalPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPrice.setText("Price of Tickets");

        lbl3Dinfo1.setForeground(new java.awt.Color(255, 255, 255));
        lbl3Dinfo1.setText("If the film booked in shown in 3D, then additional costs will be added to prices shown on the left: ");

        lbl3Dinfo2.setForeground(new java.awt.Color(255, 255, 255));
        lbl3Dinfo2.setText("Adult: +£2.00");

        lbl3Dinfo3.setForeground(new java.awt.Color(255, 255, 255));
        lbl3Dinfo3.setText("Family: +£6.40");

        lbl3Dinfo4.setForeground(new java.awt.Color(255, 255, 255));
        lbl3Dinfo4.setText("Others: +£1.60 ");

        lblFamInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblFamInfo.setText("If the tickets bought are either 1 adult, 3 children or 2 adult, 2 children, these will be discounted as family tickets  ");

        lblDiscountName.setForeground(new java.awt.Color(255, 255, 255));
        lblDiscountName.setText("Discount");

        lblDiscount.setForeground(new java.awt.Color(255, 255, 255));
        lblDiscount.setText("Price of Discount");

        lblAddCostsName.setForeground(new java.awt.Color(255, 255, 255));
        lblAddCostsName.setText("Additional Costs");

        lblAddCosts.setForeground(new java.awt.Color(255, 255, 255));
        lblAddCosts.setText("Price of Costs");

        lblSubTotalName.setForeground(new java.awt.Color(255, 255, 255));
        lblSubTotalName.setText("Sub-Total");

        lblSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblSubTotal.setText("Price of Tickets");

        btnGoConfirmation.setText("OK");
        btnGoConfirmation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoConfirmationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panMapLayout = new javax.swing.GroupLayout(panMap);
        panMap.setLayout(panMapLayout);
        panMapLayout.setHorizontalGroup(
            panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                    .addComponent(lbl3Dinfo4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl3Dinfo1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl3Dinfo2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panMapLayout.createSequentialGroup()
                        .addComponent(lbl3Dinfo3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
                        .addComponent(btnGoConfirmation, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panMapLayout.createSequentialGroup()
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDiscountName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddCostsName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubTotalName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panMapLayout.createSequentialGroup()
                                .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblPreChildName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPreTeenName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPreStudentName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPreSeniorName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblPreAdultName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblStnChildName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblStnTeenName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblStnStudentName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblStnSeniorName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblStnAdultName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                                    .addComponent(lblTotalName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTotalNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStnAdultNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStnSenNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStnStuNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStnTeeNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStnChiNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblpreAduNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblpreSenNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblPreStuNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblpreTeeNumTix))
                                    .addGroup(panMapLayout.createSequentialGroup()
                                        .addComponent(lblX10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblpreChiNumTix)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalPrice)
                            .addComponent(lblPreChiTotal)
                            .addComponent(lblPreTeeTotal)
                            .addComponent(lblPreStuTotal)
                            .addComponent(lblPreSenTotal)
                            .addComponent(lblPreAduTotal)
                            .addComponent(lblStnChiTotal)
                            .addComponent(lblStnTeeTotal)
                            .addComponent(lblStnStuTotal)
                            .addComponent(lblStnSenTotal)
                            .addComponent(lblStnAduTotal)
                            .addComponent(lblSubTotal)
                            .addComponent(lblDiscount)
                            .addComponent(lblAddCosts)))
                    .addComponent(lblFamInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panMapLayout.setVerticalGroup(
            panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panMapLayout.createSequentialGroup()
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnAdultName)
                            .addComponent(lblX1)
                            .addComponent(lblStnAdultNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnSeniorName)
                            .addComponent(lblX2)
                            .addComponent(lblStnSenNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnStudentName)
                            .addComponent(lblX3)
                            .addComponent(lblStnStuNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnTeenName)
                            .addComponent(lblX4)
                            .addComponent(lblStnTeeNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnChildName)
                            .addComponent(lblX5)
                            .addComponent(lblStnChiNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPreAdultName)
                            .addComponent(lblX6)
                            .addComponent(lblpreAduNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPreSeniorName)
                            .addComponent(lblX7)
                            .addComponent(lblpreSenNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPreStudentName)
                            .addComponent(lblX8)
                            .addComponent(lblPreStuNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPreTeenName)
                            .addComponent(lblX9)
                            .addComponent(lblpreTeeNumTix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPreChildName)
                            .addComponent(lblX10)
                            .addComponent(lblpreChiNumTix)))
                    .addGroup(panMapLayout.createSequentialGroup()
                        .addComponent(lblStnAduTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStnSenTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStnStuTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStnTeeTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStnChiTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPreAduTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPreSenTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPreStuTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPreTeeTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPreChiTotal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panMapLayout.createSequentialGroup()
                        .addComponent(lblSubTotalName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDiscountName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAddCostsName)
                        .addGap(18, 18, 18)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panMapLayout.createSequentialGroup()
                                .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTotalNumTix)
                                    .addComponent(lblX11)
                                    .addComponent(lblTotalPrice))
                                .addGap(14, 14, 14)))
                        .addGap(18, 18, 18)
                        .addComponent(lbl3Dinfo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3Dinfo2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl3Dinfo3)
                            .addComponent(btnGoConfirmation))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3Dinfo4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panMapLayout.createSequentialGroup()
                        .addComponent(lblSubTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDiscount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAddCosts)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFamInfo)
                .addContainerGap())
        );

        javax.swing.GroupLayout panTicketPriceLayout = new javax.swing.GroupLayout(panTicketPrice);
        panTicketPrice.setLayout(panTicketPriceLayout);
        panTicketPriceLayout.setHorizontalGroup(
            panTicketPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTicketPriceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTicketPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeatingMap))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        panTicketPriceLayout.setVerticalGroup(
            panTicketPriceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTicketPriceLayout.createSequentialGroup()
                .addComponent(lblSeatingMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );

        panFilmInfo3.setBackground(new java.awt.Color(0, 102, 204));

        lblFilmTitle.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblFilmTitle.setForeground(new java.awt.Color(255, 255, 255));

        lblDate3.setForeground(new java.awt.Color(255, 255, 255));

        lblTime3.setForeground(new java.awt.Color(255, 255, 255));

        lblScreen.setForeground(new java.awt.Color(255, 255, 255));
        lblScreen.setText("Screen:");

        lblScreenNum.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panFilmInfo3Layout = new javax.swing.GroupLayout(panFilmInfo3);
        panFilmInfo3.setLayout(panFilmInfo3Layout);
        panFilmInfo3Layout.setHorizontalGroup(
            panFilmInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilmInfo3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panFilmInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panFilmInfo3Layout.createSequentialGroup()
                        .addComponent(lblFilmTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBBFC3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(panFilmInfo3Layout.createSequentialGroup()
                        .addComponent(lblDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTime3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblScreen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblScreenNum, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addContainerGap(665, Short.MAX_VALUE))
        );
        panFilmInfo3Layout.setVerticalGroup(
            panFilmInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilmInfo3Layout.createSequentialGroup()
                .addGroup(panFilmInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBBFC3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panFilmInfo3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblFilmTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panFilmInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFilmInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTime3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblScreen))
                    .addComponent(lblScreenNum, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panSeatsLayout = new javax.swing.GroupLayout(panSeats);
        panSeats.setLayout(panSeatsLayout);
        panSeatsLayout.setHorizontalGroup(
            panSeatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panSeatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSeatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panFilmInfo3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panSeatsLayout.createSequentialGroup()
                        .addComponent(panTickets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panTicketPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panSeatsLayout.setVerticalGroup(
            panSeatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panSeatsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panFilmInfo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panSeatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panTickets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panTicketPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pantabbedBooking.addTab("Choose Seats and Tickets", panSeats);

        panConfirm.setBackground(new java.awt.Color(0, 102, 204));

        lblCStitle.setFont(new java.awt.Font("Impact", 0, 36));
        lblCStitle.setForeground(new java.awt.Color(255, 255, 255));
        lblCStitle.setText("CONFIRMATION SCREEN");

        btnAdvanceBook.setText("Advance Book");
        btnAdvanceBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvanceBookActionPerformed(evt);
            }
        });

        btnPrintOut.setText("Print out tickets");
        btnPrintOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintOutActionPerformed(evt);
            }
        });

        panConfirmInfo.setBackground(new java.awt.Color(0, 102, 204));

        lblConfirmFilmName.setFont(new java.awt.Font("Gill Sans MT", 0, 18));
        lblConfirmFilmName.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmFilmName.setText("Film Name");

        lblConfirmScreen.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmScreen.setText("Screen");

        lblConfirmScrNum.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmScrNum.setText("0");

        lblConfirmDate.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmDate.setText("14/02/11");

        lblConfirmTime.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmTime.setText("19:25");

        javax.swing.GroupLayout panConfirmInfoLayout = new javax.swing.GroupLayout(panConfirmInfo);
        panConfirmInfo.setLayout(panConfirmInfoLayout);
        panConfirmInfoLayout.setHorizontalGroup(
            panConfirmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panConfirmInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panConfirmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panConfirmInfoLayout.createSequentialGroup()
                        .addComponent(lblConfirmDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConfirmTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblConfirmScreen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConfirmScrNum))
                    .addGroup(panConfirmInfoLayout.createSequentialGroup()
                        .addComponent(lblConfirmFilmName, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblConfirmBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addContainerGap(744, Short.MAX_VALUE))
        );
        panConfirmInfoLayout.setVerticalGroup(
            panConfirmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panConfirmInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panConfirmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmFilmName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfirmBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panConfirmInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmScreen)
                    .addComponent(lblConfirmScrNum)
                    .addComponent(lblConfirmDate)
                    .addComponent(lblConfirmTime))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 204));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Ticket Information");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total");

        lblStnAdultName1.setForeground(new java.awt.Color(255, 255, 255));
        lblStnAdultName1.setText("Adult");

        lblAdultNumTixCon.setForeground(new java.awt.Color(255, 255, 255));
        lblAdultNumTixCon.setText("Num. of Tickets");

        lblX12.setForeground(new java.awt.Color(255, 255, 255));
        lblX12.setText("x");

        lblAduTotalCon.setForeground(new java.awt.Color(255, 255, 255));
        lblAduTotalCon.setText("Price of Tickets");

        lblStnSeniorName1.setForeground(new java.awt.Color(255, 255, 255));
        lblStnSeniorName1.setText("Senior");

        lblStnStudentName1.setForeground(new java.awt.Color(255, 255, 255));
        lblStnStudentName1.setText("Student");

        lblStnTeenName1.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTeenName1.setText("Teen");

        lblStnChildName1.setForeground(new java.awt.Color(255, 255, 255));
        lblStnChildName1.setText("Child");

        lblX13.setForeground(new java.awt.Color(255, 255, 255));
        lblX13.setText("x");

        lblX14.setForeground(new java.awt.Color(255, 255, 255));
        lblX14.setText("x");

        lblX15.setForeground(new java.awt.Color(255, 255, 255));
        lblX15.setText("x");

        lblX16.setForeground(new java.awt.Color(255, 255, 255));
        lblX16.setText("x");

        lblSenNumTixCon.setForeground(new java.awt.Color(255, 255, 255));
        lblSenNumTixCon.setText("Num. of Tickets");

        lblStuNumTixCon.setForeground(new java.awt.Color(255, 255, 255));
        lblStuNumTixCon.setText("Num. of Tickets");

        lblTeeNumTixCon.setForeground(new java.awt.Color(255, 255, 255));
        lblTeeNumTixCon.setText("Num. of Tickets");

        lblChiNumTixCon.setForeground(new java.awt.Color(255, 255, 255));
        lblChiNumTixCon.setText("Num. of Tickets");

        lblChiTotalCon.setForeground(new java.awt.Color(255, 255, 255));
        lblChiTotalCon.setText("Price of Tickets");

        lblTeeTotalCon.setForeground(new java.awt.Color(255, 255, 255));
        lblTeeTotalCon.setText("Price of Tickets");

        lblStuTotalCon.setForeground(new java.awt.Color(255, 255, 255));
        lblStuTotalCon.setText("Price of Tickets");

        lblSenTotalCon.setForeground(new java.awt.Color(255, 255, 255));
        lblSenTotalCon.setText("Price of Tickets");

        lblTotalNumTixCon.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalNumTixCon.setText("Num. of Tickets");

        lblX17.setForeground(new java.awt.Color(255, 255, 255));
        lblX17.setText("x");

        lblTotalPriceCon.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPriceCon.setText("Price of Tickets");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblStnChildName1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                    .addComponent(lblStnTeenName1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                    .addComponent(lblStnSeniorName1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                    .addComponent(lblStnAdultName1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(254, 254, 254))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblStnStudentName1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblX17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalNumTixCon))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblX12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAdultNumTixCon))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblX16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSenNumTixCon))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblX15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStuNumTixCon))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblX14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTeeNumTixCon))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblX13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblChiNumTixCon)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalPriceCon)
                            .addComponent(lblChiTotalCon)
                            .addComponent(lblTeeTotalCon)
                            .addComponent(lblStuTotalCon)
                            .addComponent(lblSenTotalCon)
                            .addComponent(lblAduTotalCon))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblX12)
                                    .addComponent(lblAdultNumTixCon))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblX16)
                                    .addComponent(lblSenNumTixCon))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblX15)
                                    .addComponent(lblStuNumTixCon)
                                    .addComponent(lblStnStudentName1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblX14)
                                    .addComponent(lblTeeNumTixCon))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblX13)
                                    .addComponent(lblChiNumTixCon)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblStnAdultName1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStnSeniorName1)
                                .addGap(26, 26, 26)
                                .addComponent(lblStnTeenName1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStnChildName1))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(lblAduTotalCon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSenTotalCon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStuTotalCon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTeeTotalCon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblChiTotalCon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblX17)
                    .addComponent(lblTotalNumTixCon)
                    .addComponent(lblTotalPriceCon))
                .addContainerGap())
        );

        lblConfirmMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmMessage.setText("Confirmation Message");

        btnDoneBooking.setText("Done Booking");
        btnDoneBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneBookingActionPerformed(evt);
            }
        });

        panPremierClub.setBackground(new java.awt.Color(0, 51, 204));

        lblPremierClubTitle.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblPremierClubTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblPremierClubTitle.setText("ODEON Premiere Club");

        lblSentence1.setForeground(new java.awt.Color(255, 255, 255));
        lblSentence1.setText("points can be earned from this booking");

        lblPoints.setForeground(new java.awt.Color(255, 255, 255));
        lblPoints.setText(null);

        lblEnterNum.setForeground(new java.awt.Color(255, 255, 255));
        lblEnterNum.setText("Enter card number here");

        lblTotalPointsTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPointsTitle.setText("Total Points");

        lblTotalPoints.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPoints.setText(null);

        lblSentence2.setForeground(new java.awt.Color(255, 255, 255));
        lblSentence2.setText("How many points does the customer want to spend?");

        lblStnInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblStnInfo.setText("Standard ticket: 800 ODEON Points");

        lblPreInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblPreInfo.setText("Premier ticket: 1000 ODEON Points");

        lbl3DFilms.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DFilms.setText("3D Films: Add 300 ODEON Points to above");

        btnCardNumber.setText("Enter");
        btnCardNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCardNumberActionPerformed(evt);
            }
        });

        btnSpendPoints.setText("Enter");
        btnSpendPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpendPointsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panPremierClubLayout = new javax.swing.GroupLayout(panPremierClub);
        panPremierClub.setLayout(panPremierClubLayout);
        panPremierClubLayout.setHorizontalGroup(
            panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPremierClubLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPremierClubTitle)
                    .addGroup(panPremierClubLayout.createSequentialGroup()
                        .addComponent(lblPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSentence1))
                    .addComponent(lblEnterNum)
                    .addGroup(panPremierClubLayout.createSequentialGroup()
                        .addComponent(lblTotalPointsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblPreInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStnInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lbl3DFilms)
                    .addGroup(panPremierClubLayout.createSequentialGroup()
                        .addComponent(txtCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCardNumber))
                    .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panPremierClubLayout.createSequentialGroup()
                            .addComponent(txtSpendPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSpendPoints))
                        .addComponent(lblSentence2, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        panPremierClubLayout.setVerticalGroup(
            panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPremierClubLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPremierClubTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSentence1)
                    .addComponent(lblPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEnterNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCardNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPointsTitle)
                    .addComponent(lblTotalPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSentence2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panPremierClubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSpendPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSpendPoints))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStnInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPreInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl3DFilms)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        panOW.setBackground(new java.awt.Color(0, 51, 204));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Orange Wednesdays");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Check if customer has the code");

        jButton1.setText("Yes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Can't be used for advance bookings");

        lblOWmessage.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panOWLayout = new javax.swing.GroupLayout(panOW);
        panOW.setLayout(panOWLayout);
        panOWLayout.setHorizontalGroup(
            panOWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOWLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panOWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOWmessage, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addGroup(panOWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addComponent(jLabel21))
                    .addComponent(jLabel22))
                .addContainerGap())
        );
        panOWLayout.setVerticalGroup(
            panOWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOWLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOWmessage, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panConfirmLayout = new javax.swing.GroupLayout(panConfirm);
        panConfirm.setLayout(panConfirmLayout);
        panConfirmLayout.setHorizontalGroup(
            panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panConfirmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panConfirmInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCStitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panConfirmLayout.createSequentialGroup()
                        .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panOW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panPremierClub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panConfirmLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panConfirmLayout.createSequentialGroup()
                                        .addComponent(btnAdvanceBook)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnPrintOut)
                                        .addGap(274, 274, 274))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panConfirmLayout.createSequentialGroup()
                                        .addComponent(btnDoneBooking)
                                        .addGap(332, 332, 332))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panConfirmLayout.createSequentialGroup()
                                        .addComponent(lblConfirmMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(184, 184, 184))))
                            .addGroup(panConfirmLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(82, 82, 82))
        );
        panConfirmLayout.setVerticalGroup(
            panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panConfirmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCStitle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(panConfirmInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panPremierClub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panConfirmLayout.createSequentialGroup()
                        .addComponent(panOW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panConfirmLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdvanceBook)
                            .addComponent(btnPrintOut))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConfirmMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDoneBooking)
                        .addGap(29, 29, 29))))
        );

        pantabbedBooking.addTab("Confirmation Screen", panConfirm);

        lblMarbleArch.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblMarbleArch.setForeground(new java.awt.Color(255, 255, 255));
        lblMarbleArch.setText("Marble Arch (0871 22 44 007)");

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblDate.setForeground(new java.awt.Color(255, 255, 255));

        btnPickUp.setText("Pick up Tickets");
        btnPickUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPickUpActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panBookingLayout = new javax.swing.GroupLayout(panBooking);
        panBooking.setLayout(panBookingLayout);
        panBookingLayout.setHorizontalGroup(
            panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBookingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panBookingLayout.createSequentialGroup()
                        .addComponent(lblOdeon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(lblMarbleArch))
                        .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panBookingLayout.createSequentialGroup()
                                .addGap(1018, 1018, 1018)
                                .addComponent(panBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panBookingLayout.createSequentialGroup()
                                .addGap(188, 188, 188)
                                .addComponent(btnPickUp))))
                    .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnClose)
                        .addComponent(pantabbedBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 1193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panBookingLayout.setVerticalGroup(
            panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBookingLayout.createSequentialGroup()
                .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panBookingLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panBookingLayout.createSequentialGroup()
                                .addGroup(panBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMarbleArch)
                                    .addComponent(btnPickUp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblOdeon)))
                    .addGroup(panBookingLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnClose)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pantabbedBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(510, 510, 510)
                .addComponent(panBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 1221, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchFilmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchFilmActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtSearchFilmActionPerformed

    private void btnFilmSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilmSearchActionPerformed
   searchFilms();
    }//GEN-LAST:event_btnFilmSearchActionPerformed

    private void listFilmsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listFilmsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listFilmsMouseClicked

    private void listFilmsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listFilmsMousePressed
     pantabbedBooking.setSelectedComponent(panScreenings);
     int index = listFilms.getSelectedIndex();
     String filmName = (String)filmList.get(index);
     filmInformation(filmName);
     generateTimesList(filmName);
     cinemaTimes(filmName);   
    }//GEN-LAST:event_listFilmsMousePressed

    private void tblSearchedFilmsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSearchedFilmsMousePressed
     pantabbedBooking.setSelectedComponent(panScreenings);
     int index = tblSearchedFilms.getSelectedRow();
     String filmName = (String)filmList.get(index);
     filmInformation(filmName);
     generateTimesList(filmName);
     cinemaTimes(filmName);
    }//GEN-LAST:event_tblSearchedFilmsMousePressed

    private void listTimesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTimesMousePressed
    pantabbedBooking.setSelectedComponent(panSeats);
    int index = listTimes.getSelectedIndex();
    
    String filmName = lblFilmName.getText();
    String dateTime = (String)timeList.get(index);
    String fDate = dateTime.substring(0, 10);
    String fTime = dateTime.substring(11, 19);
    filmInfoforSeating(fDate, fTime, filmName);
    listTicketPrices(fDate, fTime, filmName);   
    }//GEN-LAST:event_listTimesMousePressed

    private void comStnAdultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comStnAdultActionPerformed
      StnAduQuantity = Integer.parseInt(comStnAdult.getSelectedItem().toString());
    }//GEN-LAST:event_comStnAdultActionPerformed

    private void comStnSeniorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comStnSeniorActionPerformed
      StnSenQuantity = Integer.parseInt(comStnSenior.getSelectedItem().toString());
    }//GEN-LAST:event_comStnSeniorActionPerformed

    private void comStnStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comStnStudentActionPerformed
      StnStuQuantity = Integer.parseInt(comStnStudent.getSelectedItem().toString());
    }//GEN-LAST:event_comStnStudentActionPerformed

    private void comStnTeenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comStnTeenActionPerformed
     StnTeeQuantity = Integer.parseInt(comStnTeen.getSelectedItem().toString());
    }//GEN-LAST:event_comStnTeenActionPerformed

    private void comStnChildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comStnChildActionPerformed
    StnChiQuantity = Integer.parseInt(comStnChild.getSelectedItem().toString());
    }//GEN-LAST:event_comStnChildActionPerformed

    private void comPreAdultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPreAdultActionPerformed
    PreAduQuantity = Integer.parseInt(comPreAdult.getSelectedItem().toString());
    }//GEN-LAST:event_comPreAdultActionPerformed

    private void comPreSeniorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPreSeniorActionPerformed
    PreSenQuantity = Integer.parseInt(comPreSenior.getSelectedItem().toString());
    }//GEN-LAST:event_comPreSeniorActionPerformed

    private void comPreStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPreStudentActionPerformed
    PreStuQuantity = Integer.parseInt(comPreStudent.getSelectedItem().toString());
    }//GEN-LAST:event_comPreStudentActionPerformed

    private void comPreTeenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPreTeenActionPerformed
    PreTeeQuantity = Integer.parseInt(comPreTeen.getSelectedItem().toString());
    }//GEN-LAST:event_comPreTeenActionPerformed

    private void comPreChildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comPreChildActionPerformed
    PreChiQuantity = Integer.parseInt(comPreChild.getSelectedItem().toString());
    }//GEN-LAST:event_comPreChildActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
    tickets();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnGoConfirmationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoConfirmationActionPerformed
    // Only want to go confirmation screen not add and create records before the confirmation screen
    pantabbedBooking.setSelectedComponent(panConfirm);
    confirmation();
    showPricesConfirm();
}//GEN-LAST:event_btnGoConfirmationActionPerformed

    private void btnAdvanceBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvanceBookActionPerformed
    int index = listTimes.getSelectedIndex();
    String filmName = lblFilmName.getText();
    String dateTime = (String)timeList.get(index);
    String fDate = dateTime.substring(0, 10);
    String fTime = dateTime.substring(11, 19);
    createTicketBookingRecord(fDate, fTime, filmName);
    addPoints();
    
    try {
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();
           
           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           stmt.execute("UPDATE BOOKINGS SET PRINTED = 'N' WHERE BOOKING_ID = "+ bookingID +"");
           
          }
          catch (Exception ex)
          {
           System.err.print("SQLException:");
           System.err.println(ex.getMessage());
          }

    lblConfirmMessage.setText("Booking Done");
}//GEN-LAST:event_btnAdvanceBookActionPerformed

    private void btnPrintOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintOutActionPerformed
    int index = listTimes.getSelectedIndex();
    String filmName = lblFilmName.getText();
    String dateTime = (String)timeList.get(index);
    String fDate = dateTime.substring(0, 10);
    String fTime = dateTime.substring(11, 19);
    createTicketBookingRecord(fDate, fTime, filmName);
    addPoints();
    getTickets();
    lblConfirmMessage.setText("Booking Done");
}//GEN-LAST:event_btnPrintOutActionPerformed

    private void btnDoneBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneBookingActionPerformed
     pantabbedBooking.setSelectedComponent(panFilms);
}//GEN-LAST:event_btnDoneBookingActionPerformed

    private void btnCardNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCardNumberActionPerformed
    getTotalPoints();
    }//GEN-LAST:event_btnCardNumberActionPerformed

    private void btnSpendPointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpendPointsActionPerformed
    spendPoints();
    showPricesConfirm();
    pointsSpent = true;
}//GEN-LAST:event_btnSpendPointsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    orangeWednesday();
    showPricesConfirm();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPickUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPickUpActionPerformed
     new PickUp().setVisible(true);
    }//GEN-LAST:event_btnPickUpActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
     this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtSearchFilmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFilmFocusLost

    }//GEN-LAST:event_txtSearchFilmFocusLost

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Booking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdvanceBook;
    private javax.swing.JButton btnCardNumber;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDoneBooking;
    private javax.swing.JButton btnFilmSearch;
    private javax.swing.JButton btnGoConfirmation;
    private javax.swing.JButton btnPickUp;
    private javax.swing.JButton btnPrintOut;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnSpendPoints;
    private javax.swing.JComboBox comPreAdult;
    private javax.swing.JComboBox comPreChild;
    private javax.swing.JComboBox comPreSenior;
    private javax.swing.JComboBox comPreStudent;
    private javax.swing.JComboBox comPreTeen;
    private javax.swing.JComboBox comStnAdult;
    private javax.swing.JComboBox comStnChild;
    private javax.swing.JComboBox comStnSenior;
    private javax.swing.JComboBox comStnStudent;
    private javax.swing.JComboBox comStnTeen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl12Ainfo;
    private javax.swing.JLabel lbl12Ainfo2;
    private javax.swing.JLabel lbl12Ainfo3;
    private javax.swing.JLabel lbl15info;
    private javax.swing.JLabel lbl18info;
    private javax.swing.JLabel lbl3DFilms;
    private javax.swing.JLabel lbl3Dinfo1;
    private javax.swing.JLabel lbl3Dinfo2;
    private javax.swing.JLabel lbl3Dinfo3;
    private javax.swing.JLabel lbl3Dinfo4;
    private javax.swing.JLabel lblAddCosts;
    private javax.swing.JLabel lblAddCostsName;
    private javax.swing.JLabel lblAduTotalCon;
    private javax.swing.JLabel lblAdultInfo;
    private javax.swing.JLabel lblAdultNumTixCon;
    private javax.swing.JLabel lblBBFC;
    private javax.swing.JLabel lblBBFC3;
    private javax.swing.JLabel lblBBFCinfoHeading;
    private javax.swing.JLabel lblBBFCtitle;
    private javax.swing.JLabel lblCStitle;
    private javax.swing.JLabel lblChiNumTixCon;
    private javax.swing.JLabel lblChiTotalCon;
    private javax.swing.JLabel lblChildInfo;
    private javax.swing.JLabel lblConfirmBBFC;
    private javax.swing.JLabel lblConfirmDate;
    private javax.swing.JLabel lblConfirmFilmName;
    private javax.swing.JLabel lblConfirmMessage;
    private javax.swing.JLabel lblConfirmScrNum;
    private javax.swing.JLabel lblConfirmScreen;
    private javax.swing.JLabel lblConfirmTime;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblCountryHeading;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDate3;
    private javax.swing.JLabel lblDirector;
    private javax.swing.JLabel lblDirectorHeading;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblDiscountName;
    private javax.swing.JLabel lblDistributor;
    private javax.swing.JLabel lblDistributorHeading;
    private javax.swing.JLabel lblEnterNum;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblFLtitle;
    private javax.swing.JLabel lblFamInfo;
    private javax.swing.JLabel lblFamilyInfo;
    private javax.swing.JLabel lblFilmName;
    private javax.swing.JLabel lblFilmTitle;
    private javax.swing.JLabel lblFirst;
    private javax.swing.JLabel lblGenre;
    private javax.swing.JLabel lblGenreHeading;
    private javax.swing.JLabel lblLanguage;
    private javax.swing.JLabel lblLanguageHeading;
    private javax.swing.JLabel lblLast;
    private javax.swing.JLabel lblMarbleArch;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblOWmessage;
    private javax.swing.JLabel lblOdeon;
    private javax.swing.JLabel lblPGinfo;
    private javax.swing.JLabel lblPeak;
    private javax.swing.JLabel lblPlotHeading;
    private javax.swing.JLabel lblPoints;
    private javax.swing.JLabel lblPreAduTotal;
    private javax.swing.JLabel lblPreAdult;
    private javax.swing.JLabel lblPreAdultName;
    private javax.swing.JLabel lblPreAdultPrice;
    private javax.swing.JLabel lblPreChiTotal;
    private javax.swing.JLabel lblPreChild;
    private javax.swing.JLabel lblPreChildName;
    private javax.swing.JLabel lblPreChildPrice;
    private javax.swing.JLabel lblPreInfo;
    private javax.swing.JLabel lblPreSenTotal;
    private javax.swing.JLabel lblPreSenior;
    private javax.swing.JLabel lblPreSeniorName;
    private javax.swing.JLabel lblPreSeniorPrice;
    private javax.swing.JLabel lblPreStuNumTix;
    private javax.swing.JLabel lblPreStuTotal;
    private javax.swing.JLabel lblPreStudent;
    private javax.swing.JLabel lblPreStudentName;
    private javax.swing.JLabel lblPreStudentPrice;
    private javax.swing.JLabel lblPreTeeTotal;
    private javax.swing.JLabel lblPreTeen;
    private javax.swing.JLabel lblPreTeenName;
    private javax.swing.JLabel lblPreTeenPrice;
    private javax.swing.JLabel lblPremier;
    private javax.swing.JLabel lblPremierClubTitle;
    private javax.swing.JLabel lblPrices;
    private javax.swing.JLabel lblRunTime;
    private javax.swing.JLabel lblRunTimeHeading;
    private javax.swing.JLabel lblScreen;
    private javax.swing.JLabel lblScreenNum;
    private javax.swing.JLabel lblSearchTitle;
    private javax.swing.JLabel lblSeatingMap;
    private javax.swing.JLabel lblSenNumTixCon;
    private javax.swing.JLabel lblSenTotalCon;
    private javax.swing.JLabel lblSeniorInfo;
    private javax.swing.JLabel lblSentence1;
    private javax.swing.JLabel lblSentence2;
    private javax.swing.JLabel lblShowingTitle;
    private javax.swing.JLabel lblStandard;
    private javax.swing.JLabel lblStnAduTotal;
    private javax.swing.JLabel lblStnAdult;
    private javax.swing.JLabel lblStnAdultName;
    private javax.swing.JLabel lblStnAdultName1;
    private javax.swing.JLabel lblStnAdultNumTix;
    private javax.swing.JLabel lblStnAdultPrice;
    private javax.swing.JLabel lblStnChiNumTix;
    private javax.swing.JLabel lblStnChiTotal;
    private javax.swing.JLabel lblStnChild;
    private javax.swing.JLabel lblStnChildName;
    private javax.swing.JLabel lblStnChildName1;
    private javax.swing.JLabel lblStnChildPrice;
    private javax.swing.JLabel lblStnInfo;
    private javax.swing.JLabel lblStnSenNumTix;
    private javax.swing.JLabel lblStnSenTotal;
    private javax.swing.JLabel lblStnSenior;
    private javax.swing.JLabel lblStnSeniorName;
    private javax.swing.JLabel lblStnSeniorName1;
    private javax.swing.JLabel lblStnSeniorPrice;
    private javax.swing.JLabel lblStnStuNumTix;
    private javax.swing.JLabel lblStnStuTotal;
    private javax.swing.JLabel lblStnStudent;
    private javax.swing.JLabel lblStnStudentName;
    private javax.swing.JLabel lblStnStudentName1;
    private javax.swing.JLabel lblStnStudentPrice;
    private javax.swing.JLabel lblStnTeeNumTix;
    private javax.swing.JLabel lblStnTeeTotal;
    private javax.swing.JLabel lblStnTeen;
    private javax.swing.JLabel lblStnTeenName;
    private javax.swing.JLabel lblStnTeenName1;
    private javax.swing.JLabel lblStnTeenPrice;
    private javax.swing.JLabel lblStuNumTixCon;
    private javax.swing.JLabel lblStuTotalCon;
    private javax.swing.JLabel lblStudentInfo;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblSubTotalName;
    private javax.swing.JLabel lblSuperSaver;
    private javax.swing.JLabel lblTeeNumTixCon;
    private javax.swing.JLabel lblTeeTotalCon;
    private javax.swing.JLabel lblTeenInfo;
    private javax.swing.JLabel lblTicketMessage;
    private javax.swing.JLabel lblTicketsHeading;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTime1;
    private javax.swing.JLabel lblTime2;
    private javax.swing.JLabel lblTime3;
    private javax.swing.JLabel lblTimes;
    private javax.swing.JLabel lblTimesHeading;
    private javax.swing.JLabel lblTotalName;
    private javax.swing.JLabel lblTotalNumTix;
    private javax.swing.JLabel lblTotalNumTixCon;
    private javax.swing.JLabel lblTotalPoints;
    private javax.swing.JLabel lblTotalPointsTitle;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lblTotalPriceCon;
    private javax.swing.JLabel lblUinfo;
    private javax.swing.JLabel lblWelcomeScr;
    private javax.swing.JLabel lblX1;
    private javax.swing.JLabel lblX10;
    private javax.swing.JLabel lblX11;
    private javax.swing.JLabel lblX12;
    private javax.swing.JLabel lblX13;
    private javax.swing.JLabel lblX14;
    private javax.swing.JLabel lblX15;
    private javax.swing.JLabel lblX16;
    private javax.swing.JLabel lblX17;
    private javax.swing.JLabel lblX2;
    private javax.swing.JLabel lblX3;
    private javax.swing.JLabel lblX4;
    private javax.swing.JLabel lblX5;
    private javax.swing.JLabel lblX6;
    private javax.swing.JLabel lblX7;
    private javax.swing.JLabel lblX8;
    private javax.swing.JLabel lblX9;
    private javax.swing.JLabel lblpreAduNumTix;
    private javax.swing.JLabel lblpreChiNumTix;
    private javax.swing.JLabel lblpreSenNumTix;
    private javax.swing.JLabel lblpreTeeNumTix;
    private javax.swing.JList listFilms;
    private javax.swing.JList listTimes;
    private javax.swing.JPanel panBBFC;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBooking;
    private javax.swing.JPanel panCinemaTimes;
    private javax.swing.JPanel panConfirm;
    private javax.swing.JPanel panConfirmInfo;
    private javax.swing.JPanel panFilmInfo;
    private javax.swing.JPanel panFilmInfo3;
    private javax.swing.JPanel panFilms;
    private javax.swing.JPanel panMap;
    private javax.swing.JPanel panOW;
    private javax.swing.JPanel panPremierClub;
    private javax.swing.JPanel panScreenings;
    private javax.swing.JPanel panSearch;
    private javax.swing.JPanel panSeats;
    private javax.swing.JPanel panTicketPrice;
    private javax.swing.JPanel panTickets;
    private javax.swing.JPanel panWelcome;
    private javax.swing.JTabbedPane pantabbedBooking;
    private javax.swing.JScrollPane scrBBFCinfo;
    private javax.swing.JScrollPane scrPlot;
    private javax.swing.JScrollPane scrSearchedFilms;
    private javax.swing.JScrollPane scrTimes;
    private javax.swing.JTabbedPane tabPrices;
    private javax.swing.JTable tbl3DFilms;
    private javax.swing.JTable tblPremier;
    private javax.swing.JTable tblPremier3D;
    private javax.swing.JTable tblSearchedFilms;
    private javax.swing.JTable tblStandard;
    private javax.swing.JTable tblTimes;
    private javax.swing.JTextPane txtBBFCinfo;
    private javax.swing.JTextField txtCardNumber;
    private javax.swing.JTextPane txtPlot;
    private javax.swing.JTextField txtSearchFilm;
    private javax.swing.JTextField txtSpendPoints;
    // End of variables declaration//GEN-END:variables

}
