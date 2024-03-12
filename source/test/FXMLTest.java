package source.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/source/view/login.fxml"));

            // Create a Scene with the loaded FXML content
            Scene scene = new Scene(root, 1280, 800);

            // Set the title of the Stage
            primaryStage.setTitle("FXML Test");

            // Set the Scene for the Stage
            primaryStage.setScene(scene);

            // Show the Stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
