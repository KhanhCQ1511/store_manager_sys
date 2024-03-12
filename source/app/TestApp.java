package source.app;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import source.src.dataSource;

public class TestApp extends Application {
    /**
     * {@inheritDoc}
     * @param primaryStage      Accepts Stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/source/view/login.fxml"));
            // Parent root = FXMLLoader.load(getClass().getResource("/source/view/admin/pages/products/add_products.fxml"));
            primaryStage.setTitle("Store Management System");
            primaryStage.getIcons().add(new Image("/source/view/resource/img/fav.png"));
            primaryStage.setScene(new Scene(root, 1280, 800));
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * This method initializes the application and opens the connection to the database.
     * @throws Exception      If an input or exception occurred.
     */
    @Override
    public void init() throws Exception {
        super.init();
        if(!dataSource.getInstance().open()) {
            System.out.println("FATAL ERROR: Couldn't connect to database");
            Platform.exit();
        }
    }

    /**
     * {@inheritDoc}
     * This method stops the application and closes the connection to the database.
     * @throws Exception      If an input or exception occurred.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        dataSource.getInstance().close();
    }

    /**
     * {@inheritDoc}
     * The main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
