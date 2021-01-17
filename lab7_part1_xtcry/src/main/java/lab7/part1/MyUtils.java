package lab7.part1;

import java.awt.Component;
import javax.swing.JOptionPane;

public class MyUtils {
	public static void showInfoMessage(Component parent, String msg) {
		JOptionPane.showMessageDialog(parent, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showErrorMessage(Component parent, String msg) {
		JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
