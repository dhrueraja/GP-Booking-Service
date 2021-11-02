package com.CO559;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.regex.*;
import dataBaseConnect.connection;



public class Registration extends JFrame implements ActionListener {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JLabel title, forename, surname, address, dob, nhsno, email, phoneno, username, password, gender, doctor;
    private JTextField forename1, surname1, address1, dob1, nhsno1, email1, phoneno1, username1, password1;
    private ComboBoxEditor title1, gender1, doctor1;
    private JButton register;
    private connection databaseConnect = new connection();

    String[] titleStrings = {"Mr", "Mrs", "Miss", "Ms"};
    String[] genderStrings = {"M", "F", "O"};
    //String[] doctorStrings = {"1", "2", "3"};
    String[] doctorStrings = new String[50];
    ArrayList<String> doctors = new ArrayList<String>();

    public Registration() {
        super();
        makeFrame();
        
    }

    public void makeFrame(){
    	JPanel contentPane = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 553);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        


        //Title
        title = new JLabel("Title");
        title.setBounds(25,10,45,25);
        contentPane.add(title);
        JComboBox title1 = new JComboBox(titleStrings);
        title1.setBounds(90,10,70,25);
        contentPane.add(title1);

        //Forename
        forename = new JLabel("Forename");
        forename.setBounds(25,50, 80,25);
        contentPane.add(forename);
        forename1 = new JTextField(45);
        forename1.setBounds(90,50,300,25);
        contentPane.add(forename1);

        //Surname
        surname = new JLabel("Surname");
        surname.setBounds(25, 90, 80,25);
        contentPane.add(surname);
        surname1 =  new JTextField(45);
        surname1.setBounds(90,90,300,25);
        contentPane.add(surname1);

        //Address
        address = new JLabel("Address");
        address.setBounds(25,130,80,25);
        contentPane.add(address);
        address1 = new JTextField(45);
        address1.setBounds(90,130,300,25);
        contentPane.add(address1);

        //Date of birth
        dob = new JLabel("Date of birth YYYY-MM-DD");
        dob.setBounds(25,170,300,25);
        contentPane.add(dob);
        dob1 = new JTextField(10);
        dob1.setBounds(190,170,200,25);
        contentPane.add(dob1);

        //nhs Number
        nhsno = new JLabel("NHS Number");
        nhsno.setBounds(25,210,80,25);
        contentPane.add(nhsno);
        nhsno1 = new JTextField(10);
        nhsno1.setBounds(130,210, 260,25);
        contentPane.add(nhsno1);

        //Email
        email = new JLabel("Email Address");
        email.setBounds(25,250,80,25);
        contentPane.add(email);
        email1 =  new JTextField(45);
        email1.setBounds(130,250,260,25);
        contentPane.add(email1);

        //Phone number
        phoneno = new JLabel("Phone Number");
        phoneno.setBounds(25,290,110,25);
        contentPane.add(phoneno);
        phoneno1 = new JTextField(11);
        phoneno1.setBounds(130,290,260,25);
        contentPane.add(phoneno1);

        //Username
        username = new JLabel("Username");
        username.setBounds(25,330,80,25);
        contentPane.add(username);
        username1 = new JTextField(45);
        username1.setBounds(90,330,300,25);
        contentPane.add(username1);

        //Password
        password = new JLabel("Password");
        password.setBounds(25,370,80,25);
        contentPane.add(password);
        password1 = new JTextField(45);
        password1.setBounds(90,370,300,25);
        contentPane.add(password1);

        //Gender
        gender = new JLabel("Gender");
        gender.setBounds(25,410,80,25);
        contentPane.add(gender);
        JComboBox gender1 = new JComboBox(genderStrings);
        gender1.setBounds(90,410,60,25);
        contentPane.add(gender1);

        //Doctor Selection
        try {
        	//get forename and surname of all doctors in table
        	doctors = databaseConnect.getDoctors();
        	doctorStrings = doctors.toArray(doctorStrings);
       
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        doctor = new JLabel("Doctor");
        doctor.setBounds(25,440,80,25);
        contentPane.add(doctor);
        JComboBox doctor1 = new JComboBox(doctorStrings);
        doctor1.setBounds(90,440,80,25);
        contentPane.add(doctor1);

		JButton btnback = new JButton("Back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				try {
					databaseConnect.logAccess("Patient went back to login page", Login.patientID);
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				dispose();
			}
		});
		btnback.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnback.setBounds(200, 475, 100, 25);
		contentPane.add(btnback);
		
        
        
        //adding register button
        register = new JButton("Register");
        register.setBounds(90,475, 100, 25);
        contentPane.add(register);
        register.addActionListener(new ActionListener() {
        	
        	
        	/**
        	 * when the register button is pressed, check all the inputs are of the correct type and add them to database
        	 * else show an error message
        	 */
			public void actionPerformed(ActionEvent e) {
				//stores each detail of the new patient
				String[] details = new String[12];
				//whether the details match the expected types
				boolean validDetails = true;
				//get selected values from the drop down box and add them to arrayList
				
				try {
					//value selected in title content box is stored in details list
					details[0] = String.valueOf(title1.getSelectedItem());
					
					// if forename matches correct type then add it to the details list
					// else show an error message
					if(checkVal(forename1.getText())) {
			    		details[1] = forename1.getText();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Please enter a valid forename.");
					    try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid forename", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					// if surname matches correct type then add it to the details list if the previous checks were also valid
					// else show an error message
					if(checkVal(surname1.getText())&& validDetails == true) {
						details[2] = surname1.getText();
					}
					else if (validDetails == true){
						JOptionPane.showMessageDialog(frame, "Please enter a valid surname.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid surname", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					//check that the address is correct type & not null, else show an error message
					if(checkVal(address1.getText())&& validDetails == true){
						details[3] = address1.getText();
					}
					else if (validDetails == true){
						JOptionPane.showMessageDialog(frame, "Please enter a valid address.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid address", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					//dob
					if(dateFormat(dob1.getText())&& validDetails == true) {
						details[4] = dob1.getText();
					}
					else if(validDetails == true) {
						JOptionPane.showMessageDialog(frame, "Please enter a valid date of birth.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid date of birth", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					
					//if the entered NHS number is length 10 and only contains numeric values or is empty then add to details 
					//if its already in the database or invalid then display error message
					if (nhsno1.getText().length() ==10 && validDetails == true && isNumber(nhsno1.getText())== true) {
						if(databaseConnect.checkNhsNo(nhsno1.getText())) {
							details[5] = nhsno1.getText();
						}
						else {
							JOptionPane.showMessageDialog(frame, "The entered NHS number has already been registered.");
							try {
						    	databaseConnect.logAccess("Patient tried to register but entered a NHS number that was already registered", 0);
						    }catch(Exception exc) {
						    	exc.printStackTrace();
						    }
							validDetails = false;
						}
					}
					else if(nhsno1.getText().equals("")&& validDetails == true) {
						details[5] = nhsno1.getText();
					}
					else if(validDetails == true) {
						JOptionPane.showMessageDialog(frame, "Please enter a valid nhs number or if non-applicable, leave blank.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid NHS number", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					//checks if the email fits the valid format or is null, else return error message
					Pattern pattern = Pattern.compile("^.+@.+\\..+$");
					Matcher matcher = pattern.matcher(email1.getText());
					if((matcher.matches()||email1.getText().equals(""))&&validDetails == true) {
						details[6] = email1.getText();
					}
					else if(validDetails == true) {
						JOptionPane.showMessageDialog(frame, "Please enter a valid email address, or if non applicable then leave blank.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid email address", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					//checks that the phone number is 11 digits 
					//else returns an error
					if(checkPhone(phoneno1.getText())== true && validDetails == true) {
						details[7] = phoneno1.getText();
					}
					else if (validDetails == true) {
						JOptionPane.showMessageDialog(frame, "Please enter a valid phone number.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid phone number", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					//if user name hasn't already been used then add it to details
					//else return error message
					if(username1.getText().equals("")&&validDetails == true) {
        					JOptionPane.showMessageDialog(frame, "Please enter a username.");
        					try {
    					    	databaseConnect.logAccess("Patient tried to register but entered a null username", 0);
    					    }catch(Exception exc) {
    					    	exc.printStackTrace();
    					    }
        					validDetails = false;
        					}
					
					else if(databaseConnect.checkUsername(username1.getText()) == 0 && validDetails == true) {
						details[8] = username1.getText();
					}
					
					else if (validDetails == true) {
						JOptionPane.showMessageDialog(frame, "This username has been taken.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid username", 0);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					
					//check that the password is strong enough, else show error message
					if(checkPass(password1.getText())&& validDetails == true) {
						details[9]=password1.getText();
					}
					else if (validDetails == true){
						JOptionPane.showMessageDialog(frame, "Please enter a valid password. It needs to be at least 6 characters long and contain at least one upper case and lower case letter, and one number.");
						try {
					    	databaseConnect.logAccess("Patient tried to register but entered an invalid password", Login.patientID);
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						validDetails = false;
					}
					
					//value selected in gender content box is stored in details list
					details[10] =String.valueOf(gender1.getSelectedItem());
					//value selected in doctor content box is stored in details list
					
					details[11] = String.valueOf(doctor1.getSelectedItem());
								
					
					
					// if all details are valid add them to database and show welcome screen
					if(validDetails) {
						databaseConnect.addToDB(details);
						JOptionPane.showMessageDialog(frame, "You have now registered, please login with your username and password to make bookings.");
						Login login = new Login();
						login.setVisible(true);
						try {
					    	databaseConnect.logAccess("Patient successfully registered", databaseConnect.getPatientID(username1.getText()));
					    	//messages added
					    	String message = "new patient registered: patientID = " + details[1] + " " + details[2];
					    	databaseConnect.updateDMessages("new patient registered", details[11]); 
					    	databaseConnect.updatePMessages("registration successful)", databaseConnect.getPatientID(details[8]) );
					    }catch(Exception exc) {
					    	exc.printStackTrace();
					    }
						dispose();
					}
					
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
    }
    
    /**
     * Checks that the input is a not null string value of length 45 
     * @param input - string, address, forename or surname
     * @return boolean - true if input is valid and able to be added to database, else false
     */
    public boolean checkVal(String input) {
    	if (input.length()<=45&& !input.equals("") ) {
    		
    		return true;
    	}
    	return false;
    }
    
    /**
     * Checks that the password entered is strong and valid enough
     * @param password - string
     * @return boolean - true if password is strong enough, false if too weak
     */
    public boolean checkPass(String password) {
    	boolean upper = false;
    	boolean lower = false;
    	boolean number = false;
    	
    	if(password.length() <7 ) {
    		return false;
    	}
    	for(int i=0; i<password.length() ; i++) {
    		if (password.charAt(i)>64 && password.charAt(i)< 91) {
    			upper = true;
    		}
    		else if(password.charAt(i)>47 && password.charAt(i)< 58) {
    			number = true;
    		}    
    		else if(password.charAt(i)>96 && password.charAt(i)< 123) {
    			lower = true;
    		}
    	}
    	return upper&&number&&lower;
    }
    
    /**
     * check if the string contains numbers
     * @param number - string input to check
     * @return false if there's other characters in the string
     */
    public boolean isNumber(String number) {
    	boolean isNumber = false;
    	
    	for(int i=0; i<number.length() ; i++) {
    		if(number.charAt(i)>47 && number.charAt(i)< 58) {
    			isNumber = true;
    		}    
    		else {
    			return false;
    		}
    	}
    	return isNumber;
    }
  
    /**
     * checks the format of date, and check its a correct date
     * @param date - entered in registration form
     * @return true if its a valid date, otherwise false
     */
    public boolean dateFormat(String date) {
    	String[] format = date.split("-");
    	boolean number = true;
    	int month =0;
    	int day =0;
    	int year =0;
    	
    	//checks that the date given is numeric
    	for(int i =0; i< format.length ; i++) {
    		if(!isNumber(format[i])) {
    			number = false;
    		}
    	}
    	//checks the format is correct
    	if(format[0].length() == 4 && number&& format[1].length() == 2 && format[2].length() == 2) {
    		year = Integer.valueOf(format[0]);
    		month = Integer.valueOf(format[1]);
    		day = Integer.valueOf(format[2]);
    		
    		//checks the year is valid 
    		if(year> 2021 || year < 1900) {
    			return false;
    		}
    		//checks the month and days are valid
    		else if(month< 1 || month>12 ) {
    			return false;
    		}
    		//checks february date is correct
    		else if (month== 2 && day >28) {
    			return false;
    		}
    		//check 30 day months are correct
    		else if((month == 9|| month == 4 || month == 6 || month == 11)&&(day< 1 || day>31)) {
    			return false;
    		}
    		//check other months are correct 
    		else if(day< 1 || day>31 ){
    			return false;
    		}
    		return true;
    	}
    	return false;
    }
    
    /** 
     * checks the phone number is in the correct format - 11 digits, begins with 0, is a number
     * @param phoneNo - entered in registration
     * @return true if phone number is in correct format, false if not
     */
    public boolean checkPhone(String phoneNo) {
    	if (phoneNo.equals("")) {
    		return false;
    	}
    	else if(!(phoneNo.charAt(0)== 48)) {
    		return false;
    	}
    	else if (phoneNo.length() !=11) {
    		return false;
    	}
    	else if (!isNumber(phoneNo)) {
    		return false;
    	}
    	return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {


    }
    
    

}
