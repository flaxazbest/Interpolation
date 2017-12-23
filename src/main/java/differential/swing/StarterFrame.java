package differential.swing;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import differential.delauney.Point;
import differential.javafx.jfx3d.DelauneyModifier;
import differential.javafx.jfx3d.Mesh3DChartPanel;
import differential.Prime;
import javafx.geometry.Point3D;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class StarterFrame extends JFrame {

    public static void main(String[] args) {
        new StarterFrame().showChart();
    }

    // constants to calculate gauss probability density function
    private final double sdX   = 1;
    private final double sdY   = 1;
    private final double meanX = 0;
    private final double meanY = 0;
    private final double p     = 0.0;

    private final double a     = 1.0 / (2.0 * Math.PI * sdX * sdY * Math.sqrt(1.0 - (p * p)));
    private final double b     = -1.0 / (2.0 - (p * p * 2.0));

    public StarterFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void showChart() {
        final Mesh3DChartPanel chart = new Mesh3DChartPanel();
        chart.setDelauneyModifier(new MyDelauneyModifier());
        chart.setAxisTitleX("X-Axis");
        chart.setAxisTitleY("Z-Axis");
        chart.setAxisTitleZ("Y-Axis");
        chart.addMeshControlPanel();
        chart.showMeshPanel(getPointsForGauss());

        getContentPane().add(chart);
        setSize(1200, 800);
        setVisible(true);
    }

    private List<Point3D> getPointsForGauss() {
        List<Point3D> result = new ArrayList<Point3D>();
        /*for (double x = -3.0; x <= 3.0; x = x + 0.20) {
            for (double y = -3.0; y <= 3.0; y = y + 0.20) {
                result.add(new Point3D(x, calculatePDF(x, y), y));
            }
        }*/

        Prime prime = new Prime();


        for (int j=0; j<=prime.getNy(); j++) {
            for (int i=0; i<=prime.getNx(); i++) {
                result.add(new Point3D(prime.getX()[i], prime.getTableU()[i][j], prime.getY()[j]));
//                result.add(new Point3D(prime.getX()[i], prime.getDstarX()[i][j], prime.getY()[j]));
//                result.add(new Point3D(prime.getX()[i], prime.getDstarY()[i][j], prime.getY()[j]));
//                result.add(new Point3D(prime.getX()[i], prime.getDapproxX()[i][j], prime.getY()[j]));
//                result.add(new Point3D(prime.getX()[i], prime.getDapproxY()[i][j], prime.getY()[j]));
//                result.add(new Point3D(prime.getX()[i], prime.getDanaliticX()[i][j], prime.getY()[j]));
//                result.add(new Point3D(prime.getX()[i], prime.getDanaliticY()[i][j], prime.getY()[j]));
            }
        }
        return result;
    }

    private double calculatePDF(double x, double y) {
        /*double c = Math.pow(x - meanX, 2.0) / (sdX * sdX);
        double d = Math.pow(y - meanY, 2.0) / (sdY * sdY);
        double e = -2.0 * p * (x * meanX) * (y * meanY) / (sdX * sdY);

        return a * Math.exp(b * (c + d + e));*/
        return sin(x) + cos(y);
    }

    public static class MyDelauneyModifier implements DelauneyModifier {

        @Override
        public Point convertPoint3d4Delauney(Point3D point) {
            return new Point(point.getX(), point.getZ(), point.getY());
        }

        @Override
        public Point3D convertPointFromDelauney(Point coord) {
            return new Point3D(coord.getX(), coord.getZ(), coord.getY());
        }

    }

}
