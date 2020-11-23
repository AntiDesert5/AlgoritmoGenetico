package AG;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class ImageVector {

    //Даний вектор містить координти (x, y) вершин всіх полігонів
    protected MyVector coordinatesVector;

    //Даний вектор містить кольори (r, g, b, a) всіх полігонів
    protected MyVector rgbaVector;

    //Кількість полігонів
    protected int polygonsCount;

    //Кількість вершин у кожному полігоні
    protected int vertexesCount;

    public ImageVector( int polygonsCount, int vertexesCount ) {
        this.polygonsCount = polygonsCount;
        this.vertexesCount = vertexesCount;
        coordinatesVector = new MyVector( polygonsCount * vertexesCount * 2 );
        rgbaVector = new MyVector( polygonsCount * 4 );
    }

    public void fillWithRandomCoords( int width, int height ) {
        double[] coords = coordinatesVector.getVector();
        for ( int i = 0; i < polygonsCount; i++ )
        {
            double currX = random() * width;
            double currY = random() * height;
            int currPolygonStartIndex = i * vertexesCount * 2;
            for ( int j = 0; j < vertexesCount; j++ )
            {
                coords[currPolygonStartIndex + j * 2] = currX;
                coords[currPolygonStartIndex + j * 2 + 1] = currY;
            }
        }
    }

    public void fillWithRandomRGBA( int rCoeff, int gCoeff, int bCoeff, int aCoeff) {
        double[] rgba = rgbaVector.getVector();
        for ( int i = 0; i < rgba.length; i += 4 )
        {
            rgba[i] = random() * rCoeff; //R
            rgba[i + 1] = random() * gCoeff; //G
            rgba[i + 2] = random() * bCoeff; //B
            rgba[i + 3] = random() * aCoeff; //A
        }
    }

    public void fillWithRandomRGBA() {
        fillWithRandomRGBA( 255, 255, 255, 255 );
    }

    public void paint(Graphics2D graphics) {
        double[] coords = coordinatesVector.getVector();
        double[] rgba = rgbaVector.getVector();

        List<Poligono> polygons = new LinkedList<Poligono>();

        for ( int i = 0; i < polygonsCount; i++ )
        {
            int[] xPoints = new int[vertexesCount];
            int[] yPoints = new int[vertexesCount];

            int currPolygonStartIndex = i * vertexesCount * 2;

            for ( int j = 0; j < vertexesCount; j++ )
            {
                xPoints[j] = (int) coords[currPolygonStartIndex + j * 2];
                yPoints[j] = (int) coords[currPolygonStartIndex + j * 2 + 1];
            }

            /* Циклічна повторюваність кольорів.
             * Для кожного числа Х застосовуються такі правила:
             * 1) X <- abs(X)
             * 2) X <- (X % 511)
             * 2) if ((0 < X) && (X < 256)) return X
             * 3) if ((255 < X) && (X < 512)) return (511 - X)
             * Таким гарантується, що значення колірної компоненти буде в діапазоні [0..255],
             * а також гарантується гладкість функції колірної компоненти.
             */
            int r = abs( (int) rgba[i * 4] ) % 511;
            int g = abs( (int) rgba[i * 4 + 1] ) % 511;
            int b = abs( (int) rgba[i * 4 + 2] ) % 511;
            int a = abs( (int) rgba[i * 4 + 3] ) % 511;

            r = ( r > 255 ) ? 511 - r : r;
            g = ( g > 255 ) ? 511 - g : g;
            b = ( b > 255 ) ? 511 - b : b;
            a = ( a > 255 ) ? 511 - a : a;

            Poligono curr = new Poligono( xPoints, yPoints, r, g, b, a, vertexesCount );

            polygons.add(curr);
        }

        Collections.sort(polygons);
        for ( Poligono p : polygons )
        {
            graphics.setColor( new Color( p.r, p.g, p.b, p.a ) );
            graphics.fillPolygon( p.xCoords, p.yCoords, p.complejidadPoligono);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\n");
        sb.append("polygonsCount : ").append(polygonsCount).append("\n");
        sb.append("vertexesCount : ").append(vertexesCount).append("\n");
        sb.append("coords : ").append( coordinatesVector.toString() ).append("\n");
        sb.append("rgba : ").append( rgbaVector.toString() ).append("\n");
        sb.append("}").append("\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        ImageVector iv = new ImageVector( 2, 3 );
        iv.fillWithRandomCoords(5, 5);
        iv.fillWithRandomRGBA();
        System.out.println(iv);
    }

}
