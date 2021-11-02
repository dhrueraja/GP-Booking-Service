package com.CO559;
import com.CO559.*;

import dataBaseConnect.connection;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class Reschedule extends JFrame implements ActionListener{
    
    private JPanel contentPane;
    private JTextField txtOldDate;
    private JTextField txtOldTime;
 // copied over
    private connection databaseConnect = new connection();
    
    String[] doctorStrings = new String[50];
    String doctors = "";
    private int patientID = Login.patientID;
    private int doctorID = 0;
    String[] details = new String[5];
    private JTextField txtReason;
    private JTextField txtNewDate;
    private JTextField txtNewTime;
    boolean error = false;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   
                    Reschedule frame = new Reschedule();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Make the frame.
     */
    public Reschedule(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 570, 653);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblReschedule = new JLabel("Reschedule Booking");
        lblReschedule.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblReschedule.setBounds(137, 11, 231, 48);
        contentPane.add(lblReschedule);
        
        // copied over
        //Doctor Selection
          try{
      //Get forename and surname of all the doctors in the table
            doctors = databaseConnect.getDoctor(patientID);
            doctorID = databaseConnect.getDoctorsID(doctors);


          }catch(Exception e) {
            e.printStackTrace();
          }
          
        
        JLabel lblOldDate = new JLabel("Select old date of your appointment (YYYY-MM-DD):");
        lblOldDate.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblOldDate.setBounds(29, 90, 456, 23);
        contentPane.add(lblOldDate);
        
        txtOldDate = new JTextField();
        txtOldDate.setBounds(29, 124, 456, 34);
        contentPane.add(txtOldDate);
        txtOldDate.setColumns(10);
        
        JLabel lblOldTime = new JLabel("Select old time of your appointment (HH:MM):");
        lblOldTime.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblOldTime.setBounds(29, 181, 456, 23);
        contentPane.add(lblOldTime);
        
        txtOldTime = new JTextField();
        txtOldTime.setColumns(10);
        txtOldTime.setBounds(29, 210, 456, 34);
        contentPane.add(txtOldTime);
        
        /*
         * JButton btnCancel = new JButton("Cancel"); btnCancel.setFont(new
         * Font("Tahoma", Font.PLAIN, 16)); btnCancel.setBounds(29, 547, 117, 23);
         * contentPane.add(btnCancel); btnCancel.addActionListener(new ActionListener()
         * { public void actionPerformed(ActionEvent e) { Welcome welcome = new
         * Welcome(); welcome.setVisible(true); try {
         * databaseConnect.logAccess("Patient accessed the welcome page",
         * Login.patientID); }catch(Exception exc) { exc.printStackTrace(); } dispose();
         * } });
         */
        
        JButton btnBack = new JButton("Back to welcome page");
        btnBack.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Welcome welcome = new Welcome();
            welcome.setVisible(true);
            try {
    			databaseConnect.logAccess("Patient accessed the welcome page", Login.patientID);
    		}catch(Exception exc) {
    			exc.printStackTrace();
    		}
            dispose();
          }
        });
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBack.setBounds(28, 574, 201, 31);
        contentPane.add(btnBack);

        
        JButton btnReschedule = new JButton("Reschedule Appointment");
        btnReschedule.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnReschedule.setBounds(278, 574, 207, 31);
        contentPane.add(btnReschedule);
        
        JLabel lblReason = new JLabel("What is the reason for this appointment?");
        lblReason.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblReason.setBounds(29, 443, 375, 23);
        contentPane.add(lblReason);
        
        txtReason = new JTextField();
        txtReason.setColumns(10);
        txtReason.setBounds(29, 465, 456, 34);
        contentPane.add(txtReason);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(29, 265, 456, 2);
        contentPane.add(separator);
        
        JLabel lblNewDate = new JLabel("Select NEW date of your appointment (YYYY-MM-DD):");
        lblNewDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewDate.setBounds(29, 278, 456, 23);
        contentPane.add(lblNewDate);
        
        txtNewDate = new JTextField();
        txtNewDate.setColumns(10);
        txtNewDate.setBounds(29, 312, 456, 34);
        contentPane.add(txtNewDate);
        
        JLabel lblNewTime = new JLabel("Select NEW time of your appointment (HH:MM):");
        lblNewTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewTime.setBounds(29, 369, 456, 23);
        contentPane.add(lblNewTime);
        
        txtNewTime = new JTextField();
        txtNewTime.setColumns(10);
        txtNewTime.setBounds(29, 398, 456, 34);
        contentPane.add(txtNewTime);
        btnReschedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
              //check reason is within 45 characters
                if(txtReason.getText().length() <= 45) {
                  details[4] = txtReason.getText();

                }

                else {
                  JOptionPane.showInternalMessageDialog(contentPane, "Please ensure the "
                      + "reason for your appointment is 45-characters long");
                }
                
              //check new and old date format
                if(MakeBooking.checkDate(txtOldDate.getText())) {
                    details[0]= txtOldDate.getText();
                    

                }
                else {
                  JOptionPane.showInternalMessageDialog(contentPane, "Please use the "
                      + "valid date format yyyy-mm-dd");
                }
                
                if(MakeBooking.checkDate(txtNewDate.getText())) {
                    details[2]= txtNewDate.getText();

                }
                else {
                  JOptionPane.showInternalMessageDialog(contentPane, "Please use the "
                      + "valid date format yyyy-mm-dd");
                }
                
                
              //check old and new time format
                if(txtOldTime.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    details[1] = txtOldTime.getText();

                }
                else {
                  JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
                      + "valid time in the format HH:mm");
                }
                
                if(txtNewTime.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    details[3] = txtNewTime.getText();

                  }
                  else {
                    JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
                        + "valid time in the format HH:mm");
                  }
                
                //handle empty fields
                if(txtOldDate.getText().isEmpty()) {
                    JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
                        + "date for your appointment");
                  }
                if(txtNewDate.getText().isEmpty()) {
                    JOptionPane.showInternalMessageDialog(contentPane, "Please enter the "
                        + "new date of your appointment.");
                  }
                  if(txtOldTime.getText().isEmpty()) {
                    JOptionPane.showInternalMessageDialog(contentPane, "Please enter the "
                        + "time you wish to book the appointment for.");
                  }
                  
                  if(txtNewTime.getText().isEmpty()) {
                      JOptionPane.showInternalMessageDialog(contentPane, "Please enter the "
                          + "new time.");
                    }                
                  if(txtReason.getText().isEmpty()) {
                      JOptionPane.showInternalMessageDialog(contentPane, "Please enter the "
                          + "reason for rescheduling your appointment.");
                    }
                  
                  
                  
                //pop up a confirmation message, dispose make booking frame & show welcome screen
                  

                  try {       //check if the booking is an existing booking 
                              if(databaseConnect.checkBooking(txtOldTime.getText(), txtOldDate.getText(), doctorID)) {
                                  try {

                                      databaseConnect.reschedule(details); 
                                      databaseConnect.logAccess("Patient rescheduled a booking", Login.patientID);
                              		
                                      JOptionPane.showInternalMessageDialog(contentPane,"Appointment booked");
                                    }catch(Exception ex) {//appointment is now booked
                                      ex.printStackTrace();
                                    }
                                  
                                    //add the confirmation message to tables messages and doctorMessages 
                                    //send confirmation message to patient - add to db: 
                                  try {
                                    databaseConnect.updatePMessages("New appointment on " + txtNewDate.getText() +
                                    " at "+ txtNewTime.getText()+".", patientID); } 
                                  catch (Exception e1) {
                                    e1.printStackTrace(); }
                                   
                                   
                                    //send confirmation message to doctor - add to db: 
                                  try {
                                    databaseConnect.updateDMessages("New appt. on " + txtNewDate.getText() + " "+
                                    txtNewTime.getText()+", ", doctors); } 
                                  catch (Exception e1) {
                                    e1.printStackTrace(); }    
                              } //if the old date and time dont match an original booking 
                              else {
                                  JOptionPane.showInternalMessageDialog(contentPane,"There is no " +
                              "original booking for the old date/time entered.\n Please check that you've " +
                               "entered the correct old date/time.");
                                 databaseConnect.logAccess("Patient tried to reschedule a booking but no original booking", Login.patientID);
                              }
                              
                          } catch (Exception e1) {
                              // TODO Auto-generated catch block 
                              e1.printStackTrace();
                          }
                  
                  
                
              }
            });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
