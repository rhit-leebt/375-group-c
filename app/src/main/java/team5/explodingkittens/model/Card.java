package team5.explodingkittens.model;

import team5.explodingkittens.controller.GameController;
import team5.explodingkittens.controller.ResourceController;

/**
 * The main bridge between generic details of a card type
 * and their gameplay usage. Connects {@link CardType}
 * and {@link Deck} mainly, but can be found elsewhere too.
 *
 * @author Duncan McKee, Andrew Orians
 */
public class Card implements Comparable<Card> {
    public final CardType type;

    /**
     * Constructs a card from a given card type and the
     * index of which the card art will be.
     *
     * @param type A card type that this card will be created to model.
     */
    public Card(CardType type) {
        this.type = type;
    }

    public boolean checkForExplodingKitten() {
        return type == CardType.EXPLODING_KITTEN;
    }

    public boolean checkForDefuse() {
        return type == CardType.DEFUSE;
    }

    /**
     * Checks if this card is a cat card.
     *
     * @return If this card is a cat card.
     */
    public boolean checkForCatCard() {
        return type == CardType.TACOCAT
                || type == CardType.BEARD_CAT
                || type == CardType.HAIRY_POTATO_CAT
                || type == CardType.CATTERMELON
                || type == CardType.RAINBOW_RALPHING_CAT
                || type == CardType.FERAL_CAT;
    }

    /**
     * Checks if this card and the other card can be played together.
     *
     * @param other The card to compare to
     * @return If the cards can be played together
     */
    public boolean isSameCatTypeAs(Card other) {
        return other.checkForCatCard() && this.checkForCatCard()
                && (other.type == CardType.FERAL_CAT
                || this.type == CardType.FERAL_CAT
                || this.type == other.type);
    }

    public String getImagePath() {
        return type.getPath();
    }

    public String getName() {
        return ResourceController.getCardName(type);
    }

    public String getCardInfo() {
        return ResourceController.getCardInfo(type);
    }

    public void playCard(GameController controller) {
        type.playCard(controller);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int compareTo(Card card) {
        return this.type.getGenericName().compareTo(card.type.getGenericName());
    }
}
