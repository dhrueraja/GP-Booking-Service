//package com.CO559;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
////
//public class RegistrationTest {
//	private Registration reg = new Registration();
////
////
//	@Test
//	public void testCheckVal() {
//		//testing a NN value
//		assertFalse(reg.checkVal(""));
////		//testing a value above 45
//		String input = "123456765432345tyuytrdfgbnbgfdfgbhgfdfghgfdfghefgh";
//		assertFalse(reg.checkVal(input));
//		//testing a value equal to 45
//		input =  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrs";
//		assertTrue(reg.checkVal(input));
////		//testing a value less than 45
//		input = "abcdefg";
//		assertTrue(reg.checkVal(input));
//	}
////
//	@Test
//	public void testCheckPass() {
////		//testing a null value
//		assertFalse(reg.checkPass(""));
////		//testing a value with length < 7
//		String password = "abcd";
//		assertFalse(reg.checkPass(password));
////		//testing a value with valid length & all lowercase
//		password = "abcdefghi";
//		assertFalse(reg.checkPass(password));
////		//testing a value with valid length & all uppercase
//		password = "ABCDEFGHI";
//		assertFalse(reg.checkPass(password));
////		//testing a value with valid length & all numeric
//		password = "1234567";
//		assertFalse(reg.checkPass(password));
////		//testing a value with valid length & all upper and lowercase
//		password = "abcDEFGHI";
//		assertFalse(reg.checkPass(password));
////		//testing a value with valid length & all numeric and lowercase
//		password = "abcd4567";
//		assertFalse(reg.checkPass(password));
////		//testing a value with valid length & all numeric and uppercase
//		password = "ABCD4567";
//		assertFalse(reg.checkPass(password));
////		//testing a valid password
//		password = "abcDEF123";
//		assertTrue(reg.checkPass(password));
//	}
////
//	@Test
//	public void testIsNumber() {
//		//testing a null value
//		assertFalse(reg.isNumber(""));
////		//test all alphabetic characters
//		String number = "abcdefghi";
//		assertFalse(reg.isNumber(number));
////		//testing a mix of numbers and letters
//		number = "abcd1234";
//		assertFalse(reg.isNumber(number));
////		//testing non alphanumeric characters
//		number = "!�$%^^";
//		assertFalse(reg.isNumber(number));
////		//testing a valid number - using 0 and 9 to check the bounds are correct
//		number = "0123456789";
//		assertTrue(reg.isNumber(number));
//	}
////
//	@Test
//	public void testDateFormat() {
////		//testing a null value
//		assertFalse(reg.dateFormat(""));
////		//testing non numeric in correct format
//		String date = "YYYY-MM-DD";
//		assertFalse(reg.dateFormat(date));
////		//testing invalid year
//		date = "1850-02-25";
//		assertFalse(reg.dateFormat(date));
////		//testing invalid month
//		date = "2020-20-15";
//		assertFalse(reg.dateFormat(date));
////		//testing invalid date
//		date = "2020-01-45";
//		assertFalse(reg.dateFormat(date));
////		//testing invalid month and date
//		date = "2020-13-32";
//		assertFalse(reg.dateFormat(date));
////		//testing invalid date and year
//		date = "2022-01-35";
//		assertFalse(reg.dateFormat(date));
//     	//testing invalid month and year
//		date = "2023-24-15";
//		assertFalse(reg.dateFormat(date));
////		//testing valid date in wrong format
//		date = "01-15-2020";
//		assertFalse(reg.dateFormat(date));
//		//testing valid date in wrong format
//		date = "15-01-2020";
//		assertFalse(reg.dateFormat(date));
////		//testing invalid type
//		date = "aaa";
//		assertFalse(reg.dateFormat(date));
////		//testing valid date in correct format
//		date = "2020-01-15";
//		assertTrue(reg.dateFormat(date));
//	}
////
//	@Test
//	public void testCheckPhone() {
//		//testing a null value
//		assertFalse(reg.checkPhone(""));
//		//starts with a 0 but length < 11
//		String phoneNo = "078456723";
//		assertFalse(reg.checkPhone(phoneNo));
//		//starts with a 0 but length > 11
//		phoneNo = "07845672387543";
//		assertFalse(reg.checkPhone(phoneNo));
//		//alphabetic digits
//		phoneNo = "abcdhnfj";
//		assertFalse(reg.checkPhone(phoneNo));
//		//correct length,starts with a 0 but alphanumeric digits
//		phoneNo = "078456723AB";
//		assertFalse(reg.checkPhone(phoneNo));
//		//invalid length and doesn't start with 0
//		phoneNo = "78456723";
//		assertFalse(reg.checkPhone(phoneNo));
//		//valid length but doesn't start with 0
//		phoneNo = "97531345456";
//		assertFalse(reg.checkPhone(phoneNo));
//		//correct format and length
//		phoneNo = "07531345456";
//		assertTrue(reg.checkPhone(phoneNo));
//
//	}
////
//}
