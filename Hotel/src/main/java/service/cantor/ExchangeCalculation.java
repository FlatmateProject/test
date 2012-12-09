package service.cantor;

public class ExchangeCalculation {

    private CURRENCY saleCurrency;

    private CURRENCY buyCurrency;

    private float amount;

    private float cost;

    private float gain;

    public static final ExchangeCalculation ZERO = new ExchangeCalculation(0.0f, 0.0f, 0.0f);

    private ExchangeCalculation(float amount, float cost, float gain) {
        this.amount = amount;
        this.cost = cost;
        this.gain = gain;
    }

    public static ExchangeCalculation save(CURRENCY saleCurrency, CURRENCY buyCurrency, float amount, float cost, float gain) {
        ExchangeCalculation exchangeCalculation = new ExchangeCalculation(amount, cost, gain);
        exchangeCalculation.saleCurrency = saleCurrency;
        exchangeCalculation.buyCurrency = buyCurrency;
        return exchangeCalculation;
    }

    public CURRENCY getSaleCurrency() {
        return saleCurrency;
    }

    public CURRENCY getBuyCurrency() {
        return buyCurrency;
    }

    public float getAmount() {
        return amount;
    }

    public float getCost() {
        return cost;
    }

    public float getGain() {
        return gain;
    }
}
