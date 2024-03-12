package source.controller.admin.pages.customers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import source.src.customer;
import source.src.dataSource;

public class viewCustomersController extends customersController {
    @FXML
    private TextField fieldViewCustomersFullname;
    @FXML
    private TextField fieldViewCustomersPhone;
    @FXML
    private TextField fieldViewCustomersEmail;
    @FXML
    private TextArea fieldViewCustomersDescription;

    @FXML
    private void initialize(){

    }

    /**
     * The method gets customers from the database and binds them to the view.
     */
    @FXML 
    public void fillViewCustomer(int customer_id) {
        Task<ObservableList<customer>> fillProductTask = new Task<ObservableList<customer>>() {
            @Override
            protected ObservableList<customer> call() {
                return FXCollections.observableArrayList(
                        dataSource.getInstance().getOneCustomer(customer_id));
            }
        };
        fillProductTask.setOnSucceeded(e -> {
            // viewProductsCode.setText("Viewing: " + fillProductTask.valueProperty().getValue().get(0).getProduct_code());
            fieldViewCustomersFullname.setText(fillProductTask.valueProperty().getValue().get(0).getCustomer_fullname());
            fieldViewCustomersPhone.setText(fillProductTask.valueProperty().getValue().get(0).getCustomer_phone());
            fieldViewCustomersEmail.setText(fillProductTask.valueProperty().getValue().get(0).getCustomer_email());
            fieldViewCustomersDescription.setText(fillProductTask.valueProperty().getValue().get(0).getCustomer_description());
        });

        new Thread(fillProductTask).start();
    }
}
