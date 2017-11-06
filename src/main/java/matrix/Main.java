package matrix;

public class Main {

    public static void main(String[] args) {

        Matrix m = new Matrix();
        m.solveGauss();
        m.printInMatrixMode();
        m.printSolve();
        System.out.println();
        m.printR();


        try {
            m.solveGaussWithMainElement();
            m.printInMatrixMode();
            m.printSolve();
            System.out.println();
            m.printR();
        } catch (BadSLAR badSLAR) {
            System.out.println("Bad SLAR");
        }

    }

}
