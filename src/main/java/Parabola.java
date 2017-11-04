import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Parabola {

    private double[] a;

    public Parabola() {
        a = new double[3];
    }

    public Parabola(double[] a) {
        this.a = a;
    }

    public Parabola(double a0, double a1, double a2) {
        a = new double[3];
        a[0] = a0;
        a[1] = a1;
        a[2] = a2;
    }

    public double calcY(double x) {
        return x*x*a[2] + x*a[1] + a[0];
    }

    public void setA(double[] a) {
        this.a = a;
    }

    public void draw(GraphicsContext gc, Window w, double a, double b, Color c) {

        gc.setLineWidth(2);
        gc.setStroke(c);

        Point2D oldP = w.realToScreen(new Point2D(a, calcY(a)));
        Point2D p;

        double h = 0.003;
        while (a < b) {
            a += h;
            p = w.realToScreen(new Point2D(a, calcY(a)));
            gc.strokeLine(oldP.getX(),oldP.getY(), p.getX(), p.getY());
            oldP = p;
        }
    }

    @Override
    public String toString() {
        return "Parabola{" +
                "a=" + Arrays.toString(a) +
                '}';
    }
}
