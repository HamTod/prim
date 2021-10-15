
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vento
 */
public class Vertex {

    int x;
    int y;
    String name;
    int r; //รัศมี
    int shift;
    boolean isSelect;
    boolean isSelect2;

    int dist; /// add component
    String prev; /// add component
    boolean visited; /// add component

    int id;
    static int idGen = 0;
    int value;

    Vertex(int x, int y,int num) {
        this.id = idGen; 
        idGen++;
        this.r = 36;
        this.shift = 30;
        this.x = x;
        this.y = y;
        this.name = "V"+num;
        this.isSelect = false;
    }


    boolean inCircle(int x0, int y0) {
        return ((x0 - x) * (x0 - x) + (y0 - y) * (y0 - y)) <= r * r;
    }

    void draw(Graphics2D g) {
        g.setColor(isSelect ? Color.BLUE : Color.BLUE);
        g.setStroke(new BasicStroke(5));
        if (isSelect2 && GraphDrawing.isStartVertexName) {
            g.setColor(Color.ORANGE);
        }
        g.fillOval(x - r, y - r, r * 2, r * 2);

        g.setColor(Color.WHITE);
        g.fillOval(x - r + (r - shift) / 2, y - r + (r - shift) / 2, r * 2 - (r - shift), r * 2 - (r - shift));

        g.setColor(isSelect ? Color.red : Color.BLUE);
        Font f = new Font("sans-serif", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString(name, x - 10, y + 10);
        g.setColor(isSelect ? Color.BLUE : Color.BLUE);
        g.setStroke(new BasicStroke(1));

    }

    void drawDegree(Graphics2D g, int deegree) {
        g.setStroke(new BasicStroke(1));

    }

    void drawSimulateDijkstra(Graphics2D g) {
        if (GraphDrawing.isFindingShortestPath) {
            Font f = new Font("sans-serif", Font.PLAIN, 18);

            g.drawString(dist == Integer.MAX_VALUE ? "" : dist + "", x - 50, y - 75);

        }
    }

}
