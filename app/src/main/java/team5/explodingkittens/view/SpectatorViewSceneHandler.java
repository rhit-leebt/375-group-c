package team5.explodingkittens.view;

import javafx.scene.Scene;

public class SpectatorViewSceneHandler {

    private Scene scene;

    public void replaceScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }
}
