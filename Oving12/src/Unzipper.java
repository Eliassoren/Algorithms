import java.io.IOException;

/**
 * Created by EliasBrattli on 03/11/2016.
 */


public class Unzipper {
    private FileUtil fileUtil;
    private byte[] data;
    private byte[] output;
    private int outputCount;

    public Unzipper(){
        fileUtil = new FileUtil();
    }
    private byte[] readFile(String file)throws IOException {
       return fileUtil.getFileAsByteArray(file);
    }
    public void initData(String file){
        try {
            data = readFile(file);
           output = new byte[data.length*5];
        }catch (IOException e){
            e.printStackTrace();
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }

    public void unpack(String outputFileName){
        byte header;
        for (int i = 0; i < data.length; i++) {
            header = data[i];
            if(header<0) {
                // Uncompressed data
                int length = Math.abs(header);
                for (int j = i+1; j <= i+length; j++) {
                    output[outputCount++] = data[j];
                }
                i+=length;
            }else{
                // Compressed data, search
                int len = header;
                int off = data[++i];
                int startIndex = outputCount - off;
                if(startIndex+len > output.length)break;
                for (int j = startIndex; j < startIndex+len; j++) {
                    output[outputCount++] = output[j];
                }
            }
        }
        fileUtil.writeFile(outputFileName,output,outputCount);
    }
    private String convertToString(byte[] pattern, int startIndex, int count) {
        String s = "";
        for (int i = startIndex; i < startIndex + count; i++) {
            s += (char)(pattern[i]);
        }
        return s;
    }
    public static void main(String[] args) {
        Unzipper unzipper = new Unzipper();
        unzipper.initData("/data/output/compressed.txt");
        unzipper.unpack("/data/output/unpacked.txt");
        //unzipper.unpack("/data/output/unpacked.pdf");
    }
}
