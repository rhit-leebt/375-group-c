package team5.explodingkittens.model.cardaction;


import team5.explodingkittens.controller.GameController;

public class AlterTheFutureAction implements CardAction {
    @Override
    public void applyAction(GameController controller) {
        controller.alterTheFuture();
    }
}
