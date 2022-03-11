package team5.explodingkittens.controller;

import java.util.ArrayList;
import java.util.List;

import team5.explodingkittens.controller.notification.*;
import team5.explodingkittens.model.Card;
import team5.explodingkittens.model.CardType;
import team5.explodingkittens.model.Deck;
import team5.explodingkittens.model.DiscardPile;
import team5.explodingkittens.model.Player;
import team5.explodingkittens.model.TurnState;
import team5.explodingkittens.view.AbstractUserView;
import team5.explodingkittens.view.UserViewFactory;

/**
 * The main class that manages the interactions between all users
 * with each other and with all other major game objects. Only
 * one instance of this class can exist.
 *
 * @author Duncan McKee, Maura Coriale, Andrew Orians
 */
public class GameController extends Subject {

    private TurnState state;
    protected Deck deck;
    protected DiscardPile discardPile;
    private Card contestedCard;
    private boolean beenNoped;
    private int wantToPlayAcknowledged;

    public GameController() {

    }

    /**
     * Testing constructor to allow for dependency injection.
     *
     * @param state       the TurnState object to use, usually mocked
     * @param discardPile the DiscardPile object to use, usually mocked
     */
    public GameController(TurnState state, DiscardPile discardPile) {
        this.state = state;
        this.discardPile = discardPile;
    }

    /**
     * Testing constructor to allow for dependency injection.
     *
     * @param state       the TurnState object to use, usually mocked
     * @param discardPile the DiscardPile object to use, usually mocked
     * @param deck        the Deck object to use, usually mocked
     */
    public GameController(TurnState state, DiscardPile discardPile, Deck deck) {
        this.state = state;
        this.discardPile = discardPile;
        this.deck = deck;
    }

    /**
     * Testing constructor to allow for dependency injection for the deck.
     *
     * @param deck the Deck object to use, usually mocked
     */
    public GameController(Deck deck) {
        this.deck = deck;
    }

    /**
     * This method starts the game with a given number of players.
     * This will create a new deck, discard pile, and each user.
     *
     * @param numPlayers The number of players who will be playing in the game.
     */
    public void startGame(int numPlayers, UserViewFactory userViewFactory) {
        if (numPlayers < 2) {
            throw new IllegalArgumentException("Must have more than two players to start game");
        }
        if (numPlayers > 10) {
            throw new IllegalArgumentException("Must have less than ten players to start game");
        }
        state = new TurnState(numPlayers);
        List<UserController> users = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            AbstractUserView userView = userViewFactory.createUserView(numPlayers, i);
            Player player = new Player();
            users.add(new UserController(this, userView, player, i));
            userView.setUserController(users.get(i));
        }
        this.deck = new Deck(numPlayers);
        deck.dealCards(users);
        this.discardPile = new DiscardPile();
    }

    /**
     * Removes a given player from the game.
     *
     * @param userId The player to remove from the game
     */
    public void removeUser(int userId) {
        state.invalidatePlayer(userId);
        if (state.isGameOver()) {
            notifyObservers(new WinGameNotification(state.findLowestValid()));
        }
    }

    public void closeGame() {
        notifyObservers(new CloseGameNotification());
    }

    public void setName(int playerId, String name) {
        notifyObservers(new SetNameNotification(playerId, name));
    }

    /**
     * The player with ID playerId is requesting to play a card
     * from their hand. If it is currently that player's turn then
     * the card will be played and put in the discard pile.
     *
     * @param playerId The ID of the player who is requesting to play a card.
     * @param card     The card that is attempting to be played.
     */
    public void playCard(int playerId, Card card) {
        if (contestedCard == null && state.getTurnPlayerId() == playerId) {
            contestedCard = card;
            beenNoped = false;
            wantToPlayAcknowledged = 1;
            notifyObservers(new PlayNotification(playerId, card));
            discardPile.discardCard(card);
        } else if (card.type == CardType.NOPE) {
            beenNoped = !beenNoped;
            wantToPlayAcknowledged = 1;
            notifyObservers(new CloseNopeNotification());
            notifyObservers(new PlayNotification(playerId, card));
            discardPile.discardCard(card);
        }
    }

    /**
     * A player can acknowledge if they accept the playing of the card.
     */
    public void acknowledgePlayCard() {
        wantToPlayAcknowledged++;
        if (wantToPlayAcknowledged == state.getValidCount()) {
            resolvePlayCard();
        }
    }

    /**
     * Resolves the card that is currently being noped.
     */
    private void resolvePlayCard() {
        if (!beenNoped) {
            contestedCard.playCard(this);
        }
        contestedCard = null;
    }

    /**
     * The player with ID playerId is requesting to draw a card
     * from the deck. If it is currently that player's turn then
     * the player will be given a card from the deck.
     *
     * @param playerId The ID of the player who is requesting a card.
     */
    public void drawCard(int playerId) {
        if (state.getTurnPlayerId() == playerId) {
            Card card;
            if (state.drawType == TurnState.DrawType.DRAW_FROM_TOP) {
                card = deck.draw();
            } else {
                card = deck.drawAtBottom();
            }
            if (card.checkForExplodingKitten()) {
                notifyObservers(new TryExplodeNotification(playerId, card));
            } else {
                notifyObservers(new DrawNotification(playerId, card));
            }
            state.drawCard();
        }
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public void drawFromTheBottom() {
        state.drawType = TurnState.DrawType.DRAW_FROM_BOTTOM;
    }

    public void skipAction() {
        state.skipAction();
    }

    public void attackAction() {
        state.attackAction();
    }

    public void startFavor() {
        notifyObservers(new FavorPickPlayerNotification(state.getTurnPlayerId()));
    }

    public void favorSelect(int toPlayerId, int fromPlayerId) {
        notifyObservers(new FavorSelectCardNotification(toPlayerId, fromPlayerId));
    }

    public void giveCard(int toPlayerId, int fromPlayerId, Card card) {
        notifyObservers(new GiveCardNotification(toPlayerId, fromPlayerId, card));
    }

    public void startCatCard() {
        notifyObservers(new CatStealNotification(state.getTurnPlayerId()));
    }

    public void stealCardFrom(int toPlayerId, int fromPlayerId) {
        notifyObservers(new StealCardNotification(toPlayerId, fromPlayerId));
    }

    public void discardCard(Card card) {
        discardPile.discardCard(card);
        notifyObservers(new DiscardNotification(state.getTurnPlayerId(), card));
    }

    /**
     * Adds the card back into the deck at the provided depth.
     * If it was going to be placed beyond the ends place it at the end.
     *
     * @param card The card to replace
     * @param depth The depth to put it at
     */
    public void addCard(Card card, int depth) {
        try {
            deck.insertCard(card, depth);
        } catch (IllegalArgumentException e) {
            if (depth < 0) {
                deck.insertCard(card, 0);
            } else {
                deck.insertCard(card, deck.getSize());
            }
        }
    }

    public List<Card> viewDiscard() {
        return discardPile.viewCards();
    }

    public Card takeFromDiscard(int depth) {
        return discardPile.takeCard(depth);
    }

    public void seeTheFuture() {
        notifyObservers(new SeeTheFutureNotification(state.getTurnPlayerId()));
    }

    public void alterTheFuture() {
        notifyObservers(new AlterTheFutureNotification(state.getTurnPlayerId()));
    }

    public void rearrangeTopThree(ArrayList<Card> cards){
        for(int i = 0; i < 3; i++){
            deck.draw();
        }
        for(Card card : cards){
            deck.insertCard(card, cards.indexOf(card));
        }
    }
}