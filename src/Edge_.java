
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

/**
 *
 * @author vento
 */
public class Edge_ {

    Vertex vertexA;
    Vertex vertexB;
    String weight;

    boolean isSelect;
    boolean isSelect2;

    int x_center;
    int y_center;
    int r_center;

    Edge_(Vertex a, Vertex b) {
        this.vertexA = a;
        this.vertexB = b;

        this.r_center = 50;

        this.weight = " "; //ต่าเริ่มต้น

        this.isSelect = false;
        if (a == null) {
            this.weight = null;
        }
    }

    boolean inLine(int x0, int y0) {
        return ((x0 - x_center) * (x0 - x_center) + (y0 - y_center) * (y0 - y_center)) <= r_center * r_center;
    }

    void draw(Graphics2D g) {
        g.setColor(isSelect ? Color.red : Color.black);
        if (isSelect2) {
            g.setColor(Color.RED);
        }
        g.setStroke(new BasicStroke(3));
        if (vertexA != vertexB) {
            g.draw(new QuadCurve2D.Float(vertexA.x, vertexA.y, x_center, y_center, vertexB.x, vertexB.y));
        } else {
            double angle = Math.atan2(y_center - vertexA.y, x_center - vertexA.x);
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);

            int rc = (int) (vertexA.r * Math.sqrt(2));
            int xloop = vertexA.x - vertexA.r + (int) (dx * rc);
            int yloop = vertexA.y - vertexA.r + (int) (dy * rc);

            g.drawArc(xloop, yloop, vertexA.r * 2, vertexA.r * 2, 0, 360);
        }
        Font f = new Font("sans-serif", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString(weight, x_center, y_center);
    }

}
