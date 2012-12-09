package dao;

import org.apache.log4j.Logger;

import java.sql.*;
public class Singleton {

	private static final Logger log = Logger.getLogger(Singleton.class);
	
	private ResultSet rset;
	private static Singleton instance = null;
	private static Connection con;
	private Statement stmt = null;

	public static Singleton getInstance() {
		synchronized (Singleton.class) {
			if (instance == null) {
				instance = new Singleton();
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "hotel", "hotel_dupe");
				} catch (Exception e) {
					System.err.println(e.getLocalizedMessage()
							+ "\nBrak połączenia z bazą danych!");
				}
			}
		}
		return instance;
	}

	public ResultSet query(String s) {
		try {
			log.info(s);
			stmt = (Statement) con.createStatement();
			rset = stmt.executeQuery(s);
			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean queryUp(String s) {
		try {
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate(s);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
