package team5.explodingkittens.view.spectatorview;

import javafx.scene.Scene;
import team5.explodingkittens.controller.Observer;
import team5.explodingkittens.controller.notification.Notification;

import java.util.List;

public class SpectatorViewSceneHandler implements Observer {

    private final Scene scene;
    private final List<SpectatorViewSinglePlayerUI> singlePlayerUIs;

    public SpectatorViewSceneHandler(List<SpectatorViewSinglePlayerUI> singlePlayerUIs, Scene scene) {
        this.scene = scene;
        this.singlePlayerUIs = singlePlayerUIs;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void update(Notification notification) {
        notification.applyNotification(this);
    }

    public void updateNames() {
        for (SpectatorViewSinglePlayerUI singlePlayerUI : singlePlayerUIs) {
            singlePlayerUI.updateName();
        }
    }

    public void updateHands() {
        for (SpectatorViewSinglePlayerUI singlePlayerUI : singlePlayerUIs) {
            singlePlayerUI.updateHand();
        }
    }
}
