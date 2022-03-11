package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;

/**
 * A notification for trying to exploding a specific player.
 */
public class TryExplodeNotification implements Notification {
    public int playerId;
    public Card card;

    public TryExplodeNotification(int playerId, Card card) {
        this.playerId = playerId;
        this.card = card;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.tryExplode(playerId, card);
    }
}
