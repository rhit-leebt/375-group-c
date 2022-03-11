package team5.explodingkittens.view;

import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import team5.explodingkittens.model.Card;


/**
 * The UI element that organizes a card for the player's hand UI element.
 *
 * @author Duncan McKee, Maura Coriale
 */
public class UiHandCard extends UiCard {

    private Card card;

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
}

