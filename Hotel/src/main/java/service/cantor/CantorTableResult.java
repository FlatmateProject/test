package service.cantor;

public class CantorTableResult {

    public static final String EMPTY_LABEL = "Brak danych";

    public static final Object[] EMPTY_ROW = new Object[]{EMPTY_LABEL};

    public static final String[] EMPTY_COLUMN = new String[]{EMPTY_LABEL};

    public static final CantorTableResult EMPTY = new CantorTableResult(new Object[][]{EMPTY_ROW}, EMPTY_COLUMN);

    final private Object rowsData[][];

    final private String columnNames[];

    public static CantorTableResult store(Object[][] rowsData, String[] columnNames) {
        return new CantorTableResult(rowsData, columnNames);
    }

    public Object[][] getRowsData() {
        return rowsData;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    private CantorTableResult(Object[][] rowsData, String[] columnNames) {
        this.columnNames = columnNames;
        this.rowsData = rowsData;
    }
}
