

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

    public byte[] descomprime(Map<Byte, Double> map, ArrayList<Integer> fileCompressed, boolean verbose, int tam) {
        Util u = new Util();
        ArrayList<Byte> arrayByte = new ArrayList();
        float high = 9999;
        float low = 0;
        float code = u.getCode(fileCompressed);
        double index;
        double low_freq = 0;

        /**Pega o primeira frequencia do map
         * */
        double high_freq = map.entrySet().iterator().next().getValue();

        /**Pega o primeiro byte do Map
         * **/
        Byte abyte = map.entrySet().iterator().next().getKey();
        int i = 0;
        while (arrayByte.size() < tam) {
            if (verbose) System.out.println("=====================");
            i++;
            index =  ((((code - low) + 1) * 10 - 1) / (high - low + 1))/10.0;


            /**
             * Começa a varrer o Map em busca de algum dos intervalos onde o index
             * esteja dentro
             * */
            Iterator it = map.keySet().iterator();
            while (it.hasNext() && !((low_freq <= index) && (index  < high_freq))) {
                Byte k = (Byte) it.next();
                abyte = k;
                try {
                    low_freq = map.get(u.getLowerKey(map, k));
                } catch (NullPointerException e) {
                    low_freq = 0;
                }
                high_freq = map.get(k);

            }

            /**
             * Adiciona o byte no vetor de saida
             **/
            arrayByte.add(abyte);

            float low_aux = low;
            low =  (int) (low_aux + ((high - low_aux + 1) * (low_freq * 10 )) / 10);
            high = (int) (low_aux + ((high - low_aux + 1) * (high_freq * 10 )) / 10) - 1;

            int ultimoDigitoHigh = (int) high / 1000;
            int ultimoDigitoLow = (int)low / 1000;

            /**
             * Testa o underflow,
             * Se tiver ocorrido o underflow, atualiza o High, o Low
             * e o Code
             **/


            if (ultimoDigitoHigh - ultimoDigitoLow == 1) {

                String high_string = String.valueOf((int) high);
                String low_string = String.valueOf((int) low);



                /**
                 * Checa se o numero é menor de que 999
                 * se for, coloca 0s a esquerda para que
                 * o numero tenha 4 caractéres.
                 **/
                high_string = u.checkLength(high_string);
                low_string = u.checkLength(low_string);

                /**
                 * Pega o segundo caractér.
                 **/
                int segundoDigitoHigh = Integer.parseInt((String.valueOf(high_string.charAt(1))));
                int segundoDigitoLow = Integer.parseInt((String.valueOf(low_string.charAt(1))));

                /**
                 * Testa se o valor do segundo digito é 0 e 9, pois dai se enquadra
                 * como um caso de underflow
                 **/
                if (segundoDigitoHigh == 0 && segundoDigitoLow == 9){
                    /**
                     * Cria uma nova string onde retira-se o segundo digito
                     * e acrescenta um 0 ou um 9 dependendo se é high ou se
                     * é low
                     **/

                    String newHigh = String.valueOf(high).substring(0,1) + String.valueOf(high).substring(2) + "9";
                    String newLow = String.valueOf(low).substring(0,1) + String.valueOf(low).substring(2) + "0";

                    /**
                     * transforma a nova string em integer,
                     * e o atribui o high e o low.
                     **/
                    high = Float.parseFloat(newHigh);
                    low = Float.parseFloat(newLow);
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
                System.out.print("shift_left | ");

            }
            System.out.println("");
            if (verbose) {

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


    public ArrayList<Integer> comprimeFile(ArrayList<Byte> arrayByte, Map<Byte, Double> map, boolean verbose) {
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
            double prob_inicial;


            /**
             * Tenta acessar a chave anterior a 'b', se causar uma exception de ponteiro para Null,
             * então atribui 0. Só ocorre caso esteja processando o byte correspondente a primeira chave
             * do Map.
             */
            try {
                prob_inicial = map.get(u.getLowerKey(map, b));
            } catch (NullPointerException e) {
                prob_inicial = 0;
            }
            double prob_final = map.get(b);


            if (verbose) {
                System.out.println("===========================================================");
                System.out.println(b);
                System.out.println("high: " + high);
                System.out.println("low: " + low);
                System.out.println("prob_inicial: " + prob_inicial);
                System.out.println("prob_final: " + prob_final);
            }
            int old_low = low;
            low = (int)(old_low + (high - old_low + 1) * prob_inicial);
            high = (int) (old_low + (high - old_low + 1) * prob_final) - 1;

            if (verbose){
                System.out.println("new_low: " + low);
                System.out.println("new_high: " + high);
            }

            int ultimoDigitoHigh = high / 1000;
            int ultimoDigitoLow = low / 1000;


            /**
             * Solução para o underflow
             **/


            if (ultimoDigitoHigh - ultimoDigitoLow == 1) {

                String high_string = String.valueOf(high);
                String low_string = String.valueOf(low);

                /**
                 * Caso os valores sejam menor do que 1000
                 * isso faz com que alguns deem erro
                 * EXEMPLO: 987, quando processado, o seu segundo digito vai o 8
                 * quando devia ser o 9, pois o numero deve ser cumputado como
                 * se fosse 0987.
                 **/

                high_string = u.checkLength(high_string);
                low_string = u.checkLength(low_string);


                /**
                 * Pega o segundo digito
                 **/
                int segundoDigitoHigh = Integer.parseInt((String.valueOf(high_string.charAt(1))));

                int segundoDigitoLow = Integer.parseInt((String.valueOf(low_string.charAt(1))));

                /**
                 * Testa se o valor do segundo digito é 0 e 9, pois dai se enquadra
                 * como o  underflow
                 **/

                if (segundoDigitoHigh == 0 && segundoDigitoLow == 9){
                    underflow_counter++;

                    /**
                     * Guarda o segundo digito pra depois saber
                     * qual valor adicionar na saída.
                     **/
                    ultimoDigitoUnderflowlow = ultimoDigitoLow;
                    ultimoDigitoUnderflowHigh = ultimoDigitoHigh;

                    /**
                     * Cria uma nova string onde retira-se o segundo digito.
                    **/
                    String newHigh = String.valueOf(high).substring(0,1) + String.valueOf(high).substring(2) + "9";
                    String newLow = String.valueOf(low).substring(0,1) + String.valueOf(low).substring(2) + "0";


                    /**
                     * transforma a nova string em integer,
                     * e o atribui o high e o low.
                     **/
                    high = Integer.parseInt(newHigh);
                    low = Integer.parseInt(newLow);

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

                }

                if (verbose) {
                    System.out.println("Shift left");
                }
                ultimoDigitoHigh *= 1000;
                ultimoDigitoLow *= 1000;

                high = (high - ultimoDigitoHigh) * 10 + 9;
                low = (low - ultimoDigitoLow) * 10;

                if (verbose) System.out.println("\tnew_high: " + high + "\n\tnew_low: "+low);

                ultimoDigitoHigh = high / 1000;
                ultimoDigitoLow = low / 1000;
            }
        }




        u.getLow(saida,low);

        if (verbose) System.out.println(saida);

        return saida;
    }
}
