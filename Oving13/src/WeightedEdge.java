/**
 * Created by EliasBrattli on 09/10/2016.
 */
public class WeightedEdge{
    int weight;
    WeightedEdge next;
    Node to;
    public WeightedEdge(WeightedEdge edge, Node node, int w){
        next = edge;
        to = node;
        weight = w;
    }
}

