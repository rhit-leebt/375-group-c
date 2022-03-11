package team5.explodingkittens.model.cardaction;

import team5.explodingkittens.controller.GameController;

/**
 * The action that is performed when a Skip card is played.
 *
 * @author Duncan McKee
 */
public class SkipCardAction implements CardAction {
    @Override
    public void applyAction(GameController controller) {
        controller.skipAction();
    }
}
