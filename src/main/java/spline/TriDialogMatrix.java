package spline;

import javafx.geometry.Point2D;

public class TriDialogMatrix {

    private double[][] matrix;
    private int n;

    private TriDialogMatrix(Point2D[] table)
    throws MatrixBuildException{

        n = table.length;
        matrix = new double[10][n];
        double x3,xn;
        int i;

        if (n<3)
            throw new MatrixBuildException();

        x3 = table[2].getX() - table[0].getX();
        xn = table[n-1].getX() - table[n-3].getX();

        for (i=0; i<n-1; i++)
        {
            matrix[4][i] = table[i].getY();                                         // a
            matrix[9][i] = table[i+1].getX() - table[i].getX();                     // h
            matrix[8][i] = (table[i+1].getY() - table[i].getY())/matrix[9][i];      // delta
            matrix[0][i] = i>0?matrix[9][i]:x3;
            matrix[3][i] = i>0?3*(matrix[9][i]*matrix[8][i-1] + matrix[9][i-1]*matrix[8][i]):0; // f
        }

        matrix[1][0] = matrix[9][0];
        matrix[2][0] = matrix[9][0];
        for (int j=1; j<n-1; j++)
        {
            matrix[1][i] = 2*(matrix[9][i] + matrix[9][i-1]);
            matrix[2][i] = matrix[9][i];
        }
        matrix[1][n-1] = matrix[9][n-2];
        matrix[2][n-1] = xn;
        matrix[0][n-1] = matrix[9][n-2];

        i = n-1;
        matrix[3][0] = ((matrix[9][0]+2*x3)*matrix[9][1]*matrix[8][0] + Math.pow(matrix[9][0],2)*matrix[8][1])/x3;
        matrix[3][n-1]=(Math.pow(matrix[9][i-1],2)*matrix[8][i-2]+(2*xn+matrix[9][i-1])*matrix[9][i-2]*matrix[8][i-1])/xn;

        solve();

    }

    public static Cubic[] getCoeficients(Point2D[] table) throws MatrixBuildException {
        TriDialogMatrix tdm = new TriDialogMatrix(table);
        int n = table.length;
        Cubic[] result = new Cubic[n-1];

        int j;
        for (j=0; j<n-1; j++)
        {
            tdm.matrix[7][j] = (tdm.matrix[5][j+1]+tdm.matrix[5][j]-2*tdm.matrix[8][j])/(tdm.matrix[9][j]*tdm.matrix[9][j]);
            tdm.matrix[6][j] = 2*(tdm.matrix[8][j]-tdm.matrix[5][j])/tdm.matrix[9][j]-(tdm.matrix[5][j+1]-tdm.matrix[8][j])/tdm.matrix[9][j];
            result[j] = new Cubic(tdm.matrix[7][j], tdm.matrix[6][j], tdm.matrix[5][j], tdm.matrix[4][j]);
        }

        return result;
    }

    private void solve() {
        double[] alph = new double[n-1];
	    double[] beta = new double[n-1];

        int i;

        alph[0] = - matrix[2][0]/matrix[1][0];
        beta[0] = matrix[3][0]/matrix[1][0];

        for (i=1; i<n-1; i++)
        {
            alph[i] = -matrix[2][i]/(matrix[1][i] + matrix[0][i]*alph[i-1]);
            beta[i] = (matrix[3][i]-matrix[0][i]*beta[i-1])/(matrix[1][i] + matrix[0][i]*alph[i-1]);
        }

        matrix[5][n-1] = (matrix[3][n-1]-matrix[0][n-1]*beta[n-2])/(matrix[1][n-1] + matrix[0][n-1]*alph[n-2]);

        for (i=n-2; i>-1; i--)
        {
            matrix[5][i] = matrix[5][i+1] * alph[i] + beta[i];
        }
    }
}
