
package dataBaseConnect;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class connectionTest {
	
	private connection connect = new connection();
	

	@Test
	void testCheckUsername() throws Exception{
		//check a null value
		assertEquals(0, connect.checkUsername(""));
		//this username is only going to work when using Lauren's database 
		String username = "lml22";
		assertEquals(1, connect.checkUsername(username));
		//check an invalid username
		username = "abc1234";
		assertEquals(0, connect.checkUsername(username));
		//check a valid username that doesnt match the patientID expected
		username = "lml22";
		assertNotEquals(3, connect.checkUsername(username));
	}

	@Test
	void testCheckPassword() throws Exception{
		//check a null password and patientID
		assertFalse(connect.checkPassword("", 0));
		//check a password that doesnt match the patient ID
		assertFalse(connect.checkPassword("abcd", 1));
		//check a valid password that matches the username
		//NOTE: this will only work for lauren's database 
		assertTrue(connect.checkPassword("pass1234", 1));
	}

	@Test
	void testOpenNewMessages() throws Exception{
		//checking an invalid patient 
		assertEquals("", connect.openNewMessages(0));
		//checking a valid patient returns the valid message
		//NOTE: this will change when user logs into the system and either become "" or have different messages
		//assertEquals("2021-03-07: 	Doctor changed",connect.openNewMessages(1));
		//checking a valid patient returns no message if no message is in the system
	    //NOTE: this will change when user logs into the system and either stay as 0 "" or have different messages
		//assertEquals("",connect.openNewMessages(2));
	}

	@Test
	void testGetDoctors() throws Exception {
		ArrayList<String> doctors = new ArrayList<String>();
		//check a null arrayList
		assertNotEquals(doctors, connect.getDoctors());
		//check a valid arrayList (NOTE this only works for laurens database)
		doctors.add("seth ham");
		doctors.add("john doe");
		assertEquals(doctors, connect.getDoctors());
		//check an invalid arrayList
		doctors.add("jim smith");
		assertNotEquals(doctors, connect.getDoctors());
	}
	

	@Test
	void testGetName() throws Exception{
		//check a null name
		assertEquals("", connect.getName(0));
		//checking a valid patientID , NOTE this will only work for laurens database
		int patientID = 1;
		assertEquals("lauren", connect.getName(1));
		//checking an invalid patientID , NOTE this will only work for laurens database
		patientID = 1;
		assertNotEquals("john", connect.getName(12));
	}

	@Test
	void testGetDoctorsID() throws Exception {
		//check a null name
		assertEquals(0, connect.getDoctorsID(""));
		//NOTE: this will only work for laurens db
		//checking a valid doctor name
		assertEquals(1, connect.getDoctorsID("seth ham"));
		//checking a name not in the db
		assertNotEquals(1, connect.getDoctorsID("jane doe"));
	}

	@Test
	void testCheckNhsNo() throws Exception{
		//check a null name
		assertFalse(connect.checkNhsNo(""));
		//if nhs no is already in db, will only work for laurens db
		assertFalse(connect.checkNhsNo("1234567890"));
		//if nhs no is unique 
		assertTrue(connect.checkNhsNo("2345678901"));
	}

	@Test
	void testGetDoctor() throws Exception{
		//patientID not valid
		assertEquals("", connect.getDoctor(0));
		//patientID valid & matches doctor
		//will only work for laurens db
		assertEquals("seth ham", connect.getDoctor(1));
	}

	@Test
	void testDisplayBookings() throws Exception{
		assertEquals("", connect.displayBookings("", "", ""));
	}

}

//package dataBaseConnect;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//
//class connectionTest {
//	
//	private connection connect = new connection();
//	
//
//	@Test
//	void testCheckUsername() throws Exception{
//		//check a null value
//		assertEquals(0, connect.checkUsername(""));
//		//this username is only going to work when using Lauren's database 
//		String username = "lml22";
//		assertEquals(1, connect.checkUsername(username));
//		//check an invalid username
//		username = "abc1234";
//		assertEquals(0, connect.checkUsername(username));
//		//check a valid username that doesnt match the patientID expected
//		username = "lml22";
//		assertNotEquals(3, connect.checkUsername(username));
//	}
//
//	@Test
//	void testCheckPassword() throws Exception{
//		//check a null password and patientID
//		assertFalse(connect.checkPassword("", 0));
//		//check a password that doesnt match the patient ID
//		assertFalse(connect.checkPassword("abcd", 1));
//		//check a valid password that matches the username
//		//NOTE: this will only work for lauren's database 
//		assertTrue(connect.checkPassword("pass1234", 1));
//	}
//
//	@Test
//	void testOpenNewMessages() throws Exception{
//		//checking an invalid patient 
//		assertEquals("", connect.openNewMessages(0));
//		//checking a valid patient returns the valid message
//		//NOTE: this will change when user logs into the system and either become "" or have different messages
//		assertEquals("2021-03-23: Doctor changed",connect.openNewMessages(2));
//		//checking a valid patient returns no message if no message is in the system
//	    //NOTE: this will change when user logs into the system and either stay as 0 "" or have different messages
//		assertEquals("",connect.openNewMessages(5));
//	}
//
//	@Test
//	void testGetDoctors() throws Exception {
//		ArrayList<String> doctors = new ArrayList<String>();
//		//check a null arrayList
//		assertNotEquals(doctors, connect.getDoctors());
//		//check a valid arrayList (NOTE this only works for laurens database)
//		doctors.add("seth ham");
//		doctors.add("john doe");
//		assertEquals(doctors, connect.getDoctors());
//		//check an invalid arrayList
//		doctors.add("jim smith");
//		assertNotEquals(doctors, connect.getDoctors());
//	}
//	
//
//	@Test
//	void testGetName() throws Exception{
//		//check a null name
//		assertEquals("", connect.getName(0));
//		//checking a valid patientID , NOTE this will only work for laurens database
//		int patientID = 1;
//		assertEquals("lauren", connect.getName(1));
//		//checking an invalid patientID , NOTE this will only work for laurens database
//		patientID = 1;
//		assertNotEquals("john", connect.getName(12));
//	}
//
//	@Test
//	void testGetDoctorsID() throws Exception {
//		//check a null name
//		assertEquals(0, connect.getDoctorsID(""));
//		//NOTE: this will only work for laurens db
//		//checking a valid doctor name
//		assertEquals(1, connect.getDoctorsID("seth ham"));
//		//checking a name not in the db
//		assertNotEquals(1, connect.getDoctorsID("jane doe"));
//	}
//
//	@Test
//	void testCheckNhsNo() throws Exception{
//		//check a null name
//		assertFalse(connect.checkNhsNo(""));
//		//if nhs no is already in db, will only work for laurens db
//		assertFalse(connect.checkNhsNo("1234567890"));
//		//if nhs no is unique 
//		assertTrue(connect.checkNhsNo("2345678901"));
//	}
//
//	@Test
//	void testGetDoctor() throws Exception{
//		//patientID not valid
//		assertEquals("", connect.getDoctor(0));
//		//patientID valid & matches doctor
//		//will only work for laurens db
//		assertEquals("seth ham", connect.getDoctor(1));
//	}
//
//	//@Test
//	//void testDisplayBookings() {
//		//fail("Not yet implemented");
//	//}
//
//}

