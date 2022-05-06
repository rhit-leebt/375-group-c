package team5.explodingkittens.view.spectatorview;

import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import team5.explodingkittens.model.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorViewSceneBuilder {

    private static final double DEFAULT_SCREEN_WIDTH = 1000;
    private static final double DEFAULT_SCREEN_HEIGHT = 1000;
    private final List<Player> players;
    private final List<SpectatorViewSinglePlayerUI> playerInfoUIs = new ArrayList<>();

    public SpectatorViewSceneBuilder(List<Player> players) {
        this.players = players;
    }

    public SpectatorViewSceneHandler generateSceneFromPlayerInfo() {
        GridPane playerInfoGrid = generatePlayerInfoUIGrid();
        SpectatorViewSceneHandler sceneHandler = new SpectatorViewSceneHandler(playerInfoUIs);
        Scene scene = new Scene(playerInfoGrid, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        sceneHandler.replaceScene(scene);
        return sceneHandler;
    }

    private GridPane generatePlayerInfoUIGrid() {
        GridPane playerInfoGrid = new GridPane();

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d / 3);
        for (int i = 0; i < 3; i++) {
            playerInfoGrid.getRowConstraints().add(rc);
        }

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100d / 4);
        for (int i = 0; i < 4; i++) {
            playerInfoGrid.getColumnConstraints().add(cc);
        }

        int rowIndex = 0;
        int colIndex = 0;
        for (Player player : players) {
            SpectatorViewSinglePlayerUI playerInfoGui = new SpectatorViewSinglePlayerUI(player);
            playerInfoUIs.add(playerInfoGui);
            playerInfoGrid.add(playerInfoGui.getMainPane(), colIndex, rowIndex);
            colIndex++;
            if (colIndex > 3) {
                rowIndex++;
                colIndex = 0;
            }
        }

        playerInfoGrid.setHgap(20);
        playerInfoGrid.setVgap(10);

        return playerInfoGrid;
    }

}