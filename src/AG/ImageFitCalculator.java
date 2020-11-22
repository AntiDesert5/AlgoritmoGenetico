package AG;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFitCalculator {

    protected BufferedImage sampleImage;
    protected int width;
    protected int height;
    protected BufferedImage operationalImage;
    protected Graphics2D operationalImageGraphics;
    protected ImageSimilarity imageSimilarityCalculator;

    /**
     *
     * @param sampleImage (!) тип зображення повинен бути BufferedImage.TYPE_INT_RGB
     * @param imageSimilarity клас, який реалізує механізм порівняння подібності зображень
     */
    public ImageFitCalculator( BufferedImage sampleImage, ImageSimilarity imageSimilarity ) {
        this.sampleImage = sampleImage;
        this.imageSimilarityCalculator = imageSimilarity;

        operationalImage = new BufferedImage( sampleImage.getWidth(), sampleImage.getHeight(),
                BufferedImage.TYPE_INT_RGB );
        width = sampleImage.getWidth();
        height = sampleImage.getHeight();
        operationalImageGraphics = (Graphics2D) operationalImage.getGraphics();

        operationalImageGraphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
    }

    public double calcFit( ImageVector imageVector ) {
        imageVector.paint( operationalImageGraphics );
        double fit = imageSimilarityCalculator.difference( sampleImage, operationalImage );
        operationalImageGraphics.clearRect(0, 0, width, height);
        return fit;
    }

}
