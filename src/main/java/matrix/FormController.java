package matrix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;

public class FormController {

    @FXML
    Canvas canvas;

    @FXML
    TextArea area;

    private Matrix m;

    public void solve(ActionEvent actionEvent) {
        m = new Matrix();
        m.Nekrasov();
        m.drawStatistic(canvas.getGraphicsContext2D());
        m.printStatistic(area);
        //m.printR();
    }

    public void initialize() {




    }
}
