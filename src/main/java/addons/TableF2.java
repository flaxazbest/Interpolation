package addons;

public class TableF2 {

    private F2 u;
    private double min;
    private double max;
    private int Nx;
    private int Ny;
    private double deltaX;
    private double deltaY;

    public TableF2(F2 u, double min, double max, int nx, int ny) {
        this.u = u;
        this.min = min;
        this.max = max;
        Nx = nx;
        Ny = ny;
        deltaX = (max-min)/Nx;
        deltaY = (max-min)/Ny;
    }

    public double getUindex(int i, int j) {
        return u.getU(min + deltaX*i, min + deltaY*j);
    }
}
