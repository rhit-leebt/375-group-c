package team5.explodingkittens.view;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;

/**
 * The UI object that organizes a hand for another player on screen.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class UiOtherPlayer extends VBox implements UiPlayer {
    private static final String NAME_LOADING_TEXT = "nameLoading";
    private static final String CARDS_REMAINING_TEXT = "cardsRemaining";
    private static final String FXML_FILE_NAME = "UIOtherPlayer.fxml";

    private final int playerId;
    private String name = null;
    private int cardsInHand;

    @FXML
    private UiCard cardBack;
    @FXML
    private Label nameLabel;
    @FXML
    private Label cardsLabel;

    /**
     * Creates a UiOtherPlayer object from the FXML file.
     */
    public UiOtherPlayer(int playerId) {
        this.playerId = playerId;
        FXMLLoader.loadFXML(FXML_FILE_NAME, this);
      
        cardsInHand = 8;
        nameLabel.setText(ResourceController.getString(NAME_LOADING_TEXT));
        refresh();
    }

    private void refresh() {
        cardsLabel.setText(ResourceController.getString(CARDS_REMAINING_TEXT)
                + ": " + ResourceController.formatInteger(cardsInHand));
    }

    @Override
    public String getName() {
        if (name != null) {
            return name;
        }
        return ResourceController.formatInteger(playerId);
    }

    @Override
    public void setName(String name) {
        this.name = name;
        nameLabel.setText(name);
    }

    @Override
    public UiCard drawCard(Card card) {
        cardsInHand++;
        refresh();
        UiCard displayCard = new UiCard();
        cardBack.getChildren().add(displayCard);
        return displayCard;
    }

    @Override
    public EventHandler<ActionEvent> getDrawAnimationHandler(UiCard target) {
        return event -> cardBack.getChildren().remove(target);
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void discardCard(Card card) {
        cardsInHand--;
        refresh();
    }

    @Override
    public void discardAllCards() {
        cardsInHand = 0;
        refresh();
    }
}
