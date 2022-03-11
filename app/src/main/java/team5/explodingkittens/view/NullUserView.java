package team5.explodingkittens.view;

import java.util.ArrayList;
import java.util.Set;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;

/**
 * An empty shell of the UserView that does not perform any actions.
 *
 * @author Duncan McKee
 */
public class NullUserView implements AbstractUserView {
    @Override
    public void setName(int playerId, String name) {

    }

    @Override
    public void drawCard(int playerId, Card card) {

    }

    @Override
    public Card showFavorDialog(int toPlayerId, Player player) {
        return null;
    }

    @Override
    public void giveCard(int toPlayerId, int fromPlayerId, Card card) {

    }

    @Override
    public void discardCard(int playerId, Card card) {

    }

    @Override
    public void discardAllCards(int playerId) {

    }

    @Override
    public boolean showExplodeDialog() {
        return false;
    }

    @Override
    public void showCantDefuseDialog() {

    }

    @Override
    public int showPutExplodingKittenBackDialog() {
        return 0;
    }

    @Override
    public void winGame() {

    }

    @Override
    public void close() {

    }

    @Override
    public void setUserController(UserController userController) {

    }

    @Override
    public CardType showPickPair(Set<CardType> types) {
        return null;
    }

    @Override
    public int showPickOtherPlayer() {
        return 0;
    }

    @Override
    public void showCantPlayCat() {

    }

    @Override
    public boolean showNopePlay(int playerId, Card card) {
        return false;
    }

    @Override
    public void closeNope() {

    }

    @Override
    public void seeTheFuture(Card card0, Card card1, Card card2) {

    }

    @Override
    public ArrayList<Card> alterTheFuture(Card card0, Card card1, Card card2) {
        return null;
    }
}
