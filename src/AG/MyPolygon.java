package AG;

import static java.lang.Math.abs;

public class MyPolygon implements Comparable<MyPolygon> {

    int[] xCoords;
    int[] yCoords;
    int polygonComplexity;
    int r;
    int g;
    int b;
    int a;
    double area;

    public MyPolygon( int[] xCoords, int[] yCoords, int r, int g, int b, int a, int polygonComplexity ) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.polygonComplexity = polygonComplexity;
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.area = polygonArea(xCoords, yCoords, polygonComplexity);
    }

    public static double polygonArea( int[] xCoords, int[] yCoords, int polygonComplexity ) {
        double sum = 0.0;
        for ( int i = 0; i < polygonComplexity - 1; i++ )
        {
            sum += xCoords[i] * yCoords[i + 1] - yCoords[i] * xCoords[i + 1];
        }
        return abs( sum * 0.5 );
    }

    @Override
    public int compareTo(MyPolygon o) {
        if ( this.area > o.area )
            return -1;
        else if ( this.area < o.area )
            return 1;
        else
            return 0;
    }

}
