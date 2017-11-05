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

import java.util.LinkedList;

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

        final int CHARTS = 5;
        final double[] eps = {1e-3, 1e-4, 1e-5, 1e-6, 1e-12};
        final String[] ser = {"0", "1", "2", "3"};
        final CategoryAxis[] xAxis = new CategoryAxis[CHARTS];
        for (int i=0;i<CHARTS;i++)
            xAxis[i] = new CategoryAxis();
        final NumberAxis[] yAxis = new NumberAxis[CHARTS];
        for (int i=0;i<CHARTS;i++) {
            yAxis[i] = new NumberAxis();
            yAxis[i].setLabel(String.valueOf(eps[i]));
        }
        final BarChart<String, Number>[] bc = new BarChart[CHARTS];
        for (int i=0;i<CHARTS;i++)
            bc[i] = new BarChart<String, Number>(xAxis[i], yAxis[i]);

        Function f = new Function();
        Interval dy = f.getMinMaxY(new Interval(Parameters.I.a, Parameters.I.b));
        w = new Window(Parameters.I.a, Parameters.I.b, dy.a-0.5, dy.b+0.5, (int)canvas.getWidth(), (int)canvas.getHeight());
        f.draw(canvas.getGraphicsContext2D(), w, Parameters.I.a, Parameters.I.b);

        Newton newton = new Newton(f);
        int rootsCount = newton.getRootsCount();

        for (int p = 0; p < CHARTS; p++) {

            XYChart.Series[][] series = new XYChart.Series[CHARTS][rootsCount];
            for (int k = 0; k < rootsCount; k++) {
                series[p][k] = new XYChart.Series();
            }
            //xAxis.setLabel("First approximation");
            for (int j = 0; j < 4; j++) {
                LinkedList<RootEquation> llre = newton.solve(j, eps[p]);
                for (int k = 0; k < llre.size(); k++) {
                    series[p][k].getData().add(new XYChart.Data(ser[j], llre.get(k).itration));
                }
            }
            bc[p].getData().addAll(series[p]);
            gridPane.add(bc[p], (p+1)%2, (p+1)/2);
        }

        LinkedList<RootEquation> list = newton.solve(1, 1e-6);
        StringBuilder sb = new StringBuilder();
        for (int p=0; p<list.size(); p++) {
            sb.append("[").append(String.format("%.1f", newton.getInterval(p).a)).append(";").
                           append(String.format("%.1f", newton.getInterval(p).b)).append("]\n\tx = ").
                           append(String.format("%.5f",list.get(p).x)).append("\n");
        }
        roots.setText(sb.toString());

    }

    public void move(MouseEvent mouseEvent) {

        if (w != null) {

            Point2D p = w.screenToReal(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
            xx.setText(String.format("%.4f", p.getX()));
            yy.setText(String.format("%.4f", p.getY()));

        }

    }
}
