package com.CO559;
import dataBaseConnect.connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ChangeDoctor extends JFrame {
	private JPanel contentPane;
	String[] doctorStrings = new String[50];
	ArrayList<String> doctorList = new ArrayList<String>();
	connection databaseConnect = new connection();

	 public ChangeDoctor() {
	        super();
	        makeFrame();

	    }

	    public void makeFrame(){

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 500, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			 try {

				 	doctorList = databaseConnect.getDoctors();
		        	doctorStrings = doctorList.toArray(doctorStrings);

		        }catch(Exception e) {
		        	e.printStackTrace();
		        }

			JLabel lblChooseDoc = new JLabel("Select a new doctor: ");
			lblChooseDoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblChooseDoc.setBounds(20, 20, 229, 31);
			contentPane.add(lblChooseDoc);
			JComboBox doctors = new JComboBox(doctorStrings);
			doctors.setBounds(190,20,229, 31);
	        contentPane.add(doctors);

	        JButton btnChange = new JButton("Request change");
	        btnChange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						databaseConnect.updatePMessages("Doctor changed", Login.patientID);
						String doctorMessage = "New patient: " + databaseConnect.getName(Login.patientID);
						databaseConnect.updateDMessages(doctorMessage, String.valueOf(doctors.getSelectedItem()));
						databaseConnect.newDoctor(String.valueOf(doctors.getSelectedItem()), Login.patientID);
						databaseConnect.logAccess("Patient requested a different doctor", Login.patientID);
					}catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
	        btnChange.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnChange.setBounds(20, 75, 229, 31);
			contentPane.add(btnChange);




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
			btnBack.setBounds(20, 130, 229, 31);
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
			btnSignOut.setBounds(20, 185, 229, 31);
			contentPane.add(btnSignOut);

	    }


}
