package com.CO559;
import com.CO559.*;
import dataBaseConnect.*;
// welcome page, to be expanded

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Welcome extends JFrame {

	private JPanel contentPane;
	private connection databaseConnection = new connection();
	private String patientName = "";
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome frame = new Welcome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Welcome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			patientName = databaseConnection.getName(Login.patientID);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		JLabel lblWelcome = new JLabel("Welcome, " + patientName);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblWelcome.setBounds(10, 11, 229, 31);
		contentPane.add(lblWelcome);
		
		
		
		JButton btnBooking = new JButton("Make a Booking");
		btnBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MakeBooking makeBooking = null;
                try {
                    makeBooking = new MakeBooking();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
				makeBooking.setVisible(true);
				try {
					databaseConnection.logAccess("Patient accessed the make booking page", Login.patientID);
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				dispose();
			}
		});
		btnBooking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBooking.setBounds(10, 282, 215, 47);
		contentPane.add(btnBooking);
		
		
		
		JButton btnViewBooking = new JButton("View Bookings");
		btnViewBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewBookings view = new ViewBookings();
				view.setVisible(true);
				try {
					databaseConnection.logAccess("Patient accessed the view booking page", Login.patientID);
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				dispose();
				
			}
		});
		btnViewBooking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnViewBooking.setBounds(10, 350, 215, 47);
		contentPane.add(btnViewBooking);
		
		
		JButton btnSignOut = new JButton("Sign out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				try {
					databaseConnection.logAccess("Patient signed out", Login.patientID);
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				dispose();
			}
		});
		btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSignOut.setBounds(354, 414, 178, 47);
		contentPane.add(btnSignOut);
		
		
		JButton btnChangeDoctor = new JButton("Change Doctor");
		btnChangeDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeDoctor change = new ChangeDoctor();
				change.setVisible(true);
				try {
					databaseConnection.logAccess("Patient accessed the change doctor page", Login.patientID);
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				dispose();
			}
		});
		btnChangeDoctor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnChangeDoctor.setBounds(10, 213, 215, 47);
		contentPane.add(btnChangeDoctor);
		
		
	//Prescriptions button
    JButton btnPrescriptions = new JButton("View Prescriptions");
    btnPrescriptions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ViewPrescriptions vPrescriptions = new ViewPrescriptions();
        try {
			databaseConnection.logAccess("Patient accessed the view prescriptions page", Login.patientID);
		}catch(Exception exc) {
			exc.printStackTrace();
		}
        vPrescriptions.setVisible(true);
        dispose();
      }
    });
    btnPrescriptions.setFont(new Font("Tahoma", Font.PLAIN, 16));
    btnPrescriptions.setBounds(10, 95, 215, 47);
    contentPane.add(btnPrescriptions);
    
		//Reschedule button
		JButton btnReschedule = new JButton("Reschedule Appt.");
    btnReschedule.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Reschedule reschedule = new Reschedule();
        reschedule.setVisible(true);
        try {
			databaseConnection.logAccess("Patient accessed the reschedule booking page", Login.patientID);
		}catch(Exception exc) {
			exc.printStackTrace();
		}
        dispose();
      }
    });
    btnReschedule.setFont(new Font("Tahoma", Font.PLAIN, 16));
    btnReschedule.setBounds(10, 415, 215, 47);
    contentPane.add(btnReschedule);
		
    //View details button
    JButton btnviewDetails = new JButton("View Details");
    btnviewDetails.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ViewDetails viewDetails = new ViewDetails();
        viewDetails.setVisible(true);
        try {
			databaseConnection.logAccess("Patient accessed the view details page", Login.patientID);
		}catch(Exception exc) {
			exc.printStackTrace();
		}
        dispose();
      }
    });
    btnviewDetails.setFont(new Font("Tahoma", Font.PLAIN, 16));
    btnviewDetails.setBounds(10, 155, 215, 47);
    contentPane.add(btnviewDetails);
    
    /*
     * JLabel lblNewMsgs = new JLabel("New messages here...");
     * lblNewMsgs.setFont(new Font("Tahoma", Font.PLAIN, 16));
     * lblNewMsgs.setBounds(23, 85, 229, 31); contentPane.add(lblNewMsgs);
     */
    
    
		//text box to show any new messages
				JTextArea messages = new JTextArea();
				messages.setBounds(300, 85, 250,250);
				//unable to be edited by user
				messages.setEditable(false);
				messages.setLineWrap(true);
				messages.setWrapStyleWord(true);
				String newMessages = "";
				
				//retrieves new messages from patient logged in
				try {
			
					newMessages = databaseConnection.openNewMessages(Login.patientID);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				//if no new messages available then display that, else display the new messages
				if(newMessages.equals("")) {
					messages.setText("No new messages.");
				}
				else {
					messages.setText(newMessages);
				}
				contentPane.add(messages);
	}
	

}
