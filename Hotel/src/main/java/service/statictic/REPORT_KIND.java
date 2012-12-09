package service.statictic;

import service.statictic.executors.*;
import service.statictic.templates.*;

public enum REPORT_KIND {
	FINANCE_MONTH("Bilansu z miesięcy", new FinanceMonthReportTemplateBuilder(), new FinanceMonthReportCreator()), //
	FINANCE_YEAR("Bilansu z lat", new FinanceYearReportTemplateBuilder(), new FinanceYearReportCreator()), //
	HOTEL_ROOM_TYPES("Raportu z wykorzystania klas pokoi",new HotelRoomTypesReportTemplateBuilder(), new HotelRoomTypesReportCreator()), //
	HOTEL_ROOMS("Raportu z wykorzystania pokoi w klasie", new HotelRoomsReportTemplateBuilder(), new HotelRoomsInTypeReportCreator()), //
	HOTEL_SERVICE_TYPES("Raportu z wykorzystania typów usług", new HotelServiceTypesReportTemplateBuilder(), new HotelServiceTypesReportCreator()), //
	HOTEL_SERVICE("Raportu z wybranej uslugi", new HotelServiceReportTemplateBuilder(), new HotelServiceReportCreator())//
	;

	private final String desc;

	private ReportTemplateBuilder templateBuilder;

	private ReportCreator reportCreator;

	private REPORT_KIND(String desc) {
		this.desc = desc;
	}

	private REPORT_KIND(String desc, ReportTemplateBuilder template, ReportCreator creator) {
		this.desc = desc;
		this.templateBuilder = template;
		this.reportCreator = creator;
	}

	public ReportTemplateBuilder getReportTemplateBuilder() {
		return templateBuilder;
	}

	public ReportCreator getReportCreator() {
		return reportCreator;
	}
	
	@Override
	public String toString() {
		return desc;
	}

    public static REPORT_KIND[] hotel(){
        return new REPORT_KIND[]{
                HOTEL_ROOM_TYPES,
                HOTEL_ROOMS,
                HOTEL_SERVICE_TYPES,
                HOTEL_SERVICE
        };
    }

    public static REPORT_KIND[] finance(){
        return new REPORT_KIND[]{
                FINANCE_MONTH,
                FINANCE_YEAR
        };
    }
}