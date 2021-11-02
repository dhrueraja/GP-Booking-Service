package com.CO559;
import dataBaseConnect.connection;
//import com.CO559.Registration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JFrame;

//import com.sun.beans.decoder.StringElementHandler;

import java.awt.EventQueue;
import java.sql.*;
import java.time.LocalDateTime;

public class Login extends JFrame
		implements ActionListener {

	private JFrame frame = new JFrame();
	private JLabel label1, usernameLabel, passwordLabel;
	private JTextField username;
	private JPasswordField password;
	private JButton loginButton, registerButton, forgotpassButton;
	//references the database, can use functions from the database collection package
	private connection databaseConnection = new connection();
	
	public static int patientID;

	public Login(){
		super();
		makeFrame();

	}

	public void makeFrame()
	{
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		frame.setLayout(new FlowLayout());

		frame.setSize(600,600);
		label1 = new JLabel("Please enter your username and password below to login");
		frame.add(label1);

		usernameLabel = new JLabel("Username:");
		frame.add(usernameLabel);
		username = new JTextField(45);
		frame.add(username);

		passwordLabel = new JLabel("Password:");
		frame.add(passwordLabel);
		password = new JPasswordField(45);
		frame.add(password);
		
		

		loginButton = new JButton("Login");
		loginButton.setBounds(20,20,20,20);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){				
				boolean correctPassword = false;

				try {
					//get the patientID that matches the user name
					//getting the text from the user name text field
					patientID = databaseConnection.checkUsername(username.getText());

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				//if patient exists then check password else show an error message
				if (patientID !=0) {

					//uses the password entered and gives patientID (determined by a valid user name) to check if the password is correct
					try {
						correctPassword = databaseConnection.checkPassword(String.valueOf(password.getPassword()), patientID);
					}catch (Exception ex) {
						ex.printStackTrace();
					}
					//if password was correct then display welcome page else give valid error message
					if (correctPassword == true) {
						Welcome welcome = new Welcome();
						welcome.setVisible(true);
						try {
							databaseConnection.logAccess("Patient successfully logged in and accessed the welcome page", patientID);
						}catch(Exception exc) {
							exc.printStackTrace();
						}
						
						frame.dispose();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Your username and password do not match");
						try {
							databaseConnection.logAccess("Patient tried to log in but had an invalid password", 0);
						}catch(Exception exc) {
							exc.printStackTrace();
						}
					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "Your username is not valid");
					try {
						databaseConnection.logAccess("Patient tried to log in but had an invalid username", 0);
					}catch(Exception exc) {
						exc.printStackTrace();
					}
				}

				

			}
		});
		frame.add(loginButton);

		registerButton = new JButton("Register new patient");
		registerButton.setBounds(20,20,20,20);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registration registration = new Registration();
			    try {
				databaseConnection.logAccess("Patient accessed the registration page", 0);
			    }catch(Exception exc) {
			    	exc.printStackTrace();
			    }
				registration.setVisible(true);
			frame.dispose();
				// take to register page
			}
		});
		frame.add(registerButton);

		/* //not needed for this sprint
		forgotpassButton = new JButton("Forgotten password");
		forgotpassButton.setBounds(20,20,20,20);
		forgotpassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// take to forgotten password page
			}
		});
		frame.add(forgotpassButton);
		*/
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

