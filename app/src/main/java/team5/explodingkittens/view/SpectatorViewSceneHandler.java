package team5.explodingkittens.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import team5.explodingkittens.controller.Observer;
import team5.explodingkittens.controller.notification.Notification;
import team5.explodingkittens.model.Player;

import java.util.List;

public class SpectatorViewSceneHandler implements Observer {

    private Scene scene;
    private List<SpectatorViewSinglePlayerUI> singlePlayerUIs;

    public SpectatorViewSceneHandler(List<SpectatorViewSinglePlayerUI> singlePlayerUIs) {
        this.singlePlayerUIs = singlePlayerUIs;
    }

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
