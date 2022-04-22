package team5.explodingkittens.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import team5.explodingkittens.controller.UserController;

/**
 * A storage location for {@link Card}, where an order will be
 * maintained, so that card drawing can be performed.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class Deck {
    private final List<Card> drawPile;
    public Random random = new Random();

    /**
     * Given a number of players the deck will be created
     * with the correct ratio of {@link Card} from each {@link CardType}.
     *
     * @param numPlayers The number of players who will be playing the game.
     */
    public Deck(int numPlayers) {
        if (numPlayers < 2) {
            throw new IllegalArgumentException(
                    "Number of players should be greater than or equal to 2");
        } else if (numPlayers > 10) {
            throw new IllegalArgumentException(
                    "Number of players should be less than or equal to 10");
        }
        drawPile = new ArrayList<>();

        for (CardType type : CardType.values()) {
            if (type.preload) {
                for (int i = 0; i < type.getCount(numPlayers); i++) {
                    drawPile.add(new Card(type));
                }
            }
        }
    }

    /**
     * Given a list of users the deck will distribute the starting cards to all players.
     * During this process at least one {@link CardType#DEFUSE} card will be given to
     * each player. Additionally, the {@link CardType#EXPLODING_KITTEN} cards will be
     * added to the deck, and the deck will be {@link Deck#shuffle()}.
     *
     * @param users A list of uses who will be provided their starting hands.
     */
    public void dealCards(List<UserController> users) {
        for (UserController userController : users) {
            userController.drawCard((new Card(CardType.DEFUSE)));
        }
        for (int i = users.size(); i < CardType.DEFUSE.getCount(users.size()); i++) {
            drawPile.add(new Card(CardType.DEFUSE));
        }
        shuffle();
        for (int i = 0; i < Player.HAND_SIZE - 1; i++) {
            for (UserController user : users) {
                user.drawCard(draw());
            }
        }
        for (int i = 0; i < users.size() - 1; i++) {
            drawPile.add(new Card(CardType.EXPLODING_KITTEN));
        }
        shuffle();
    }

    public int getSize() {
        return drawPile.size();
    }

    public Card getCard(int depth) {
        return drawPile.get(drawPile.size() - 1 - depth);
    }

    /**
     * Take the top card from the deck, remove it from the deck,
     * and return it to the caller. Used for anytime a card is drawn.
     *
     * @return The card object that was located at the top of the deck.
     */
    public Card draw() {
        if (drawPile.size() < 1) {
            throw new IllegalArgumentException("Cannot draw when there are no cards remaining");
        }
        Card drawn = drawPile.get(drawPile.size() - 1);
        drawPile.remove(drawPile.size() - 1);
        return drawn;
    }

    /**
     * Take the bottom card from the deck, remove it from the deck,
     * and returns it to the caller. Used for {@link CardType#DRAW_FROM_THE_BOTTOM}.
     *
     * @return The card object that was located at the bottom of the deck.
     */
    public Card drawAtBottom() {
        if (drawPile.size() < 1) {
            throw new IllegalArgumentException("Cannot draw when there are no cards remaining");
        }
        Card drawn = drawPile.get(0);
        drawPile.remove(0);
        return drawn;
    }

    /**
     * Inserts the given card at the given point in the deck.
     *
     * @param card  the card to insert
     * @param depth the depth to insert it at
     */
    public void insertCard(Card card, int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Cannot insert a card at a negative depth");
        }
        if (depth > drawPile.size()) {
            throw new IllegalArgumentException("Deck depth is not that large");
        }
        drawPile.add(drawPile.size() - depth, card);
    }

    public void shuffle() {
        Collections.shuffle(drawPile, random);
    }
}
