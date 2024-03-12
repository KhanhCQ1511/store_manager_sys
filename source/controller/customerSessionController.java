package source.controller;

public class customerSessionController {
    private static int customerId;
    private static String customerFullname;
    private static String customerPhone;
    private static String customerEmail;
    private static String customerDescription;

    /**
     * Create an object of customerSessionController
     */
    private static final customerSessionController instance = new customerSessionController();

    /**
     * Make the constructor private so that this class cannot be instantiated
     */
    private customerSessionController() { }

    /**
     * Get the only object available
     */
    public static customerSessionController getInstance() {
        return instance;
    }

    public static int getCustomerId() {
        return customerId;
    }

    public static void setUserId(int customerId) {
        customerSessionController.customerId = customerId;
    }

    public static String getCustomerFullname() {
        return customerFullname;
    }

    public static void setCustomerFullname(String customerFullname) {
        customerSessionController.customerFullname = customerFullname;
    }

    public static String getCustomerPhone() {
        return customerPhone;
    }

    public static void setCustomerPhone(String customerPhone) {
        customerSessionController.customerPhone = customerPhone;
    }

    public static String getCustomerEmail() {
        return customerEmail;
    }

    public static void setCustomerEmail(String customerEmail) {
        customerSessionController.customerEmail = customerEmail;
    }

    public static String getCustomerDescription() {
        return customerDescription;
    }

    public static void setCustomerDescription(String customerDescription) {
        customerSessionController.customerDescription = customerDescription;
    }
    
    public static void cleanCustomerSession() {
        customerId= 0;
        customerFullname = null;
        customerPhone = null;
        customerEmail = null;
        customerDescription = null;
    }
}
