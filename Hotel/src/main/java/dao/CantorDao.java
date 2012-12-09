package dao;

import dto.SimpleNameData;
import dto.cantor.CompanyData;
import dto.cantor.CurrencyData;
import dto.cantor.CustomerData;
import exception.DAOException;
import service.cantor.CURRENCY;

import java.util.List;

public interface CantorDao {

    List<SimpleNameData> showColumnsForCurrency() throws DAOException;

    List<CurrencyData> findAllCurrency() throws DAOException;

    List<SimpleNameData> showColumnsForCustomer() throws DAOException;

    List<CustomerData> findAllCustomers(long customerId) throws DAOException;

    List<SimpleNameData> showColumnsForCompany() throws DAOException;

    List<CompanyData> findAllComparable(String companyId) throws DAOException;

    CurrencyData findCurrencyByName(CURRENCY currency) throws DAOException;
}
