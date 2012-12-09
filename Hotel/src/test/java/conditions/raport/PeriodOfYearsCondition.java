package conditions.raport;

import org.fest.assertions.Condition;

public class PeriodOfYearsCondition extends Condition<String> {

    private int yearFrom;

    private int yearTo;

    public PeriodOfYearsCondition(int yearFrom, int yearTo, String assertFailDescription) {
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        as(assertFailDescription);
    }

    public static PeriodOfYearsCondition headerContainPeriodOfYears(int yearFrom, int yearTo) {
        return new PeriodOfYearsCondition(yearFrom, yearTo, "headerContainPeriodOfYears");
    }

    @Override
    public boolean matches(String textReport) {
        return textReport.contains(Integer.toString(yearFrom))
                && textReport.contains(Integer.toString(yearTo))
                && textReport.indexOf(Integer.toString(yearFrom)) < textReport.indexOf(Integer.toString(yearTo));
    }
}
