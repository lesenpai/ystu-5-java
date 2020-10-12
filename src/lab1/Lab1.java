/*
* variant 7
* */

package lab1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.math.*;
import java.util.stream.LongStream;

import static java.lang.System.out;

public class Lab1 extends Application {

    public static void main(String[] args) {
        part2();
    }

    public static long factorial(int n) {
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long x, long y) -> x * y);
    }

    public static double part2_sFunc(double x, int n) {
        return Math.pow(x, 2*n-1) / factorial(2*n-1);
    }

    public static double sh(double x) {
        return (Math.exp(x) - Math.exp(-x)) / 2;
    }

    public static String round(double x, int prec) {
        return "".format("%."+prec+"f", x);
    }

    public static void part2() {
        int xMin = 2, xMax = 3;
        double dx = 0.1, e = 0.001;
        out.printf( "%s %15s %15s %n", "X", "S", "Y");
        out.println("-".repeat(40));
        double s = 0, y = 0;

        for (double x = xMin, i = 1; x <= xMax; x += dx, i++) {
            s = 0;
            int n = 1;
            double inc = part2_sFunc(x, n);

            while(inc >= e) {
                s += inc;
                inc = part2_sFunc(x, n);
                n++;
            }

            y = sh(x);

            out.printf( "%s %15s %15s %n", round(x, 2), round(s, 5), round(y, 5));
        }
    }

    public static void part1() {
        var a = 4.21;
        var b = 0.89;
        int x_min = -3, x_max = 3, dx = 1;
        out.printf( "%3s %40s %n", "X", "Y");
        out.println("-".repeat(44));
        double y = 0;

        for(double x = x_min, i = 1; x <= x_max; x += dx, i++) {
            if (x <= b)
                y = Math.sqrt(b-x);
            else if (x>b && x<a)
                y = (x+a) / (Math.exp(a)+Math.exp(x));
            else if (x >= a)
                y = a*x + b;

            out.printf( "%3s %40s %n", x, y);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Lab1.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Hello JavaFX");
        stage.setWidth(250);
        stage.setHeight(200);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);
    }
}
