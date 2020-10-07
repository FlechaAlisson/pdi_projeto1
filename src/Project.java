import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Project {


     static Map<Byte, Double> CalculaFrequencia(byte[] file){
        Map<Byte, Double> ocorrencia = new HashMap<>();
        Map<Byte, Double> freq = new HashMap<>();


         for (int i = 0; i < file.length; i++) {
            if (ocorrencia.containsKey(file[i])){
                ocorrencia.put(file[i], ocorrencia.get(file[i]) + 1);
            }else {
                ocorrencia.put(file[i], (double) 1);
            }
        }

         Iterator it = ocorrencia.keySet().iterator();

         while (it.hasNext()){
             Object key = it.next();
             Double value = Double.valueOf(ocorrencia.get(key));

             freq.put((Byte) key, value / file.length);
         }
        return freq;

    }


    public static void main(String[] args) {
        fileHandler f = new fileHandler();

        byte[] file = f.readFile("C:\\Users\\afcfl\\IdeaProjects\\projeto1_PDI\\src\\mto_pequena.png");

        Map<Byte, Double> freq = CalculaFrequencia(file);

        Iterator it = freq.keySet().iterator();
        double test = 0;

        while (it.hasNext()){
            Object key = it.next();
            System.out.print(Double.valueOf(freq.get(key)));
            System.out.print(" + " + test+ " = ");
            test+= Double.valueOf(freq.get(key));
            System.out.println(test);

   }

        System.out.println(test);





    }
}
