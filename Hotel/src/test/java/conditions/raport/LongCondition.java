package conditions.raport;

import org.fest.assertions.Condition;

public class LongCondition extends Condition<String> {

	private long value;
	
	public LongCondition(long value, String assertFailDescription) {
		this.value = value;
        as(assertFailDescription);
    }
	
	public static LongCondition bodyContainSummaryTime(long summaryTime) {
		return new LongCondition(summaryTime, "bodyContainSummaryTime");
	}

	@Override
	public boolean matches(String textReport) {
		return textReport.contains(Long.toString(value));
	}

}
