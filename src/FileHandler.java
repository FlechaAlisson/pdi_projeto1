import java.io.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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


    public void writeFile(ArrayList<Integer> saida, Map<Byte, Double> ocorrencia) throws IOException {

        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File("saida.art"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        DataOutputStream dout = new DataOutputStream(f);


        //escreve no arquivo o tamanho do Map
        dout.writeInt(ocorrencia.size());

        for (Map.Entry<Byte,Double> pair : ocorrencia.entrySet()
        ) {
            dout.writeByte(pair.getKey());
            dout.writeDouble(pair.getValue());
        }

        dout.writeInt(saida.size());

        for (int i : saida
        ) {
            dout.writeInt(i);
        }
    }


    public Object[] readCompressedFile(String path) throws IOException {
        Object[] returno = new Object[2];

        Map<Byte, Double> ocorrencia = new HashMap<>();
        ArrayList<Integer> saida = new ArrayList();
        int size = 0;

        DataInputStream in = new DataInputStream(new FileInputStream(path));


            size = in.readInt();

        for (int i = 0; i < size; i++) {
            Byte key = in.readByte();
            Double value = in.readDouble();

            ocorrencia.put(key,value);
        }

        size = in.readInt();

        for (int i = 0; i < size; i++) {
            saida.add(in.readInt());
        }

        returno[0] = ocorrencia;
        returno[1] = saida;


        return returno;

    }
}
