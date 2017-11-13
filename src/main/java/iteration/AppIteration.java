package iteration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppIteration extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlFile = "../iteration.fxml";

        FXMLLoader loader = new FXMLLoader();

        Parent root = (Parent) loader.load(getClass().getResource(fxmlFile));
        primaryStage.setTitle("Iterator");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
