package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;

/**
 * Notification when an Alter The Future card is played.
 *
 * @author Maura Coriale
 */
public class NameChangeNotification implements Notification {

    @Override
    public void applyNotification(UserController userController) {
        // TODO: behavior?
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        sceneHandler.updateNames();
    }
}
