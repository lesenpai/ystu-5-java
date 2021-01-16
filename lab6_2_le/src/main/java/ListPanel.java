import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class ListPanel extends JPanel {
    JList jList;
    JLabel jLabel;
    JScrollPane jscrlp;
    String colors[] = { "black", "green", "red",
        "yellow", "blue", "cyan", "pink" };

    Map<String, Color> colorMap = new HashMap<String, Color>() {{
        put("black", Color.BLACK);
        put("green", Color.GREEN);
        put("red", Color.RED);
        put("yellow", Color.YELLOW);
        put("blue", Color.BLUE);
        put("cyan", Color.CYAN);
        put("pink", Color.PINK);
    }};

    ListPanel() {
        jList = new JList(colors);
        jscrlp = new JScrollPane(jList);
        jscrlp.setPreferredSize(new Dimension(120, 100));
        jLabel = new JLabel("JLABLE");
        jList.addListSelectionListener(e -> {
            this.setBackground(colorMap.get(String.valueOf(jList.getSelectedValue())));
        });
        add(jscrlp);
    }
}
