package com.CO559;
import com.CO559.*;

import dataBaseConnect.connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dataBaseConnect.connection;

public class ViewDetails extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JLabel title;
    private JTextField detailsbox;
    private JButton btnGoBack1;

    private connection databaseConnection = new connection();

    //Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	ViewDetails frame = new ViewDetails();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Creating the frame
    public ViewDetails(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,580,553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //page title
        JLabel title = new JLabel("View details");
        title.setFont(new Font("Tahoma", Font.PLAIN, 25));
        title.setBounds(10,11,600,31);
        contentPane.add(title);
        
        //box for details of all previous appointments
		JTextField detailsbox = new JTextField(200);
		detailsbox.setBounds(225, 85, 250,250);
		detailsbox.setEditable(false);
		contentPane.add(detailsbox);
		
		String viewDetails = "";
		
		try {
			viewDetails = databaseConnection.displayDetails(String.valueOf(Login.patientID));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		//if no bookings made in the specified timeframe then display that, else display bookings
		if(viewDetails.equals("")) {
			detailsbox.setText("No details to display.");
			try {
				databaseConnection.logAccess("Patient tried to view details but none to display", Login.patientID);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		}
		else {
			detailsbox.setText(viewDetails);
			try {
				databaseConnection.logAccess("Patient viewed details", Login.patientID);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
    

    JButton btnGoBack1 = new JButton("Go Back");
	btnGoBack1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Welcome welcome = new Welcome();
			welcome.setVisible(true);
			try {
				databaseConnection.logAccess("Patient accessed the welcome page", Login.patientID);
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			dispose();
		}
	});
	btnGoBack1.setFont(new Font("Tahoma", Font.PLAIN, 16));
	btnGoBack1.setBounds(10, 394, 128, 23);
	contentPane.add(btnGoBack1);

    }
















    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
