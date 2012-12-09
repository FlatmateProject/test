package dto.cantor;

import org.fest.util.Arrays;
import service.cantor.ArrayObtained;

public class CurrencyData implements ArrayObtained {

    private long currencyId;

    private String name;

    private float salePrice;

    private float buyPrice;

    private float quantity;

    public Object[] getArray() {
        return Arrays.array(currencyId, name, salePrice, buyPrice, quantity);
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public String getName() {
        return name;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public float getQuantity() {
        return quantity;
    }
}
