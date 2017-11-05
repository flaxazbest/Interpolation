package newton;

import addons.F;
import addons.Interval;

import java.util.LinkedList;

public class Newton {

    private F f;
    private LinkedList<Double> x;
    private LinkedList<Interval> intervals;

    public Newton(F f) {
        this.f = f;
        x = new LinkedList<>();
        intervals = new LinkedList<>();

        splitIntervals();
//        for (Interval i: intervals) {
//            System.out.println(i);
//            //System.out.println(solve(i, (i.a+i.b)/2));
//            System.out.println(solve(i, i.a+3*(i.b-i.a)/3));
//        }

        for (int i=0; i<intervals.size(); i++) {
            System.out.println(intervals.get(i));
            System.out.println(solve(intervals.get(i), intervals.get(i).a+3*(intervals.get(i).b-intervals.get(i).a)/3));
        }

    }

    private void splitIntervals() {
        double a = Parameters.I.a;
        while (a < Parameters.I.b) {
            double l = f.getY(a);
            if (l == 0)
                x.add(a);
            double r = f.getY(a+Parameters.H);

            if (l * r < 0) {
                intervals.add(new Interval(a, a+Parameters.H));
            }

            a += Parameters.H;
        }
    }

    private double solve(Interval interval, double x0) {
        double x = x0 - f.getY(x0) / f.getPrime(x0);
        int it = 0;
        while (Math.abs( x - x0 ) > Parameters.EPS) {
            x0 = x;
            x = x0 - f.getY(x0) / f.getPrime(x0);
            it++;
        }
        System.err.println(it);
        return x;
    }


    public static void main(String[] args) {
        Newton n = new Newton(new Function());

    }
}
