
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.print.DocFlavor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author vento
 */
public class GraphDrawing extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    static boolean isStartVertexName;
    static boolean isFindingShortestPath;
    String data[][];
    String column[] = {"N"};
    String dataMST[];
    ArrayList<Vertex> A = new ArrayList<>();
    ArrayList<Vertex> N = new ArrayList<>();
    ArrayList<String> columnArrayList = new ArrayList<>();
    ArrayList<Edge_> T = new ArrayList<>();
    //------add new-------
    boolean delete_Zero_Degree;
    boolean isChangedName;
    int num = 0;
    //Data of graph
    ArrayList<Vertex> Vertexs = new ArrayList<>();
    ArrayList<Edge_> Edge_s = new ArrayList<>();

    Object selected = null;
    TempEdge TempEdge = null; //TempEdge edge

    //UI 
    Canvas c;
    String mode = "Vertex"; //Vertex,Edge_

    //set font 
    Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 24);

    //find size monitor
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JTextField text_start = new JTextField();
    JFrame frameHelp = new JFrame("Help");
    JFrame settings = new JFrame("Setting");
    JPanel boxSave = new JPanel();
    JPanel boxOpen = new JPanel();
    JPanel boxHelp = new JPanel();
    int num1 = 0;
    JButton saveButt = new JButton();
    JButton openButt = new JButton();
    JButton helpButt = new JButton();
    JButton clearButt = new JButton();
    JFileChooser pathSave = new JFileChooser();
    JFileChooser pathOpen = new JFileChooser();



    JLabel helpString = new JLabel();

    JTable table = new JTable() {
        public boolean isCellEditable(int nRow, int nCol) {
            return false;
        }
    };
    //add new scrollbar
    int scroll_vertical = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int scroll_horizontal = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
    JScrollPane scroll_panel = new JScrollPane(table, scroll_vertical, scroll_horizontal);
    //----add new---- http://code.function.in.th/java-swing/jscrollpaneconstants/HORIZONTAL_SCROLLBAR_ALWAYS  
    //add new  Rename a button nextBuut ---> algorithmButt
    JButton algorithmButt = new JButton();
    //------add new-------
    JButton resetButt = new JButton();

    //----- Menu bar
    JPanel menubar = new JPanel();
    int shift = 50;

    //////////////////////////////// Backup ////////////////////////////////
    class Backup {

        ArrayList<Vertex> VertexsBackup;
        ArrayList<Edge_> Edge_sBackup;

        public Backup() {
            this.VertexsBackup = Vertexs;
            this.Edge_sBackup = Edge_s;
        }

    }

    GraphDrawing() {

        super("PRIMMY");
        this.data = new String[Vertexs.size()][Vertexs.size()];

        // create a empty canvas 
        c = new Canvas() {
            @Override
            public void paint(Graphics g) {
            }
        };
        c.setBackground(Color.white);

        // add mouse listener 
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        // add keyboard listener 
        c.addKeyListener(this);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        Font f = new Font("sans-serif", Font.PLAIN, 18);

        saveButt.setFont(sanSerifFont);
        openButt.setFont(sanSerifFont);
        helpButt.setFont(sanSerifFont);
        clearButt.setFont(sanSerifFont);

        helpString.setFont(sanSerifFont);

        boxSave.setBackground(Color.white);
        boxOpen.setBackground(Color.PINK);

        boxHelp.setBackground(Color.white);
        frameHelp.add(boxHelp);
        //add new "table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF)" Make the scrollbar scrollable
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //----add new---- https://stackoverflow.com/questions/25569770/horizontal-scrollbar-is-not-working-with-jtable-in-java-swing
        DefaultTableModel contactTableModel = (DefaultTableModel) table
                .getModel();
        contactTableModel.setColumnIdentifiers(column);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scroll_panel.setBounds((screenSize.width - getWidth()) - 430 + shift, (int) (screenSize.height * 0.20), 300, 300);
        scroll_panel.setSize(350, 400);
        getContentPane().add(scroll_panel);
        Font fontTextField = new Font("SansSerif", Font.CENTER_BASELINE, 20);
        text_start.setBounds((screenSize.width - getWidth()) - 320 + shift, 50, 150, 60);
        text_start.setHorizontalAlignment(JTextField.CENTER);
        text_start.setFont(fontTextField);
        getContentPane().add(text_start);
        //button
        saveButt.setText("save");

        saveButt.setForeground(Color.BLACK);
        saveButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 785, 150, 70);
        getContentPane().add(saveButt);
        saveButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        openButt.setText("open");
        openButt.setForeground(Color.BLACK);

        openButt.setBounds((screenSize.width - getWidth()) - 400 + 150 + shift, 785, 150, 70);
        getContentPane().add(openButt);
        openButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
// เพิ่ม ปุ่ม Clear สำหรับ เครียกราฟทั้งหมดและข้อมูลเข้าสู่ค่าเริ่มต้นของโปรแกรม//
        clearButt.setText("Clear");
        clearButt.setForeground(Color.BLACK);

        clearButt.setBounds((screenSize.width - getWidth()) - 420 + 50 + shift, 620, 250, 40);
        getContentPane().add(clearButt);
        clearButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
                //เมื่อกด Clear สำเร็จจะขึ้นวว่า Clear Success!//
                Utils.message("Clear Success!");
            }
        });
//เมื่อต้องการเริ่มต้นสำหรับค้นหาเส้นทาง Prim Tree ให้กด Ok// 
        //add new Button operation
        algorithmButt.setText("OK");
        //------add new-------
        algorithmButt.setForeground(Color.BLACK);

        //add new Correct the size and move the position form ((screenSize.width - getWidth()) - 400 + 150 + shift, 150, 150, 23)
        algorithmButt.setBounds((screenSize.width - getWidth()) - 420 + 50 + shift, (int) (screenSize.height * 0.15), 250, 40);
        //------add new-------
        getContentPane().add(algorithmButt);
        algorithmButt.addActionListener(new ActionListener() {
            Vertex chooseVertex = null;

            public void actionPerformed(ActionEvent e) {
                for (Edge_ s : Edge_s) {
                    //ถ้าน้ำหนักของเส้นทางไม่ครบจะแจ้งเตือน//
                    if (s.weight.length() == 0) {
                        Utils.error("Error!", "กรุณาใส่น้ำหนักของเส้นทางให้ครบถ้วน"
                                + "กรุณาใส่น้ำหนักเส้นทางให้ครบ");
                        return;
                    }
                    //ถ้าน้ำหนักของเส้นน้อยกว่า 1 จะแจ้งเตือน//
                    if (Integer.parseInt(s.weight) <= 0) {
                        Utils.error("Error!", "กรุณาใส่น้ำหนักของเส้นทางที่มีค่ามากกว่า 0"
                        );
                        return;
                    }

                }
                if (Vertexs.isEmpty()) {

                    //ถ้าเส้นตำแหน่งไม่มีเส้นทาง
                    Utils.error("Error!", "\nSorry!, กรุณาเพิ่มตำแหน่งของเส้นทาง! \n");
                    return;
                }

                for (Vertex v : Vertexs) {
                    //ถ้าชื่อ vertex ไม่ครบจะแจ้งเตือน//
                    if (v.name.length() == 0) {
                        Utils.error("Error!", " กรุณากรอกชื่อ Vertex ให้ครบถ้วน!\n");
                        columnArrayList.clear();
                        return;
                    } else {
                        columnArrayList.add(v.name);
                    }
                }
                //add new Check the weight of edge
                if (Edge_s.isEmpty()) {
                    Utils.error("Error!", "\nSorry!,ตำแหน่งของเส้นทางต้องมีมากกว่า 1\n");
                    return;
                }
                // เมื่อทำการกด Ok จะเริ่มต้นการคำนวนและปุ่มจะเปลี่ยนเป็น Next//
                algorithmButt.setText("Next");
                algorithmButt.setBounds((screenSize.width - getWidth()) - 450 + 2000 + shift, (int) (screenSize.height * 0.15), 250, 40);
                dataMST = new String[Vertexs.size() + 1];

                Vertex startVertexName = null;
                //------add new-------

                startVertexName = Utils.findVertexFromString(Vertexs, text_start.getText());
                if (!isStartVertexName) {
                    for (int i = 0; i < columnArrayList.size(); i++) {
                        contactTableModel.addColumn(columnArrayList.get(i));
                    }

                    if (startVertexName == null) {
                        Utils.error("Error!", "\nกรุณาใส่ตำแหน่งที่ต้องการค้นหา \n");
                        return;
                    }
                    if (startVertexName == null) {
                        Utils.error("Error!", "ไม่พบชื่อตำแหน่งที่คุณใส่ กรุณากรอกใหม่");
                        return;
                    }
                    for (Vertex v : Vertexs) { //set value and prev to default
                        v.value = Integer.MAX_VALUE;

                    }
                    N.add(startVertexName);
                    isStartVertexName = true;
                    algorithmButt.setText("Next");
                    algorithmButt.setBounds((screenSize.width - getWidth()) - 450 + 2000 + shift, (int) (screenSize.height * 0.15), 250, 40);
                    resetButt.show();

                    chooseVertex = calprim(startVertexName.name);
                } else {

                    if (chooseVertex != null) {
                        chooseVertex = calprim(chooseVertex.name);
                    } else {
                        // เมื่อโปรแกรมทำงานเสร็จสิ้นจะขึ้น Finish//
                        Utils.message("Finish!");

                        algorithmButt.hide();
                    }
                }

            }
        }
        );
// reset สำหรับ reset ค่าในการคำนวนแต่จะไม่ reset ค่าในตาราง//
        resetButt.setText(
                "Reset");
        resetButt.setForeground(Color.BLACK);

        resetButt.hide();

        resetButt.setBounds(
                (screenSize.width - getWidth()) - 320 + shift, (int) (screenSize.height
                * 0.12), 150, 23);
        getContentPane()
                .add(resetButt);
        resetButt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                resetButtAction();
            }
        });
//ปุ่มสำหรับกดเพื่อช่วยเหลือ//
        helpButt.setText("Help");

        helpButt.setForeground(Color.BLACK);
        helpButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 875, 300, 23);
        getContentPane().add(helpButt);
        helpButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpButtAction(e);
            }
        });

        //-----###e     
        menubar.setBackground(Color.PINK);
        menubar.setBounds((screenSize.width - getWidth()) - 400, 0, 400, (screenSize.height - getHeight()));
        c.setBounds(0, 0, (screenSize.width - getWidth()) - 400, (screenSize.height - getHeight()));
        setBounds(0, 0, (screenSize.width - getWidth()), (screenSize.height - getHeight()));
        //setUndecorated(true);
        //setVisible(true);
        add(c);
        add(menubar);
        // setSize(1500, 1000);

        show();

    }

    //add new calprim สำหรับคำนวนโดย ทฤษฏีของ Prim// 
    Vertex calprim(String startVertexName) {

        // จำนวนของ vertex ทั้งหมด
        int n = Vertexs.size();

// สร้าง utils สำหรับเก็บข้อมูลเริ่มต้นในตำแหน่งของกราฟ เพื่อนำไปใช้ในการค้นหา//
        Vertex startVertex = Utils.findVertexFromString(Vertexs, startVertexName);

        // startVetex is null or not encounter
        for (Vertex v : Vertexs) {
            if (v == startVertex) {
                continue;
            }
            A.add(v);
        }

        Vertex chooseVertex = startVertex; // เลือก vertex สำหรับเริ่มต้น
// เก็บชื่อ ของ vertex ที่เลือก//
        dataMST[0] = chooseVertex.name + " ";

  
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

   
        if (N.size() < n) { // loop
            int min = Integer.MAX_VALUE;
            Vertex next_chooseVertex = null;
            for (int i = 0; i < Edge_s.size(); ++i) {
                Edge_ e = Edge_s.get(i); // Edge
                if (e.vertexA != chooseVertex && e.vertexB != chooseVertex) {
                    continue;
                }
                Vertex v = (e.vertexA == chooseVertex) ? e.vertexB : e.vertexA;
                if (N.contains(v)) {
                    continue;
                }
                int weight = Integer.parseInt(e.weight);

                if (weight < v.value) {
                    v.value = weight;
                    v.prev = chooseVertex.name;
                }
            }
            chooseVertex.isSelect2 = true;
            //ค้นหาค่าน้ำหนักน้อยที่สุด ใน  vertex ต่อไป//
            for (Vertex v : A) {
                if (v.value <= min && !N.contains(v)) {
                    min = v.value;
                    next_chooseVertex = v;
                }
            }
            for (int i = 0; i < Edge_s.size(); ++i) {
                Edge_ e = Edge_s.get(i);
                if (Integer.parseInt(e.weight) == min && e.vertexA == chooseVertex
                        && !N.contains(e.vertexB)) {
                    Edge_s.get(i).isSelect2 = true;
                    //T.add(e); // TO DO T                    
                } else if (Integer.parseInt(e.weight) == min && e.vertexB == chooseVertex
                        && !N.contains(e.vertexA)) {
                    Edge_s.get(i).isSelect2 = true;
                    //T.add(e); // TO DO T
                } else if (Integer.parseInt(e.weight) == min && e.vertexA.name == next_chooseVertex.prev
                        && e.vertexB == next_chooseVertex) {
                    Edge_s.get(i).isSelect2 = true;
                    //T.add(e); // TO DO T
                } else if (Integer.parseInt(e.weight) == min && e.vertexB.name == next_chooseVertex.prev
                        && e.vertexA == next_chooseVertex) {
                    Edge_s.get(i).isSelect2 = true;
                    //T.add(e); // TO DO T
                }
            }

            // ผลลัพธิ์ออกมาเป็น String//
            for (int i = 0; i < n; ++i) {
                StringBuilder result = new StringBuilder();
                if (N.contains(Vertexs.get(i))) {
                    result.append("-");
                    Vertexs.get(i).value = Integer.MAX_VALUE;
                } else {
                    result.append((Vertexs.get(i).value == Integer.MAX_VALUE ? "∞" : Vertexs.get(i).value + "," + Vertexs.get(i).prev));
                }
                result.append(i < n - 1 ? " " : "");
                dataMST[i + 1] = result.toString();
            }
           
            tableModel.addRow(dataMST);
            next_chooseVertex.isSelect2 = true;
            if (next_chooseVertex == null) {
                return null;
            }
            for (String i : dataMST) {
                System.out.print(i + "");
            }
            System.out.println("");
            N.add(next_chooseVertex);
            A.remove(next_chooseVertex);
            dataMST[0] = next_chooseVertex.name;
            return chooseVertex = next_chooseVertex;

        }
      
        return null;


    }

//----Reset สำหรับเริ่มต้นค่าใหม่ในการคำนวนกราฟเดิม----
    void resetButtAction() {
        if (isStartVertexName) {
            algorithmButt.setText("Find!");
            algorithmButt.show();

            resetButt.hide();
            Q.clear();
            target = null;

            //un select
            selected = null;
            for (Vertex v : Vertexs) {
                v.isSelect = false;
                v.isSelect2 = false;
            }
            for (Edge_ e1 : Edge_s) {
                e1.isSelect = false;
                e1.isSelect2 = false;
            }
            isStartVertexName = false;
            dataMST = null;
            N.clear();
            A.clear();
            T.clear();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);
        }
        draw();
    }

    
    //สำหรับ hilight เส้น//
    void lightLineShortestPath(Vertex target
    ) {
        Vertex s = Utils.findVertexFromString(Vertexs, target.prev); //prev

        
   
        if (target == s|| target.dist==0) {

            return;
        }     
       
        target.isSelect = true;
        for (Edge_ e : Edge_s) {
            if (e.vertexA == s && e.vertexB == target
                    || e.vertexA == target && e.vertexB == s) {
                e.isSelect = true;
            }

        }
        if (s == null) {
        
            return;
        }
        s.isSelect = true;

        lightLineShortestPath(s);

    }

    void helpButtAction(ActionEvent e
    ) {
        String help = "<html>";
        help += "Double click for create Vertex<br>";
        help += "Click on Vertex then type for rename<br>";
        help += "Click on Vertex or center of edge then it is blue you can edit etc move , rename , delete <br>";
        help += "Click on Vertex then press delete for remove Vertex<br>";
        help += "Press and hold spacebar with drag mouse for create edge<br>";
        help += "Click on character on edge then type for rename<br>";
        help += "Click on character on edge then drag mouse for move edge<br>";
        help += "Click on character on edge then press delete for remove edge<br>";
        help += "Press Button save for save Graph on canvas to json file<br>";
        help += "Press Button open for open Graph json file to canvas<br>";
        help += "Press Button clear to clear the canvas<br>";
        help += "Press find shortest path for simulate the shortest path by dijkstra's algorithm<br>";
        help += "Press ESC on the keyboard to unselect all the vertexs and edges<br>";
        helpString.setText(help);
        boxHelp.add(helpString);
        boxHelp.setAutoscrolls(true);

        //frame help center
        frameHelp.setBounds(screenSize.width / 2 - 500, screenSize.height / 11, 1000, 400);
        frameHelp.setSize((int) (screenSize.width * 0.60), screenSize.height - 300);
        frameHelp.setAlwaysOnTop(true);
        frameHelp.setVisible(true);

    }

    // Queue set
    ArrayList<Vertex> Q = new ArrayList<>();
    Vertex target = null;

    public void findShortestPathNext(Vertex source, Vertex target) {
        Q.clear();
        this.target = target;
        // init dist(v) and prev(v) to null;
        for (Vertex v : Vertexs) {
            if (v != source) {
                v.prev = null;
                v.dist = Integer.MAX_VALUE; // infinity
                v.visited = false;
            }

        }
        //initialize
        source.dist = 0; // start point equalto 0
        source.visited = true; // visited

        Q.add(source);

        //update
        draw();

    }

    public boolean hasNext() {

        while (!Q.isEmpty()) {
            Vertex u = getNearNeighbour();

            Q.remove(u);
            if (u == target) {
                break;
            }

            for (Edge_ e : Edge_s) {
                if (e.vertexA != u && e.vertexB != u) {
                    continue;
                }
                Vertex v = (e.vertexA == u) ? e.vertexB : e.vertexA;
                int sum = u.dist + (Integer.parseInt(e.weight));

                if (sum < v.dist) {
                    v.dist = sum;
                    v.prev = u.name;
                    if (!Q.contains(v)) {
                        Q.add(v);
                    }
                }
                //select
                //e.isSelect = true;

            }

            u.visited = true;

            draw();
            break;
        }
        return !Q.isEmpty();
    }

    public Vertex getNearNeighbour() {
        int min = Integer.MAX_VALUE;
        Vertex u = null;
        for (int i = 0; i < Q.size(); i++) {
            Vertex v = Q.get(i);

            if (v.dist < min) {
                u = v;
                min = v.dist;
            }

        }
        return u;
    }

    void saveButtAction(ActionEvent e) throws IOException {
        pathSave.setBounds(60, 120, 750, 450);
        boxSave.add(pathSave);

        int ret = pathSave.showDialog(null, "save");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathSave.getSelectedFile();
            path = filePath.getPath();
            save(path);
        }
    }

    void openButtAction(ActionEvent e) throws IOException {
        pathOpen.setBounds(60, 120, 750, 450);
        boxOpen.add(pathOpen);

        int ret = pathOpen.showDialog(null, "open");
        String path = "";

        resetButtAction();
        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathOpen.getSelectedFile();
            path = filePath.getPath();
            open(path);
            draw();
        }
    }

    public void save(String path) throws IOException {

        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.setPrettyPrinting().create();

        FileWriter writer = new FileWriter(path);

        Backup backup = new Backup();
        writer.write(gson.toJson(backup));
        writer.close();
    }

    public void open(String path) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Backup backup = gson.fromJson(bufferedReader, Backup.class
        );

        Vertexs = backup.VertexsBackup;
        Edge_s = backup.Edge_sBackup;

        //bind object reference
        for (Edge_ e : Edge_s) {
            if (e.vertexA != null) {
                int id = e.vertexA.id;
                for (Vertex v : Vertexs) {
                    if (v.id == id) {
                        e.vertexA = v;
                        break;
                    }
                }
            }
            if (e.vertexB != null) {
                int id = e.vertexB.id;
                for (Vertex v : Vertexs) {
                    if (v.id == id) {
                        e.vertexB = v;
                        break;
                    }
                }
            }
        }
    }

//set canvas is white
    public void clear() {
        Vertexs.clear();
        Edge_s.clear();
        degree.clear();
        //add new Remove vector array lists and tables
        columnArrayList.clear();
        DefaultTableModel model_info = (DefaultTableModel) table.getModel();
        model_info.setColumnCount(0);
        model_info.setColumnIdentifiers(column);
        isStartVertexName = false;
        dataMST = null;
        N.clear();
        A.clear();
        T.clear();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        //-------add new-----
        resetButtAction();
        selected(-1, -1);
        draw();
    }

    public void selected(int x, int y) {
        Object obj = null;
        for (Vertex s : Vertexs) {
            if (s.inCircle(x, y)) {
                s.isSelect = true;
                obj = s;
                break;
            }
        }
        if (obj == null) {
            for (Edge_ t : Edge_s) {
                if (t.inLine(x, y)) {
                    t.isSelect = true;
                    obj = t;
                    break;
                }
            }
        }
        if (obj == null) {
            if (selected == null) {
                return;
            } else {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    s.isSelect = false;
                } else {
                    Edge_ t = (Edge_) selected;
                    t.isSelect = false;
                }
                selected = null;
            }
        } else {
            if (selected == null) {
                selected = obj;
            } else {
                if (obj == selected) {
                    return;
                } else {
                    if (selected instanceof Vertex) {
                        Vertex s = (Vertex) selected;
                        s.isSelect = false;
                    } else {
                        Edge_ t = (Edge_) selected;
                        t.isSelect = false;
                    }
                    selected = obj;
                }
            }
        }
        if (selected == null) {
            unSelectAll();
        }
    }
    BufferedImage grid = null;

    public void draw() {

        Graphics2D g = (Graphics2D) c.getGraphics();

        g.setFont(sanSerifFont);

        if (grid == null) {
            grid = (BufferedImage) createImage(c.getWidth(), c.getHeight());
        }

        Graphics2D g2 = grid.createGraphics();

        g2.setColor(Color.white);
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (Edge_ t : Edge_s) {
            t.draw(g2);
        }

        if (TempEdge != null) {
            TempEdge.line(g2);
        }
        String[] str = new String[Vertexs.size()];
        int i = 0;
        for (Vertex s : Vertexs) {
            s.draw(g2);
            str[i++] = s.name;
        }

        for (Edge_ t : Edge_s) {

        }

        
        updateDegree(g2);

        g.drawImage(grid, null, 0, 0);

    }

    boolean unSelectAll() {

        selected = null;
        for (Vertex v : Vertexs) {
            v.isSelect = false;
        }
        for (Edge_ e : Edge_s) {
            e.isSelect = false;
        }

        if (!isChangedName) {
            return true;
        }

        Vertex dup = null;
        for (Vertex v : Vertexs) {
            if (v.name.length() < 1) {
                continue;
            }

            for (int i = Vertexs.size() - 1; i >= 0; --i) {
                if (v == Vertexs.get(i)) {
                    continue;
                }
                if (v.name.length() < 1) {
                    continue;
                }

                if (v.name.equals(Vertexs.get(i).name)) {
                    dup = v;
                    break;
                }
            }
        }

        if (dup != null) {
            selected = dup;
            dup.isSelect = true;
            Utils.error("Duplicate name", "Duplicate name at \" " + dup.name + " \"");
        } else {
            isChangedName = true;
        }
        return false;
    }
////////////////////////////////  UI EVENT  ////////////////////////////////
    // 3.1 mouse listener and mouse motion listener mehods 
    // keyboard listener and keyboard motion listener mehods 

    @Override
    public void keyTyped(KeyEvent ke) {

        //System.out.println("key " + ke.getKeyChar() + " = " + (int) ke.getKeyChar());
        if (isFindingShortestPath) {
            return;
        }
        if ((int) ke.getKeyChar() == 27) {
            if (unSelectAll()) {
                return;
            }
        } else if ((int) ke.getKeyChar() == 19) {
            try {
                //ctrl + S
                save("backup.json");
            } catch (IOException ex) {

            }
        } else if ((int) ke.getKeyChar() == 15) {
            try {
                //ctrl + O 
                open("backup.json");
            } catch (IOException ex) {

            }
        } else if ((int) ke.getKeyChar() == 14) {
            //ctrl + N 

        } else if ((int) ke.getKeyChar() == 12) {
            //ctrl + L

        } else if ((int) ke.getKeyChar() == 18) {
            //ctrl + R 

        } else if ((int) ke.getKeyChar() == 9) {

        } else if ((int) ke.getKeyChar() == 1) {
            //ctrl + A 

        }

        if (selected instanceof Vertex) {

            Vertex s = (Vertex) selected;

            int status = (int) ke.getKeyChar();
            if (status == 8) { //delete

                if (s.name.length() > 1) {
                    String newName = s.name.substring(0, s.name.length() - 1).trim();
                    s.name = newName;
                    isChangedName = true;
                } else {
                    s.name = "".trim();
                }

            } else if (status == 127) { // space

                removeVertex((Vertex) selected, delete_Zero_Degree);
                unSelectAll();

            } else {
                if (ke.getKeyChar() != 27 || ke.getKeyChar() != 36) {
                    if (Utils.isStringOnlyAlphabet(ke.getKeyChar() + "")) {
                        String newName = (s.name + ke.getKeyChar()).trim();
                        isChangedName = true;
                        s.name = newName;
                    }
                }

            }

        } else if (selected instanceof Edge_) {
            Edge_ t = (Edge_) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) {

                if (t.weight.length() > 1) {
                    t.weight = t.weight.substring(0, t.weight.length() - 1).trim();
                } else {
                    t.weight = "".trim();
                }
            } else if (status == 127) {

                removeEdge((Edge_) selected, delete_Zero_Degree);
                unSelectAll();

            } else {
                if (ke.getKeyChar() == ' ') {
                    return;
                }
                int key = (int) ke.getKeyChar();

                // Add error message Only numbs
                if (key < 48 || key > 57) {
                    Utils.error("Input invalid", "\n  That character is not number!  \n");
                    return;
                }
                t.weight += ke.getKeyChar();
                t.weight = t.weight.trim();
            }

        }
        draw();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if ((int) ke.getKeyChar() == 32) {
            //press space bar
            mode = "Edge_";
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //release space bar
        mode = "Vertex";
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isFindingShortestPath) {
            return;
        }
        int x = e.getX();
        int y = e.getY();
        selected(x, y);
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            if (!Vertexs.contains(selected)) {
                Vertex TempVertex = new Vertex(x, y, num);
                Vertexs.add(TempVertex);
                selected(x, y);
                num++;
            }

        }
        draw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isFindingShortestPath) {
            return;
        }
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Vertex")) {
            if (selected != null) {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    for (Edge_ t : Edge_s) {
                        if (t.vertexA == s || t.vertexB == s) {
                            int difx = x - s.x;
                            int dify = y - s.y;
                            if (t.vertexA != t.vertexB) {
                                if (t.vertexA != null) {
                                    t.x_center = (t.vertexA.x + t.vertexB.x) / 2;
                                    t.y_center = (t.vertexA.y + t.vertexB.y) / 2;
                                }
                            } else {
                                t.x_center += difx;
                                t.y_center += dify;
                            }

                        }
                    }
                    s.x = x;
                    s.y = y;
                } else {
                    Edge_ t = (Edge_) selected;
                    t.x_center = x;
                    t.y_center = y;
                }
            }

        } else if (mode.equals("Edge_")) {
            try {
                Vertex Vertex = null;
                for (Vertex s : Vertexs) {
                    if (s.inCircle(x, y)) {
                        Vertex = s;
                    }
                }
                if (Vertex != null) {
                    if (Vertex != TempEdge.vertexA) {
                        double angle = Math.atan2(TempEdge.vertexA.y - Vertex.y, TempEdge.vertexA.x - Vertex.x);
                        double dx = Math.cos(angle);
                        double dy = Math.sin(angle);
                        TempEdge.x1 = Vertex.x + (int) (Vertex.r * dx);
                        TempEdge.y1 = Vertex.y + (int) (Vertex.r * dy);
                    } else {
                        TempEdge.x1 = x;
                        TempEdge.y1 = y;
                    }
                } else {
                    TempEdge.x1 = x;
                    TempEdge.y1 = y;
                }
            } catch (Exception ex) {

            }

        }

        draw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Vertex")) {
            TempEdge = null;
        } else if (mode.equals("Edge_")) {
            try {
                TempEdge.x1 = x;
                TempEdge.y1 = y;
                Vertex vertexB = null;
                for (Vertex s : Vertexs) {
                    if (s.inCircle(x, y)) {
                        TempEdge.x1 = s.x;
                        TempEdge.y1 = s.y;
                        vertexB = s;
                        Edge_ edge = new Edge_(TempEdge.vertexA, vertexB);

                        if (s != TempEdge.vertexA) {
                            edge.x_center = (TempEdge.vertexA.x + s.x) / 2;
                            edge.y_center = (TempEdge.vertexA.y + s.y) / 2;
                        } else {
                            double angle = Math.atan2(y - TempEdge.vertexA.y, x - TempEdge.vertexA.x);
                            double dx = Math.cos(angle);
                            double dy = Math.sin(angle);

                            int rc = 3 * TempEdge.vertexA.r;

                            edge.x_center = TempEdge.vertexA.x + (int) (dx * rc);
                            edge.y_center = TempEdge.vertexA.y + (int) (dy * rc);

                        }

                        Edge_s.add(edge);
                        break;
                    }
                }
                TempEdge = null;
            } catch (Exception ex) {
            }
        }
        draw();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Edge_")) {
            TempEdge = new TempEdge(x, y);
            for (Vertex s : Vertexs) {
                if (s.inCircle(x, y)) {
                    TempEdge.setA(s);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    ArrayList<Integer> degree = new ArrayList<>();

    //New 
    public void updateDegree(Graphics2D g) {

        g.setFont(sanSerifFont);
        degree.clear();

        for (int i = 0; i < Vertexs.size(); ++i) {
            int count = 0; // count edge_
            for (int j = 0; j < Edge_s.size(); ++j) {

                // Loop edge 
                if ((Vertexs.get(i).id == Edge_s.get(j).vertexA.id && Vertexs.get(i).id == Edge_s.get(j).vertexB.id)) {
                    count += 2;
                } // Normal edge
                else if (Vertexs.get(i).id == Edge_s.get(j).vertexA.id || Vertexs.get(i).id == Edge_s.get(j).vertexB.id) {
                    count++;
                }

            }
            Vertexs.get(i).id = i;
            degree.add(count); // add new degree at i
            Vertex v = new Vertex(Vertexs.get(i).x, Vertexs.get(i).y, num);
            v.drawDegree(g, degree.get(i));
        }

    }

    public void removeEdge(Edge_ edge, boolean deleteAB) {

        if (deleteAB) {
            Vertex a = edge.vertexA;
            Vertex b = edge.vertexB;
            if (degree.get(a.id) - 1 == 0) {
                Vertexs.remove(a);
            }
            if (degree.get(b.id) - 1 == 0) {
                Vertexs.remove(b);
            }
        }

        Edge_s.remove(edge);
    }

    public void removeVertex(Vertex vertex, boolean deleteAB) {
        ArrayList<Edge_> TempEdge = new ArrayList<>();
        for (Edge_ t : Edge_s) {
            if (t.vertexA == vertex || t.vertexB == vertex) {
                TempEdge.add(t);
            }
        }
        for (Edge_ t : TempEdge) {
            Edge_s.remove(t);
        }
        Vertexs.remove(vertex);
    }

    public static void main(String[] args) {
        try {
            GraphDrawing g = new GraphDrawing();
        } catch (Exception e) {

        }

    }
}
