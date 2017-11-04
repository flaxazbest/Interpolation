import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) throws Exception {
        String fxmlFile = "form.fxml";

        FXMLLoader loader = new FXMLLoader();

        Parent root = (Parent) loader.load(getClass().getResource(fxmlFile));
        primaryStage.setTitle("Draw function");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
