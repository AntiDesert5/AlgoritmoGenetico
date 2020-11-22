package AG;

import java.awt.image.BufferedImage;
import java.util.List;

public class ImgFitCalc2 extends ImageFitCalculator {

    private List<ImageVectorGenetic> baseVectors;

    public ImgFitCalc2(BufferedImage sampleImage,
                       ImageSimilarity imageSimilarity, List<ImageVectorGenetic> baseVectors) {
        super(sampleImage, imageSimilarity);
        this.baseVectors = baseVectors;
    }

    @Override
    public double calcFit(ImageVector imageVector) {
        for( ImageVectorGenetic vect : baseVectors )
        {
            vect.paint( super.operationalImageGraphics );
        }

        imageVector.paint( super.operationalImageGraphics );
        double fit = super.imageSimilarityCalculator.difference( sampleImage, operationalImage );
        super.operationalImageGraphics.clearRect(0, 0, width, height);
        return fit;
    }

}
