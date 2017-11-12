package addons;

public class Interval {

    public double a;
    public double b;

    public Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

    Interval() {
        this(0.0, 0.0);
    }

    public double getMiddle() {
        return (a + b) / 2.0;
    }

    @Override
    public String toString() {
        return "(" + a + ";" + b + ")";
    }
}
