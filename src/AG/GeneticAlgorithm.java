package AG;

import java.util.*;

import static java.lang.Math.random;

public class GeneticAlgorithm<T extends GeneticOptimizable<T>> extends Observable {

    private List<VectorFitnessCalculated> población;
    private double pMutation;//mutacion porcentaje
    private double pCrossover;//cruza
    private int parentsCount;//padres
    private int elitarParentsCount = 1;

    private class VectorFitnessCalculated implements Comparable<VectorFitnessCalculated> {
        T vector;
        double fitness;

        public VectorFitnessCalculated( T vector, double fitness ) {
            this.vector = vector;
            this.fitness = fitness;
        }

        public int compareTo(AG.GeneticAlgorithm<T>.VectorFitnessCalculated o) {
            if ( this.fitness > o.fitness)
                return 1;
            else if ( this.fitness < o.fitness)
                return -1;
            else
                return 0;
        };
    }

    public GeneticAlgorithm( List<T> vectors ) {
        población = new LinkedList<>();

        for ( T v : vectors )
        {
            VectorFitnessCalculated tmp = new VectorFitnessCalculated( v, v.fitness() );
            población.add(tmp);
        }

        Collections.sort(población);
    }

    public void iterate( int iterationsCount ) {

        int populationSize = población.size();

        List<VectorFitnessCalculated> parents = new ArrayList<>(parentsCount);

        for ( int i = 0; i < iterationsCount; i++ )
        {
            Collections.sort( población );

            parents.clear();

            int currParentIndex = 0;
            while ( ( población.size() > 0 ) && ( currParentIndex < parentsCount ) )
            {
                parents.add( población.get(0) );
                población.remove(0);
                ++currParentIndex;
            }

            población.clear();

            for ( int j = 0; j < populationSize - elitarParentsCount; j++ )
            {
                int randIndex1 = (int) ( random() * ( parentsCount - 1 ) );
                int randIndex2 = (int) ( random() * ( parentsCount - 1 ) );

                T parent1 = parents.get( randIndex1 ).vector;
                T parent2 = parents.get( randIndex2 ).vector;

                T child = parent1.crossover( parent2, pCrossover );
                child = child.mutate( pMutation );

                VectorFitnessCalculated tmp = new VectorFitnessCalculated( child, child.fitness() );
                población.add( tmp );
            }

            for ( int j = 0; j < elitarParentsCount; j++ )
            {
                población.add( parents.get(j) );
            }

            Collections.sort( población );
        }

        setChanged();
        notifyObservers(población.get(0).fitness);
    }

    public T getBestFitVector() {
        T ret = población.get(0).vector;
        return ret;
    }

    public void setPCrossover(double crossover) {
        pCrossover = crossover;
    }

    public double getPCrossover() {
        return pCrossover;
    }

    public void setPMutation(double mutation) {
        pMutation = mutation;
    }

    public double getPMutation() {
        return pMutation;
    }

    public void setElitarParentsCount(int elitarParentsCount) {
        this.elitarParentsCount = elitarParentsCount;
    }

    public void setParentsCount(int parentsCount) {
        this.parentsCount = parentsCount;
    }
}