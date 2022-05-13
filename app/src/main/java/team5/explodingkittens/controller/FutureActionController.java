package team5.explodingkittens.controller;

import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.userview.AbstractUserView;

import java.util.ArrayList;

public class FutureActionController extends ActionController {

    public FutureActionController(GameController gameController, AbstractUserView view, int playerId, Player player) {
        super(gameController, view, playerId, player);
    }

    public void seeTheFuture(int playerId) {
        if (playerId == this.playerId) {
            Card card0 = gameController.deck.getCard(0);
            Card card1 = gameController.deck.getCard(1);
            Card card2 = gameController.deck.getCard(2);
            view.seeTheFuture(card0, card1, card2);
        }
    }

    public void alterTheFuture(int playerId) {
        if(playerId == this.playerId){
            Card card0 = gameController.deck.getCard(0);
            Card card1 = gameController.deck.getCard(1);
            Card card2 = gameController.deck.getCard(2);
            ArrayList<Card> cards = view.alterTheFuture(card0, card1, card2);
            gameController.rearrangeTopThree(cards);
        }
    }
}
