package spline;

import addons.F;
import addons.Parameters;
import addons.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SplineController {

    private Window w;
    private F f;
    private GraphicsContext gc;
    private Point2D pp;

    @FXML
    private Canvas canvas;

    @FXML
    TextField coordinatesX;

    @FXML
    TextField coordinatesY;

    public void onDraw(ActionEvent actionEvent) {
        Spline s = new Spline(f);
        s.draw(gc, w);
    }

    public void move(MouseEvent mouseEvent) {
        pp = w.screenToReal(mouseEvent.getX(), mouseEvent.getY());
        coordinatesX.setText(String.format("X: %.4f", pp.getX()));
        coordinatesY.setText(String.format("Y: %.4f", pp.getY()));
    }

    public void initialize() {
        w = new Window(Parameters.A, Parameters.B, 0.0, 3.0, (int)canvas.getWidth(), (int)canvas.getHeight());
        f = new F() {
            @Override
            public double getY(double x) {
                return Math.sqrt(x) + Math.cos(x);
            }
        };
        f.setPrime(new F() {
            @Override
            public double getY(double x) {
                return 0.5/Math.sqrt(x) - Math.sin(x);
            }
        });

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,w.getW(), w.getH());
        f.draw(gc, w, Parameters.A, Parameters.B, Color.DARKBLUE);
    }
}
