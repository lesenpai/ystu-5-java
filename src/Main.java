import lab1.Lab1;

import static java.lang.System.out;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var in = new Scanner(System.in);
        out.println("type lab number:");
        var input = in.nextLine();

//        Lab1.main(null);
        switch (input) {
            case "1":
                out.println("running lab 1...");
                Lab1.main(null);
                break;
            default:
                out.println("no lab");
                break;
        }
    }
}
