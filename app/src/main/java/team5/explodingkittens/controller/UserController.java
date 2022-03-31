package team5.explodingkittens.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import team5.explodingkittens.controller.notification.ExplodeNotification;
import team5.explodingkittens.controller.notification.Notification;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.view.AbstractUserView;

/**
 * The main class that manages a singular player. It
 * receives updates from the {@link GameController},
 * and uses those to update its underlying data and UI.
 *
 * @author Duncan McKee, Andrew Orians, Maura Coriale
 */
public class UserController implements Observer {
    private GameController gameController;
    private final AbstractUserView view;
    private final int playerId;
    private final Player player;

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
    }

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
            if (player.hasNope()) {
                if (view.showNopePlay(playerId, card)) {
                    gameController.playCard(this.playerId, player.getNope());
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
        if (playerId == this.playerId) {
            player.removeAllCards();
            this.gameController.removeUser(playerId);
        }
        view.discardAllCards(playerId);
    }

    /**
     * Attepts to explode a player.
     * The player is prompted whether or not to play their defuse card if they have one.
     * They explode if one is not played
     *
     * @param playerId the player to try and explode
     */
    public void tryExplode(int playerId, Card explodingKitten) {
        if (playerId == this.playerId) {
            if (player.hasDefuse()) {
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

    public void tryPlayDefuseCard() {
        gameController.discardCard(player.getDefuse());
    }

    /**
     * Displays the select player popup if this is the player who
     * played a favor card.
     *
     * @param playerId The player who played the favor card
     */
    public void favorPickPlayer(int playerId) {
        if (playerId == this.playerId) {
            int fromPlayerId = view.showPickOtherPlayer();
            this.gameController.favorSelect(this.playerId, fromPlayerId);
        }
    }

    /**
     * Displays the select card popup if this is the player who is
     * losing a card.
     *
     * @param toPlayerId The player who is receiving a card
     * @param fromPlayerId The player who is giving a card
     */
    public void favorSelectCard(int toPlayerId, int fromPlayerId) {
        if (fromPlayerId == this.playerId) {
            Card cardToGive = view.showFavorDialog(toPlayerId, player);
            if (cardToGive != null) {
                gameController.giveCard(toPlayerId, fromPlayerId, cardToGive);
            }
        }
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
        Set<CardType> types = player.findMatchingTypes(card.type);
        CardType pickedType = view.showPickPair(types);
        Card pickedCard = player.findCardOfTypeExcluding(pickedType, card);
        gameController.discardCard(pickedCard);
    }

    /**
     * If this is the correct user then they will be prompted
     * to select a player to steal from.
     *
     * @param playerId The player who is stealing
     */
    public void catStealCard(int playerId) {
        if (playerId == this.playerId) {
            int targetPlayerId = view.showPickOtherPlayer();
            gameController.stealCardFrom(this.playerId, targetPlayerId);
        }
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
        if (currentPlayerId == playerId) {
            view.changeUiOnTurnChange(true);
        } else {
            view.changeUiOnTurnChange(false);
        }
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
