package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;

/**
 * A notification for alerting a player to select a card to give to another.
 */
public class FavorSelectCardNotification implements Notification {
    public int toPlayerId;
    public int fromPlayerId;

    public FavorSelectCardNotification(int toPlayerId, int fromPlayerId) {
        this.toPlayerId = toPlayerId;
        this.fromPlayerId = fromPlayerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.favorSelectCard(toPlayerId, fromPlayerId);
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
