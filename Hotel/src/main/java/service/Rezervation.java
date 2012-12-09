package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import dao.Singleton;

public class Rezervation {
	
	private static final Logger log = Logger.getLogger(Rezervation.class);
	
	private Singleton sing = Singleton.getInstance();
	private ResultSet rset1, rset2, rset4, rset5;

	public Rezervation() {

	}

	public JTable createClasTable() {
		try {
			int i = 0, j = 0, cols, rows;
			rset1 = sing.query("show columns from hotel.klasy");
			rset2 = sing.query("select * from hotel.klasy");

			rset1.last();
			cols = rset1.getRow();
			rset2.last();
			rows = rset2.getRow();
			Object rowData[][] = new Object[rows][cols];
			String columnNames[] = new String[cols];
			rset1.first();
			do {
				columnNames[i] = rset1.getString(1);
				i++;
			} while (rset1.next());
			i = 0;
			rset2.first();
			while (rset2.next()) {
				for (j = 0; j < cols; j++) {
					rowData[i][j] = rset2.getString(j + 1);
				}
				i++;
			} 
			JTable t = new JTable(rowData, columnNames);
			t.setFillsViewportHeight(true);
			return t;
		} catch (Exception e) {
			log.info("Brak danych");
		}
		return null;
	}

	public String[] createRoomList(int idKlasy, String date1, String date2) {
		String[] roomTemp = new String[50];
		String[] room = null;
		String[] room1 = { "Brak pokoi" };
		int i = 0;
		try {
			if (idKlasy == 0) {
				rset1 = sing.query("select * from hotel.pokoje");
			} else {
				rset1 = sing
						.query("select * from hotel.pokoje where ID_KLASY= "
								+ idKlasy);
			}
			if(rset1 == null){
				return null;
			}
			rset1.first();
			do {
				rset2 = sing
						.query("select count(ID_REZ) from hotel.rezerwacje where ID_POKOJU="
								+ rset1.getInt(1)
								+ " AND ("
								+ "'"
								+ date1 
								+ "' = DATA_Z OR"
								+ "'"
								+ date1 
								+ "' = DATA_W OR"
								+ "'"
								+ date2
								+ "' = DATA_Z OR"
								+ "'"
								+ date2
								+ "' = DATA_W OR ("
								+ "'"
								+ date1
								+ "' > DATA_Z AND"
								+ "'"
								+ date1
								+ "' < DATA_W) OR ("
								+ "'"
								+ date1
								+ "' < DATA_Z AND"
								+ "'"
								+ date2
								+ "' > DATA_Z) OR ("
								+ "'"
								+ date2
								+ "' > DATA_W AND"
								+ "'"
								+ date1
								+ "' < DATA_W) OR("
								+ "'"
								+ date2
								+ "' < DATA_W AND"
								+ "'"
								+ date2
								+ "' > DATA_Z)"
								+ ")"
						
						
						
						
						
						
						);

				if (rset2.first()) {
					if (rset2.getInt(1) == 0) {
						roomTemp[i] = rset1.getString(1);
						i++;
					} else {
					}
				}
			} while (rset1.next());
			room = new String[i];
			for (int j = 0; j < i; j++)
				room[j] = roomTemp[j];
		} catch (SQLException e) {
			return room1;
		}
		return room;
	}

	public JTable createTable(boolean idKind, long idK) {
		try {
			int i = 0, j = 0, cols, rows;
			rset1 = sing.query("show columns from hotel.klienci");
			if (idKind)
				rset2 = sing
						.query("select * from hotel.klienci where IDK_PESEL="
								+ idK);
			else
				rset2 = sing.query("select * from hotel.firmy where IDF_KRS="
						+ idK);

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
		}
		return null;
	}

	public JTable createServTable(String cat) {
		try {
			int i = 0, j = 0, cols, rows;
			rset1 = sing.query("show columns from hotel.uslugi");
			if (cat == "")
				rset2 = sing.query("select * from hotel.uslugi");
			else
				rset2 = sing.query("select * from hotel.uslugi where TYP="
						+ "'" + cat + "'");
			rset1.last();
			cols = rset1.getRow();
			rset2.last();
			rows = rset2.getRow();
			Object rowData[][] = new Object[rows][cols];
			String columnNames[] = new String[cols];
			rset1.first();
			do {
				columnNames[i] = rset1.getString(1);
				i++;
			} while (rset1.next());
			i = 0;
			rset2.first();
			while (rset2.next()) {
				for (j = 0; j < cols; j++) {
					rowData[i][j] = rset2.getString(j + 1);
				}
				i++;
			} 
			JTable t = new JTable(rowData, columnNames);
			t.setFillsViewportHeight(true);
			return t;
		} catch (Exception e) {
			return null;
		}
	}

	public String trimInput(String input) {
		return input.replaceAll("\\D*", "");
	}

	public boolean isValidNip(String nip) {
		nip = trimInput(nip);
		int nsize = nip.length();
		if (nsize != 10) {
			return false;
		}
		int[] weights = { 6, 5, 7, 2, 3, 4, 5, 6, 7 };
		int j = 0, sum = 0, control = 0;
		int csum = new Integer(nip.substring(nsize - 1)).intValue();
		if (csum == 0) {
			csum = 10;
		}
		for (int i = 0; i < nsize - 1; i++) {
			char c = nip.charAt(i);
			j = new Integer(String.valueOf(c)).intValue();
			sum += j * weights[i];
		}
		control = sum % 11;
		return (control == csum);
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

	public JTable fillForm() {
		return null;

	}

	public int diffInDays3(Date d1, Date d2) {
		return (int) Math.round((d1.getTime() - d2.getTime()) / 86400000.0);
	}

	public boolean isAfter(String dateStr) throws Exception {
		float diff = 0;
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
		date = (Date) df.parse(dateStr);
		String sYear1 = dfY.format(date);
		String sMonth1 = dfM.format(date);
		String sDay1 = dfD.format(date);
		c1.set(Integer.parseInt(sYear), Integer.parseInt(sMonth), Integer
				.parseInt(sDay));
		c2.set(Integer.parseInt(sYear1), Integer.parseInt(sMonth1), Integer
				.parseInt(sDay1));
		diff = diffInDays3(c2.getTime(), c1.getTime());
		if (diff <= 0)
			return false;
		else
			return true;

	}

	public String addDate(String date1, int day) throws ParseException {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		date = (Date) f.parse(date1);
		now.setTime(date);
		now.add(Calendar.DAY_OF_YEAR, +day);
		return f.format(now.getTime()).toString();

	}

	public float calculate(int room, int manyDay, int manyServ,
			String servTab[]) {
		float price, serv, amount, roomCost = 0, servCost = 0;
		try {
			rset4 = sing
					.query("select CENA from hotel.klasy where ID_KLASY=(select ID_KLASY from hotel.pokoje where ID_POKOJU="
							+ room + ")");
			rset4.first();
			price = (Float.parseFloat(rset4.getString(1)));
			roomCost = price * manyDay;
			for (int i = 0; i < manyServ; i++) {
				rset5 = sing.query("select CENA from hotel.uslugi where NAZWA="
						+ "'" + servTab[i] + "'");
				rset5.first();
				serv = Float.parseFloat(rset5.getString(1));
				amount = manyDay;
				servCost = servCost + (serv * amount);
			}
			return servCost + roomCost;
		} catch (Exception e) {
			return servCost + roomCost;
		}
	}

	public void createRezervation() {

	}

	public boolean addClient(long idK, String name, String surname,
			String state, String city, String street, int num, String stat,
			long tel, long nip, String coment) {
		rset1 = sing
				.query("select * from hotel.klienci where IDK_PESEL=" + idK);
		try {
			rset1.first();
			if (!rset1.getString(1).isEmpty())
				return false;
		} catch (SQLException e) {
		}
		sing
				.queryUp("insert into hotel.klienci (IDK_PESEL, IMIE, NAZWISKO, WOJEWODZTWO, MIASTO, ULICA, NR_LOKALU, STATUS, TELEFON, NIP, UWAGI) VALUES("
						+ idK
						+ ", "
						+ "'"
						+ name
						+ "'"
						+ ", "
						+ "'"
						+ surname
						+ "'"
						+ ", "
						+ "'"
						+ state
						+ "'"
						+ ", "
						+ "'"
						+ city
						+ "'"
						+ ", "
						+ "'"
						+ street
						+ "'"
						+ ", "
						+ num
						+ ", "
						+ "'"
						+ stat
						+ "'"
						+ ", "
						+ tel
						+ ", "
						+ nip
						+ ", "
						+ "'" + coment + "'" + ")");
		return true;

	}

	public boolean addComp(long idF, String name, String state, String city,
			String street, int num, String stat, long reg, long nip, long tel,
			String coment) {
		rset1 = sing.query("select * from hotel.firmy where IDF_KRS=" + idF);
		try {
			rset1.first();
			if (!rset1.getString(1).isEmpty())
				return false;
		} catch (SQLException e) {
		}
		sing
				.queryUp("insert into hotel.firmy (IDF_KRS, NAZWA, WOJEWODZTWO, MIASTO, ULICA, NR_LOKALU, STATUS, REGON, NIP, TELEFON, UWAGI) VALUES("
						+ idF
						+ ", "
						+ "'"
						+ name
						+ "'"
						+ ", "
						+ "'"
						+ state
						+ "'"
						+ ", "
						+ "'"
						+ city
						+ "'"
						+ ", "
						+ "'"
						+ street
						+ "'"
						+ ","
						+ num
						+ ", "
						+ "'"
						+ stat
						+ "'"
						+ ", "
						+ reg
						+ ", "
						+ nip
						+ ", "
						+ tel
						+ ","
						+ "'"
						+ coment
						+ "'" + ")");
		return true;

	}

	public boolean doRezerv(boolean idKind, long id, int idRom,
			String dateZ, String dateW) {
		rset1 = sing.query("select ID_KLASY from hotel.pokoje where ID_POKOJU=" + idRom);
		try {
			rset1.first();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (idKind)
			try {
				sing
						.queryUp("insert into hotel.rezerwacje (IDK_PESEL, ID_POKOJU, TYP, DATA_Z, DATA_W) VALUES("
								+ id
								+ ", "
								+ idRom
								+ ", "
								+ rset1.getInt(1)
								+ ", "
								+ "'"
								+ dateZ + "'" + ", " + "'" + dateW + "'" + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			try {
				sing
						.queryUp("insert into hotel.rezerwacje (IDF_KRS, ID_POKOJU, TYP, DATA_Z, DATA_W) VALUES("
								+ id
								+ ", "
								+ idRom
								+ ", "
								+ rset1.getInt(1)
								+ ", "
								+ "'"
								+ dateZ + "'" + ", " + "'" + dateW + "'" + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;

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
