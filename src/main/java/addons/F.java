package addons;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import newton.Parameters;

public abstract class F {

    public abstract double getY(double x);

    public double getPrime(double x) {
        return (getY(x + Parameters.DELTA) - getY(x)) / Parameters.DELTA;
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

    public void draw(GraphicsContext gc, Window window, double a, double b) {
        int w = window.getW();
        double h = (b-a) / w;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0, window.getW(), window.getH());

        gc.setLineWidth(3);
        gc.setStroke(Color.BLUEVIOLET);

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

}
