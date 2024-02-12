package source.src;

public class customer {
    private int customer_id;
    private String customer_fullname;
    private String customer_phone;
    private String customer_email;
    private String customer_description;
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public String getCustomer_fullname() {
        return customer_fullname;
    }
    public void setCustomer_fullname(String customer_fullname) {
        this.customer_fullname = customer_fullname;
    }
    public String getCustomer_phone() {
        return customer_phone;
    }
    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }
    public String getCustomer_email() {
        return customer_email;
    }
    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }
    public String getCustomer_description() {
        return customer_description;
    }
    public void setCustomer_description(String customer_description) {
        this.customer_description = customer_description;
    }
    @Override
    public String toString() {
        return "customer [customer_id=" + customer_id + ", customer_fullname=" + customer_fullname + ", customer_phone="
                + customer_phone + ", customer_email=" + customer_email + ", customer_description="
                + customer_description + "]";
    }
    
}
