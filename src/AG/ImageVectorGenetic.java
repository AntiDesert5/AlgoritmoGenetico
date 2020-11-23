package AG;

import java.util.Random;
import static java.lang.Math.random;

public class ImageVectorGenetic extends ImageVector
    	implements GeneticOptimizable<ImageVectorGenetic> {

        private double xMutRange = 0;
        private double yMutRange = 0;

        private double rMutRange = 0;
        private double gMutRange = 0;
        private double bMutRange = 0;
        private double aMutRange = 0;

        private ImageFitCalculator fitCalculator;

        private double width = 0;
        private double height = 0;

        private Random rnd = new Random();

	public ImageVectorGenetic( int polygonsCount, int vertexesCount, ImageFitCalculator fitCalculator ) {
            super( polygonsCount, vertexesCount );
            this.fitCalculator = fitCalculator;
        }

	public ImageVectorGenetic( ImageVectorGenetic prototype ) {
            super( prototype.polygonsCount, prototype.vertexesCount );

            double[] currCoords = super.coordinatesVector.getVector();
            double[] protoCoords = prototype.coordinatesVector.getVector();
            for ( int i = 0; i < currCoords.length; i++ )
            {
                currCoords[i] = protoCoords[i];
            }

            double[] currRGBA = super.rgbaVector.getVector();
            double[] protoRGBA = prototype.rgbaVector.getVector();
            for ( int i = 0; i < currRGBA.length; i++ )
            {
                currRGBA[i] = protoRGBA[i];
            }

            this.xMutRange = prototype.xMutRange;
            this.yMutRange = prototype.yMutRange;

            this.rMutRange = prototype.rMutRange;
            this.gMutRange = prototype.gMutRange;
            this.bMutRange = prototype.bMutRange;
            this.aMutRange = prototype.aMutRange;

            this.fitCalculator = prototype.fitCalculator;

            this.width = prototype.width;
            this.height = prototype.height;
        }

        public ImageVectorGenetic scale(double coeff) {
            ImageVectorGenetic ret = new ImageVectorGenetic(this);
            ret.coordinatesVector.scale(coeff);
            return ret;
        }

        @Override
        public void fillWithRandomCoords(int width, int height) {
            this.width = width;
            this.height = height;

            super.fillWithRandomCoords(width, height);
        }

        @Override
        public double fitness() {
            double fit = fitCalculator.calcFit(this);
            return fit;
        }

        @Override
        public ImageVectorGenetic crossover(ImageVectorGenetic associate, double pCrossover) {
            ImageVectorGenetic ret;
            double[] associateCoords;
            double[] associateRGBA;

            if ( random() > 0.5 )
            {
                ret = new ImageVectorGenetic(this);
                associateCoords = associate.coordinatesVector.getVector();
                associateRGBA = associate.rgbaVector.getVector();
            }
            else
            {
                ret = new ImageVectorGenetic(associate);
                associateCoords = this.coordinatesVector.getVector();
                associateRGBA = this.rgbaVector.getVector();
            }

            if ( pCrossover < 0 )
                return ret;

            double[] retCoords = ret.coordinatesVector.getVector();
            double[] retRGBA = ret.rgbaVector.getVector();

            int startIndex = (int) ( random() * ( super.polygonsCount - 1 ) );
            boolean exchange = true;
            for ( int i = startIndex; i < super.polygonsCount; i++ )
            {
                if ( exchange )
                {
                    int currPolygonStartIndex = i * super.vertexesCount * 2;
                    for ( int j = 0; j < super.vertexesCount; j++ )
                    {
                        retCoords[currPolygonStartIndex + j * 2] = associateCoords[currPolygonStartIndex + j * 2];
                        retCoords[currPolygonStartIndex + j * 2 + 1] = associateCoords[currPolygonStartIndex + j * 2 + 1];
                    }

                    retRGBA[i * 4] = associateRGBA[i * 4];
                    retRGBA[i * 4 + 1] = associateRGBA[i * 4 + 1];
                    retRGBA[i * 4 + 2] = associateRGBA[i * 4 + 2];
                    retRGBA[i * 4 + 3] = associateRGBA[i * 4 + 3];
                }

                if ( random() < pCrossover )
                {
                    exchange = !exchange;
                }
            }

            return ret;
        }

        @Override
        public ImageVectorGenetic mutate(double pMutation) {
            ImageVectorGenetic ret = new ImageVectorGenetic(this);

            double[] retCoords = ret.coordinatesVector.getVector();
            double[] retRGBA = ret.rgbaVector.getVector();

//		if ( random() < 0.2 )
//			mutations1(pMutation, retCoords, retRGBA);
//		else
//			mutations2(pMutation, retCoords, retRGBA);

            //mutations1(pMutation, retCoords, retRGBA);
            //mutations2(pMutation, retCoords, retRGBA);
            mutations3(pMutation, retCoords, retRGBA);

            return ret;
        }

        private void mutations2(double pMutation, double[] retCoords,
        double[] retRGBA) {

            for ( int i = 0; i < super.polygonsCount; i++ )
            {
                if ( random() < pMutation )
                {


                    int currPolygonStartIndex = i * super.vertexesCount * 2;

                    for ( int j = 0; j < super.vertexesCount; j++ )
                    {
                        retCoords[currPolygonStartIndex + j * 2] += (int)( ( random() - random() ) * 2 );
                        retCoords[currPolygonStartIndex + j * 2 + 1] += (int)( ( random() - random() ) * 2 );
                    }

                    retRGBA[i * 4] += (int)( ( random() - random() ) * 4 );
                    retRGBA[i * 4 + 1] += (int)( ( random() - random() ) * 4 );
                    retRGBA[i * 4 + 2] += (int)( ( random() - random() ) * 4 );
                    retRGBA[i * 4 + 3] += (int)( ( random() - random() ) * 2 );

                }
            }
        }

        //TODO
        private void mutations3(double pMutation, double[] retCoords,
        double[] retRGBA) {

            for ( int i = 0; i < super.polygonsCount; i++ )
            {
                if ( random() < pMutation )
                {
                    int currPolygonStartIndex = i * super.vertexesCount * 2;

                    int currColorMutation = rnd.nextInt(11);

//				int colMut = 20;
//				int coordMut = 3;

                    int colMut = 15;
                    int coordMut = 2;

                    switch (currColorMutation) {
                        case 0:
                        case 1:
                            retRGBA[i * 4] += rnd.nextInt(colMut) - rnd.nextInt(colMut);
                            break;
                        case 2:
                        case 3:
                            retRGBA[i * 4 + 1] += rnd.nextInt(colMut) - rnd.nextInt(colMut);
                            break;
                        case 4:
                        case 5:
                            retRGBA[i * 4 + 2] += rnd.nextInt(colMut) - rnd.nextInt(colMut);
                            break;
                        case 6:
                        case 7:
                            retRGBA[i * 4 + 3] += rnd.nextInt(colMut) - rnd.nextInt(colMut);
                            break;
                        case 8:
                            int dy = rnd.nextInt(coordMut * 2) - rnd.nextInt(coordMut * 2);
                            double rndm = Math.random();
                            for ( int j = 0; j < super.vertexesCount; j++ )
                            {
                                if ( rndm < 0.5 )
                                {
                                    dy = rnd.nextInt(coordMut * 2) - rnd.nextInt(coordMut * 2);
                                }
                                retCoords[currPolygonStartIndex + j * 2 + 1] += dy;
                            }
                            break;
                        case 9:
                            int dx = rnd.nextInt(coordMut * 2) - rnd.nextInt(coordMut * 2);
                            rndm = Math.random();
                            for ( int j = 0; j < super.vertexesCount; j++ )
                            {
                                if ( rndm < 0.5 )
                                {
                                    dx = rnd.nextInt(coordMut * 2) - rnd.nextInt(coordMut * 2);
                                }
                                retCoords[currPolygonStartIndex + j * 2] += dx;
                            }
                            break;
                        case 10:
                            for ( int j = 0; j < super.vertexesCount; j++ )
                            {
                                retCoords[currPolygonStartIndex + j * 2] += rnd.nextInt(coordMut) - rnd.nextInt(coordMut);
                                retCoords[currPolygonStartIndex + j * 2 + 1] += rnd.nextInt(coordMut) - rnd.nextInt(coordMut);
                            }
                            break;
                    }
                }
            }
        }

//----------------------------------------------------------------------------------------------

        private void mutations1(double pMutation, double[] retCoords,
        double[] retRGBA) {

            for ( int i = 0; i < super.polygonsCount; i++ )
            {
                if ( random() < pMutation )
                {

                    if ( random() < 0.5 )
                    {
                        double rnd = random();
                        if ( rnd < 0.25 )
                            mutateColorsTotally(retCoords, retRGBA, i);
                        else if ( rnd < 0.5 )
                            mutateCoordsXYParallel(retCoords, retRGBA, i);
                        else if ( rnd < 0.75 )
                            mutateCoordsXParallel(retCoords, retRGBA, i);
                        else if ( rnd < 1.0 )
                            mutateCoordsYParallel(retCoords, retRGBA, i);
                    }
                    if ( random() < 0.5 )
                    {
                        double rnd = random();
                        if ( rnd < 0.5 )
                            mutateColorsTotally(retCoords, retRGBA, i);
                        else if ( rnd < 1.0 )
                            mutateColorsBrightness(retCoords, retRGBA, i);
                    }
                }
            }
        }

        private void mutations0(double pMutation, double[] retCoords,
        double[] retRGBA) {

            for ( int i = 0; i < super.polygonsCount; i++ )
            {
                if ( random() < pMutation )
                {

                    if ( random() < 0.5 )
                    {
                        double rnd = random();
                        if ( rnd < 0.1 )
                            mutateCoordsTotally(retCoords, retRGBA, i);
                        else if ( rnd < 0.35 )
                            mutateCoordsXYParallel(retCoords, retRGBA, i);
                        else if ( rnd < 0.6 )
                            mutateCoordsXParallel(retCoords, retRGBA, i);
                        else if ( rnd < 0.85 )
                            mutateCoordsYParallel(retCoords, retRGBA, i);
                        else
                            mutateCoordsCollapse(retCoords, retRGBA, i);
                    }

                    if ( random() < 0.8 )
                    {
                        double rnd = random();
                        if ( rnd < 0.5 )
                            mutateColorsTotally(retCoords, retRGBA, i);
                        else if ( rnd < 1.0 )
                            mutateColorsBrightness(retCoords, retRGBA, i);
                    }
                }
            }
        }

        private void mutateCoordsCollapse( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currPolygonStartIndex = polygonNumber * super.vertexesCount * 2;
            int currColorStartIndex = polygonNumber * 4;

            double newX = random() * ( width + 2 * xMutRange ) - xMutRange;
            double newY = random() * ( height + 2 * yMutRange ) - yMutRange;

            for ( int j = 0; j < super.vertexesCount; j++ )
            {
                retCoords[currPolygonStartIndex + j * 2] = newX;
                retCoords[currPolygonStartIndex + j * 2 + 1] = newY;
            }

            retRGBA[currColorStartIndex] = 0;
            retRGBA[currColorStartIndex + 1] = 0;
            retRGBA[currColorStartIndex + 2] = 0;
            retRGBA[currColorStartIndex + 3] = 0;
        }

        private void mutateCoordsTotally( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currPolygonStartIndex = polygonNumber * super.vertexesCount * 2;

            for ( int j = 0; j < super.vertexesCount; j++ )
            {
                retCoords[currPolygonStartIndex + j * 2] += ( random() - random() ) * xMutRange;
                retCoords[currPolygonStartIndex + j * 2 + 1] += ( random() - random() ) * yMutRange;
            }
        }

        private void mutateCoordsXParallel( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currPolygonStartIndex = polygonNumber * super.vertexesCount * 2;

            for ( int j = 0; j < super.vertexesCount; j++ )
            {
                retCoords[currPolygonStartIndex + j * 2 + 1] += ( random() - random() ) * yMutRange;
            }
        }

        private void mutateCoordsYParallel( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currPolygonStartIndex = polygonNumber * super.vertexesCount * 2;

            for ( int j = 0; j < super.vertexesCount; j++ )
            {
                retCoords[currPolygonStartIndex + j * 2] += ( random() - random() ) * xMutRange;
            }
        }

        private void mutateCoordsXYParallel( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currPolygonStartIndex = polygonNumber * super.vertexesCount * 2;

            double dx = ( random() - random() ) * xMutRange;
            double dy = ( random() - random() ) * yMutRange;
            for ( int j = 0; j < super.vertexesCount; j++ )
            {
                retCoords[currPolygonStartIndex + j * 2] += dx;
                retCoords[currPolygonStartIndex + j * 2 + 1] += dy;
            }
        }

        private void mutateColorsTotally( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currColorStartIndex = polygonNumber * 4;

            retRGBA[currColorStartIndex] += ( random() - random() ) * rMutRange;
            retRGBA[currColorStartIndex + 1] += ( random() - random() ) * gMutRange;
            retRGBA[currColorStartIndex + 2] += ( random() - random() ) * bMutRange;
            retRGBA[currColorStartIndex + 3] += ( random() - random() ) * aMutRange;
        }

        private void mutateColorsBrightness( double[] retCoords, double[] retRGBA, int polygonNumber ) {

            int currColorStartIndex = polygonNumber * 4;

            double delta = ( random() - random() );
            retRGBA[currColorStartIndex] += delta * rMutRange;
            retRGBA[currColorStartIndex + 1] += delta * gMutRange;
            retRGBA[currColorStartIndex + 2] += delta * bMutRange;
            retRGBA[currColorStartIndex + 3] += delta * aMutRange;
        }

        public void setXMutRange(double mutRange) {
            xMutRange = mutRange;
        }

        public void setYMutRange(double mutRange) {
            yMutRange = mutRange;
        }

        public void setRMutRange(double mutRange) {
            rMutRange = mutRange;
        }

        public void setGMutRange(double mutRange) {
            gMutRange = mutRange;
        }

        public void setBMutRange(double mutRange) {
            bMutRange = mutRange;
        }

        public void setAMutRange(double mutRange) {
            aMutRange = mutRange;
        }

    }
