package team5.explodingkittens.view;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;

/**
 * The UI object that organizes a discard pile on screen.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class UiDiscard extends VBox {
    private static final String DISCARD_LABEL = "discardDescription";
    private static final String FXML_FILE_NAME = "UIDiscard.fxml";

    @FXML
    protected UiCard cardBack;
    @FXML
    private Label discardLabel;
    private int cardsLeft;

    /**
     * Creates a UiDiscard element from the existing FXML file.
     */
    public UiDiscard() {
        FXMLLoader.loadFXML(FXML_FILE_NAME, this);

        discardLabel.setText(ResourceController.getString(DISCARD_LABEL));
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
