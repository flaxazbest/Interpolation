package newton;

import addons.F;
import addons.Interval;

import java.util.ArrayList;
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
    }

    public int getRootsCount() {
        return intervals.size();
    }

    public Interval getInterval(int no) {
        return intervals.get(no);
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

    public LinkedList<RootEquation> solve(int firstApprox, double epsilon) {
        LinkedList<RootEquation> arrayList = new LinkedList<>();
        for (int i=0; i<intervals.size(); i++) {
            //System.out.println(intervals.get(i));
            double a = intervals.get(i).a;
            double b = intervals.get(i).b;
            double x0 = a + firstApprox*(b-a)/3;
            double x = x0 - f.getY(x0) / f.getPrime(x0);
            int it = 0;
            while (Math.abs( x - x0 ) > epsilon) {
                x0 = x;
                x = x0 - f.getY(x0) / f.getPrime(x0);
                it++;
            }
            RootEquation re = new RootEquation(x, it);
            arrayList.add(re);
        }
        return arrayList;
    }


    public static void main(String[] args) {
        Newton n = new Newton(new Function());

    }
}
