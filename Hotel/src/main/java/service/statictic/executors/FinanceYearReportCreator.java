package service.statictic.executors;

import dto.statictic.YearSummaryGainData;
import exception.DAOException;
import service.statictic.DiagramElement;
import service.statictic.REPORT_KIND;
import service.statictic.StatisticReport;
import service.statictic.templates.ReportTemplateBuilder;

import java.util.LinkedList;
import java.util.List;

public class FinanceYearReportCreator extends ReportCreator {

    private int yearFrom;

    private int yearTo;

    @Override
    public void setup(ReportDetails reportDetails) {
        yearFrom = reportDetails.getYearFrom();
        yearTo = reportDetails.getYearTo();
        if (yearFrom > yearTo) {
            swapYears();
        }
    }

    @Override
    public StatisticReport createReport(ReportTemplateBuilder templateBuilder) throws DAOException {
        int i = 0;
        List<DiagramElement> diagramElements = new LinkedList<DiagramElement>();
        List<YearSummaryGainData> yearSummaryGainData = statisticDao.findYearSummaryGains(yearFrom, yearTo);
        templateBuilder.createHeader(yearFrom, yearTo);
        for (YearSummaryGainData gainData : yearSummaryGainData) {
            double reservationSummaryGain = gainData.getReservationSummaryGain();
            double serviceSummaryGain = gainData.getServiceSummaryGain();
            double cantorSummaryGain = gainData.getCantorSummaryGain();
            double summaryGain = reservationSummaryGain + serviceSummaryGain + cantorSummaryGain;
            templateBuilder.appendBodyBlock(gainData.getYear(), i, reservationSummaryGain, serviceSummaryGain, cantorSummaryGain, summaryGain);
            diagramElements.add(new DiagramElement(reservationSummaryGain, serviceSummaryGain, cantorSummaryGain, summaryGain));
            i++;
        }
        templateBuilder.createFoot(yearSummaryGainData.size());
        return new StatisticReport(REPORT_KIND.FINANCE_YEAR, diagramElements, templateBuilder);
    }

    private void swapYears() {
        int tmp = yearFrom;
        yearFrom = yearTo;
        yearTo = tmp;
    }
}
