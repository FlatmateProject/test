package service.statistic;

import assertions.DiagramBarsAssert;
import dao.StatisticDao;
import dto.statictic.*;
import exception.DAOException;
import org.apache.log4j.Logger;
import org.fest.assertions.Condition;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.dictionary.MONTH;
import service.statictic.REPORT_KIND;
import service.statictic.Statistic;
import service.statictic.StatisticReport;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static conditions.raport.DoubleCondition.*;
import static conditions.raport.IntegerCondition.*;
import static conditions.raport.LongCondition.bodyContainSummaryTime;
import static conditions.raport.MonthCondition.headerContainMonth;
import static conditions.raport.PeriodOfMonthsCondition.headerContainPeriodOfMonths;
import static conditions.raport.PeriodOfYearsCondition.headerContainPeriodOfYears;
import static conditions.raport.StringCondition.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatisticMockitoTest {
	
	static final Logger log = Logger.getLogger(StatisticMockitoTest.class);
	
	int year = 2012;
	
	MONTH month = MONTH.September;
	
	String roomType = "jednoosobowy";

	String serviceTypeName = "wynajem";

	int yearTo = 2012;
	
	@Test
	public void shouldCreateEmptyRoomTypesReport() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_ROOM_TYPES;
		List<RoomTypesData> inputData = Collections.emptyList();
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findRoomTypes(month.id(), year)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);

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
		
		double[][] array = report.getArrayResult();
		assertThat(array).isNull();
	}
	
	@Test
	public void shouldCreateRoomTypesReport() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_ROOM_TYPES;
		float summaryGain = 100.0f;
		int numberOccupiedRooms = 2;
		float unitGain = summaryGain / numberOccupiedRooms;
		int expectedNumberOfBars = 2;
		
		RoomTypesData row = mock(RoomTypesData.class);
		when(row.getRoomTypeName()).thenReturn(roomType);
		when(row.getNumberOccupiedRooms()).thenReturn(numberOccupiedRooms);
		when(row.getSummaryGain()).thenReturn(summaryGain);
		
		List<RoomTypesData> inputData = Arrays.asList(row);
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findRoomTypes(month.id(), year)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);
		
		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.is(footerContainLegend())//
				.is(bodyContainSummaryGain(summaryGain))//
				.is(bodyContainNumberOccupiedRooms(numberOccupiedRooms))//
				.is(bodyContainUnitGain(unitGain));
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array)
                .isNotNull()
                .hasSize(1);
        DiagramBarsAssert.assertThat(array[0])
                .isNotNull()
                .hasSize(expectedNumberOfBars)
                .isSummaryGainEqualTo(summaryGain)
                .isUnitGainEqualTo(unitGain);
	}

	@Test
	public void shouldCreateEmptyRoomsInTypeReport() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_ROOMS;
		String roomType = "jednoosobowy";
		List<RoomData> inputData = Collections.emptyList();
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findRoomsByType(month.id(), year, roomType)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);

		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.is(headerContainRoomType(roomType))//
				.isNot(footerContainLegend());
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array).isNull();
	}
	
	@Test
	public void shouldCreateRoomsInTypeReport() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_ROOMS;
		String roomType = "jednoosobowy";
		float summaryGain = 100.0f;
		int occupationNumber = 2;
		float unitGain = summaryGain / occupationNumber;
		int expectedNumberOfBars = 2;
		long roomId = 1;

		RoomData row = mock(RoomData.class);
		when(row.getRoomId()).thenReturn(roomId);
		when(row.getOccupationNumber()).thenReturn(occupationNumber);
		when(row.getSummaryGain()).thenReturn(summaryGain);
		
		List<RoomData> inputData = Arrays.asList(row);
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findRoomsByType(month.id(), year, roomType)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);
		
		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.is(footerContainLegend())//
				.is(bodyContainSummaryGain(summaryGain))//
				.is(bodyContainOccupationNumber(occupationNumber))//
				.is(bodyContainUnitGain(unitGain));
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array)
                .isNotNull()
                .hasSize(1);
		DiagramBarsAssert.assertThat(array[0])
                .isNotNull()
                .hasSize(expectedNumberOfBars)
                .isSummaryGainEqualTo(summaryGain)
                .isUnitGainEqualTo(unitGain);
	}
	
	@Test
	public void shouldCreateEmptyServiceTypesReport() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_SERVICE_TYPES;
		List<ServiceTypeData> inputData = Collections.emptyList();
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findServiceTypes(month.id(), year)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);

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
		
		double[][] array = report.getArrayResult();
		assertThat(array).isNull();
	}
	
	@Test
	public void shouldCreateServiceTypesReport() throws Exception {
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_SERVICE_TYPES;
		float summaryGain = 100.0f;
		int useNumber = 2;
		float unitGain = summaryGain / useNumber;
		int expectedNumberOfBars = 2;
		long summaryTime = 10;

		ServiceTypeData row = mock(ServiceTypeData.class);
		when(row.getSummaryTime()).thenReturn(summaryTime);
		when(row.getTypeName()).thenReturn(serviceTypeName);
		when(row.getSummaryGain()).thenReturn(summaryGain);
		
		List<ServiceTypeData> inputData = Arrays.asList(row);
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findServiceTypes(month.id(), year)).thenReturn(inputData);
		when(statisticDao.countUseNumberForServiceType(serviceTypeName)).thenReturn(useNumber);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);
		
		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.is(footerContainLegend())//
				.is(bodyContainSummaryTime(summaryTime))//
				.is(bodyContainSummaryGain(summaryGain))//
				.is(bodyContainUseNumber(useNumber))//
				.is(bodyContainUnitGain(unitGain));
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array)
                .isNotNull()
                .hasSize(1);
		DiagramBarsAssert.assertThat(array[0])
                .isNotNull()
                .hasSize(expectedNumberOfBars)
                .isSummaryGainEqualTo(summaryGain)
                .isUnitGainEqualTo(unitGain);
	}
	
	@Test             
	public void shouldCreateEmptyServiceReport() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_SERVICE;
		String serviceTypeName = "wynajem";
		List<ServiceData> inputData = Collections.emptyList();
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findServiceByType(month.id(), year, serviceTypeName)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);

		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.is(headerContainServiceType(serviceTypeName))//
				.isNot(footerContainLegend());
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array).isNull();
	}
	
	@Test
	public void shouldCreateServiceReport() throws DAOException {
		// given
		REPORT_KIND reportKind = REPORT_KIND.HOTEL_SERVICE;
		String serviceTypeName = "wynajem";
		float summaryGain = 100.0f;
		int useNumber = 2;
		float unitGain = summaryGain / useNumber;
		String serviceName = "jednosobosy";
		long summaryTime = 10;
		int expectedNumberOfBars = 2;
		
		ServiceData row = mock(ServiceData.class);
		when(row.getSummaryTime()).thenReturn(summaryTime);
		when(row.getServiceName()).thenReturn(serviceName);
		when(row.getSummaryGain()).thenReturn(summaryGain);
		
		List<ServiceData> inputData = Arrays.asList(row);
		
		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findServiceByType(month.id(), year, serviceTypeName)).thenReturn(inputData);
		when(statisticDao.countUseNumberForServiceName(serviceName)).thenReturn(useNumber);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.hotel(reportKind, month, year, roomType, serviceTypeName);
		
		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport)//
				.isNotNull()//
				.is(headerContainMonth(month))//
				.is(headerContainYear(year))//
				.is(footerContainLegend())//
				.is(bodyContainSummaryTime(summaryTime))//
				.is(bodyContainSummaryGain(summaryGain))//
				.is(bodyContainUseNumber(useNumber))//
				.is(bodyContainUnitGain(unitGain));
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array).isNotNull().hasSize(1);
		assertThat(array[0])
                .isNotNull()
                .hasSize(expectedNumberOfBars);
		DiagramBarsAssert.assertThat(array[0])
                .isNotNull()
                .hasSize(expectedNumberOfBars)
                .isSummaryGainEqualTo(summaryGain)
                .isUnitGainEqualTo(unitGain);
	}
	
	@Test(dataProvider = "prepareCasesForFinanceMonthReport")
	public void shouldCreateEmptyFinanceMonthReportWithCorrectTitle(MONTH monthFrom, MONTH monthTo, Condition<String> monthCondition) throws DAOException {
		// given
		REPORT_KIND reportKind = REPORT_KIND.FINANCE_MONTH;

		List<MonthSummaryGainData> inputData = Collections.emptyList();

		StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findMonthSummaryGains(monthFrom.id(), monthTo.id(), year)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.finance(reportKind, monthFrom, monthTo, year, yearTo);
		
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
		
		double[][] array = report.getArrayResult();
		assertThat(array).isNull();
	}
	
	@DataProvider
	public static Object[][] prepareCasesForFinanceMonthReport() {
		return new Object[][] {//
				{ MONTH.September, MONTH.December,  headerContainPeriodOfMonths(MONTH.September, MONTH.December) },//
				{ MONTH.September, MONTH.September, headerContainMonth(MONTH.September) },//
				{ MONTH.September, MONTH.May,       headerContainPeriodOfMonths(MONTH.May, MONTH.September) },//
		};
	}
	
	@Test
	public void shouldCreateFinanceMonthReportWithCorrectTitle() throws DAOException{
		// given
		REPORT_KIND reportKind = REPORT_KIND.FINANCE_MONTH;
		MONTH monthFrom = MONTH.September;
		MONTH monthTo = MONTH.May;
		int expectedNumberOfBars = 4;
		double reservationSummaryGain = 100.0;
		double serviceSummaryGain = 200.0;
		double cantorSummaryGain = 300.0;
		double summaryGain = reservationSummaryGain + serviceSummaryGain + cantorSummaryGain;
		
		MonthSummaryGainData row = mock(MonthSummaryGainData.class);
		when(row.getMonth()).thenReturn(monthTo.id());
		when(row.getReservationSummaryGain()).thenReturn(reservationSummaryGain);
		when(row.getServiceSummaryGain()).thenReturn(serviceSummaryGain);
		when(row.getCantorSummaryGain()).thenReturn(cantorSummaryGain);
		
		List<MonthSummaryGainData> inputData = Arrays.asList(row);

        StatisticDao statisticDao = mock(StatisticDao.class);
		when(statisticDao.findMonthSummaryGains(monthTo.id(), monthFrom.id(), year)).thenReturn(inputData);
		
		// when
		Statistic statistic = new Statistic(statisticDao);
		StatisticReport report = statistic.finance(reportKind, monthFrom, monthTo, year, yearTo);
		
		// then
		assertThat(report).isNotNull();
		assertThat(report.getReportKind()).isEqualTo(reportKind);
		String textReport = report.getTextResult();
		assertThat(textReport).isNotNull()//
				.is(headerContainPeriodOfMonths(monthTo, monthFrom))//
				.is(headerContainYear(year))//
				.is(footerContainLegend())//
				.is(bodyContainReservationSummaryGain(reservationSummaryGain))//
				.is(bodyContainServiceSummaryGain(serviceSummaryGain))//
				.is(bodyContainCantorSummaryGain(cantorSummaryGain))//
				.is(bodyContainHotelSummaryGain(summaryGain));
		log.info(textReport);
		
		double[][] array = report.getArrayResult();
		assertThat(array)
                .isNotNull()
                .hasSize(1);
        DiagramBarsAssert.assertThat(array[0])
                .isNotNull()
                .hasSize(expectedNumberOfBars)
                .isReservationSummaryGainEqualTo(reservationSummaryGain)
				.isServiceSummaryGainEqualTo(serviceSummaryGain)//
				.isCantorSummaryGainEqualTo(cantorSummaryGain)//
				.isHotelSummaryGainEqualTo(summaryGain);
	}

    @Test(dataProvider = "prepareCasesForFinanceYearReport")
    public void shouldCreateEmptyFinanceYearReportWithCorrectTitle(int yearFrom, int yearTo, Condition<String> yearCondition) throws DAOException {
        // given
        REPORT_KIND reportKind = REPORT_KIND.FINANCE_YEAR;

        List<YearSummaryGainData> inputData = Collections.emptyList();

        StatisticDao statisticDao = mock(StatisticDao.class);
        when(statisticDao.findYearSummaryGains(yearFrom, yearTo)).thenReturn(inputData);

        // when
        Statistic statistic = new Statistic(statisticDao);
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

        double[][] array = report.getArrayResult();
        assertThat(array).isNull();
    }

    @DataProvider
    public static Object[][] prepareCasesForFinanceYearReport() {
        return new Object[][] {//
                { 2012, 2013 , headerContainPeriodOfYears(2012, 2013)},//
                { 2012, 2012 , headerContainYear(2012)},//
                { 2012, 2011 , headerContainPeriodOfYears(2011, 2012)},//
        };
    }

    @Test
    public void shouldCreateFinanceYearReportWithCorrectTitle() throws DAOException {
        // given
        REPORT_KIND reportKind = REPORT_KIND.FINANCE_YEAR;
        int yearFrom = 2013;
        int yearTo = 2012;
        int expectedNumberOfBars = 4;
        double reservationSummaryGain = 100.0;
        double serviceSummaryGain = 200.0;
        double cantorSummaryGain = 300.0;
        double summaryGain = reservationSummaryGain + serviceSummaryGain + cantorSummaryGain;

        YearSummaryGainData row = mock(YearSummaryGainData.class);
        when(row.getYear()).thenReturn(yearTo);
        when(row.getReservationSummaryGain()).thenReturn(reservationSummaryGain);
        when(row.getServiceSummaryGain()).thenReturn(serviceSummaryGain);
        when(row.getCantorSummaryGain()).thenReturn(cantorSummaryGain);

        List<YearSummaryGainData> inputData = Arrays.asList(row);

        StatisticDao statisticDao = mock(StatisticDao.class);
        when(statisticDao.findYearSummaryGains(yearTo, yearFrom)).thenReturn(inputData);

        // when
        Statistic statistic = new Statistic(statisticDao);
        StatisticReport report = statistic.finance(reportKind, null, null, yearFrom, yearTo);

        // then
        assertThat(report).isNotNull();
        assertThat(report.getReportKind()).isEqualTo(reportKind);
        String textReport = report.getTextResult();
        assertThat(textReport).isNotNull()//
                .is(headerContainPeriodOfYears(yearTo, yearFrom))//
                .is(footerContainLegend())//
                .is(bodyContainReservationSummaryGain(reservationSummaryGain))//
                .is(bodyContainServiceSummaryGain(serviceSummaryGain))//
                .is(bodyContainCantorSummaryGain(cantorSummaryGain))//
                .is(bodyContainHotelSummaryGain(summaryGain));
        log.info(textReport);

        double[][] array = report.getArrayResult();
        assertThat(array)
                .isNotNull()
                .hasSize(1);
        DiagramBarsAssert.assertThat(array[0])
                .isNotNull().
                hasSize(expectedNumberOfBars)
                .isReservationSummaryGainEqualTo(reservationSummaryGain)
                .isServiceSummaryGainEqualTo(serviceSummaryGain)//
                .isCantorSummaryGainEqualTo(cantorSummaryGain)//
                .isHotelSummaryGainEqualTo(summaryGain);
    }
}
