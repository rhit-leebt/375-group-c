package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * A notification for alerting a player to pick a player to take from.
 */
public class FavorPickPlayerNotification implements Notification {
    public int playerId;

    public FavorPickPlayerNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.favorPickPlayer(playerId);
    }
}
