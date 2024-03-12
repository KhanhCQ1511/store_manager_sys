package source.controller.admin.pages.products;

import javafx.collections.FXCollections;
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

public class addProductsController extends productsController {
    @FXML
    public ComboBox<categories> fieldAddProductCategoriesId;
    @FXML
    public ComboBox<distributor> fieldAddProductDistributorId;
    public TextField fieldAddProductsCode;
    public TextField fieldAddProductsName;
    public TextField fieldAddProductsSize;
    public TextField fieldAddProductsPrice;
    public TextField fieldAddProductsQuantity;
    public TextArea fieldAddProductsDescription;
    public Text viewProductsResponse;

    @FXML
    private void initialize(){
        if (fieldAddProductCategoriesId != null) {
            fieldAddProductCategoriesId.setItems(FXCollections.observableArrayList(dataSource.getInstance().getProductCategories(dataSource.ORDER_BY_ASC)));
        }
        if (fieldAddProductDistributorId != null) {
            fieldAddProductDistributorId.setItems(FXCollections.observableArrayList(dataSource.getInstance().getProductDistributor(dataSource.ORDER_BY_ASC)));
        }
        TextFormatter<Double> textFormatterDouble = formatDoubleField();
        TextFormatter<Integer> textFormatterInteger = formatIntField();
        fieldAddProductsPrice.setTextFormatter(textFormatterDouble);
        fieldAddProductsQuantity.setTextFormatter(textFormatterInteger);
    }
    

    /*
     * This private method handles the add product button functionality.
     * It validates user input fields and adds the values to the database. done
     */
    @FXML
    public void btnAddProductsOnAction(){
        categories category = fieldAddProductCategoriesId.getSelectionModel().getSelectedItem();
        distributor distributor = fieldAddProductDistributorId.getSelectionModel().getSelectedItem();
        int categories_id = 0;
        if (category != null) {
            categories_id = category.getCategories_id();
        }
        int distributor_id = 0;
        if (distributor != null) {
            distributor_id = distributor.getDistributor_id();
        }
        assert category != null;
        assert distributor != null;
        if (areProductsInputValid(fieldAddProductsCode.getText(), fieldAddProductsName.getText(), fieldAddProductsSize.getText(), fieldAddProductsPrice.getText(), fieldAddProductsQuantity.getText(), fieldAddProductsDescription.getText(), categories_id, distributor_id)) {
            String productsCode = fieldAddProductsCode.getText();
            String productsName = fieldAddProductsName.getText();
            String productsSize = fieldAddProductsSize.getText();
            double productsPrice = Double.parseDouble(fieldAddProductsPrice.getText());
            int productsQuantity = Integer.parseInt(fieldAddProductsQuantity.getText());
            String productsDescription = fieldAddProductsDescription.getText();
            int productsCategoryId = category.getCategories_id();
            int productsDistributorId = distributor.getDistributor_id();
            System.out.println(productsCode+productsName+productsSize+productsPrice+productsQuantity+productsDescription+productsCategoryId+productsDistributorId);

            Task<Boolean> addProductsTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return dataSource.getInstance().insertNewProduct(productsCode, productsName, productsSize, productsPrice, productsQuantity , productsDescription, productsCategoryId ,productsDistributorId);
                }
            };
            addProductsTask.setOnSucceeded(e ->{
                if (addProductsTask.valueProperty().get()) {
                    viewProductsResponse.setVisible(true);
                    System.out.println("Added new product successfully!");
                }
            });
            new Thread(addProductsTask).start();
        }
    }
}
