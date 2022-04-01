package team5.explodingkittens.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import team5.explodingkittens.model.Card;

/**
 * The UI element that represents a card on screen.
 * Can be selected, which will cause it to change color.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class UiCard extends StackPane {
    public static final double CARD_WIDTH = 200;
    public static final double CARD_HEIGHT = 300;

    private static final String FXML_FILE_NAME = "UICard.fxml";
    private static final String CARD_BACK_PATH = "app" + File.separator
            + "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "images" + File.separator + "cards"
            + File.separator + "cardback.png";

    private boolean selected = false;

    @FXML
    protected Rectangle selectionMask;
    @FXML
    protected ImageView overlayImage;

    /**
     * Creates a UiCard object based upon the FXML setup at the file path.
     */
    public UiCard() {
        FXMLLoader.loadFXML(FXML_FILE_NAME, this);
    }

    /**
     * Sets the image to the provided card's image, or the card
     * back image when provided null.
     *
     * @param card The card to display on this UiElement
     */
    public void setCard(Card card) {
        if (card == null) {
            try {
                overlayImage.setImage(new Image(new FileInputStream(CARD_BACK_PATH)));
            } catch (FileNotFoundException e) {
                // TODO: Do something
            }
        } else {
            try {
                overlayImage.setImage(new Image(new FileInputStream(card.getImagePath())));
            } catch (FileNotFoundException e) {
                // TODO: Do something
            }
            overlayImage.setPreserveRatio(true);
            overlayImage.setFitWidth(200);
        }
    }

    /**
     *  Selects this card, by raising it and showing the mask.
     */
    public void select() {
        if (!selected) {
            setTranslateY(getTranslateY() - 30);
            selectionMask.setOpacity(0.3);
            selected = true;
        }
    }

    /**
     * Deselects this card, by lowering it and hiding the mask.
     */
    public void deselect() {
        if (selected) {
            setTranslateY(getTranslateY() + 30);
            selectionMask.setOpacity(0);
            selected = false;
        }
    }
}
