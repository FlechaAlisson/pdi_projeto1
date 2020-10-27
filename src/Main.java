import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;


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

        Map<Byte, BigDecimal> prob = u.getModeloProb(file);

        //transforma o vetor de bytes em um arraylist
        for (byte b : file) {
            arrayByte.add(b);
        }







        ArrayList<Integer> saida = compressorAritmetico.comprimeFile(arrayByte,prob, verbose);

        System.out.println("comprensão finalizada");

        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de execucao da codificação: " + (double) ((fim - inicio)) + " milissegundos" );

        /*try {
            f.writeFile(saida, prob,file.length);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*Object[] aux = new Object[2];
        try {
            aux = f.readCompressedFile("saida.art");
        } catch (IOException e) {
            e.printStackTrace();
        }*/


       /*ArrayList<String> arrayListString = new ArrayList();
        ArrayList<Integer> arrayListInteger = (ArrayList<Integer>) aux[1];*/


        /*for (Integer i :
                arrayListInteger) {
            arrayListString.add(i.toString());

        }*/

        byte[] finalFile = compressorAritmetico.descomprime(prob,saida, verbose, file.length);

        f.writeFile("saida.bmp",finalFile);

        fim  = System.currentTimeMillis();
        System.out.println("Tempo de execucao: " + (double) ((fim - inicio)) + " milissegundos" );


        u.procurarErros(file, finalFile);
    }
}
