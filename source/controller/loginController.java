package source.controller;

import source.src.*;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import source.util.helperMethods;

public class loginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;

    Stage dialogStage = new Stage();
    Scene scene;

    /**
     * This method handles the login button action event.
     * It gets the user entered data and makes the proper validations.
     * If the entered details are correct, it creates an new UserSessionController
     * instance
     * and transitions the user screen to the appropriate dashboard.
     */
    public void handleLoginButtonAction(ActionEvent event) throws SQLException, IOException {
        String username = usernameField.getText();
        String providedPassword = passwordField.getText();
        // check if username or provided password is already
        if ((username == null) || (providedPassword == null) || (username.isEmpty() || providedPassword.isEmpty())) {
            helperMethods.alertBox("Please enter Username or Password!", null, "Login failed!");
        } else if (!helperMethods.validateUsername(username)) { // check if username is valid
            helperMethods.alertBox("Please enter valid username!", null, "Login failed!");
        } else {
            users user = dataSource.getInstance().getUserByUsername(username);
            if (user.getUsers_password() == null || user.getUsers_password().isEmpty()) {
                helperMethods.alertBox("There is no user register with this username!", null, "Login failed!");
            } else {
                // boolean passwordMatch = passwordUtils.verifyUserPassword(providedPassword, user.getUsers_password(),
                //         user.getUsers_phone()); //hashing the password
                if (!providedPassword.isEmpty()) {
                    userSessionController.setUserId(user.getUsers_id());
                    userSessionController.setUserUsername(user.getUsers_username());
                    userSessionController.setUserPassword(user.getUsers_password());
                    userSessionController.setUserFullname(user.getUsers_fullname());
                    userSessionController.setUserPhone(user.getUsers_phone());
                    userSessionController.setUserAddress(user.getUsers_address());
                    userSessionController.setUserDescription(user.getUsers_description());

                    Node node = (Node) event.getSource();
                    dialogStage = (Stage) node.getScene().getWindow();
                    dialogStage.close();
                    if (!user.getUsers_username().trim().equals("admin")) {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/user/main-dashboard.fxml")));
                    } else if (user.getUsers_username().trim().equals("admin")) {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/admin/main-dashboard.fxml")));
                    }
                    dialogStage.setScene(scene);
                    dialogStage.show();
                } else {
                    helperMethods.alertBox("Please enter correct Email and Password", null, "Login Failed!");
                }
            }
        }
    }
    /**
     * This method handles the register button action event.
     * It transfers the user screen to the registration view.
     */
    public void handleRegisterButtonAction(ActionEvent actionEvent) throws IOException {
        Stage dialogStage;
        Node node = (Node) actionEvent.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/source/view/register.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
