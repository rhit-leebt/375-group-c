package team5.explodingkittens.model.cardaction;

import team5.explodingkittens.controller.GameController;

/**
 * The action that is performed when a card that does not perform an action is played.
 *
 * @author Duncan McKee
 */
public class NullCardAction implements CardAction {
    @Override
    public void applyAction(GameController controller) {

    }
}
