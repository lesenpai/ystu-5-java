/*
 * variant 7
 * */

package lab1.p1;

import static java.lang.System.out;

public class Lab1 {

	public static void main(String[] args) {
		out.println(prog1());
		out.println(prog2());
	}

	public static String prog1() {
		StringBuilder sb = new StringBuilder();
		double a = -2.8368, b = -2.825,
			xMin = -4, xMax = 0.8, dx = 0.8,
			x = 0, y = 0;
		sb.append(String.format("%10s %20s %n", "X", "Y"));
		sb.append("-".repeat(34)).append("\n");

		for (x = xMin; x <= xMax; x += dx) {
			var ax2 = a * x * x;
			if (ax2 < b)
				y = pow(Math.E, -2 * x) * Math.cos(a * x);
			else if (ax2 >= b && Math.abs(a) >= x * x * x)
				y = Math.log(Math.abs((a * a * x) / (b * b * b)));
			else if (ax2 >= b && Math.abs(a) < x * x * x)
				y = pow(Math.atan(a * b * x * x), 2) - 4.7 - Math.sin(Math.abs(a - x));

			sb.append(String.format("%10s %20s %n", round(x, 3), round(y, 5)));
		}

		return sb.toString();
	}

	public static String round(double n, int prec) {
		return String.format("%." + prec + "f", n);
	}

	private static double pow(double a, double b) {
		return Math.pow(a, b);
	}

	public static String prog2() {
		StringBuilder sb = new StringBuilder();
		double xMin = 0, xMax = 2 * Math.PI;
		double dx = Math.PI / 6, E = 1e-5;
		sb.append(String.format("%5s %15s %n", "X", "Y"));
		sb.append("-".repeat(25)).append("\n");
		double T = 0, y = 0;
		int k;

		for (double x = xMin, i = 1; x <= xMax; x += dx, i++) {
			T = 1;
			y = 0;
			k = 1;
			while (Math.abs(T) > E) {
				y += T;
				T *= -(x * x) / (k * k + 1.5 * k + 0.5);
				k++;
			}
			sb.append(String.format("%5s %15s %n", round(x, 2), round(y, 5)));
		}

		return sb.toString();
	}
}