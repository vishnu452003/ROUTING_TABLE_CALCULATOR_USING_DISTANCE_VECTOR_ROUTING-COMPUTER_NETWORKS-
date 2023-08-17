import java.io.*;
import java.net.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RTClient
{
    static int inpgraph[][];
    static int mid[][];
    static int rt[][];
    static int v;
    static int e;
    static int sn=1;
    public static void main(String args[]) throws IOException

    {
        JTextField t1,t2,t3;
        JLabel l1,l2,l8;
        JButton jb;
        JFrame f=new JFrame();
        l8= new JLabel("ROUTING TABLES (DISTANCE VECTOR ROUTING PROTOCOL)");
        l8.setBounds(800,20,500,30);
        l1=new JLabel("ENTER NO OF VERTICES:");
        l1.setBounds(90,50,250,25);
        t1=new JTextField();
        t1.setBounds(90,75,200,25);
        l2=new JLabel("ENTER NO OF EDGES:");
        l2.setBounds(420,50,250,25);
        t2=new JTextField();
        t2.setBounds(420,75,200,25);
        jb=new JButton("Enter");
        jb.setBounds(300,450,100,25);
        JTextArea ta=new JTextArea();
        JScrollPane TAS=new JScrollPane(ta);
        TAS.setBounds(750,50,600,700);
        JButton ls=new JButton("LINK_STATE");
        ls.setBounds(1355,750,120,25);
//create a table model
        JTable table=new JTable();
        Object[]columns={"source","destination","cost"};
        DefaultTableModel model=new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
//JTable color
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font=new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);
        JLabel l3=new JLabel("SOURCE:");
        l3.setBounds(90,190,200,25);
        JTextField src=new JTextField();

        src.setBounds(90,220,200,25);
        JLabel l4=new JLabel("DESTINATION:");
        l4.setBounds(420,190,200,25);
        JTextField dest=new JTextField();
        dest.setBounds(420,220,200,25);
        JLabel l5=new JLabel("COST:");
        l5.setBounds(250,290,200,25);
        JTextField ct=new JTextField();
        ct.setBounds(250,320,200,25);
        JButton btadd=new JButton("Add");
        JButton btdel=new JButton("delete");
        JButton btup=new JButton("update");
        btadd.setBounds(150,400,100,25);
        btdel.setBounds(300,400,100,25);
        btup.setBounds(450,400,100,25);
//create JScrollPane
        JScrollPane pane=new JScrollPane(table);
        pane.setBounds(90,500,600,200);
        f.setTitle("ROUTING TABLE CALCULATOR");
        f.add(l8);
        f.add(l1);
        f.add(l2);
        f.add(l3);
        f.add(l4);
        f.add(l5);
        f.add(TAS);
        f.add(ls);
        f.add(t1);
        f.add(t2);
        f.add(jb);
        f.add(pane);
        f.add(src);
        f.add(dest);
        f.add(ct);
        f.add(btadd);
        f.add(btdel);
        f.add(btup);

        Object row[]=new Object[3];
        ls.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent at)
            {
                String s=at.getActionCommand();
                if(s.contentEquals("LINK_STATE"))
                {

                    int inpgraph[][] = new int[][] {
                            { 0, 3, 0, 0, 0, 0, 0, 8, 0 },
                            { 3, 0, 8, 0, 0, 0, 0, 10, 0 },
                            { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                            { 0, 0, 7, 0, 10, 14, 0, 0, 0 },
                            { 0, 0, 0, 10, 0, 10, 0, 0, 0 },
                            { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
                            { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
                            { 8, 10, 0, 0, 0, 0, 1, 0, 7 },
                            { 0, 0, 2, 0, 0, 0, 6, 7, 0 }
                    }; //Initalizing the inpgraph
                    LSRouting T = new LSRouting(); //Instantiating the object.
                    String str = new String();
                    for (int m = 0; m < inpgraph.length; m++) {
                        str+=T.routingcal(inpgraph, m);
                    }
T.display(str);
                }}
        });
//add row
        btadd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ace)
            {
                row[0]=src.getText();
                row[1]=dest.getText();
                row[2]=ct.getText();
                model.addRow(row);

            }
        });
//delete row
        btdel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae2)
            {
                int i=table.getSelectedRow();
                if(i>=0)
                {
                    model.removeRow(i);
                }
                else
                {
                    System.out.println("Delete Error");
                }}
        });
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                int i=table.getSelectedRow();
                src.setText(model.getValueAt(i,0).toString());
                dest.setText(model.getValueAt(i,1).toString());
                ct.setText(model.getValueAt(i,2).toString());
            }
        });
//update row
        btup.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae3){
                int i=table.getSelectedRow();
                if(i>=0)
                {
                    model.setValueAt(src.getText(),i,0);
                    model.setValueAt(dest.getText(),i,1);
                    model.setValueAt(ct.getText(),i,2);
                }
                else
                {

                    System.out.println("Update Error");
                }}
        });
//after pressing enter
        jb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae3){
                String s1= t1.getText();
                String s2= t2.getText();
                v=Integer.parseInt(s1);
                e=Integer.parseInt(s2);
                inpgraph = new int[v][v];
                mid = new int[v][v];
                rt = new int[v][v];
                for(int i = 0; i < v; i++)
                    for(int j = 0; j < v; j++)
                    {
                        if(i == j)
                            inpgraph[i][j] = 0;
                        else
                            inpgraph[i][j] = 9999;
                    }
                try
                {
                    int x=0,y=0;
                    for(int i = 0; i < e; i++)
                    {
                        Object ob1=table.getModel().getValueAt(x,y);
                        int s = Integer.parseInt(ob1.toString());
                        s--;
                        Object ob2=table.getModel().getValueAt(x,y+1);
                        int d = Integer.parseInt(ob2.toString());
                        d--;
                        Object ob3=table.getModel().getValueAt(x,y+2);
                        int c = Integer.parseInt(ob3.toString());
                        x=x+1;

                        inpgraph[s][d] = c;
                        inpgraph[d][s] = c;
                    }
                    Socket s=new Socket("localhost",2002);
                    System.out.println("Connection established with server..");
                    DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                    dout.writeUTF(t1.getText());
                    DataOutputStream dout1=new DataOutputStream(s.getOutputStream());
                    dout1.writeUTF(t2.getText());
                    ObjectOutputStream os=new ObjectOutputStream(s.getOutputStream());
                    os.writeObject(inpgraph);
                    ObjectInputStream is1=new ObjectInputStream(s.getInputStream());
                    rt=(int [][])is1.readObject();
                    ObjectInputStream is2=new ObjectInputStream(s.getInputStream());
                    mid=(int [][])is2.readObject();
                    s.close();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                String sb=new String();
                sb= print_textarea();
                ta.setText(sb);
            }
        });
        f.setSize(1500,1000);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    static String print_textarea()
    {
        StringBuilder str=new StringBuilder();
        for(int i = 0; i < v; i++)
        { str.append("ROUTING TABLE OF NODE"+(i+1)+"\n");

            str.append("\n"+"nodes"+"\t"+"Distance"+"\t"+"Next Hop"+"\n");
            for(int j = 0; j < v; j++)
            {
                str.append(""+(j+1)+"\t"+ rt[i][j] +"\t"+(mid[i][j]+1)+"\n");
            }
            str.append("\n");
        }
        return str.toString();
    }}