/**
 * Created by EliasBrattli on 05/10/2016.
 */
public class Edge {
    public Edge next;
    public Node to;
    public Edge(Edge e, Node n){
        next = e;
        to = n;
    }

}
