package source.src;

import java.sql.Timestamp;

public class orders {
    private int orders_id;
    private Timestamp orders_date;
    private String orders_pay_status;
    private int product_id;
    private int users_id;
    private int customer_id;  
    private String product_name;
    private String customer_name;
    private String users_name; 
    public int getOrders_id() {
        return orders_id;
    }
    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }
    public Timestamp getOrders_date() {
        return orders_date;
    }
    public void setOrders_date(Timestamp orders_date) {
        this.orders_date = orders_date;
    }
    public String getOrders_pay_status() {
        return orders_pay_status;
    }
    public void setOrders_pay_status(String orders_pay_status) {
        this.orders_pay_status = orders_pay_status;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public int getUsers_id() {
        return users_id;
    }
    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public String getUsers_name() {
        return users_name;
    }
    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }
    @Override
    public String toString() {
        return "oders [orders_id=" + orders_id + ", orders_date=" + orders_date + ", orders_pay_status="
                + orders_pay_status + "]";
    }
    
}
