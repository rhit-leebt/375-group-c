package team5.explodingkittens.view.userview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.view.*;
import java.util.HashMap;
import java.util.Map;

public class UserViewSceneHandler {

    private final UserViewUIParts uiParts;
    private Scene scene;
    private final TranslateAnimator animator;
    private EventHandler<KeyEvent> drawKeyHandler;

    public UserViewSceneHandler(UserViewUIParts uiParts, Scene scene) {
        this.uiParts = uiParts;
        this.animator = new TranslateAnimator(1);
        this.scene = scene;
        assignKeyActions();
    }

    private void assignKeyActions() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode().isDigitKey()) {
                handleDigitKey(e);
            } else if (e.getCode() == KeyCode.SHIFT) {
                drawKeyHandler.handle(e);
            }
        });
    }

    private void handleDigitKey(KeyEvent event) {
        int index = Integer.parseInt(event.getText());
        if (index == 0) {
            index = 10;
        }
        index--;
        uiParts.playerHandUi.selectCardByIndex(index);
        uiParts.playerHandUi.hoverCard(index);
    }

    public void setDrawKeyHandler(EventHandler<KeyEvent> keyHandler) {
        this.drawKeyHandler = keyHandler;
    }

    public void setNameOfPlayerUi(int playerId, String name) {
        getPlayerUIWithId(playerId).setName(name);
    }

    public void drawCardAndAnimateAction(int playerId, Card card) {
        UiCard uiCard = getPlayerUIWithId(playerId).drawCard(card);
        EventHandler<ActionEvent> handler = getPlayerUIWithId(playerId).getDrawAnimationHandler(uiCard);
        animator.animate(uiCard, uiParts.deckUi, handler);
    }

    public void discardCardAndAnimateAction(int playerId, Card card) {
        getPlayerUIWithId(playerId).discardCard(card);
        UiCard uiCard = uiParts.discardUi.discardCard(card);
        EventHandler<ActionEvent> handler = uiParts.discardUi.getDiscardAnimationHandler(uiCard, card);
        animator.animate(uiCard, getPlayerUIWithId(playerId).getNode(), handler);
    }

    public void giveCardAndAnimateAction(int toPlayerId, int fromPlayerId, Card card) {
        UiCard uiCard = getPlayerUIWithId(toPlayerId).drawCard(card);
        getPlayerUIWithId(fromPlayerId).discardCard(card);
        EventHandler<ActionEvent> handler = getPlayerUIWithId(toPlayerId).getDrawAnimationHandler(uiCard);
        animator.animate(uiCard, getPlayerUIWithId(fromPlayerId).getNode(), handler);
    }

    public Scene getScene() {
        return scene;
    }

    public UiPlayer getPlayerUIWithId(int id) {
        return uiParts.playerUiList.get(id);
    }

    public Card getSelectedCard() {
        return uiParts.playerHandUi.getSelectedCard();
    }

    public Map<String, Integer> getPlayerInfoMapFromUi() {
        Map<String, Integer> namesToId = new HashMap<>();
        for (int i = 0; i < uiParts.playerUiList.size(); i++) {
            if (getPlayerUIWithId(i) != uiParts.playerHandUi) {
                String name = getPlayerUIWithId(i).getName();
                namesToId.put(name, i);
            }
        }
        return namesToId;
    }
}
