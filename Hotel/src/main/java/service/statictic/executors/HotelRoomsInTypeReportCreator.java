package service.statictic.executors;

import dto.statictic.RoomData;
import exception.DAOException;
import service.dictionary.MONTH;
import service.statictic.DiagramElement;
import service.statictic.REPORT_KIND;
import service.statictic.StatisticReport;
import service.statictic.templates.ReportTemplateBuilder;

import java.util.LinkedList;
import java.util.List;

public class HotelRoomsInTypeReportCreator extends ReportCreator {

	private MONTH month;
	
	private int year;
	
	private String roomType;
	
	@Override
	public void setup(ReportDetails reportDetails) {
		month = reportDetails.getMonth();
		year = reportDetails.getYear();
		roomType = reportDetails.getRoomType();
	}

	@Override
	public StatisticReport createReport(ReportTemplateBuilder templateBuilder) throws DAOException {
		int i = 0;
		List<DiagramElement> diagramElements = new LinkedList<DiagramElement>();
		List<RoomData> rooms = statisticDao.findRoomsByType(month.id(), year, roomType);
		templateBuilder.createHeader(roomType, month,year);
		for (RoomData room : rooms) {
			int occupationNumber = room.getOccupationNumber();
			float summaryGain = room.getSummaryGain();
			float unitGain = summaryGain / occupationNumber;
			diagramElements.add(new DiagramElement(summaryGain, unitGain));
			templateBuilder.appendBodyBlock(room.getRoomId(), i, summaryGain, occupationNumber, unitGain);
			i++;
		}
		templateBuilder.createFoot(rooms.size());
		return new StatisticReport(REPORT_KIND.HOTEL_ROOMS, diagramElements, templateBuilder);
	}

}
