package com.CO559;

//to be expanded

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.Date;

import dataBaseConnect.connection;

public class MakeBooking extends JFrame  implements ActionListener{


   private JPanel contentPane;
   private JLabel title, doctor, patientid, reason, date, time;
   private JTextField patientid1, reason1, date1, time1;
   private JButton book;

   private connection databaseConnect = new connection();

   String[] doctorStrings = new String[50];
   String doctors = "";
   private int patientID = Login.patientID;
   private String patientName = databaseConnect.getBothNames(patientID);
   private int doctorID = 0;
   String[] details = new String[5];
   private boolean error = false;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               MakeBooking frame = new MakeBooking();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    *
    */
   public MakeBooking() throws Exception {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 425, 440);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

//Adding a title to the booking form
      title = new JLabel("Booking Form");
      title.setBounds(25,10,200,30);
      title.setFont(new Font("Tahoma", Font.PLAIN, 25));
      contentPane.add(title);

//Doctor Selection
      try{
//Get fore name and surname of all the doctors in the table
         doctors = databaseConnect.getDoctor(patientID);
         doctorID = databaseConnect.getDoctorsID(doctors);
//adding doctor and patientID to array to be added to DB
         details[1] = String.valueOf(doctorID);
         details[2] = String.valueOf(patientID);

      }catch(Exception e) {
         e.printStackTrace();
      }

//Adding the doctor selection to the form
      doctor = new JLabel("Doctor: ");
      doctor.setBounds(25,50,100,25);
      contentPane.add(doctor);
      JTextField doctor1 = new JTextField(doctors); // fetch doctor assigned to this patient
      doctor1.setEditable(false);
      doctor1.setBounds(200, 50, 100,25);
      contentPane.add(doctor1);

//Adding the patient id to the form
      patientid = new JLabel("Patient: ");
      patientid.setBounds(25,80,80,25);
      contentPane.add(patientid);
      patientid1 = new JTextField(45);
      patientid1.setText(patientName); // id is being fetched from the database
      patientid1.setEditable(false);

      patientid1.setBounds(200,80,100,25);
      contentPane.add(patientid1);

//Adding reason for the appointment
      reason = new JLabel("Reason for appointment");
      reason.setBounds(25,110,200,25);
      contentPane.add(reason);
      reason1 = new JTextField(45);
      reason1.setBounds(200,110,200,75);
      contentPane.add(reason1);

//Adding the date of the appointment
      date = new JLabel("Date (YYYY-MM-DD)");
      date.setBounds(25,190,200,25);
      contentPane.add(date);
      date1 = new JTextField(10);
      date1.setBounds(200,190,100,25);
      contentPane.add(date1);

//Adding the time of the appointment
      time = new JLabel("Time (HH:MM)");
      time.setBounds(25,220,200,25);
      contentPane.add(time);
      time1 = new JTextField(5);
      time1.setBounds(200,220,100,25);
      contentPane.add(time1);

     
      JButton btnBack = new JButton("Back to welcome page");
      btnBack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Welcome welcome = new Welcome();
            welcome.setVisible(true);
            try {
               databaseConnect.logAccess("Patient went back to welcome page", Login.patientID);
            }
            catch (Exception exc) {
               exc.printStackTrace();
            }
            dispose();
         }
      });
      btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
      btnBack.setBounds(20, 320, 229, 31);
      contentPane.add(btnBack);


      JButton btnSignOut = new JButton("Sign out");
      btnSignOut.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Login login = new Login();
            login.setVisible(true);
            try {
               databaseConnect.logAccess("Patient signed out", Login.patientID);
            }
            catch (Exception exc) {
               exc.printStackTrace();
            }
            dispose();
         }
      });
      btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
      btnSignOut.setBounds(20, 280, 229, 31);
      contentPane.add(btnSignOut);
     
      

      book = new JButton("Book appointment");
      book.setFont(new Font("Tahoma", Font.PLAIN, 16));
      book.setBounds(20,250,175,23);
      contentPane.add(book);
      book.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

            //check reason is within 45 characters
            if(reason1.getText().length() <= 45) {
               details[3] = reason1.getText();

            }
				else if (error == false){
					JOptionPane.showInternalMessageDialog(contentPane, "Please ensure the "
							+ "reason for your appointment is 45-characters long");
					try {
						databaseConnect.logAccess("Patient tried to make a booking but reason that was entered was an invalid length", Login.patientID);
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
					error = true;
				}

            else {
               JOptionPane.showInternalMessageDialog(contentPane, "Please ensure the "
                     + "reason for your appointment is 45-characters long");
               error=true;
               try {
                  databaseConnect.logAccess("Patient tried to make a booking but reason that was entered was an invalid length", Login.patientID);
               }
               catch (Exception exc) {
                  exc.printStackTrace();
               }
            }

            //check date format
            if(checkDate(date1.getText())) {
               details[0]= date1.getText();

				}
				else if  (error == false){
					JOptionPane.showInternalMessageDialog(contentPane, "Please use the "
							+ "valid date format yyyy-mm-dd");
		
					try {
						databaseConnect.logAccess("Patient tried to make a booking but entered an invalid date", Login.patientID);
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
					error = true;
				}

            
            else {
               JOptionPane.showInternalMessageDialog(contentPane, "Please use the "
                     + "valid date format yyyy-mm-dd");
               error=true;
               try {
                  databaseConnect.logAccess("Patient tried to make a booking but entered an invalid date", Login.patientID);
               }
               catch (Exception exc) {
                  exc.printStackTrace();
               }
            }

            //check time format
            if(time1.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
               details[4] = time1.getText();


				}
				else{
					 if (error == false){
					JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
							+ "valid time in the format HH:mm");
	
					try {
						databaseConnect.logAccess("Patient tried to make a booking but entered an invalid time", Login.patientID);
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
					error = true;
					 }
				
            
            else {
               JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
                     + "valid time in the format HH:mm");
               error=true;
               try {
                  databaseConnect.logAccess("Patient tried to make a booking but entered an invalid time", Login.patientID);
               }
               catch (Exception exc) {
                  exc.printStackTrace();
               }
            }

            //check if any of the fields are empty
            if(reason1.getText().isEmpty()) {
               JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
                     + "reason for your appointment");
               error=true;
               try {
                  databaseConnect.logAccess("Patient tried to make booking but no reason given", Login.patientID);
               }
               catch (Exception exc) {
                  exc.printStackTrace();
               }
            }

            if(date1.getText().isEmpty()) {
               JOptionPane.showInternalMessageDialog(contentPane, "Please enter a "
                     + "date for your appointment");
               error=true;
               try {
                  databaseConnect.logAccess("Patient tried to make booking but no date entered", Login.patientID);
               }
               catch (Exception exc) {
                  exc.printStackTrace();
               }
            }
            if(time1.getText().isEmpty()) {
               JOptionPane.showInternalMessageDialog(contentPane, "Please enter the "
                     + "time you wish to book the appointment for.");
               error=true;
               try {
                  databaseConnect.logAccess("Patient tried to make booking but no time entered", Login.patientID);
               }
               catch (Exception exc) {
                  exc.printStackTrace();
               }
            }

           
            //pop up a confirmation message, dispose make booking frame & show welcome screen
           
            try {       //check if the booking to be added isn't already in the database
                    if(!databaseConnect.checkBooking(time1.getText(), date1.getText(), doctorID)) {
                        try {
                            databaseConnect.addToBookings(details);
                        databaseConnect.logAccess("Patient successfully made a booking", Login.patientID);
                            JOptionPane.showInternalMessageDialog(contentPane,"Appointment booked");
                          }catch(Exception ex) {
                            ex.printStackTrace();
                          }
                       
                          //add the confirmation message to tables messages and doctorMessages
                          //send confirmation message to patient - add to db:
                        try {
                        	if(error==false) {
                          databaseConnect.updatePMessages("New appointment on " + date1.getText() +
                          " at "+ time1.getText()+".", patientID); }}
                        catch (Exception e1) {
                          e1.printStackTrace(); }
                        
                         
                          //send confirmation message to doctor - add to db:
                        try {
                        	if(error==false) {
                          databaseConnect.updateDMessages("New appt. on " + date1.getText() + " "+
                          time1.getText()+", ", doctors); }}
                        catch (Exception e1) {
                          e1.printStackTrace(); }   
                    }//appointment is now booked
                   
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
           
         }
      }});



   }
   /**
    * Checks if date entered has the valid format.
   * @param strDate The date string input.
    * @return true if input is valid, false otherwise.
    * */
   static boolean checkDate(String strDate) {
      SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
      sdfrmt.setLenient(false);

     try
      {   //creates a date object from parsing the date input string
         Date javaDate = sdfrmt.parse(strDate);
      }
      // if date is invalid
      catch (ParseException e)
      {
         return false;
      }

      return true; // date is valid
   }

   @Override
   public void actionPerformed(ActionEvent e) {

   }

}