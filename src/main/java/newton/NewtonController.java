package newton;

import addons.Interval;
import addons.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class NewtonController {

    Window w = null;

    @FXML
    Canvas canvas;

    @FXML
    TextField xx;

    @FXML
    TextField yy;

    @FXML
    TextArea roots;

    @FXML
    GridPane gridPane;

    @FXML
    BarChart<Integer, Integer> eps3;

    public void calc(ActionEvent actionEvent) {

        Function f = new Function();
        Interval dy = f.getMinMaxY(new Interval(Parameters.I.a, Parameters.I.b));
        w = new Window(Parameters.I.a, Parameters.I.b, dy.a-0.5, dy.b+0.5, (int)canvas.getWidth(), (int)canvas.getHeight());
        f.draw(canvas.getGraphicsContext2D(), w, Parameters.I.a, Parameters.I.b);

        Newton newton = new Newton(f);

        final String austria = "Austria";
        final String brazil = "Brazil";
        final String france = "France";
        final String italy = "Italy";
        final String usa = "USA";

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data(austria, 25601.34));
        series1.getData().add(new XYChart.Data(brazil, 20148.82));
        series1.getData().add(new XYChart.Data(france, 10000));
        series1.getData().add(new XYChart.Data(italy, 35407.15));
        series1.getData().add(new XYChart.Data(usa, 12000));

        bc.getData().addAll(series1);

        gridPane.add(bc, 1, 0);


    }

    public void move(MouseEvent mouseEvent) {

        if (w != null) {

            Point2D p = w.screenToReal(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            xx.setText(String.format("%.4f", p.getX()));
            yy.setText(String.format("%.4f", p.getY()));

        }

    }
}
