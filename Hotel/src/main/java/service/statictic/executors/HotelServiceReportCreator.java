package service.statictic.executors;

import dto.statictic.ServiceData;
import exception.DAOException;
import service.dictionary.MONTH;
import service.statictic.DiagramElement;
import service.statictic.REPORT_KIND;
import service.statictic.StatisticReport;
import service.statictic.templates.ReportTemplateBuilder;

import java.util.LinkedList;
import java.util.List;

public class HotelServiceReportCreator extends ReportCreator {
	
	private MONTH month;
	
	private int year;
	
	private String serviceTypeName;
	
	@Override
	public void setup(ReportDetails reportDetails) {
		month = reportDetails.getMonth();
		year = reportDetails.getYear();
		serviceTypeName = reportDetails.getServiceTypeName();
	}

	@Override
	public StatisticReport createReport(ReportTemplateBuilder templateBuilder) throws DAOException {
		int i = 0;
		List<DiagramElement> diagramElements = new LinkedList<DiagramElement>();
		List<ServiceData> services = statisticDao.findServiceByType(month.id(), year,serviceTypeName);
		templateBuilder.createHeader(serviceTypeName, month, year);
		for (ServiceData service : services) {
			String serveName = service.getServiceName();
			float summaryGain = service.getSummaryGain();
			int useNumber = statisticDao.countUseNumberForServiceName(serveName);
			float unitGain = summaryGain / useNumber;
			templateBuilder.appendBodyBlock(serveName, i, service.getSummaryTime(), summaryGain, useNumber, unitGain);
			diagramElements.add(new DiagramElement(summaryGain, unitGain));
			i++;
		}
		templateBuilder.createFoot(services.size());
		
		return new StatisticReport(REPORT_KIND.HOTEL_SERVICE, diagramElements, templateBuilder);
	}

}
