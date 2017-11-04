package parabolic;

import addons.F;

public class Function extends F {

    @Override
    public double getY(double x) {
        return Math.sqrt(x) + Math.cos(x);
//        return x*x;
    }
}
