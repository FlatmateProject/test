package assertions;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;
import service.cantor.CantorTableResult;

public class TableAssert extends GenericAssert<TableAssert, CantorTableResult>{

    private TableAssert(CantorTableResult actual) {
        super(TableAssert.class, actual);
    }

    public static TableAssert assertThat(CantorTableResult actual) {
        return new TableAssert(actual);
    }

    public TableAssert hasRowNumber(int rowNumber) {
        Assertions.assertThat(actual.getRowsData().length).isEqualTo(rowNumber);
        return this;
    }

    public TableAssert hasColumnNumber(int columnNumber) {
        Assertions.assertThat(actual.getColumnNames().length).isEqualTo(columnNumber);
        return this;
    }


}
