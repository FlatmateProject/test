package conditions.table;

import org.fest.assertions.Condition;
import service.cantor.CantorTableResult;

public class ColumnCondition extends Condition<CantorTableResult> {

    private String[] expectedColumnNames;

    private ColumnCondition(String[] object, String description) {
        this.expectedColumnNames = object;
        as(description);
    }

    public static ColumnCondition containColumns(String[] expectedColumnNames){
      return new ColumnCondition(expectedColumnNames, "containColumns");
    }

    @Override
    public boolean matches(CantorTableResult cantorTableResult) {
        String[] actualColumnNames = cantorTableResult.getColumnNames();
        for (int i = 0; i < actualColumnNames.length; i++) {
           if(isNotEqual(actualColumnNames[i], expectedColumnNames[i])){
               return false;
           }
        }
        return true;
    }

    private boolean isNotEqual(String actualColumnName, String expectedColumnName) {
        return !actualColumnName.equals(expectedColumnName);
    }
}
