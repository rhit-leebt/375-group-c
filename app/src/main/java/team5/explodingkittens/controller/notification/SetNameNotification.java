package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;

/**
 * A notification for alerting all players to the name of a player.
 */
public class SetNameNotification implements Notification {
    public int playerId;
    public String name;

    public SetNameNotification(int playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.setName(playerId, name);
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
