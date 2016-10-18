package oppg2;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by EliasBrattli on 20.09.2016.
 */

public class NumHashtable {
    private final int length;
    private int[] hTable;

    public NumHashtable(int length){
        this.length = length;
        hTable = new int[length];
        for(int i = 0; i< hTable.length;i++){
            hTable[i] = -1;
        }
    }

    public int getLength() {
        return length;
    }

    private int hash1(int key){
        final double A = (Math.sqrt(5.)-1)/2;
        return (int)Math.floor(length*(key*A-Math.floor(key*A)));
    }
    private int hash2(int key){
        return key % length;
    }
    private int probe(int h1, int h2, int i, int m){
        return (h1+i) % m;
    }


    public int put(int input){
       int h1 = 0, h2 = 0;
        h1 = hash1(input);
        h2 = hash2(input);
        for (int i = 0; i < length; i++) {
            int pos = probe(h1,h2,i,length);
         if(pos > -1) {
                if (hTable[pos] == -1) {
                    hTable[pos] = input;
                    return pos;
                }else{
                    //System.out.println(input +" | | "+hTable[pos]);
                }
            }
        }
        return -1;
    }

    public int getPos(int val){
        int h1 = hash1(val);
        int h2 = hash2(val);
        for (int i = 0; i < length; i++) {
            int pos = probe(h1,h2,i,length);
            if(hTable[pos] == -1) return -1;
            if(val == hTable[pos]){
                return pos;
            }
        }
        return -1;
    }
    public static void main(String args[]){
        int[] randArr = new int[10000000];
        NumHashtable table;
        Random rand = new Random();
        for (int i = 0; i < randArr.length; i++) {
            randArr[i] = (rand.nextInt(Integer.MAX_VALUE/128)-1);
            if(i<100) {
                System.out.println(randArr[i]);
            }
        }
        //table = new NumHashtable((int)(randArr.length *1.25));
       /* for(int i: randArr){
            System.out.println(i);
            System.out.println(table.put(i));
        }*/
        beregnTid(0,10,randArr);
    }
    public static void beregnTid(int startVal, int sluttVal, int[] arr) {
        double tid = 1.0;
        NumHashtable table;
        HashMap<Integer,Integer> map;

        int[] test;
        if (startVal < sluttVal && sluttVal < 2000000) {
            for (int n = startVal; n < sluttVal; n++) {
                table = new NumHashtable((12500000));
                //map = new HashMap<Integer, Integer>((int)(arr.length*1.25));
                //test = new int[10000000];
                Date starttid = new Date();
                //eksp2(2,n);
                Date sluttid = new Date();
                int antRunder = 0;
                while (sluttid.getTime() - starttid.getTime() < 10) {
                    //Her testes metoden i HashTable
                    for(int i: arr){
                        //System.out.println(s);
                        table.put(i);
                       // map.put(i,i);
                    }

                    //for (int i = 0; i < test.length; i++) {
                      //  test[i] = arr[i];
                    //}
                    sluttid = new Date();
                    antRunder++;
                }
                double forrigeTid = tid;
                tid = (double) (sluttid.getTime() - starttid.getTime()) / antRunder;
                System.out.format("Antall: %6d, tid: %8.8f ms, forholdstall: %6.2f, antall runder: %6d%n", n, tid, tid / forrigeTid, antRunder);
            }
        }
    }
}

