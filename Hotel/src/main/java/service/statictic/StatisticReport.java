package service.statictic;

import java.util.List;

import service.statictic.templates.ReportTemplateBuilder;


public class StatisticReport {

	private final double[][] array;
	
	private final String textResult;
	
	private final REPORT_KIND reportKind;
	
	public StatisticReport(REPORT_KIND reportKind, double[][] array, String resultText) {
		this.array = array;
		this.textResult = resultText;
		this.reportKind = reportKind;
	}
	
	public StatisticReport(REPORT_KIND reportKind, List<DiagramElement> elements, ReportTemplateBuilder templateBuilder) {
		this.array = createResultArray(elements);
		this.textResult = templateBuilder.build();
		this.reportKind = reportKind;
	}

	private double[][] createResultArray(List<DiagramElement> elements) {
		if(elements.size() == 0){
			return null;
		}
		double[][] array = new double[elements.size()][];
		int i = 0;
		for (DiagramElement point : elements) {
			array[i] = point.getBars();
			i++;
		}
		return array;
	}
	
	public double[][] getArrayResult() {
		return array;
	}

	public String getTextResult() {
		return textResult;
	}

	public REPORT_KIND getReportKind() {
		return reportKind;
	}
}
