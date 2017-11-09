import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by EliasBrattli on 05/10/2016.
 */
public class Node implements Comparable<Node>{
    public int id;
    public WeightedEdge edge1;
    public boolean checked;
    public Object data;
    public Coordinate coordinates;
    public Node(){
        checked = false;
    }
    @Override
    public int compareTo(Node node){
        Prev thisPrev = (Prev)data;
        Prev thatPrev = (Prev)node.data;
        return thisPrev.pri-thatPrev.pri;
    }
}
