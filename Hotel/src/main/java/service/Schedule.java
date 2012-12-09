package service;

import java.sql.ResultSet;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import dao.Singleton;

public class Schedule {
	
	private static final Logger log = Logger.getLogger(Schedule.class);
	private Singleton sing = Singleton.getInstance();
	private ResultSet rset1, rset2;

	public Schedule() {

	}

	public JTable getTable(int d, int m, int y) {
		try {
			int i = 0, j, rows;
			String columnNames[] = { "ID_REZ", "IDK_PESEL", "IDF_KRS", "ID_POKOJU",
					"TYP", "DATA_Z", "DATA_W", "IMIE", "NAZWISKO", "NAZWA FIRMY" };
			String q;
			if (d < 1 || d > 31) {
				q = "SELECT rezerwacje.*, klienci.imie, klienci.nazwisko, firmy.nazwa FROM rezerwacje left JOIN klienci ON rezerwacje.idk_pesel = klienci.idk_pesel left join firmy on rezerwacje.idf_krs = firmy.idf_krs where (month(data_z) = " + m + " AND year(data_z) = " + y + ") OR (month(data_w) = " + m + " AND year(data_w) = " + y + ")";
			} else {
				q = "SELECT rezerwacje.*, klienci.imie, klienci.nazwisko, firmy.nazwa FROM rezerwacje left JOIN klienci ON rezerwacje.idk_pesel = klienci.idk_pesel left join firmy on rezerwacje.idf_krs = firmy.idf_krs where (month(data_z) = " + m + " AND year(data_z) = " + y + " AND day(data_z) = " + d + ") OR (month(data_w) = " + m + " AND year(data_w) = " + y + " AND day(data_w) = " + d + ")";
			}
			log.info(q);
			rset1 = sing.query(q);
			rset1.last();
			rows = rset1.getRow();
			Object rowData[][] = new Object[rows][columnNames.length];
			rset1.first();
			do {
				for (j = 0; j < columnNames.length; j++) {
						rowData[i][j] = rset1.getString(j + 1);
				}
				i++;
			} while (rset1.next());
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
}
