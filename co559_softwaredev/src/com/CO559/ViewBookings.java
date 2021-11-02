package com.CO559;
import com.CO559.*;
import dataBaseConnect.connection;

//to be expanded
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewBookings extends JFrame {

	private JPanel contentPane;
	private JLabel month, year;
    private JTextField month1, year1, bookingbox; 
    private JLabel lblbookings;
    private connection databaseConnection = new connection();
    private JButton btnView;
    String[] monthStrings = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    	//{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul" , "Aug", "Sep", "Oct", "Nov", "Dec"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewBookings frame = new ViewBookings();
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
	public ViewBookings() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Booking Title
		JLabel lblBookings = new JLabel("View Bookings");
		lblBookings.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblBookings.setBounds(10, 11, 128, 23);
		contentPane.add(lblBookings);
		
		//Month
        month = new JLabel("Month:");
        month.setBounds(25,50, 80,25);
        contentPane.add(month);
        JComboBox month1 = new JComboBox(monthStrings);
        month1.setBounds(90,50,60,25);
        contentPane.add(month1);

        //Year
        year = new JLabel("Year:");
        year.setBounds(25, 90, 80,25);
        contentPane.add(year);
        year1 =  new JTextField(45);
        year1.setBounds(90,90,90,25);
        contentPane.add(year1);
        
        //GOES BEHIND TEXTFIELDBOX so not visible, NEED TO BRING TO FRONT 
        /**JLabel lblbookings = new JLabel("View bookings here...");
        lblbookings.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblbookings.setBounds(200, 85, 229, 31);
		contentPane.add(lblbookings);
		**/
        
		JTextArea bookingbox = new JTextArea();
		bookingbox.setBounds(225, 85, 250,250);
		bookingbox.setEditable(false);
		bookingbox.setLineWrap(true);
		bookingbox.setWrapStyleWord(true);
		contentPane.add(bookingbox);
		
		 JButton btnView = new JButton("View");
		 btnView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						int yearInt=Integer.valueOf(year1.getText());
						
						if(yearInt > 2021 || yearInt < 1900) {
							JOptionPane.showMessageDialog(contentPane, "Please enter a valid year.");
							try {
								databaseConnection.logAccess("Patient tried to view booking but entered an invalid year", Login.patientID);
							}
							catch (Exception exc) {
								exc.printStackTrace();
							}
						}
						else{
							String viewBookings = "";
		
						//retrieves bookings in specified month and year from patient logged in
						try {
	
							viewBookings = databaseConnection.displayBookings(String.valueOf(year1.getText()), String.valueOf(month1.getSelectedItem()),String.valueOf(Login.patientID));
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						//if no bookings made in the specified timeframe then display that, else display bookings
						if(viewBookings.equals("")) {
							bookingbox.setText("No bookings.");
							try {
								databaseConnection.logAccess("Patient had no bookings to view", Login.patientID);
							}
							catch (Exception exc) {
								exc.printStackTrace();
							}
						}
						else {
							bookingbox.setText(viewBookings);
							try {
								databaseConnection.logAccess("Patient viewed their bookings", Login.patientID);
							}
							catch (Exception exc) {
								exc.printStackTrace();
							}
						}
						
						}
					}
				});
		 btnView.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 btnView.setBounds(25, 130, 80,25);
		 contentPane.add(btnView);
		
		 JButton btnBack = new JButton("Back to welcome page");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Welcome welcome = new Welcome();
					welcome.setVisible(true);
					try {
						databaseConnection.logAccess("Patient went back to welcome page", Login.patientID);
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
					dispose();
				}
			});
			btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnBack.setBounds(20, 380, 229, 31);
			contentPane.add(btnBack);

			JButton btnSignOut = new JButton("Sign out");
			btnSignOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Login login = new Login();
					login.setVisible(true);
					try {
						databaseConnection.logAccess("Patient signed out", Login.patientID);
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
					dispose();
				}
			});
			btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnSignOut.setBounds(20, 340, 229, 31);
			contentPane.add(btnSignOut);
	}

}
