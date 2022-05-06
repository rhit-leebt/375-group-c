package team5.explodingkittens.view.userview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import team5.explodingkittens.view.*;
import java.util.ArrayList;
import java.util.List;

public class UserViewSceneBuilder {

    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private static final double DECK_DISCARD_SPACING = 20;
    private static final double VERTICAL_SPACING = 10;
    private static final double OUTSIDE_PADDING = 25;

    private UiPlayerHand playerHandUi;
    private List<UiPlayer> playerUis;
    private final UserViewUIParts userViewUIParts;
    private UserViewEvents userViewEvents;

    public UserViewSceneBuilder(UserViewEvents userViewEvents) {
        this.userViewEvents = userViewEvents;
        this.userViewUIParts = new UserViewUIParts();
    }

    public UserViewSceneHandler generateSceneFromPlayerInfo(int numPlayers, int playerId) {
        populatePlayerUiList(numPlayers, playerId);

        HBox otherPlayerUiArea = new HBox();
        HBox pileUiArea = new HBox();

        buildOtherPlayerUiArea(otherPlayerUiArea, numPlayers, playerId);
        buildPileUiArea(pileUiArea);

        HBox alignedUiGroup = getAlignedUiGroup(List.of(otherPlayerUiArea, pileUiArea));
        Scene scene = generateSceneFromUiArea(alignedUiGroup);

        UserViewSceneHandler sceneHandler = new UserViewSceneHandler(userViewUIParts);
        sceneHandler.replaceScene(scene);

        sceneHandler.setDrawKeyHandler(userViewEvents.drawKeyHandler);

        return sceneHandler;
    }

    private void populatePlayerUiList(int numPlayers, int playerId) {
        playerUis = new ArrayList<>(numPlayers);
        playerHandUi = new UiPlayerHand(userViewEvents.playActionHandler);
        for (int i = 0; i < numPlayers; i++) {
            if (i == playerId) {
                playerUis.add(playerHandUi);
            } else {
                UiOtherPlayer otherPlayerUi = new UiOtherPlayer(i);
                playerUis.add(otherPlayerUi);
            }
        }

        userViewUIParts.playerUiList = playerUis;
        userViewUIParts.playerHandUi = playerHandUi;
    }

    private void buildOtherPlayerUiArea(HBox otherPlayerUiArea, int numPlayers, int playerId) {
        otherPlayerUiArea.setAlignment(Pos.CENTER);
        for (int i = 0; i < numPlayers; i++) {
            if (i != playerId) {
                UiOtherPlayer otherPlayerUi = (UiOtherPlayer) playerUis.get(i);
                otherPlayerUiArea.getChildren().add(otherPlayerUi);
            }
        }
    }

    private void buildPileUiArea(HBox pileUiArea) {
        pileUiArea.setSpacing(DECK_DISCARD_SPACING);
        UiDeck deckUi = new UiDeck();
        deckUi.setOnMouseClicked(userViewEvents.drawMouseHandler);
        UiDiscard discardUi = new UiDiscard();
        pileUiArea.getChildren().addAll(deckUi, discardUi);

        userViewUIParts.deckUi = deckUi;
        userViewUIParts.discardUi = discardUi;
    }

    private HBox getAlignedUiGroup(List<HBox> areas) {
        VBox verticalAlign = new VBox();
        verticalAlign.setSpacing(VERTICAL_SPACING);
        verticalAlign.getChildren().addAll(areas);
        verticalAlign.getChildren().add(playerHandUi);

        HBox horizontalAlign = new HBox();
        horizontalAlign.getChildren().add(verticalAlign);
        horizontalAlign.setPadding(new Insets(OUTSIDE_PADDING));
        horizontalAlign.setAlignment(Pos.CENTER);

        return horizontalAlign;
    }

    private Scene generateSceneFromUiArea(HBox uiArea) {
        return new Scene(uiArea, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
    }


}
