package spline;

import addons.F;
import addons.Window;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static addons.Parameters.A;
import static addons.Parameters.B;
import static addons.Parameters.parts;

public class SplineP {

    private Cubic[] splines;
    private F function;
    private Point2D[] table;
    private int n;

    private double[][] matrix;


    public SplineP(F function, int n) {
        this.function = function;
        this.n = n;

        matrix = new double[11][n];
        table = new Point2D[n];

        double hh = (B - A) / (n - 1);
        for (int i = 0; i < n; i++) {
            matrix[4][i] = A + i * hh;
            matrix[3][i] = function.getY(A + i * hh);
            table[i] = new Point2D(matrix[4][i], matrix[3][i]);
        }

        for (int i = 2; i < n; i++) {
            matrix[7][1] = 0;
            matrix[8][1] = 0;
            matrix[5][i - 1] = matrix[4][i - 1] - matrix[4][i - 2];
            matrix[5][i] = matrix[4][i] - matrix[4][i - 1];
            matrix[10][i] = 2 * (matrix[5][i] + matrix[5][i - 1]);
            matrix[9][i] = 3 * ((matrix[3][i] - matrix[3][i - 1]) / matrix[5][i] - (matrix[3][i - 1] - matrix[3][i - 2]) / matrix[5][i - 1]);
            matrix[7][i] = (matrix[9][i] - matrix[5][i - 1] * matrix[7][i - 1]) / (matrix[10][i] - matrix[5][i - 1] * matrix[8][i - 1]);
            matrix[8][i] = matrix[5][i] / (matrix[10][i] - matrix[5][i - 1] * matrix[8][i - 1]);
        }

        matrix[2][n - 1] = matrix[7][n - 1];
        for (int i = n - 2; i >= 2; i--)
            matrix[2][i] = matrix[7][i] - matrix[8][i] * matrix[2][i + 1];


        for (int i = 1; i < n-1; i++) {
            matrix[5][i] = matrix[4][i] - matrix[4][i - 1];
            matrix[0][i] = matrix[3][i - 1];
            matrix[1][i] = (matrix[3][i] - matrix[3][i - 1]) / matrix[5][i] - matrix[5][i] * (2 * matrix[2][i] + matrix[2][i + 1]) / 3.0;
            matrix[6][i] = (matrix[2][i + 1] - matrix[2][i]) / (3.0 * matrix[5][i]);
        }

        splines = new Cubic[n-1];
        for (int i=0; i<n-1; i++) {
            double a = matrix[0][i] - matrix[1][i] * matrix[4][i+1] +
                       matrix[2][i] * Math.pow(matrix[4][i+1], 2) -
                       matrix[6][i] *  Math.pow(matrix[4][i+1], 3);
            double b = matrix[1][i] - 2 * matrix[2][i] * matrix[4][i+1] + matrix[6][i] * Math.pow(matrix[4][i+1], 2);
            double c = matrix[2][i] - matrix[6][i] * matrix[4][i+1];
            double d = matrix[6][i];
            splines[i] = new Cubic(a, b, c, d);
        }

/*        int i = 1;
        double x1 = matrix[4][0];
        int x2;
        double y2=0;
        do {
            do {
                y2=matrix[0][i]+b[i]*(x1-x[i-1])+c[i]*pow((x1-x[i-1]), 2)+d[i]*pow((x1-x[i-1]), 3);
                Series1->AddXY(x1, y2);
                x1=x1+0.001;
                x2=static_cast<int> (x1);
            } while (x2!=x[i]);

            i++;
            x1=x[i-1];
        } while (i!=n-1);

    }*/

        // a = 0
        // b = 1
        // c = 2
        // y = 3
        // x = 4
        // h = 5
        // d = 6
        // k = 7
        // l = 8
        // r = 9
        // s = 10

    }

    public void draw(GraphicsContext gc, Window w) {
        for (int i=0; i<parts; i++) {
            if (splines[i]!= null)
                splines[i].draw(gc, w, table[i].getX(), table[i+1].getX(), Color.BROWN, 1);
        }
    }
}