package tmp;

import java.util.Scanner;
import static java.lang.System.out;
import java.util.Random;

class Temp {

    public static void main(String[] args) {

        int offset = 97;
        var in = new Scanner(System.in);
        out.println("enter input:");
        out.print(">> ");
        var input = in.nextLine();
        var gamma = genString(input.length());
        out.println("gamma: "+gamma);
        var encrypted = "";
        for(int i=0; i<input.length();i++) {
            encrypted += (char)(((int)input.toCharArray()[i]-offset + (int)gamma.toCharArray()[i] - offset)%26+offset);
        }
        out.println("encrypted: "+encrypted);
        var decrypted = "";
        for(int i=0; i<input.length();i++) {
            decrypted += (char)(((int)(encrypted.toCharArray()[i]-offset)-(int)(gamma.toCharArray()[i]-offset)+26)%26+offset);
        }
        out.println("decrypted: "+decrypted);
    }

    public static String genString(int size) {
        var charAr = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random rndGen = new Random();
        String s = "";
        for (int i = 0; i < size; i++) {
            s += charAr[rndGen.nextInt(charAr.length)];
        }
        return s;
    }
}
