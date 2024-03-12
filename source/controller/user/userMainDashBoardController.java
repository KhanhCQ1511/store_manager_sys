package source.controller.user;

import source.controller.*;
import source.controller.user.pages.userHomeController;
import source.controller.user.pages.userOrdersController;
import source.controller.user.pages.userProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class handles the simple user dashboard interactions.
 */
public class userMainDashBoardController implements Initializable {
    public Button btnHome;
    public Button btnProducts;
    public Button btnOrders;
    public Button lblLogOut;
    public AnchorPane dashHead;
    @FXML
    private StackPane dashContent;
    @FXML
    private Label lblUsrName;


    /**
     * This method handles the Home button click.
     * It loads the home page and it's contents.
     */
    public void btnHomeOnClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = loadFxmlPage("/source/view/user/pages/home/home.fxml");
        userHomeController homeController = fxmlLoader.getController();
        homeController.getDashboardProdCount();
        homeController.getDashboardOrdersCount();
    }

    /**
     * This method handles the Orders button click.
     * It loads the Orders page and it's contents.
     */
    public void btnOrdersOnClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = loadFxmlPage("/source/view/user/pages/orders/orders.fxml");
        userOrdersController ordersController = fxmlLoader.getController();
        ordersController.listOrders();
    }

    /**
     * This method handles the Products button click.
     * It loads the Products page and it's contents.
     */
    public void btnProductsOnClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = loadFxmlPage("/source/view/user/pages/products/products.fxml");
        userProductsController userController = fxmlLoader.getController();
        userController.listProducts();
    }

    /**
     * This method handles the LogOut button click.
     * On click and confirmation it opens the login view and clears the user session instance.
     */
    public void btnLogOutOnClick(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure that you want to log out?");
        alert.setTitle("Log Out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            userSessionController.cleanUserSession();
            Stage dialogStage;
            new Stage();
            Node node = (Node) actionEvent.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/login.fxml")));
            dialogStage.setScene(scene);
            dialogStage.show();
        }
    }

    /**
     * This private helper method loads the view file.
     */
    private FXMLLoader loadFxmlPage(String view_path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource(view_path).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        dashContent.getChildren().clear();
        dashContent.getChildren().add(root);

        return fxmlLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUsrName.setText(userSessionController.getUserFullname());

        FXMLLoader fxmlLoader = loadFxmlPage("/source/view/user/pages/home/home.fxml");
        userHomeController homeController = fxmlLoader.getController();
        homeController.getDashboardProdCount();
        homeController.getDashboardOrdersCount();
    }
}