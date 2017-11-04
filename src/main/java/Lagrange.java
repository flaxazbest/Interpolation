public class Lagrange {

    public static Parabola interpolate(Function f, double x0, double x1, double x2) {

        double y0 = f.getY(x0);
        double y1 = f.getY(x1);
        double y2 = f.getY(x2);

        double a0, a1, a2;

        double d0 = y0 / ((x0 - x1) * (x0 - x2));
        double d1 = y1 / ((x1 - x0) * (x1 - x2));
        double d2 = y2 / ((x2 - x0) * (x2 - x1));

        a0 = d0 * (x1 * x2) + d1 * (x0 * x2) + d2 * (x0 * x1);

        a1 = -d0 * (x1 + x2) - d1 * (x0 + x2) - d2 * (x0 + x1);

        a2 = d0 + d1 + d2;

        return new Parabola(a0, a1, a2);
    }


}
