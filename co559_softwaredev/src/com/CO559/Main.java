package com.CO559;
import com.CO559.Login;
import dataBaseConnect.connection;
//import java.sql.*;
//import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception{

        Login login = new Login();
        connection connect = new connection();
        
        try {
			connect.logAccess("Unknown patient accessed the login page", Login.patientID);
		}catch(Exception exc) {
			exc.printStackTrace();
		}
    }
}
