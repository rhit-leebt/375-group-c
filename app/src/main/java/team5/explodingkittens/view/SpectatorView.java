package team5.explodingkittens.view;

import javafx.stage.Stage;
import team5.explodingkittens.model.Player;

import java.util.List;

public class SpectatorView extends Stage {

    private final SpectatorViewSceneHandler sceneHandler;

    public SpectatorView(List<Player> players) {
        SpectatorViewSceneBuilder builder = new SpectatorViewSceneBuilder(players);
        sceneHandler = builder.generateSceneFromPlayerInfo();
        setScene(sceneHandler.getScene());
        show();
    }
}
