package source.controller.user.pages;

import source.controller.userSessionController;
import source.src.dataSource;
import source.src.orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * This class handles the users orders page.
 */
public class userOrdersController {
    @SuppressWarnings("rawtypes")
    public TableView tableOrdersPage;

    /**
     * This method lists all the orders to the view table.
     * It starts a new Task, gets all the products from the database then bind the results to the view.
     */
    @SuppressWarnings("unchecked")
    @FXML
    public void listOrders() {

        Task<ObservableList<orders>> getAllOrdersTask = new Task<ObservableList<orders>>() {
            @Override
            protected ObservableList<orders> call() {
                return FXCollections.observableArrayList(dataSource.getInstance().getAllOrders(dataSource.ORDER_BY_NONE));
            }
        };

        tableOrdersPage.itemsProperty().bind(getAllOrdersTask.valueProperty());
        new Thread(getAllOrdersTask).start();

    }

    public void btnOrdersSearchOnAction(ActionEvent actionEvent) {
        // TODO
        //  Add orders search functionality.
        System.out.println("TODO: Add orders search functionality.");
    }
}