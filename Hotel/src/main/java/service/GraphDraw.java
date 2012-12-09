package service;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GraphDraw extends JPanel {
	private static final long serialVersionUID = -169819062522277246L;
	private int width = 0;
	private int height = 0;
	private int change = 0;
	private double array[][];
	private Color col[];

	public GraphDraw() {
		super();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (array != null)
			drawGraph(array, g);
	}

	public void setArray(double array[][]) {
		this.array = array;
		change = 0;
	}

	private void drawGraph(double array[][], Graphics g) {
		int i = 0, j = 0;
		int n = array.length;
		int m = array[0].length;
		int we = 0, h = 0, p = 0, r = 0, t = 0, ytext = 0, y = 0;
		double max = 0, min = 0, ampli = 0, pow = 0.0;
		double ii = 0.0;
		String text = "";

		width = getWidth();
		height = getHeight();
		// log.info("height: "+height);
		// log.info("width: "+width);

		max = min = array[0][0];
		for (i = 0; i < n; i++)
			for (j = 0; j < m; j++) {
				if (array[i][j] > max)
					max = array[i][j];
				if (array[i][j] < min)
					min = array[i][j];
			}
		h = (int) (0.9 * height);
		if (min >= 0) {
			min = 0;
			ampli = max - min;
			r = (int) (height - 0.05 * height);
			ytext = r + 15;
			// log.info("if 1");
		} else if (max <= 0) {
			max = 0;
			ampli = max - min;
			r = (int) (0.05 * height);
			ytext = r - 5;
			// log.info("if 2");
		} else {
			ampli = max - min;
			r = (int) (0.05 * height + h * max / ampli);
			ytext = (int) (height - 0.03 * height);
		}
		// log.info("n: "+n);
		// log.info("m: "+m);
		// log.info("max: "+max);
		// log.info("min: "+min);
		// log.info("ampli: "+(max-min));
		// log.info("ymidle: "+r);
		change++;
		if (change == 1) {
			col = new Color[m];
			for (i = 0; i < m; i++)
				col[i] = Color.getHSBColor((float) Math.random(), (float) Math
						.random(), (float) Math.random());
		}
		g.setColor(Color.BLACK);
		we = (int) (0.09 * width);
		g.drawLine(we - 5, r, width, r);
		g.drawLine(we, 0, we, height);

		// min=-125;
		// max=6;
		// ampli=max-min;

		if (ampli < 0.0)
			ampli *= -1;
		if (Math.abs(max) >= Math.abs(min))
			t = (int) Math.log10(max);
		else
			t = (int) Math.log10(-min);
		pow = Math.pow(10, t);
		// log.info("ampli: "+ampli);
		// log.info("log10(ampli) "+t);
		// log.info("unit: "+pow);
		y = r + 5;
		g.setColor(Color.BLACK);
		if ((max > 0 && max <= pow) || (min < 0 && min >= -pow))
			pow /= 2;
		while (ii <= max) {
			// System.out.printf("ii:"+ii);
			text = "" + ii;
			g.drawString(text, 1, (int) (y - h * (ii / ampli)));
			ii += pow;
		}
		ii = -pow;
		y = r + 5;
		while (ii >= min) {
			text = "" + ii;
			g.drawString(text, 1, (int) (y - h * (ii / ampli)));
			ii -= pow;
		}
		p = (int) (0.9 * width);
		p = p / n;
		p = p / (m + 1);
		we += (p >> 1);
		// log.info("client height: "+h);
		// log.info("client width: "+0.9*width);
		// log.info("xstart : "+we);
		// log.info("xtext : "+d);
		for (i = 0; i < n; i++) {
			text = "(" + (i + 1) + ")";
			g.setColor(Color.BLACK);
			g.drawString(text, we, ytext);
			for (j = 0; j < m; j++) {
				g.setColor(col[j]);
				t = (int) (h * (array[i][j] / ampli));
				if (t >= 0)
					g.fillRect(we, (r - t), p, t);
				else
					g.fillRect(we, r, p, -t);
				// log.info("x: "+we+" y: "+t);
				// System.out.printf("t: %d wart: %.1f ampli: %.1f wsp: %.8f\n",t,array[i][j],ampli,array[i][j]/ampli);
				we += p;
			}
			we += p;
		}
	}
}