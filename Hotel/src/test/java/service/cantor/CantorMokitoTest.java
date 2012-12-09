package service.cantor;

import dao.CantorDao;
import dto.SimpleNameData;
import dto.cantor.CurrencyData;
import dto.cantor.CustomerData;
import exception.DAOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static assertions.ExchangeCalculationAssertion.assertThat;
import static assertions.TableAssert.assertThat;
import static conditions.table.ColumnCondition.containColumns;
import static conditions.table.RowCondition.containsRow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CantorMokitoTest {

    @Test
    public void shouldCreateEmptyCurrencyTable() throws Exception {
        //given
        CantorDao cantorDao = mock(CantorDao.class);

        //when
        Cantor cantor = new Cantor(cantorDao);
        CantorTableResult result = cantor.createCurrencyTable();

        //then
        assertThat(result)
                .isNotNull()
                .hasRowNumber(1)
                .hasColumnNumber(1)
                .is(containColumns(CantorTableResult.EMPTY_COLUMN))
                .is(containsRow(CantorTableResult.EMPTY_ROW));
    }

    @Test
    public void shouldCreateCurrencyTableWithOneRow() throws DAOException {
        // given
        Object[] row = new Object[]{1, "USD", 320, 300, 100};
        String[] columnNames = new String[]{"Id", "Name", "Sale Price", "Buy Price", "Quantity"};

        CurrencyData currencyData = mock(CurrencyData.class);
        when(currencyData.getArray()).thenReturn(row);

        SimpleNameData simpleNameData = mock(SimpleNameData.class);
        when(simpleNameData.getName()).thenReturn(columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4]);

        CantorDao cantorDao = mock(CantorDao.class);
        when(cantorDao.showColumnsForCurrency()).thenReturn(Arrays.asList(simpleNameData, simpleNameData, simpleNameData, simpleNameData, simpleNameData));
        when(cantorDao.findAllCurrency()).thenReturn(Arrays.asList(currencyData));

        // when
        Cantor cantor = new Cantor(cantorDao);
        CantorTableResult result = cantor.createCurrencyTable();

        // then
        assertThat(result)
                .isNotNull()
                .hasColumnNumber(5)
                .hasRowNumber(1)
                .is(containColumns(columnNames))
                .is(containsRow(row));
    }

    @Test
    public void shouldCreateEmptyCustomerTable() throws Exception {
        //given
        long pesel = 87122206592L;
        CantorDao cantorDao = mock(CantorDao.class);

        //when
        Cantor cantor = new Cantor(cantorDao);
        CantorTableResult result = cantor.createCustomerTable(pesel);

        //then
        assertThat(result)
                .isNotNull()
                .hasRowNumber(1)
                .hasColumnNumber(1)
                .is(containColumns(CantorTableResult.EMPTY_COLUMN))
                .is(containsRow(CantorTableResult.EMPTY_ROW));
    }

    @Test
    public void shouldCreateCustomerTableWithOneRow() throws DAOException {
        // given
        long pesel = 87122206592L;
        String[] columnNames = new String[]{ "Pesel", "Name", "Family", "County", "City", "Street", "Block", "Flat", "Status", "Notes", "Phone", "Nip"};
        Object[] row = new Object[]{87122206592L, "Piotr", "Piotrowski", "Małopolsie", "Kraków", "Zdunów", "22c", 30L, "NEW","OK", 889225169L, 6582514L};

        SimpleNameData simpleNameData = mock(SimpleNameData.class);
        when(simpleNameData.getName()).thenReturn(columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4],
                (columnNames[5]), columnNames[6], columnNames[7], columnNames[8], columnNames[9], columnNames[10], columnNames[11]);

        CustomerData customerData = mock(CustomerData.class);
        when(customerData.getArray()).thenReturn(row);

        CantorDao cantorDao = mock(CantorDao.class);
        when(cantorDao.showColumnsForCustomer()).thenReturn(Arrays.asList(simpleNameData, simpleNameData, simpleNameData, simpleNameData, simpleNameData, simpleNameData,
                simpleNameData, simpleNameData, simpleNameData, simpleNameData, simpleNameData, simpleNameData));
        when(cantorDao.findAllCustomers(pesel)).thenReturn(Arrays.asList(customerData));

        // when
        Cantor cantor = new Cantor(cantorDao);
        CantorTableResult result = cantor.createCustomerTable(pesel);

        // then
        assertThat(result)
                .isNotNull()
                .hasColumnNumber(12)
                .hasRowNumber(1)
                .is(containColumns(columnNames))
                .is(containsRow(row));
    }

    @Test(dataProvider = "prepareCasesForCalculateMoneyExchange")
    public void shouldCalculateMoneyExchangeForGivenCurrency(CURRENCY saleCurrency, CURRENCY buyCurrency, float amount, float cost, float gain,
                                                             float buyPriceForSaleCurrency, float salePriceForSaleCurrency, float salePriceForBuyCurrency) throws DAOException {
        // given
        CantorDao cantorDao = mock(CantorDao.class);

        CurrencyData saleCurrencyData = mock(CurrencyData.class);
        when(saleCurrencyData.getBuyPrice()).thenReturn(buyPriceForSaleCurrency);
        when(saleCurrencyData.getSalePrice()).thenReturn(salePriceForSaleCurrency);

        CurrencyData buyCurrencyData = mock(CurrencyData.class);
        when(buyCurrencyData.getSalePrice()).thenReturn(salePriceForBuyCurrency);

        when(cantorDao.findCurrencyByName(buyCurrency)).thenReturn(buyCurrencyData);
        when(cantorDao.findCurrencyByName(saleCurrency)).thenReturn(saleCurrencyData);

        // when
        Cantor cantor = new Cantor(cantorDao);
        ExchangeCalculation exchangeCalculation = cantor.changeCalc(saleCurrency, buyCurrency, amount);

        // then
        assertThat(exchangeCalculation)
            .isNotNull()
            .isSaleCurrency(saleCurrency)
            .isBuyCurrency(buyCurrency)
            .isAmount(amount)
            .isCost(cost)
            .isGain(gain);
    }

    @DataProvider
    public static Object[][] prepareCasesForCalculateMoneyExchange() {
        return new Object[][]{
                { CURRENCY.PLN, CURRENCY.EUR, 100.0f, 25.0f, 0.0f, 1.0f, 1.0f, 4.0f },
                { CURRENCY.EUR, CURRENCY.PLN, 100.0f, 300.0f, 100.0f, 3.0f, 4.0f, 1.0f },
                { CURRENCY.EUR, CURRENCY.USD, 100.0f, 150.0f, 100.0f, 3.0f, 4.0f, 2.0f },
                { CURRENCY.EUR, CURRENCY.EUR, 100.0f, 75.0f, 100.0f, 3.0f, 4.0f, 4.0f }
        };
    }
}
