package team5.explodingkittens.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FXMLLoader {
    private static String FXML_BASE_PATH = "fxml/";

    public static void loadFXML(String filename, Object controller) {
        URL fxmlUrl;
        try {
            fxmlUrl = FXMLLoader.class.getClassLoader().getResource(FXML_BASE_PATH + filename);
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
