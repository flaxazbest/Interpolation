package spline;

import addons.F;

public class Cubic extends F {
    private double[] a;

    public Cubic() {
        a = new double[4];
    }

    public Cubic(double[] a) {
        this.a = a;
    }

    double getKoeficient(int i) throws WrongCoeficientException {
        if (a == null || i < 0 || i>3)
            throw new WrongCoeficientException();
        return a[i];
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

    public Cubic(double a0, double a1, double a2, double a3) {
        this();
        a[0] = a0;
        a[1] = a1;
        a[2] = a2;
        a[3] = a3;
    }

    @Override
    public double getY(double x) {
        return a[3]*x*x*x + a[2]*x*x + a[1]*x + a[0];
    }
}
