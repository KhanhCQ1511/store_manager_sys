package source.src;

public class product {
    private int product_id;
    private String product_code;
    private String product_name;
    private String product_size;
    private String product_price;
    private String product_quantity;
    private String product_description;
    private int categories_id;
    private int distributor_id;
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public String getProduct_code() {
        return product_code;
    }
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getProduct_size() {
        return product_size;
    }
    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }
    public String getProduct_price() {
        return product_price;
    }
    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
    public String getProduct_quantity() {
        return product_quantity;
    }
    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }
    public String getProduct_description() {
        return product_description;
    }
    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }
    public int getCategories_id() {
        return categories_id;
    }
    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }
    public int getDistributor_id() {
        return distributor_id;
    }
    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
    }
    @Override
    public String toString() {
        return "product [product_id=" + product_id + ", product_code=" + product_code + ", product_name=" + product_name
                + ", product_size=" + product_size + ", product_price=" + product_price + ", product_quantity="
                + product_quantity + ", product_description=" + product_description + "]";
    }
    
}
