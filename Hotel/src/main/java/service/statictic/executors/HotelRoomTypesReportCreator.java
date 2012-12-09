package service.statictic.executors;

import dto.statictic.RoomTypesData;
import exception.DAOException;
import service.dictionary.MONTH;
import service.statictic.DiagramElement;
import service.statictic.REPORT_KIND;
import service.statictic.StatisticReport;
import service.statictic.templates.ReportTemplateBuilder;

import java.util.LinkedList;
import java.util.List;

public class HotelRoomTypesReportCreator extends ReportCreator {
	
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
		List<RoomTypesData> roomTypes = statisticDao.findRoomTypes(month.id(), year);//
		templateBuilder.createHeader(month, year);
		for (RoomTypesData roomType : roomTypes) {
			int numberOccupiedRooms = roomType.getNumberOccupiedRooms();
			float summaryGain = roomType.getSummaryGain();
			double unitGain = summaryGain / numberOccupiedRooms;
			diagramElements.add(new DiagramElement(summaryGain, unitGain));
			templateBuilder.appendBodyBlock(roomType.getRoomTypeName(), i, summaryGain, numberOccupiedRooms, unitGain);
			i++;
		}
		templateBuilder.createFoot(roomTypes.size());
		return new StatisticReport(REPORT_KIND.HOTEL_ROOM_TYPES, diagramElements, templateBuilder);
	}
}
