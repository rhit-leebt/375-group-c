package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import team5.explodingkittens.model.Player;

import java.util.List;

public class SpectatorViewSceneBuilder {

    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private final SpectatorViewSceneHandler sceneHandler;

    public SpectatorViewSceneBuilder() {
        this.sceneHandler = new SpectatorViewSceneHandler();
    }

    public SpectatorViewSceneHandler generateSceneFromPlayerInfo(List<Player> players) {
        Scene scene = new Scene(new HBox(), DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        sceneHandler.replaceScene(scene);
        return sceneHandler;
    }
}
