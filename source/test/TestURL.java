package source.test;

import java.net.URL;


public class TestURL {
    public static void main(String[] args) {


    }
    public void testResourcePaths() {
        // Kiểm tra đường dẫn tới tệp login.fxml
        URL loginFXMLUrl = getClass().getResource("source/view/login.fxml");
        if (loginFXMLUrl != null) {
            System.out.println("Đường dẫn tới tệp login.fxml: " + loginFXMLUrl.toString());
        } else {
            System.out.println("Không thể tìm thấy tệp login.fxml");
        }

        // Kiểm tra đường dẫn tới hình ảnh fav.png
        URL favImageUrl = getClass().getResource("source/view/resource/img/fav.png");
        if (favImageUrl != null) {
            System.out.println("Đường dẫn tới hình ảnh fav.png: " + favImageUrl.toString());
        } else {
            System.out.println("Không thể tìm thấy hình ảnh fav.png");
        }
    }
}
