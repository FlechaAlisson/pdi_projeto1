import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        FileHandler f = new FileHandler();

        byte[] file = f.readFile("./Fotos/teste.txt");

        CompressorAritmetico compressorAritmetico = new CompressorAritmetico(file);

        Map<Byte,Double> ocorrencia = compressorAritmetico.prob(file);

        //transforma o vetor de bytes em um arraylist
        ArrayList<Byte> arrayByte = new ArrayList<>();
        for (byte b : file) {
            arrayByte.add(b);
        }
        ArrayList<Integer> saida = compressorAritmetico.comprimeFile(arrayByte,ocorrencia, false);
        System.out.println(saida);
        System.out.println(arrayByte);
        System.out.println(ocorrencia);

        try {
            f.writeFile(saida, ocorrencia);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long fim  = System.currentTimeMillis();
        System.out.println("Tempo de execucao: " + (double) ((fim - inicio)) + " milissegundos" );

        Object[] aux = new Object[2];
        try {
            aux = f.readCompressedFile("saida.art");
        } catch (IOException e) {
            e.printStackTrace();
        }

        compressorAritmetico.descomprime((Map<Byte, Double>) aux[0],(ArrayList<Integer>)aux[1]);


    }


}
