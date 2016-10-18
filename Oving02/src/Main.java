import java.util.Date;

/**
 * Created by EliasBrattli on 24/08/2016.
 */
public class Main {
    public static void main(String args[]) {
        //System.out.println("Eksp: "+ eksp(2,8));
        //System.out.println("Eksp2: "+ eksp2(2,8));
        double tid = 1.0;
        int start = 10;
        for (int n = start; n < 2000000; n *= 2) {

            Date starttid = new Date();
            //eksp2(2,n);
            Date sluttid = new Date();
            int antRunder = 0;
            while (sluttid.getTime() - starttid.getTime() < 1) {
                //eksp(2,n);
                eksp2(2, n);
                sluttid = new Date();
                antRunder++;
            }
            double forrigeTid = tid;
            tid = (double) (sluttid.getTime() - starttid.getTime()) / antRunder;
            System.out.format("Antall: %6d, tid: %8.8f ms, forholdstall: %6.2f, antall runder: %6d%n", n, tid, tid / forrigeTid, antRunder);
        }

        /* eksp
        * Lineær
        *   Antall:     10, tid: 0.00016780 ms, forholdstall:   0.00, antall runder:  59596
            Antall:     20, tid: 0.00012717 ms, forholdstall:   0.76, antall runder:  78632
            Antall:     40, tid: 0.00015912 ms, forholdstall:   1.25, antall runder:  62844
            Antall:     80, tid: 0.00019658 ms, forholdstall:   1.24, antall runder:  50869
            Antall:    160, tid: 0.00046926 ms, forholdstall:   2.39, antall runder:  21310
            Antall:    320, tid: 0.00089350 ms, forholdstall:   1.90, antall runder:  11192
            Antall:    640, tid: 0.00305998 ms, forholdstall:   3.42, antall runder:   3268
            Antall:   1280, tid: 0.00685871 ms, forholdstall:   2.24, antall runder:   1458
            Antall:   2560, tid: 0.01555210 ms, forholdstall:   2.27, antall runder:    643
            Antall:   5120, tid: 0.04166667 ms, forholdstall:   2.38, antall runder:    240*/

        /*eksp2:
        * Logaritmisk
        *Antall:     10, tid: 0.00038791 ms, forholdstall:   0.00, antall runder:  25779
        Antall:     20, tid: 0.00009780 ms, forholdstall:   0.25, antall runder: 112478
        Antall:     40, tid: 0.00009579 ms, forholdstall:   0.98, antall runder: 104400
        Antall:     80, tid: 0.00011953 ms, forholdstall:   1.25, antall runder:  83663
        Antall:    160, tid: 0.00013538 ms, forholdstall:   1.13, antall runder:  73865
        Antall:    320, tid: 0.00017939 ms, forholdstall:   1.33, antall runder:  55744
        Antall:    640, tid: 0.00016870 ms, forholdstall:   0.94, antall runder:  59276
        Antall:   1280, tid: 0.00019590 ms, forholdstall:   1.16, antall runder:  51047
        Antall:   2560, tid: 0.00017989 ms, forholdstall:   0.92, antall runder:  55589
        Antall:   5120, tid: 0.00018342 ms, forholdstall:   1.02, antall runder:  54520
        Antall:  10240, tid: 0.00020543 ms, forholdstall:   1.12, antall runder:  48679
        * */
    }

    //Oppg 2.1-1
    public static double eksp(double x, int n) {
        return (n < 1) ? 1 : x * eksp(x, n - 1);
    }

    //Oppg 2.2-3
    public static double eksp2(double x, int n) {
        if (n < 1) {
            return 1;
        } else {
            if ((n % 2) == 0) {
                return eksp2(x * x, n / 2);
            }
            return x * eksp2(x * x, (n - 1) / 2);
        }
    }

    public static void beregnTid(int startVal, int sluttVal) {
        double tid = 1.0;
        if (startVal < sluttVal && sluttVal < 2000000) {
            for (int n = startVal; n < sluttVal; n *= 2) {

                Date starttid = new Date();
                //eksp2(2,n);
                Date sluttid = new Date();
                int antRunder = 0;
                while (sluttid.getTime() - starttid.getTime() < 1) {
                    //eksp(2,n);
                    eksp2(2, n);
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
