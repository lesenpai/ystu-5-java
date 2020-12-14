package lab2.p2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Lab2UIMain {
	public static void main(String[] args) {
		var form = new Lab2UIMainWindow();
		form.setTitle("Lab 2");
		form.pack();
		form.setSize(800, 400);
		form.setAlwaysOnTop(true);
		form.setAlwaysOnTop(false);
		form.setVisible(true);
	}
}
