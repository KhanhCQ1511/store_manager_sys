package source.src;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dataSource {
    public static final String DB_Name = "store_manager";
    String username = "root";
    String password = "15112003";
    public static final String CONNECTION_STRING = "jdbc:mySQL://localhost:3306/store_manager";

    // All the database tables and their columns are stored as String variables
    // table users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "users_id";
    public static final String COLUMN_USERS_USERNAME = "users_username";
    public static final String COLUMN_USERS_PASSWORD = "users_password";
    public static final String COLUMN_USERS_FULLNAME = "users_fullname";
    public static final String COLUMN_USERS_PHONE = "users_phone";
    public static final String COLUMN_USERS_ADDRESS = "users_address";
    public static final String COLUMN_USERS_DESCRIPTION = "user_description";
    // table customer
    public static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_CUSTOMER_FULLNAME = "customer_fullname";
    public static final String COLUMN_CUSTOMER_PHONE = "customer_phone";
    public static final String COLUMN_CUSTOMER_EMAIL = "customer_email";
    public static final String COLUMN_CUSTOMER_DESCRIPTION = "customer_description";
    // table categories
    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CATEGORIES_ID = "categories_id";
    public static final String COLUMN_CATEGORIES_NAME = "categories_name";
    public static final String COLUMN_CATEGORIES_DESCRIPTION = "categories_description";
    // table distributors
    public static final String TABLE_DISTRIBUTOR = "distributor";
    public static final String COLUMN_DISTRIBUTOR_ID = "distributor_id";
    public static final String COLUMN_DISTRIBUTOR_NAME = "distributor_name";
    public static final String COLUMN_DISTRIBUTOR_PHONE = "distributor_phone";
    public static final String COLUMN_DISTRIBUTOR_ADDRESS = "distributor_address";
    // table product
    public static final String TABLE_PRODUCT = "product";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_CODE = "product_code";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_SIZE = "product_size";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_PRODUCT_QUANTITY = "product_quantity";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
    public static final String COLUMN_PRODUCT_CATEGORIES_ID = "categories_id";
    public static final String COLUMN_PRODUCT_DISTRIBUTOR_ID = "distributor_id";
    // table orders
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDERS_ID = "orders_id";
    public static final String COLUMN_ORDERS_DATE = "orders_date";
    public static final String COLUMN_ORDERS_PAY_STATUS = "orders_pay_status";
    public static final String COLUMN_ORDERS_PRODUCT_ID = "product_id";
    public static final String COLUMN_ORDERS_USERS_ID = "users_id";
    public static final String COLUMN_ORDERS_CUSTOMER_ID = "customer_id";

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;
    private Connection conn;
    /**
     * Create an object of Datasource
     */
    private static final dataSource instance = new dataSource();

    /**
     * Make the constructor private so that this class cannot be instantiated
     */
    private dataSource() {
    }

    /**
     * Get the only object available
     */
    public static dataSource getInstance() {
        return instance;
    }

    /**
     * This method makes the connection to the database and assigns the Connection
     * to the conn variable.
     */
    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING,username,password);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method closes the connection to the database.
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " +e.getMessage());
        }
    }

    /****************************
     * PRODUCTS QUERRY *
     ****************************/
    /**
     * This private method returns an default query for the products.
     * note: fix as in findProduct  done**
     */
    private StringBuilder querryProducts() {
        return new StringBuilder("SELECT " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_ID + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_CODE + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_NAME + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_SIZE + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_PRICE + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_QUANTITY + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_DESCRIPTION + ", " +
                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_ID + ", "+
                TABLE_DISTRIBUTOR + "." + COLUMN_DISTRIBUTOR_ID + ", "+
                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_NAME + ", " +
                TABLE_DISTRIBUTOR + "." + COLUMN_DISTRIBUTOR_NAME +
                " FROM " + TABLE_PRODUCT +
                " LEFT JOIN " + TABLE_CATEGORIES +
                " ON " + TABLE_PRODUCT + "." + COLUMN_PRODUCT_CATEGORIES_ID +
                " = " + TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_ID+
                " LEFT JOIN " + TABLE_DISTRIBUTOR +
                " ON " + TABLE_PRODUCT + "." + COLUMN_PRODUCT_DISTRIBUTOR_ID +
                " = " + TABLE_DISTRIBUTOR + "." + COLUMN_DISTRIBUTOR_ID);
    }

    /*
     * Get all products from the database, done
     */
    public List<product> getAllProducts(int sortOrder) {
        StringBuilder querryProducts = querryProducts();
        if (sortOrder != ORDER_BY_NONE) {
            querryProducts.append(" ORDER BY ");
            querryProducts.append(COLUMN_PRODUCT_CODE);
            if (sortOrder == ORDER_BY_DESC) {
                querryProducts.append(" DESC");
            } else {
                querryProducts.append(" ASC");
            }
        }
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(querryProducts.toString());
            List<product> products = new ArrayList<product>();
            while (rs.next()) {
                product newProducts = new product();
                newProducts.setProduct_id(rs.getInt(1));
                newProducts.setProduct_code(rs.getString(2));
                newProducts.setProduct_name(rs.getString(3));
                newProducts.setProduct_size(rs.getString(4));
                newProducts.setProduct_price(rs.getString(5));
                newProducts.setProduct_quantity(rs.getString(6));
                newProducts.setProduct_description(rs.getString(7));
                newProducts.setCategories_id(rs.getInt(8));
                newProducts.setDistributor_id(rs.getInt(9));
                newProducts.setCategories_name(rs.getString(10));
                newProducts.setDistributor_name(rs.getString(11));
                products.add(newProducts);
            }
            return products;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method get one product from the database based on the provided
     * product_id. done
     */
    public List<product> getOneProduct(int product_id) {
        StringBuilder queryProducts = querryProducts();
        queryProducts.append(" WHERE " + TABLE_PRODUCT + "." + COLUMN_PRODUCT_ID + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryProducts))) {
            statement.setInt(1, product_id);
            ResultSet rs = statement.executeQuery();
            List<product> products = new ArrayList<>();
            while (rs.next()) {
                product newProducts = new product();
                newProducts.setProduct_id(rs.getInt(1));
                newProducts.setProduct_code(rs.getString(2));
                newProducts.setProduct_name(rs.getString(3));
                newProducts.setProduct_size(rs.getString(4));
                newProducts.setProduct_price(rs.getString(5));
                newProducts.setProduct_quantity(rs.getString(6));
                newProducts.setProduct_description(rs.getString(7));
                newProducts.setCategories_name(rs.getString(10));
                newProducts.setDistributor_name(rs.getString(11));
                products.add(newProducts);
            }
            return products;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method searches products from the database based on the provided
     * searchString.  done
     */
    public List<product> searchProducts(String searchString, int sortOrder) {
        StringBuilder queryProducts = querryProducts();
        queryProducts.append(" WHERE (" + TABLE_PRODUCT + "." + COLUMN_PRODUCT_NAME + " LIKE ? OR " + TABLE_PRODUCT
                + "." + COLUMN_PRODUCT_CODE + " LIKE ?)");

        if (sortOrder != ORDER_BY_NONE) {
            queryProducts.append(" ORDER BY ");
            queryProducts.append(COLUMN_PRODUCT_NAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryProducts.append(" DESC");
            } else {
                queryProducts.append(" ASC");
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(queryProducts.toString())) {
            statement.setString(1, "%" + searchString + "%");
            statement.setString(2, "%" + searchString + "%");
            ResultSet rs = statement.executeQuery();

            List<product> products = new ArrayList<>();
            while (rs.next()) {
                product newProducts = new product();
                newProducts.setProduct_id(rs.getInt(1));
                newProducts.setProduct_code(rs.getString(2));
                newProducts.setProduct_name(rs.getString(3));
                newProducts.setProduct_size(rs.getString(4));
                newProducts.setProduct_price(rs.getString(5));
                newProducts.setProduct_quantity(rs.getString(6));
                newProducts.setProduct_description(rs.getString(7));
                newProducts.setCategories_name(rs.getString(10));
                newProducts.setDistributor_name(rs.getString(11));
                products.add(newProducts);
            }
            return products;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method deletes one product based on the productId provided. done
     */
    public boolean deleteSingleProduct(int productId) {
        String sql = "DELETE FROM " + TABLE_PRODUCT + " WHERE " + COLUMN_PRODUCT_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, productId);
            int rows = statement.executeUpdate();
            System.out.println(rows + " record(s) deleted.");
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method insert one product to the database. done
     */
    public boolean insertNewProduct(String code, String name, String size, double price, int quantity,
            String description, int category_id, int distributor_id) {

        String sql = "INSERT INTO " + TABLE_PRODUCT + " ("
                + COLUMN_PRODUCT_CODE + ", "
                + COLUMN_PRODUCT_NAME + ", "
                + COLUMN_PRODUCT_SIZE + ", "
                + COLUMN_PRODUCT_PRICE + ", "
                + COLUMN_PRODUCT_QUANTITY + ", "
                + COLUMN_PRODUCT_DESCRIPTION + ", "
                + COLUMN_PRODUCT_CATEGORIES_ID + ", "
                + COLUMN_PRODUCT_DISTRIBUTOR_ID +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, size);
            statement.setDouble(4, price);
            statement.setInt(5, quantity);
            statement.setString(6, description);
            statement.setInt(7, category_id);
            statement.setInt(8, distributor_id);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method updates one product to the database. done
     */
    public boolean updateOneProduct(String code, String name, String size, double price, int quantity,
            String description, int category_id, int distributor_id, String oldCode) {

        String sql = "UPDATE " + TABLE_PRODUCT + " SET "
                + COLUMN_PRODUCT_CODE + " = ?" + ", "
                + COLUMN_PRODUCT_NAME + " = ?" + ", "
                + COLUMN_PRODUCT_SIZE + " = ?" + ", "
                + COLUMN_PRODUCT_PRICE + " = ?" + ", "
                + COLUMN_PRODUCT_QUANTITY + " = ?" + ", "
                + COLUMN_PRODUCT_DESCRIPTION + " = ?" + ", "
                + COLUMN_PRODUCT_CATEGORIES_ID + " = ?" + ", "
                + COLUMN_PRODUCT_DISTRIBUTOR_ID + " = ?" +
                " WHERE " + COLUMN_PRODUCT_CODE + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, size);
            statement.setDouble(4, price);
            statement.setInt(5, quantity);
            statement.setString(6, description);
            statement.setInt(7, category_id);
            statement.setInt(8, distributor_id);
            statement.setString(9, oldCode);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method decreases the product stock by one based on the provided
     * product_id.
     */
    public void decreaseStock(int product_id) {

        String sql = "UPDATE " + TABLE_PRODUCT + " SET " + COLUMN_PRODUCT_QUANTITY + " = " + COLUMN_PRODUCT_QUANTITY
                + " - 1 WHERE " + COLUMN_PRODUCT_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, product_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    /****************************
     * END PRODUCTS QUERRY *
     ****************************/

    /***************************
     * BEGIN CATEGORIES QUERRY *
     ****************************/
    /**
     * This method gets all the product categories from the database.
     * 
     */
    public List<categories> getProductCategories(int sortOrder) {
        StringBuilder queryCategories = new StringBuilder("SELECT " +
                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_ID + ", " +
                TABLE_CATEGORIES + "." + COLUMN_CATEGORIES_NAME +
                " FROM " + TABLE_CATEGORIES);

        if (sortOrder != ORDER_BY_NONE) {
            queryCategories.append(" ORDER BY ");
            queryCategories.append(COLUMN_CATEGORIES_ID);
            if (sortOrder == ORDER_BY_DESC) {
                queryCategories.append(" DESC");
            } else {
                queryCategories.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(queryCategories.toString())) {

            List<categories> categories = new ArrayList<>();
            while (results.next()) {
                categories category = new categories();
                category.setCategories_id(results.getInt(1));
                category.setCategories_name(results.getString(2));
                categories.add(category);
            }
            return categories;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /***************************
     * END CATEGORIES QUERRY *
     ****************************/

     public List<distributor> getProductDistributor(int sortOrder) {
        StringBuilder queryDistributor = new StringBuilder("SELECT " +
                TABLE_DISTRIBUTOR + "." + COLUMN_DISTRIBUTOR_ID + ", " +
                TABLE_DISTRIBUTOR + "." + COLUMN_DISTRIBUTOR_NAME +
                " FROM " + TABLE_DISTRIBUTOR);

        if (sortOrder != ORDER_BY_NONE) {
            queryDistributor.append(" ORDER BY ");
            queryDistributor.append(COLUMN_DISTRIBUTOR_ID);
            if (sortOrder == ORDER_BY_DESC) {
                queryDistributor.append(" DESC");
            } else {
                queryDistributor.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(queryDistributor.toString())) {

            List<distributor> dList = new ArrayList<>();
            while (results.next()) {
                distributor distributor = new distributor();
                distributor.setDistributor_id(results.getInt(1));
                distributor.setDistributor_name(results.getString(2));
                dList.add(distributor);
            }
            return dList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /***************************
     * BEGIN CUSTOMER QUERRY *
     ****************************/
    /**
     * This private method returns an default query for the customers.
     */
    private StringBuilder queryCustomers() {
        return new StringBuilder("SELECT " +
                TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_FULLNAME + ", " +
                TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_PHONE + ", " +
                TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_EMAIL + ", " +
                TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_DESCRIPTION + 
                " FROM " + TABLE_CUSTOMER);
    }

    /**
     * This method get all the customers from the database.
     */
    public List<customer> getAllCustomers(int sortOrder) {

        StringBuilder queryCustomers = queryCustomers();

        if (sortOrder != ORDER_BY_NONE) {
            queryCustomers.append(" ORDER BY ");
            queryCustomers.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryCustomers.append(" DESC");
            } else {
                queryCustomers.append(" ASC");
            }
        }
        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(queryCustomers.toString())) {

            List<customer> customers = new ArrayList<>();
            while (results.next()) {
                customer customer = new customer();
                customer.setCustomer_id(results.getInt(1));
                customer.setCustomer_fullname(results.getString(2));
                customer.setCustomer_phone(results.getString(3));
                customer.setCustomer_email(results.getString(4));
                customer.setCustomer_description(results.getString(5));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method get one customer from the database based on the provided
     * product_id.
     */
    public List<customer> getOneCustomer(int customer_id) {

        StringBuilder queryCustomers = queryCustomers();
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryCustomers))) {
            statement.setInt(1, customer_id);
            ResultSet results = statement.executeQuery();
            List<customer> customers = new ArrayList<>();
            while (results.next()) {
                customer customer = new customer();
                customer.setCustomer_fullname(results.getString(2));
                customer.setCustomer_phone(results.getString(3));
                customer.setCustomer_email(results.getString(4));
                customer.setCustomer_description(results.getString(5));
                customers.add(customer);
            }
            System.out.println(customers);
            return customers;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method searches customers from the database based on the provided
     * searchString.
     */
    public List<customer> searchCustomers(String searchString, int sortOrder) {

        StringBuilder queryCustomers = queryCustomers();

        queryCustomers.append(" AND (" + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_FULLNAME + " LIKE ? OR "
                + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_PHONE + " LIKE ?)");

        if (sortOrder != ORDER_BY_NONE) {
            queryCustomers.append(" ORDER BY ");
            queryCustomers.append(COLUMN_CUSTOMER_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryCustomers.append(" DESC");
            } else {
                queryCustomers.append(" ASC");
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(queryCustomers.toString())) {
            statement.setString(1, "%" + searchString + "%");
            statement.setString(2, "%" + searchString + "%");
            ResultSet results = statement.executeQuery();

            List<customer> customers = new ArrayList<>();
            while (results.next()) {
                customer customer = new customer();
                customer.setCustomer_id(results.getInt(1));
                customer.setCustomer_fullname(results.getString(2));
                customer.setCustomer_phone(results.getString(3));
                customer.setCustomer_email(results.getString(4));
                customer.setCustomer_description(results.getString(5));
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method deletes one customer based on the customerId provided.
     */
    public boolean deleteSingleCustomer(int customerId) {
        String sql = "DELETE FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMER_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            int rows = statement.executeUpdate();
            System.out.println(rows + " " + TABLE_CUSTOMER + " record(s) deleted.");

            String sql2 = "DELETE FROM " + TABLE_ORDERS + " WHERE " + COLUMN_ORDERS_CUSTOMER_ID + " = ?";

            try (PreparedStatement statement2 = conn.prepareStatement(sql2)) {
                statement2.setInt(1, customerId);
                int rows2 = statement2.executeUpdate();
                System.out.println(rows2 + " " + TABLE_ORDERS + " record(s) deleted.");
                return true;
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method gets one user from the database based on the email provided.
     */
    public customer getCustomerByEmail(String email) throws SQLException {

        PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMER_EMAIL + " = ?");
        preparedStatement.setString(1, email);
        ResultSet results = preparedStatement.executeQuery();

        customer customer = new customer();
        if (results.next()) {
            customer.setCustomer_id(results.getInt("customer_id"));
            customer.setCustomer_fullname(results.getString("customer_fullname"));
            customer.setCustomer_phone(results.getString("customer_phone"));
            customer.setCustomer_email(results.getString("customer_email"));
            customer.setCustomer_description(results.getString("customer_description"));
        }

        return customer;
    }

    /***************************
     * END CUSTOMER QUERRY *
     ****************************/

    /***************************
     * BEGIN USER QUERRY *
     ****************************/
    /**
     * This method gets one user from the database based on the username provided.
     */
    public users getUserByUsername(String username) throws SQLException {

        PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_USERNAME + " = ?");
        preparedStatement.setString(1, username);
        ResultSet results = preparedStatement.executeQuery();

        users user = new users();
        if (results.next()) {

            user.setUsers_id(results.getInt("users_id"));
            user.setUsers_username(results.getString("users_username"));
            user.setUsers_password(results.getString("users_password"));
            user.setUsers_fullname(results.getString("users_fullname"));
            user.setUsers_phone(results.getString("users_phone"));
            user.setUsers_address(results.getString("users_address"));
            user.setUsers_description(results.getString("user_description"));
        }

        return user;
    }

    /**
     * This method insert one simple user to the database.
     */
    public boolean insertNewUser(String users_username, String users_password, String users_fullname,
            String users_phone, String users_address, String users_description) {

        String sql = "INSERT INTO " + TABLE_USERS + " ("
                + COLUMN_USERS_USERNAME + ", "
                + COLUMN_USERS_PASSWORD + ", "
                + COLUMN_USERS_FULLNAME + ", "
                + COLUMN_USERS_PHONE + ", "
                + COLUMN_USERS_ADDRESS + ", "
                + COLUMN_USERS_DESCRIPTION +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, users_username);
            statement.setString(2, users_password);
            statement.setString(3, users_fullname);
            statement.setString(4, users_phone);
            statement.setString(5, users_address);
            statement.setString(6, users_description);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    public users getUserByPhone(String phone) throws SQLException {

        PreparedStatement preparedStatement = conn
                .prepareStatement("SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_CUSTOMER_PHONE + " = ?");
        preparedStatement.setString(1, phone);
        ResultSet results = preparedStatement.executeQuery();

        users user = new users();
        if (results.next()) {
            user.setUsers_id(results.getInt("users_id"));
            user.setUsers_username(results.getString("users_username"));
            user.setUsers_password(results.getString("users_password"));
            user.setUsers_fullname(results.getString("users_fullname"));
            user.setUsers_phone(results.getString("users_phone"));
            user.setUsers_address(results.getString("users_address"));
            user.setUsers_description(results.getString("user_description"));
        }

        return user;
    }

    /***************************
     * END USER QUERRY *
     ****************************/

    /***************************
     * BEGIN ODERS QUERRY *
     ****************************/
    /**
     * This method gets all orders from the database. done
     */
    public List<orders> getAllOrders(int sortOrder) {
        StringBuilder queryOrders = new StringBuilder("SELECT " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_NAME + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_DATE + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_PAY_STATUS+ ", " +
                TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_FULLNAME + ", " +
                TABLE_USERS + "." + COLUMN_USERS_FULLNAME +
                " FROM " + TABLE_ORDERS);
        queryOrders.append("" +
                " LEFT JOIN " + TABLE_PRODUCT +
                " ON " + TABLE_ORDERS + "." + COLUMN_ORDERS_PRODUCT_ID +
                " = " + TABLE_PRODUCT + "." + COLUMN_PRODUCT_ID);
        queryOrders.append("" +
                " LEFT JOIN " + TABLE_USERS +
                " ON " + TABLE_ORDERS + "." + COLUMN_ORDERS_USERS_ID +
                " = " + TABLE_USERS + "." + COLUMN_USERS_ID);
        queryOrders.append("" +
                " LEFT JOIN " + TABLE_CUSTOMER +
                " ON " + TABLE_ORDERS + "." + COLUMN_ORDERS_CUSTOMER_ID +
                " = " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_ID);

        if (sortOrder != ORDER_BY_NONE) {
            queryOrders.append(" ORDER BY ");
            queryOrders.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryOrders.append(" DESC");
            } else {
                queryOrders.append(" ASC");
            }
        }
        try (Statement statement = conn.createStatement()) {
        ResultSet rs = statement.executeQuery(queryOrders.toString());
        List<orders> orders = new ArrayList<>();
        while (rs.next()) {
            orders newOrder = new orders();
            newOrder.setProduct_name(rs.getString(1));
            newOrder.setOrders_date(rs.getTimestamp(2));
            newOrder.setOrders_pay_status(rs.getString(3));
            newOrder.setCustomer_name(rs.getString(4));
            newOrder.setUsers_name(rs.getString(5));
            orders.add(newOrder);
        }
        return orders;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method gets all orders of the simple user from the database.
     */
    public List<orders> getAllUserOrders(int sortOrder, int user_id) {

        StringBuilder queryOrders = new StringBuilder("SELECT " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_ID + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_PRODUCT_ID + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_USERS_ID + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_CUSTOMER_ID + ", " +
                TABLE_USERS + "." + COLUMN_USERS_FULLNAME + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_DATE + ", " +
                TABLE_ORDERS + "." + COLUMN_ORDERS_PAY_STATUS + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_NAME + ", " +
                TABLE_PRODUCT + "." + COLUMN_PRODUCT_PRICE +
                " FROM " + TABLE_ORDERS);

        queryOrders.append("" +
                " LEFT JOIN " + TABLE_PRODUCT +
                " ON " + TABLE_ORDERS + "." + COLUMN_ORDERS_PRODUCT_ID +
                " = " + TABLE_PRODUCT + "." + COLUMN_PRODUCT_ID);
        queryOrders.append("" +
                " LEFT JOIN " + TABLE_USERS +
                " ON " + TABLE_ORDERS + "." + COLUMN_ORDERS_USERS_ID +
                " = " + TABLE_USERS + "." + COLUMN_USERS_ID);
        queryOrders.append(" WHERE " + TABLE_ORDERS + "." + COLUMN_ORDERS_USERS_ID + " = ").append(user_id);

        if (sortOrder != ORDER_BY_NONE) {
            queryOrders.append(" ORDER BY ");
            queryOrders.append(COLUMN_USERS_FULLNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryOrders.append(" DESC");
            } else {
                queryOrders.append(" ASC");
            }
        }

        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery(queryOrders.toString())) {

            List<orders> orders = new ArrayList<>();
            while (results.next()) {
                orders order = new orders();
                order.setOrders_id(results.getInt("orders_id"));
                order.setOrders_date(results.getTimestamp("orders_date"));
                order.setOrders_pay_status(results.getString("orders_pay_status"));
                order.setProduct_id(results.getInt("product_id"));
                order.setCustomer_id(results.getInt("customer_id"));
                order.setUsers_id(results.getInt("users_id"));
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method insert one order to the database.
     */
    public boolean insertNewOrder(int product_id, int user_id, int customer_id, Date order_date,
            String order_pay_status) {

        String sql = "INSERT INTO " + TABLE_ORDERS + " ("
                + COLUMN_ORDERS_PRODUCT_ID + ", "
                + COLUMN_ORDERS_USERS_ID + ", "
                + COLUMN_ORDERS_CUSTOMER_ID + ", "
                + COLUMN_ORDERS_DATE + ", "
                + COLUMN_ORDERS_PAY_STATUS +
                ") VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, product_id);
            statement.setInt(2, user_id);
            statement.setInt(3, customer_id);
            statement.setDate(4, order_date);
            statement.setString(4, order_pay_status);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /***************************
     * END ODERS QUERRY *
     ****************************/
    /**
     * This method counts all the products on the database.
     */
    public Integer countAllProducts() {
        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCT)) {
            if (results.next()) {
                return results.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return 0;
        }
    }

    /**
     * This method counts all the simple users on the database.
     */
    public Integer countAllCustomers() {
        try (Statement statement = conn.createStatement();
                ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM " + TABLE_CUSTOMER)) {
            if (results.next()) {
                return results.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return 0;
        }
    }

    /**
     * This method counts all the orders on the database.
     */
    public Integer countUserOrders(int user_id) {

        try (PreparedStatement statement = conn.prepareStatement(
                String.valueOf("SELECT COUNT(*) FROM " + TABLE_ORDERS + " WHERE " + COLUMN_ORDERS_USERS_ID + "= ?"))) {
            statement.setInt(1, user_id);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return results.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return 0;
        }
    }

}
