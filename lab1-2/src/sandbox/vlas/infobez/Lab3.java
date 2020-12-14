package sandbox.vlas.infobez;

import static java.lang.System.out;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Lab3 {
	public static void main(String[] args) {
		final int offset = 97;
		//var in = new Scanner(System.in);
		//out.println("enter input:");
		//out.print(">> ");
		//var input = in.nextLine();
		var input = "hello";
		var inputChars = input.toCharArray();

		var inputNumbers = new int[input.length()];
		for (int i = 0; i < input.length(); i++) {
			inputNumbers[i] = (((int) inputChars[i] - offset));
		}
		out.printf("input numbers: %s\n", Arrays.toString(inputNumbers));

		int
			p = 113, q = 127,
			//p = 5, q = 23,
			n = p * q, f = (p - 1) * (q - 1);
		out.printf("n = %s\n", n);
		out.printf("f = %s\n", f);
		int e = 1;
		var fOrig = f;
		while (e != 0 && f != 0) {
			if (e > f)
				e = e % f;
			else f = f % e;
			if (f == 0) {
				f = ((p - 1) * (q - 1));
				e++;
			}
			else {
				//out.println(e);
				break;
			}
		}
		f = fOrig;

		//e=5;

		out.printf("e = %s\n", e);

		//int d = e + 1;
		int d=2;
		while ((d * e) % f != 1) {
			d++;
		}
		out.printf("d = %s\n", d);

		//var encoded = new int[input.length()];
		//var encoded = (int)Math.pow(31, 5)%n;
		//out.println(encoded);

		var encoded = new int[input.length()];
		for (int i=0; i<input.length(); i++) {
			encoded[i] = (int) Math.pow(inputNumbers[i], e) % n;
		}
		out.printf("encoded: %s\n", Arrays.toString(encoded));

		var decodedNumbers = new BigInteger[input.length()];
		for (int i=0; i<input.length(); i++) {
			//decoded += Math.pow(encoded[i], d) % n;
			var nBigInt = BigInteger.valueOf(n);
			var encdodedI = BigInteger.valueOf(encoded[i]);
			decodedNumbers[i] = (encdodedI.pow(d)).mod(nBigInt);
		}
		out.printf("decoded: %s\n", Arrays.toString(decodedNumbers));
		var decoded = "";
		for (int i=0; i<input.length(); i++) {
			decoded += (char)(decodedNumbers[i].intValue()+offset);
		}
		out.printf("decoded: %s\n", decoded);
	}
}
