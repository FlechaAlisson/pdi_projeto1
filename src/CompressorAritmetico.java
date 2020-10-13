import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public void descomprime(Map<Byte, Double> ocorrencia, ArrayList<Integer> saida) {
        
    }


    public ArrayList<Integer> comprimeFile(ArrayList<Byte> arrayByte, Map<Byte, Double> ocorrencia, boolean verbose) {
        int high = 9999;
        int low = 0;
        int underflow = 0;
        ArrayList<Integer> saida = new ArrayList<>();
        NavigableMap <Byte, Double> myMap = transformaMapNavigableMap(ocorrencia);

        for (Byte b : arrayByte) {

            double prob_inicial;
            try {
                prob_inicial = myMap.get(myMap.lowerKey(b));
            }catch (NullPointerException e){
                prob_inicial = 0;
            }
            double prob_final = myMap.get(b);


            if (verbose) {
                System.out.println("===========================================================");
                System.out.println(b);
                System.out.println("high: "+ high);
                System.out.println("low: "+ low);
                System.out.println("prob_inicial: " + prob_inicial);
                System.out.println("prob_final: "+ prob_final);
            }
            low = (int) (low + (high - low + 1) * prob_inicial);
            high = (int) (low + (high - low + 1) * prob_final - 1);



            if (verbose) {
                System.out.println("new_prob_inicial: " + prob_inicial);
                System.out.println("new_prob_final: "+ prob_final);
            }
            int ultimoDigitoHigh = high/1000;
            int ultimoDigitoLow = low/1000;



            /**
             * Testa o Underflow, Caso seja TRUE
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
            if (verbose) {
                System.out.println("new_high: "+ high);
                System.out.println("new_low: "+ low);
            }
        }
            while (low % 10 == 0){
                low/=10;
            }

            if(underflow != 0){
                saida.add(underflow);
            }
            saida.add(low);
            if (verbose) System.out.println(saida);

            return saida;
    }

    private NavigableMap<Byte, Double> transformaMapNavigableMap(Map<Byte, Double> ocorrencia) {
            NavigableMap<Byte, Double> newMap = new TreeMap<>();

        for (Map.Entry<Byte,Double> pair : ocorrencia.entrySet()
        ) {
                newMap.put(pair.getKey(),pair.getValue());
        }

            return newMap;
    }


    public Map<Byte, Double> prob(byte[] file) {
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

}
