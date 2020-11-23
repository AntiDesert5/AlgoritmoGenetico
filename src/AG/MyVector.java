package AG;

public class MyVector {

    protected final int dim;
    protected final double[] vector;

    public MyVector( int dim ) {
        this.dim = dim;
        this.vector = new double[dim];
    }

    public double[] getVector() {
        return vector;
    }

   /* public void scale(double coeff) {
        for ( int i = 0; i < dim; i++ )
        {
            vector[i] *= coeff;
        }
    }*/
}
