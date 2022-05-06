package team5.explodingkittens.controller;

import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.userview.AbstractUserView;

import java.util.Set;

public class CatActionController extends ActionController {

    public CatActionController(GameController gameController, AbstractUserView view, int playerId, Player player) {
        super(gameController, view, playerId, player);
    }


    public void catPickPair(Card card) {
        Set<CardType> types = player.findMatchingTypes(card.type);
        CardType pickedType = view.showPickPair(types);
        Card pickedCard = player.findCardOfTypeExcluding(pickedType, card);
        gameController.discardCard(pickedCard);
    }

    public void catStealCard(int playerId) {
        if (playerId == this.playerId) {
            int targetPlayerId = view.showPickOtherPlayer();
            gameController.stealCardFrom(this.playerId, targetPlayerId);
        }
    }
}
