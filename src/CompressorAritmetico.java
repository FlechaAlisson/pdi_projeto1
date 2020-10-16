

import java.util.*;

public class CompressorAritmetico {

    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public CompressorAritmetico(byte[] file) {
        this.file = file;
    }


    /**
     *
     * @return Essa função vai retornar um array de byte.
     */

    public byte[] descomprime(Map<Byte, Double> map, ArrayList<Integer> saida, boolean verbose, int tam) {
        Util u = new Util();
        ArrayList<Byte> arrayByte = new ArrayList();
        int high = 9999;
        int low = 0;
        int code = u.getCode(saida.get(0));
        double index;
        double low_freq = 0;

        /*talvez inicializarele com o last*/
        double high_freq = map.entrySet().iterator().next().getValue();

        Byte abyte = map.entrySet().iterator().next().getKey();
        int i = 0;
        while (i < tam  && code != 0){
            if (verbose) System.out.println("=====================");
            i++;
            index = (double)(((code - low) + 1) * 10 - 1) / (high - low + 1);

            double index_aux = ((double) index/10);
            Iterator it = map.keySet().iterator();
            while (it.hasNext() && !((low_freq <= index_aux) && (index_aux < high_freq))){
                Byte k = (Byte) it.next();
                abyte = k;
                try {
                    low_freq = map.get(u.getLowerKey(map,k));
                }catch (NullPointerException e){
                    low_freq = 0;
                }
                high_freq = map.get(k);

            }

            arrayByte.add(abyte);

            int low_aux = low;
            low = (int) (low_aux + ((high - low_aux + 1) * (low_freq * 10)) / 10);
            high = (int) (low_aux + ((high - low_aux + 1) * (high_freq * 10)) / 10) - 1;

            int ultimoDigitoHigh = high/1000;
            int ultimoDigitoLow = low/1000;



            while (ultimoDigitoHigh == ultimoDigitoLow || high - low < 10)
            {

                ultimoDigitoHigh*= 1000;
                ultimoDigitoLow*= 1000;
                high = (high - ultimoDigitoHigh) * 10 + 9;
                low = (low - ultimoDigitoLow) * 10;

                u.atualizaCode(saida);
                ultimoDigitoHigh = high/1000;
                ultimoDigitoLow = low/1000;

            }
            if (verbose) {

                System.out.println("lowfreq: " + low_freq + "\nhighfreq: " + high_freq);
                System.out.println("newlow: " + low + "\nnewhigh: " + high);
            }

            try {
                if (((saida.get(0)) < 999) && saida.size() >= 2) saida.remove(0);
                code = u.getCode(saida.get(0));
            }catch (java.lang.IndexOutOfBoundsException e){}



        }

        Object[] objects = arrayByte.toArray();
        byte[] final_array = new byte[objects.length];
        for (int j = 0; j < objects.length ; j++) {
            final_array[j] = (byte) objects[j];
        }


        return  final_array;
    }


    public ArrayList<Integer> comprimeFile(ArrayList<Byte> arrayByte, Map<Byte, Double> map, boolean verbose) {
        int high = 9999;
        int low = 0;
        int underflow = 0;
        Util u = new Util();
        ArrayList<Integer> saida = new ArrayList<>();

        int i = 1;
        for (Byte b : arrayByte) {

            i++;
            double prob_inicial;


            /**
             * Tenta acessar a chave anterior a 'b', se causar uma exception de ponteiro para Null,
             * então atribui 0. Só ocorre caso esteja processando o byte correspondente a primeira chave
             * do Map.
             */
            try {
                prob_inicial = map.get(u.getLowerKey(map,b));
            }catch (NullPointerException e){
                prob_inicial = 0;
            }
            double prob_final = map.get(b);


            if (verbose) {
                System.out.println("===========================================================");
                System.out.println(b);
                System.out.println("high: "+ high);
                System.out.println("low: "+ low);
                System.out.println("prob_inicial: " + prob_inicial);
                System.out.println("prob_final: "+ prob_final);
            }
            int old_low = low;
            low = (int) (old_low + (high - old_low + 1) * prob_inicial);
            high = (int) (old_low + (high - old_low + 1) * prob_final ) - 1;



            if (verbose) {
                System.out.println("new_prob_inicial: " + prob_inicial);
                System.out.println("new_prob_final: "+ prob_final);
            }
            int ultimoDigitoHigh = high/1000;
            int ultimoDigitoLow = low/1000;



            /**
             * Testa o Underflow;
             * A variavel underflow é inicializada com 0, se for TRUE multiplica o valor de underflow
             * por 10 e soma o ultimo digito;
             * Utiliza os métodos da classe Math pra poder fazer a soma e a multiplicação, pois se
             * causar overflow, uma exception é acionada, fazendo com que o adicione o underflow
             * na lista e renicializa a variavel underflow com o ultimo digito.
             * */
            while (ultimoDigitoHigh == ultimoDigitoLow || (high - low) < 10){
                try{
                    underflow = Math.multiplyExact(underflow,10);
                    underflow = Math.addExact(underflow, ultimoDigitoHigh);
                }catch (java.lang.ArithmeticException e){
                    saida.add(underflow);
                    underflow = ultimoDigitoHigh;
                }
                ultimoDigitoHigh*= 1000;
                ultimoDigitoLow*= 1000;
                high = (high - ultimoDigitoHigh) * 10 + 9;
                low = (low - ultimoDigitoLow) * 10;

                ultimoDigitoHigh = high/1000;
                ultimoDigitoLow = low/1000;
                if (verbose) System.out.println("underflow");

            }
        }


            if(underflow != 0) saida.add(underflow);

            saida.add(low);

            if (verbose) System.out.println(saida);

            return saida;
    }



}
