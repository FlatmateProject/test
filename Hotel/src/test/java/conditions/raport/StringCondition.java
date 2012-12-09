package conditions.raport;

import org.fest.assertions.Condition;

public class StringCondition extends Condition<String> {

	private String value;
	
	public StringCondition(String value, String assertFailDescription) {
		this.value = value;
        as(assertFailDescription);
    }
	
	public static StringCondition headerContainRoomType(String roomTypeName) {
		return new StringCondition(roomTypeName, "headerContainRoomType");
	}

	public static StringCondition headerContainServiceType(String serviceTypeName) {
		return new StringCondition(serviceTypeName, "headerContainRoomType");
	}
	
	public static StringCondition footerContainLegend() {
		String legend = "Legenda";
		return new StringCondition(legend, "headerContainRoomType");
	}
	
	@Override
	public boolean matches(String textReport) {
		return textReport.contains(value);
	}

}
