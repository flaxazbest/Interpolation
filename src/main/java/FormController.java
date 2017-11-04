import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;

public class FormController {

    private Window w;
    private Function f;
    private GraphicsContext gc;
    private Point2D pp;
    private ObservableList<Analyse> list;

    @FXML
    private Canvas canvas;

    @FXML
    private Button draw;

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
        ParabolicSpline ps = new ParabolicSpline(1.0, 4.0, 3);
        ps.calcSplines(f);
        ps.draw(gc, w);

        LinkedList<Analyse> lList = new LinkedList<>();

        double h = 0.05;
        double a = 1.0;
        while (a < 4.0) {
            double z = f.getY(a);
            double phi = ps.getPhi(a);
            Analyse an = new Analyse(String.format("%.4f", a),String.format("%.4f", z), String.format("%.4f", phi), String.format("%.4f", z - phi), String.format("%.4f", Math.abs((z - phi)/z*100))+"%");
            table.getItems().add(an);
            a += h;
        }
    }

    public void initialize() {
        w = new Window(1.0, 4.0, 0.0, 3.0, 800, 600);
        f = new Function();
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,800, 600);

        f.draw(gc, w, 1.0, 4.0);

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
