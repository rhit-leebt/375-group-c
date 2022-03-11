package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * A notification to close the game.
 */
public class CloseGameNotification implements Notification {
    @Override
    public void applyNotification(UserController userController) {
        userController.closeGame();
    }
}
