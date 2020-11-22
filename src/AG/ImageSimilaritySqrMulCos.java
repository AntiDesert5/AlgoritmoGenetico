package AG;

import static java.lang.Math.sqrt;

public class ImageSimilaritySqrMulCos extends ImageSimilarity {

    @Override
    public double distanceRGB(int r1, int g1, int b1, int r2, int g2, int b2) {

        int deltaR = r1 - r2;
        int deltaG = g1 - g2;
        int deltaB = b1 - b2;

        return 	( deltaR * deltaR
                + deltaG * deltaG
                + deltaB * deltaB )
                * ( 2 - ( r1 * r2 + g1 * g2 + b1 * b2 )
                / ( sqrt(r1 * r1 + g1 * g1 + b1 * b1) * sqrt(r2 * r2 + g2 * g2 + b2 * b2) + 1 ) );
    }

}
