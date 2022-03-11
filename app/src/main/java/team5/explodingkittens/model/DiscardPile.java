package team5.explodingkittens.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A storage location for {@link Card}, where an order will be
 * maintained, so that card redrawing can be performed. Used
 * for storing cards after they have been played once.
 *
 * @author Duncan McKee
 */
public class DiscardPile {
    private final List<Card> discardPileCards;

    public DiscardPile() {
        discardPileCards = new ArrayList<>();
    }

    public void discardCard(Card card) {
        discardPileCards.add(card);
    }

    /**
     * Takes a card from the discard pile at a given depth,
     * remove it from the discard pile, and then return it.
     *
     * @param depth The depth to take a card from.
     * @return A card that was located in the discard pile at the provided depth.
     */
    public Card takeCard(int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Cannot take from a negative depth");
        }
        if (discardPileCards.size() <= 0) {
            throw new IllegalStateException("Cannot take from an empty pile");
        }
        if (depth >= discardPileCards.size()) {
            throw new IllegalArgumentException("That index is larger than the deck");
        }
        return discardPileCards.get(discardPileCards.size() - 1 - depth);
    }

    public List<Card> viewCards() {
        return discardPileCards;
    }

    public int getSize() {
        return discardPileCards.size();
    }

}
