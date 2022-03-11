package team5.explodingkittens.model.cardaction;

import team5.explodingkittens.controller.GameController;

/**
 * An interface for each type of action that a card can apply.
 *
 * @author Duncan McKee
 */
public interface CardAction {
    void applyAction(GameController controller);
}
