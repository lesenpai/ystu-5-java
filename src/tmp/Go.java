package tmp;

import java.io.File;  // Import the File class
import java.io.FileInputStream;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import static java.lang.System.out;

public class Go {
    public static void main(String[] args) throws IOException {
        /*try {
            File file = new File("etc/lab2/raw.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                out.println(data);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            out.println("ERROR");
            e.printStackTrace();
        }*/
        File file = new File(("etc/lab2/raw.txt"));
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        var s = new String(data, "UTF-8");

        for(String val: s.split("\n")) {
            out.println(val);
        }

    }
}