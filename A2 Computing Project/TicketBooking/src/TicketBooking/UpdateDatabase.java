/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UpdateDatabase.java
 *
 * Created on 16-Jan-2011, 16:27:52
 */

package TicketBooking;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.*;

public class UpdateDatabase extends javax.swing.JFrame {
    
    /** Creates new form UpdateDatabase */
    public UpdateDatabase() {
        initComponents();
    }

    //Adds a new film record to the FILMS table
    public void addFilms()
    {
       String film_ID = "";
       int filmID = 0;
       boolean correct = true;

       try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           //create and execute query
           String query = "SELECT * from FILMS ORDER BY FILM_ID";
           ResultSet result = stmt.executeQuery(query);

           //loop through until goes to last record
           if (result != null)
           {
                while (result.next())
                {
                film_ID = result.getString("FILM_ID");
                }
           }

           //Converts string to integer and then adds one
           filmID = Integer.parseInt(film_ID);
           filmID = filmID + 1;

           //get the fields from the interface
           String film = txtFilm.getText();
           String shortFilm = txtSfilm.getText();
           String BBFC = boxBBFC.getSelectedItem().toString();
           String distributor = txtDistributor.getText();
           String director = txtDirector.getText();
           String origin = txtOrigin.getText();
           int runtime = 0;
           String genre = txtGenre.getText();
           String filmLanguage = txtLanguage.getText();
           String BBFCinfo = txtBBFCinfo.getText();
           String plot = txtPlot.getText();
           String showing = boxShowing.getSelectedItem().toString();

           //Converting String to char
           char showingChar = showing.charAt(0);

            //Validating if appropriate value have been inputted
           try
           {
            runtime = Integer.parseInt(txtRunTime.getText());
           }
           catch (Exception e)
           {
           lblMessageFilms.setText("The run time has to be a number");
           correct = false;
           }

           //Checking if any important fields have been inputted
           if (film.equals("")||shortFilm.equals("")||showing.equals(""))
           {
            lblMessageFilms.setText("Data which is needed is missing");
            correct = false;
           }

           if(correct == true)
           {
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO FILMS (FILM_ID, FILM_NAME, SHORT_NAME, BBFC_CERTIFICATE,"
           + "BBFC_INFO, DIRECTOR, ORIGIN, RUN_TIME, GENRE, SYNOPSIS, FILM_LANGUAGE, DISTRIBUTOR, SHOWING) VALUES"
           + "("+ filmID +",'" + film + "','" + shortFilm + "','" + BBFC + "','"+ BBFCinfo + "','" + director +"','"+ origin +"',"+ runtime +",'" + genre + "','"+ plot + "','" + filmLanguage +"','"+ distributor +"','"+ showingChar +"')");
    
           stmt.close();
           conn.close();

           lblMessageFilms.setText("Record added");

           }
        }
         catch (Exception ex)
         {
             System.err.print("SQLException: ");
             System.err.println(ex.getMessage());
         }
    }
    
    //Adds a new screenings record to the SCREENINGS table
    public void addScreenings()
    {
        int screeningID = 0;
        boolean correct = true;

        try{
           //get the field from the interface
           String screeningDate = txtDate.getText();
           String screeningTime = txtTime.getText();
           String clubType = boxClubType.getSelectedItem().toString();
           int screenNumber = 0;
           String screeningType = boxScreeningType.getSelectedItem().toString();
           String priceType = boxPriceType.getSelectedItem().toString();
           int filmID = 0;
           int cinemaID = 0;
           char scrStatus = 'Y';

           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           //create and execute query
           String query = "SELECT SCREENING_ID from SCREENINGS ORDER BY SCREENING_ID";
           ResultSet result = stmt.executeQuery(query);

           //loop through until goes to last record
           if (result != null)
           {
            while (result.next())
             {
              screeningID = result.getInt("SCREENING_ID");
             }
           }

           //Add one to screeningID which is the primary key
           screeningID = screeningID + 1;

            //Check if data inputted screen number field is a number
           try{
               screenNumber = Integer.parseInt(txtScrNum.getText());
           }
           catch(NumberFormatException e)
           {
               lblMessageScr.setText("Screen Number needs to be a number");
               correct = false;
           }
          //Check if data inputted screen number field is a number
           try{
               filmID = Integer.parseInt(txtFilmScr.getText());
           }
           catch(NumberFormatException e)
           {
               lblMessageScr.setText("Film ID needs to be a number");
               correct = false;
           }

           //Check if data inputted screen number field is a number
           try{
               cinemaID = Integer.parseInt(txtCinemaID.getText());
           }
           catch(NumberFormatException e)
           {
               lblMessageScr.setText("Cinema ID needs to be a number");
               correct = false;
           }

           //Check if film ID typed in is less than 10 characters
           try{
               if(txtFilmScr.getText().length() > 10)
               {
                   throw new Exception();
               }
           }
           catch(Exception e)
           {
               lblMessageScr.setText("Too many characters have been entered");
               correct = false;
           }

           //Check if cinema ID typed in is less than 10 characters
           try{
               if(txtCinemaID.getText().length() > 10)
               {
                   throw new Exception();
               }
           }
           catch(Exception e)
           {
               lblMessageScr.setText("Too many characters have been entered");
               correct = false;
           }

           //Checking if any important fields have been inputted
           if (screeningDate.equals("")||screeningTime.equals(""))
           {
            lblMessageScr.setText("Fields haven't been filled yet");
            correct = false;
           }

           //Validating a Date
           try {
               //set desired format
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
               //restrict what will be accepted as a date
               sdf.setLenient(false);
               sdf.parse(txtDate.getText());
           }
           catch (ParseException e)
           {
               lblMessageScr.setText("invalid date entered");
               correct = false;
           }

           //Validating a Time
           try {
               SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
               sdf.setLenient(false);
               sdf.parse(txtTime.getText());
           }
           catch (ParseException e)
           {
               lblMessageScr.setText("invalid time entered, only 24 hours");
               correct = false;
           }

           //Converting the dd/mm/yyyy string into the format required by SQL
           SimpleDateFormat inFmt= new SimpleDateFormat("dd/MM/yyyy");
           SimpleDateFormat outFmt = new SimpleDateFormat("yyyy-MM-dd");

           try{
               screeningDate = outFmt.format(inFmt.parse(txtDate.getText()));
           }
           catch (ParseException e){
           }

           if(correct == true)
           {
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO SCREENINGS (SCREENING_ID, FILM_ID, SDATE, CLUB_TYPE,"
           + "SCREENING_TYPE, CINEMA_ID, SCREENING_STATUS, STIME, PRICE_TYPE, SCREEN_NUMBER) VALUES"
           + "(" + screeningID + "," + filmID + ",'" + screeningDate + "','" + clubType + "','" + screeningType +"',"+ cinemaID +",'"+ scrStatus +"','" + screeningTime + "','"+ priceType +"',"+ screenNumber + ")");

           lblMessageScr.setText("Record added");
           
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

    //Adds a new cinema record to the CINEMAS table
    public void addCinemas()
    {
       //get the field from the interface
        String cinema_ID = "";
        int cinemaID = 0;
        boolean correct = true;

        String cinemaName = txtCinemaName.getText();
        String cinemaInfo = txtCinemaInfo.getText();
        String accessInfo = txtAccessInfo.getText();

         try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

                      //create and execute query
           String query = "SELECT * from CINEMAS ORDER BY CINEMA_ID";
           ResultSet result = stmt.executeQuery(query);

           //loop through until goes to last record
           if (result != null)
           {
                while (result.next())
                {
                cinema_ID = result.getString("CINEMA_ID");
                }
           }

           //Add one to cinemaID so a new record can be made
           cinemaID = Integer.parseInt(cinema_ID);
           cinemaID = cinemaID + 1;

           //Presence check on Cinema Name field
            if (cinemaName.equals(""))
           {
            lblMessageCin.setText("Cinema Name needs be entered");
            correct = false;
           }

           if(correct == true)
           {
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO CINEMAS (CINEMA_ID, CINEMA_NAME, CINEMA_INFO, ACCESS_INFO) VALUES"
           + "(" + cinemaID + ",'" + cinemaName + "','" + cinemaInfo + "','" + accessInfo +"')");

           lblMessageCin.setText("Record added");

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

    //Adds a new price record to the STANDARDINFO table
    public void addStandardPrices()
    {
        boolean correct = true;
        Double superSaver = 0.00;
        Double peak = 0.00;

        //get the field from the interface
        String ticketType = txtStnTicketType.getText();

        try{
        superSaver = Double.parseDouble(txtStnSuperSaver.getText());
        peak = Double.parseDouble(txtStnPeak.getText());
        }
        catch (NumberFormatException e)
        {
         lblMessagePrices.setText("Price has to be in correct format");
        }

        //Presence check on fields
        if (ticketType.equals(""))
        {
        lblMessagePrices.setText("All fields needs to be entered");
        correct = false;
        }
        
        try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           if(correct == true)
           {
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO STANDARDINFO (TICKET_TYPE, SUPER_SAVER, PEAK) VALUES"
           + "('" + ticketType + "'," + superSaver + "," + peak + ")");

           lblMessagePrices.setText("Record added");

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

    //Adds a new price record to the THREEDINFO table
    public void add3DPrices()
    {
        boolean correct = true;
        Double superSaver = 0.00;
        Double peak = 0.00;

       //get the field from the interface
        String ticketType = txt3DTicketType.getText();

        try{
        superSaver = Double.parseDouble(txt3DSuperSaver.getText());
        peak = Double.parseDouble(txt3DPeak.getText());
        }
        catch (NumberFormatException e)
        {
            lblMessagePrices.setText("Price has to be in correct format");
        }

        //Presence check on fields
        if (ticketType.equals(""))
        {
        lblMessagePrices.setText("All fields needs to be entered");
        correct = false;
        }

         try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           if(correct == true){
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO STANDARDINFO (TICKET_TYPE, SUPER_SAVER, PEAK) VALUES"
           + "('" + ticketType + "','" + superSaver + "','" + peak + "')");

           lblMessagePrices.setText("Record added");

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

    //Adds a new price record to the PREMIERINFO table
    public void addPremierPrices()
    {
        boolean correct = true;
        Double superSaver = 0.00;
        Double peak = 0.00;

       //get the field from the interface
        String ticketType = txtPreTicketType.getText();

        try{
        superSaver = Double.parseDouble(txtPreSuperSaver.getText());
        peak = Double.parseDouble(txtPrePeak.getText());
        }
        catch (NumberFormatException e)
        {
            lblMessagePrices.setText("Price has to be in correct format");
        }

                   //Presence check on fields
            if (ticketType.equals(""))
           {
            lblMessagePrices.setText("All fields needs to be entered");
            correct = false;
           }

         try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           if(correct == true){
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO STANDARDINFO (TICKET_TYPE, SUPER_SAVER, PEAK) VALUES"
           + "('" + ticketType + "','" + superSaver + "','" + peak + "')");

           lblMessagePrices.setText("Record added");

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

    //Adds a new price record to the THREEDPREMIERINFO table
    public void add3DPremierPrices()
    {
        boolean correct = true;
        Double superSaver = 0.00;
        Double peak = 0.00;

        //get the field from the interface
        String ticketType = txt3DPreTicketTypeID.getText();

        try{
        superSaver = Double.parseDouble(txt3DPreSuperSaver.getText());
        peak = Double.parseDouble(txt3DPrePeak.getText());
        }
        catch (NumberFormatException e)
        {
            lblMessagePrices.setText("Price has to be in correct format");
        }

                   //Presence check on fields
            if (ticketType.equals(""))
           {
            lblMessagePrices.setText("All fields needs to be entered");
            correct = false;
           }

         try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           if(correct == true){
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO STANDARDINFO (TICKET_TYPE, SUPER_SAVER, PEAK) VALUES"
           + "('" + ticketType + "','" + superSaver + "','" + peak + "')");

           lblMessagePrices.setText("Record added");

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

    //Adds a staff record to the STAFF table
    public void addStaff()
    {
     try{
        int staffID = 0;
        String staff_ID = "";
        boolean correct = true;

        //connect to the database
        DatabaseConnect.init();
        Connection conn = DatabaseConnect.getConnection();

        //enable SQL statements to be created and run
        Statement stmt = conn.createStatement();
       
        //get the field from the interface
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String occupation = boxOccupation.getSelectedItem().toString();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        //create and execute query
        String query = "SELECT * from STAFF ORDER BY CASHIER_NUMBER";
        ResultSet result = stmt.executeQuery(query);

        //loop through until goes to last record
        if (result != null)
          {
           while (result.next())
           {
            staff_ID = result.getString("CASHIER_NUMBER");
           }
          }

        //Add one to cinemaID so a new record can be made
        staffID = Integer.parseInt(staff_ID);
        staffID = staffID + 1;

        if(username.equals(""))
        {
         lblMessageStaff.setText("Username needs to be entered");
         correct = false;
        }

        if(password.equals(""))
        {
         lblMessageStaff.setText("Password needs to be entered");
         correct = false;
        }

        if(correct == true)
         {
           //use the values you have set to insert the data.
           stmt.execute("INSERT INTO STAFF (CASHIER_NUMBER, FIRST_NAME, LAST_NAME, OCCUPATION, USER_NAME, PASSWORD ) VALUES"
           + "(" + staffID + ",'" + firstName + "','" + lastName + "','" + occupation + "','" + username + "','" + password + "')");

           lblMessageStaff.setText("Record added");
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        panUpdate = new javax.swing.JPanel();
        panTabbedUpdate = new javax.swing.JTabbedPane();
        panAddFilms = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFilm = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSfilm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDirector = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDistributor = new javax.swing.JTextField();
        txtOrigin = new javax.swing.JTextField();
        txtRunTime = new javax.swing.JTextField();
        txtGenre = new javax.swing.JTextField();
        txtLanguage = new javax.swing.JTextField();
        boxBBFC = new javax.swing.JComboBox();
        txtBBFCinfo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPlot = new javax.swing.JTextField();
        btnFilmAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblMessageFilms = new javax.swing.JLabel();
        boxShowing = new javax.swing.JComboBox();
        panAddScreenings = new javax.swing.JPanel();
        lblFilmHeading = new javax.swing.JLabel();
        lblDateHeading = new javax.swing.JLabel();
        lblTimeHeading = new javax.swing.JLabel();
        txtFilmScr = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        lblClubTypeHeading = new javax.swing.JLabel();
        lblScreenNumHeading = new javax.swing.JLabel();
        lblScreenType = new javax.swing.JLabel();
        lblCinemaID = new javax.swing.JLabel();
        txtScrNum = new javax.swing.JTextField();
        txtCinemaID = new javax.swing.JTextField();
        btnAddScreenings = new javax.swing.JButton();
        boxClubType = new javax.swing.JComboBox();
        boxScreeningType = new javax.swing.JComboBox();
        lblMessageScr = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        boxPriceType = new javax.swing.JComboBox();
        panAddCinemas = new javax.swing.JPanel();
        lblCinemaName = new javax.swing.JLabel();
        txtCinemaName = new javax.swing.JTextField();
        lblCinemaInfo = new javax.swing.JLabel();
        lblAccessInfo = new javax.swing.JLabel();
        txtCinemaInfo = new javax.swing.JTextField();
        txtAccessInfo = new javax.swing.JTextField();
        btnAddCinemas = new javax.swing.JButton();
        lblMessageCin = new javax.swing.JLabel();
        panAddPrices = new javax.swing.JPanel();
        lblStnTicketTypeID = new javax.swing.JLabel();
        lblStnTicketType = new javax.swing.JLabel();
        txtStnTicketType = new javax.swing.JTextField();
        txtStnSuperSaver = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        lblStnTimeType = new javax.swing.JLabel();
        txtStnPeak = new javax.swing.JTextField();
        lblStandard = new javax.swing.JLabel();
        lbl3DTicketTypeID = new javax.swing.JLabel();
        txt3DTicketType = new javax.swing.JTextField();
        txt3DSuperSaver = new javax.swing.JTextField();
        lbl3DTicketType = new javax.swing.JLabel();
        txt3DPeak = new javax.swing.JTextField();
        lbl3D = new javax.swing.JLabel();
        lblPreTicketTypeID = new javax.swing.JLabel();
        txtPreTicketType = new javax.swing.JTextField();
        txtPreSuperSaver = new javax.swing.JTextField();
        lblPreTicketType = new javax.swing.JLabel();
        txtPrePeak = new javax.swing.JTextField();
        lblPremier = new javax.swing.JLabel();
        lbl3DPremier = new javax.swing.JLabel();
        lbl3DPreTicketTypeID = new javax.swing.JLabel();
        txt3DPreTicketTypeID = new javax.swing.JTextField();
        txt3DPreSuperSaver = new javax.swing.JTextField();
        lbl3DPreTicketType = new javax.swing.JLabel();
        txt3DPrePeak = new javax.swing.JTextField();
        lbl3DTimeType = new javax.swing.JLabel();
        lblPreTimeType = new javax.swing.JLabel();
        lbl3DPreTimeType = new javax.swing.JLabel();
        btnStandardPrices = new javax.swing.JButton();
        btnAdd3DPrices = new javax.swing.JButton();
        btnPremierPrices = new javax.swing.JButton();
        btn3DPremierPrices = new javax.swing.JButton();
        lblMessagePrices = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        panAddStaff = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblOccupation = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        btnAddStaff = new javax.swing.JButton();
        boxOccupation = new javax.swing.JComboBox();
        lblMessageStaff = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblUpdate = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 103, 204));

        panUpdate.setBackground(new java.awt.Color(0, 153, 204));
        panUpdate.setForeground(new java.awt.Color(0, 103, 204));

        panAddFilms.setBackground(new java.awt.Color(0, 102, 204));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Film Name*");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Short Hand Film Name*");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("BBFC Certificate*");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("BBFC Information");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Director");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Origin");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Run Time*");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Genre");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Language");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Distributor");

        boxBBFC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "U", "PG", "12A", "15", "18", "TBD" }));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Plot");

        btnFilmAdd.setText("Add Record");
        btnFilmAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilmAddActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Currently Showing*");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("* - Has to be inputted");

        lblMessageFilms.setForeground(new java.awt.Color(255, 255, 255));

        boxShowing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Y", "N" }));

        javax.swing.GroupLayout panAddFilmsLayout = new javax.swing.GroupLayout(panAddFilms);
        panAddFilms.setLayout(panAddFilmsLayout);
        panAddFilmsLayout.setHorizontalGroup(
            panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddFilmsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boxShowing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSfilm)
                    .addComponent(txtFilm, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(txtDistributor))
                .addGap(22, 22, 22)
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtOrigin)
                    .addComponent(txtDirector, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(txtLanguage)
                    .addComponent(txtGenre, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(txtRunTime))
                .addContainerGap())
            .addGroup(panAddFilmsLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(btnFilmAdd)
                .addContainerGap(423, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panAddFilmsLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddFilmsLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panAddFilmsLayout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panAddFilmsLayout.createSequentialGroup()
                            .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtBBFCinfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                                .addComponent(txtPlot, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addComponent(lblMessageFilms, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        panAddFilmsLayout.setVerticalGroup(
            panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddFilmsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddFilmsLayout.createSequentialGroup()
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtRunTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panAddFilmsLayout.createSequentialGroup()
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(panAddFilmsLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtSfilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(boxBBFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDistributor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(boxShowing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBBFCinfo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddFilmsLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMessageFilms, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                    .addComponent(txtPlot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFilmAdd)
                .addContainerGap())
        );

        panTabbedUpdate.addTab("Add Films", panAddFilms);

        panAddScreenings.setBackground(new java.awt.Color(0, 102, 204));

        lblFilmHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblFilmHeading.setText("Film ID*");

        lblDateHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblDateHeading.setText("Date*");

        lblTimeHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeHeading.setText("Time*");

        lblClubTypeHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblClubTypeHeading.setText("Club Type*");

        lblScreenNumHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblScreenNumHeading.setText("Screen Number*");

        lblScreenType.setForeground(new java.awt.Color(255, 255, 255));
        lblScreenType.setText("Screening Type*");

        lblCinemaID.setForeground(new java.awt.Color(255, 255, 255));
        lblCinemaID.setText("Cinema ID*");

        btnAddScreenings.setText("Add Record");
        btnAddScreenings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddScreeningsActionPerformed(evt);
            }
        });

        boxClubType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Standard", "OdeonPlus", "ODEON Kids", "ODEON Senior Screen", "ODEON Newbies", "ODEON Bollywood" }));

        boxScreeningType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Standard", "3D" }));

        lblMessageScr.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Price Type*");

        boxPriceType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Super Saver", "Peak" }));

        javax.swing.GroupLayout panAddScreeningsLayout = new javax.swing.GroupLayout(panAddScreenings);
        panAddScreenings.setLayout(panAddScreeningsLayout);
        panAddScreeningsLayout.setHorizontalGroup(
            panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddScreeningsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMessageScr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addGroup(panAddScreeningsLayout.createSequentialGroup()
                        .addComponent(lblCinemaID)
                        .addGap(70, 70, 70)
                        .addComponent(txtCinemaID, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                    .addGroup(panAddScreeningsLayout.createSequentialGroup()
                        .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblScreenNumHeading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblClubTypeHeading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addComponent(lblScreenType))
                            .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblFilmHeading, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDateHeading, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTimeHeading, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(txtFilmScr, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(txtScrNum, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(boxClubType, 0, 334, Short.MAX_VALUE)
                            .addComponent(boxScreeningType, 0, 334, Short.MAX_VALUE)
                            .addComponent(btnAddScreenings)
                            .addComponent(boxPriceType, 0, 334, Short.MAX_VALUE))))
                .addGap(316, 316, 316))
        );
        panAddScreeningsLayout.setVerticalGroup(
            panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panAddScreeningsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFilmHeading)
                    .addComponent(txtFilmScr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateHeading)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimeHeading)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClubTypeHeading)
                    .addComponent(boxClubType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtScrNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScreenNumHeading))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblScreenType)
                    .addComponent(boxScreeningType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(boxPriceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panAddScreeningsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCinemaID)
                    .addComponent(txtCinemaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(btnAddScreenings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMessageScr, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        panTabbedUpdate.addTab("Add Screenings", panAddScreenings);

        panAddCinemas.setBackground(new java.awt.Color(0, 102, 204));

        lblCinemaName.setForeground(new java.awt.Color(255, 255, 255));
        lblCinemaName.setText("Cinema Name*");

        lblCinemaInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblCinemaInfo.setText("Cinema Information");

        lblAccessInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblAccessInfo.setText("Accessiblility Information");

        btnAddCinemas.setText("Add Record");
        btnAddCinemas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCinemasActionPerformed(evt);
            }
        });

        lblMessageCin.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panAddCinemasLayout = new javax.swing.GroupLayout(panAddCinemas);
        panAddCinemas.setLayout(panAddCinemasLayout);
        panAddCinemasLayout.setHorizontalGroup(
            panAddCinemasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddCinemasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddCinemasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddCinemasLayout.createSequentialGroup()
                        .addComponent(lblCinemaInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                        .addGap(643, 643, 643))
                    .addGroup(panAddCinemasLayout.createSequentialGroup()
                        .addComponent(lblAccessInfo)
                        .addContainerGap(657, Short.MAX_VALUE))
                    .addGroup(panAddCinemasLayout.createSequentialGroup()
                        .addGroup(panAddCinemasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAccessInfo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCinemaInfo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddCinemasLayout.createSequentialGroup()
                                .addComponent(lblCinemaName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCinemaName, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(panAddCinemasLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(btnAddCinemas)
                .addGap(102, 102, 102)
                .addComponent(lblMessageCin, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addGap(156, 156, 156))
        );
        panAddCinemasLayout.setVerticalGroup(
            panAddCinemasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddCinemasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddCinemasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCinemaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCinemaName))
                .addGap(32, 32, 32)
                .addComponent(lblCinemaInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCinemaInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAccessInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAccessInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panAddCinemasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCinemas)
                    .addComponent(lblMessageCin, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panTabbedUpdate.addTab("Add Cinemas", panAddCinemas);

        panAddPrices.setBackground(new java.awt.Color(0, 102, 204));

        lblStnTicketTypeID.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTicketTypeID.setText("Ticket Type");

        lblStnTicketType.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTicketType.setText("Super Saver");

        lblStnTimeType.setForeground(new java.awt.Color(255, 255, 255));
        lblStnTimeType.setText("Peak");

        txtStnPeak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStnPeakActionPerformed(evt);
            }
        });

        lblStandard.setForeground(new java.awt.Color(255, 255, 255));
        lblStandard.setText("Standard Prices");

        lbl3DTicketTypeID.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DTicketTypeID.setText("Ticket Type");

        lbl3DTicketType.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DTicketType.setText("Super Saver");

        lbl3D.setForeground(new java.awt.Color(255, 255, 255));
        lbl3D.setText("3D Prices");

        lblPreTicketTypeID.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTicketTypeID.setText("Ticket Type");

        lblPreTicketType.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTicketType.setText("Super Saver");

        lblPremier.setForeground(new java.awt.Color(255, 255, 255));
        lblPremier.setText("Premier Prices");

        lbl3DPremier.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DPremier.setText("3D and Premier Prices");

        lbl3DPreTicketTypeID.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DPreTicketTypeID.setText("Ticket Type");

        lbl3DPreTicketType.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DPreTicketType.setText("Super Saver");

        lbl3DTimeType.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DTimeType.setText("Peak");

        lblPreTimeType.setForeground(new java.awt.Color(255, 255, 255));
        lblPreTimeType.setText("Peak");

        lbl3DPreTimeType.setForeground(new java.awt.Color(255, 255, 255));
        lbl3DPreTimeType.setText("Peak");

        btnStandardPrices.setText("Add Record");
        btnStandardPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStandardPricesActionPerformed(evt);
            }
        });

        btnAdd3DPrices.setText("Add Record");
        btnAdd3DPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd3DPricesActionPerformed(evt);
            }
        });

        btnPremierPrices.setText("Add Record");
        btnPremierPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPremierPricesActionPerformed(evt);
            }
        });

        btn3DPremierPrices.setText("Add Record");
        btn3DPremierPrices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3DPremierPricesActionPerformed(evt);
            }
        });

        lblMessagePrices.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panAddPricesLayout = new javax.swing.GroupLayout(panAddPrices);
        panAddPrices.setLayout(panAddPricesLayout);
        panAddPricesLayout.setHorizontalGroup(
            panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddPricesLayout.createSequentialGroup()
                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMessagePrices, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(jLabel28))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStnTicketTypeID)
                            .addComponent(lblStnTicketType))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStnSuperSaver)
                            .addComponent(txtStnTicketType, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addComponent(lblStnTimeType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStnPeak, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStandardPrices))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblStandard, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblPremier))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl3DPremier))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl3DPreTicketTypeID)
                            .addComponent(lbl3DPreTicketType))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt3DPreSuperSaver)
                            .addComponent(txt3DPreTicketTypeID, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addComponent(lbl3DPreTimeType)
                        .addGap(10, 10, 10)
                        .addComponent(txt3DPrePeak, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn3DPremierPrices))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lbl3D))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator2)
                            .addGroup(panAddPricesLayout.createSequentialGroup()
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl3DTicketTypeID)
                                    .addComponent(lbl3DTicketType))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt3DSuperSaver)
                                    .addComponent(txt3DTicketType, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                                .addGap(15, 15, 15)
                                .addComponent(lbl3DTimeType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt3DPeak, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd3DPrices))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panAddPricesLayout.createSequentialGroup()
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPreTicketTypeID)
                                    .addComponent(lblPreTicketType))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPreSuperSaver)
                                    .addComponent(txtPreTicketType, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                                .addGap(9, 9, 9)
                                .addComponent(lblPreTimeType)
                                .addGap(10, 10, 10)
                                .addComponent(txtPrePeak, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPremierPrices)))))
                .addGap(221, 221, 221))
        );
        panAddPricesLayout.setVerticalGroup(
            panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddPricesLayout.createSequentialGroup()
                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddPricesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(lblStandard))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnTicketTypeID)
                            .addComponent(txtStnTicketType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStnTicketType)
                            .addComponent(txtStnSuperSaver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStnPeak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStnTimeType)))
                    .addGroup(panAddPricesLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnStandardPrices)))
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddPricesLayout.createSequentialGroup()
                        .addComponent(lbl3D)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl3DTicketTypeID)
                            .addComponent(txt3DTicketType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt3DSuperSaver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt3DPeak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl3DTimeType)
                            .addComponent(lbl3DTicketType)))
                    .addGroup(panAddPricesLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnAdd3DPrices)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAddPricesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panAddPricesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPremier)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblPreTicketTypeID)
                                    .addComponent(txtPreTicketType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblPreTicketType)
                                    .addComponent(txtPreSuperSaver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrePeak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panAddPricesLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(lblPreTimeType)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panAddPricesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lbl3DPremier)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl3DPreTicketTypeID)
                                    .addComponent(txt3DPreTicketTypeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panAddPricesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl3DPreTicketType)
                                    .addComponent(txt3DPreSuperSaver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt3DPrePeak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panAddPricesLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(lbl3DPreTimeType))))
                    .addGroup(panAddPricesLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnPremierPrices)
                        .addGap(62, 62, 62)
                        .addComponent(btn3DPremierPrices)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMessagePrices, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );

        panTabbedUpdate.addTab("Add Prices", panAddPrices);

        panAddStaff.setBackground(new java.awt.Color(0, 102, 204));

        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("First Name       ");

        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name       ");

        lblOccupation.setForeground(new java.awt.Color(255, 255, 255));
        lblOccupation.setText("Occupation     ");

        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username");

        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        btnAddStaff.setText("Add Record");
        btnAddStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStaffActionPerformed(evt);
            }
        });

        boxOccupation.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cashier", "Team Leader", "Manager" }));

        lblMessageStaff.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panAddStaffLayout = new javax.swing.GroupLayout(panAddStaff);
        panAddStaff.setLayout(panAddStaffLayout);
        panAddStaffLayout.setHorizontalGroup(
            panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMessageStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panAddStaffLayout.createSequentialGroup()
                        .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOccupation)
                            .addComponent(lblUsername)
                            .addComponent(lblFirstName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(boxOccupation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUsername)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLastName)
                            .addComponent(lblPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddStaff)
                            .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPassword)
                                .addComponent(txtLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)))))
                .addContainerGap(234, Short.MAX_VALUE))
        );
        panAddStaffLayout.setVerticalGroup(
            panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAddStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAddStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOccupation)
                    .addComponent(boxOccupation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddStaff))
                .addGap(18, 18, 18)
                .addComponent(lblMessageStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        panTabbedUpdate.addTab("Add Staff", panAddStaff);

        lblUpdate.setFont(new java.awt.Font("Impact", 0, 36));
        lblUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblUpdate.setText("UPDATE DATABASE");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panUpdateLayout = new javax.swing.GroupLayout(panUpdate);
        panUpdate.setLayout(panUpdateLayout);
        panUpdateLayout.setHorizontalGroup(
            panUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panTabbedUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panUpdateLayout.createSequentialGroup()
                        .addComponent(lblUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        panUpdateLayout.setVerticalGroup(
            panUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panTabbedUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStnPeakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStnPeakActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtStnPeakActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtUsernameActionPerformed

    private void btnFilmAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilmAddActionPerformed
    addFilms();
    }//GEN-LAST:event_btnFilmAddActionPerformed

    private void btnAddScreeningsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddScreeningsActionPerformed
    addScreenings();
}//GEN-LAST:event_btnAddScreeningsActionPerformed

    private void btnAddCinemasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCinemasActionPerformed
    addCinemas();
    }//GEN-LAST:event_btnAddCinemasActionPerformed

    private void btnStandardPricesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStandardPricesActionPerformed
    addStandardPrices();
}//GEN-LAST:event_btnStandardPricesActionPerformed

    private void btnAdd3DPricesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd3DPricesActionPerformed
    add3DPrices();
    }//GEN-LAST:event_btnAdd3DPricesActionPerformed

    private void btnPremierPricesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPremierPricesActionPerformed
    addPremierPrices();
    }//GEN-LAST:event_btnPremierPricesActionPerformed

    private void btn3DPremierPricesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3DPremierPricesActionPerformed
    add3DPremierPrices();
    }//GEN-LAST:event_btn3DPremierPricesActionPerformed

    private void btnAddStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStaffActionPerformed
    addStaff();
    }//GEN-LAST:event_btnAddStaffActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateDatabase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxBBFC;
    private javax.swing.JComboBox boxClubType;
    private javax.swing.JComboBox boxOccupation;
    private javax.swing.JComboBox boxPriceType;
    private javax.swing.JComboBox boxScreeningType;
    private javax.swing.JComboBox boxShowing;
    private javax.swing.JButton btn3DPremierPrices;
    private javax.swing.JButton btnAdd3DPrices;
    private javax.swing.JButton btnAddCinemas;
    private javax.swing.JButton btnAddScreenings;
    private javax.swing.JButton btnAddStaff;
    private javax.swing.JButton btnFilmAdd;
    private javax.swing.JButton btnPremierPrices;
    private javax.swing.JButton btnStandardPrices;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lbl3D;
    private javax.swing.JLabel lbl3DPreTicketType;
    private javax.swing.JLabel lbl3DPreTicketTypeID;
    private javax.swing.JLabel lbl3DPreTimeType;
    private javax.swing.JLabel lbl3DPremier;
    private javax.swing.JLabel lbl3DTicketType;
    private javax.swing.JLabel lbl3DTicketTypeID;
    private javax.swing.JLabel lbl3DTimeType;
    private javax.swing.JLabel lblAccessInfo;
    private javax.swing.JLabel lblCinemaID;
    private javax.swing.JLabel lblCinemaInfo;
    private javax.swing.JLabel lblCinemaName;
    private javax.swing.JLabel lblClubTypeHeading;
    private javax.swing.JLabel lblDateHeading;
    private javax.swing.JLabel lblFilmHeading;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblMessageCin;
    private javax.swing.JLabel lblMessageFilms;
    private javax.swing.JLabel lblMessagePrices;
    private javax.swing.JLabel lblMessageScr;
    private javax.swing.JLabel lblMessageStaff;
    private javax.swing.JLabel lblOccupation;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPreTicketType;
    private javax.swing.JLabel lblPreTicketTypeID;
    private javax.swing.JLabel lblPreTimeType;
    private javax.swing.JLabel lblPremier;
    private javax.swing.JLabel lblScreenNumHeading;
    private javax.swing.JLabel lblScreenType;
    private javax.swing.JLabel lblStandard;
    private javax.swing.JLabel lblStnTicketType;
    private javax.swing.JLabel lblStnTicketTypeID;
    private javax.swing.JLabel lblStnTimeType;
    private javax.swing.JLabel lblTimeHeading;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panAddCinemas;
    private javax.swing.JPanel panAddFilms;
    private javax.swing.JPanel panAddPrices;
    private javax.swing.JPanel panAddScreenings;
    private javax.swing.JPanel panAddStaff;
    private javax.swing.JTabbedPane panTabbedUpdate;
    private javax.swing.JPanel panUpdate;
    private javax.swing.JTextField txt3DPeak;
    private javax.swing.JTextField txt3DPrePeak;
    private javax.swing.JTextField txt3DPreSuperSaver;
    private javax.swing.JTextField txt3DPreTicketTypeID;
    private javax.swing.JTextField txt3DSuperSaver;
    private javax.swing.JTextField txt3DTicketType;
    private javax.swing.JTextField txtAccessInfo;
    private javax.swing.JTextField txtBBFCinfo;
    private javax.swing.JTextField txtCinemaID;
    private javax.swing.JTextField txtCinemaInfo;
    private javax.swing.JTextField txtCinemaName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDirector;
    private javax.swing.JTextField txtDistributor;
    private javax.swing.JTextField txtFilm;
    private javax.swing.JTextField txtFilmScr;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtGenre;
    private javax.swing.JTextField txtLanguage;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtOrigin;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPlot;
    private javax.swing.JTextField txtPrePeak;
    private javax.swing.JTextField txtPreSuperSaver;
    private javax.swing.JTextField txtPreTicketType;
    private javax.swing.JTextField txtRunTime;
    private javax.swing.JTextField txtScrNum;
    private javax.swing.JTextField txtSfilm;
    private javax.swing.JTextField txtStnPeak;
    private javax.swing.JTextField txtStnSuperSaver;
    private javax.swing.JTextField txtStnTicketType;
    private javax.swing.JTextField txtTime;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

}
