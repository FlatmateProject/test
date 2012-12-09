package service.statictic.templates;

import dto.statictic.MonthSummaryGainData;
import exception.DAOException;
import service.dictionary.MONTH;
import service.statictic.DiagramElement;
import service.statictic.REPORT_KIND;
import service.statictic.StatisticReport;
import service.statictic.executors.ReportCreator;
import service.statictic.executors.ReportDetails;

import java.util.LinkedList;
import java.util.List;

public class FinanceMonthReportCreator extends ReportCreator {

	private MONTH monthFrom;
	
	private MONTH monthTo;
	
	private int year;
	
	@Override
	public void setup(ReportDetails reportDetails) {
		monthFrom = reportDetails.getMonthFrom();
		monthTo = reportDetails.getMonthTo();
		year = reportDetails.getYear();
		if (monthFrom.after(monthTo)) {
			swapMonths();
		}
	}

	@Override
	public StatisticReport createReport(ReportTemplateBuilder templateBuilder) throws DAOException {
		int i = 0;
		List<DiagramElement> plotPoints = new LinkedList<DiagramElement>();
		List<MonthSummaryGainData> monthSummaryGains = statisticDao.findMonthSummaryGains(monthFrom.id(), monthTo.id(), year);
		templateBuilder.createHeader(monthFrom, monthTo, year);
		for (MonthSummaryGainData financeMonthReportData : monthSummaryGains) {
			int month = financeMonthReportData.getMonth();
			double reservationSummaryGain = financeMonthReportData.getReservationSummaryGain();
			double serviceSummaryGain = financeMonthReportData.getServiceSummaryGain();
			double cantorSummaryGain = financeMonthReportData.getCantorSummaryGain();
			double summaryGain = reservationSummaryGain + serviceSummaryGain + cantorSummaryGain;
			plotPoints.add(new DiagramElement(reservationSummaryGain, serviceSummaryGain, cantorSummaryGain, summaryGain));
			templateBuilder.appendBodyBlock(MONTH.getMonthName(month), i, reservationSummaryGain, serviceSummaryGain, cantorSummaryGain, summaryGain);
			i++;
		}
		templateBuilder.createFoot(monthSummaryGains.size());
        return new StatisticReport(REPORT_KIND.FINANCE_MONTH, plotPoints, templateBuilder);
	}

	private void swapMonths() {
		MONTH tmp = monthFrom;
		monthFrom = monthTo;
		monthTo = tmp;
	}

}
