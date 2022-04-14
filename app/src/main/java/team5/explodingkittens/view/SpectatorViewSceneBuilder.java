package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import team5.explodingkittens.model.Player;

import java.util.List;

public class SpectatorViewSceneBuilder {

    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private final SpectatorViewSceneHandler sceneHandler;
    private final List<Player> players;

    public SpectatorViewSceneBuilder(List<Player> players) {
        this.players = players;
        this.sceneHandler = new SpectatorViewSceneHandler();
    }

    public SpectatorViewSceneHandler generateSceneFromPlayerInfo() {
        VBox vBox = new VBox();
        addPlayerNamesToVBox(vBox);
        Scene scene = new Scene(vBox, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        sceneHandler.replaceScene(scene);
        return sceneHandler;
    }

    private void addPlayerNamesToVBox(VBox vBox) {
        for (Player player : players) {
            // As it turns out, at the time this view is built, names are not created yet
            // (dialog box appears after game initialization) hence the placeholders
            Label nameLabel = new Label("Currently choosing a name...");
            vBox.getChildren().add(nameLabel);
        }
    }
}
