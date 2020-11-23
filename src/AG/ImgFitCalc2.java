package AG;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImgFitCalc2 extends calculoimagenfitness {

    private List<vectorimagenGenetico> baseVectors;

    public ImgFitCalc2(BufferedImage sampleImage,
                       similitudImagen similitudImagen, List<vectorimagenGenetico> baseVectors) {
        super(sampleImage, similitudImagen);
        this.baseVectors = baseVectors;
    }

    @Override
    public double calcFit(ImageVector imageVector) {
        for( vectorimagenGenetico vect : baseVectors )
        {
            vect.paint( super.ImagenGraficos);
        }

        imageVector.paint( super.ImagenGraficos);
        double fit = super.calculosimilitudImagen.difference( sampleImage, operationalImage );//diferencia entre la imagen de muestra y la pasada (la que pasamos por parametro)
        super.ImagenGraficos.clearRect(0, 0, width, height);
        return fit;
    }

}
