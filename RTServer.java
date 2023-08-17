import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class RTServer
{
    static int v;
    static int e;
    static int[][] inpgraph;
    static int[][] mid;
    static int[][] rt;

    public static void main(String[] args) throws Exception
    {

        while(true)
        {
            ServerSocket ss=new ServerSocket(2002);
            System.out.println("Socket is successfully created with port number and listening.....");
            Socket s=ss.accept();
            DataInputStream din1=new DataInputStream(s.getInputStream());
            v = Integer.parseInt(din1.readUTF());
            DataInputStream din2=new DataInputStream(s.getInputStream());
            e = Integer.parseInt(din2.readUTF());
            inpgraph = new int[v][v];
            mid = new int[v][v];
            rt = new int[v][v];
            ObjectInputStream is=new ObjectInputStream(s.getInputStream());
            inpgraph=(int [][])is.readObject();
            dvr_calc_disp("The Routing Tables for the Network are: ");
            ObjectOutputStream os1=new ObjectOutputStream(s.getOutputStream());
            os1.writeObject(rt);
            ObjectOutputStream os2=new ObjectOutputStream(s.getOutputStream());
            os2.writeObject(mid);
            ss.close();
        }}
    static void dvr_calc_disp(@SuppressWarnings("SameParameterValue") String message)
    {
        System.out.println();
        init_tables();
        update_tables();
        System.out.println(message);
        print_tables();
        System.out.println();
    }
    static void update_table(int source)
    {
        for(int i = 0; i < v; i++)
        {
            if(inpgraph[source][i] != 9999)
            {
                int dist = inpgraph[source][i];
                for(int j = 0; j < v; j++)
                {
                    int inter_dist = rt[i][j];
                    if(mid[i][j] == source)
                        inter_dist = 9999;
                    if(dist + inter_dist < rt[source][j])
                    {
                        rt[source][j] = dist + inter_dist;
                        mid[source][j] = i;
                    }}}}}
    static void update_tables()
    {
        int k = 0;
        for(int i = 0; i < 2*v; i++)
        {
            update_table(k);
            k++;
            if(k == v)
                k = 0;
        }}
    static void init_tables()
    {
        for(int i = 0; i < v; i++)
        {
            for(int j = 0; j < v; j++)
            {
                if(i == j)
                {
                    rt[i][j] = 0;

                    mid[i][j] = i;
                }
                else
                {
                    rt[i][j] = 9999;
                    mid[i][j] = 100;
                }}}}
    static void print_tables()
    {
        for(int i = 0; i < v; i++)
        {
            for(int j = 0; j < v; j++)
            {
                System.out.print("Distance: " + rt[i][j] + " ");
            }
            System.out.println();
        }}}
