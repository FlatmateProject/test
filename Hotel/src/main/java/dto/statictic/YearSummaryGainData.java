package dto.statictic;

public class YearSummaryGainData {

    private int year;

    private double reservationSummaryGain;

    private double serviceSummaryGain;

    private double cantorSummaryGain;

    public YearSummaryGainData() {
    }

    public YearSummaryGainData(int year, double reservationSummaryGain, double serviceSummaryGain, double cantorSummaryGain) {
        this.year = year;
        this.reservationSummaryGain = reservationSummaryGain;
        this.serviceSummaryGain = serviceSummaryGain;
        this.cantorSummaryGain = cantorSummaryGain;
    }

    public int getYear() {
        return year;
    }

    public double getReservationSummaryGain() {
        return reservationSummaryGain;
    }

    public double getServiceSummaryGain() {
        return serviceSummaryGain;
    }

    public double getCantorSummaryGain() {
        return cantorSummaryGain;
    }
}
