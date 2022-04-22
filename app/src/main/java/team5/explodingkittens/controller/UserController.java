package team5.explodingkittens.controller;

import team5.explodingkittens.controller.notification.Notification;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.AbstractUserView;

import java.util.Random;

/**
 * The main class that manages a singular player. It
 * receives updates from the {@link GameController},
 * and uses those to update its underlying data and UI.
 *
 * @author Duncan McKee, Andrew Orians, Maura Coriale
 */
public class UserController implements Observer {
    private final GameController gameController;
    private final AbstractUserView view;
    private final int playerId;
    private final Player player;
    private ExplodeActionController explodeActionController;
    private CatActionController catActionController;
    private FavorActionController favorActionController;
    private FutureActionController futureActionController;

    /**
     * Creates a UserController object with the provided details.
     * Also registers this UserController object to the GameController.
     *
     * @param view     The view to push update to.
     * @param player   The underlying player model that represents the hand.
     * @param playerId The ID of this player.
     */
    public UserController(GameController gameController,
                          AbstractUserView view, Player player, int playerId) {
        this.gameController = gameController;
        this.gameController.registerObserver(this);
        this.view = view;
        this.playerId = playerId;
        this.player = player;
        this.initControllers();
    }

    private void initControllers() {
        this.explodeActionController = new ExplodeActionController(this.gameController, this.view, this.playerId, this.player);
        this.catActionController = new CatActionController(this.gameController, this.view, this.playerId, this.player);
        this.favorActionController = new FavorActionController(this.gameController, this.view, this.playerId, this.player);
        this.futureActionController = new FutureActionController(this.gameController, this.view, this.playerId, this.player);
    }

    // This method seems to be ONLY used for dependency injection
    // I believe "trySetName" is the method actually used in production
    public void setName(int playerId, String name) {
        view.setName(playerId, name);
    }

    public void drawCard(Card card) {
        player.addCard(card);
        view.drawCard(playerId, card);
    }

    /**
     * Alerts the player model and player ui to a card that has been drawn.
     *
     * @param playerId The ID of the player that drew the card.
     * @param card     The card that has been drawn.
     */
    public void drawCard(int playerId, Card card) {
        if (playerId == this.playerId) {
            player.addCard(card);
        }
        view.drawCard(playerId, card);
    }

    /**
     * Alerts the player model and player ui to a card that has been played.
     *
     * @param playerId The ID of the player that played the card.
     * @param card     The card that has been played.
     */
    public void playCard(int playerId, Card card) {
        view.discardCard(playerId, card);
        if (playerId == this.playerId) {
            player.removeCard(card);
        } else {
            if (player.hasCardType(CardType.NOPE)) {
                if (view.showNopePlay(playerId, card)) {
                    gameController.playCard(this.playerId, player.getCardType(CardType.NOPE));
                } else {
                    gameController.acknowledgePlayCard();
                }
            } else {
                gameController.acknowledgePlayCard();
            }
        }
    }

    public void closeNope() {
        view.closeNope();
    }

    /**
     * Discards the provided card without playing it.
     *
     * @param playerId The player who is discarding the card
     * @param card The card to discard
     */
    public void discardCard(int playerId, Card card) {
        view.discardCard(playerId, card);
        if (playerId == this.playerId) {
            player.removeCard(card);
        }
    }

    /**
     * Removes all of a player's cards and notifies the GameController they have exploded.
     *
     * @param playerId the player who exploded
     */
    public void explodePlayer(int playerId) {
        this.explodeActionController.explodePlayer(playerId);
    }

    /**
     * Attempts to explode a player.
     * The player is prompted whether to play their defuse card if they have one.
     * They explode if one is not played
     *
     * @param playerId the player to try and explode
     */
    public void tryExplode(int playerId, Card explodingKitten) {
        this.explodeActionController.tryExplode(playerId, explodingKitten);
    }

    /**
     * Displays the select player popup if this is the player who
     * played a favor card.
     *
     * @param playerId The player who played the favor card
     */
    public void favorPickPlayer(int playerId) {
        this.favorActionController.favorPickPlayer(playerId);
    }

    /**
     * Displays the select card popup if this is the player who is
     * losing a card.
     *
     * @param toPlayerId The player who is receiving a card
     * @param fromPlayerId The player who is giving a card
     */
    public void favorSelectCard(int toPlayerId, int fromPlayerId) {
        this.favorActionController.favorSelectCard(toPlayerId, fromPlayerId);
    }

    /**
     * Removes and adds card to give a card from one player to another,
     * also tells the UI to display this change.
     *
     * @param toPlayerId   The player receiving the card
     * @param fromPlayerId The player giving the card
     * @param card         The card
     */
    public void giveCard(int toPlayerId, int fromPlayerId, Card card) {
        if (fromPlayerId == this.playerId) {
            player.removeCard(card);
        }
        if (toPlayerId == this.playerId) {
            player.addCard(card);
        }
        view.giveCard(toPlayerId, fromPlayerId, card);
    }

    /**
     * Requests the view to create dialogs to select cards
     * to be played to match this cat card.
     *
     * @param card The card to match
     */
    public void catPickPair(Card card) {
        this.catActionController.catPickPair(card);
    }

    /**
     * If this is the correct user then they will be prompted
     * to select a player to steal from.
     *
     * @param playerId The player who is stealing
     */
    public void catStealCard(int playerId) {
        this.catActionController.catStealCard(playerId);
    }

    /**
     * Displays the win game popup if this is the user that won.
     *
     * @param playerId The user that won
     */
    public void winGame(int playerId) {
        if (playerId == this.playerId) {
            view.winGame();
        }
    }

    public void closeGame() {
        view.close();
    }

    public void trySetName(String name) {
        this.player.setName(name);
        this.gameController.setName(playerId, name);
    }

    /**
     * Try to play a card. If it is a cat card make sure
     * there is a second matching card to play.
     *
     * @param card The card to play
     */
    public void tryPlayCard(Card card) {
        if (card.checkForCatCard()) {
            if (player.hasMatchingPair(card)) {
                catPickPair(card);
                this.gameController.playCard(playerId, card);
            } else {
                view.showCantPlayCat();
            }
        } else {
            this.gameController.playCard(playerId, card);
        }
    }

    public void tryDrawCard() {
        this.gameController.drawCard(playerId);
    }

    public void changeUiOnTurnChange(int currentPlayerId) {
        view.changeUiOnTurnChange(currentPlayerId == playerId);
    }

    /**
     * Requests the game controller to send a random card from one player to another.
     *
     * @param toPlayerId The player who will receive the card
     * @param fromPlayerId The player who is sending the card
     */
    public void stealCard(int toPlayerId, int fromPlayerId) {
        if (fromPlayerId == this.playerId) {
            Card card = player.getRandomCard(new Random());
            gameController.giveCard(toPlayerId, fromPlayerId, card);
        }
    }

    public void tryCloseGame() {
        this.gameController.closeGame();
    }

    @Override
    public void update(Notification notification) {
        notification.applyNotification(this);
    }

    /**
     * Lets the player see the next three cards.
     *
     * @param playerId the player who's allowed to see ahead
     */
    public void seeTheFuture(int playerId) {
        this.futureActionController.seeTheFuture(playerId);
    }

    public void alterTheFuture(int playerId) {
        this.futureActionController.alterTheFuture(playerId);
    }
}
