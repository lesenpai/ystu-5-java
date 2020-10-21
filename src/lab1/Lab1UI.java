/*
 * variant 7
 * */

package lab1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import static lab1.Lab1.round;

public class Lab1UI extends Application {
    @FXML private TextArea ta_P1_Text;
    @FXML private TextArea ta_P2_Text;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @FXML private void btn_P1_Start_Click(ActionEvent e) {
        ta_P1_Text.clear();
        double a = 4.21, b = 0.89, y = 0;
        int xMin = -3, xMax = 3, dx = 1;
        ta_P1_Text.appendText(String.format("%3s %15s %n", "X", "Y"));
        ta_P1_Text.appendText("-".repeat(30));
        ta_P1_Text.appendText("\n");

        for (int x = xMin, i = 1; x <= xMax; x += dx, i++) {
            if (x <= b)
                y = Math.sqrt(b - x);
            else if (x > b && x < a)
                y = (x + a) / (Math.exp(a) + Math.exp(x));
            else if (x >= a)
                y = a * x + b;

            ta_P1_Text.appendText(String.format("%3s %15s %n", x, round(y, 5)));
        }
    }

    @FXML private void btn_P2_Start_Click(ActionEvent e) {
        ta_P2_Text.clear();
        int xMin = 2, xMax = 3;
        double dx = 0.1, eps = 0.001, s = 0, y = 0;
        ta_P2_Text.appendText(String.format("%3s %15s %15s %n", "X", "S", "Y"));
        ta_P2_Text.appendText("-".repeat(40));
        ta_P2_Text.appendText("\n");

        for (double x = xMin, i = 1; x <= xMax; x += dx, i++) {
            s = 0;
            int n = 1;
            double inc = Lab1.part2_sFunc(x, n);

            while (inc >= eps) {
                s += inc;
                inc = Lab1.part2_sFunc(x, n);
                n++;
            }

            y = Lab1.sh(x);
            ta_P2_Text.appendText(String.format("%3s %15s %15s %n", round(x, 2), round(s, 5), round(y, 5)));
        }
    }

    @FXML private void btn_PX_Clear_Click(Event e) {
        String btnId = ((Control)e.getSource()).getId();
        if (btnId.equals("btn_P1_Clear")) {
            ta_P1_Text.clear();
        }
        else if (btnId.equals("btn_P2_Clear")) {
            ta_P2_Text.clear();
        }
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