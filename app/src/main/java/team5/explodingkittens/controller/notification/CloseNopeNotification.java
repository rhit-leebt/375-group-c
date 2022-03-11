package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

/**
 * A notification to close any open nope dialogs.
 */
public class CloseNopeNotification implements Notification {
    @Override
    public void applyNotification(UserController userController) {
        userController.closeNope();
    }
}
