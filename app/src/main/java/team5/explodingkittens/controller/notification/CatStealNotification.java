package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * A notification to request a user to select the target to steal from.
 */
public class CatStealNotification implements Notification {
    public int playerId;

    public CatStealNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.catStealCard(playerId);
    }
}
