package AG;

import java.awt.*;
import java.awt.image.BufferedImage;

public class calculoimagenfitness {

    protected BufferedImage sampleImage; //imagen que usuamos
    protected int width;//su ancho
    protected int height;
    protected BufferedImage operationalImage;//igual es de tipo de imagen
    protected Graphics2D ImagenGraficos;
    protected similitudImagen calculosimilitudImagen;

    public calculoimagenfitness(BufferedImage sampleImage, similitudImagen similitudImagen) {
        this.sampleImage = sampleImage;
        this.calculosimilitudImagen = similitudImagen;

        operationalImage = new BufferedImage( sampleImage.getWidth(), sampleImage.getHeight(),
                BufferedImage.TYPE_INT_RGB );
        width = sampleImage.getWidth();
        height = sampleImage.getHeight();
        ImagenGraficos = (Graphics2D) operationalImage.getGraphics();

        ImagenGraficos.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
    }

    public double calcFit( ImageVector imageVector ) {//calculo de fitness
        imageVector.paint(ImagenGraficos);
        double fit = calculosimilitudImagen.difference( sampleImage, operationalImage );
        ImagenGraficos.clearRect(0, 0, width, height);
        return fit;
    }

}
