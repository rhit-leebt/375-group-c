package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * A notification for stealing a card from a specific player and
 * giving it to another specific player.
 */
public class StealCardNotification implements Notification {
    public int toPlayerId;
    public int fromPlayerId;

    public StealCardNotification(int toPlayerId, int fromPlayerId) {
        this.toPlayerId = toPlayerId;
        this.fromPlayerId = fromPlayerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.stealCard(toPlayerId, fromPlayerId);
    }
}
