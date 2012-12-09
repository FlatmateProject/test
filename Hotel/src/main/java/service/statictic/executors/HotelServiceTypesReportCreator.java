package service.statictic.executors;

import dto.statictic.ServiceTypeData;
import exception.DAOException;
import service.dictionary.MONTH;
import service.statictic.DiagramElement;
import service.statictic.REPORT_KIND;
import service.statictic.StatisticReport;
import service.statictic.templates.ReportTemplateBuilder;

import java.util.LinkedList;
import java.util.List;

public class HotelServiceTypesReportCreator extends ReportCreator {

	private MONTH month;
	
	private int year;
	
	@Override
	public void setup(ReportDetails reportDetails) {
		month = reportDetails.getMonth();
		year = reportDetails.getYear();
	}

	@Override
	public StatisticReport createReport(ReportTemplateBuilder templateBuilder) throws DAOException {
		int i = 0;
		List<DiagramElement> diagramElements = new LinkedList<DiagramElement>();
		
		List<ServiceTypeData> serviceTypes = statisticDao.findServiceTypes(month.id(), year);
		templateBuilder.createHeader(month, year);
		for (ServiceTypeData serviceType : serviceTypes) {
			String typeName = serviceType.getTypeName();
			float summaryGain = serviceType.getSummaryGain();
			int useNumber = statisticDao.countUseNumberForServiceType(typeName);
			float unitGain = summaryGain / useNumber;
			templateBuilder.appendBodyBlock(typeName, i, serviceType.getSummaryTime(), summaryGain, useNumber, unitGain);
			diagramElements.add(new DiagramElement(summaryGain, unitGain));
			i++;
		}
		templateBuilder.createFoot(serviceTypes.size());
		return new StatisticReport(REPORT_KIND.HOTEL_SERVICE_TYPES, diagramElements, templateBuilder);
	}

}
