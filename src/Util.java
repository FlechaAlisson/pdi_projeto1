import java.util.*;
import java.util.stream.Collectors;

public class Util {
    public Byte getLowerKey(Map map, Byte key){
        /**É suposto pegar a primeira key*/

        Byte lowerKey = (Byte) map.keySet().iterator().next();
        if (lowerKey == key) return null;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()){
            Byte k = (Byte) it.next();
            if (k == key) break;

            lowerKey = k;
        }
        return lowerKey;
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
        */
        prob.forEach((k,v) -> prob.put(k, v/file.length));

        double aux = 0;


        /**
         * Probabilidade acumulada
        */
        for (Map.Entry<Byte,Double> pair : prob.entrySet()
        ) {
            aux+= pair.getValue();
            if (aux >= 1) aux = 1;
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

    int getCode(String code){

        /**
         * Função para pegar os 4 primeiros digito do code
         */
        String value = null;

        switch (code.length()){
            case 1 :
                value = code.substring(0,1);
                break;
            case 2 :
                value = code.substring(0,2);
                break;
            case 3 :
                value = code.substring(0,3);
                break;
            default: value = code.substring(0,4);

        }

    return Integer.valueOf(value);
    }

    void atualizaCode(ArrayList<String> arrayList){


        if (arrayList.size() >= 2){
            for (int i = 0; i < arrayList.size() - 1; i++) {
                System.out.println(arrayList);
                String digito_sem_primeiro = arrayList.get(i).substring(1);


                String primeiro_digito_prox = null;
                if (arrayList.get(i + 1).length() != 0) primeiro_digito_prox = arrayList.get(i + 1).substring(0, 1);
                else primeiro_digito_prox = arrayList.get(i + 1);

                arrayList.set(i, digito_sem_primeiro.concat(primeiro_digito_prox));


                try{
                    String prox_digito_sem_primeiro = arrayList.get(i + 1).substring(1);
                    arrayList.set(i + 1, prox_digito_sem_primeiro);

                }catch (StringIndexOutOfBoundsException e){
                    arrayList.remove(arrayList.size()-1);
                }
            }

        }else {
            try {
                String digito_sem_primeiro = arrayList.get(0).substring(1);
                arrayList.set(0, digito_sem_primeiro);
            }catch (StringIndexOutOfBoundsException e) {
                arrayList.remove(0);
                return;
            }
        }

    }




    /*
    void atualizaCode(ArrayList<Integer> arrayList){
        int i, num_decimal_atual, num_decimal_prox, encaixe, primeiro_atual;
        int n_digitos_atual;
        int n_digitos_proximo;




        if (arrayList.get(arrayList.size() - 1) == 0) {
            try {
                /**
                 * Remove o ultimo elemento, pois ele é 0
                 * testa se é o único elemento.

                arrayList.remove(arrayList.size() - 1);
                if (arrayList.size() == 0) return;
                /**
                 * Se não for, pega o penultimo elemento e o multiplica por 10,
                 * isso serve pra "levar" o zero pro proximo elemento
                int aux = arrayList.get(arrayList.size()-1);
                arrayList.set(arrayList.size()-1, aux * 10);

            }catch (IndexOutOfBoundsException e){
            }
        }


        for(i = 0; i <= arrayList.size()-1 ; i++){



            n_digitos_atual = 0;
            n_digitos_proximo = 0;
            num_decimal_atual = num_decimal_prox = 1;

            /**
             * Pega o numero de digitos indice atual

            for(int aux = arrayList.get(i); aux != 0 ; aux/=10, n_digitos_atual++);
            //pega o numeral decimal
            while (n_digitos_atual > 1){
                num_decimal_atual *= 10;
                n_digitos_atual--;
            }
            /**
             * Pega o primeiro digito atual

            primeiro_atual = (arrayList.get(i) / num_decimal_atual);

            /**
             * Modifica o indice atual

            arrayList.set(i, arrayList.get(i) - num_decimal_atual*primeiro_atual);

            /**
             * Apenas registra o encaixe caso o indice atual seja menor que o ultimo indice

            if(i < arrayList.size()-1) {

                pega o numero de digitos do proximo indice
                for (int aux = arrayList.get(i + 1); aux != 0; aux /= 10, n_digitos_proximo++) ;
                while (n_digitos_proximo > 1) {
                    num_decimal_prox *= 10;
                    n_digitos_proximo--;
                }
                /**
                 * Pega o primeiro do proximo

                encaixe = (arrayList.get(i + 1) / num_decimal_prox);
                arrayList.set(i, (arrayList.get(i) * 10) + encaixe);
            }



        }


    }*/

    void procurarErros(byte[] fileAntigo, byte[] fileNovo)
    {
        int erros_total = 0;
        for (int i = 0; i < fileAntigo.length; i++) {
            if (fileAntigo[i] != fileNovo[i]) {
                System.out.println("Erro no byte: " + fileNovo[i] +
                        "\nDevia Ser: " + fileAntigo[i] +
                        "\nPosicao: " + i);
                erros_total++;
            }
        }
        System.out.println("TOTAL DE ERROS: " + erros_total);
    }



}
