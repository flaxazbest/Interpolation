package parabolic;

import addons.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class FormController {

    private Window w;
    private Function f;
    private GraphicsContext gc;
    private Point2D pp;

    @FXML
    private Canvas canvas;

    @FXML
    TextField coordinatesX;

    @FXML
    TextField coordinatesY;

    @FXML
    TableColumn<Analyse, String> xx;

    @FXML
    TableColumn<Analyse, String> fx;

    @FXML
    TableColumn<Analyse, String> phi;

    @FXML
    TableColumn<Analyse, String> diff;

    @FXML
    TableColumn<Analyse, String> error;

    @FXML
    TableView<Analyse> table;

    public void onDraw(ActionEvent actionEvent) {
        ParabolicSpline ps = new ParabolicSpline(Parameters.A, Parameters.B, Parameters.parts);
        ps.calcSplines(f);
        ps.draw(gc, w);

        LinkedList<Analyse> lList = new LinkedList<>();

        double h = 0.05;
        double a = Parameters.A;
        while (a < Parameters.B) {
            double z = f.getY(a);
            double phi = ps.getPhi(a);
            Analyse an = new Analyse(String.format("%.4f", a),String.format("%.4f", z), String.format("%.4f", phi), String.format("%.4f", z - phi), String.format("%.4f", Math.abs((z - phi)/z*100))+"%");
            table.getItems().add(an);
            a += h;
        }
    }

    public void initialize() {
        w = new Window(Parameters.A, Parameters.B, 0.0, 3.0, 800, 600);
        f = new Function();
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,800, 600);

        f.draw(gc, w, Parameters.A, Parameters.B);

        xx.setCellValueFactory(
                new PropertyValueFactory<Analyse,String>("x")
        );
        fx.setCellValueFactory(
                new PropertyValueFactory<Analyse,String>("f")
        );
        phi.setCellValueFactory(
                new PropertyValueFactory<Analyse,String>("phi")
        );
        diff.setCellValueFactory(
                new PropertyValueFactory<Analyse,String>("diff")
        );
        error.setCellValueFactory(
                new PropertyValueFactory<Analyse,String>("error")
        );
    }

    public void move(MouseEvent mouseEvent) {
        pp = w.screenToReal(mouseEvent.getX(), mouseEvent.getY());
                coordinatesX.setText(String.format("X: %.4f", pp.getX()));
                coordinatesY.setText(String.format("Y: %.4f", pp.getY()));
    }
}
