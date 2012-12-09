package conditions.raport;

import org.fest.assertions.Condition;
import service.dictionary.MONTH;

public class MonthCondition extends Condition<String> {

	private MONTH month;
	
	public MonthCondition(MONTH month, String assertFailDescription) {
		this.month = month;
        as(assertFailDescription);
    }
	
	public static MonthCondition headerContainMonth(MONTH month) {
		return new MonthCondition(month, "headerContainMonth");
	}

	@Override
	public boolean matches(String textReport) {
		return textReport.contains(month.getDesc());
	}

}
