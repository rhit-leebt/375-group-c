package team5.explodingkittens.view;

import java.util.List;
import javafx.scene.Scene;

public class UserViewSceneHandler {

    public UiDeck deckUi;
    public UiDiscard discardUi;
    public List<UiPlayer> playerUis;
    public UiPlayerHand playerHandUi;
    public Scene scene;

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
}
