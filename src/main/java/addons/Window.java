package addons;

import javafx.geometry.Point2D;

public class Window {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private int w;
    private int h;

    public Window(double minX, double maxX, double minY, double maxY, int w, int h) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.w = w;
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public Point2D screenToReal(Point2D point) {
        double x = point.getX() * (maxX - minX) / w + minX;
        double y = (h - point.getY()) * ( (maxY - minY) / h ) + minY;
        return new Point2D(x,y);
    }

    public Point2D screenToReal(double xx, double yy) {
        double x = xx * (maxX - minX) / w + minX;
        double y = (h - yy) * ( (maxY - minY) / h ) + minY;
        return new Point2D(x,y);
    }

    public Point2D realToScreen(Point2D point) {
        double x = (point.getX() - minX) * w / (maxX - minX);
        double y = h - (point.getY() - minY) * h / (maxY - minY);
        return new Point2D(x,y);
    }
}
