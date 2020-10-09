import java.io.*;

import java.nio.file.Files;
import java.util.ArrayList;

public class FileHandler {

    public byte[] readFile(String path){
        byte [] data = null;
        try {
            FileInputStream fileInputStream = new FileInputStream( new File(path));
            BufferedInputStream reader = new BufferedInputStream(fileInputStream);

            data = reader.readAllBytes();


            reader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }


    public void writeFile(ArrayList<Integer> saida) {
        byte[] data = new byte[saida.size()];
        for (int i = 0; i < saida.size(); i++) {
            data[i] =  saida.get(i).byteValue();
        }

        try {
            FileOutputStream f = new FileOutputStream(new File("saida.art"));
            f.write(data,0,data.length);
            f.flush();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
