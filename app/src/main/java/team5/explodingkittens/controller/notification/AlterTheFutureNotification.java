package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.SpectatorViewSceneHandler;

/**
 * Notification when an Alter The Future card is played.
 *
 * @author Maura Coriale
 */
public class AlterTheFutureNotification implements Notification {
    private final int playerId;

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

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
