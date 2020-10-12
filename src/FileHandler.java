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

        ByteArrayOutputStream bout = new ByteArrayOutputStream(saida.size() * 4);
        DataOutputStream dout = new DataOutputStream(bout);
        for (int i : saida
        ) {
            try {
                dout.writeInt(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("saida.art");
            bout.writeTo(fout);
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
