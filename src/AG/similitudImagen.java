package AG;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class similitudImagen {

    private int[] rgb1 = new int[3];
    private int[] rgb2 = new int[3];

    public abstract double distanceRGB ( int r1, int g1, int b1, int r2, int g2, int b2 );

    public double difference(BufferedImage img1, BufferedImage img2 ) {//calculamos la diferencia entre los pixels y de ahi obtenemos la funcion fitness
        double ret = 0;

        WritableRaster r1 = img1.getRaster();
        WritableRaster r2 = img2.getRaster();

        for ( int i = 0; i < img1.getWidth(); i ++ )
        {
            for ( int j = 0; j < img1.getHeight(); j ++ )//tamaño ancho - largo
            {
                rgb1 = r1.getPixel( i, j, rgb1 );
                rgb2 = r2.getPixel( i, j, rgb2 );

                ret += distanceRGB( rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);//calculamos fistancia, si estan en el lugar correcto deberia ser 0
            }
        }

        return ret;
    }

}
