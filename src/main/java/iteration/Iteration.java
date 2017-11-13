package iteration;

import addons.F;
import addons.Interval;
import addons.Parameters;
import addons.Window;

import java.util.LinkedList;

public class Iteration {

    private F f;
    private F g;
    private Window w;

    private LinkedList<LinkedList<Double>> roots;
    private LinkedList<Interval> intervals;

    Iteration(F f, F g, Window w) {
        this.f = f;
        this.g = g;
        this.w = w;
        roots = new LinkedList<>();
        intervals = new LinkedList<>();
        splitIntervals();
    }

    public double clarify(Interval interval)
    throws MethodDivergenceException {

        if (g.getMaxAbsoluteDerivativeValueAtInterval(interval) >= 1)
            throw new MethodDivergenceException();

        roots.add(new LinkedList<>());
        int n = roots.size()-1;

//        double x = interval.getMiddle();
        double x = interval.a;

        double xi=x+2.0* Parameters.EPS;
        for (int i = 1; Math.abs(xi - x) > Parameters.EPS; i++)
        {
            x = xi;
            xi = g.getY(x);
//            System.out.println(i + ":\t" + xi);;
            roots.get(n).add(xi);
        }
        System.out.println("\n\n");
        return xi;
    }

    public static void main(String[] args) {

/*

        F f = new F() {
            @Override
            public double getY(double x) {
                return 3*x*x*x + x - 3;
            }
        };
        F g = new F() {
            @Override
            public double getY(double x) {
                return Math.pow(3-x, 1.0/3.0);
            }
        };

//*/

//*
        F f = new F() {
            @Override
            public double getY(double x) {
                return 3*Math.sin(Math.sqrt(Math.abs(x))) + 0.35*x - 3.8;
            }
        };
        F g = new F() {
            @Override
            public double getY(double x) {
                return (3.8 - 3*Math.sin(Math.sqrt(Math.abs(x)))) / 0.35;
            }
        };
//*/
        Iteration it = new Iteration(f, g, null);

        for (Interval interval: it.getIntervals()) {
            try {
                it.clarify(interval);
            } catch (MethodDivergenceException e) {
                System.err.println("no conditions on " + interval);
            }
        }
    }


    public LinkedList<Interval> getIntervals() {
        return intervals;
    }

    private void splitIntervals() {
        double a = Parameters.A;
        while (a < Parameters.B) {
            double l = f.getY(a);
            if (l == 0) {
                roots.add(new LinkedList<>());
                roots.get(roots.size()-1).add(a);
            }
            double r = f.getY(a+Parameters.H);
            if (l * r < 0) {
                intervals.add(new Interval(a, a+Parameters.H));
            }
            a += Parameters.H;
        }
    }

    public String rootsToString() {
        if (roots == null)
            return "No roots";
        StringBuilder sb = new StringBuilder();
        for (LinkedList<Double> r: roots) {
            for (int i=0; i<r.size(); i++) {
                sb.append(i+1).append(":\t").append(r.get(i)).append("\n");
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }

}
