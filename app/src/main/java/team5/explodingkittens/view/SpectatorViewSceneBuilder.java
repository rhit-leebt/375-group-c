package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public class SpectatorViewSceneBuilder {

    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private final SpectatorViewSceneHandler sceneHandler;

    public SpectatorViewSceneBuilder() {
        this.sceneHandler = new SpectatorViewSceneHandler();
    }

    public SpectatorViewSceneHandler generateSceneFromPlayerInfo(int numPlayers) {
        Scene scene = new Scene(new HBox(), DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        sceneHandler.replaceScene(scene);
        return sceneHandler;
    }
}
