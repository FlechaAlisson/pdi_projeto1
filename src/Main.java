import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.pow;

public class Main {

    public static void main(String[] args) {
       /* long inicio = System.currentTimeMillis();
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




        */

        Main m = new Main();
        ArrayList<Integer> i = new ArrayList();
        i.add(5234);
        i.add(5678);
        i.add(9);
        System.out.println(i);

        //m.atualizaCode(i);
        System.out.println(i);
    }

//    void atualizaCode(ArrayList<Integer> arrayList){
//        int numero = arrayList.get(0);
//        int ultimoDigito = numero;
//        int qt =0;
//
//        /**
//        Pega o ultimo número do primeiro digito
//         */
//        while (ultimoDigito >= 10) {
//            ultimoDigito /= 10;
//            qt++;
//        }
//
//        /**
//        * Atualiza o primeiro numero
//        * */
//        arrayList.set(0, (int) ((numero) - (pow(10,qt)) * ultimoDigito));
//
//        numero = arrayList.get(1);
//        ultimoDigito = numero;
//        qt =0;
//
//        for (int i = 2; i <= arrayList.size()-1; i++) {
//            while (ultimoDigito >= 10) {
//                ultimoDigito /= 10;
//                qt++;
//            }
//
//            /*
//             * Atualiza o valor e puxa
//             */
//
//            numero = arrayList.get(i);
//            ultimoDigito = numero;
//            System.out.println(numero);
////            qt =0;
//        }
//
//    }


}
