package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.cardaction.AlterTheFutureAction;

/**
 * Notification when an Alter The Future card is played.
 *
 * @author Maura Coriale
 */
public class AlterTheFutureNotification implements Notification {
    private int playerId;

    /**
     * Constructor for AlterTheFutureNotification.
     * @param playerId takes the player who is allowed to alter the future.
     */
    public AlterTheFutureNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.alterTheFuture(this.playerId);
    }
}