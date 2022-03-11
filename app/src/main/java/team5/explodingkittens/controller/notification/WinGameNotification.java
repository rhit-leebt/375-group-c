package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * A notification for letting a player know they won.
 */
public class WinGameNotification implements Notification {
    public int playerId;

    public WinGameNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.winGame(playerId);
    }
}
