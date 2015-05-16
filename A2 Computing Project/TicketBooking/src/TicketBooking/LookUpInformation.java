/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LookUpInformation.java
 *
 * Created on 13-Feb-2011, 22:00:17
 */

package TicketBooking;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Piravin Satkunarajah
 */
public class LookUpInformation extends javax.swing.JFrame {

    /** Creates new form LookUpInformation */
    public LookUpInformation() {
        initComponents();
    }

    //Retrieves a record from the FILMS table using the film ID and then puts
    //this data into tblFilms
    public void filmsTable(){

     String filmID = txtFilmID.getText();

     if (filmID.equals(""))
     {
     lblMessageFilms.setText("Film ID needs be entered");
     }
     else
     {
            try {
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //create and execute query

            String query = "SELECT * from FILMS where FILM_ID = "+ filmID +"";
            ResultSet result = stmt.executeQuery(query);

           //retireve the table model - so that rows can be added
           DefaultTableModel model = (DefaultTableModel)tblFilms.getModel();

           //move to first record
           if(result != null)
           {
             //loop through all the records retrieved
               while(result.next())
               {
                int filmIDsql = result.getInt("FILM_ID");
                String filmName = result.getString("FILM_NAME");
                String short_name = result.getString("SHORT_NAME");
                String BBFCcert = result.getString("BBFC_CERTIFICATE");
                String BBFCinfo = result.getString("BBFC_INFO");
                String director = result.getString("DIRECTOR");
                String origin = result.getString("ORIGIN");
                int run_time = result.getInt("RUN_TIME");
                String genre = result.getString("GENRE");
                String synopsis = result.getString("SYNOPSIS");
                String film_language = result.getString("FILM_LANGUAGE");
                String distributor = result.getString("DISTRIBUTOR");


                //This adds a new row to the table. The array
                //Object contains the content of each column
                model.addRow(new Object[]{filmIDsql, filmName, short_name, 
                BBFCcert, BBFCinfo, director, origin, run_time, genre,
                synopsis, film_language, distributor});
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
    }

    //Retrieves a record from the STAFF table using the cashier number and then puts
    //this data into tblStaff
    public void staffTable()
    {
      String staffID = txtCashierNumber.getText();

      if(staffID.equals(""))
      {
       lblStaffMessage.setText("Staff ID needs be entered");
      }
      else{
          try{
            //connect to the database
            DatabaseConnect.init();
            Connection conn = DatabaseConnect.getConnection();

            //enable SQL statements to be created and run
            Statement stmt= conn.createStatement();

            //create and execute query
            String query = "SELECT * from STAFF where CASHIER_NUMBER = "+ staffID +"";
            ResultSet result = stmt.executeQuery(query);

            //retireve the table model - so that rows can be added
            DefaultTableModel model = (DefaultTableModel)tblStaff.getModel();

            //move to first record
            if(result != null)
            {
             //loop through all the records retrieved
               while(result.next())
               {
                int cashierNumber = result.getInt("CASHIER_NUMBER");
                String firstName = result.getString("FIRST_NAME");
                String lastName = result.getString("LAST_NAME");
                String occupation = result.getString("OCCUPATION");
                String userName = result.getString("USER_NAME");
                String password = result.getString("PASSWORD");

                //This adds a new row to the table. The array
                //Object contains the content of each column
                model.addRow(new Object[]{cashierNumber, firstName, lastName, occupation, userName, password});
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lblEnterBookingNum = new javax.swing.JLabel();
        txtFilmID = new javax.swing.JTextField();
        btnCreateTable = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFilms = new javax.swing.JTable();
        lblMessageFilms = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblEnterBookingNum1 = new javax.swing.JLabel();
        txtCashierNumber = new javax.swing.JTextField();
        btnCreateTable2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStaff = new javax.swing.JTable();
        lblStaffMessage = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        lblPickUpTitle.setFont(new java.awt.Font("Impact", 0, 24));
        lblPickUpTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblPickUpTitle.setText("LOOK UP INFORMATION");

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        lblEnterBookingNum.setForeground(new java.awt.Color(255, 255, 255));
        lblEnterBookingNum.setText("Enter Film ID:");

        btnCreateTable.setText("Enter");
        btnCreateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateTableActionPerformed(evt);
            }
        });

        tblFilms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Film ID", "Film Name", "Short Name", "BBFC Certificate", "BBFC Information", "Director", "Origin", "Run Time", "Genre", "Synopsis", "Film Language", "Distributor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFilms);

        lblMessageFilms.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
                    .addComponent(lblEnterBookingNum)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFilmID, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157)
                        .addComponent(lblMessageFilms, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnCreateTable)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEnterBookingNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMessageFilms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFilmID))
                .addGap(18, 18, 18)
                .addComponent(btnCreateTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Look up Films", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        lblEnterBookingNum1.setForeground(new java.awt.Color(255, 255, 255));
        lblEnterBookingNum1.setText("Enter Cashier Number:");

        btnCreateTable2.setText("Enter");
        btnCreateTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateTable2ActionPerformed(evt);
            }
        });

        tblStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cashier Number", "First Name", "Last Name", "Occupation", "User Name", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblStaff);

        lblStaffMessage.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
                    .addComponent(lblEnterBookingNum1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCashierNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(lblStaffMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnCreateTable2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEnterBookingNum1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCashierNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStaffMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCreateTable2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Look up Staff", jPanel3);

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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPickUpTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
                        .addComponent(btnClose)
                        .addGap(143, 143, 143))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPickUpTitle)
                    .addComponent(btnClose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
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

    private void btnCreateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateTableActionPerformed
    filmsTable();
}//GEN-LAST:event_btnCreateTableActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
    this.dispose();
}//GEN-LAST:event_btnCloseActionPerformed

    private void btnCreateTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateTable2ActionPerformed
    staffTable();
}//GEN-LAST:event_btnCreateTable2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LookUpInformation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCreateTable;
    private javax.swing.JButton btnCreateTable2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblEnterBookingNum;
    private javax.swing.JLabel lblEnterBookingNum1;
    private javax.swing.JLabel lblMessageFilms;
    private javax.swing.JLabel lblPickUpTitle;
    private javax.swing.JLabel lblStaffMessage;
    private javax.swing.JTable tblFilms;
    private javax.swing.JTable tblStaff;
    private javax.swing.JTextField txtCashierNumber;
    private javax.swing.JTextField txtFilmID;
    // End of variables declaration//GEN-END:variables

}
