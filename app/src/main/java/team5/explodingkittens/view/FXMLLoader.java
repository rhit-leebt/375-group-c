package team5.explodingkittens.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FXMLLoader {
    // For use with jar-compatible I/O
    // private static String FXML_BASE_PATH = "fxml/";
    private static String FXML_BASE_PATH = "app"
            + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "fxml" + File.separator;

    public static void loadFXML(String filename, Object controller) {
        URL fxmlUrl;
        try {
            // For use with jar-compatible I/O
            // fxmlUrl = FXMLLoader.class.getClassLoader().getResource(FXML_BASE_PATH + filename);
            fxmlUrl = new File(FXML_BASE_PATH + filename).toURI().toURL();
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
