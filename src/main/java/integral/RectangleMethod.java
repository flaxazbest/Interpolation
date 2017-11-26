package integral;

import addons.F;
import addons.Interval;
import addons.Window;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static addons.Parameters.EPS;

public class RectangleMethod {

    private final int multiplier = 2;
    private F antiderivative;
    private F f;
    private Interval interval;

    public RectangleMethod(F f, F antiderivative, Interval interval) {
        this.antiderivative = antiderivative;
        this.f = f;
        this.interval = interval;
    }

    private double integrate(int parts, Method method) {
        double h = interval.length() / parts;
        double a = interval.a;
        double s = 0.0;
        while (a < interval.b) {
            switch (method) {
                case LEFT:
                    s += f.getY(a) * h;
                    break;
                case RIGHT:
                    s += f.getY(a+h) * h;
                    break;
                case MIDDLE:
                    s += f.getY(a+h/2.0) * h;
                    break;
            }
            a += h;
        }
        return s;
    }

    public double accuranceValue() {
        return antiderivative.getY(interval.b) - antiderivative.getY(interval.a);
    }

    public IntegratedResult integrate(Method method) {
        IntegratedResult res = new IntegratedResult();
        res.accuranceValue = accuranceValue();
        int parts = 2;
        double r = integrate(parts, method);
        double old = 0.0;
        int iteration = 0;
        do {
            res.d.add(Math.abs(res.accuranceValue - r));
            old = r;
            iteration++;
            parts *= multiplier;
            r = integrate(parts, method);
        } while (Math.abs(r - old) > EPS);
        res.iteration = iteration;
        res.delta = Math.abs(r - res.accuranceValue);
        res.value = r;
//        System.err.println("Iteration = " + iteration);
        return res;
    }

    public static void main(String[] args) {

        F f = new F() {
            @Override
            public double getY(double x) {
                return Math.sqrt(2*x - x*x);
            }
        };

        F antiD = new F() {
            @Override
            public double getY(double x) {
                return (x-1) * Math.sqrt(2*x - x*x) + 0.5*Math.asin(x-1);
            }
        };

        RectangleMethod rm = new RectangleMethod(f, antiD, new Interval(0.0, 2.0));

        double lv = rm.integrate(Method.LEFT).value;

        System.out.println("I = " + lv);
        System.out.println("Accurace value = " + rm.accuranceValue());
        System.out.println("D = " + Math.abs(lv - rm.accuranceValue()));

    }

    public void draw(GraphicsContext gc, Window w, Method method, int parts) {
        gc.setFill(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.clearRect(0,0, w.getW(), w.getH());
        gc.fillRect(0,0,w.getW(), w.getH());
        f.draw(gc, w, interval.a-0.1, interval.b+0.1);
        gc.setStroke(Color.AQUAMARINE);
        double a = interval.a;
        double h = interval.length() / parts;
        gc.beginPath();
        while (a < interval.b) {
            Point2D lP = new Point2D(a, 0);
            Point2D rP = null;
            switch (method) {
                case LEFT:
                    rP = new Point2D(a+h, f.getY(a));
                    break;
                case RIGHT:
                    rP = new Point2D(a+h, f.getY(a+h));
                    break;
                case MIDDLE:
                    rP = new Point2D(a+h, f.getY(a+h/2.0));
                    break;
            }
            Point2D elP = w.realToScreen(lP);
            Point2D erP = w.realToScreen(rP);
            gc.rect(elP.getX(), elP.getY(), (erP.getX()-elP.getX()), (erP.getY()-elP.getY()));
            a += h;
        }
        gc.closePath();
        gc.stroke();
    }

}
