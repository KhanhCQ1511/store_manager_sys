package source.src;

import java.sql.Date;

public class orders {
    private int orders_id;
    private Date orders_date;
    private String orders_pay_status;
    private int product_id;
    private int users_id;
    private int customer_id;   
    public int getOrders_id() {
        return orders_id;
    }
    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }
    public Date getOrders_date() {
        return orders_date;
    }
    public void setOrders_date(Date orders_date) {
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
    @Override
    public String toString() {
        return "oders [orders_id=" + orders_id + ", orders_date=" + orders_date + ", orders_pay_status="
                + orders_pay_status + "]";
    }
    
}
