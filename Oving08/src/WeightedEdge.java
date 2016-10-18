/**
 * Created by EliasBrattli on 09/10/2016.
 */
public class WeightedEdge extends Edge{
    int weight;
    public WeightedEdge(WeightedEdge edge, Node node, int weight){
        super(edge,node);
        this.weight = weight;
    }
}

