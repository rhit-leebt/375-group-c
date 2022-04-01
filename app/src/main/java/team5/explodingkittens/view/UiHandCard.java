package team5.explodingkittens.view;

import team5.explodingkittens.model.Card;


/**
 * The UI element that organizes a card for the player's hand UI element.
 *
 * @author Duncan McKee, Maura Coriale
 */
public class UiHandCard extends UiCard implements Comparable<UiHandCard>{

    private final Card card;

    /**
     * Creates the UiHandCard and its underlying infoPopup.
     *
     * @param card the underlying card for the UiHandCard
     */
    public UiHandCard(Card card) {
        this.card = card;
        setCard(card);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public void deselect() {
        super.deselect();
    }

    @Override
    public int compareTo(UiHandCard o) {
        return this.card.compareTo(o.getCard());
    }
}

