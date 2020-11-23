package AG;

import java.util.Random;
import static java.lang.Math.random;

public class vectorimagenGenetico extends ImageVector
    	implements GeneticOptimizable<vectorimagenGenetico> {

        private double xMutRange = 0;
        private double yMutRange = 0;

        private double rMutRange = 0;
        private double gMutRange = 0;
        private double bMutRange = 0;
        private double aMutRange = 0;

        private calculoimagenfitness fitCalculator;

        private double width = 0;
        private double height = 0;

        private Random rnd = new Random();

        //Constructor
	public vectorimagenGenetico(int polygonsCount, int vertexesCount, calculoimagenfitness fitCalculator ) {
            super( polygonsCount, vertexesCount );
            this.fitCalculator = fitCalculator;
        }

	public vectorimagenGenetico(vectorimagenGenetico prototype ) {
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
//RGB Colores que cambian
            this.rMutRange = prototype.rMutRange;
            this.gMutRange = prototype.gMutRange;
            this.bMutRange = prototype.bMutRange;
            this.aMutRange = prototype.aMutRange;
//Calculo de la funcion fitness
            this.fitCalculator = prototype.fitCalculator;

            this.width = prototype.width;
            this.height = prototype.height;
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
        public vectorimagenGenetico crossover(vectorimagenGenetico associate, double pCrossover) {
            vectorimagenGenetico ret;
            double[] associateCoords;
            double[] associateRGBA;

            if ( random() > 0.5 )
            {
                ret = new vectorimagenGenetico(this);
                associateCoords = associate.coordinatesVector.getVector();
                associateRGBA = associate.rgbaVector.getVector();
            }
            else
            {
                ret = new vectorimagenGenetico(associate);
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
        public vectorimagenGenetico mutate(double pMutation) {
            vectorimagenGenetico ret = new vectorimagenGenetico(this);

            double[] retCoords = ret.coordinatesVector.getVector();
            double[] retRGBA = ret.rgbaVector.getVector();

            mutations3(pMutation, retCoords, retRGBA);

            return ret;
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
