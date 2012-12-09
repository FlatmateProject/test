package service.statictic.executors;

import service.dictionary.MONTH;

public class ReportDetails {

	private MONTH month;
	
	private int year; 
	
	private String roomType; 
	
	private String serviceTypeName;
	
	private MONTH monthTo;
	
	private int yearTo;
	
	public ReportDetails(MONTH month, int year, String classRoom, String serviceTypeName) {
		this.month = month;
		this.year = year;
		this.roomType = classRoom;
		this.serviceTypeName = serviceTypeName;
	}

	public ReportDetails(MONTH monthFrom, MONTH monthTo, int yearFrom, int yearTo)  {
		this.month = monthFrom;
		this.year = yearFrom;
		this.monthTo = monthTo;
		this.yearTo = yearTo;
	}
	
	public MONTH getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public MONTH getMonthFrom() {
		return month;
	}

	public int getYearFrom() {
		return year;
	}
	
	public String getRoomType() {
		return roomType;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public MONTH getMonthTo() {
		return monthTo;
	}

	public int getYearTo() {
		return yearTo;
	}
}
