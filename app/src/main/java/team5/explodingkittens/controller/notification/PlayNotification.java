package team5.explodingkittens.controller.notification;

import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.view.spectatorview.SpectatorViewSceneHandler;

/**
 * A notification that a player played a card.
 */
public class PlayNotification implements Notification {
    public int playerId;
    public Card card;

    public PlayNotification(int playerId, Card card) {
        this.playerId = playerId;
        this.card = card;
    }

    @Override
    public void applyNotification(UserController userController) {
        userController.playCard(playerId, card);
    }

    @Override
    public void applyNotification(SpectatorViewSceneHandler sceneHandler) {
        // TODO: behavior?
    }
}
