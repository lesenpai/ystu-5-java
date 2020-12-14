package lab2.p2;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MyTable extends JTable {
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		if (!isRowSelected(row)) {
			c.setBackground(getRowBackground(row));
		}

		return c;
	}

	private Color getRowBackground(int row) {
		return (row % 2 == 0) ? Color.decode("#EDEDE2") : Color.WHITE;
	}
}
