package newton;

import addons.F;

public class Function extends F {

    @Override
    public double getY(double x) {
        return 3 * Math.sin(Math.sqrt(Math.abs(x))) + 0.35 * x - 3.8;
    }
}
