package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import dao.Singleton;

public class EmployeeManager {

	private static final Logger log = Logger.getLogger(EmployeeManager.class);

	private Singleton db;
	private ResultSet result;
	private ResultSet resEmp;
	private double time = 0;
	private int j = 0;
	private int shift = 0;
	private int count = 0;
	private int workHour[];
	private int days;
	private final int[] dayInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };
	private boolean exist = false;
	private String matrix[][];
	private String array[];
	private String resultText;
	private String stmt;

	private Calendar calendar = GregorianCalendar.getInstance();

	public EmployeeManager() {
		try {
			db = Singleton.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPaysRaport(int month) {
		String s = "";
		try {
			resultText = "";
			stmt = "";
			db = Singleton.getInstance();
			result = db
					.query("SELECT p.idp_pesel, p.imie, p.nazwisko, count(g.nadgodziny)*8 , "
							+ "sum(s.podstawa)*8 podstawa, sum(s.premia)*8 premia FROM grafik g "
							+ "JOIN pracownicy p ON g.idp_pesel=p.idp_pesel JOIN stanowiska s "
							+ "ON p.id_stanowiska=s.id_stanowiska WHERE p.id_stanowiska=1 "
							+ "AND MONTH(data)="
							+ month
							+ " GROUP BY p.idp_pesel,  g.nadgodziny");

			log.info("SELECT p.idp_pesel, p.imie, p.nazwisko, count(g.nadgodziny)*8 , "
					+ "sum(s.podstawa)*8 podstawa, sum(s.premia)*8 premia FROM grafik g "
					+ "JOIN pracownicy p ON g.idp_pesel=p.idp_pesel JOIN stanowiska s "
					+ "ON p.id_stanowiska=s.id_stanowiska WHERE s.nazwa='sprz�taczka' "
					+ "AND MONTH(data)="
					+ month
					+ " GROUP BY p.idp_pesel,  g.nadgodziny");
			if (result != null) {
				while (result.next()) {
					s = result.getString(1);
					if (s.equals(stmt)) {
						resultText += "  godzin dodatkowych: "
								+ result.getString(4);
						resultText += " pensja dodatkowa: "
								+ result.getString(6) + "\n";
					} else {
						resultText += "\n" + result.getString(2) + " "
								+ result.getString(3) + " " + s + "\n";
						resultText += "  godzin podstawowych: "
								+ result.getString(4);
						resultText += " pensja podstawowa: "
								+ result.getString(5) + "\n";
					}
					stmt = result.getString(1);
				}
				log.info("siema: " + resultText);
				return resultText;
			} else {
				log.info("Brak danych o zarobkach z danego miesi�ca");
				return "Brak danych o zarobkach z danego miesi�ca";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Wyst�pi� problem z baza danych";
		}
	}

	public String[] getDaySchedule(String date) {
		int extra = 0;
		array = null;
		try {
			result = db
					.query("SELECT p.imie, p.nazwisko, g.zmiana, g.id_poko, g.id_pokd FROM grafik g "
							+ "LEFT JOIN  pracownicy p ON g.idp_pesel=p.idp_pesel WHERE g.data='"
							+ date + "'");
			log.info("SELECT p.imie, p.nazwisko, g.zmiana, g.id_poko, g.id_pokd FROM grafik g LEFT JOIN  pracownicy p ON g.idp_pesel=p.idp_pesel WHERE g.data='"
					+ date + "'");
			if (result != null) {
				result.last();
				count = result.getRow();
				result.beforeFirst();
				array = new String[count + 9];
				j = 0;
				shift = 1;
				// array[j]="Zmiana 1";
				// j++;
				while (result.next()) {
					if (Integer.valueOf(result.getString(3)) == shift) {
						array[j++] = " ";
						array[j] = "Zmiana " + shift;
						log.info(array[j]);
						shift++;
						j++;
					}
					array[j] = result.getString(1);
					if (array[j] != null)
						array[j] += " " + result.getString(2) + " pokoje od ";
					else {
						array[j] = "[" + extra + " nadgodziny] pokoje od ";
						extra++;
					}
					array[j] += result.getString(4) + " do "
							+ result.getString(5);
					log.info(array[j]);
					j++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	public String[] updateSchedule(int i, String text, String date,
			String pesel, String id) {
		try {
			db.query("UPDATE grafik SET idp_pesel=" + pesel + " WHERE data='"
					+ date + "' AND idp_pesel=" + id);
			log.info("UPDATE grafik SET idp_pesel=" + pesel + " WHERE data='"
					+ date + "' AND idp_pesel=" + id);
			array[i] = text
					+ array[i].substring(array[i].lastIndexOf("]") + 1,
							array[i].length());
			log.info(text
					+ array[i].substring(array[i].lastIndexOf("]") + 1,
							array[i].length()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	// /////////////////////////---------------b
	public boolean createSchedule(int month) {
		try {
			result = db.query("SELECT count(*) from grafik WHERE MONTH(data)="
					+ month);
			if (result.next() && result.getInt(1) > 0)
				exist = true;
			else
				exist = false;
			log.info("SELECT count(*) from grafik WHERE MONTH(data)=" + month);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!exist) {
			newSchedule(month);
			return true;
		} else
			return false;
	}

	// //////////////////----------------------------------e

	private String newSchedule(int month) {
		boolean cont = true;
		boolean isNext = false;
		int rf = 1, rt = 1, extra = 0;
		String ym = calendar.get(Calendar.YEAR) + "/" + month + "/";
		log.info("ym: " + ym);
		try {
			stmt = "INSERT INTO grafik(idp_pesel,zmiana,data,nadgodziny,id_poko,id_pokd) VALUE";
			result = db
					.query("SELECT p.id_pokoju, k.czassp FROM pokoje p JOIN klasy k ON "
							+ "p.id_klasy=k.id_klasy ORDER BY id_pokoju");
			log.info("SELECT p.id_pokoju, k.czassp FROM pokoje p JOIN klasy k ON p.id_klasy=k.id_klasy");
			log.info("SELECT imie, nazwisko FROM pracownicy WHERE id_stanowiska=1");
			resEmp = db
					.query("SELECT imie, nazwisko, idp_pesel FROM pracownicy WHERE id_stanowiska=1");
			// resultText="Grafik \n";
			if (result != null && resEmp != null) {
				exist = true;
				resEmp.last();
				workHour = new int[resEmp.getRow()];
				resEmp.beforeFirst();
				for (j = 0; j < workHour.length; j++)
					workHour[j] = 0;

				days = dayInMonth[month - 1];
				if (calendar.get(Calendar.YEAR) % 4 == 0)
					days++;
				log.info("days: " + days);

				j = 0;
				for (int i = 0; i < days; i++) {
					shift = 1;
					// resultText+=ym+(i+1)+"\n";
					while (shift <= 3) {
						// resultText+="zmiana "+shift+"\n";
						cont = true;
						while (cont) {
							isNext = resEmp.next();
							// if(isNext)
							// resultText+=" "+resEmp.getString(1)+" "+resEmp.getString(2)+" pokoje: ";//
							// else resultText+=" [nadgodziny] pokoje: "; //
							time = 0;
							rf = rt;
							while ((cont = result.next())
									&& (time += result.getDouble(2)) < 8) {
								// resultText+=result.getString(1)+" ";
								rt = result.getInt(1);
							}
							if (isNext) {
								db.query(stmt + "(" + resEmp.getString(3) + ","
										+ shift + ",'" + ym + (i + 1) + "',0,"
										+ rf + "," + rt + ")");
								workHour[j++] += 8;
							} else {
								db.query(stmt + "(" + extra + "," + shift
										+ ",'" + ym + (i + 1) + "',1," + rf
										+ "," + rt + ")");
								extra++;
							}

							if (cont)
								rt = result.getInt(1);
							// resultText+="  ["+String.format("%.2f",time)+"]\n";
							result.previous();
						}
						shift++;
						result.beforeFirst();
						rf = rt = 1;
						if (resEmp.isLast() || resEmp.isAfterLast()) {
							resEmp.beforeFirst();
							j = 0;
						}
					}
					// resultText+=" \n";
					extra = 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultText;
	}

	/*

  */
	public boolean addEmployee(String p, String i, String n, String h,
			String w, String m, String u, String l, String s) {
		try {
			count = 0;
			result = db
					.query("SELECT id_stanowiska FROM stanowiska WHERE nazwa='"
							+ s + "'");
			result.next();
			j = result.getInt(1);
			log.info("INSERT INTO pracownicy VALUE(" + p + ",'" + i + "','" + n
					+ "',SHA1('" + h + "'),'" + w + "','" + m + "','" + u
					+ "'," + l + "," + j + ")");

			if (db.queryUp("INSERT INTO pracownicy VALUE(" + p + ",'" + i
					+ "','" + n + "',SHA1('" + h + "'),'" + w + "','" + m
					+ "','" + u + "'," + l + "," + j + ")"))
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String[][] findEmployee(String p, String i, String n, String s) {
		matrix = null;
		try {
			count = 0;
			log.info("p: " + p + " i: " + i + " n: " + n + " s: " + s);
			String stmt = "";
			j = 0;
			if (!p.equals("")) {
				stmt += "p.idp_pesel=" + p;
				j++;
			}
			if (!i.equals("")) {
				if (j > 0)
					stmt += " AND ";
				stmt += "p.imie='" + i + "'";
				j++;
			}
			if (!n.equals("")) {
				if (j > 0)
					stmt += " AND ";
				stmt += "p.nazwisko='" + n + "'";
				j++;
			}
			if (!s.equals("")) {
				if (j > 0)
					stmt += " AND ";
				stmt += "s.nazwa='" + s + "'";
			}
			if (stmt != "")
				stmt = "SELECT p.idp_pesel, p.imie, p.nazwisko, s.nazwa FROM pracownicy p "
						+ "JOIN stanowiska s ON p.id_stanowiska=s.id_stanowiska WHERE "
						+ stmt;
			else
				stmt = "SELECT p.idp_pesel, p.imie, p.nazwisko, s.nazwa FROM pracownicy p "
						+ "JOIN stanowiska s ON p.id_stanowiska=s.id_stanowiska";
			log.info(stmt);
			result = db.query(stmt);
			if (result != null) {
				result.last();
				count = result.getRow();
				result.beforeFirst();
				matrix = new String[count][4];
				j = 0;
				while (result.next()) {
					matrix[j][0] = result.getString(1);
					matrix[j][1] = result.getString(2);
					matrix[j][2] = result.getString(3);
					matrix[j][3] = result.getString(4);
					j++;
				}
			}
			if (j == 0) {
				matrix = new String[1][4];
				matrix[0][0] = "brak danych";
				matrix[0][1] = "";
				matrix[0][2] = "";
				matrix[0][3] = "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("lipa z insertemm");
		}
		return matrix;
	}

	public boolean delSchedule(int month) {
		boolean ret = false;
		try {
			ret = db.queryUp("DELETE FROM grafik WHERE YEAR(data)="
					+ calendar.get(Calendar.YEAR) + " AND MONTH(data)=" + month);
			log.info("DELETE FROM grafik WHERE YEAR(data)="
					+ calendar.get(Calendar.YEAR) + " AND MONTH(data)=" + month);
			log.info("ret: " + ret);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delEmployee(String p) {
		try {
			log.info("DELETE FROM pracownicy WHERE idp_pesel=" + p);
			return db.queryUp("DELETE FROM pracownicy WHERE idp_pesel=" + p);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getRowCount() {
		return count;
	}
}
