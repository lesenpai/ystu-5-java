import java.awt.*;
import java.applet.*;
import java.util.*;

public class MyApplet extends Applet {
    private DiagramDrawer diagramDrawer;

    Calendar calendar = Calendar.getInstance();

    class RectDrawer {
        public int intPercent;
        public Color color;

        public RectDrawer(int intPercent, Color color) {
            System.out.println("/ intPercent: "+intPercent);
            this.intPercent = intPercent;
            this.color = color;
        }

        public void draw(Graphics2D g2d, int x, int y, int w, int outHeight) {
            int h = (int)(outHeight * intPercent / 100);
            g2d.setColor(this.color);
            g2d.fillRect(x, y, w, h);

            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, w, h);

            g2d.drawString((int)(intPercent)+"%", x+(w/2)-10, y+(h/2));
        }
    }

    class DiagramDrawer {
        public RectDrawer[] rectDrawers;

        public void draw(Graphics2D g2d, int x, int y, int outWidth, int outHeight) {
            int w = (int)(outWidth * 0.8), h = (int)(outHeight);
            int topHLineHeight = 6;
            g2d.setColor(Color.black);
            g2d.fillRect(x, y, w, topHLineHeight);

            int spaceBetweenRects = 15;
            int rectX = 20;
            int rectY = y + topHLineHeight;
            int rectW = (int)(w / rectDrawers.length) - spaceBetweenRects;

            for (RectDrawer rectDrawer : rectDrawers) {
                rectDrawer.draw(g2d, rectX, rectY, rectW, h);
                rectX += rectW + spaceBetweenRects;
            }
        }
    }

    Color[] colors = {
        Color.red,
        Color.orange,
        Color.yellow,
        Color.green,
        Color.blue
    };

    String imageParam;
    //AudioClip auClip;
    Image myImage;

    public void init() {
        resize(800, 600);
		imageParam = getParameter("image");
		myImage = getImage(getDocumentBase(), imageParam);
		//String sound = getParameter("sound");
		//auClip = this.getAudioClip(getDocumentBase(),sound);
        StringTokenizer stk = new StringTokenizer(getParameter("data"), ",");

        this.diagramDrawer = new DiagramDrawer();
        diagramDrawer.rectDrawers = new RectDrawer[stk.countTokens()];

        for (int i = 0; stk.hasMoreTokens(); i++) {
            String strValue = stk.nextToken();
            System.out.println(strValue);
            int intPercent = Integer.parseInt(strValue);
            Color color = colors[i % colors.length];
            diagramDrawer.rectDrawers[i] = new RectDrawer(intPercent, color);
        }
    }

    public void start() {
        //auClip.loop();
    }

    public void stop() {
        //auClip.stop();
    }

    public void paint(Graphics _g) {
        Graphics2D g2d = (Graphics2D)_g;
        int w = getSize().width, h = getSize().height;
        Font font = new Font("serif", Font.PLAIN, 14);
        g2d.setFont(font);

        int x = 10, y = 20;
        g2d.drawString("Date: "+ calendar.get(Calendar.DATE)+"."+ calendar.get(Calendar.MONTH)+"."+ calendar.get(Calendar.YEAR), x, y);

        y = 40;
        String student = "Yulia Shuleva";
        String group = "EIS-36";
        g2d.drawString(student, x, y);

        x = 200;
        y = 20;
        g2d.drawImage(myImage, x, y, 100, 120, this);

        x = 10;
        y = 150;
        this.diagramDrawer.draw(g2d, x, y, w, h-y);
    }
}

