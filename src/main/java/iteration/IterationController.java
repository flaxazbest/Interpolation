package iteration;

import addons.F;
import addons.Parameters;
import addons.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class IterationController {

    private Window w;
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

    public void drawClick(ActionEvent actionEvent) {
        f.draw(gcF, w, -5.0, 5.0);
        g.draw(gcG, w, -5.0, 5.0);
        bisector.draw(gcG, w, -5.0, 5.0);
        g.getPrime().draw(gcD, dW, -5.0, 5.0);
    }

    public void initialize() {
        gcF = function.getGraphicsContext2D();
        gcG = approx.getGraphicsContext2D();
        gcD = derivative.getGraphicsContext2D();

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
        bisector = new F() {
            @Override
            public double getY(double x) {
                return x;
            }
        };

        w = new Window(Parameters.A, Parameters.B, -5.0, 5.0, (int)function.getWidth(), (int)function.getHeight());
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
}
