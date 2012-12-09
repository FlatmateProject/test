package dto.statictic;

public class MonthSummaryGainData {

	private int month;

	private double reservationSummaryGain;

	private double serviceSummaryGain;

	private double cantorSummaryGain;

    public MonthSummaryGainData(){

    }

    public MonthSummaryGainData(int month, double reservationSummaryGain, double serviceSummaryGain, double cantorSummaryGain) {
        this.month = month;
        this.reservationSummaryGain = reservationSummaryGain;
        this.serviceSummaryGain = serviceSummaryGain;
        this.cantorSummaryGain = cantorSummaryGain;
    }

    public int getMonth() {
		return month;
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