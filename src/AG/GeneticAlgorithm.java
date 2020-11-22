package AG;

import java.util.*;

import static java.lang.Math.random;

public class GeneticAlgorithm<T extends GeneticOptimizable<T>> extends Observable {

    private List<VectorFitnessCalculated> population;
    private double pMutation;
    private double pCrossover;
    private int parentsCount;
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
        population = new LinkedList<VectorFitnessCalculated>();

        for ( T v : vectors )
        {
            VectorFitnessCalculated tmp = new VectorFitnessCalculated( v, v.fitness() );
            population.add(tmp);
        }

        Collections.sort(population);
    }

    public void iterate( int iterationsCount ) {

        int populationSize = population.size();

        List<VectorFitnessCalculated> parents = new ArrayList<VectorFitnessCalculated>(parentsCount);

        for ( int i = 0; i < iterationsCount; i++ )
        {
            Collections.sort( population );

            parents.clear();

            int currParentIndex = 0;
            while ( ( population.size() > 0 ) && ( currParentIndex < parentsCount ) )
            {
                parents.add( population.get(0) );
                population.remove(0);
                ++currParentIndex;
            }

            population.clear();

            for ( int j = 0; j < populationSize - elitarParentsCount; j++ )
            {
                int randIndex1 = (int) ( random() * ( parentsCount - 1 ) );
                int randIndex2 = (int) ( random() * ( parentsCount - 1 ) );

                T parent1 = parents.get( randIndex1 ).vector;
                T parent2 = parents.get( randIndex2 ).vector;

                T child = parent1.crossover( parent2, pCrossover );
                child = child.mutate( pMutation );

                VectorFitnessCalculated tmp = new VectorFitnessCalculated( child, child.fitness() );
                population.add( tmp );
            }

            for ( int j = 0; j < elitarParentsCount; j++ )
            {
                population.add( parents.get(j) );
            }

            Collections.sort( population );
        }

        setChanged();
        notifyObservers(population.get(0).fitness);
    }

    public T getBestFitVector() {
        T ret = population.get(0).vector;
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