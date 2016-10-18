

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * Created by EliasBrattli on 13/09/2016.
 */
public class CodeInspector {
    private static Stack<Character> stack = new Stack<Character>();
    private static char[] characters;

    public static void readFile(){
        String fileName = "src/code.txt";
        String content = "";
        try{
            content = new String(Files.readAllBytes(Paths.get(fileName)));
            characters = content.toCharArray();

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean analyzeCharacters(char[] chars){
        char startBracket = '{';
        char endBracket = '}';
        char currentChar;
        boolean specialSymb = false;
        for (int i = 0; i < chars.length ; i++) {
            currentChar = chars[i];
                if (currentChar == startBracket) {
                    stack.push(currentChar);
                } else if (currentChar == endBracket) {
                    if (!stack.empty()) {
                        if (stack.peek() == startBracket) {
                            stack.pop();
                        }
                    } else {
                        return false;
                    }
                }

        }
        return stack.empty();
    }
    public static void main(String[] args){
        readFile();
        System.out.println("Programmet er rent: "+analyzeCharacters(characters));
    }
}
