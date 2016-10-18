import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by EliasBrattli on 05/10/2016.
 */

public class Graph {
    static Node[]nodes;
    static int NODE_AMOUNT = 0, EDGE_AMOUNT = 0;
    static LinkedList<Node> queque;
    public static void main(String[] args){
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/data/graf1.txt");
        try {
            newGraph(readFile(filePath));
            //initPrev(nodes[0]);
            for (int i = 0; i < nodes.length; i++) {
                System.out.println(nodes[i].id);
            }
            wfSearch(nodes[5]);
            for (int i = 0; i < nodes.length; i++) {
                Prev prev = ((Prev)nodes[i].data);
                int id = 0;
                if(prev.prevNode != null) {
                    id = prev.prevNode.id;
                }else{
                    id = -1;
                }
                System.out.println("Node: "+nodes[i].id+" Avstand: "+((Prev)nodes[i].data).dist+" Prev "+id);
            }

            /*Node n = topoSort();
            Topolist t = (Topolist) n.data;
            while (n != null){
                System.out.println(n.id);
                if(t.next != null){
                    n = t.next;
                    t = (Topolist)n.data;

                }else{
                    break;
                }*/

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static List<String> readFile(String fileName )throws IOException {
        List<String> content = (Files.readAllLines(Paths.get(fileName)));
        return content;
    }
    public static void newGraph(List<String> graph){
        String[] amts = graph.get(0).split(" ");
        System.out.println(amts[0]+" "+amts[1]);
        NODE_AMOUNT = Integer.parseInt(amts[0]);
        nodes = new Node[NODE_AMOUNT];

        for (int i = 0; i < NODE_AMOUNT; i++) {
            nodes[i] = new Node();
            nodes[i].id = i;
        }

        EDGE_AMOUNT = Integer.parseInt(amts[1]);
        for (int i = 1; i <= EDGE_AMOUNT; i++) {
            int from = Integer.parseInt(graph.get(i).split(" ")[0]);
            int to = Integer.parseInt(graph.get(i).split(" ")[1]);
            System.out.println("Fra "+from+" Til "+to);
            Edge edge = new Edge(nodes[from].edge1,nodes[to]);
            nodes[from].edge1 = edge;
        }
    }
    public static void initPrev(Node startnode){
        for (int i = 0; i < NODE_AMOUNT;i++) {
            nodes[i].data = new Prev();
        }
        ((Prev)startnode.data).dist = 0;
    }
    public static void wfSearch(Node startnode){
        initPrev(startnode);
        queque = new LinkedList<Node>();
        queque.add(startnode);
        int i = 0;
        while (!queque.isEmpty()){
            Node node = queque.pop();
            System.out.println("n "+node.id);
            for(Edge edge = node.edge1;edge != null;edge=edge.next){
                Prev prev = (Prev)edge.to.data;

                //System.out.println("prev: "+prev.prevNode);
                if(prev.dist == prev.maxint){
                    prev.dist = ((Prev)node.data).dist + 1;
                    prev.prevNode = node;
                    queque.add(edge.to);
                }
            }
            i++;
        }
    }
    static Node dfTopolog(Node n, Node l){
        Topolist nd = (Topolist)n.data;
        if(nd.found)return l;

        nd.found = true;
        for(Edge e = n.edge1;e!= null;e=e.next){
            l= dfTopolog(e.to,l);
        }
        nd.next = l;
        return n;
    }
    static Node topoSort(){
        Node l = null;
        for (int i = 0; i < NODE_AMOUNT; i++) {
            nodes[i].data = new Topolist();
        }
        for (int i = 0; i < NODE_AMOUNT; i++) {
            l = dfTopolog(nodes[i],l);
        }
        return l;
    }
}
