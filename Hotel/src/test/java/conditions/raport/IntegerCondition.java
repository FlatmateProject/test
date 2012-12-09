package conditions.raport;

import org.fest.assertions.Condition;

public class IntegerCondition extends Condition<String> {

    private int value;

    public IntegerCondition(int value, String assertFailDescription) {
        this.value = value;
        as(assertFailDescription);
    }

    public static IntegerCondition bodyContainUseNumber(int useNumber) {
        return new IntegerCondition(useNumber, "bodyContainUseNumber");
    }

    public static IntegerCondition headerContainYear(int year) {
        return new IntegerCondition(year, "headerContainYear");
    }

    public static IntegerCondition bodyContainNumberOccupiedRooms(int numberOccupiedRooms) {
        return new IntegerCondition(numberOccupiedRooms, "bodyContainNumberOccupiedRooms");
    }

    public static IntegerCondition bodyContainOccupationNumber(int occupationNumber) {
        return new IntegerCondition(occupationNumber, "bodyContainOccupationNumber");
    }

    @Override
    public boolean matches(String textReport) {
        return textReport.contains(Integer.toString(value));
    }

}
