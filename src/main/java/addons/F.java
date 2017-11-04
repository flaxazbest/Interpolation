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

    public void draw(GraphicsContext gc, Window window, double a, double b) {
        int w = window.getW();
        double h = (b-a) / w;


        gc.setLineWidth(4);
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
    }

}
