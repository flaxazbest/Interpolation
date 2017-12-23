package differential;

import addons.F2;
import addons.TableF2;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Prime {

    int Nx = 10;
    int Ny = 5;

    double min = 0.0;
    double max = 2.5;

    double deltaX = (max - min) / Nx;
    double deltaY = (max - min) / Ny;

    double[][] tableU = new double[Nx+1][Ny+1];
    double[][] DstarX = new double[Nx+1][Ny+1];
    double[][] DstarY = new double[Nx+1][Ny+1];
    double[][] DapproxX = new double[Nx+1][Ny+1];
    double[][] DapproxY = new double[Nx+1][Ny+1];
    double[][] DanaliticX = new double[Nx+1][Ny+1];
    double[][] DanaliticY = new double[Nx+1][Ny+1];

    double[] X = new double[Nx+1];
    double[] Y = new double[Ny+1];

    public double[][] getTableU() {
        return tableU;
    }

    public double[][] getDstarX() {
        return DstarX;
    }

    public double[][] getDstarY() {
        return DstarY;
    }

    public double[][] getDapproxX() {
        return DapproxX;
    }

    public double[][] getDapproxY() {
        return DapproxY;
    }

    public double[][] getDanaliticX() {
        return DanaliticX;
    }

    public double[][] getDanaliticY() {
        return DanaliticY;
    }

    public double[] getX() {
        return X;
    }

    public double[] getY() {
        return Y;
    }

    public Prime() {
        F2 u = new F2() {
            @Override
            public double getU(double x, double y) {
                return sin(x) + 0.1 * cos(2*x) + y*y*y;
            }
        };

        TableF2 tU = new TableF2(u, min, max, Nx, Ny);

        int i;

        double x = min;
        double y = min;

        for (i=0; i<=Nx; i++) {
            X[i] = x;
            x += deltaX;
        }

        for (i=0; i<=Ny; i++) {
            Y[i] = y;
            y += deltaY;
        }

        x = min;
        y = min;
        System.out.println("U(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                //System.out.printf("%8.3f", tU.getUindex(i, j));
                tableU[i][j] = u.getU(x, y);
                System.out.printf("%8.3f", tableU[i][j]);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");


        x = min;
        y = min;
        System.out.println("Dx*(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');
        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            for (i=0; i<=Nx; i++) {
                DstarX[i][j] = (tU.getUindex(i+1, j) - tU.getUindex(i, j))/deltaX;
                System.out.printf("%8.3f", DstarX[i][j]);
            }
            y += deltaY;
        }

        System.out.println("\n\n");


        x = min;
        y = min;
        System.out.println("Dy*(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');
        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            for (i=0; i<=Nx; i++) {
                DstarY[i][j] = (tU.getUindex(i, j+1) - tU.getUindex(i, j))/deltaY;
                System.out.printf("%8.3f", DstarY[i][j]);
            }
            y += deltaY;
        }

        System.out.println("\n\n");


        x = min;
        y = min;
        System.out.println("Dx~(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');
        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            for (i=0; i<=Nx; i++) {
                DapproxX[i][j] = (
                        tU.getUindex(i+1, j+1)
                                - tU.getUindex(i-1, j+1)
                                + tU.getUindex(i+1, j-1)
                                - tU.getUindex(i-1, j-1)
                ) / (4 * deltaX);
                System.out.printf("%8.3f", DapproxX[i][j]);
            }
            y += deltaY;
        }

        System.out.println("\n\n");


        x = min;
        y = min;
        System.out.println("Dy~(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');
        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            for (i=0; i<=Nx; i++) {
                DapproxY[i][j] = (
                        tU.getUindex(i+1, j+1)
                                - tU.getUindex(i+1, j-1)
                                + tU.getUindex(i-1, j+1)
                                - tU.getUindex(i-1, j-1)
                ) / (4 * deltaY);
                System.out.printf("%8.3f", DapproxY[i][j]);
            }
            y += deltaY;
        }

        System.out.println("\n\n");

        F2 dudx = new F2() {
            @Override
            public double getU(double x, double y) {
                return cos(x) - 0.2 * sin(2*x);
            }
        };

        x = min;
        y = min;
        System.out.println("Dx(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                DanaliticX[i][j] = dudx.getU(x, y);
                System.out.printf("%8.3f", DanaliticX[i][j]);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");



        F2 dudy = new F2() {
            @Override
            public double getU(double x, double y) {
                return 3 * y*y;
            }
        };

        x = min;
        y = min;
        System.out.println("Dy(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                DanaliticY[i][j] = dudy.getU(x, y);
                System.out.printf("%8.3f", DanaliticY[i][j]);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");


        x = min;
        y = min;
        System.out.println("dDx*");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                System.out.printf("%8.3f", (DanaliticX[i][j] - DstarX[i][j])/DanaliticX[i][j]*100);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");



        x = min;
        y = min;
        System.out.println("dDy*");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                System.out.printf("%8.3f", (DanaliticY[i][j] - DstarY[i][j])/DanaliticY[i][j]*100);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");



        x = min;
        y = min;
        System.out.println("dDx~");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                System.out.printf("%8.3f", (DanaliticX[i][j] - DapproxX[i][j])/DanaliticX[i][j]*100);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");



        x = min;
        y = min;
        System.out.println("dDy~");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');

        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            x = min;
            for (i=0; i<=Nx; i++) {
                System.out.printf("%8.3f", (DanaliticY[i][j] - DapproxY[i][j])/DanaliticY[i][j]*100);
                x += deltaX;
            }
            y += deltaY;
        }

        System.out.println("\n\n");


        //addition

        final int part = 4;
        TableF2 tU4 = new TableF2(u, min, max, Nx*part, Ny*part);

        x = min;
        y = min;
        System.out.println("D4x~(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');
        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            for (i=0; i<=Nx; i++) {
                System.out.printf("%8.3f", (
                        tU4.getUindex(part*i+1, part*j+1)
                                - tU4.getUindex(part*i-1, part*j+1)
                                + tU4.getUindex(part*i+1, part*j-1)
                                - tU4.getUindex(part*i-1, part*j-1)
                ) / (4 * deltaX / part));
            }
            y += deltaY;
        }

        System.out.println("\n\n");

        x = min;
        y = min;
        System.out.println("D4y~(x,y)");
        System.out.print(" Y  \\ X |");
        for (i=0; i<=Nx; i++) {
            System.out.printf("%8.3f", x);
            x += deltaX;
        }
        System.out.println();
        for (i=0; i<=8*(Nx+2); i++)
            System.out.print('-');
        for (int j=0; j<=Ny; j++) {
            System.out.printf("\n%6.3f  |", y);
            for (i=0; i<=Nx; i++) {
                System.out.printf("%8.3f", (
                        tU4.getUindex(part*i+1, part*j+1)
                                - tU4.getUindex(part*i+1, part*j-1)
                                + tU4.getUindex(part*i-1, part*j+1)
                                - tU4.getUindex(part*i-1, part*j-1)
                ) / (4 * deltaY / part));
            }
            y += deltaY;
        }

        System.out.println("\n\n");

    }
}
