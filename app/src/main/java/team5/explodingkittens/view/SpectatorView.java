package team5.explodingkittens.view;

import javafx.stage.Stage;

public class SpectatorView extends Stage {

    private final SpectatorViewSceneHandler sceneHandler;

    public SpectatorView(int numPlayers) {
        SpectatorViewSceneBuilder builder = new SpectatorViewSceneBuilder();
        sceneHandler = builder.generateSceneFromPlayerInfo(numPlayers);
        setScene(sceneHandler.getScene());
        show();
    }
}
