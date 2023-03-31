package application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author baona
 * Establish the connection to Sqlite database
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import application.Message;
public class SqLiteConnection {
	private static String dbName = "acc.db";
	public static Connection connector() {
        try {
            String dbURL = "jdbc:sqlite:"+dbName;
            Connection conn = DriverManager.getConnection(dbURL);
            return conn;
        } catch (SQLException e) {
        	Message.showError(e.toString());
            return null;
        }		
	}
	public static String getDbName() {
		return dbName;
	}
	
}
