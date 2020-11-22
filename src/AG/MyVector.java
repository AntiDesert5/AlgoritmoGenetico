package AG;

public class MyVector {

    protected final int dim;
    protected final double[] vector;

    public MyVector( int dim ) {
        this.dim = dim;
        this.vector = new double[dim];
    }

    public MyVector( MyVector prototype ) {
        dim = prototype.dim;
        vector = new double[dim];
        System.arraycopy( prototype.vector, 0, vector, 0, dim );
    }

    public void setValue( int index, double value ) {
        vector[index] = value;
    }

    public double getValue( int index ) {
        return vector[index];
    }

    public double[] getVector() {
        return vector;
    }

	/*public int getDimension() {
		return dim;
	}*/

    public void scale(double coeff) {
        for ( int i = 0; i < dim; i++ )
        {
            vector[i] *= coeff;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(dim);

        sb.append("[");
        for ( int i = 0; i < dim - 1; i++ )
        {
            sb.append( vector[i] ).append(", ");
        }
        sb.append( vector[ dim - 1 ] );
        sb.append("]");

        return sb.toString();
    }

    public static void main(String[] args) {
        MyVector v1 = new MyVector(5);
        for ( int i = 0; i < 5; i++ )
            v1.setValue( i, i );

        MyVector v2 = new MyVector( v1 );
        for ( int i = 0; i < 5; i++ )
            v2.setValue( i, v2.getValue(i) + 1 );

        System.out.println(v1);
        System.out.println(v2);
    }

}
