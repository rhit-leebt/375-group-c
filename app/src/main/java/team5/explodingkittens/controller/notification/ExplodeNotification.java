package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;

/**
 * A notification for exploding a specific player.
 */
public class ExplodeNotification implements Notification {
    public int playerId;

    public ExplodeNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.explodePlayer(playerId);
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
