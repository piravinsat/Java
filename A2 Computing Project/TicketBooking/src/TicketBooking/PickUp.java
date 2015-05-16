/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PickUp.java
 *
 * Created on 13-Feb-2011, 21:31:40
 */

package TicketBooking;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.print.*;
import javax.swing.*;

/**
 * @author Piravin Satkunarajah
 */
public class PickUp extends javax.swing.JFrame {

    int bookingNum = 0;
    int quantity = 0;
    String bookingNumString = "";

    static JTextArea textarea = new JTextArea(10,40);
    static JFrame   window = new JFrame("Print");

    /** Creates new form PickUp */
    public PickUp() {
        initComponents();
    }

    //Retrieves the booking record from the BOOKINGS table using the booking
    //number but only if it hasn't been printed and then calls getTickets
    public void enterBookingNum()
    {
      bookingNumString = txtBookingNum.getText();
      
      if (bookingNumString.equals(""))
      {
         lblMessage.setText("Booking number needs be entered");
      }
      else
      {
         bookingNum = Integer.parseInt(bookingNumString);
            try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //create and execute query

            String query = "SELECT * FROM BOOKINGS WHERE BOOKING_ID = "+ bookingNum + " AND PRINTED = 'N' ";
            ResultSet result = stmt.executeQuery(query);

            //get the time for the first film of the day
            if (result != null && result.next())
            {
              lblMessage.setText("Booking Number Accepted");
              getTickets();
            }
            else
            {
              lblMessage.setText("Booking Number does not exist or already printed");
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

    }

    //It updates the booking record to change the PRINTED field to 'Y' and
    //then retrieves the film and screening information which linked to the
    //booking as well as the ticket records and calls the printTickets procedure
    //with parameters.
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

           stmt.execute("UPDATE BOOKINGS SET PRINTED = 'Y' WHERE BOOKING_ID = "+ bookingNum +"");

           //create and execute new query
           String query = "SELECT * from FILMS, SCREENINGS, BOOKINGS WHERE BOOKING_ID = "+ bookingNum +" AND BOOKINGS.SCREENING_ID = SCREENINGS.SCREENING_ID AND FILMS.FILM_ID = SCREENINGS.FILM_ID ";
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

         String query3 = "SELECT TICKET_ID from TICKETS WHERE BOOKING_ID = "+bookingNum +"";
         result = stmt.executeQuery(query3);


         if(result != null && result.next())
         {
             ticketID = result.getString("TICKET_ID");
         }

            if (result != null)
           {
                while (result.next()) {
                quantity++;
                result.next();
                }
          }

         int tixID = Integer.parseInt(ticketID);

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
       int screenNum = 0;

       try{
           //connect to the database
           DatabaseConnect.init();
           Connection conn = DatabaseConnect.getConnection();

           //enable SQL statements to be created and run
           Statement stmt = conn.createStatement();

           final Container cp = window.getContentPane();
           LayoutManager lm = new FlowLayout(FlowLayout.CENTER, 20,20);

           String query = "SELECT * from TICKETS WHERE TICKET_ID = "+ tixID +" ";
           ResultSet result = stmt.executeQuery(query);

           //find the ticket type ID
           if(result != null && result.next())
           {
            type = result.getString("TICKET_TYPE_ID");
           }


           String query2 = "SELECT SCREEN_NUMBER FROM SCREENINGS, BOOKINGS WHERE BOOKINGS.SCREENING_ID=SCREENINGS.SCREENING_ID ";
           result = stmt.executeQuery(query2);

           if(result != null && result.next())
           {
               screenNum = result.getInt("SCREEN_NUMBER");
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
    textarea.append("     ---         " + "----\n");
    textarea.append(type + " " + totalPrice + "\n");
    textarea.append("\n");
    textarea.append("Served by: " + "1  Set of " + quantity + " tickets \n");
    textarea.append("Booking number: " + bookingNum);
    textarea.setEditable(false);

     // set up layout and fill in sample
     cp.setLayout(lm);
     cp.add(new JScrollPane(textarea));

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
       } catch (PrinterException pe) {
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

        jPanel1 = new javax.swing.JPanel();
        lblPickUpTitle = new javax.swing.JLabel();
        lblEnterBookingNum = new javax.swing.JLabel();
        txtBookingNum = new javax.swing.JTextField();
        lblMessage = new javax.swing.JLabel();
        btnPrintTickets = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 255));

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        lblPickUpTitle.setFont(new java.awt.Font("Impact", 0, 24));
        lblPickUpTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblPickUpTitle.setText("PICK UP TICKETS");

        lblEnterBookingNum.setText("Enter booking number:");

        lblMessage.setText("Message");

        btnPrintTickets.setText("Print Tickets");
        btnPrintTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTicketsActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPickUpTitle)
                            .addComponent(lblEnterBookingNum)
                            .addComponent(txtBookingNum, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnPrintTickets)
                        .addGap(18, 18, 18)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPickUpTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEnterBookingNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBookingNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnPrintTickets))
                .addGap(98, 98, 98)
                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintTicketsActionPerformed
       enterBookingNum();
}//GEN-LAST:event_btnPrintTicketsActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
     this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PickUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPrintTickets;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEnterBookingNum;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblPickUpTitle;
    private javax.swing.JTextField txtBookingNum;
    // End of variables declaration//GEN-END:variables

}
