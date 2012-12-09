package service.statictic.executors;

import java.sql.SQLException;

import exception.DAOException;
import service.statictic.StatisticReport;
import service.statictic.templates.ReportTemplateBuilder;
import dao.StatisticDao;

public abstract class ReportCreator {

	protected StatisticDao statisticDao;
	
	public abstract void setup(ReportDetails reportDetails);
	
	public abstract StatisticReport createReport(ReportTemplateBuilder templateBuilder) throws DAOException;

	public void injectStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}
}
