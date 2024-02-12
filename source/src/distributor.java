package source.src;

public class distributor {
    private int distributor_id;
    private String distributor_name;
    private String distributor_phone;
    private String distributor_address;
    public int getDistributor_id() {
        return distributor_id;
    }
    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
    }
    public String getDistributor_name() {
        return distributor_name;
    }
    public void setDistributor_name(String distributor_name) {
        this.distributor_name = distributor_name;
    }
    public String getDistributor_phone() {
        return distributor_phone;
    }
    public void setDistributor_phone(String distributor_phone) {
        this.distributor_phone = distributor_phone;
    }
    public String getDistributor_address() {
        return distributor_address;
    }
    public void setDistributor_address(String distributor_address) {
        this.distributor_address = distributor_address;
    }
    @Override
    public String toString() {
        return "distributor [distributor_id=" + distributor_id + ", distributor_name=" + distributor_name
                + ", distributor_phone=" + distributor_phone + ", distributor_address=" + distributor_address + "]";
    }
    
}
