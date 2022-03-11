package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;

/**
 * A notification for giving a card from one player to another.
 */
public class GiveCardNotification implements Notification {
    public int toPlayerId;
    public int fromPlayerId;
    public Card card;

    /**
     * Creates a favor give notification.
     *
     * @param toPlayerId The player who will receive the card
     * @param fromPlayerId The player who is giving up a card
     * @param card The card
     */
    public GiveCardNotification(int toPlayerId, int fromPlayerId, Card card) {
        this.toPlayerId = toPlayerId;
        this.fromPlayerId = fromPlayerId;
        this.card = card;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.giveCard(toPlayerId, fromPlayerId, card);
    }
}
