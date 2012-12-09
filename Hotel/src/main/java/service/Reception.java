package service;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import dao.Singleton;

public class Reception {
	
	private static final Logger log = Logger.getLogger(Reception.class);
	
	private Singleton sing = Singleton.getInstance();
	private ResultSet rset1, rset2, rset3, rset4, rset5, rset6, rset7;

	public Reception() {

	}

	public JTable createTable(String s1) {
		try {
			int i = 0, j = 0, cols, rows;
			rset1 = sing.query("show columns from hotel.rezerwacje");
			rset2 = sing.query("select * from hotel.rezerwacje" + s1);
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

	public String trimInput(String input) {
		return input.replaceAll("\\D*", "");
	}

	public boolean isPesel(String pesel) {
		pesel = trimInput(pesel);
		int psize = pesel.length();
		if (psize != 11) {
			return false;
		}
		int[] weights = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
		int j = 0, sum = 0, control = 0;
		int csum = new Integer(pesel.substring(psize - 1)).intValue();
		for (int i = 0; i < psize - 1; i++) {
			char c = pesel.charAt(i);
			j = new Integer(String.valueOf(c)).intValue();
			sum += j * weights[i];
		}
		control = 10 - (sum % 10);
		if (control == 10) {
			control = 0;
		}
		return (control == csum);
	}

	public boolean isKRS(String krs) {
		if (krs.length() != 10)
			return false;
		for (int i = 0; i < krs.length(); ++i) {
			if (krs.charAt(i) <= '0' || krs.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

	public boolean pay(int id, String date, String form, float tax, float much,
			String name) {
		boolean flag = true;
		rset1 = sing.query("select ID_REZ from hotel.rachunki where ID_REZ=" + id);
		try {
			rset1.first();
			if (rset1.getInt(1) == id)
				flag = false;
		} catch (Exception e) {
				flag = true;
		}
		if(flag){
		sing
				.queryUp("insert into hotel.rachunki (ID_REZ, DATA, FORMA, PODATEK, WARTOSC, NAZWA) VALUES("
						+ id
						+ ", "
						+ "'"
						+ date
						+ "'"
						+ ", "
						+ "'"
						+ form
						+ "'"
						+ ", "
						+ tax
						+ ", "
						+ much
						+ ", "
						+ "'"
						+ name
						+ "'" + ")");
		return true;
		}
		else{
		return false;}
	}

	public int diffInDays3(Date d1, Date d2) {
		return (int) Math.round((d1.getTime() - d2.getTime()) / 86400000.0);
	}

	public void deleteRez1(int id){
		sing.queryUp("delete from hotel.rezerwacje where ID_REZ=" + id);
	}
	public float calculate(int idRez) {
		float diff, price, serv, amount, roomCost = 0, servCost = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfY = new SimpleDateFormat("yyyy");
		DateFormat dfM = new SimpleDateFormat("MM");
		DateFormat dfD = new SimpleDateFormat("dd");
		Calendar calendar = Calendar.getInstance();
		Date date;
		String sYear = dfY.format(calendar.getTime());
		String sMonth = dfM.format(calendar.getTime());
		String sDay = dfD.format(calendar.getTime());
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			rset3 = sing
					.query("select DATA_Z,TYP from hotel.rezerwacje where ID_REZ="
							+ idRez);
			rset3.first();
			date = (Date) df.parse(rset3.getString(1));
			String sYear1 = dfY.format(date);
			String sMonth1 = dfM.format(date);
			String sDay1 = dfD.format(date);
			c1.set(Integer.parseInt(sYear), Integer.parseInt(sMonth), Integer
					.parseInt(sDay));
			c2.set(Integer.parseInt(sYear1), Integer.parseInt(sMonth1), Integer
					.parseInt(sDay1));
			diff = diffInDays3(c1.getTime(), c2.getTime());
			rset4 = sing.query("select CENA from hotel.klasy where ID_KLASY="
					+ Integer.parseInt(rset3.getString(2)));
			rset4.first();
			price = (Float.parseFloat(rset4.getString(1)));
			if(diff<1)
				return -1;
			else{
			roomCost = price * diff;
			rset5 = sing
					.query("select ID_USLUGI, CZAS from hotel.rekreacja where ID_REZ="
							+ idRez);
			rset5.first();
			do {
				rset6 = sing
						.query("select CENA from hotel.uslugi where ID_USLUGI="
								+ rset5.getString(1));
				rset6.first();
				serv = Float.parseFloat(rset6.getString(1));
				amount = Float.parseFloat(rset5.getString(2));
				servCost = servCost + (serv * amount);
			} while (rset5.next());
			return servCost + roomCost;
			}} catch (Exception e) {
			return servCost + roomCost;
		}
	}

	public boolean checkPay(int idRez) {
		int idR = 0;
		try {
			rset7 = sing
					.query("select id_rachunku from hotel.rachunki where id_rez="
							+ idRez);
			rset7.first();
			idR = Integer.parseInt(rset7.getString(1));
			if (idR > 0)
				return true;
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	public void deleteRez(int idRez2) {
		sing.queryUp("delete from hotel.rezerwacje where id_rez=" + idRez2);
	}

	public void archivRez(int idRez3, boolean idKind, long idK,
			int idRom, int type, String dZ, String dW) {
		if (idKind)
			sing
					.queryUp("insert into hotel.archiwum(ID_REZ, IDK_PESEL, ID_POKOJU, TYP, DATA_Z, DATA_W) VALUES("
							+ idRez3
							+ ", "
							+ idK
							+ ", "
							+ idRom
							+ ", "
							+ type
							+ ", "
							+ "'"
							+ dZ
							+ "'"
							+ ", "
							+ "'"
							+ dW
							+ "'"
							+ ")");
		else
			sing
					.queryUp("insert into hotel.archiwum(ID_REZ, IDF_KRS, ID_POKOJU, TYP, DATA_Z, DATA_W) VALUES("
							+ idRez3
							+ ", "
							+ idK
							+ ", "
							+ idRom
							+ ", "
							+ type
							+ ", "
							+ "'"
							+ dZ
							+ "'"
							+ ", "
							+ "'"
							+ dW
							+ "'"
							+ ")");
		sing.queryUp("update hotel.pokoje set ID_REZ=NULL WHERE ID_POKOJU=" + idRom);
	}

	public boolean isDate(String date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			df.parse(date);
		} catch (ParseException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public boolean isNumber(String num) {
		for (int i = 0; i < num.length(); ++i) {
			if (num.charAt(i) <= '0' || num.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

}
