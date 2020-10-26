import lab1.Lab1;
import lab1.Lab1UI;
import lab2_old.Lab2;
import lab2_old.ui.Lab2UI;

import static java.lang.System.out;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        var DEBUG = false;
        if(DEBUG)
            Lab2UI.main(null);
        else {
            var in = new Scanner(System.in);
            out.println("type lab number:");
            var input = in.nextLine();
            switch (input) {
                case "1":
                    out.println("type part (1=cmd, 2=ui):");
                    input = in.nextLine();
                    switch (input) {
                        case "1" -> {
                            Lab1.main(null);
                        }
                        case "2" -> {
                            Lab1UI.main(null);
                        }
                        default -> {
                            out.println("bad input");
                        }
                    }
                    break;
                case "2":
                    out.println("type part (1=cmd, 2=ui):");
                    input=in.nextLine();
                    switch (input) {
                        case "1":
                            Lab2.main(null);
                            break;
                        case "2":
                            Lab2UI.main(null);
                            break;
                        default:
                            out.println("bad input");
                            break;
                    }
                    break;
                default:
                    out.println("no lab");
                    break;
            }
        }
    }
}