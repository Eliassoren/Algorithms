package oppg1;

/**
 * Created by EliasBrattli on 17.09.2016.
 * Oppg 1
 */
public class Hashtable {
    private final int size;
    private String[] hTable;
    private double spaceTaken;
    public Hashtable(int size){
        this.size = size;
        hTable = new String[size];
    }

    public int getSize() {
        return size;
    }
    public double getSpaceTaken(){
        return spaceTaken;
    }
    private int hash1(int key){
        final double A = (Math.sqrt(5.)-1)/2;
        return (int)Math.floor(size *(key*A-Math.floor(key*A)));
    }
    private int hash2(int key){
        return key % size;
    }
    private int probe(int h1, int h2, int i, int m){
        return (h1+i*h2) % m;
    }
    //String.codePointAt()
    private int makeKey(String s){
        int sum = 0;
        for(int i = 0; i< s.length();i++){
            //Hvert tegn vektes ulikt, unngår dermed kollisjoner
            sum += s.codePointAt(i)*Math.pow(2.,i);
        }
        return sum;
    }

    public int put(String input){
        int key = 0, h1 = 0, h2 = 0;
        key = makeKey(input);
        h1 = hash1(key);
        h2 = hash2(key);
        for (int i = 0; i < size; i++) {
            int pos = probe(h1,h2,i, size);
                if(pos > -1) {
                if (hTable[pos] == null) {
                    hTable[pos] = input;
                    spaceTaken++;
                    return pos;
                }else{
                    System.out.println(input +" | | "+ hTable[pos]);
                }
            }
        }
        return -1;
    }

    public int getPos(String val){
        int key = makeKey(val);
        int h1 = hash1(key);
        int h2 = hash2(key);
        for (int i = 0; i < size; i++) {
            int pos = probe(h1,h2,i, size);
            if(hTable[pos] == null) return -1;
            if(key == makeKey(hTable[pos])){
                return pos;
            }
        }
        return -1;
    }


}
