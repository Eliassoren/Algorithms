import java.util.Map;

/**
 * Created by EliasBrattli on 15/11/2016.
 */
public class Coordinate {
    private double lat;
    private double lng;
    public Coordinate(double lat,double lng){
        this.lat = lat;
        this.lng = lng;
    }
    public double getLatRad(){
        return Math.toRadians(lat);
    }
    public double getLngRad(){
        return Math.toRadians(lng);
    }
}
