import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EliasBrattli on 21/10/2016.
 */
public class Main {
    public static void main(String args[]){
        System.out.println("Inneholder tall");
        Pattern numPattern = Pattern.compile(".*\\d.*");
        System.out.println(numPattern.matcher("eeeeeees").matches());//false
        System.out.println(numPattern.matcher("1sssssss").matches());
        System.out.println("Dato");
        Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
        System.out.println(datePattern.matcher("10/0/2010").matches());//false
        System.out.println(datePattern.matcher("10/10/2010").matches());//true
        System.out.println(datePattern.matcher("222").matches());//false
        System.out.println("Lengde");
        Pattern lengthPattern = Pattern.compile(".{10,}");
        System.out.println(lengthPattern.matcher("111111").matches());
        System.out.println(lengthPattern.matcher("1111111111").matches());
        Pattern noLetterPattern = Pattern.compile(".*^[A-Za-zæøåÆØÅ]+.*");
        System.out.println("Tegn som ikke er bokstav");
        System.out.println(noLetterPattern.matcher("wwwwwwwww?22").matches());
        System.out.println();
        System.out.println();
    }

}
