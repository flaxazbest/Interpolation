package addons;

import javafx.scene.paint.Color;

public abstract class Parameters {

    public static final double A = 1.0;
    public static final double B = 20.0;
    public static final int parts = 4;
    public static final double H = 0.5;
    public static final double EPS = 1e-5;
    public static final double DELTA = 1e-2;
    public static final Interval I = new Interval(1, 20);
    public static final Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.DARKBLUE, Color.VIOLET, Color.GRAY};
    public static final Color background = Color.rgb(63, 63, 63);
    public static final double pointRadius = 6.0;

}
