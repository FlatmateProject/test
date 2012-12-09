package service.statictic;

public class DiagramElement {

	private final double[] bars;
	
	public DiagramElement(double... bars) {
		this.bars = bars;
	}

	public double[] getBars() {
		return bars;
	}
}
