
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//สร้างมาสำหรับค้นหาค่า String Start name//
public class Utils {
    
   
    public static void error(String title, String str) {
        JOptionPane.showMessageDialog(new JFrame(), str, title,
        JOptionPane.ERROR_MESSAGE);
    }
    public static void message( String str) {
        JOptionPane.showMessageDialog(new JFrame(), str);
    }

    
    public static boolean isStringOnlyAlphabet(String s) {
        return s.matches("^[a-zA-Z]*$");
    }
    public static Vertex findVertexFromString(ArrayList<Vertex> verxs,String name) {
        for(Vertex v : verxs)
            if(v.name.equals(name))
                 return v;
        
        return null;
    }
}
