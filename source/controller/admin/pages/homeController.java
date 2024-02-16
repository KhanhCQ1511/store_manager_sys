package source.controller.admin.pages;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import source.src.dataSource;

public class homeController {
    @FXML
    public Label productsCount;
    @FXML
    public Label customersCount;

    /*
     *This method gets the products count for the admin dashboard and sets it to the productsCount label.
     */
    public void getDashboardProductCount(){
        Task<Integer> getDashProdCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return dataSource.getInstance().countAllProducts();
            }
        };

        getDashProdCount.setOnSucceeded(e -> {
            productsCount.setText(String.valueOf(getDashProdCount.valueProperty().getValue()));
        });

        new Thread(getDashProdCount).start();
    }
    /**
     * This method gets the customers count for the admin dashboard and sets it to the customersCount label.
     */
    public void getDashboardCostCount() {
        Task<Integer> getDashCostCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return dataSource.getInstance().countAllCustomers();
            }
        };

        getDashCostCount.setOnSucceeded(e -> {
            customersCount.setText(String.valueOf(getDashCostCount.valueProperty().getValue()));
        });

        new Thread(getDashCostCount).start();
    }
}
