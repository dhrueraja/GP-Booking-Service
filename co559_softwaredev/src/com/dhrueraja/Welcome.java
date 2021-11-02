package com.dhrueraja;
import com.CO559.*;
import dataBaseConnect.*;
// welcome page, to be expanded

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Welcome extends JFrame {

	private JPanel contentPane;
	private connection databaseConnection = new connection();
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
		
		JLabel lblWelcome = new JLabel("Welcome, [user]");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblWelcome.setBounds(10, 11, 229, 31);
		contentPane.add(lblWelcome);
		
		JButton btnRegister = new JButton("Register as a Patient");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registration register = new Registration();
				//register.setVisible(true); try
				dispose();
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRegister.setBounds(10, 205, 215, 47);
		contentPane.add(btnRegister);
		
		JButton btnBooking = new JButton("Make a Booking");
		btnBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MakeBooking makeBooking = null;
				try {
					makeBooking = new MakeBooking();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				makeBooking.setVisible(true);
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
				dispose();
				
			}
		});
		btnViewBooking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnViewBooking.setBounds(10, 371, 215, 47);
		contentPane.add(btnViewBooking);
		
		JButton btnSignOut = new JButton("Sign out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				//login.setVisible(true); comment g
				dispose();
			}
		});
		btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSignOut.setBounds(354, 414, 178, 47);
		contentPane.add(btnSignOut);
		
		JLabel lblNewMsgs = new JLabel("New messages: ");
		lblNewMsgs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewMsgs.setBounds(180, 85, 229, 31);
		contentPane.add(lblNewMsgs);
		
		
		
		
		
		
	}
}
