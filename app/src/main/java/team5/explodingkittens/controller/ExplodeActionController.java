package team5.explodingkittens.controller;

import team5.explodingkittens.controller.notification.ExplodeNotification;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.userview.AbstractUserView;

public class ExplodeActionController extends ActionController {

    public ExplodeActionController(GameController gameController, AbstractUserView view, int playerId, Player player) {
        super(gameController, view, playerId, player);
    }

    public void explodePlayer(int playerId) {
        if (playerId == this.playerId) {
            player.removeAllCards();
            this.gameController.removeUser(playerId);
        }
        view.discardAllCards(playerId);
    }

    public void tryExplode(int playerId, Card explodingKitten) {
        if (playerId == this.playerId) {
            if (player.hasCardType(CardType.DEFUSE)) {
                if (view.showExplodeDialog()) {
                    int depth = view.showPutExplodingKittenBackDialog();
                    this.gameController.addCard(explodingKitten, depth);
                    tryPlayDefuseCard();
                } else {
                    this.gameController.notifyObservers(
                            new ExplodeNotification(playerId));
                }
            } else {
                view.showCantDefuseDialog();
                this.gameController.notifyObservers(
                        new ExplodeNotification(playerId));
            }
        }
    }

    private void tryPlayDefuseCard() {
        gameController.discardCard(player.getCardType(CardType.DEFUSE));
    }
}
