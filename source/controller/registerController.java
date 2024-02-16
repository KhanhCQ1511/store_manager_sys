package source.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import source.util.helperMethods;
import source.util.passwordUtils;
import source.src.*;

public class registerController {
    @FXML
    private TextField users_usernameField;
    @FXML
    private PasswordField users_passwordField;
    @FXML
    private TextField users_fullnameField;
    @FXML
    private TextField users_phoneField;
    @FXML
    private TextField users_addressField;
    @FXML
    private TextField users_descriptionField;

    Stage dialogStage = new Stage();
    Scene scene;
    /**
     * This method handles the login button action event.
     * It transfers the user screen to the login view.
     */
    public void handleLoginButtonAction(ActionEvent actionEvent) throws IOException {
        Stage dialogStage;
        Node node = (Node) actionEvent.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/login.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();
    }
    /**
     * This method handles the register button action event.
     * It gets the user entered data and makes the proper validations.
     * If the entered details are correct, it saves the user data to the database,
     * creates an new UserSessionController instance and transitions the user screen
     * to the appropriate dashboard.
     */
    public void handleRegisterButtonAction(ActionEvent actionEvent) throws SQLException {
        String validationErrors = "";
        boolean errors = false;
        String username = users_usernameField.getText();
        String providedPassword = users_passwordField.getText();
        String fullName = users_fullnameField.getText();
        String phone = users_phoneField.getText();
        String address = users_addressField.getText();
        String description = users_descriptionField.getText();

        // Validate Full Name
        if (fullName == null || fullName.isEmpty()) {
            validationErrors += "Please enter your Name and Surname! \n";
            errors = true;
        } else if (!helperMethods.validateFullName(fullName)) {
            validationErrors += "Please enter a valid Name and Surname! \n";
            errors = true;
        }

        //Validate Phone
        if (phone == null || phone.isEmpty()) {
            validationErrors += "Please enter your phone number! \n";
            errors = true;
        } else if (!helperMethods.validatePhone(phone)) {
            validationErrors += "Please enter a valid phone number! \n";
            errors = true;
        }

        // Validate Username
        if (username == null || username.isEmpty()) {
            validationErrors += "Please enter a username! \n";
            errors = true;
        } else if (!helperMethods.validateUsername(username)) {
            validationErrors += "Please enter a valid Username! \n";
            errors = true;
        } else {
            users userByUsername = dataSource.getInstance().getUserByUsername(username);
            if (userByUsername.getUsers_username() != null) {
                validationErrors += "There is already a user registered with this username! \n";
                errors = true;
            }
        }

        // // Validate Email
        // if (email == null || email.isEmpty()) {
        //     validationErrors += "Please enter an email address! \n";
        //     errors = true;
        // } else if (!HelperMethods.validateEmail(email)) {
        //     validationErrors += "Please enter a valid Email address! \n";
        //     errors = true;
        // } else {
        //     users userByEmail = dataSource.getInstance().getUserByEmail(email);
        //     if (userByEmail.getEmail() != null) {
        //         validationErrors += "There is already a user registered with this email address! \n";
        //         errors = true;
        //     }
        // }

        // Validate Password
        if (providedPassword == null || providedPassword.isEmpty()) {
            validationErrors += "Please enter the password! \n";
            errors = true;
        } else if (!helperMethods.validatePassword(providedPassword)){
            validationErrors += "Password must be at least 6 and maximum 16 characters! \n";
            errors = true;
        }

        if (errors) {
            helperMethods.alertBox(validationErrors, null, "Registration Failed!");
        } else {

            String salt = passwordUtils.getSalt(30);
            String securePassword = passwordUtils.generateSecurePassword(providedPassword, salt);

            Task<Boolean> addUserTask = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    return dataSource.getInstance().insertNewUser(username, securePassword, fullName, phone, address, description);
                }
            };

            addUserTask.setOnSucceeded(e -> {
                if (addUserTask.valueProperty().get()) {
                    users user = null;
                    try {
                        user = dataSource.getInstance().getUserByPhone(phone);
                    } catch (SQLException err) {
                        err.printStackTrace();
                    }

                    // Method invocation 'getId' may produce 'NullPointerException'
                    assert user != null;

                    userSessionController.setUserId(user.getUsers_id());
                    userSessionController.setUserUsername(user.getUsers_username());
                    userSessionController.setUserFullname(user.getUsers_fullname());
                    userSessionController.setUserPhone(user.getUsers_phone());
                    userSessionController.setUserAddress(user.getUsers_address());
                    userSessionController.setUserDescription(user.getUsers_description());

                    Node node = (Node) actionEvent.getSource();
                    dialogStage = (Stage) node.getScene().getWindow();
                    dialogStage.close();
                    if (user.getUsers_username() != "admin") {
                        try {
                            scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/user/main-dashboard.fxml")));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (user.getUsers_username() == "admin") {
                        try {
                            scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/admin/main-dashboard.fxml")));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    dialogStage.setScene(scene);
                    dialogStage.show();
                }
            });

            new Thread(addUserTask).start();

        }
    }
}
