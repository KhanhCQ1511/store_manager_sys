package source.controller.user.pages;

import source.controller.userSessionController;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import source.src.dataSource;

/**
 * This class handles the users home page.
 */
public class userHomeController {

    public Label productsCount;
    public Label ordersCount;

    /**
     * This method gets the products count for the user dashboard and sets it to the productsCount label.
     */
    public void getDashboardProdCount() {
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
     * This method gets the orders count for the user dashboard and sets it to the ordersCount label.
     */
    public void getDashboardOrdersCount() {
        Task<Integer> getDashOrderCount = new Task<Integer>() {
            @Override
            protected Integer call() {
                return dataSource.getInstance().countUserOrders(userSessionController.getUserId());
            }
        };

        getDashOrderCount.setOnSucceeded(e -> {
            ordersCount.setText(String.valueOf(getDashOrderCount.valueProperty().getValue()));
        });

        new Thread(getDashOrderCount).start();
    }

}