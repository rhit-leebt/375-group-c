package team5.explodingkittens.view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import team5.explodingkittens.model.Card;

public class UserViewSceneHandler {

    public UiDeck deckUi;
    public UiDiscard discardUi;
    public List<UiPlayer> playerUis;
    public UiPlayerHand playerHandUi;
    private Scene scene;
    private final TranslateAnimator animator;

    public UserViewSceneHandler() {
        animator = new TranslateAnimator(1);
    }

    public void setDeckUi(UiDeck deckUi) {
        this.deckUi = deckUi;
    }

    public void setDiscardUi(UiDiscard discardUi) {
        this.discardUi = discardUi;
    }

    public void setPlayerUiList(List<UiPlayer> playerUiList) {
        this.playerUis = playerUiList;
    }

    public void setUiPlayerHand(UiPlayerHand playerHandUi) {
        this.playerHandUi = playerHandUi;
    }

    public void replaceScene(Scene scene) {
        this.scene = scene;
    }

    public void setNameOfPlayerUi(int playerId, String name) {
        playerUis.get(playerId).setName(name);
    }

    public void drawCardAndAnimateAction(int playerId, Card card) {
        UiCard uiCard = playerUis.get(playerId).drawCard(card);
        EventHandler<ActionEvent> handler = playerUis.get(playerId).getDrawAnimationHandler(uiCard);
        animator.animate(uiCard, deckUi, handler);
    }

    public void discardCardAndAnimateAction(int playerId, Card card) {
        playerUis.get(playerId).discardCard(card);
        UiCard uiCard = discardUi.discardCard(card);
        EventHandler<ActionEvent> handler = discardUi.getDiscardAnimationHandler(uiCard, card);
        animator.animate(uiCard, playerUis.get(playerId).getNode(), handler);
    }

    public void giveCardAndAnimateAction(int toPlayerId, int fromPlayerId, Card card) {
        UiCard uiCard = playerUis.get(toPlayerId).drawCard(card);
        playerUis.get(fromPlayerId).discardCard(card);
        EventHandler<ActionEvent> handler = playerUis.get(toPlayerId).getDrawAnimationHandler(uiCard);
        animator.animate(uiCard, playerUis.get(fromPlayerId).getNode(), handler);
    }

    public Scene getScene() {
        return scene;
    }
}
