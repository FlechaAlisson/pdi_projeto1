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

        BigDecimal aux = new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN);


        /**
         * Probabilidade acumulada
         */
        for (Map.Entry<Byte,Double> pair : prob.entrySet()
        ) {
            BigDecimal value = new BigDecimal(pair.getValue()).setScale(4, RoundingMode.HALF_EVEN);
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

    int getCode(ArrayList<Integer> code) {

        /**
         * Função para pegar os 4 primeiros digito do code
         */
        int value = code.get(0);

        if (code.size() <= 3){
            value = 0;
            for (Integer i : code) {
                value *= 10;
                value += i;
            }
        }
        else{
            for (int i = 1; i < 4; i++) {
                value *= 10;
                value += code.get(i);
            }
        }

    return value;
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


    public void getLow(ArrayList<Integer> saida, int low) {

        String aux = Integer.toString(low);

        for (int i = 0; i < aux.length(); i++) {
            saida.add(Character.getNumericValue(aux.charAt(i)));
        }


    }

    public String checkLength(String num) {
        if (num.length() <= 3){
            for (int j = 0; num.length() < 4  ; j++) {
                num = "0" + num;
            }
        }
        return num;
    }
}
