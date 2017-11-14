package spline;

import addons.F;
import addons.Parameters;
import addons.Window;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static addons.Parameters.*;

public class SplineC {

    private Cubic[] splines;
    private F function;
    private Point2D[] table;
    private int n;

    private double[] b;

    public SplineC(F function, int n) {
        this.function = function;
        this.n = n;

        table = new Point2D[n];
        double hh = (B - A) / (n-1);
        for (int i=0; i<n; i++) {
            table[i] = new Point2D(A+i*hh, function.getY(A+i*hh));
        }

        double[] a = new double[n-1];
        double[] c = new double[n-1];
        double[] d = new double[n-1];
        double[] delta = new double[n-1];
        double[] h = new double[n-1];
        double[][] TriDiagMatrix = new double[3][n];
        b = new double[n];

        TriDiagMatrix[0] = new double[n];
        TriDiagMatrix[1] = new double[n];
        TriDiagMatrix[2] = new double[n];

        double[] f = new double[n];
        double x3,xn;
        int i;

        if (n<3)
            throw new IllegalArgumentException();

        x3 = table[2].getX() - table[0].getX();
        xn = table[n-1].getX() - table[n-3].getX();

        for (i=0; i<n-1; i++) {
            a[i] = table[i].getY();
            h[i] = table[i+1].getX() - table[i].getX();
            delta[i] = (table[i+1].getY() - table[i].getY())/h[i];
            TriDiagMatrix[0][i] = i>0?h[i]:x3;
            f[i] = i>0?3*(h[i]*delta[i-1] + h[i-1]*delta[i]):0;
        }
        TriDiagMatrix[1][0] = h[0];
        TriDiagMatrix[2][0] = h[0];
        for (int ii=1; ii<n-1;ii++) {
            TriDiagMatrix[1][ii] = 2*(h[ii] + h[ii-1]);
            TriDiagMatrix[2][ii] = h[ii];
        }
        TriDiagMatrix[1][n-1] = h[n-2];
        TriDiagMatrix[2][n-1] = xn;
        TriDiagMatrix[0][n-1] = h[n-2];


        i = n-1;
        f[0] = ((h[0]+2*x3)*h[1]*delta[0] + Math.pow(h[0],2)*delta[1])/x3;
        f[n-1]=(Math.pow(h[i-1],2)*delta[i-2]+(2*xn+h[i-1])*h[i-2]*delta[i-1])/xn;

        solveTriDiag(TriDiagMatrix,f);

        splines = new Cubic[n-1];
//        for (int j=0; j<n-1; j++) {
//            splines[j] = new Cubic();
//        }

        for (int j=0; j<n-1; j++)
        {
            d[j] = (b[j+1]+b[j]-2*delta[j])/(h[j]*h[j]);
            c[j] = 2*(delta[j]-b[j])/h[j]-(b[j+1]-delta[j])/h[j];

            //*              -
            double aa = a[j] - b[j]*table[j].getX() + c[j] * Math.pow(table[j].getX(), 2)
                             - d[j] * Math.pow(table[j].getX(), 3);
            double bb = b[j] - 2 * c[j] * table[j].getX() + 3 * d[j] * Math.pow(table[j].getX(), 2);
            double cc = c[j] - 3 * d[j] * table[j].getX();
            double dd = d[j];

            splines[j] = new Cubic(aa, bb, cc, dd);

//            try {
//                splines[j].setKoeficient(aa, 0);
//                splines[j].setKoeficient(bb, 1);
//                splines[j].setKoeficient(cc, 2);
//                splines[j].setKoeficient(dd, 3);
//            } catch (WrongCoeficientException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void solveTriDiag(double[][] TDM, double[] F)
    {
        double[] alph = new double[n-1];
        double[] beta = new double[n-1];

        alph[0] = - TDM[2][0]/TDM[1][0];
        beta[0] = F[0]/TDM[1][0];
        for (int i=1; i<n-1; i++)
        {
            alph[i] = -TDM[2][i]/(TDM[1][i] + TDM[0][i]*alph[i-1]);
            beta[i] = (F[i]-TDM[0][i]*beta[i-1])/(TDM[1][i] + TDM[0][i]*alph[i-1]);
        }
        b[n-1] = (F[n-1]-TDM[0][n-1]*beta[n-2])/(TDM[1][n-1] + TDM[0][n-1]*alph[n-2]);
        for (int i=n-2; i>-1; i--)
        {
            b[i] = b[i+1] * alph[i] + beta[i];
        }
    }

    public void draw(GraphicsContext gc, Window w) {
        for (int i=0; i<n-1; i++) {
            splines[i].draw(gc, w, table[i].getX(), table[i+1].getX(), Color.BLUEVIOLET, 2);
        }

        gc.setLineWidth(1);
        gc.setFill(colors[2]);
        gc.setStroke(colors[7]);
        for (int i=0; i<n; i++) {
            Point2D p = w.realToScreen(table[i]);
            gc.fillOval(p.getX()-Parameters.pointRadius/2, p.getY()-Parameters.pointRadius/2,
                    Parameters.pointRadius, Parameters.pointRadius);
            gc.strokeOval(p.getX()-Parameters.pointRadius/2, p.getY()-Parameters.pointRadius/2,
                    Parameters.pointRadius, Parameters.pointRadius);
        }
    }

    public void drawVerticalLines(GraphicsContext gc, Window w) {
        gc.setStroke(colors[7]);
        gc.setLineWidth(1);
        gc.setLineDashes(10);
        Point2D p;
        for (int i=0; i<n; i++) {
            p = w.realToScreen(table[i]);
            gc.strokeLine(p.getX(), 0, p.getX(), w.getH());
        }
        gc.setLineDashes(0);
    }

    public void drawSplines1(GraphicsContext gc, Window w) {
        for (int i=0; i<n-1; i++) {
            splines[i].getPrime().draw(gc, w, table[i].getX(), table[i+1].getX(), Color.LIGHTGREEN, 2);
        }

        Point2D p;

        gc.setFill(Color.DARKGREEN);
        for (int i=0; i<n-1; i++) {
            p = w.realToScreen(new Point2D(table[i].getX(), splines[i].getPrime(table[i].getX())));
            gc.fillOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                    Parameters.pointRadius, Parameters.pointRadius);
        }
        p = w.realToScreen(new Point2D(table[n-1].getX(), splines[n-2].getPrime(table[n-1].getX())));
        gc.fillOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                Parameters.pointRadius, Parameters.pointRadius);

    }

    public void drawSplines2(GraphicsContext gc, Window w) {
        for (int i=0; i<n-1; i++) {
            splines[i].getPrime().getPrime().draw(gc, w, table[i].getX(), table[i+1].getX(), Color.LIGHTBLUE, 2);
        }
        Point2D p;
        gc.setFill(Color.DARKBLUE);
        for (int i=0; i<n-1; i++) {
            p = w.realToScreen(new Point2D(table[i].getX(), splines[i].getPrime().getPrime(table[i].getX())));
            gc.fillOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                    Parameters.pointRadius, Parameters.pointRadius);
        }
        p = w.realToScreen(new Point2D(table[n-1].getX(), splines[n-2].getPrime().getPrime(table[n-1].getX())));
        gc.fillOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                Parameters.pointRadius, Parameters.pointRadius);
    }

    public void drawSplines3(GraphicsContext gc, Window w) {
        for (int i=0; i<n-1; i++) {
            splines[i].getPrime().getPrime().getPrime().draw(gc, w, table[i].getX(), table[i+1].getX(), Color.LIGHTCORAL, 2);
        }
        Point2D p;
        gc.setLineWidth(1);
        gc.setStroke(Color.DARKRED);
        gc.setFill(Color.DARKRED);
        for (int i=0; i<n-1; i++) {
            p = w.realToScreen(new Point2D(table[i].getX(), splines[i].getPrime().getPrime().getPrime(table[i].getX())));
            gc.fillOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                    Parameters.pointRadius, Parameters.pointRadius);
            p = w.realToScreen(new Point2D(table[i+1].getX(), splines[i].getPrime().getPrime().getPrime(table[i+1].getX())));
            gc.strokeOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                    Parameters.pointRadius, Parameters.pointRadius);

        }
        p = w.realToScreen(new Point2D(table[n-1].getX(), splines[n-2].getPrime().getPrime().getPrime(table[n-1].getX())));
        gc.fillOval(p.getX() - Parameters.pointRadius / 2, p.getY() - Parameters.pointRadius / 2,
                Parameters.pointRadius, Parameters.pointRadius);
    }

}