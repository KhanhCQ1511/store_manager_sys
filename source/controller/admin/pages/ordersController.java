package source.controller.admin.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import source.src.dataSource;
import source.src.orders;

public class ordersController {
    @FXML
    private TableView<orders> tableOrdersPage;

    public void listOrders(){
        Task<ObservableList<orders>> getAllOrdersTask = new Task<ObservableList<orders>>() {

            @Override
            protected ObservableList<orders> call() throws Exception {
               return FXCollections.observableArrayList(dataSource.getInstance().getAllOrders(dataSource.ORDER_BY_NONE));
            }
        };
        tableOrdersPage.itemsProperty().bind(getAllOrdersTask.valueProperty());
        new Thread(getAllOrdersTask).start();
    }

    public void btnOrdersSearchOnAction(ActionEvent event) {
        System.out.println("TODO: Add orders search functionality.");
    }
}
