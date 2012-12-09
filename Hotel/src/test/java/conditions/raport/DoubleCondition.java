package conditions.raport;

import org.fest.assertions.Condition;

public class DoubleCondition extends Condition<String> {

	private double value;

	public DoubleCondition(double value, String assertFailDescription) {
		this.value = value;
        as(assertFailDescription);
	}

	public static DoubleCondition bodyContainSummaryGain(double summaryGain) {
		return new DoubleCondition(summaryGain, null);
	}

	public static DoubleCondition bodyContainReservationSummaryGain(double reservationSummaryGain) {
		return new DoubleCondition(reservationSummaryGain, "bodyContainSummaryGain");
	}

	public static DoubleCondition bodyContainServiceSummaryGain(double serviceSummaryGain) {
		return new DoubleCondition(serviceSummaryGain, "bodyContainServiceSummaryGain");
	}

	public static DoubleCondition bodyContainCantorSummaryGain(double cantorSummaryGain) {
		return new DoubleCondition(cantorSummaryGain, "bodyContainCantorSummaryGain");
	}
	public static DoubleCondition bodyContainHotelSummaryGain(double summaryGain) {
		return new DoubleCondition(summaryGain, "bodyContainHotelSummaryGain");
	}

	public static DoubleCondition bodyContainUnitGain(double unitGain) {
		return new DoubleCondition(unitGain, "bodyContainHotelSummaryGain");
	}

	@Override
	public boolean matches(String textReport) {
		return textReport.contains(Double.toString(value));
	}
}
