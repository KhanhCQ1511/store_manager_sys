package source.controller.admin.pages.products;

import java.io.IOException;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import source.src.dataSource;
import source.src.product;
import source.util.helperMethods;

public class productsController {
    @FXML
    public TextField fieldProductsSearch;
    @FXML
    public Text viewProductsResponse;
    @FXML
    public GridPane formEditProductsView;
    @FXML
    private StackPane productsContent;
    @FXML
    private TableView<product> tableProductsPage;

    /*
     * This method get product to the view table
     * In start new Task, get all products from the database and bind results to view
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

    /*
     * This private method add action buttons to the table rows
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @FXML
    private void addActionButtonsToTable() {
        TableColumn colBtnEdit = new TableColumn("Actions");
        
        Callback<TableColumn<product, Void>, TableCell<product, Void>> cellFactory = new Callback<TableColumn<product, Void>, TableCell<product, Void>>() {

            @Override
            public TableCell<product, Void> call(TableColumn<product, Void> param) {
                return new TableCell<product, Void>(){
                    private final Button viewButton = new Button("View");
                    {
                        viewButton.getStyleClass().add("button");
                        viewButton.getStyleClass().add("xs");
                        viewButton.getStyleClass().add("info");
                        viewButton.setOnAction((ActionEvent event) -> {
                            product productData = getTableView().getItems().get(getIndex());
                            btnViewProduct(productData.getProduct_id());
                        });
                    }

                    private final Button editButton = new Button("Edit");

                    {
                        editButton.getStyleClass().add("button");
                        editButton.getStyleClass().add("xs");
                        editButton.getStyleClass().add("primary");
                        editButton.setOnAction((ActionEvent event) -> {
                            product productData = getTableView().getItems().get(getIndex());
                            btnEditProduct(productData.getProduct_id());
                        });
                    }

                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.getStyleClass().add("button");
                        deleteButton.getStyleClass().add("xs");
                        deleteButton.getStyleClass().add("danger");
                        deleteButton.setOnAction((ActionEvent event) -> {
                            product productData = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Are you sure that you want to delete " + productData.getProduct_name() + " ?");
                            alert.setTitle("Delete " + productData.getProduct_name() + " ?");
                            Optional<ButtonType> deleteConfirmation = alert.showAndWait();

                            if (deleteConfirmation.get() == ButtonType.OK) {
                                System.out.println("Delete Product");
                                System.out.println("product id: " + productData.getProduct_id());
                                System.out.println("product name: " + productData.getProduct_name());
                                if (dataSource.getInstance().deleteSingleProduct(productData.getProduct_id())) {
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

        tableProductsPage.getColumns().add(colBtnEdit);
    }

    /**
     * This private method loads the add product view page.
     */
    @FXML
    private void btnAddProductOnClick() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/sources/view/admin/pages/products/add_products.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        productsContent.getChildren().clear();
        productsContent.getChildren().add(root);

    }

    /**
     * This private method loads the edit product view page.

     */
    @FXML
    private void btnEditProduct(int product_id) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/sources/view/admin/pages/products/edit_products.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane root = fxmlLoader.getRoot();
        productsContent.getChildren().clear();
        productsContent.getChildren().add(root);

        editProductsController controller = fxmlLoader.getController();
        controller.fillEditingProductFields(product_id);

    }

    /**
     * This private method loads single add product view page.
     */
    @FXML
    private void btnViewProduct(int product_id){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/sources/view/admin/pages/products/view_products.fxml").openStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        productsContent.getChildren().clear();
        productsContent.getChildren().add(root);

        viewProductsController controller = fxmlLoader.getController();
        controller.fillViewingProductFields(product_id);
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

    /*
     * This method check user input field for product
     */
    protected boolean areProductsInputValid(String fieldAddProductsCode, String fieldAddProductsName, String fieldAddProductsSize, String fieldAddProductsPrice, String fieldAddProductsQuantity, String fieldAddProductsDescription, int productsCategoriesId, int productsDistributorId){
        String errorMessage = "";
        if(productsCategoriesId == 0){
            errorMessage += "Not valid categories id!\n";
        } 
        if (productsDistributorId == 0) {
            errorMessage += "Not valid distributor id!\n";
        }
        if (fieldAddProductsCode == null | fieldAddProductsCode.length() != 12) {
            errorMessage += "Not valid product code!\n";
        }
        if (fieldAddProductsName == null || fieldAddProductsName.length() < 5) {
            errorMessage += "Not valid product name!\n";
        }
        if (fieldAddProductsSize == null){
            errorMessage += "Not valid product size!\n";
        }
        if (helperMethods.validateProductPrice(fieldAddProductsPrice)) {
            errorMessage += "Not vaild product price!\n";
        } 
        if (helperMethods.validateProductQuantity(fieldAddProductsQuantity)) {
            errorMessage += "Not vaild product quantity!\n";
        }
        if (errorMessage.length()==0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid field");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    /*
     * This method returns the TextFormatter for validating as double text input fields.
     */
    public static TextFormatter<Double> formatDoubleField(){
        //  Pattern validEditingState = Pattern.compile("^[0-9]+(|\\.)[0-9]+$");
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };
        StringConverter<Double> converter = new StringConverter<Double>(){

            @Override
            public Double fromString(String string) {
                if (string.isEmpty() || "-".equals(string) || ".".equals(string) || "-.".equals(string)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(string);
                }
            }

            @Override
            public String toString(Double object) {
                return object.toString();
            }
        };
        return new TextFormatter<>(converter, 0.0, filter);
    }

    /*
     * This method returns the TextFormatter for validating as integer text input fields.
     */
    public static TextFormatter<Integer> formatIntField(){
        Pattern validEditingState = Pattern.compile("^[0-9]+$");
        UnaryOperator<TextFormatter.Change> filter = c ->{
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };
        StringConverter<Integer> converter = new StringConverter<Integer>(){

            @Override
            public Integer fromString(String string) {
                if (string.isEmpty() || "-".equals(string) || ".".equals(string) || "-.".equals(string)) {
                    return 0 ;
                } else {
                    return Integer.valueOf(string);
                }
            }

            @Override
            public String toString(Integer object) {
              return object.toString();
            }
        };
        return new TextFormatter<>(converter, 0, filter);
    }
}
