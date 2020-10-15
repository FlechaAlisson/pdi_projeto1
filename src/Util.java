import java.util.*;
import java.util.stream.Collectors;

public class Util {
    public NavigableMap<Byte, Double> transformaMapNavigableMap(Map<Byte, Double> ocorrencia) {
        NavigableMap<Byte, Double> newMap = new TreeMap<>();

        for (Map.Entry<Byte,Double> pair : ocorrencia.entrySet()
        ) {
            newMap.put(pair.getKey(),pair.getValue());
        }

        return newMap;
    }

    public Map<Byte, Double> getModeloProb(byte[] file) {
        Map<Byte, Double> prob = new HashMap<>();
        for (byte b : file) {
            if (prob.containsKey(b)) {
                prob.put(b, prob.get(b) + 1);
            } else {
                prob.put(b, (double) 1);
            }
        }

        /**
         * Probabilidade
         * */
        prob.forEach((k,v) -> prob.put(k, v/file.length));

        double aux = 0;


        /**
         * Probabilidade acumulada
         **/
        for (Map.Entry<Byte,Double> pair : prob.entrySet()
        ) {
            aux+= pair.getValue();
            pair.setValue(aux);
        }


        /**
         * Ordena o map
         */
        return prob.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, LinkedHashMap::new));
    }

    int getCode(int code){
        int final_code = 0;
        for (int i = 0; i < 4 ; i++) {
            int ultimo_digito = code;
            int j;
            for (j = 0; ultimo_digito >= 10; j++, ultimo_digito /= 10);
            code -= ultimo_digito * Math.pow( 10 , j);
            final_code = final_code * 10 + ultimo_digito;
            if ((code < 0)) break;
        }

    return final_code;
    }

    void atualizaCode(ArrayList<Integer> arrayList){
        int i, num_decimal_atual, num_decimal_prox, encaixe, primeiro_atual;
        int n_digitos_atual;
        int n_digitos_proximo;



        if (arrayList.get(arrayList.size() - 1) == 0) {
            arrayList.remove(arrayList.size() - 1);
            int aux = arrayList.get(arrayList.size()-1);
            arrayList.set(arrayList.size()-1, aux * 10);
        }


        for(i = 0; i <= arrayList.size()-1 ; i++){



            n_digitos_atual = 0;
            n_digitos_proximo = 0;
            num_decimal_atual = num_decimal_prox = 1;

            /**
             * Pega o numero de digitos indice atual
             * */
            for(int aux = arrayList.get(i); aux != 0 ; aux/=10, n_digitos_atual++);
            //pega o numeral decimal
            while (n_digitos_atual > 1){
                num_decimal_atual *= 10;
                n_digitos_atual--;
            }
            /**
             * Pega o primeiro digito atual
             * */
            primeiro_atual = (arrayList.get(i) / num_decimal_atual);

            /**
             * Modifica o indice atual
             * */
            arrayList.set(i, arrayList.get(i) - num_decimal_atual*primeiro_atual);

            /**
             * Apenas registra o encaixe caso o indice atual seja menor que o ultimo indice
             **/
            if(i < arrayList.size()-1) {

                //pega o numero de digitos do proximo indice
                for (int aux = arrayList.get(i + 1); aux != 0; aux /= 10, n_digitos_proximo++) ;
                while (n_digitos_proximo > 1) {
                    num_decimal_prox *= 10;
                    n_digitos_proximo--;
                }
                /**
                 * Pega o primeiro do proximo
                 * */
                encaixe = (arrayList.get(i + 1) / num_decimal_prox);
                arrayList.set(i, (arrayList.get(i) * 10) + encaixe);
            }



        }


    }


}
