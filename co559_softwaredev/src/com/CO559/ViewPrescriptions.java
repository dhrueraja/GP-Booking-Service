package com.CO559;

import dataBaseConnect.connection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dataBaseConnect.connection;

public class ViewPrescriptions extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JLabel title;


    private connection databaseConnect = new connection();

    //Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewPrescriptions frame = new ViewPrescriptions();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Creating the frame
    public ViewPrescriptions(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,580,553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("View current, previous and repeat prescriptions");
        title.setFont(new Font("Tahoma", Font.PLAIN, 25));
        title.setBounds(10,11,600,31);
        contentPane.add(title);

        JTextArea prescription = new JTextArea();
        prescription.setBounds(10,40,400,400);
        prescription.setEditable(false);
        prescription.setLineWrap(true);
        prescription.setWrapStyleWord(true);
        contentPane.add(prescription);
        try {
            prescription.setText(databaseConnect.getPrescription(Login.patientID));
            databaseConnect.logAccess("Patient viewed their prescriptions", Login.patientID);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        

        JButton back = new JButton("Back");
        back.setBounds(29,438,117,23);
        contentPane.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Welcome welcome = new Welcome();
                welcome.setVisible(true);
                try {
                    databaseConnect.logAccess("Patient accessed welcome page", Login.patientID);
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });


    }




















    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
