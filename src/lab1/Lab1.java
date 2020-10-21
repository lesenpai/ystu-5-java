/*
 * variant 7
 * */

package lab1;

import java.util.stream.LongStream;
import static java.lang.System.out;

public class Lab1 {

    public static void main(String[] args) {
        part1();
        out.println();
        part2();
    }

    public static void part1() {
        double a = 4.21, b = 0.89, y = 0;
        int xMin = -3, xMax = 3, dx = 1;
        out.printf("%3s %15s %n", "X", "Y");
        out.println("-".repeat(30));

        for (int x = xMin, i = 1; x <= xMax; x += dx, i++) {
            if (x <= b)
                y = Math.sqrt(b - x);
            else if (x > b && x < a)
                y = (x + a) / (Math.exp(a) + Math.exp(x));
            else if (x >= a)
                y = a * x + b;

            out.printf("%3s %15s %n", x, round(y, 5));
        }
    }

    public static long factorial(int n) {
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long x, long y) -> x * y);
    }

    public static double part2_sFunc(double x, int n) {
        return Math.pow(x, 2 * n - 1) / factorial(2 * n - 1);
    }

    public static double sh(double x) {
        return (Math.exp(x) - Math.exp(-x)) / 2;
    }

    public static String round(double x, int prec) {
        return String.format("%." + prec + "f", x);
    }

    public static void part2() {
        int xMin = 2, xMax = 3;
        double dx = 0.1, e = 0.001;
        out.printf("%3s %15s %15s %n", "X", "S", "Y");
        out.println("-".repeat(40));
        double s = 0, y = 0;

        for (double x = xMin, i = 1; x <= xMax; x += dx, i++) {
            s = 0;
            int n = 1;
            double inc = part2_sFunc(x, n);

            while (inc >= e) {
                s += inc;
                inc = part2_sFunc(x, n);
                n++;
            }

            y = sh(x);
            out.printf("%3s %15s %15s %n", round(x, 2), round(s, 5), round(y, 5));
        }
    }
}