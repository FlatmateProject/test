package validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ValidationUtils 
{
	public static boolean isDate(String date) {
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

	public static boolean isNumber(String num)
	{
     try {
    	 Integer.valueOf(num);
    	 return true;
     } catch (NumberFormatException e) {
    	 return false;
     }
	}
	
	private static String trimInput(String input) {
		return input.replaceAll("\\D*", "");
	}
	
	public static boolean isPesel(String pesel) {
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

	public static boolean isKRS(String krs) {
		if (krs.length() != 10)
			return false;
		for (int i = 0; i < krs.length(); ++i) {
			if (krs.charAt(i) <= '0' || krs.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}
	
}
