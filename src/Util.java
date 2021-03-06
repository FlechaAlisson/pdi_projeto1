import java.math.BigDecimal;
import java.math.MathContext;
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



    public Map<Byte, BigDecimal> getModeloProb(byte[] file) {
        Map<Byte, BigDecimal> prob = new HashMap<>();


        for (byte b : file) {
            if (prob.containsKey(b)) {
                BigDecimal value = prob.get(b);
                prob.put(b, new BigDecimal(value.doubleValue() + 1));
            } else {
                prob.put(b, new BigDecimal(1));
            }
        }

        /**
         * Probabilidade
        */

        prob.forEach((k,v) -> prob.put(k, v.divide(BigDecimal.valueOf(file.length),MathContext.DECIMAL32)));

        BigDecimal aux = new BigDecimal(0);


        /**
         * Probabilidade acumulada
         */
        for (Map.Entry<Byte,BigDecimal> pair : prob.entrySet()
        ) {

            BigDecimal value = pair.getValue().setScale(3, RoundingMode.HALF_EVEN);
            aux = aux.add(value);
            if (aux.doubleValue() <= 1) pair.setValue(aux);
            else pair.setValue(BigDecimal.ONE);
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
        else {
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
                System.out.print("Byte original: " + fileAntigo[i] + ", Byte novo: " + fileNovo[i] +
                        ", pos: " + i +" | ");
                erros_total++;
            }
        }
        System.out.println("");
        System.out.println("TOTAL DE ERROS: " + erros_total);
        System.out.println("TOTAL DE BYTES: "+ fileAntigo.length);
    }


    public void getLow(ArrayList<Integer> saida, double low) {

        String aux = Integer.toString((int) low);

        for (int i = 0; i < aux.length(); i++) {
            saida.add(Character.getNumericValue(aux.charAt(i)));
        }


    }


    /**
     * Caso os valores sejam menor do que 1000
     * isso faz com que alguns deem erro
     * EXEMPLO: 987, quando processado, o seu segundo digito vai o 8
     * quando devia ser o 9, pois o numero deve ser cumputado como
     * se fosse 0987.
     **/
    public String checkLength(String num) {
        if (num.length() <= 3){
            for (int j = 0; num.length() < 4  ; j++) {
                num = "0" + num;
            }
        }
        return num;
    }

    public int getSegundoDigito(int number) {
        String number_string = String.valueOf(number);
        number_string = this.checkLength(number_string);
        return Integer.parseInt((String.valueOf(number_string.charAt(1))));
    }

}
