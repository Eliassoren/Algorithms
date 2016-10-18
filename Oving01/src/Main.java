import java.util.Date;
import java.util.Random;

/**
 * Created by EliasBrattli on 22/08/2016.
 */
public class Main {
    public static void main(String[] args) {

        int[] kursforandringer = {-1,3,-9,2,2,-1,2,-1,-5};
        int[] dager = finnDager(kursforandringer,0,kursforandringer.length);
            for (int i = 0; i < dager.length; i++) {
                System.out.println("Dag " +(i+1)+": "+ dager[i]+1);
            }
        }

    static int[] finnDager( int[] data, int start,int n){
        int temp = start;//1
        int[] verdier = new int[data.length];//1
        int[] dager = new int[2];//1
        temp = verdier[0];//1
        for (int i = 0; i < n; i++) {//1+2n
            temp += data[i];
            verdier[i] = temp;
        }
        temp = Integer.MIN_VALUE;//1
        for (int i = 0; i <= n; i++) { //1+2n
            for (int j = i+1; j < n; j++) { //(1+2(n))*n
                if( verdier[j]- verdier[i] > temp){ //2n*(n)
                    temp = verdier[j]- verdier[i];// 2n(n)
                    dager[0] = i;// n(n-i)
                    dager[1] = j;// n(n-i)
                }
            }
        }
        return dager;//1
    }
 }
/*
  7
 +2(1+2n)
 1+2(n-i)*n = 1 + 2n^2- 2in
 (n-i)*2n = 2n^2 - 2in
 (n-i)*2n = 2n^2 - 2in
 n(n-i) = n^2 - in
 n(n-i) = n^2 - in

 = 2(2n^2-2in) + 2 (n^2 - in) + 2(1+2n) + 7
 = 4(n^2 - in + 2 (n^2 - in ) + 2 + 4n + 7
 = 6 (n^2 - in) + 4n + 9
 = 6n^2 - 10 n + 9


10 - 10 ms
  100 - 10 ms
  1000 - 10 ms
  10000 - 66 ms
  100000 - 6100 ms
  1000000 - 611 488 ms
*/