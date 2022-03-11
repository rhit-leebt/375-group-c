package team5.explodingkittens.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import team5.explodingkittens.model.Card;

/**
 * The UI object that organizes a discard pile on screen.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class UiDiscard extends StackPane {
    private static final String FXML_FILE_PATH = "app"
            + File.separator + "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "fxml" + File.separator + "UIDiscard.fxml";

    @FXML
    protected UiCard cardBack;
    private int cardsLeft;

    /**
     * Creates a UiDiscard element from the existing FXML file.
     */
    public UiDiscard() {
        URL fxmlUrl = null;
        try {
            fxmlUrl = new File(FXML_FILE_PATH).toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the discarding process that will be finished
     * in the discard EventHandler.
     *
     * @param card The card that is being discarded.
     * @return The card to be animated
     */
    public UiCard discardCard(Card card) {
        UiCard displayCard = new UiCard();
        displayCard.setCard(card);
        cardBack.getChildren().add(displayCard);
        return displayCard;
    }

    /**
     * Returns the EventHandler to run when the discard animation is finished.
     *
     * @param target The card is being animated, that will be destroyed.
     * @param card The card element to display on the discard pile at the end
     * @return An EventHandler for the animator
     */
    public EventHandler<ActionEvent> getDiscardAnimationHandler(UiCard target, Card card) {
        return event -> {
            cardBack.getChildren().remove(target);
            cardBack.setCard(card);
            cardsLeft++;
        };
    }
}
