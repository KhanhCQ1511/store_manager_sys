package source.controller.admin.pages.products;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import source.src.categories;
import source.src.dataSource;
import source.src.distributor;
import source.src.product;

public class viewProductsController extends productsController{
    @FXML
    public TextField fieldViewProductsCode;
    public TextField fieldViewProductsName;
    public TextField fieldViewProductsSize;
    public TextField fieldViewProductsPrice;
    public TextField fieldViewProductsQuantity;
    public ComboBox<categories> fieldViewProductCategoriesId;
    public ComboBox<distributor> fieldViewProductDistributorId;
    public TextArea fieldViewProductsDescription;
    public Text viewProductsCode;

     @FXML
    private void initialize() {
        fieldViewProductCategoriesId.setItems(FXCollections.observableArrayList(dataSource.getInstance().getProductCategories(dataSource.ORDER_BY_ASC)));
    }

    /**
     * This method gets the data for one product from the database and binds the values to viewing fields.
     */
    public void fillViewingProductFields(int product_id) {
        Task<ObservableList<product>> fillProductTask = new Task<ObservableList<product>>() {
            @Override
            protected ObservableList<product> call() {
                return FXCollections.observableArrayList(
                        dataSource.getInstance().getOneProduct(product_id));
            }
        };
        fillProductTask.setOnSucceeded(e -> {
            viewProductsCode.setText("Viewing: " + fillProductTask.valueProperty().getValue().get(0).getProduct_code());
            fieldViewProductsCode.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_code());
            fieldViewProductsName.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_name());
            fieldViewProductsSize.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_size());
            fieldViewProductsPrice.setText(String.valueOf(fillProductTask.valueProperty().getValue().get(0).getProduct_price()));
            fieldViewProductsQuantity.setText(String.valueOf(fillProductTask.valueProperty().getValue().get(0).getProduct_quantity()));
            fieldViewProductsDescription.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_description());

            categories category = new categories();
            category.setCategories_id(fillProductTask.valueProperty().getValue().get(0).getCategories_id());
            category.setCategories_name(fillProductTask.valueProperty().getValue().get(0).getCategories_name());
            fieldViewProductCategoriesId.getSelectionModel().select(category);

            distributor distributor =  new distributor();
            distributor.setDistributor_id(fillProductTask.valueProperty().getValue().get(0).getDistributor_id());
            distributor.setDistributor_name(fillProductTask.valueProperty().getValue().get(0).getDistributor_name());
            fieldViewProductDistributorId.getSelectionModel().select(distributor);
        });

        new Thread(fillProductTask).start();
    }
}
