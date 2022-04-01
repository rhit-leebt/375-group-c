package team5.explodingkittens.view;

import java.util.ArrayList;
import java.util.Set;
import team5.explodingkittens.controller.UserController;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;

/**
 * The interface that bridges the gap between controller and view.
 *
 * @author Duncan McKee
 */
public interface AbstractUserView {
    void setName(int playerId, String name);

    void drawCard(int playerId, Card card);

    Card showFavorDialog(int toPlayerId, Player player);

    void giveCard(int toPlayerId, int fromPlayerId, Card card);

    void discardCard(int playerId, Card card);

    void discardAllCards(int playerId);

    boolean showExplodeDialog();

    void showCantDefuseDialog();

    int showPutExplodingKittenBackDialog();

    void winGame();

    void close();

    void setUserController(UserController userController);

    CardType showPickPair(Set<CardType> types);

    int showPickOtherPlayer();

    void showCantPlayCat();

    boolean showNopePlay(int playerId, Card card);

    void closeNope();

    void seeTheFuture(Card card0, Card card1, Card card2);

    ArrayList<Card> alterTheFuture(Card card0, Card card1, Card card2);

    void changeUiOnTurnChange(boolean currentTurnIsNow);
}
