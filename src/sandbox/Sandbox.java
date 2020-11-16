package sandbox;

import com.mysql.jdbc.StringUtils;
import lab2.p1.DBType;
import lab2.sql.SQL;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;

public class Sandbox {
	public static void main(String[] args) throws SQLException, IOException {
		//String db = "football", u = "le", p = "ledb";
		//String[] script = Arrays.stream(Arrays.stream(Files.readString(Path.of("./src/lab2/sql/sqlserver_init.sql"))
		//		.split("\n"))
		//		.filter(s -> !s.startsWith("#")) // remove commented lines
		//		.toArray(String[]::new))
		//		.reduce("", String::concat)
		//		.split(";(?!$)"); // split into queries

		//for (var l : script) out.println(">>> " + l.replaceAll("\r", ""));
		//var sql = new SQL(DBType.JavaDB, "", u, p);
		vlasInfobez2_2();
	}

	public static void vlasInfobez2_2() {
		var in = new Scanner(System.in);

		String input;

		boolean isRunning = true;
		while (isRunning) {
			out.println("go? (go=1, exit=bb)");
			out.print(">> ");
			input = in.nextLine();
			switch (input) {
				case "1":
					out.println("[go]");
					out.println("enter <input>:");
					out.print(">> ");
					var inputStr = in.nextLine();
					var ansiInputStr = "";
					//convert input to win1251 (ansi) each symbol
					try {
						ansiInputStr = new String(inputStr.getBytes(StandardCharsets.UTF_8), "windows-1251");
					}
					catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						return;
					}

					// generate gamma
					int p = 7, q = 19, m = p * q;
					var gamma = new int[inputStr.length()];
					for (int i = 0; i < inputStr.length(); i++) {
						int xn = m - 80;
						int x = (int)(Math.pow(xn, ((Math.pow(2, i)) % ((p - 1) * (q - 1))))) % m;
						gamma[i] = x;
					}
					out.println("gamma: "+ Arrays.toString(gamma));

					var encoded = new int[inputStr.length()];
					for (int i=0; i<inputStr.length();i++) {
						encoded[i] = ((int)ansiInputStr.charAt(i)) ^ gamma[i];
					}

					out.println("do encode? (yes=1)");
					out.print(">> ");
					input = in.nextLine();
					if (input.equals("1")) {
						out.println("[encode]");
						var decoded = "";
						for (int i=0; i<inputStr.length(); i++) {
							decoded += (char)(encoded[i] ^ gamma[i]);
						}
						out.println("decoded: "+decoded);
					}
					break;
				case "bb":
					return;
			}
		}
	}
}
