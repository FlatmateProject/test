package service;



import dao.Singleton;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestBook {
	
	private static final Logger log = Logger.getLogger(GuestBook.class);
	
	private Singleton sing = Singleton.getInstance();
	private ResultSet rset1, rset2;

	public GuestBook() {

	}

	public String[] getLabel(String table) {
		int i = 0;
		String s[] = new String[12];
		Singleton.getInstance();
		rset1 = sing.query("show columns from hotel." + table);
		if(rset1 == null){
			return new String[]{"","","","","","","","","","","","",""};
		}
		try {
			while (rset1.next()) {
				s[i] = rset1.getString(1);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public JTable getTable(String s, String s2) {
		try {
			int i = 0, j = 0, cols, rows;
			String columnNames[] = { "ID_REZ", "IDF_KRS", "ID_POKOJU", "TYP", "DATA_Z", "DATA_W", "" };
			rset2 = sing.query("select * from hotel." + s + " " + s2);
			rset1.last();
			cols = rset1.getRow();
			rset2.last();
			rows = rset2.getRow();
			Object rowData[][] = new Object[rows][cols];
			rset1.first();
			rset2.first();
			do {
				columnNames[i] = rset1.getString(1);
				i++;
			} while (rset1.next());
			i = 0;
			do {
				for (j = 0; j < cols; j++) {
					rowData[i][j] = rset2.getString(j + 1);
				}
				i++;
			} while (rset2.next());
			JTable t = new JTable(rowData, columnNames);
			t.setFillsViewportHeight(true);
			return t;
		} catch (Exception e) {
			log.info("Brak danych");
			Object rowData[][] = { { "Brak danych" } };
			String columnNames[] = { "Brak danych" };
			return new JTable(rowData, columnNames);
		}
	}
	
	public JTable createTable(String s, String s2) {
		try {
			int i = 0, j = 0, cols, rows;
			rset1 = sing.query("show columns from hotel." + s);
			rset2 = sing.query("select * from hotel." + s + " " + s2);
			rset1.last();
			cols = rset1.getRow();
			rset2.last();
			rows = rset2.getRow();
			Object rowData[][] = new Object[rows][cols];
			String columnNames[] = new String[cols];
			rset1.first();
			rset2.first();
			do {
				columnNames[i] = rset1.getString(1);
				i++;
			} while (rset1.next());
			i = 0;
			do {
				for (j = 0; j < cols; j++) {
					rowData[i][j] = rset2.getString(j + 1);
				}
				i++;
			} while (rset2.next());
			JTable t = new JTable(rowData, columnNames);
			t.setFillsViewportHeight(true);
			return t;
		} catch (Exception e) {
			log.info("Brak danych");
			Object rowData[][] = { { "Brak danych" } };
			String columnNames[] = { "Brak danych" };
			return new JTable(rowData, columnNames);
		}
	}

	public Boolean updateClientData(String l[], String d[]) {
		String q = "update hotel.klienci set ";
		for (int i = 1; i < 10; i++) {
			if (i != 1)
				q += ", ";
			if (!d[i].isEmpty())
				q += l[i] + " = \"" + d[i] + "\"";
			else
				q += l[i] + " = :OLD." + l[i];
		}
		q += " where " + l[0] + " = \"" + d[0] + "\"";
		log.info(q);
		return sing.queryUp(q);
	}

	public String[] getClientData() {
		int i = 0;
		String s[] = new String[10];
		Singleton.getInstance();
		rset1 = sing.query("show columns from hotel.klienci;");
		try {
			while (rset1.next()) {
				if (rset1.getString(1) != "Uwagi") {
					s[i] = rset1.getString(1);
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
}
