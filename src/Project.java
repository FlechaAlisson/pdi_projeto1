import java.util.*;

public class Project {

    int MAX_VALUE = 10000;
    int HIGH = MAX_VALUE - 1;
    int LOW = 0;



    ArrayList<Double> calculaProb(Map<Byte, Double> prob, int total){
        ArrayList<Double> frequencia = new ArrayList<>();

        Iterator it = prob.keySet().iterator();


        while (it.hasNext()){
            Object key = it.next();
            double aux = Double.valueOf(prob.get(key)) / total;



            frequencia.add(aux);

        }
        frequencia.sort(Double::compareTo);
        return frequencia;
    }

    ArrayList<Double> calculaProbAcumulada(ArrayList<Double> prob){

        ArrayList<Double> probAcumulada = new ArrayList<>();
        double aux = 0;

        for (Double i: prob
             ) {
            aux+= i;
            probAcumulada.add( aux);

        }

        probAcumulada.sort(Double::compareTo);
        return probAcumulada;

    }


    private void comprimeFile(ArrayList<Byte> file, ArrayList<Double> freq, int high, int low, ArrayList<Double> prob, int i) {
        if (!file.isEmpty()){

            int new_low = (int) (low + ( high - low + 1) * freq.get(0));
            int new_high = (int) (low + ( high - low + 1) * freq.get(freq.size() - 1) - 1);

            System.out.println("high: "+ high);
            System.out.println("low:  "+ low);
            file.remove(i);

            comprimeFile(file, freq, new_high, new_low, prob, file.size() - 1);
        }
    }



    public static void main(String[] args) {
        Project p = new Project();
        fileHandler f = new fileHandler();

        byte[] file = f.readFile("C:\\Users\\afcfl\\IdeaProjects\\projeto1_PDI\\src\\mto_pequena.png");

        Map<Byte,Double> ocorrencia = p.ocorrencia(file);
        //System.out.println(ocorrencia);
        ArrayList<Double> freq = p.calculaProb(ocorrencia, file.length);
        System.out.println(freq);
        ArrayList<Double> freqAcumulada = p.calculaProbAcumulada(freq);
        System.out.println(freqAcumulada);

        //System.out.println(freq);
        //System.out.println("=======");
        //System.out.println(freqAcumulada);


        //System.out.println(freq);


        //transforma o vetor de bytes em um arraylist
        ArrayList<Byte> arrayByte = new ArrayList<Byte>();
        for (int i = 0; i < file.length; i++) {
            arrayByte.add(file[i]);
        }
        p.comprimeFile(arrayByte,freqAcumulada,p.HIGH,p.LOW,freq,arrayByte.size()-1);

    }

    private Map<Byte, Double> ocorrencia(byte[] file) {
        Map<Byte, Double> prob = new HashMap<>();
        for (int i = 0; i < file.length; i++) {
            if (prob.containsKey(file[i])){
                prob.put(file[i], prob.get(file[i]) + 1);
            }else {
                prob.put(file[i], (double) 1);
            }
        }
        return prob;
    }

}
