package conditions.table;

import org.fest.assertions.Condition;
import service.cantor.CantorTableResult;

/**
 * User: piotro
 * Date: 11/11/12
 * Time: 12:08 PM
 */
public class RowCondition extends Condition<CantorTableResult> {

    private Object[] expectedCells;

    private RowCondition(Object[] expectedCells, String description) {
        this.expectedCells = expectedCells;
        as(description);
    }

    public static RowCondition containsRow(Object[] expectedColumnNames){
        return new RowCondition(expectedColumnNames, "containsRow");
    }

    @Override
    public boolean matches(CantorTableResult cantorTableResult) {
        Object[][] data = cantorTableResult.getRowsData();
        for (int i = 0; i < data.length; i++) {
            Object[] actualCells = data[i];
            if(isActualRowEqualToExpectedRow(actualCells)) {
                return true;
            }
        }
        return false;
    }

    private boolean isActualRowEqualToExpectedRow(Object[] actualCells) {
        for (int i = 0; i < actualCells.length; i++) {
            if (isNotEqual(actualCells[i], expectedCells[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isNotEqual(Object actualCell, Object expectedCell) {
        return !actualCell.equals(expectedCell);
    }
}
