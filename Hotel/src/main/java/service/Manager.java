package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import dao.Singleton;

public class Manager {

	
	private static final Logger log = Logger.getLogger(Manager.class);

	private Singleton sing = Singleton.getInstance();
	private ResultSet rset1, rset2;

	public int getCount(String s) {
		String stmt = "select count(*) from " + s;
		rset1 = sing.query(stmt);

		if (rset1 == null) {
			return 0;
		}
		try {
			rset1.next();
			return rset1.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Co� zepsu�e�!", "UWAGA!",
					JOptionPane.ERROR_MESSAGE);
		}
		return 0;
	}

	public Boolean updateData(String table, String l[], String d[], int size) {
		String q = "update hotel." + table + " set ";
		for (int i = 1; i < size; i++) {
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

	public Boolean deleteData(String table, String l, String d) {
		String q = "delete from hotel." + table + " where " + l + " = " + d;
		return sing.queryUp(q);
	}

	public Boolean insertData(String table, String l[], String d[], int size) {
		String q = "insert into hotel." + table + "(";
		for (int i = 0; i < size; i++) {
			if (i == 0 && d[i].isEmpty()) {
				return false;
			} else if (!d[i].isEmpty()) {
				if (i != 0)
					q += ", ";
				q += l[i];
			}
		}
		q += ") values(";
		for (int i = 0; i < size; i++) {
			if (i == 0 && d[i].isEmpty()) {
				return false;
			} else if (!d[i].isEmpty()) {
				if (i != 0)
					q += ", ";
				q += "\"" + d[i] + "\"";
			}
		}
		q += ");";
		log.info(q);
		return sing.queryUp(q);
	}

	public Boolean checkTable(String table, String l, String d) {
		String q = "select count(*) from " + table + " where " + l + " = " + d;
		rset1 = sing.query(q);
		try {
			rset1.next();
			if (rset1.getString(1).equals("0")) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Co� zepsu�e�!", "UWAGA!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public String[] getColumns(String s) {
		int i = 0;
		rset1 = sing.query("show columns from " + s);
		try {
			if (rset1 == null) {
				return null;
			}
			rset1.last();
			String cols[] = new String[rset1.getRow()];
			rset1.first();
			do {
				cols[i++] = rset1.getString(1);
			} while (rset1.next());
			return cols;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Co� zepsu�e�!", "UWAGA!",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public String[][] createTable(String table) {
		try {
			int i = 0, j = 0, cols, rows;
			rset1 = sing.query("show columns from hotel." + table);
			rset2 = sing.query("select * from hotel." + table);
			rset1.last();
			cols = rset1.getRow();
			rset2.last();
			rows = rset2.getRow();
			// Object rowData[][] = new Object[rows][cols];
			String tableData[][] = new String[rows + 1][cols];
			rset1.first();
			rset2.first();
			do {
				tableData[0][i] = rset1.getString(1);
				i++;
			} while (rset1.next());
			i = 1;
			do {
				for (j = 0; j < cols; j++) {
					tableData[i][j] = rset2.getString(j + 1);
				}
				i++;
			} while (rset2.next());
			return tableData;
		} catch (Exception e) {
			log.info("Brak danych");
			String tableData[][] = { { "Brak danych" } };
			return tableData;
		}
	}

}
