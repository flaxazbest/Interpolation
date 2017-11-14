package iteration;

import addons.F;
import addons.Interval;
import addons.Parameters;
import addons.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;

public class IterationController {

    private Window w;
    private Window gW;
    private Window dW;
    private F f, g, bisector;
    private GraphicsContext gcF;
    private GraphicsContext gcG;
    private GraphicsContext gcD;
    private Point2D pp;


    @FXML
    Canvas function;
    @FXML
    Canvas approx;
    @FXML
    Canvas derivative;
    @FXML
    Button draw;
    @FXML
    Label cX;
    @FXML
    Label cY;
    @FXML
    TextArea area;

    public void drawClick(ActionEvent actionEvent) {
        f.draw(gcF, w, Parameters.A, Parameters.B);
        g.draw(gcG, gW, Parameters.A, Parameters.B);
        bisector.draw(gcG, gW, Parameters.A, Parameters.B);

        g.getPrime().draw(gcD, dW, Parameters.A, Parameters.B);
        gcD.setStroke(Color.WHITE);
        gcD.setLineWidth(1);
        Point2D st = new Point2D(Parameters.A, 1.0);
        st = dW.realToScreen(st);
        Point2D en = new Point2D(Parameters.B, 1.0);
        en = dW.realToScreen(en);
        gcD.strokeLine(st.getX(), st.getY(), en.getX(), en.getY());

        st = new Point2D(Parameters.A, -1.0);
        st = dW.realToScreen(st);
        en = new Point2D(Parameters.B, -1.0);
        en = dW.realToScreen(en);
        gcD.strokeLine(st.getX(), st.getY(), en.getX(), en.getY());

        Iteration it = new Iteration(f, g, null);

        for (Interval interval: it.getIntervals()) {
            showInterval(gcF, w, interval);
            showInterval(gcG, gW, interval);
            showInterval(gcD, dW, interval);
            try {
                it.clarify(interval);
                it.clarifyR(interval, 1);
            } catch (MethodDivergenceException e) {
                System.err.println("Wrong condition in interval " + interval);;
            }
        }

        area.setText(it.rootsToString());

    }

    public void initialize() {
        gcF = function.getGraphicsContext2D();
        gcG = approx.getGraphicsContext2D();
        gcD = derivative.getGraphicsContext2D();


//*

        f = new F() {
            @Override
            public double getY(double x) {
                return 3*Math.sin(Math.sqrt(Math.abs(x))) + 0.35*x - 3.8;
            }
        };

        g = new F() {
            @Override
            public double getY(double x) {
                return (3.8 - 3*Math.sin(Math.sqrt(Math.abs(x)))) / 0.35;
            }
        };

        g.setPrime(new F() {
            @Override
            public double getY(double x) {
                return - ( 30.0 * Math.cos(Math.sqrt(Math.abs(x)))) / ( 7 * Math.sqrt(Math.abs(x)) );
            }
        });

//*/

/*
        f = new F() {
            @Override
            public double getY(double x) {
                return x*x*x + x - 3;
            }
        };
        g = new F() {
            @Override
            public double getY(double x) {
                return Math.pow(3 - x, (1.0/3.0));
            }
        };
        g.setPrime(new F() {
            @Override
            public double getY(double x) {
                return - 1.0 / (3 * Math.pow(3 - x, 2.0/3.0));
            }
        });
//*/
        bisector = new F() {
            @Override
            public double getY(double x) {
                return x;
            }
        };

        w = new Window(Parameters.A, Parameters.B, -1.0, 1.0, (int)function.getWidth(), (int)function.getHeight());
        gW = new Window(Parameters.A, Parameters.B, -2.0, 20.0, (int)function.getWidth(), (int)function.getHeight());
        dW = new Window(Parameters.A, Parameters.B, -2.0, 2.0, (int)derivative.getWidth(), (int)derivative.getHeight());
        flushCanvas(function);
        flushCanvas(approx);
        flushCanvas(derivative);

    }

    private void flushCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
        canvas.getGraphicsContext2D().fillRect(0,0,w.getW(), w.getH());
    }

    public void moved(MouseEvent mouseEvent) {
        pp = w.screenToReal(mouseEvent.getX(), mouseEvent.getY());
        cX.setText(String.format("X: %.3f", pp.getX()));
        cY.setText(String.format("Y: %.3f", pp.getY()));
    }

    public void movedG(MouseEvent mouseEvent) {
        pp = gW.screenToReal(mouseEvent.getX(), mouseEvent.getY());
        cX.setText(String.format("X: %.3f", pp.getX()));
        cY.setText(String.format("Y: %.3f", pp.getY()));
    }

    private void showInterval(GraphicsContext gc, Window w, Interval in) {
        gc.setFill(Color.grayRgb(0, 0.1647));
        gc.setFillRule(FillRule.EVEN_ODD);

        Point2D st = new Point2D(in.a, 100.0);
        st = w.realToScreen(st);
        Point2D en = new Point2D(in.b, -100.0);
        en = w.realToScreen(en);
        gc.fillRect(st.getX(), st.getY(), (en.getX()-st.getX()), (en.getY() - st.getY()));
    }
}
