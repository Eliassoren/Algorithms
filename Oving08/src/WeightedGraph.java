import kotlin.io.NoSuchFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EliasBrattli on 09/10/2016.
 */
public class WeightedGraph {
    static ArrayList<Node>nodes;
    static Node[] nodescopy;
    static Node startNode;
    static ArrayList<Node> minheap;
    static int startval = 0;
    static int NODE_AMOUNT = 0, EDGE_AMOUNT = 0, min = 0;
    static List<String> readFile(String fileName )throws IOException {
        List<String> content = (Files.readAllLines(Paths.get(fileName)));
        return content;
    }
    static void newGraph(List<String> graph){
        String s = graph.get(0).replaceAll("^\\s+","");
        String[] amts = s.split("\\s");
         //System.out.println(amts[0]+" "+amts[1]);
        NODE_AMOUNT = Integer.parseInt(amts[0]);
        nodes = new ArrayList<Node>();
        for (int i = 0; i < NODE_AMOUNT; i++) {
            Node n = new Node();
            n.id = i;
            nodes.add(n);
        }
        nodescopy = new Node[nodes.size()];
        startNode = nodes.get(startval);
        EDGE_AMOUNT = Integer.parseInt(amts[1]);

        for (int i = 1; i <= EDGE_AMOUNT; i++) {
            s = graph.get(i);
            s = s.trim();
            s = s.replaceAll("\\s+"," ");
            String[] elements = s.split("\\s");
            int from = Integer.parseInt(elements[0]);
            int to = Integer.parseInt(elements[1]);
            int weight = Integer.parseInt(elements[2]);
            //System.out.println("Fra "+from+" Til "+to +" Vekt "+weight);
            Edge edge = new WeightedEdge((WeightedEdge)nodes.get(from).edge1,nodes.get(to),weight);
            nodes.get(from).edge1 = edge;
        }
    }
    static void copy(){
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            nodescopy[i] = n;
        }
    }
     static void initPrev(Node startnode){
        for (int i = 0; i < NODE_AMOUNT;i++) {
            nodes.get(i).data = new Prev();
        }
        ((Prev)startnode.data).dist = 0;
    }

    static void shorten(WeightedEdge wEdge, Node node){
        Prev prev1 = (Prev)node.data, prev2=(Prev)wEdge.to.data;
        //System.out.println("Dist: "+prev1.dist +" "+prev2.dist);
        if(prev2.dist > prev1.dist + wEdge.weight){
            prev2.dist = prev1.dist + wEdge.weight;
            prev2.prevNode = node;
            bubble(wEdge.to);
        }
    }
    static void bubble(Node n){
        for (int i = 0; i< minheap.size();i++) {
            Node curr = minheap.get(i);
            if(((Prev)n.data).dist >= ((Prev)curr.data).dist){
                continue;
            }
            minheap.add(i,n);
            return;
        }
        minheap.add(n);
    }
    static void initpri(ArrayList<Node> pri){
        for (int i = 0; i < NODE_AMOUNT; i++) {
            pri.add(removeMin(nodes));
        }
    }
    static Node removeMin(ArrayList<Node> nodes){
        Node min = nodes.get(0);
        Node n;
        int minindex = 0;
        for (int i = 1; i < nodes.size(); i++) {
            n = nodes.get(i);
            if(((Prev)min.data).dist >= ((Prev)n.data).dist){
                min = n;
                minindex = i;
            }
        }
        return nodes.remove(minindex);
    }

    static void dijkstra(Node node){
        Node n;
        minheap = new ArrayList<Node>();
        initPrev(node);
        initpri(minheap);
        for (int i = 0; i < NODE_AMOUNT; i++) {
            n = minheap.remove(0);
            for(WeightedEdge we = (WeightedEdge)n.edge1; we != null; we = (WeightedEdge)we.next){
                shorten(we,n);
            }
        }
    }
    public static void main(String[] args){
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/data/graph04.txt");

       try{
           newGraph(readFile(filePath));
           copy();
           dijkstra(startNode);
           int id = 0;
           for (int i = 0; i < nodescopy.length; i++) {
               Prev prev = ((Prev)nodescopy[i].data);
               if(prev.prevNode != null) {
                   id = prev.prevNode.id;
               }else{
                   id = -1;
               }
               System.out.println("Node: "+nodescopy[i].id+" Dist: "+((Prev)nodescopy[i].data).dist+" Prev "+ id);
           }
        }catch(NoSuchFileException nos){
            nos.printStackTrace();
       } catch (IOException e){
           e.printStackTrace();
        }catch(Exception ne){
           ne.printStackTrace();
       }

    }
}
