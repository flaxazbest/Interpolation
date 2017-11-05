package addons;

public class Interval {

    public double a;
    public double b;

    public Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Interval() {
        this(0.0, 0.0);
    }

    @Override
    public String toString() {
        return "(" + a + ";" + b + ")";
    }
}
