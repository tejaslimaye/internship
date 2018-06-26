package com.uniken.automation.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

	private static int intNumConns = 0;
	private static DBUtils instance = null;
	private Connection conn =  null;
	public Connection getConnection()
	{
		return conn;
	}
	
	
	private DBUtils() {
		intNumConns++;
		// TODO Auto-generated constructor stub
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("resources/dbprops.properties"));
			String strDBDriver = props.getProperty("DB_DRIVER");
			String strDBURL = props.getProperty("DB_URL");
			String strDBUser = props.getProperty("DB_USER");
			String strDBPASS = props.getProperty("DB_PASS");
			if(strDBPASS==null)
			{
				strDBPASS="";
			}
			Class.forName(strDBDriver);
			conn = DriverManager.getConnection(strDBURL, strDBUser, strDBPASS);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error getting connection...exiting");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error getting connection...exiting");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error getting connection...exiting");
			System.exit(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error getting connection...exiting");
			System.exit(1);
		}
	}
	
	public static DBUtils getInstance()
	{
		if(instance==null)
			instance =  new DBUtils();
		return instance;
		
	}

	
		
}
