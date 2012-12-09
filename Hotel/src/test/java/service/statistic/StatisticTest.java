package service.statistic;

import dao.StatisticDaoImpl;
import org.apache.log4j.Logger;
import org.fest.assertions.Condition;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.dictionary.MONTH;
import service.statictic.REPORT_KIND;
import service.statictic.Statistic;
import service.statictic.StatisticReport;

import static conditions.raport.IntegerCondition.headerContainYear;
import static conditions.raport.MonthCondition.headerContainMonth;
import static conditions.raport.PeriodOfMonthsCondition.headerContainPeriodOfMonths;
import static conditions.raport.PeriodOfYearsCondition.headerContainPeriodOfYears;
import static conditions.raport.StringCondition.footerContainLegend;
import static org.fest.assertions.Assertions.assertThat;

public class StatisticTest {

	private static final Logger log = Logger.getLogger(StatisticTest.class);

	@Test(dataProvider = "prepareCasesForHotelReport")
	public void shouldCreateEmptyHotelReport(REPORT_KIND reportKind, int year, MONTH month, String serve, String roomType) {
		// given
		Statistic statistic = new Statistic(new StatisticDaoImpl());

		// when
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serve);

		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.isNot(footerContainLegend());
		log.info(textReport);

		double[][] arrayResult = report.getArrayResult();
		assertThat(arrayResult).isNull();
	}

	@DataProvider
	public static Object[][] prepareCasesForHotelReport() {
		return new Object[][] {//
				{ REPORT_KIND.HOTEL_ROOM_TYPES, 2012, MONTH.September, null, null },//
				{ REPORT_KIND.HOTEL_ROOMS, 2012, MONTH.September, null,	"pokój jednosobowy" },//
				{ REPORT_KIND.HOTEL_SERVICE_TYPES, 2012, MONTH.September, null,	"pokój jednosobowy" },//
				{ REPORT_KIND.HOTEL_SERVICE, 2012, MONTH.September,	"rekreacja", null },//
		};
	}

	@Test(dataProvider = "prepareCasesForFinanceMonthReport")
	public void shouldCreateEmptyFinanceMonthReport(MONTH monthFrom, MONTH monthTo, Condition<String> monthCondition) {
		// given
		Statistic statistic = new Statistic(new StatisticDaoImpl());
		REPORT_KIND reportKind = REPORT_KIND.FINANCE_MONTH;
		int year = 2012;
		
		// when
		StatisticReport report = statistic.finance(reportKind, monthFrom, monthTo, year, 0);

		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(monthCondition)//
				.is(headerContainYear(year))//
				.isNot(footerContainLegend());
		log.info(textReport);

		double[][] arrayResult = report.getArrayResult();
		assertThat(arrayResult).isNull();
	}

	@DataProvider
	public static Object[][] prepareCasesForFinanceMonthReport() {
		return new Object[][] {//
				{ MONTH.September, MONTH.December, headerContainPeriodOfMonths(MONTH.September, MONTH.December) },//
				{ MONTH.September, MONTH.September, headerContainMonth(MONTH.September) },//
				{ MONTH.September, MONTH.May, headerContainPeriodOfMonths(MONTH.May, MONTH.September) },//
		};
	}
	
	@Test(dataProvider = "prepareCasesForFinanceYearReport")
	public void shouldCreateEmptyFinanceYearReport(int yearFrom, int yearTo, Condition<String> yearCondition) {
		// given
		Statistic statistic = new Statistic(new StatisticDaoImpl());
		REPORT_KIND reportKind = REPORT_KIND.FINANCE_YEAR;
		
		// when
		StatisticReport report = statistic.finance(reportKind, null, null, yearFrom, yearTo);

		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(yearCondition)//
				.isNot(footerContainLegend());
		log.info(textReport);

		double[][] arrayResult = report.getArrayResult();
		assertThat(arrayResult).isNull();
	}

    @DataProvider
    public static Object[][] prepareCasesForFinanceYearReport() {
        return new Object[][] {//
                { 2012, 2013 , headerContainPeriodOfYears(2012, 2013)},//
                { 2012, 2012 , headerContainYear(2012)},//
                { 2012, 2011 , headerContainPeriodOfYears(2011, 2012)},//
        };
    }
}
