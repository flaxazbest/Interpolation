package addons;

public class Interval {

    public double a;
    public double b;

    public Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "(" + a + ";" + b + ")";
    }
}
