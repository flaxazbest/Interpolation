package iteration;

import addons.F;
import addons.Interval;
import addons.Parameters;
import addons.Window;

public class Iteration {

    private F f;
    private F g;
    private Window w;

    Iteration(F f, F g, Window w) {
        this.f = f;
        this.g = g;
        this.w = w;
    }

    public double clarify(Interval interval)
    throws MethodDivergenceException {

        if (g.getMaxAbsoluteDerivativeValueAtInterval(interval) >= 1)
            throw new MethodDivergenceException();

        double x = interval.getMiddle();

        double xi=x+2.0* Parameters.EPS;
        for (int i = 1; Math.abs(xi - x) > Parameters.EPS; i++)
        {
            x = xi;
            xi = g.getY(x);
            System.out.println(i + ":\t" + xi);;
        }
        return xi;
    }

    public static void main(String[] args) throws MethodDivergenceException {

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

        Iteration it = new Iteration(f, g, null);
        it.clarify(new Interval(1.5, 2.7));

    }

}
