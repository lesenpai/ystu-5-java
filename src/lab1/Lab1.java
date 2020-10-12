package lab1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.math.*;

public class Lab1 extends Application {

    public static void main(String[] args) {
//        launch(args);

        var a = 4.21;
        var b = 0.89;
        int x_min = -3, x_max = 3, dx = 1;
        System.out.printf( "%3s %40s %n", "X", "Y");
        System.out.println("-".repeat(44));

        double y = 0;
        for(int x = x_min, i=1; x <= x_max; x += dx, i++) {
            if (x <= b)
                y = Math.sqrt(b-x);
            else if (x>b && x<a)
                y = (x+a) / (Math.exp(a)+Math.exp(x));
            else if (x >= a)
                y = a*x + b;

            System.out.printf( "%3s %40s %n", x, y);
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
