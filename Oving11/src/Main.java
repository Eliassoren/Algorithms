/**
 * Created by EliasBrattli on 31/10/2016.
 */
public class Main {
    public static void main(String[] args) {
        int[][]nxtCondA = {{1,1,2,3},{3,2,3,3}};
        char[] inputAlphaA = "01".toCharArray();
        int[]acceptingCondA = {2};
        Automat a = new Automat(inputAlphaA,acceptingCondA,nxtCondA);
        char[] inputA2 = "010".toCharArray();
        char[] inputA1 = {};
        char[] inputA3 = "111".toCharArray();
        char[] inputA4 = "010110".toCharArray();
        char[] inputA5 = "001000".toCharArray();
        System.out.println("Sjekker tom streng: "+a.checkInput(inputA1));
        System.out.println("Sjekker tom 010: "+a.checkInput(inputA2));
        System.out.println("Sjekker 111: "+a.checkInput(inputA3));
        System.out.println("Sjekker 010110: "+a.checkInput(inputA4));
        System.out.println("Sjekker 001000: "+a.checkInput(inputA5));

        int[][] nxtCondB = {{1,4,3,3,4},{2,3,4,3,4}};
        char[] inputAlphaB = "ab".toCharArray();
        int[]acceptingCondB = {3};
        Automat b = new Automat(inputAlphaB,acceptingCondB,nxtCondB);
        char[] inputB1 = {};
        char[] inputB2 = "abbb".toCharArray();
        char[] inputB3 = "aaab".toCharArray();
        char[] inputB4 = "babab".toCharArray();
        char[] inputB5 = "bbbb".toCharArray();
        System.out.println("Sjekker tom streng: "+b.checkInput(inputB1));
        System.out.println("Sjekker abbb: "+b.checkInput(inputB2));
        System.out.println("Sjekker aaab: "+ b.checkInput(inputB3));
        System.out.println("Sjekker babab: "+ b.checkInput(inputB4));
        System.out.println("Sjekker bbbb: "+ b.checkInput(inputB5));
    }
}
