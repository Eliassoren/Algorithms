package oppg1;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by EliasBrattli on 17.09.2016.
 */
public class Main {

    public static void main(String[] args) {
        oppg1.Hashtable table;
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/data/navn.txt");
        System.out.println(filePath);

        try {
            String[] names = readFile(filePath);
            table = new oppg1.Hashtable(names.length + names.length/4 ); // Burde generere primtall
            System.out.println(names.length);
            //beregnTid(0,25, names);
            for(String s: names){
                System.out.println(s);
                System.out.println(table.put(s));
                double lastfaktor = table.getSpaceTaken()/(double)table.getSize();
                System.out.format("Lastfaktor: %.4f \n", lastfaktor);
            }
            //System.out.println("Skal finne Arbo, Audun: "+table.getPos("Arbo, Audun Wigum"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String[] readFile(String fileName)throws IOException{
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content.split("\n");
    }

    public static void beregnTid(int startVal, int sluttVal, String[] names) {
        double tid = 1.0;
        oppg1.Hashtable table;
        if (startVal < sluttVal && sluttVal < 2000000) {
            for (int n = startVal; n < sluttVal; n++) {
                table = new oppg1.Hashtable(names.length + names.length/4);

                Date starttid = new Date();
                //eksp2(2,n);
                Date sluttid = new Date();
                int antRunder = 0;
                while (sluttid.getTime() - starttid.getTime() < 10) {
                    //Her testes metoden i HashTable
                    for(String s: names){
                        //System.out.println(s);
                        table.put(s);
                    }
                    //Test ferdig
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
