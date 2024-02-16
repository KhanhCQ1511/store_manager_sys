package source.controller.admin.pages;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import source.src.customer;
import source.src.dataSource;

public class customersController {
    @FXML
    public TextField fieldCustomersSearch;
    @FXML
    private StackPane customersContent;
    @FXML
    private TableView<customer> tableCustomersPage;

    /*
     * This method lists all the customers to the view table.
     * It starts a new Task, gets all the simple users from the database then bind the results to the view.
     */
    @FXML
    public void listCustomers() {
         Task<ObservableList<customer>> getAllCustomersTask = new Task<ObservableList<customer>>() {
            @Override
            protected ObservableList<customer> call() {
                return FXCollections.observableArrayList(dataSource.getInstance().getAllCustomers(dataSource.ORDER_BY_NONE));
            }
        };

        tableCustomersPage.itemsProperty().bind(getAllCustomersTask.valueProperty());
        addActionButtonsToTable();
        new Thread(getAllCustomersTask).start();
    }

    /**
     * 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnEdit = new TableColumn("Actions");
        Callback<TableColumn<customer, Void>, TableCell<customer, Void>> cellFactory = new Callback<TableColumn<customer, Void>, TableCell<customer, Void>>() {

            @Override
            public TableCell<customer, Void> call(TableColumn<customer, Void> param) {
                return new TableCell<customer, Void>(){
                    private final Button viewButton = new Button("View");
                    {
                        viewButton.getStyleClass().add("button");
                        viewButton.getStyleClass().add("xs");
                        viewButton.getStyleClass().add("info");
                        viewButton.setOnAction((ActionEvent event) -> {
                            customer customerData = getTableView().getItems().get(getIndex());
                            btnViewCustomer((int) customerData.getCustomer_id());
                            System.out.println("View Customer");
                            System.out.println("customer id: "+ customerData.getCustomer_id());
                            System.out.println("customer name: "+ customerData.getCustomer_fullname());
                        });
                    }

                    private final Button editButton = new Button("Edit");
                    {
                        editButton.getStyleClass().add("button");
                        editButton.getStyleClass().add("xs");
                        editButton.getStyleClass().add("info");
                        editButton.setOnAction((ActionEvent event)->{
                            customer customerData = getTableView().getItems().get(getIndex());
                            btnEditCustomer((int) customerData.getCustomer_id());
                            System.out.println("Edit Customer");
                            System.out.println("customer id: "+ customerData.getCustomer_id());
                            System.out.println("customer name: "+ customerData.getCustomer_fullname());
                        });
                    }

                    private final Button deleteButton = new Button("Delete");
                    {
                        deleteButton.getStyleClass().add("button");
                        deleteButton.getStyleClass().add("xs");
                        deleteButton.getStyleClass().add("info");
                        deleteButton.setOnAction((ActionEvent event)->{
                            customer customerData = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Are you sure that you want to delete " + customerData.getCustomer_fullname() + " ?");
                            alert.setTitle("Delete " + customerData.getCustomer_fullname() + " ?");
                            Optional<ButtonType> deleteConfirmation = alert.showAndWait();

                            if (deleteConfirmation.get() == ButtonType.OK) {
                                System.out.println("Delete Customer");
                                System.out.println("customer id: " + customerData.getCustomer_id());
                                System.out.println("customer name: " + customerData.getCustomer_fullname());
                                if (dataSource.getInstance().deleteSingleCustomer(customerData.getCustomer_id())) {
                                    getTableView().getItems().remove(getIndex());
                                }
                            }
                        });
                    }

                    private final HBox buttonsPane = new HBox();
                    {
                        buttonsPane.setSpacing(10);
                        buttonsPane.getChildren().add(viewButton);
                        buttonsPane.getChildren().add(editButton);
                        buttonsPane.getChildren().add(deleteButton);
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
        colBtnEdit.setCellFactory(cellFactory);
        tableCustomersPage.getColumns().add(colBtnEdit);
    }

    /*
     * This method loads single customer view
     */
    @FXML
    private void btnViewCustomer(int customer_Id) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/source/view/admin/pages/customers/view_customers.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        customersContent.getChildren().clear();
        customersContent.getChildren().add(root);

        fillEditCustomer(customer_Id);
    }

    /*
     * This method load the edit customer view page
     */
    @FXML
    private void btnEditCustomer(int customer_Id) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/source/view/admin/pages/customers/edit_customers.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        customersContent.getChildren().clear();
        customersContent.getChildren().add(root);

        fillEditCustomer(customer_Id);
    }

    /**
     * This private method handles the customers search functionality.
     * It creates a new task, gets the search results from the database and binds them to the view table.
     */
    public void btnCustomersSearchOnAction() {
        Task<ObservableList<customer>> searchCustomersTask = new Task<ObservableList<customer>>() {
            @Override
            protected ObservableList<customer> call() {
                return FXCollections.observableArrayList(
                        dataSource.getInstance().searchCustomers(fieldCustomersSearch.getText().toLowerCase(), dataSource.ORDER_BY_NONE));
            }
        };
        tableCustomersPage.itemsProperty().bind(searchCustomersTask.valueProperty());

        new Thread(searchCustomersTask).start();
    }

    /*
     * This method gets single customer from database and bind them to view
     */
    @FXML
    private void fillEditCustomer(int customer_Id){
        Task<ObservableList<customer>> fillCustomerTask = new Task<ObservableList<customer>>() {
            @Override
            protected ObservableList<customer> call() throws Exception {
                return FXCollections.observableArrayList(dataSource.getInstance().getOneCustomer(customer_Id));
            }
        };
        fillCustomerTask.setOnSucceeded(e ->{
            // fieldAddCustomerNameEdit.setText("testCustomer");
            System.out.println("pr name:" + fillCustomerTask.valueProperty().getValue().get(0).getCustomer_fullname());
            //  fieldAddCustomerName.setText("test");
            //  fieldAddCustomerName.setText(fillCustomerTask.valueProperty().getValue().get(0).getName());
        });
        new Thread(fillCustomerTask).start();
    }
}
