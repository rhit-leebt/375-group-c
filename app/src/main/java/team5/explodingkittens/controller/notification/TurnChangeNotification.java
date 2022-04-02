package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;

public class TurnChangeNotification implements Notification {
    private int playerId;

    public TurnChangeNotification(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.changeUiOnTurnChange(playerId);
    }
}
