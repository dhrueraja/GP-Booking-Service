package dataBaseConnect;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.text.*;
import java.util.Date;


public class connection {
	private Statement stmt = null;
	private Connection connect = null;
	/**
	 * setting up a connection to the database
	 * @throws Exception
	 */
	public void setUpDatabase() throws Exception{
		try {
//establish a connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			//connect =DriverManager.getConnection(
				//"jdbc:mysql://127.0.0.1:3306/gpsurgery?" + "user=root&password=Chaucer17!lml22");
			//connect =DriverManager.getConnection(
			//"jdbc:mysql://127.0.0.1:3306/gpsurgery?" + "user=root&password=jamaicinha12");
		connect =DriverManager.getConnection(
		"jdbc:mysql://127.0.0.1:3306/gpsurgery?" + "user=root&password=D11ruebina!");

			stmt =connect.createStatement();
		} catch(Exception e){
			throw e;
		}
	}
	/**
	 * checks if the username entered is in the database - either for logging in or registering
	 * @param username - the username that the patient has entered
	 * @return int, either 0 (if not in database) or the patientID
	 * @throws Exception
	 */
	public int checkUsername(String username)throws Exception{
//establishes data base connection
		setUpDatabase();
		String tableUsername;
		stmt =connect.createStatement();
//execute query receiving user name and patientID from the table
		ResultSet rs = stmt.executeQuery("select username,patientID from patient");
//search the table results for the entered user name
		while(rs.next()) {
			tableUsername = rs.getString(1);
//if the user name given matches one in the table then return the patientID
			if (tableUsername.equals(username)){
				return rs.getInt(2);
			}
		}
		return 0;
	}
	/**
	 * checks if the password entered matches the password that the patient registered with
	 * @param password - the entered password
	 * @param patientID - the id of the person trying to login (based off of user name entered)
	 * @return true if password matches the user name, false if it doesn't
	 * @throws Exception
	 */
	public boolean checkPassword(String password, int patientID) throws Exception {
		setUpDatabase();
//password from table
		String tablePassword;
		stmt =connect.createStatement();
//get the password that matches the given user name
		ResultSet rs = stmt.executeQuery("select password from patient where patientID = " + patientID);
		while (rs.next()) {
			tablePassword = rs.getString(1);
//if password in table matches the given one then return true
			if(tablePassword.equals(password)) {
				return true;
			}
		}
//if passwords don't match return false
		return false;
	}
	/**
	 * gets any new messages from the database and marks them as old
	 * @param patientID - of the patient who has just logged in
	 * @return the new messages
	 * @throws Exception
	 */
	public String openNewMessages(int patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		String newMessages = "";
		if (patientID ==0) {
			return "";
		}
//get message contents, date and id from the messages table where patientID matches the patient logged in
// and where the message is new
		pStmt = connect.prepareStatement("select contents, messageDate, messageID from messages inner join patient on messages.patientID = patient.patientID where oldMessage =?");
		pStmt.setString(1, "F");
		ResultSet rs = pStmt.executeQuery();
// loops through all of the new messages and creates a string
		while(rs.next()) {
			newMessages +=  rs.getString(2) + ": " + rs.getString(1) + "\n";
//once a message has been output its changed to an old message
			pStmt = connect.prepareStatement("update messages set oldMessage =? where messageID =?" );
			pStmt.setString(1, "T");
			pStmt.setInt(2, rs.getInt(3));
			pStmt.executeUpdate();
		}
		//System.out.println(newMessages);
//return "" if no new messages or all of the new messages from the table
		return newMessages;
	}
	/**
	 * retrieve the forenames and surnames of all of the doctors in the database
	 * @return an array list of all of the doctors in the database
	 * @throws Exception
	 */
	public ArrayList<String> getDoctors() throws Exception{
		ArrayList<String> doctors = new ArrayList<String>();
		setUpDatabase();
		PreparedStatement pStmt;
//getting forename and surname of doctors from db
		pStmt = connect.prepareStatement("select forename, surname from doctor");
		ResultSet rs = pStmt.executeQuery();
//adding each doctor to the array list
		while(rs.next()) {
			doctors.add(rs.getString(1) + " " + rs.getString(2));
		}
		return doctors;
	}
	/**
	 * retrieving the forename of the patient from the db.
	 * @param patientID - id of the patient who has logged in
	 * @return either the name of the patient or "" if no patient exists
	 * @throws Exception
	 */
	public String getName(int patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		pStmt = connect.prepareStatement("select forename from patient where patientID =?");
		pStmt.setInt(1, patientID);
		ResultSet rs = pStmt.executeQuery();
//return patient name
		while(rs.next()) {
			return(rs.getString(1));
		}
		return "";
	}
	/**
	 * adding the registration details to the database
	 * @param details - string of each of the entered information to be added (all except primary key)
	 * @throws Exception
	 */
	public void addToDB(String[] details) throws Exception {
		setUpDatabase();
		PreparedStatement pStmt;
		//assigns the patientID
		int patientID = assignPatientID();
		//logs the patient registering to survery 
		//logAccess("Patient successfully registered to the surgery", patientID);
		//gets the doctorsID using the forename and surname of the doctor
		int doctorID = getDoctorsID(details[11]);

		//insert values into db
		pStmt = connect.prepareStatement("insert into patient(patientID, title, forename, surname, address, dob, nhsNo, email, phoneNo, username, password, gender, doctorID) \r\n"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
//set patientID
		pStmt.setInt(1, patientID);
//set title
		pStmt.setString(2,details[0]);
//set forename
		pStmt.setString(3,details[1]);
//set surname
		pStmt.setString(4,details[2]);
//set address
		pStmt.setString(5,details[3]);
//set date of birth
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(details[4]);
		java.sql.Date myDate =new java.sql.Date(date.getTime());
		pStmt.setDate(6, myDate);
//nhs number can be null
		if(details[5] =="") {
			pStmt.setString(7, null);
		}
		else {
			pStmt.setString(7, details[5]);
		}
//email can be null
		if(details[6] =="") {
			pStmt.setString(8, null);
		}
		else {
			pStmt.setString(8, details[6]);
		}
//set phone number
		pStmt.setString(9, details[7]);
//set username
		pStmt.setString(10, details[8]);
//set password
		pStmt.setString(11,details[9]);
//set gender
		pStmt.setString(12, details[10]);
//set doctorID
		pStmt.setInt(13, doctorID);
		pStmt.executeUpdate();
		
		updatePMessages("Successfully registered to the GP surgery", patientID);

	}

	/**
	 * assigning a patientID to the next patient being registered
	 * @return the new patientID
	 * @throws Exception
	 */
	public int assignPatientID() throws Exception{
		setUpDatabase();
		int lastPID = 0;
		PreparedStatement pStmt;
		//patientID is incremented with each entry so get the biggest patientID from the database and increment it
		pStmt = connect.prepareStatement("select max(patientID) from patient");
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			lastPID = rs.getInt(1);
		}
//return the new patientID
		System.out.println(lastPID+1);
		return lastPID + 1;
	}


	/**
	 * assigning a bookingID when a new booking is made
	 * @return the new bookingID
	 * @throws Exception
	 */
	public int assignBookingID() throws Exception{
		setUpDatabase();
		int lastBID = 0;
		PreparedStatement pStmt;
		//find the max ID number from the table
		pStmt = connect.prepareStatement("select max(bookingID) from booking");
		ResultSet rs = pStmt.executeQuery();

		while(rs.next()) {
			lastBID = rs.getInt(1); // retrieves the ID value of the last booking
		}

		return lastBID + 1;// increment the newest booking ID
	}



	/**
	 * retrieve the doctorsID from the db based on the doctors forename and surname
	 * @param doctor - doctors forename and surname
	 * @return doctorID
	 * @throws Exception
	 */
	public int getDoctorsID(String doctor) throws Exception{
		setUpDatabase();
		//split into forename and surname
		String[] docName = doctor.split(" ");
		int doctorID = 0;
		PreparedStatement pStmt;
		pStmt = connect.prepareStatement("select doctorID from doctor where forename = ? and surname =?");
		pStmt.setString(1, docName[0]);
		pStmt.setString(2, docName[1]);
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			doctorID = rs.getInt(1);
		}

		return doctorID;
	}

	/**
	 * checks to see if the NHS number is already in the database (whether its unique)
	 * @param nhsNo -10 digit nhs number entered at registration
	 * @return true if it not already in the db, false if it is
	 * @throws Exception
	 */
	public boolean checkNhsNo(String nhsNo)throws Exception {
		setUpDatabase();
		PreparedStatement pStmt;
		if(nhsNo == "") {
			return false;
		}
		pStmt = connect.prepareStatement("select patientID from patient where nhsNo =?");
		pStmt.setString(1, nhsNo);
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			return false;
		}

		return true;

	}

	/**
	 * updates the patient messages table with new message
	 * @param message message to be added
	 * @param patientID patient who will be receiving the message
	 * @throws Exception
	 */
	public void updatePMessages(String message, int patientID) throws Exception {
		setUpDatabase();
		PreparedStatement pStmt;
		int mID = 0;
		pStmt = connect.prepareStatement("select max(messageID) from messages");
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			mID = rs.getInt(1) + 1;
		}
		LocalDate date = LocalDate.now();
		pStmt = connect.prepareStatement("insert into messages(messageID, patientID, contents, messageDate, oldMessage) values(?,?,?, ?,?)");
		pStmt.setInt(1, mID);
		pStmt.setInt(2, patientID);
		pStmt.setString(3, message);
		pStmt.setDate(4, java.sql.Date.valueOf(date));
		pStmt.setString(5, "F");
		pStmt.executeUpdate();

	}

	/**
	 * updates doctors messages table with new message
	 * @param message message to be added
	 * @param doctorsName the name of the doctor who will receive the message
	 * @throws Exception
	 */
	public void updateDMessages(String message,String doctorsName) throws Exception {
		setUpDatabase();
		PreparedStatement pStmt;
		int mID = 0;
		int doctorID = getDoctorsID(doctorsName);
		pStmt = connect.prepareStatement("select max(dMessageID) from doctormessages");
		ResultSet rs = pStmt.executeQuery();
		while(rs.next()) {
			mID = rs.getInt(1) + 1;
			//System.out.println(mID);
		}
		LocalDate date = LocalDate.now();
		pStmt = connect.prepareStatement("insert into doctormessages(dMessageID, doctorID, contents, messageDate, oldMessage) values(?,?,?, ?,?)");
		pStmt.setInt(1, mID);
		pStmt.setInt(2, doctorID);
		pStmt.setString(3, message);
		pStmt.setDate(4, java.sql.Date.valueOf(date));
		pStmt.setString(5, "F");
		pStmt.executeUpdate();
	}


	public String getDoctor(int patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		int doctorID = 0;
		pStmt = connect.prepareStatement("select doctorID from patient where patientID = ?");
		pStmt.setInt(1, patientID);
		ResultSet rs = pStmt.executeQuery();

		while (rs.next()) {
			doctorID = rs.getInt(1);

		}

		pStmt = connect.prepareStatement("select forename, surname from doctor where doctorID = ?");
		pStmt.setInt(1, doctorID);
		ResultSet result = pStmt.executeQuery();

		while (result.next()) {
			return result.getString(1) + " " + result.getString(2);

		}

		return "";
	}

	public void newDoctor(String doctorsName, int patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		int doctorID = getDoctorsID(doctorsName);
		pStmt = connect.prepareStatement("update patient set doctorID = ? where patientID = ?");
		pStmt.setInt(1, doctorID);
		pStmt.setInt(2, patientID);
		pStmt.executeUpdate();
	}

	/**
	 * gets any previously made bookings from the database in the month and year as specified by the user
	 * @param month - month for which the user would like to view the made the bookings for
	 * @param year - year for which the user would like to view the made the bookings for
	 * @param patientID - of the patient who has just logged in
	 * @return the new messages
	 * @throws Exception
	 */
	public String displayBookings(String year, String month, String patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		String viewBookings = "";
		String earliestdatetime = year + "-" + month + "-01";
		String latestdatetime = year + "-" + month + "-31";
//System.out.println(earliestdatetime);
//System.out.println(latestdatetime);
//get dateTime and reason for appointment from the booking table
//where patientID matches the patient logged in and year and month matches dateTime
		pStmt = connect.prepareStatement("select date, time, reason from booking inner join patient on booking.patientID = patient.patientID = ? where date BETWEEN ? AND ?");
		pStmt.setString(1, patientID);
		pStmt.setDate(2, java.sql.Date.valueOf(earliestdatetime));
		pStmt.setDate(3, java.sql.Date.valueOf(latestdatetime));
		ResultSet rs = pStmt.executeQuery();

		while(rs.next()) {
			viewBookings +=  rs.getDate(1) + ": " + rs.getString(2) + ": " + rs.getString(3) + "\n";
		}
//return "" if no new messages or all of the new messages from the table
		return viewBookings;
	}
	/**
	 * retrieving the forename and surname of the patient from the db.
	 * @param patientID - id of the patient who has logged in
	 * @return either the name of the patient or "" if no patient exists
	 * @throws Exception
	 */
	public String getBothNames(int patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		pStmt = connect.prepareStatement("select forename, surname from patient where patientID =?");
		pStmt.setInt(1, patientID);
		ResultSet rs = pStmt.executeQuery();
//return patient name
		while(rs.next()) {
			return(rs.getString(1) + " " + rs.getString(2));
		}
		return "";
	}
	
	/**
	 * adding the new bookings to the database
	 * @param details to be added
	 * @throws Exception
	 */
	public void addToBookings(String details[]) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		int bookingID = assignBookingID();
		int doctorID = Integer.valueOf(details[1]);
		int patientID = Integer.valueOf(details[2]);
		String reason = details[3];
		String time = details[4];
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(details[0]);
		java.sql.Date sqlDate =new java.sql.Date(date.getTime());
		pStmt = connect.prepareStatement("insert into booking(bookingID, date, doctorID, patientID,"
		        + " reason, time) values(?,?,?,?,?,?)");
		pStmt.setInt(1, bookingID);
		pStmt.setDate(2, sqlDate);
		pStmt.setInt(3, doctorID);
		pStmt.setInt(4, patientID);
		pStmt.setString(5, reason);
		pStmt.setString(6, time);
		pStmt.executeUpdate();
	}
	
	/**
   * reschedule a booking
   * @param details to be added
   * @throws Exception
   */
  public void reschedule(String details[]) throws Exception{
      setUpDatabase();
      PreparedStatement pStmt;
      int bookingID = getBookingId(details[0], details[1]);
      int newBookingID = assignBookingID();
      String reason = details[4]; // in reschedule, reason is 4 
      String newTime = details[3]; 
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date newDate = formatter.parse(details[2]);
      java.sql.Date sqlDate =new java.sql.Date(newDate.getTime());
      pStmt = connect.prepareStatement("update booking set bookingID= ?, date=?, time=?, reason=? "
              + "where bookingId=?");
      pStmt.setInt(1, newBookingID);
      pStmt.setDate(2, sqlDate);
      pStmt.setString(3, newTime); 
      pStmt.setString(4, reason); 
      pStmt.setInt(5, bookingID); 

      pStmt.executeUpdate();
      
      System.out.println(reason);
  }

	

  /**
   * Returns the booking id for the date and time specified.
   * @param time The appointment's time
   * @param date The appointment's date
   * @return - the booking id 
   * @throws Exception
   */
  
    public int getBookingId(String oldDate, String oldTime) throws Exception {
    setUpDatabase(); 
    PreparedStatement pStmt;
    String[] details = new String[5];
    details[0] = oldDate;
    details[1] = oldTime;
    //String oldTime = details[1];
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date oldDatef = formatter.parse(details[0]);
    java.sql.Date sqlDate =new java.sql.Date(oldDatef.getTime());
    pStmt = connect.prepareStatement("select bookingID from booking where date= ? AND time= ?");
    pStmt.setDate(1, sqlDate); 
    pStmt.setString(2, oldTime); 
    ResultSet rs = pStmt.executeQuery(); 
    //return booking id 
    while(rs.next()) {
        return(rs.getInt(1)); 
    } return 0; // if its a null value (i.e. no bookingID match) 
}
   
  
  
	/**
	 * checks to see if a booking has already been made
	 * @param time - time of booking
	 * @param date - date of booking
	 * @param doctorID - doctor booking is with
	 * @return - true if there is a booking in the db, false if not
	 * @throws Exception
	 */
	public boolean checkBooking(String time, String date, int doctorID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = formatter.parse(date);
		java.sql.Date sqlDate =new java.sql.Date(newDate.getTime());
		pStmt = connect.prepareStatement("select * from booking where doctorID = ? AND date = ? AND time = ?");
		pStmt.setInt(1, doctorID);
		pStmt.setDate(2, sqlDate);
		pStmt.setString(3, time);
		ResultSet rs = pStmt.executeQuery();
		
		while(rs.next()) {
			//if there's already a booking then return true
			return true;
		}
		
		return false;
	}
	
	/**
	 * gets details of all appointments
	 * @param patientID - of the patient who has just logged in
	 * @return the details of the appointments
	 * @throws Exception
	 */
	public String displayDetails(String patientID) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		String viewDetails = "";

		//get dateTime and reason for appointment from the booking table
		//where patientID matches the patient logged in and year and month matches dateTime
		pStmt = connect.prepareStatement("select docNotes from details inner join patient on details.patientID = patient.patientID = ?");
		pStmt.setString(1, patientID);

		ResultSet rs = pStmt.executeQuery();

		while(rs.next()) {
			viewDetails +=  rs.getString(1) + "\n";
		}
		//return "" if no details
		return viewDetails;
	}
	
	public void logAccess(String reason, int patientID) throws Exception {
		int accessID=1;
		
		LocalDate now = LocalDate.now();  
		java.sql.Date sqlDate =java.sql.Date.valueOf(now);
		//System.out.println(now);
		
		Date time = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String timeStr = dateFormat.format(time);
		//System.out.println(timeStr);
		
		if(reason.length()>100) {
			reason = reason.substring(0,100);
		}
		
		setUpDatabase();
		PreparedStatement pStmt;
		pStmt = connect.prepareStatement("select MAX(accessID) from loginaccess");
		ResultSet rs = pStmt.executeQuery();

		while(rs.next()) {
			accessID = rs.getInt(1) +1;
		}
		
		pStmt = connect.prepareStatement("insert into loginaccess(accessID, patientID, date, time, accessed)"
				+ "values (?,?,?,?,?)");
		pStmt.setInt(1, accessID);
		if(patientID !=0) {
			pStmt.setInt(2, patientID);
		}
		else {
			pStmt.setNull(2, Types.INTEGER);
		}
		
		pStmt.setDate(3, sqlDate);
		pStmt.setString(4, timeStr);
		pStmt.setString(5, reason);
		
		pStmt.executeUpdate();
	}
	
	public int getPatientID(String username) throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		int patientID =0;
		
		pStmt = connect.prepareStatement("select patientID from patient where username =?");
		pStmt.setString(1, username);
		ResultSet rs = pStmt.executeQuery();

		while(rs.next()) {
			patientID = rs.getInt(1);
		}
		
		return patientID;
	}
	
	public String getPrescription(int patientID)throws Exception{
		setUpDatabase();
		PreparedStatement pStmt;
		String prescriptions = "";
		pStmt = connect.prepareStatement("select * from prescriptions where patientID = ?");
		pStmt.setInt(1, patientID);
		ResultSet rs = pStmt.executeQuery();

		while(rs.next()) {
			if(rs.getString(6).equals("T")) {
				prescriptions += "Name of medication: " + rs.getString(5) + ". Repeat prescription." + " Prescribed on: " +rs.getDate(7) +".   ";

			}
			else {
				prescriptions += "Name of medication: " + rs.getString(5) + ". One-Time prescription." + " Prescribed on: " +rs.getDate(7)+".   ";

			}
		}
		if(prescriptions.equals("")){
			return "No prescriptions.";
	}
		return prescriptions;
    }
		
	

}
