import java.io.IOException;

/**
 * Created by EliasBrattli on 03/11/2016.
 */
public class Zipper {
    private FileUtil fileUtil;
    private int defaultWindowLen;
    private byte[] data;
    private byte[] output;
    private int outputCount;
    private int[] matchLenghts = new int[1000];
    private final byte MIN_MATCH_LEN = 3;
    public Zipper(int defaultWindowLen){
        fileUtil = new FileUtil();
        this.defaultWindowLen = defaultWindowLen;
    }
    private byte[] readFile(String file)throws IOException{
        return fileUtil.getFileAsByteArray(file);
    }
    public void initData(String file){
        try {
            data = readFile(file);
            output = new byte[data.length*2]; // Maybe generate a longer array if compressing small files?
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void lempelZiv(String outputFileName){
        byte searchByte;
        byte matchLen = 0;
        int longestMatchLen = 0;
        int uncompressedCount = 0;
        final int windowLen = defaultWindowLen; // How far back are we looking. Essentially 127 bytes for speed, more for better compressing.
        int readCount = 0;
        int uncompressedId = -1;
        int searchCount;
        int matchIndex;
        int[] tuple = {-1,-1};

        while (readCount < data.length) {
            matchLen = 0;
            matchIndex = -1;
            int matchOutputCount = outputCount + 2; // Reserve space for len-off pair
            // Go one signed byte behind current
            searchCount = Math.max(0,readCount-windowLen);
            while (searchCount < readCount) {
                if(readCount+tuple[0] >= data.length )break;
                // Find a match
                 if(data[searchCount] == data[readCount + matchLen]){
                    if(matchIndex == -1){
                        matchIndex = searchCount; // Start of match, this is where we start our reference
                    }
                    matchLen++;
                     // Check if we got a longer match
                    if(tuple[0] < matchLen){
                        tuple[0] = matchLen;
                        tuple[1] = matchIndex;
                    }
                    if(matchOutputCount >= output.length) System.out.println("Outputerror");
                     if(readCount >= data.length) System.out.println("Inputerror");
                     output[matchOutputCount++] = data[readCount];
                }else if(matchIndex > -1) {
                    //reset
                     matchIndex = -1;
                     matchLen = 0;
                 }
                searchCount++;
            }
            // Check for matches
            if(tuple[0] >= MIN_MATCH_LEN && tuple[1] > -1){
                // If we have read a block of uncompressed data, finish and reset
                if(uncompressedCount > 0) {
                    output[uncompressedId] = (byte)(-uncompressedCount); // Added the amt of uncompressed bytes until next match with negative val
                    // Reset counter and
                    uncompressedCount = 0;
                    uncompressedId = -1;
                    outputCount++;
                }
                // Pack data
                output[outputCount++] = (byte)tuple[0]; // Len
                output[outputCount++] = (byte)(readCount-tuple[1]); // Offset
                // We increment readcount in every loop, compensate
                readCount += tuple[0]-1;
                // Reset
                tuple[0] = -1;
                tuple[1] = -1;
            }
            else {
                // Initiate start of next block
                if(uncompressedId == -1) {
                    uncompressedId = outputCount;
                }
                uncompressedCount++;
                output[++outputCount] = data[readCount];
            }
            // Block of uncompressed data is a full 127 signs (1 byte). if any longer, we'll lose signs.
            if(uncompressedCount == windowLen){
                output[uncompressedId] = (byte)-windowLen;
                // Reset counts
                uncompressedId = -1;
                uncompressedCount = 0;
                outputCount++;
            }
            readCount++;
        }
        //Any leftover data
        if(uncompressedCount > 0){
            output[uncompressedId] = (byte)-uncompressedCount;
            outputCount++;
        }
        fileUtil.writeFile(outputFileName,output,outputCount);
    }


    private void expand(byte[] arr,int len){
        int newlen = arr.length*len;
        byte[] copy = new byte[newlen];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }



    private String convertToString(byte[] pattern, int startIndex, int count) {
        String s = "";
        for (int i = startIndex; i < startIndex + count; i++) {
            s += (char)(pattern[i]);
        }
        return s;
    }
    public static void main(String[] args) {
        Zipper zipper = new Zipper(127); // Window lenght of 127 bits or
        zipper.initData("/data/input/opg12.txt");
        //zipper.initData("/data/input/problem.txt");
        //zipper.initData("/data/input/diverse.pdf");
        //zipper.initData("/data/input/diverse.txt");
        zipper.lempelZiv("/data/output/compressed.txt");
    }
}
