package source.controller.user.pages;

import source.util.helperMethods;
import source.controller.customerSessionController;
import source.controller.userSessionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import source.src.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * This class handles the users products page.
 */
public class userProductsController {


    @FXML
    public TextField fieldProductsSearch;

    @FXML
    private TableView<product> tableProductsPage;

    /**
     * This method lists all the product to the view table.
     * It starts a new Task, gets all the products from the database then bind the results to the view.
     */
    @FXML
    public void listProducts() {

        Task<ObservableList<product>> getAllProductsTask = new Task<ObservableList<product>>() {
            @Override
            protected ObservableList<product> call() {
                return FXCollections.observableArrayList(dataSource.getInstance().getAllProducts(dataSource.ORDER_BY_NONE));
            }
        };

        tableProductsPage.itemsProperty().bind(getAllProductsTask.valueProperty());
        addActionButtonsToTable();
        new Thread(getAllProductsTask).start();

    }

    /**
     * This private method adds the action buttons to the table rows.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnBuy = new TableColumn("Actions");

        Callback<TableColumn<product, Void>, TableCell<product, Void>> cellFactory = new Callback<TableColumn<product, Void>, TableCell<product, Void>>() {
            @Override
            public TableCell<product, Void> call(final TableColumn<product, Void> param) {
                return new TableCell<product, Void>() {

                    private final Button buyButton = new Button("Buy");

                    {
                        buyButton.getStyleClass().add("button");
                        buyButton.getStyleClass().add("xs");
                        buyButton.getStyleClass().add("success");
                        buyButton.setOnAction((ActionEvent event) -> {
                            product productData = getTableView().getItems().get(getIndex());
                            if (Integer.parseInt(productData.getProduct_quantity()) <= 0) {
                                helperMethods.alertBox("You can't buy this product because there is no stock!", "", "No Stock");
                            } else {
                                btnBuyProduct(productData.getProduct_id(), productData.getProduct_name());
                            }
                        });
                    }

                    private final HBox buttonsPane = new HBox();

                    {
                        buttonsPane.setSpacing(10);
                        buttonsPane.getChildren().add(buyButton);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonsPane);
                        }
                    }
                };
            }
        };

        colBtnBuy.setCellFactory(cellFactory);

        tableProductsPage.getColumns().add(colBtnBuy);

    }

    /**
     * This private method handles the products search functionality.
     * It creates a new task, gets the search results from the database and binds them to the view table.
     */
    @FXML
    private void btnProductsSearchOnAction() {
        Task<ObservableList<product>> searchProductsTask = new Task<ObservableList<product>>() {
            @Override
            protected ObservableList<product> call() {
                return FXCollections.observableArrayList(
                        dataSource.getInstance().searchProducts(fieldProductsSearch.getText().toLowerCase(), dataSource.ORDER_BY_NONE));
            }
        };
        tableProductsPage.itemsProperty().bind(searchProductsTask.valueProperty());

        new Thread(searchProductsTask).start();
    }


    /**
     * This private method handles the buy product functionality.
     */
    @FXML
    private void btnBuyProduct(int product_id, String product_name) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to buy " + product_name);
        alert.setTitle("Buy product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                int user_id = userSessionController.getUserId();
                int customer_id = customerSessionController.getCustomerId() ;
                String timestam = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                Timestamp order_date = Timestamp.valueOf(timestam);
                String order_status = "Paid";
                System.out.println(customer_id);
                Task<Boolean> addProductTask = new Task<Boolean>() {
                    @Override
                    protected Boolean call() {
                        return dataSource.getInstance().insertNewOrder(product_id, user_id, 1, order_date, order_status);
                    }
                };

                addProductTask.setOnSucceeded(e -> {
                    if (addProductTask.valueProperty().get()) {
                        dataSource.getInstance().decreaseStock(product_id);
                        System.out.println("Order placed!");
                    }
                });

                new Thread(addProductTask).start();
                System.out.println(product_id);
            }
        }

    }

}