import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class FigureMovePanel extends JPanel implements KeyListener {

	int
		ptX = 100, ptY = 100,
		pt1X = 0, pt1Y = -60,
		pt2X = 40, pt2Y = 50,
		pt3X = -60, pt3Y = -20,
		pt4X = 60, pt4Y = -20,
		pt5X = -40, pt5Y = 50;

	FigureMovePanel() {
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		addKeyListener(this);
		setFocusable(true);
	}

	public void keyTyped(KeyEvent evt) {
	}

	public void keyPressed(KeyEvent evt) {
		int step = 10;
		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			ptX -= step;
			repaint();
		}
		else if (key == KeyEvent.VK_RIGHT) {
			ptX += step;
			repaint();
		}
		else if (key == KeyEvent.VK_UP) {
			ptY -= step;
			repaint();
		}
		else if (key == KeyEvent.VK_DOWN) {
			ptY += step;
			repaint();
		}
	}

	public void keyReleased(KeyEvent evt) {
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawLine(pt1X+ptX, pt1Y+ptY, pt2X+ptX, pt2Y+ptY);
		g.drawLine(pt2X+ptX, pt2Y+ptY, pt3X+ptX, pt3Y+ptY);
		g.drawLine(pt3X+ptX, pt3Y+ptY, pt4X+ptX, pt4Y+ptY);
		g.drawLine(pt4X+ptX, pt4Y+ptY, pt5X+ptX, pt5Y+ptY);
		g.drawLine(pt5X+ptX, pt5Y+ptY, pt1X+ptX, pt1Y+ptY);
	}
}
  