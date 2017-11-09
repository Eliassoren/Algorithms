/**
 * Created by EliasBrattli on 31/10/2016.
 */
public class Automat{
    private char[] inputAlphabet;
    private int[] acceptingConditions;
    private int[][] nextConditionArr;
    public Automat(char[] inputAlphabet,int[]acceptingConditions,int[][] nextConditionArr){
        this.inputAlphabet = inputAlphabet;
        this.acceptingConditions = acceptingConditions;
        this.nextConditionArr = nextConditionArr;
    }

    public boolean checkInput(char[] input){
        if(input != null) {
            int currentCondition = 0;
            int next = -1;
            for (char c : input) {
                int index = findIndexInAlphabet(c);
                next = nextConditionArr[index][currentCondition];
                currentCondition = next;
            }
            return checkEndcondition(currentCondition);
        }
        return false;
    }
    private boolean checkEndcondition(int c){
        for (int i = 0; i < acceptingConditions.length; i++) {
            if(c == acceptingConditions[i])return true;
        }
        return false;
    }
    private int findIndexInAlphabet(char input){
            for (int i=0; i<inputAlphabet.length;i++) {
                if (input == inputAlphabet[i]) return i;
            }
        return -1;
    }

}
