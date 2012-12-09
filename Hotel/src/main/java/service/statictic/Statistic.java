package service.statictic;

import service.dictionary.MONTH;
import service.statictic.executors.ReportCreator;
import service.statictic.executors.ReportDetails;
import service.statictic.templates.ReportTemplateBuilder;
import dao.StatisticDao;

public class Statistic {

	private StatisticDao statisticDao;

	public Statistic(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}
	
	public StatisticReport finance(REPORT_KIND REPORTKind, MONTH monthFrom, MONTH monthTo, int yearFrom, int yearTo) {
		return createStatistic(REPORTKind, new ReportDetails(monthFrom, monthTo, yearFrom, yearTo));
	}

	public StatisticReport hotel(REPORT_KIND REPORTKind, MONTH month, int year, String classRoom, String serveTypeName){
		return createStatistic(REPORTKind, new ReportDetails(month, year, classRoom, serveTypeName));
	}
	
	private StatisticReport createStatistic(REPORT_KIND reportKind, ReportDetails reportDetails) {
		try {
			ReportCreator creator = reportKind.getReportCreator();
			ReportTemplateBuilder templateBuilder = reportKind.getReportTemplateBuilder();
			creator.setup(reportDetails);
			creator.injectStatisticDao(statisticDao);
			return creator.createReport(templateBuilder);
		} catch (Exception e) {
			e.printStackTrace();
			return null;	
		}
	}
}
