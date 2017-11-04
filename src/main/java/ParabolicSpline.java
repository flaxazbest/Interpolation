import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ParabolicSpline {

    private double a;
    private double b;
    private int parts;
    private double[] dots;
    private Parabola[] spline;
    private Color[] colors;

    public ParabolicSpline(double a, double b, int parts) {
        this.a = a;
        this.b = b;
        this.parts = parts;
        dots = new double[parts+1];
        double h = (b-a)/parts;
        int k = 0;
        for (double i=a; i<=b; i+=h) {
            dots[k++] = i;
        }
        spline = new Parabola[parts];
        colors = new Color[3];
        colors[0] = Color.GREEN;
        colors[1] = Color.RED;
        colors[2] = Color.BLUE;
    }

    public void draw(GraphicsContext gc, Window w) {
        for (int i=0; i<parts; i++) {
            spline[i].draw(gc, w, dots[i], dots[i+1], colors[i%3]);
        }
    }

    public Parabola getParabola(int i) {
        return spline[i];
    }

    public ParabolicSpline() {
        this(Parameters.A, Parameters.B, Parameters.parts);
    }

    public void calcSplines(Function f) {
        for (int i=0; i<parts; i++) {
            spline[i] = Lagrange.interpolate(f, dots[i], (dots[i] + dots[i+1]) / 2.0, dots[i+1]);
        }
    }

    public double getPhi(double z) {
        int i=0;
        while (dots[i] <= z)
            i++;
        return spline[i-1].calcY(z);
    }

}
