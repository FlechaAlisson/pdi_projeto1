import java.io.*;

import java.nio.file.Files;

public class fileHandler {

    public byte[] readFile(String path){
        byte [] data = null;
        try {
            FileInputStream fileInputStream = new FileInputStream( new File(path));
            BufferedInputStream reader = new BufferedInputStream(fileInputStream);

            data = reader.readAllBytes();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }



}
