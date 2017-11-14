package spline;

import addons.F;
import addons.Window;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matrix.Matrix;

import static addons.Parameters.A;
import static addons.Parameters.B;
import static addons.Parameters.parts;

public class Spline3P {

    private Cubic[] splines;
    private Point2D[] table;

    public Spline3P(F function) {
        table = new Point2D[parts+1];
        double h = (B - A) / parts;
        for (int i=0; i<=parts; i++) {
            table[i] = new Point2D(A+i*h, function.getY(A+i*h));
        }
        try {
            splines = TriDialogMatrix.getCoeficients(table);
        } catch (MatrixBuildException e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc, Window w) {
        for (int i=0; i<parts; i++) {
            splines[i].draw(gc, w, table[i].getX(), table[i+1].getX(), Color.BROWN, 1);
        }
    }

}
