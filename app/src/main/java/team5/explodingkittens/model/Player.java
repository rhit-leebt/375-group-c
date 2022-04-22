package team5.explodingkittens.model;

import team5.explodingkittens.controller.Observer;
import team5.explodingkittens.controller.notification.NameChangeNotification;
import team5.explodingkittens.controller.notification.Notification;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A structuring class between {@link Card} and
 * {@link team5.explodingkittens.controller.UserController},
 * which keeps track of the cards a user has in their hand.
 *
 * @author Andrew Orians, Duncan McKee
 */
public class Player {
    public static final int HAND_SIZE = 8;
    protected List<Observer> observers;

    ArrayList<Card> hand;
    private String name;

    public Player() {
        this.hand = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public int size() {
        return hand.size();
    }

    /**
     * Find a card a given index in the player's hand.
     *
     * @param index The index at which the card will be found.
     * @return A card that was located in the player's hand.
     */
    public Card getCard(int index) {
        if (this.hand.size() == 0) {
            throw new IllegalStateException("Cannot get from an empty hand");
        }
        if (index < 0 || index >= this.hand.size()) {
            throw new IllegalArgumentException("Index is out of range of the size of the hand");
        }
        return this.hand.get(index);
    }

    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    /**
     * Removes all of a player's cards.
     * For when they explode.
     */
    public void removeAllCards() {
        for (int i = hand.size() - 1; i >= 0; i--) {
            hand.remove(i);
        }
    }

    /**
     * Determines whether the payer has a specific card to play.
     */
    public boolean hasCardType(CardType type) {
        for (Card card : hand) {
            if (card.checkForCardType(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a player's card of a specific type, if they have one.
     *
     * @return their defuse card
     */
    public Card getCardType(CardType type) {
        for (Card card : hand) {
            if (card.checkForCardType(type)) {
                return card;
            }
        }
        throw new IllegalStateException("This player does not have that card");
    }

    /**
     * Determines if the card has a matching pair in the hand.
     *
     * @param card A card to match
     * @return If there is a pair that can be played
     */
    public boolean hasMatchingPair(Card card) {
        int count = 0;
        for (Card compareCard : hand) {
            if (card.isSameCatTypeAs(compareCard)) {
                count++;
            }
        }
        return count >= 2;
    }

    /**
     * Finds all types that can be played with the given card.
     *
     * @param type A card to find matches for
     * @return All matching types
     */
    public Set<CardType> findMatchingTypes(CardType type) {
        Set<CardType> types = new HashSet<>();
        for (Card compareCard : hand) {
            if (compareCard.checkForCatCard()
                    && (compareCard.type == type
                    || compareCard.type == CardType.FERAL_CAT
                    || type == CardType.FERAL_CAT)) {
                types.add(compareCard.type);
            }
        }
        return types;
    }

    /**
     * Finds a card that matches the specified type.
     *
     * @param type    The type to match
     * @param exclude A card to not match
     * @return The first matching card
     */
    public Card findCardOfTypeExcluding(CardType type, Card exclude) {
        for (Card compareCard : hand) {
            if (compareCard.type == type && compareCard != exclude) {
                return compareCard;
            }
        }
        return null;
    }

    public Card getRandomCard(Random random) {
        return hand.get(random.nextInt(hand.size()));
    }

    public void setName(String name) {
        this.name = name;
        notifyObservers(new NameChangeNotification());
    }

    public String getName() {
        return this.name;
    }

    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Cannot pass a null observer");
        }
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        if (observers.size() < 1) {
            throw new IllegalStateException("Can't remove an observer when none are registered");
        }
        if (!observers.contains(observer)) {
            throw new IllegalArgumentException("This observer is not registered with this subject");
        }
        observers.remove(observer);
    }

    public void notifyObservers(Notification notification) {
        for (Observer observer : this.observers) {
            observer.update(notification);
        }
    }

    public List<Card> getHand() {
        return this.hand;
    }
}
