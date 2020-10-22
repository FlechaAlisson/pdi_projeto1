import java.math.BigDecimal;
import java.math.RoundingMode;
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

        BigDecimal aux = new BigDecimal(0).setScale(5, RoundingMode.HALF_EVEN);


        /**
         * Probabilidade acumulada
         */
        for (Map.Entry<Byte,Double> pair : prob.entrySet()
        ) {
            BigDecimal value = new BigDecimal(pair.getValue()).setScale(5, RoundingMode.HALF_EVEN);
            aux = aux.add(value);
            if (aux.doubleValue() > 1) aux = BigDecimal.valueOf(1);
            pair.setValue(aux.doubleValue());
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

                String digito_sem_primeiro = arrayList.get(i).substring(1);
                String primeiro_digito_prox = null;

                primeiro_digito_prox = arrayList.get(i + 1).charAt(0) + "";
                arrayList.set(i, digito_sem_primeiro.concat(primeiro_digito_prox));


                if((i + 1) == arrayList.size()-1) {
                    String prox_digito_sem_primeiro = arrayList.get(i + 1).substring(1);
                    arrayList.set(i + 1, prox_digito_sem_primeiro);
                    if(arrayList.get(i + 1).isEmpty())
                        arrayList.remove(arrayList.size() - 1);
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



    void procurarErros(byte[] fileAntigo, byte[] fileNovo)
    {
        int erros_total = 0;
        for (int i = 0; i < fileAntigo.length; i++) {
            if (fileAntigo[i] != fileNovo[i]) {
                System.out.println("=====================");
                System.out.println("Erro no byte: " + fileNovo[i] +
                        "\nDevia Ser: " + fileAntigo[i] +
                        "\nPosicao: " + i);
                erros_total++;
            }
        }
        System.out.println("TOTAL DE ERROS: " + erros_total);
    }



}
