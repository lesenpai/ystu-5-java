/*
 * variant 20
 * */

package lab1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lab1.Lab1;

import static java.lang.System.out;
import static lab1.Lab1.round;

public class Lab1UI extends Application {
    @FXML private TextArea TArea_Text;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @FXML private void Btn_Start_Click(ActionEvent e) {
        double a = 4.21, b = 0.89, x_min = -3, x_max = 3, dx = 1;
        TArea_Text.appendText(String.format("%10s %15s %n", "X", "Y"));
        TArea_Text.appendText("-".repeat(30)+"\n");
        double y = 0;

        for(double x = x_min, i = 1; x <= x_max; x += dx, i++) {
            if (x <= b)
                y = Math.sqrt(b-x);
            else if (x>b && x<a)
                y = (x+a) / (Math.exp(a)+Math.exp(x));
            else if (x >= a)
                y = a*x + b;

            TArea_Text.appendText(String.format("%10s %15s %n", round(x,3), round(y,5)));
        }
    }

    @FXML private void Btn_Clear_Click(Event e) {
        TArea_Text.clear();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Lab1UI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Lab 1");
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);
    }
}