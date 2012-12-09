package dto.cantor;

import service.cantor.ArrayObtained;

public class CustomerData implements ArrayObtained {

    private long idPesel;

    private String name;

    private String family;

    private String county;

    private String city;

    private String street;

    private String block;

    private long flat;

    private String status;

    private String notes;

    private long phone;

    private long nip;

    @Override
    public Object[] getArray() {
        return new Object[]{idPesel, name, family, county, city, street, block, flat, status, notes, phone, nip};
    }
}
