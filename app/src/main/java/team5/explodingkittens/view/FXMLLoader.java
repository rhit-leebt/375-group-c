package team5.explodingkittens.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FXMLLoader {

    public static void loadFXML(String path, Object controller) {
        URL fxmlUrl;
        try {
            fxmlUrl = new File(path).toURI().toURL();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(fxmlUrl);
            fxmlLoader.setRoot(controller);
            fxmlLoader.setController(controller);
            fxmlLoader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
