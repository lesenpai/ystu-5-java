/*
 * variant 20
 * */

package lab1;

import static java.lang.System.out;

public class Lab1 {
    public static void main(String[] args) {
        double a = 4.21, b = 0.89, x_min = -3, x_max = 3, dx = 1;
        out.printf( "%10s %15s %n", "X", "Y");
        out.println("-".repeat(30));
        double y = 0;

        for(double x = x_min, i = 1; x <= x_max; x += dx, i++) {
            if (x <= b)
                y = Math.sqrt(b-x);
            else if (x>b && x<a)
                y = (x+a) / (Math.exp(a)+Math.exp(x));
            else if (x >= a)
                y = a*x + b;

            out.printf( "%10s %15s %n", round(x,3), round(y,5));
        }
    }

    public static String round(double x, int prec) {
        return "".format("%."+prec+"f", x);
    }
}
