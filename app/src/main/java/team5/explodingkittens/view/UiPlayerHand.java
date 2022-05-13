package team5.explodingkittens.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import team5.explodingkittens.controller.ResourceController;
import team5.explodingkittens.model.Card;

/**
 * UiPlayerHand stores the list of {@link UiHandCard} that
 * a player currently holds, and keeps track of which card
 * from those is selected.
 *
 * @author Duncan McKee, Maura Coriale, Andrew Orians
 */
public class UiPlayerHand extends StackPane implements UiPlayer {
    private static final double CARD_OVERLAP_WIDTH = 50;
    private static final String PLAYER_NO_NAME = "noNameEntered";

    private String name = null;
    private final List<UiHandCard> cards;
    private UiHandCard selectedCard;
    private final CardInfoPanel infoPanel;

    /**
     * Creates a UiPlayerHand with a provided player card popup.
     *
     * @param playHandler The EventHandler that will be triggered when a card is played.
     */
    public UiPlayerHand(EventHandler<ActionEvent> playHandler) {
        setPrefSize(UiCard.CARD_SIZE.width, UiCard.CARD_SIZE.height);
        cards = new ArrayList<>();
        setOnMouseExited(e -> resetHover());
        infoPanel = new CardInfoPanel(playHandler);
        getChildren().add(infoPanel);
    }

    private void resetHover() {
        if (selectedCard != null) {
            selectedCard.toFront();
        }
    }

    /**
     * Takes the list of cards that are in the player's hand
     * and aligns them horizontally.
     */
    public void alignCards() {
        this.resort();
        double centerIndex = ((double) cards.size()) / 2.0 + 1.5;
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setTranslateX((i - centerIndex) * CARD_OVERLAP_WIDTH);
        }
        infoPanel.setTranslateX((cards.size() - centerIndex) * CARD_OVERLAP_WIDTH + 200);
    }

    public void hoverCard(int index) {
        this.resort();
        for (int i = 0; i < index; i++) {
            cards.get(i).toFront();
        }
        for (int i = cards.size() - 1; i > index; i--) {
            cards.get(i).toFront();
        }
        cards.get(index).toFront();
    }

    private void selectCard(UiHandCard card) {
        if (selectedCard != null) {
            selectedCard.deselect();
            if (selectedCard == card) {
                selectedCard = null;
                infoPanel.clearCard();
                return;
            }
        }
        card.select();
        selectedCard = card;
        infoPanel.displayCard(selectedCard.getCard());
    }

    @Override
    public String getName() {
        if (name != null) {
            return name;
        }
        return ResourceController.getString(PLAYER_NO_NAME);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public UiCard drawCard(Card card) {
        int index = cards.size();
        UiHandCard cardUi = new UiHandCard(card);
        cardUi.setOnMouseClicked(e -> selectCard(cardUi));
        cardUi.setOnMouseEntered(e -> hoverCard(index));
        getChildren().add(cardUi);
        cards.add(cardUi);
        alignCards();
        return cardUi;
    }

    @Override
    public EventHandler<ActionEvent> getDrawAnimationHandler(UiCard target) {
        return event -> alignCards();
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void discardCard(Card card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCard().equals(card)) {
                if (cards.get(i) == selectedCard) {
                    infoPanel.clearCard();
                }
                getChildren().remove(cards.get(i));
                cards.remove(i);
                break;
            }
        }
        updateHoverIndices();
        alignCards();
    }

    @Override
    public void discardAllCards() {
        for (int i = cards.size() - 1; i >= 0; i--) {
            discardCard(cards.get(i).getCard());
        }
    }

    public Card getSelectedCard() {
        return selectedCard.getCard();
    }

    private void resort() {
        Collections.sort(cards);
        updateHoverIndices();
    }

    private void updateHoverIndices() {
        for (int i = 0; i < cards.size(); i++) {
            int index = i;
            cards.get(i).setOnMouseEntered(e -> hoverCard(index));
        }
    }

    public void selectCardByIndex(int index) {
        selectCard(this.cards.get(index));
    }

//    public void hoverCardByIndex(int index) {
//        hoverCard(this.cards.get(index));
//    }
}
