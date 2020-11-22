package AG;

public interface GeneticOptimizable<T extends GeneticOptimizable<T>> {

    T mutate( double pMutation );
    T crossover( T associate, double pCrossover );
    double fitness();

}