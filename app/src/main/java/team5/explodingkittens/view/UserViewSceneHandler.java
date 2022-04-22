package team5.explodingkittens.view;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import team5.explodingkittens.model.Card;

public class UserViewSceneHandler {

    public UiDeck deckUi;
    public UiDiscard discardUi;
    public List<UiPlayer> playerUis;
    public UiPlayerHand playerHandUi;
    public Scene scene;
    private final TranslateAnimator animator;
    private EventHandler<KeyEvent> spaceEvent = null;

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
        this.playerHandUi.alignCards();
    }

    public void replaceScene(Scene scene) {
        this.scene = scene;
        assignKeyActions();
    }

    private void assignKeyActions() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().isDigitKey()) {
                    int index = Integer.parseInt(event.getText());
                    if (index == 0) {
                        index = 10;
                    }
                    index--;
                    playerHandUi.selectCardByIndex(index);
                    playerHandUi.hoverCard(index);
                } else if (event.getCode() == KeyCode.SPACE) {
                    spaceEvent.handle(event);
                }
            }
        });
    }

    public void setSpaceBar(EventHandler<KeyEvent> spaceEvent) {
        this.spaceEvent = spaceEvent;
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
