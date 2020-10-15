import lab1.Lab1;
import lab1.Lab1UI;

import javax.swing.*;

import static java.lang.System.out;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var in = new Scanner(System.in);
        out.println("type lab number:");
        var input = in.nextLine();
        var DEBUG = false;
        if(DEBUG)
            Lab1.main(null);
        else
            switch (input) {
                case "1":
                    out.println("type part (1=cmd, 2=ui):");
                    input=in.nextLine();
                    switch (input) {
                        case "1":
                            Lab1.main(null);
                            break;
                        case "2":
                            Lab1UI.main(null);
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