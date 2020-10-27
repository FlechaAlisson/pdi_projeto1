

import java.math.BigDecimal;
import java.math.RoundingMode;
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
     * Essa função vai retornar um array de byte.
     */

    public byte[] descomprime(Map<Byte, BigDecimal> map, ArrayList<Integer> fileCompressed, boolean verbose, int tam) {
        Util u = new Util();
        ArrayList<Byte> arrayByte = new ArrayList();
        float high = 9999;
        float low = 0;
        float code = u.getCode(fileCompressed);
        double index;
        BigDecimal low_freq = new BigDecimal(0);

        /**Pega o primeira frequencia do map
         * */
        BigDecimal high_freq = map.entrySet().iterator().next().getValue();

        /**Pega o primeiro byte do Map
         * **/
        Byte abyte = map.entrySet().iterator().next().getKey();
        int i = 0;
        while (arrayByte.size() < tam) {
            if (verbose) System.out.println("=====================");
            i++;
            index = ((((code - low) + 1) * 10 - 1) / (high - low + 1))/10.0;




            /**
             * Começa a varrer o Map em busca de algum dos intervalos onde o index
             * esteja dentro
             * */
            Iterator it = map.keySet().iterator();
            while (it.hasNext() && !((low_freq.doubleValue() <= index) && (index  < high_freq.doubleValue()))) {
                Byte k = (Byte) it.next();
                abyte = k;
                low_freq = map.get(u.getLowerKey(map, k));
                if (low_freq == null) low_freq = BigDecimal.ZERO;

                high_freq = map.get(k);

            }

            /**
             * Adiciona o byte no vetor de saida
             **/
            arrayByte.add(abyte);

            float low_aux = low;
            low =  (int) (low_aux + ((high - low_aux + 1) * (low_freq.doubleValue() * 10 )) / 10);
            high = (int) (low_aux + ((high - low_aux + 1) * (high_freq.doubleValue() * 10 )) / 10) - 1;

            int ultimoDigitoHigh = (int) high / 1000;
            int ultimoDigitoLow = (int)low / 1000;

            /**
             * Testa o underflow,
             * Se tiver ocorrido o underflow, atualiza o High, o Low
             * e o Code
             **/

            int segundoDigitoHigh = u.getSegundoDigito((int) high);
            int segundoDigitoLow= u.getSegundoDigito((int) low);

            /**
             * Solução para o underflow
             **/
            while ((ultimoDigitoHigh - ultimoDigitoLow == 1) &&
                    (segundoDigitoHigh == 0 && segundoDigitoLow == 9)) {

                String high_string = u.checkLength(String.valueOf((int) high));
                String low_string =  u.checkLength(String.valueOf((int) low));
                /**
                 * Cria uma nova string onde retira-se o segundo digito.
                 **/
                String newHigh = high_string.substring(0,1) + high_string.substring(2) + "9";
                String newLow = low_string.substring(0,1) + low_string.substring(2) + "0";
                /**
                 * transforma a nova string em integer,
                 * e o atribui o high e o low.
                 **/
                high = Integer.parseInt(newHigh);
                low = Integer.parseInt(newLow);

                ultimoDigitoHigh = (int) high / 1000;
                ultimoDigitoLow = (int)  low / 1000;
                segundoDigitoHigh = u.getSegundoDigito((int) high);
                segundoDigitoLow= u.getSegundoDigito((int) low);
                if (verbose){
                    System.out.println("Underflow");
                    System.out.println("\tnew_high: " + high + "\n\tnew_low: "+low);
                }

            }


            while (ultimoDigitoHigh == ultimoDigitoLow) {
                ultimoDigitoHigh *= 1000;
                ultimoDigitoLow *= 1000;
                high = (high - ultimoDigitoHigh) * 10 + 9;
                low = (low - ultimoDigitoLow) * 10;

                fileCompressed.remove(0);
                code = u.getCode(fileCompressed);
                ultimoDigitoHigh = (int) high / 1000;
                ultimoDigitoLow =(int) low / 1000;
                if (verbose) System.out.print("shift_left | ");





            }
            if (verbose) {
                System.out.println("");
                System.out.println("lowfreq: " + low_freq + "\nhighfreq: " + high_freq);
                System.out.println("newlow: " + low + "\nnewhigh: " + high);
            }

        }

        /***
         * Transforma o Arraylist em vetor.
         */

        Object[] objects = arrayByte.toArray();
        byte[] final_array = new byte[objects.length];
        for (int j = 0; j < objects.length; j++) {
            final_array[j] = (byte) objects[j];
        }


        return final_array;
    }


    public ArrayList<Integer> comprimeFile(ArrayList<Byte> arrayByte, Map<Byte, BigDecimal> map, boolean verbose) {
        int high = 9999;
        int low = 0;
        Util u = new Util();
        ArrayList<Integer> saida = new ArrayList<>();
        int underflow_counter = 0;
        int ultimoDigitoUnderflowlow = 0;
        int ultimoDigitoUnderflowHigh = 9999;


        int i = 1;
        for (Byte b : arrayByte) {

            i++;
            BigDecimal prob_inicial;


            /**
             * Tenta acessar a chave anterior a 'b', se causar uma exception de ponteiro para Null,
             * então atribui 0. Só ocorre caso esteja processando o byte correspondente a primeira chave
             * do Map.
             */

            prob_inicial = map.get(u.getLowerKey(map, b));
            if (prob_inicial == null) prob_inicial = BigDecimal.ZERO;

            BigDecimal prob_final = map.get(b);


            if (verbose) {
                System.out.println("===========================================================");
                System.out.println(b);
                System.out.println("high: " + high);
                System.out.println("low: " + low);
                System.out.println("prob_inicial: " + prob_inicial);
                System.out.println("prob_final: " + prob_final);
            }
            double old_low = low;
            low = (int)(old_low + (high - old_low + 1) * prob_inicial.doubleValue());
            high = (int)(old_low + (high - old_low + 1) * prob_final.doubleValue()) - 1;

            if (verbose){
                System.out.println("new_high: " + high);
                System.out.println("new_low: " + low);
            }

            int ultimoDigitoHigh = (int) high / 1000;
            int ultimoDigitoLow = (int)  low / 1000;


            int segundoDigitoHigh = u.getSegundoDigito((int) high);
            int segundoDigitoLow= u.getSegundoDigito((int) low);

            /**
             * Solução para o underflow
             **/
            while ((ultimoDigitoHigh - ultimoDigitoLow == 1) &&
                    (segundoDigitoHigh == 0 && segundoDigitoLow == 9)) {


                String high_string = u.checkLength(String.valueOf((int) high));
                String low_string =  u.checkLength(String.valueOf((int) low));
                underflow_counter++;

                /**
                 * Guarda o ultimo digito pra depois saber
                 * qual valor adicionar na saída.
                 **/
                ultimoDigitoUnderflowlow = ultimoDigitoLow;
                ultimoDigitoUnderflowHigh = ultimoDigitoHigh;
                /**
                 * Cria uma nova string onde retira-se o segundo digito.
                 **/
                String newHigh = high_string.substring(0,1) + high_string.substring(2) + "9";
                String newLow = low_string.substring(0,1) + low_string.substring(2) + "0";


                /**
                 * transforma a nova string em integer,
                 * e o atribui o high e o low.
                 **/
                high = Integer.parseInt(newHigh);
                low = Integer.parseInt(newLow);

                ultimoDigitoHigh = (int) high / 1000;
                ultimoDigitoLow = (int)  low / 1000;
                segundoDigitoHigh = u.getSegundoDigito((int) high);
                segundoDigitoLow= u.getSegundoDigito((int) low);
                if (verbose){
                    System.out.println("Underflow");
                    System.out.println("\tnew_high: " + high + "\n\tnew_low: "+low);
                }

            }
            while (((ultimoDigitoHigh == ultimoDigitoLow))) {

                saida.add(ultimoDigitoLow);

                /**
                 * Testa se o ocorreu o segundo caso de underflow,
                 * caso tenha acontecido, verifica pra qual o ultimo digito covergiu
                 * case seja o High, coloca "9", caso seja o low, coloca "0"
                 **/
                if (underflow_counter != 0) {
                    if (ultimoDigitoUnderflowHigh == ultimoDigitoHigh)
                        for (int i_counter = 0; i_counter < underflow_counter; i_counter++) saida.add(9);
                    if (ultimoDigitoUnderflowlow == ultimoDigitoLow)
                        for (int i_counter = 0; i_counter < underflow_counter; i_counter++) saida.add(0);

                    underflow_counter = 0;
                }

                if (verbose) {
                    System.out.println("Shift left");
                }
                ultimoDigitoHigh *= 1000;
                ultimoDigitoLow *= 1000;

                high = (high - ultimoDigitoHigh) * 10 + 9;
                low = (low - ultimoDigitoLow) * 10;

                if (verbose) System.out.println("\tnew_high: " + high + "\n\tnew_low: "+low);

                ultimoDigitoHigh = (int)  high / 1000;
                ultimoDigitoLow = (int)  low / 1000;
            }

            System.out.println("Saida: " + saida);
        }






        u.getLow(saida,low);

        if (verbose) System.out.println(saida);

        return saida;
    }
}
