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
        i.add(1234);
        i.add(5678);
        i.add(321);
        i.add(543);

        System.out.println(i);

        m.atualizaCode(i);
        System.out.println(i);
    }

    void atualizaCode(ArrayList<Integer> arrayList){
        int i, num_decimal_atual, num_decimal_prox, encaixe, primeiro_atual;
        int n_digitos_atual;
        int n_digitos_proximo;


        for(i = 0; i <= arrayList.size()-1 ; i++){

            n_digitos_atual = 0;
            n_digitos_proximo = 0;
            num_decimal_atual = num_decimal_prox = 1;

            //pega o numero de digitos indice atual
            for(int aux = arrayList.get(i); aux != 0 ; aux/=10, n_digitos_atual++);
            //pega o numeral decimal
            while (n_digitos_atual > 1){
                num_decimal_atual *= 10;
                n_digitos_atual--;
            }
            //pega o primeiro digito atual
            primeiro_atual = (arrayList.get(i) / num_decimal_atual);

            //modifica o indice atual
            arrayList.set(i, arrayList.get(i) - num_decimal_atual*primeiro_atual);

            //apenas registra o encaixe caso o indice atual seja menor que o ultimo indice
            if(i < arrayList.size()-1) {

                //pega o numero de digitos do proximo indice
                for (int aux = arrayList.get(i + 1); aux != 0; aux /= 10, n_digitos_proximo++) ;
                while (n_digitos_proximo > 1) {
                    num_decimal_prox *= 10;
                    n_digitos_proximo--;
                }
                //pega o primeiro do proximo
                encaixe = (arrayList.get(i + 1) / num_decimal_prox);
                arrayList.set(i, (arrayList.get(i) * 10) + encaixe);
            }
            //System.out.println(arrayList.get(i));
        }


    }


}
