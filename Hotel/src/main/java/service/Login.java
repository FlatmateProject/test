package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import dao.Singleton;

public class Login {
	
	private static final Logger log = Logger.getLogger(Login.class);
	
	private Singleton sing = Singleton.getInstance();
	private ResultSet rset;

	public Boolean check(String login, char[] pass) {
		log.info(login + " " + String.valueOf(pass));
		try {
			rset = sing.query("select haslo from pracownicy where idp_pesel = \"" + login + "\"");
			rset.next();
			return String.valueOf(pass).equals(rset.getString(1));
		} catch (SQLException e) {
			return false;
		}
	}

}