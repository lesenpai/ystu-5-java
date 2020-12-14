package lab1.p2;

import lab2.p2.Lab2UIMainWindow;

public class Lab1UIMain {
	public static void main(String[] args) {
		var form = new Lab2UIMainWindow();
		form.setTitle("Lab 1");
		form.pack();
		form.setSize(400, 400);
		form.setAlwaysOnTop(true);
		form.setAlwaysOnTop(false);
		form.setVisible(true);
	}
}
