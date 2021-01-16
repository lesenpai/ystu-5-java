import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FigureDragPanel extends JPanel implements MouseMotionListener {
	int SQUARE_SIZE = 100;
	int dx = 0;
	int dy = 0;
	int imX = 0;
	int imY = 0;

	FigureDragPanel() {
		addMouseMotionListener(this);
		setFocusable(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(imX + dx, imY + dy, SQUARE_SIZE, SQUARE_SIZE);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		imX = e.getX();
		imY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}