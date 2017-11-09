/**
 * Created by EliasBrattli on 03/11/2016.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class FileUtil {

    private byte[] readFile(String fileName) throws IOException{
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat(fileName);
        return Files.readAllBytes(Paths.get(filePath));
    }

    public String[] getFileAsStringArray(String fileName)throws IOException{
        return new String(readFile(fileName)).split("");
    }
    public byte[] getFileAsByteArray(String fileName)throws IOException{
        return readFile(fileName);
    }
    public String getFileAsString(String fileName)throws IOException{
        return new String(readFile(fileName));
    }

    // Write compressed pattern to file
    public void writeFile(String filename,byte[] output, int len){
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath.concat(filename);
            FileOutputStream stream = new FileOutputStream(filePath);
            if(len < output.length)output = clean(output,len);
            stream.write(output);
            stream.close();
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    private byte[] clean(byte[] arr, int len){
        byte[] copy = new byte[len];
        for (int i = 0; i < len; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

}
