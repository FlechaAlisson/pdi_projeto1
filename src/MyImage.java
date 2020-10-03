import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyImage
{
    private BufferedImage image;

    public MyImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public static void main(String args[])throws IOException
    {
        fileHandler m = new fileHandler();
        BufferedImage image = m.readImage("./src/lenna.jpeg/");
        MyImage myImage = new MyImage(image);
        Map<Integer, Integer> ocorrencias = new HashMap<>();
        Map<Integer, Float> prob = new HashMap<>();

        int low = 0000;
        int high = 9999;


        for (int x = 0; x < myImage.getImage().getWidth(); x++) {
            for (int y = 0; y < myImage.getImage().getHeight(); y++) {
                if (!ocorrencias.containsKey(myImage.image.getRGB(x,y))){

                    ocorrencias.put(myImage.image.getRGB(x,y),1);
                }else{
                    int newValue = ocorrencias.get(myImage.image.getRGB(x,y)) + 1;
                    ocorrencias.put(myImage.image.getRGB(x,y),newValue);
                }
            }
        }

        Iterator it = ocorrencias.keySet().iterator();

         while (it.hasNext()){
            Object key = it.next();
            Float value = Float.valueOf(ocorrencias.get(key));

            prob.put((Integer) key,
                    (float) (value / (myImage.getImage().getHeight()*myImage.getImage().getWidth())));

        }

        System.out.println(prob);

        //m.writeImage("./src/pessoa.jpg/", myImage.getImage());
    }
}//class ends here 