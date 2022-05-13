package team5.explodingkittens.controller;

import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.userview.AbstractUserView;

public class FavorActionController extends ActionController {

    public FavorActionController(GameController gameController, AbstractUserView view, int playerId, Player player) {
        super(gameController, view, playerId, player);
    }


    public void favorPickPlayer(int playerId) {
        if (playerId == this.playerId) {
            int fromPlayerId = view.showPickOtherPlayer();
            this.gameController.favorSelect(this.playerId, fromPlayerId);
        }
    }

    public void favorSelectCard(int toPlayerId, int fromPlayerId) {
        if (fromPlayerId == this.playerId) {
            Card cardToGive = view.showFavorDialog(toPlayerId, player);
            if (cardToGive != null) {
                gameController.giveCard(toPlayerId, fromPlayerId, cardToGive);
            }
        }
    }
}
