package source.controller;
/**
 * This class acts as an user session.
 * It stores logged in user details.
 * It is constructed with the Singleton Design Pattern.
 *
 * This pattern involves a single class which is responsible to create an object while making sure that only single
 * object gets created. This class provides a way to access its only object which can be accessed directly without
 * need to instantiate the object of the class.
 *
 */
public class userSessionController{
    private static int userId;
    private static String userUsername;
    private static String userPassword;
    private static String userFullname;
    private static String userPhone;
    private static String userAddress;
    private static String userDescription;
    private static int userAdminstrator;

    /**
     * Create an object of UserSessionController
     */
    private static final userSessionController instance = new userSessionController();

    /**
     * Make the constructor private so that this class cannot be instantiated
     */
    private userSessionController() { }

    /**
     * Get the only object available
     * @return      UserSessionController instance.
     */
    public static userSessionController getInstance() {
        return instance;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        userSessionController.userId = userId;
    }

    public static String getUserUsername() {
        return userUsername;
    }

    public static void setUserUsername(String userUsername) {
        userSessionController.userUsername = userUsername;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        userSessionController.userPassword = userPassword;
    }

    public static String getUserFullname() {
        return userFullname;
    }

    public static void setUserFullname(String userFullname) {
        userSessionController.userFullname = userFullname;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static void setUserPhone(String userPhone) {
        userSessionController.userPhone = userPhone;
    }

    public static String getUserAddress() {
        return userAddress;
    }

    public static void setUserAddress(String userAddress) {
        userSessionController.userAddress = userAddress;
    }

    public static String getUserDescription() {
        return userDescription;
    }

    public static void setUserDescription(String userDescription) {
        userSessionController.userDescription = userDescription;
    }

    public static int getUserAdminstrator() {
        return userAdminstrator;
    }

    public static void setUserAdminstrator(int userAdminstrator) {
        userSessionController.userAdminstrator = userAdminstrator;
    }
    
    public static void cleanUserSession() {
        userId = 0;
        userUsername = null;
        userPassword = null;
        userFullname = null;
        userPhone = null;
        userAddress = null;
        userDescription = null;
    }

    
}