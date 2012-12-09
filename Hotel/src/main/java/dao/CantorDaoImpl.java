package dao;

import dto.SimpleNameData;
import dto.cantor.CompanyData;
import dto.cantor.CurrencyData;
import dto.cantor.CustomerData;
import exception.DAOException;
import service.cantor.CURRENCY;

import java.util.List;

public class CantorDaoImpl extends AbstractDao implements CantorDao{

    public List<SimpleNameData> showColumnsForCurrency() throws DAOException {
        String query = "show columns from hotel.waluty;";
        return executeQuery(query, SimpleNameData.class);
    }

    @Override
    public List<CurrencyData> findAllCurrency() throws DAOException {
        String query = "select * from waluty;";
        return executeQuery(query, CurrencyData.class);

    }

    @Override
    public List<SimpleNameData> showColumnsForCustomer() throws DAOException {
        String query = "show columns from hotel.klienci";
        return executeQuery(query, SimpleNameData.class);
    }

    @Override
    public List<CustomerData> findAllCustomers(long customerId) throws DAOException {
        String query = "select * from hotel.klienci where IDK_PESEL=" +  customerId;
        return executeQuery(query, CustomerData.class);
    }


    @Override
    public List<SimpleNameData> showColumnsForCompany() throws DAOException {
        String query = "show columns from hotel.firmy";
        return executeQuery(query, SimpleNameData.class);
    }

    @Override
    public List<CompanyData> findAllComparable(String companyId) throws DAOException {
        String query = "select * from hotel.firmy where IDF_KRS=" + companyId;
        return executeQuery(query, CompanyData.class);
    }

    @Override
    public CurrencyData findCurrencyByName(CURRENCY currency) throws DAOException {
        String query = "select * from hotel.waluty where NAZWA=" + "'" + currency + "'";
        return uniqueResult(query, CurrencyData.class);
    }

}
