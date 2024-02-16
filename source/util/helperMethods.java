package source.util;

import javafx.scene.control.Alert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class helperMethods {
    public static boolean validateFullName(String fullName) {
        // ^ # start of string
        // [a-zA-Z]{4,} # 4 or more ASCII letters
        // (?: [a-zA-Z]+){0,2} # 0 to 2 occurrences of a space followed with one or more
        // ASCII letters
        // $ # end of string.
        Matcher matcher = Pattern.compile("^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$", Pattern.CASE_INSENSITIVE)
                .matcher(fullName);
        return matcher.find();
    }

    public static boolean validateUsername(String username) {
        // ^ # start of string
        // [a-zA-Z] # lowercase or uppercase ASCII letters
        // \\w{4, 29} # remaining items are word items, which includes the underscore,
        // until it reaches the end and that is represented with $
        // {4, 29} # 5-30 character constraint given, minus the predefined first
        // character
        Matcher matcher = Pattern.compile("^[A-Za-z]\\w{4,29}$", Pattern.CASE_INSENSITIVE).matcher(username);
        return matcher.find();
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
        // String pattern =
        // "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        // ^ # start-of-string
        // (?=.*[0-9]) # a digit must occur at least once
        // (?=.*[a-z]) # a lower case letter must occur at least once
        // (?=.*[A-Z]) # an upper case letter must occur at least once
        // (?=.*[@#$%^&+=]) # a special character must occur at least once
        // (?=\S+$) # no whitespace allowed in the entire string
        // .{8,} # anything, at least eight places though
        // $ # end-of-string
        return password.matches("^.{6,16}$");
    }

    public static boolean validatePhone(String phone){
        return phone.matches("^\\d{10}$");
    }

    public static boolean validateProductQuantity(String integer) {
        return integer.matches("-?(0|[1-9]\\d*)");
    }

    public static boolean validateProductPrice(String productPrice) {
        return productPrice.matches("^[0-9]+(|\\.)[0-9]+$");
    }

    public static void alertBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.valueOf(infoMessage));
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}