package source.controller.admin.pages.products;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import source.src.categories;
import source.src.dataSource;
import source.src.distributor;
import source.src.product;

public class editProductsController extends productsController {
    @FXML
    public Text viewProductsResponse;
    public TextField fieldEditProductsCode;
    public TextField fieldEditProductsName;
    public TextField fieldEditProductsSize;
    public TextField fieldEditProductsPrice;
    public TextField fieldEditProductsQuantity;
    public ComboBox<categories> fieldEditProductCategoriesId;
    public ComboBox<distributor> fieldEditProductDistributorId;
    public TextArea fieldEditProductsDescription;
    public Text viewProductsCode;

    @FXML
    private void initialize() {
        fieldEditProductCategoriesId.setItems(FXCollections.observableArrayList(dataSource.getInstance().getProductCategories(dataSource.ORDER_BY_ASC)));

        TextFormatter<Double> textFormatterDouble = formatDoubleField();
        TextFormatter<Integer> textFormatterInt = formatIntField();
        fieldEditProductsPrice.setTextFormatter(textFormatterDouble);
        fieldEditProductsQuantity.setTextFormatter(textFormatterInt);
    }

    /**
     * This private method handles the add product button functionality.
     * It validates user input fields and adds the values to the database.
     */
    @FXML
    private void btnEditProductOnAction() {
        categories category = fieldEditProductCategoriesId.getSelectionModel().getSelectedItem();
        int cat_id = 0;
        if (category != null) {
            cat_id = category.getCategories_id();
        }
        distributor distributor = fieldEditProductDistributorId.getSelectionModel().getSelectedItem();
        int dis_id = 0;
        if (distributor != null) {
            dis_id = distributor.getDistributor_id();
        }

        assert category != null;
        assert distributor != null;
        if (areProductsInputValid(fieldEditProductsCode.getText(), fieldEditProductsName.getText(), fieldEditProductsSize.getText(), fieldEditProductsPrice.getText(), fieldEditProductsQuantity.getText(), fieldEditProductsDescription.getText(), cat_id, dis_id)) {
            String productsCode = fieldEditProductsCode.getText();
            String productsName = fieldEditProductsName.getText();
            String productsSize = fieldEditProductsSize.getText();
            double productsPrice = Double.parseDouble(fieldEditProductsPrice.getText());
            int productsQuantity = Integer.parseInt(fieldEditProductsQuantity.getText());
            String productsDescription = fieldEditProductsDescription.getText();
            int productsCategoriesId = category.getCategories_id();
            int productsDistributorId = distributor.getDistributor_id();

            Task<Boolean> addProductTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    return dataSource.getInstance().updateOneProduct(productsCode, productsName, productsSize, productsPrice, productsQuantity, productsDescription, productsCategoriesId, productsDistributorId);
                }
            };

            addProductTask.setOnSucceeded(e -> {
                if (addProductTask.valueProperty().get()) {
                    viewProductsResponse.setVisible(true);
                    System.out.println("Product edited!");
                }
            });

            new Thread(addProductTask).start();
        }
    }

    /**
     * This method gets the data for one product from the database and binds the values to editing fields.
     */
    public void fillEditingProductFields(int product_id) {
        Task<ObservableList<product>> fillProductTask = new Task<ObservableList<product>>() {
            @Override
            protected ObservableList<product> call() {
                return FXCollections.observableArrayList(
                        dataSource.getInstance().getOneProduct(product_id));
            }
        };
        fillProductTask.setOnSucceeded(e -> {
            viewProductsCode.setText("Editing: " + fillProductTask.valueProperty().getValue().get(0).getProduct_code());
            fieldEditProductsCode.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_code());
            fieldEditProductsName.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_name());
            fieldEditProductsSize.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_size());
            fieldEditProductsPrice.setText(String.valueOf(fillProductTask.valueProperty().getValue().get(0).getProduct_price()));
            fieldEditProductsQuantity.setText(String.valueOf(fillProductTask.valueProperty().getValue().get(0).getProduct_quantity()));
            fieldEditProductsDescription.setText(fillProductTask.valueProperty().getValue().get(0).getProduct_description());

            categories category = new categories();
            category.setCategories_id(fillProductTask.valueProperty().getValue().get(0).getCategories_id());
            category.setCategories_name(fillProductTask.valueProperty().getValue().get(0).getCategories_name());
            fieldEditProductCategoriesId.getSelectionModel().select(category);

            distributor distributor =  new distributor();
            distributor.setDistributor_id(fillProductTask.valueProperty().getValue().get(0).getDistributor_id());
            distributor.setDistributor_name(fillProductTask.valueProperty().getValue().get(0).getDistributor_name());
            fieldEditProductDistributorId.getSelectionModel().select(distributor);
        });

        new Thread(fillProductTask).start();
    }
}
