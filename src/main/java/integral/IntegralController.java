package integral;

import addons.F;
import addons.Interval;
import addons.Window;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class IntegralController {

    @FXML
    private Canvas canvasLeft;
    @FXML
    private AnchorPane anchorLeft;
    @FXML
    private Canvas canvasMiddle;
    @FXML
    private AnchorPane anchorMiddle;
    @FXML
    private Canvas canvasRight;
    @FXML
    private AnchorPane anchorRight;
    @FXML
    private Canvas canvasAddon;
    @FXML
    private AnchorPane anchorAddon;
    @FXML
    private Slider slider;
    @FXML
    private TextField intervalParts;
    @FXML
    private TextField delta;
    @FXML
    private ChoiceBox<String> choise;
    @FXML
    private TextField iValue;
    @FXML
    private TextField iterations;

    private int parts;
    private GraphicsContext agc;
    private Window wD = null;

    private F f;
    private F antiD;
    Interval interval;


    public void draw(ActionEvent actionEvent) {

        Window w = new Window(interval.a-0.1, interval.b+0.1, -0.01, 1.1, (int)canvasLeft.getWidth(), (int)canvasLeft.getHeight());
        RectangleMethod rm = new RectangleMethod(f, antiD, interval);
        rm.draw(canvasLeft.getGraphicsContext2D(), w, Method.LEFT, parts);
        rm.draw(canvasLeft.getGraphicsContext2D(), w, Method.LEFT, parts);
        rm.draw(canvasRight.getGraphicsContext2D(), w, Method.RIGHT, parts);
        rm.draw(canvasRight.getGraphicsContext2D(), w, Method.RIGHT, parts);
        rm.draw(canvasMiddle.getGraphicsContext2D(), w, Method.MIDDLE, parts);
        rm.draw(canvasMiddle.getGraphicsContext2D(), w, Method.MIDDLE, parts);

    }

    public void initialize() {
        canvasLeft.widthProperty().bind(anchorLeft.widthProperty().add(-5));
        canvasLeft.heightProperty().bind(anchorLeft.heightProperty().add(-5));
        canvasMiddle.widthProperty().bind(anchorMiddle.widthProperty().add(-5));
        canvasMiddle.heightProperty().bind(anchorMiddle.heightProperty().add(-5));
        canvasRight.widthProperty().bind(anchorRight.widthProperty().add(-5));
        canvasRight.heightProperty().bind(anchorRight.heightProperty().add(-5));
        canvasAddon.widthProperty().bind(anchorAddon.widthProperty().add(-5));
        canvasAddon.heightProperty().bind(anchorAddon.heightProperty().add(-5));

        agc = canvasAddon.getGraphicsContext2D();

        f = new F() {
            @Override
            public double getY(double x) {
                return Math.sqrt(2*x - x*x);
            }
        };

        antiD = new F() {
            @Override
            public double getY(double x) {
                return (x-1) * Math.sqrt(2*x - x*x) + 0.5*Math.asin(x-1);
            }
        };

        interval = new Interval(0, 2);
        parts = (int)Math.pow(2, slider.getValue());
        slider.valueProperty().addListener((arg0, arg1, arg2) -> {
            int n = (int) Math.round(slider.getValue());
            parts = (int)Math.pow(2, n);
            slider.setValue(n);
//            System.err.println(parts);
        });
    }

    public void calc(ActionEvent actionEvent) {
        RectangleMethod rm = new RectangleMethod(f, antiD, interval);
        Method m = Method.LEFT;
        switch (choise.getSelectionModel().getSelectedIndex()) {
            case 0:
                m = Method.LEFT;
                break;
            case 1:
                m = Method.RIGHT;
                break;
            case 2:
                m = Method.MIDDLE;
                break;
            default:
                m = Method.LEFT;
        }

        IntegratedResult ir = rm.integrate(m);
        iValue.setText(String.format("%.6f", ir.value));
        iterations.setText("Iterations: " + ir.iteration);

        double d = f.getMaxAbsoluteDerivativeValueAtInterval(interval) / 4096 * 100;

        wD = new Window(0, ir.iteration, 0.3, 0.4, (int)canvasAddon.getWidth(), (int)canvasAddon.getHeight());
        Point2D lP = wD.realToScreen(new Point2D(0, d));
        Point2D rP = wD.realToScreen(new Point2D(ir.iteration+1, d));

        agc.setFill(Color.DARKGRAY);
        agc.fillRect(0,0,canvasAddon.getWidth(), canvasAddon.getHeight());
        agc.setLineWidth(3);
        agc.setStroke(Color.DARKGOLDENROD);
        agc.strokeLine(lP.getX(), lP.getY(), rP.getX(),rP.getY());
        agc.setLineWidth(1);
        agc.setStroke(Color.WHITE);
        agc.setFill(Color.BLUEVIOLET);
        final int radius = 3;
        for (int i=0; i<ir.d.size()-1; i++) {
            double dd = ir.d.get(i+1) / ir.d.get(i);
            lP = wD.realToScreen(new Point2D(i+1,dd));
            agc.fillOval(lP.getX()-radius, lP.getY()-radius, radius*2, radius*2);
//            System.out.println();
        }

    }

    public void mouseMove(MouseEvent mouseEvent) {
        if (wD != null) {
            Point2D p = wD.screenToReal(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            delta.setText(String.format("D = %.3f", p.getY()));
        }
    }
}
