/*
 * variant 7
 * */

package lab1;

import java.util.stream.LongStream;
import static java.lang.System.out;

public class Lab1 {

	public static void main(String[] args) {
		out.println(part1());
		out.println(part2());
	}

	public static String part1() {
		StringBuilder sb = new StringBuilder();
		double a = 4.21, b = 0.89, y = 0;
		int xMin = -3, xMax = 3, dx = 1;
		sb.append(String.format("%3s %15s %n", "X", "Y"));
		sb.append("-".repeat(30)).append("\n");

		for (int x = xMin, i = 1; x <= xMax; x += dx, i++) {
			if (x <= b)
				y = Math.sqrt(b - x);
			else if (x > b && x < a)
				y = (x + a) / (Math.exp(a) + Math.exp(x));
			else if (x >= a)
				y = a * x + b;

			sb.append(String.format("%3s %15s %n", x, round(y, 5)));
		}

		return sb.toString();
	}

	public static double sh(double x) {
		return (Math.exp(x) - Math.exp(-x)) / 2;
	}

	public static String round(double n, int prec) {
		return String.format("%." + prec + "f", n);
	}

	public static String part2() {
		StringBuilder sb = new StringBuilder();
		int xMin = 2, xMax = 3;
		double dx = 0.1, e = 0.001;
		sb.append(String.format("%5s %15s %15s %n", "X", "S", "Y"));
		sb.append("-".repeat(40)).append("\n");
		double s = 0, y = 0;

		for (double x = xMin, i = 1; x <= xMax; x += dx, i++) {
			int n = 1;

			s = Math.sinh(x);

			y = sh(x);
			sb.append(String.format("%5s %15s %15s %n", round(x, 2), round(s, 5), round(y, 5)));
		}

		return sb.toString();
	}
}