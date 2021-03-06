package spline;

import addons.F;
import addons.Window;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cubic extends F {
    private double[] a;

    private Cubic() {
        a = new double[4];
    }

    void setKoeficient(double x, int i) throws WrongCoeficientException {
        if (a == null || i < 0 || i>3)
            throw new WrongCoeficientException();
        a[i] = x;
    }

    public double[] getA() {
        return a;
    }

    public void setA(double[] a) {
        this.a = a;
    }

    Cubic(double a0, double a1, double a2, double a3) {
        this();
        a[0] = a0;
        a[1] = a1;
        a[2] = a2;
        a[3] = a3;

        F d3 = new F() {
            @Override
            public double getY(double x) {
                return 6*a3;
            }

            @Override
            public void draw(GraphicsContext gc, Window w, double a, double b, Color c, int lineWidth) {

                gc.setLineWidth(lineWidth);
                gc.setStroke(c);

                Point2D oldP = w.realToScreen(new Point2D(a, getY(a)));
                Point2D p;

                double h = 0.003;
                while (a < b) {
                    a += h;
                    p = w.realToScreen(new Point2D(a, getY(a)));
                    gc.strokeLine(oldP.getX(),oldP.getY(), p.getX(), p.getY());
                    oldP = p;
                }
            }
        };

        F d2 = new F() {
            @Override
            public double getY(double x) {
                return 6*a3*x + 2*a2;
            }

            @Override
            public void draw(GraphicsContext gc, Window w, double a, double b, Color c, int lineWidth) {

                gc.setLineWidth(lineWidth);
                gc.setStroke(c);

                Point2D oldP = w.realToScreen(new Point2D(a, getY(a)));
                Point2D p;

                double h = 0.003;
                while (a < b) {
                    a += h;
                    p = w.realToScreen(new Point2D(a, getY(a)));
                    gc.strokeLine(oldP.getX(),oldP.getY(), p.getX(), p.getY());
                    oldP = p;
                }
            }
        };
        d2.setPrime(d3);

        F d1 = new F() {
            @Override
            public double getY(double x) {
                return 3*a3*x*x + 2*a2*x + a1;
            }

            @Override
            public void draw(GraphicsContext gc, Window w, double a, double b, Color c, int lineWidth) {

                gc.setLineWidth(lineWidth);
                gc.setStroke(c);

                Point2D oldP = w.realToScreen(new Point2D(a, getY(a)));
                Point2D p;

                double h = 0.003;
                while (a < b) {
                    a += h;
                    p = w.realToScreen(new Point2D(a, getY(a)));
                    gc.strokeLine(oldP.getX(),oldP.getY(), p.getX(), p.getY());
                    oldP = p;
                }
            }
        };
        d1.setPrime(d2);

        setPrime(d1);
    }

    @Override
    public double getY(double x) {
        return getY(x, 0.0);
    }

    public double getY(double x, double delta) {
        return a[3]*(x-delta)*(x-delta)*(x-delta) + a[2]*(x-delta)*(x-delta) + a[1]*(x-delta) + a[0];
    }

    @Override
    public void draw(GraphicsContext gc, Window w, double a, double b, Color c, int lineWidth) {
        this.draw(gc, w, a, b, c, lineWidth, 0.0);
    }

    public void draw(GraphicsContext gc, Window w, double a, double b, Color c, int lineWidth, double delta) {

        gc.setLineWidth(lineWidth);
        gc.setStroke(c);

        Point2D oldP = w.realToScreen(new Point2D(a, getY(a, delta)));
        Point2D p;

        double h = 0.003;
        while (a < b) {
            a += h;
            p = w.realToScreen(new Point2D(a, getY(a, delta)));
            gc.strokeLine(oldP.getX(),oldP.getY(), p.getX(), p.getY());
            oldP = p;
        }
    }
}
