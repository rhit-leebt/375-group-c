package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.view.SpectatorViewSceneHandler;

/**
 * An interface for all notification types.
 *
 * @author Duncan McKee
 */
public interface Notification {
    void applyNotification(UserController userController);
    
    // TODO: expand this to all scene handlers
    void applyNotification(SpectatorViewSceneHandler sceneHandler);
}
