package dao;

import exception.DAOException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

abstract class AbstractDao {

    private static final Logger log = Logger.getLogger(AbstractDao.class);

    Singleton session = Singleton.getInstance();

    private Map<Class, TypeConverter> conversionMap = new HashMap<Class, TypeConverter>();

    {
        conversionMap.put(long.class, new TypeConverter() {
            @Override
            public Object convert(ResultSet dataSet, int index) throws SQLException {
                return dataSet.getLong(index);
            }
        });

        conversionMap.put(float.class, new TypeConverter() {
            @Override
            public Object convert(ResultSet dataSet, int index) throws SQLException {
                return dataSet.getFloat(index);
            }
        });

        conversionMap.put(GregorianCalendar.class, new TypeConverter() {
            @Override
            public Object convert(ResultSet dataSet, int index) throws SQLException {
                Date date = dataSet.getDate(index);
                long millis = date.getTime();
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(millis);
                return calendar;
            }
        });

        conversionMap.put(String.class, new TypeConverter() {
            @Override
            public Object convert(ResultSet dataSet, int index) throws SQLException {
                return dataSet.getString(index);
            }
        });
    }

    Object uniqueResult(String query) throws DAOException {
        try {
            ResultSet resultSet = session.query(query);
            return resultSet.next() ? resultSet.getObject(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object();
        }
    }

    <T> T uniqueResult(String query, Class<T> dtoClass) throws DAOException {
        return executeQuery(query, dtoClass).get(0);
    }

    <T> List<T> executeQuery(String query, Class<T> dtoClass) throws DAOException {
        ResultSet resultSet = session.query(query);
        return transform(resultSet, dtoClass);
    }

    private <T> List<T> transform(ResultSet dataSet, Class<T> resultClass) {
        ArrayList<T> EMPTY_LIST = new ArrayList<T>();
        try {
            if (dataSet != null) {
                return createTransformedRows(dataSet, resultClass);
            }
            return EMPTY_LIST;
        } catch (Exception e) {
            e.printStackTrace();
            return EMPTY_LIST;
        }
    }

    private <T> List<T> createTransformedRows(ResultSet dataSet, Class<T> objectClass) throws Exception {
        List<T> transformedRows = new ArrayList<T>();
        while (dataSet.next()) {
            T object = createObject(objectClass);
            fillObject(object, objectClass.getDeclaredFields(), dataSet);
            transformedRows.add(objectClass.cast(object));
        }
        return transformedRows;
    }

    private <T> T createObject(Class<T> resultClass) throws InstantiationException, IllegalAccessException {
        return resultClass.newInstance();
    }

    private <T> void fillObject(T object, Field[] fields, ResultSet dataSet) throws IllegalArgumentException, IllegalAccessException, SQLException {
        int index = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(object, getObjectByIndex(dataSet, index, field.getType()));
            index++;
            log.info(field.getName() + ": " + field.toString());
        }
    }

    private Object getObjectByIndex(ResultSet dataSet, int index, Class<?> type) throws SQLException {
        TypeConverter typeConverter = getConverterForType(type);
        return typeConverter.convert(dataSet, index);
    }

    private AbstractDao.TypeConverter getConverterForType(Class<?> type) {
        TypeConverter typeConverter = conversionMap.get(type);
        if (typeConverter == null) {
            throw new RuntimeException("Nie ma implementcaji konwertera dla typu: " + type + " dodaj ja");
        }
        return typeConverter;
    }


    private interface TypeConverter<T> {
        T convert(ResultSet dataSet, int index) throws SQLException;
    }
}
