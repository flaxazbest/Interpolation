package integral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VisualApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlFile = "../integration.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        primaryStage.setTitle("Integration");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
