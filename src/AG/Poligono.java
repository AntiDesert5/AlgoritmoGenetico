package AG;

import static java.lang.Math.abs;

public class Poligono implements Comparable<Poligono> {

    int[] xCoords;
    int[] yCoords;
    int complejidadPoligono;
    int r;
    int g;
    int b;
    int a;
    double area;

    //Constructor
    public Poligono(int[] xCoords, int[] yCoords, int r, int g, int b, int a, int complejidadPoligono) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.complejidadPoligono = complejidadPoligono;
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.area = areapoligono(xCoords, yCoords, complejidadPoligono);
    }

    public static double areapoligono(int[] xCoorde, int[] yCoorde, int complejidadpoligono ) {
        double sum = 0.0;
        for ( int i = 0; i < complejidadpoligono - 1; i++ )
        {
            sum += xCoorde[i] * yCoorde[i + 1] - yCoorde[i] * xCoorde[i + 1];
        }
        return abs( sum * 0.5 );
    }

    @Override
    public int compareTo(Poligono o) { //clase que permite comparar este poligono con uno anterior , se reseguesan distintos casos .
        if ( this.area > o.area )
            return -1;
        else if ( this.area < o.area )
            return 1;
        else
            return 0;
    }

}
