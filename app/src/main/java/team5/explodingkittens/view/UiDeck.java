package team5.explodingkittens.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import team5.explodingkittens.controller.ResourceController;

/**
 * The UI object that organizes a deck on screen.
 * It can be clicked to draw cards to the player's hand.
 *
 * @author Duncan McKee, Andrew Orians, Maura Coriale
 */
public class UiDeck extends VBox {
    private static final String DECK_LABEL = "deckDescription";
    private static final String FXML_FILE_PATH = "app"
            + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "fxml" + File.separator + "UIDeck.fxml";

    @FXML
    private Label deckLabel;

    /**
     * Creates a UiDeck object.
     */
    public UiDeck() {
        FXMLLoader.loadFXML(FXML_FILE_PATH, this);

        deckLabel.setText(ResourceController.getString(DECK_LABEL));
    }
}
