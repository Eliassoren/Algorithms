import java.util.Date;
import java.util.Random;

/**
 * Created by EliasBrattli on 29/08/2016.
 */
public class Tabellsortering {
    public static void main (String[] args){

        int[] tab = genererTabell(10000000,100);
        /*for (int i = 0; i < tab.length; i++) {
            System.out.printf("Tabby["+tab[i]+"]\n");
        }*/

        System.out.println("\nEtter sort:\n");
        //Sortering
        //quicksortHjelp(tab,0,tab.length-1);
        beregnTid(tab,0,25);
        //for (int i = 0; i < tab.length; i++) {
           // System.out.printf("Tabby["+tab[i]+"]\n");
       // }
        //Test
       // System.out.println("Sortert riktig? "+testSort(tab));
    }
    public static void beregnTid(int[] tab, int startVal, int sluttVal) {
        double tid = 1.0;
        int[]kopi = new int[tab.length];

        for (int n = startVal; n < sluttVal; n++) {
            for (int i = 0; i < kopi.length; i++) {
                kopi[i]= tab[i];
            }
                Date starttid = new Date();
                Date sluttid = new Date();
                int antRunder = 0;
                while (sluttid.getTime() - starttid.getTime() < 1) {
                    quicksortHjelp(kopi,0,kopi.length-1);
                    //insettingsSort(kopi,0,kopi.length-1);
                    //quicksort(kopi,0,kopi.length-1);
                    //quicksortMod(kopi,0,kopi.length-1);
                    sluttid = new Date();
                    antRunder++;
                }
                double forrigeTid = tid;
                tid = (double) (sluttid.getTime() - starttid.getTime()) / antRunder;
                System.out.format("Antall: %6d,Tid: %8.8f ms, forholdstall: %6.2f, antall runder: %6d%n",n, tid, tid / forrigeTid, antRunder);
            }

    }
    public static int[] genererTabell(int lengde, int spekter){
        int[] tab = new int[lengde];
        Random random = new Random();
        for (int i = 0; i < tab.length; i++) {
            tab[i] = random.nextInt(spekter);
        }
        return tab;
    }
    public static boolean testSort(int[] tab){
        for (int i = 0; i < tab.length-2 ; i++) {
            if(tab[i+1]<tab[i]){
                return false;
            }
        }
        return true;
    }
    //Standard quicksort
    public static void quicksort(int[] tab, int bunn, int topp){
        if(topp - bunn > 2){
            int mid = splitt(tab,bunn,topp);
            quicksort(tab,bunn,mid-1);
            quicksort(tab,mid+1,topp);
        }else{
            median3sort(tab,bunn,topp);
        }
    }
    //Modifisert quicksort oppg3
    public static void quicksortMod(int[] tab, int bunn, int topp){
        if(bunn > 0 && topp < tab.length && tab[bunn-1] == tab[topp+1]){
            return;
        }
        if(topp - bunn > 2){
            int mid = splitt(tab,bunn,topp);
            quicksort(tab,bunn,mid-1);
            quicksort(tab,mid+1,topp);
        }else{
            median3sort(tab,bunn,topp);
        }
    }
    //Quicksort med hjelpealgoritme
    public static void quicksortHjelp(int[] tab, int bunn, int topp){
        if(bunn > 0 && topp < tab.length && tab[bunn-1] == tab[topp+1]){
            return;
        }
        if(topp - bunn > 100){
            int mid = splitt(tab,bunn,topp);
            quicksort(tab,bunn,mid-1);
            quicksort(tab,mid+1,topp);
        }else{
            insettingsSort(tab,bunn,topp);
        }
    }
    private static void insettingsSort(int[] tab, int fra, int til){
        for (int i = fra+1 ; i < til+1; i++) {
            int bytt = tab[i];
            int j = i-1;
            while(j >= fra && tab[j] > bytt){
                tab[j+1] = tab[j];
                --j;
            }
            tab[j+1] = bytt;
        }
    }
    private static int median3sort(int[] tab, int bunn, int topp){
        int mid = (bunn+topp)/2;
        if(tab[bunn] > tab[mid]){
            bytt(tab,bunn,mid);
        }
        if(tab[mid]>tab[topp]){
            bytt(tab,mid,topp);
            if(tab[bunn]>tab[mid]) bytt(tab,bunn,mid);
        }
        return mid;
    }
    private static void bytt(int[] tab, int bunn, int topp){
        int temp = tab[ bunn];
        tab[bunn] = tab[topp];
        tab[topp] = temp;
    }
    private static int splitt(int[] tab, int bunn, int topp){
        int indeksB, indeksT;
        int mid = median3sort(tab,bunn,topp);
        int deleVal = tab[mid];
        bytt(tab,mid,topp-1);
        for (indeksB = bunn, indeksT = topp-1;;){
            while(tab[++indeksB] < deleVal);
            while(tab[--indeksT] > deleVal);
            if(indeksB >= indeksT){
                break;
            }
            bytt(tab,indeksB,indeksT);
        }
        bytt(tab,indeksB,topp-1);
        return indeksB;
    }
}
