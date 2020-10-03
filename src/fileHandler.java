import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class fileHandler {

    public BufferedImage readImage(String path){
        int width = 10;    //width of the image
        int height = 10;   //height of the image

        // For storing image in RAM
        BufferedImage image = null;

        // READ IMAGE
        try
        {
            File input_file = new File(path); //image file path
            image = ImageIO.read(input_file);
            /* create an object of BufferedImage type and pass
               as parameter the width,  height and image int
               type.TYPE_INT_ARGB means that we are representing
               the Alpha, Red, Green and Blue component of the
               image pixel using 8 bit integer value. */
         /*   image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);*/

            // Reading input file
            image = ImageIO.read(input_file);

            System.out.println("Reading complete.");
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }finally {
            return image;
        }
    }


    public void writeImage(String s, BufferedImage image){

        // WRITE IMAGE
        try
        {
            // Output file path
            File output_file = new File(s);

            // Writing to file taking type and path as
            ImageIO.write(image, "jpg", output_file);

            System.out.println("Writing complete.");
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
}
}
