package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * Notification that handles how SeeTheFuture cards work with the UserController.
 *
 * @author Maura Coriale
 */
public class SeeTheFutureNotification implements Notification {
    private int playerId;

    /**
     * Creates a notification to handle SeeTheFuture cards.
     *
     * @param playerId the id of the player who gets to see the future
     */
    public SeeTheFutureNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.seeTheFuture(playerId);
    }
}
