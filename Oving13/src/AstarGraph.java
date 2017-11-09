import kotlin.io.NoSuchFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.PriorityQueue;

/**
 * Created by EliasBrattli on 09/10/2016.
 */
public class AstarGraph {
    static Node[]nodes;
    static PriorityQueue<Node> nodeQueue;
    static Node xstartNode;
    static Node xgoalNode;
    static int checkedCount;
   // static int startVal = 0;
   // static int goalVal;
    static int NODE_AMOUNT = 0, EDGE_AMOUNT = 0;

    static void readNodes(String fileName )throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line = bufferedReader.readLine().trim();
        NODE_AMOUNT = Integer.parseInt(line);
        nodes = new Node[NODE_AMOUNT];
        while((line = bufferedReader.readLine())!=null){
            String[] values = line.trim().split("\\s+");
            int id = Integer.parseInt(values[0]);
            double lat = Double.parseDouble(values[1]);
            double lng = Double.parseDouble(values[2]);
            Node node = new Node();
            node.id = id;
            Coordinate coord = new Coordinate(lat,lng);
            node.coordinates = coord;
            nodes[id]= node;
        }
        bufferedReader.close();
        //System.out.println("DONE with Nodes");
    }
    static void readEdges(String fileName,int startVal, int goalVal)throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line = bufferedReader.readLine().trim();
        EDGE_AMOUNT = Integer.parseInt(line);
        xstartNode = nodes[startVal];
        xgoalNode = nodes[goalVal];
        int count = 0;
        while ((line = bufferedReader.readLine())!=null){
            String[] data = line.trim().split("\\s+");
            int fromNodeId = Integer.parseInt(data[0]);
            int toNodeId = Integer.parseInt(data[1]);
            int runTime = Integer.parseInt(data[2]);
            int len = Integer.parseInt(data[3]);
            int speedLim = Integer.parseInt(data[4]);
            if(count % 100000 == 0){
                System.out.println(count);
            }
            Node fromNode = nodes[fromNodeId];
            fromNode.edge1  = new WeightedEdge(fromNode.edge1,nodes[toNodeId],runTime);
            count++;
        }
        bufferedReader.close();
    }

    static int calcDistance (Coordinate thisNode, Coordinate goal) {
        double sin_lat = Math.sin((thisNode.getLatRad()-goal.getLatRad())/2.0);
        double sin_lng = Math.sin((thisNode.getLngRad()-goal.getLngRad())/2.0);
        return (int) (2 * 6371*Math.asin(
                Math.sqrt(sin_lat*sin_lat+
                Math.cos(thisNode.getLatRad())*
                Math.cos(goal.getLatRad())*
                sin_lng*sin_lng)));
    }
    static void shorten(WeightedEdge wEdge, Node node,Node goal, boolean dijkstra){
        if(wEdge.to.data == null) wEdge.to.data = new Prev();

        Prev prev1 = (Prev)node.data, prev2=(Prev)wEdge.to.data;

        if(prev2.dist > prev1.dist + wEdge.weight){
            prev2.dist = prev1.dist + wEdge.weight;
            prev2.prevNode = node;
            prev2.time = prev1.time + wEdge.weight;
            prev2.pri = prev1.dist + wEdge.weight + (!dijkstra?calcDistance(wEdge.to.coordinates,goal.coordinates):0);
            // If we've already found the node, remove and add it again to see if we will change its priority.
            if(wEdge.to.checked) {
                nodeQueue.remove(wEdge.to);
                nodeQueue.add(wEdge.to);
            }
            else{
                // We've discovered a new node, add it to find priority.
                nodeQueue.add(wEdge.to);
                wEdge.to.checked = true;
            }

        }
    }




    static void astar(Node startNode, Node goalNode, boolean djikstra){

        nodeQueue = new PriorityQueue<Node>();
        startNode.data = new Prev();
        ((Prev)startNode.data).dist = 0;
        startNode.checked = true;
        nodeQueue.add(startNode);

        while (!nodeQueue.isEmpty()){
            Node curr = nodeQueue.poll();
            //curr.checked = true;
            checkedCount++;
            if(curr == goalNode)reconstructPath(startNode,goalNode);
            for(WeightedEdge we = curr.edge1; we != null; we = we.next){
                shorten(we,curr,goalNode,djikstra);
            }
        }

    }
    static void reconstructPath(Node startNode,Node goalNode){
        Node n = goalNode;
        while (n != startNode){
            System.out.println(Math.toDegrees(n.coordinates.getLatRad())+","+Math.toDegrees(n.coordinates.getLngRad()));
            n = ((Prev)n.data).prevNode;
        }
        System.out.println(Math.toDegrees(startNode.coordinates.getLatRad())+","+Math.toDegrees(startNode.coordinates.getLngRad()));
        System.out.println("Antall sjekkede noder: "+ checkedCount);
        System.out.println("Kjøretid: "+((Prev)goalNode.data).time+" ms");
        System.out.println("Dist "+((Prev)goalNode.data).dist);
    }
    public static void beregnTid( int startVal, int sluttVal) {
        double tid = 1.0;

        for (int n = startVal; n < sluttVal; n++) {

            Date starttid = new Date();
            Date sluttid = new Date();
            int antRunder = 0;
            while (sluttid.getTime() - starttid.getTime() < 1) {
                astar(xstartNode, xgoalNode, true);
                //astar(xstartNode, xgoalNode, false);
            sluttid = new Date();
            antRunder++;
        }
        double forrigeTid = tid;
        tid = (double) (sluttid.getTime() - starttid.getTime()) / antRunder;
        System.out.format("Antall: %6d,Tid: %8.8f ms, forholdstall: %6.2f, antall runder: %6d%n", n, tid, tid / forrigeTid, antRunder);
    }
}
    public static void main(String[] args){
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/data/astar/noder.txt");
       try{

           readNodes(filePath);
           filePath = new File("").getAbsolutePath();
           filePath = filePath.concat("/data/astar/kanter.txt");
           //readEdges(filePath,2146837,2009903); // Oslo Bergen
           //readEdges(filePath,2418547,2633550); // Gjemnes til Kårvåg
           readEdges(filePath,2058549,1051859); // Kristiansund til Helsinki


           astar(xstartNode,xgoalNode,false);
            //beregnTid(0,25);
        }catch(NoSuchFileException nos){
            nos.printStackTrace();
       } catch (IOException e){
           e.printStackTrace();
        }catch(Exception ne){
           ne.printStackTrace();
       }

    }
}
