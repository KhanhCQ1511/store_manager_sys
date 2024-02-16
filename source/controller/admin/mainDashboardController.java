package source.controller.admin;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
import source.controller.userSessionController;
import source.controller.admin.pages.customersController;
import source.controller.admin.pages.homeController;
import source.controller.admin.pages.ordersController;
import source.controller.admin.pages.products.productsController;

public class mainDashboardController implements Initializable {
    @FXML
    public Button btnHome;
    @FXML
    public Button btnProduct;
    @FXML
    public Button btnCustomers;
    @FXML
    public Button btnOrders;
    @FXML
    public Button btnSettings;
    @FXML
    public Button btnLogOut;
    @FXML
    public AnchorPane dashHead;
    @FXML
    private StackPane dashContent;
    @FXML
    private Label lblUserName;
    
    /*
     * This method handles the Home button click.
     * It loads the home page and it's contents.
     */
    public void btnHomeOnClick(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = loadFXMLPage("/source/view/admin/pages/home/home.fxml");
        homeController homeController = fxmlLoader.getController();
        homeController.getDashboardProductCount();
        homeController.getDashboardCostCount();
    }

    /*
     * This method handles the Product button click.
     * It loads the product page and it's contents.
     */
    public void btnProductOnClick(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = loadFXMLPage("/source/view/admin/pages/products/products.fxml");
        productsController controller = fxmlLoader.getController();
        controller.listProducts();
    }

    /*
     * This method handles the Order button click.
     * It loads the order page and it's contents.
     */
    public void btnOrdersOnClick(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = loadFXMLPage("/source/view/admin/pages/orders/orders.fxml");
        ordersController orders = fxmlLoader.getController();
        orders.listOrders();
    }

    /*
     * This method handles the Customer button click.
     * It loads the customer page and it's contents.
     */
    public void btnCustomersOnClick(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = loadFXMLPage("/source/view/admin/pages/customers/customers.fxml");
        customersController controller = fxmlLoader.getController();
        controller.listCustomers();
    }

    /*
     * This method handles the Settings
     */
    public void btnSettingsOnClick(ActionEvent actionEvent){
        @SuppressWarnings("unused")
        FXMLLoader fxmlLoader = loadFXMLPage("/source/view/admin/pages/settings/settings.fxml");
    }

    /*
     * This method handles the Log out on Click
     * After method is called becomes to login pages
     */
    public void btnLogOutOnClick(ActionEvent actionEvent) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure that you want to log out?");
        alert.setTitle("Log Out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            userSessionController.cleanUserSession();
            Stage dialogStage;
            Node node = (Node) actionEvent.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/login.fxml")));
            dialogStage.setScene(scene);
            dialogStage.show();
        }
    }

    private FXMLLoader loadFXMLPage(String fxml_url){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource(fxml_url).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        dashContent.getChildren().clear();
        dashContent.getChildren().add(root);

        return fxmlLoader;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUserName.setText(userSessionController.getUserFullname());

        FXMLLoader fxmlLoader = loadFXMLPage("/source/view/admin/pages/home/home.fxml");
        homeController homeController = fxmlLoader.getController();
        homeController.getDashboardProductCount();
        homeController.getDashboardCostCount();
    }
    
}
