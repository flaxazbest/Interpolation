package integral;

import java.util.LinkedList;

public class IntegratedResult {

    public double value;
    public int iteration;
    public double accuranceValue;
    public double delta;
    public LinkedList<Double> d;

    public IntegratedResult() {
        d = new LinkedList<>();
    }
}
