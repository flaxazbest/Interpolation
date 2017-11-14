package spline;

import addons.F;
import addons.Interval;
import addons.Parameters;
import addons.Window;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SplineController {

    private Window w;
    private Window wG;
    private F f;
    private GraphicsContext gc;
    private GraphicsContext gcG;
    private Point2D pp;

    @FXML
    private Canvas canvas;
    @FXML
    private Canvas canvasG;

    @FXML
    TextField coordinatesX;

    @FXML
    TextField coordinatesY;

    @FXML
    TextField parts;

    @FXML
    Slider slider;

    @FXML
    Tab tabG;

    public void onDraw(ActionEvent actionEvent) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Parameters.background);
        gc.fillRect(0,0,w.getW(), w.getH());
        f.draw(gc, w, Parameters.A, Parameters.B, Color.LIGHTGREEN, 3);

        int n = (int)slider.getValue() + 1;
//        SplineC s = new SplineC(f, Parameters.parts+1);
        SplineC s = new SplineC(f, n);
        s.drawVerticalLines(gc, w);
        s.draw(gc, w);

        tabG.setDisable(false);
        gcG = canvasG.getGraphicsContext2D();
        wG = new Window(Parameters.A, Parameters.B, -6.00, 5.5, (int)canvasG.getWidth(), (int)canvasG.getHeight());
        gcG.setFill(Color.DARKGREY);
        gcG.fillRect(0,0,canvasG.getWidth(),canvasG.getHeight());

        s.drawVerticalLines(gcG, wG);
        s.drawSplines1(gcG, wG);
        s.drawSplines2(gcG, wG);
        s.drawSplines3(gcG, wG);

    }

    public void move(MouseEvent mouseEvent) {
        pp = w.screenToReal(mouseEvent.getX(), mouseEvent.getY());
        coordinatesX.setText(String.format("X: %.4f", pp.getX()));
        coordinatesY.setText(String.format("Y: %.4f", pp.getY()));
    }

    public void initialize() {

        parts.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        slider.valueProperty()
                )
        );

        slider.setValue(Parameters.parts);
        f = new F() {
            @Override
            public double getY(double x) {
                return Math.sqrt(x) + Math.cos(x);
//                return Math.sin(4 * x);
            }
        };
        f.setPrime(new F() {
            @Override
            public double getY(double x) {
                return 0.5/Math.sqrt(x) - Math.sin(x);
//                return 4 * Math.cos(4 * x);
            }
        });
        Interval interval = f.getMinMaxY(new Interval(Parameters.A, Parameters.B));
        double delta = interval.length() * 0.1;
        w = new Window(Parameters.A, Parameters.B, interval.a-delta, interval.b+delta, (int)canvas.getWidth(), (int)canvas.getHeight());


        gc = canvas.getGraphicsContext2D();
        gc.setFill(Parameters.background);
        gc.fillRect(0,0,w.getW(), w.getH());
        f.draw(gc, w, Parameters.A, Parameters.B, Color.LIGHTGREEN, 3);
    }

    public void moveG(MouseEvent mouseEvent) {
        pp = wG.screenToReal(mouseEvent.getX(), mouseEvent.getY());
        coordinatesX.setText(String.format("X: %.4f", pp.getX()));
        coordinatesY.setText(String.format("Y: %.4f", pp.getY()));
    }
}
