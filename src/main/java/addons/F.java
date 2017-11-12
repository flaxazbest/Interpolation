package addons;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class F {

    private F prime = null;

    public abstract double getY(double x);

    public double getPrime(double x) {
        if (prime == null)
            return (getY(x + Parameters.DELTA) - getY(x)) / Parameters.DELTA;
        return prime.getY(x);
    }

    public Interval getMinMaxY(Interval dx) {
        Interval i = new Interval();
        double x = dx.a;
        double y = getY(x);
        i.b = i.a = y;
        while (x <= dx.b) {
            x += Parameters.DELTA;
            y = getY(x);
            if (y < i.a)
                i.a = y;
            if (y > i.b)
                i.b = y;
        }
        return i;
    }

    public void setPrime(F prime) {
        this.prime = prime;
    }

    public void draw(GraphicsContext gc, Window window, double a, double b, Color color, int lineWidth) {
        int w = window.getW();
        double h = (b-a) / w;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0, window.getW(), window.getH());

        gc.setLineWidth(lineWidth);
        gc.setStroke(color);

        Point2D point = new Point2D(0,0);
        double x = window.screenToReal(point).getX();
        double y = getY(x);
        Point2D sPoint = window.realToScreen(new Point2D(x, y));
        gc.moveTo(sPoint.getX(), sPoint.getY());

        for (int i=1; i<w; i++) {
            point = new Point2D(i,0);
            x = window.screenToReal(point).getX();
            y = getY(x);
            sPoint = window.realToScreen(new Point2D(x, y));
            gc.lineTo(i, sPoint.getY());
        }
        gc.stroke();

        gc.setLineWidth(1);
        gc.setStroke(Color.DARKGREEN);
        Point2D lPoint = window.realToScreen(new Point2D(a, 0));
        Point2D rPoint = window.realToScreen(new Point2D(b, 0));
        gc.strokeLine(lPoint.getX(), lPoint.getY(), rPoint.getX(), rPoint.getY());
        Point2D tPoint = window.realToScreen(new Point2D(0, window.getMaxY()));
        Point2D bPoint = window.realToScreen(new Point2D(0, window.getMinY()));
        gc.strokeLine(tPoint.getX(), tPoint.getY(), bPoint.getX(), bPoint.getY());
    }

    public void draw(GraphicsContext gc, Window window, double a, double b, Color color) {
        draw(gc, window, a, b, color, 2);
    }

    public void draw(GraphicsContext gc, Window window, double a, double b) {
        this.draw(gc, window, a, b, Color.DARKCYAN);
    }

}
