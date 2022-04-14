package team5.explodingkittens.view;

import javafx.scene.Scene;
import team5.explodingkittens.controller.Observer;
import team5.explodingkittens.controller.notification.Notification;

public class SpectatorViewSceneHandler implements Observer {

    private Scene scene;

    public void replaceScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(Notification notification) {
        notification.applyNotification(this);
    }

    public void updateNames() {
        // TODO: implement
        System.out.println("Updating names...");
    }
}
