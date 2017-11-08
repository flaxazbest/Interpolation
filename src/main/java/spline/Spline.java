package spline;

import addons.F;
import addons.Window;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matrix.BadSLAR;
import matrix.Matrix;

import static addons.Parameters.A;
import static addons.Parameters.B;
import static addons.Parameters.parts;

public class Spline {

    private Cubic[] splines;
    private F function;
    private Point2D[] table;
    private Matrix matrix;
    private int n = parts;

    public Spline(F function) {

        this.function = function;
        splines = new Cubic[parts];
        for (int i=0; i<parts; i++) {
            splines[i] = new Cubic();
        }
        table = new Point2D[parts+1];
        double h = (B - A) / parts;
        for (int i=0; i<=parts; i++) {
            table[i] = new Point2D(A+i*h, function.getY(A+i*h));
        }
        matrix = new Matrix(4*n);
        double x;

        x = table[0].getX();

        //second prime = 0 (global)
        matrix.setElement(2.0, 0, 2);
        matrix.setElement(6*x, 0, 3);
        //matrix.setElement(-0.5/Math.sqrt(x*x*x),0, 4*n);

        //first prime
        matrix.setElement(1, 1, 1);
        matrix.setElement(2*x, 1, 2);
        matrix.setElement(3*x*x, 1, 3);
        matrix.setElement(function.getPrime(x), 1, 4*n);

        for (int i=0; i<parts; i++) {

            if (i >0 /*&& i < parts-1*/) {
                //proxima2
                matrix.setElement(6 * x, i * 4, (i - 1) * 4 + 3);
                matrix.setElement(2, i * 4, (i - 1) * 4 + 2);
                matrix.setElement(-6 * x, i * 4, i * 4 + 3);
                matrix.setElement(-2, i * 4, i * 4 + 2);

                //proxima
                matrix.setElement(3 * x * x, i * 4 + 1, (i - 1) * 4 + 3);
                matrix.setElement(2 * x, i * 4 + 1, (i - 1) * 4 + 2);
                matrix.setElement(1, i * 4 + 1, (i - 1) * 4 + 1);
                matrix.setElement(-3 * x * x, i * 4 + 1, i * 4 + 3);
                matrix.setElement(-2 * x, i * 4 + 1, i * 4 + 2);
                matrix.setElement(-1, i * 4 + 1, i * 4 + 1);
            }

            matrix.setElement(x*x*x, i*4+2 ,i*4+3);
            matrix.setElement(x*x, i*4+2 ,i*4+2);
            matrix.setElement(x, i*4+2 ,i*4+1);
            matrix.setElement(1, i*4+2 ,i*4);
            matrix.setElement(table[i].getY(), i*4+2, 4*n);

            x = table[i+1].getX();
            matrix.setElement(x*x*x, i*4+3 ,i*4+3);
            matrix.setElement(x*x, i*4+3 ,i*4+2);
            matrix.setElement(x, i*4+3 ,i*4+1);
            matrix.setElement(1, i*4+3 ,i*4);
            matrix.setElement(table[i+1].getY(), i*4+3, 4*n);
        }
//*
        //second prime = 0 (global)
       // matrix.setElement(2.0, (parts-1)*4, (parts-1)*4+2);
        //matrix.setElement(6*x, (parts-1)*4, (parts-1)*4+3);
        matrix.setElement(-0.5/Math.sqrt(x*x*x),(parts-1)*4, 4*n);

        //first prime
        //matrix.setElement(1, (parts-1)*4+1, (parts-1)*4+1);
        //matrix.setElement(2*x, (parts-1)*4+1, (parts-1)*4+2);
        //matrix.setElement(3*x*x, (parts-1)*4+1, (parts-1)*4+3);
        matrix.setElement(function.getPrime(x), (parts-1)*4+1, 4*n);
//*/

        matrix.print();

        System.out.println("\n\n");

        matrix.solveGauss();
        matrix.printInMatrixMode();

        double[] r = matrix.getX();
        try {
            for (int i=0; i<parts; i++) {
                for (int j=0; j<4; j++) {
                    splines[i].setKoeficient(r[i * 4 + j], j);
                }
            }
        } catch (WrongCoeficientException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        F f = new F() {
            @Override
            public double getY(double x) {
                return Math.sqrt(x) + Math.cos(x);
            }
        };
        f.setPrime(new F() {
            @Override
            public double getY(double x) {
                return 0.5/Math.sqrt(x) - Math.sin(x);
            }
        });

        Spline s = new Spline(f);
    }

    public Cubic[] getSplines() {
        return splines;
    }

    public void draw(GraphicsContext gc, Window w) {
        for (int i=0; i<parts; i++) {
            splines[i].draw(gc, w, table[i].getX(), table[i+1].getX(), Color.BROWN, 1);
        }
    }
}
