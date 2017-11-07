package spline;

import addons.F;
import javafx.geometry.Point2D;
import matrix.Matrix;

import static addons.Parameters.A;
import static addons.Parameters.B;
import static addons.Parameters.parts;

import java.util.ArrayList;

public class Spline {

    private Cubic[] splines;
    private F function;
    private Point2D[] table;
    private Matrix matrix;
    private int n = parts;

    public Spline(F function) {

        this.function = function;
        splines = new Cubic[n];
        table = new Point2D[parts];
        double h = (B - A) / parts;
        for (int i=0; i<parts; i++) {
            table[i] = new Point2D(A+i*h, function.getY(A+i*h));
        }
        matrix = new Matrix(4*n);


    }
}
