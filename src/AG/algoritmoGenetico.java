package AG;

import java.util.*;

import static java.lang.Math.random;

public class algoritmoGenetico<T extends GeneticOptimizable<T>> extends Observable {

    private List<ActitudVectorCalculada> población;
    private double pMutacion;//mutacion porcentaje
    private double pCruza;//cruza
    private int contadorPadres;//padres
    private int elitarParentsCount = 1;

    private class ActitudVectorCalculada implements Comparable<ActitudVectorCalculada> {
        T vector;//desde la Interfaz
        double fitness;//funcion Aptitud

        public ActitudVectorCalculada(T vector, double fitness ) {//Constructor
            this.vector = vector;
            this.fitness = fitness;
        }

        public int compareTo(ActitudVectorCalculada o) {//metodo para comparar
            if ( this.fitness > o.fitness)//comparamos funciones de aptitud y regresamos cual es mejor
                return 1;
            else if ( this.fitness < o.fitness)
                return -1;
            else
                return 0;
        };
    }

    public algoritmoGenetico(List<T> vectors ) {
        población = new LinkedList<>();

        for ( T v : vectors )
        {
            ActitudVectorCalculada tmp = new ActitudVectorCalculada( v, v.fitness() );
            población.add(tmp);
        }

        Collections.sort(población);
    }

    public void Iterar(int contadoriteraciones ) {

        int tamanipoblacion = población.size();

        List<ActitudVectorCalculada> padres = new ArrayList<>(contadorPadres);

        for ( int i = 0; i < contadoriteraciones; i++ )
        {
            Collections.sort( población );

            padres.clear();

            int padresactualescontador = 0;
            while ( ( población.size() > 0 ) && ( padresactualescontador < contadorPadres) )
            {
                padres.add( población.get(0) );//agregamos poblacion a padres
                población.remove(0);//borramos el indice 0
                ++padresactualescontador;//padres actuales
            }

            población.clear();

            for ( int j = 0; j < tamanipoblacion - elitarParentsCount; j++ )//aqui empieza mutacion y cruza
            {//mandamos a llamar las funciones y seleccioanmos padres al asar
                int randIndex1 = (int) ( random() * ( contadorPadres - 1 ) );
                int randIndex2 = (int) ( random() * ( contadorPadres - 1 ) );

                T parent1 = padres.get( randIndex1 ).vector;
                T parent2 = padres.get( randIndex2 ).vector;

                T hijo = parent1.crossover( parent2, pCruza);//hacemos cruza con padre 1 y padre 2
                hijo = hijo.mutate(pMutacion);//aplicacmos mutacion al hijo

                ActitudVectorCalculada tmp = new ActitudVectorCalculada( hijo, hijo.fitness() );
                población.add( tmp );
            }

            for ( int j = 0; j < elitarParentsCount; j++ )
            {
                población.add( padres.get(j) );
            }

            Collections.sort( población );
        }

        setChanged();
    }

    public T getBestFitVector() {
        T ret = población.get(0).vector;
        return ret;
    }

    public void setPCrossover(double crossover) {
        pCruza = crossover;
    }

    public double getPCrossover() {
        return pCruza;
    }

    public void setPMutation(double mutation) {
        pMutacion = mutation;
    }

    public double getPMutation() {
        return pMutacion;
    }

    public void setElitarParentsCount(int elitarParentsCount) {
        this.elitarParentsCount = elitarParentsCount;
    }

    public void setContadorPadres(int contadorPadres) {
        this.contadorPadres = contadorPadres;
    }
}