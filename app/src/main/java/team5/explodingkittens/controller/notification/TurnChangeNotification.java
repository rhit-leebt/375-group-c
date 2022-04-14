package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.SpectatorViewSceneHandler;

public class TurnChangeNotification implements Notification {
    private int playerId;

    public TurnChangeNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.changeUiOnTurnChange(playerId);
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
