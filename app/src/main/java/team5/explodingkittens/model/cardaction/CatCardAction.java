package team5.explodingkittens.model.cardaction;

import team5.explodingkittens.controller.GameController;

/**
 * The abstract class that is the super class for each cat card type action.
 *
 * @author Duncan McKee
 */
public class CatCardAction implements CardAction {
    public void applyAction(GameController controller) {
        controller.startCatCard();
    }
}
