import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.pow;

public class Main {

    public static void main(String[] args) {


        long inicio = System.currentTimeMillis();
        FileHandler f = new FileHandler();

        byte[] file = f.readFile(args[0]);
        boolean verbose = false;
        if (args.length == 2) verbose = args[1].equals("verbose");
        Util u = new Util();
        ArrayList<Byte> arrayByte = new ArrayList<>();

        CompressorAritmetico compressorAritmetico = new CompressorAritmetico(file);

        Map<Byte,Double> ocorrencia = u.getModeloProb(file);

        //transforma o vetor de bytes em um arraylist
        for (byte b : file) {
            arrayByte.add(b);
        }

/*        arrayByte.clear();
        arrayByte.add((byte) 5);
        arrayByte.add((byte) 4);
        arrayByte.add((byte) 3);
        arrayByte.add((byte) 5);
        arrayByte.add((byte) 5);
        arrayByte.add((byte) 1);
        arrayByte.add((byte) 2);
        arrayByte.add((byte) 3);
        arrayByte.add((byte) 5);
        arrayByte.add((byte) 5);


        ocorrencia.clear();
        ocorrencia.put((byte) 1, (double) 0.1);
        ocorrencia.put((byte) 2,0.2);
        ocorrencia.put((byte) 3,0.4);
        ocorrencia.put((byte) 4,0.5);
        ocorrencia.put((byte) 5, (double) 1);

        ocorrencia.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, LinkedHashMap::new));





*/
        ArrayList<Integer> saida = compressorAritmetico.comprimeFile(arrayByte,ocorrencia, verbose);


        try {
            f.writeFile(saida, ocorrencia,file.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[] aux = new Object[2];
        try {
            aux = f.readCompressedFile("saida.art");
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] finalFile = compressorAritmetico.descomprime((Map<Byte, Double>) aux[0],(ArrayList<Integer>)aux[1],verbose,(int) aux[2]);

        f.writeFile("mto_pequena.png",finalFile);

        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de execucao: " + (double) ((fim - inicio)) + " milissegundos" );


        System.out.println(saida);
        u.procurarErros(file, finalFile);



    }



}
