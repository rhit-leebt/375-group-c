package team5.explodingkittens.model.cardaction;

import team5.explodingkittens.controller.GameController;

/**
 * The action that is performed when the See the Future card is played.
 *
 * @author Maura Coriale
 */
public class SeeTheFutureAction implements CardAction {

    @Override
    public void applyAction(GameController controller) {
        controller.seeTheFuture();
    }
}
