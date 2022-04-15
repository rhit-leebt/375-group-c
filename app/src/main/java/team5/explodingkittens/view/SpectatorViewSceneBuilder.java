package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import team5.explodingkittens.model.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorViewSceneBuilder {

    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private final List<Player> players;

    public SpectatorViewSceneBuilder(List<Player> players) {
        this.players = players;
    }

    public SpectatorViewSceneHandler generateSceneFromPlayerInfo() {
        VBox vBox = new VBox();
        List<Label> nameLabels = addPlayerNameLabelsToVBox(vBox);
        SpectatorViewSceneHandler sceneHandler = new SpectatorViewSceneHandler(players, nameLabels);
        Scene scene = new Scene(vBox, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        sceneHandler.replaceScene(scene);
        return sceneHandler;
    }

    private List<Label> addPlayerNameLabelsToVBox(VBox vBox) {
        List<Label> nameLabels = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            // As it turns out, at the time this view is built, names are not created yet
            // (dialog box appears after game initialization) hence the placeholders
            Label nameLabel = new Label();
            nameLabels.add(nameLabel);
            vBox.getChildren().add(nameLabel);
        }
        return nameLabels;
    }
}
