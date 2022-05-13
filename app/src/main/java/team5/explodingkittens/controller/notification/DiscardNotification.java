package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;

/**
 * A notification of a player discarding a card.
 */
public class DiscardNotification implements Notification {
    public int playerId;
    public Card card;

    public DiscardNotification(int playerId, Card card) {
        this.playerId = playerId;
        this.card = card;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.discardCard(playerId, card);
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
